package com.users.book.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.users.book.models.UserModel;
import com.users.book.repository.UserRepository;
import com.users.book.global.AccessResponse;
import com.users.book.global.RegisterResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    // Registrazione utente
    public Map<String, Object> registerUser(RegisterResponse registerRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Verifica se l'email esiste già
            Optional<UserModel> existingUser = userRepository.findByEmail(registerRequest.email());
            if (existingUser.isPresent()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Email già esistente nel database.");
                return response;
            }

            // Crea nuovo utente
            UserModel newUser = new UserModel(
                registerRequest.fullname(),
                registerRequest.email(),
                registerRequest.password(), // Nota: in produzione, criptare la password
                registerRequest.role(),
                registerRequest.verified()
            );
            userRepository.save(newUser);

            response.put("success", true);
            response.put("error", false);
            response.put("message", "Registrazione completata con successo.");
            response.put("data", newUser);
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", true);
            response.put("message", "Errore durante la registrazione: " + e.getMessage());
            return response;
        }
    }

    // Accesso/Login
    public Map<String, Object> loginUser(AccessResponse accessRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Verifica se l'utente esiste
            Optional<UserModel> user = userRepository.findByEmail(accessRequest.email());
            System.out.println(user);
            if (user.isEmpty()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Utente non trovato nel database.");
                return response;
            }

            // Verifica password (semplice confronto, in produzione usare BCrypt)
            if (!user.get().getPassword().equals(accessRequest.password())) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Password errata.");
                return response;
            }

            response.put("success", true);
            response.put("error", false);
            response.put("message", "Accesso riuscito.");
            response.put("data", user.get());
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", true);
            response.put("message", "Errore durante l'accesso: " + e.getMessage());
            return response;
        }
    }

    // Ottieni tutti gli utenti
    public Map<String, Object> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<UserModel> users = userRepository.findAll();
            response.put("success", true);
            response.put("error", false);
            response.put("message", "Lista utenti recuperata.");
            response.put("data", users);
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", true);
            response.put("message", "Errore nel recupero degli utenti: " + e.getMessage());
            return response;
        }
    }

    // Ottieni utente per ID
    public Map<String, Object> getUserById(Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserModel> user = userRepository.findById(id);
            if (user.isEmpty()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Utente con ID " + id + " non trovato nel database.");
                return response;
            }
            response.put("success", true);
            response.put("error", false);
            response.put("message", "Utente recuperato.");
            response.put("data", user.get());
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", true);
            response.put("message", "Errore nel recupero dell'utente: " + e.getMessage());
            return response;
        }
    }

    // Aggiorna utente
    public Map<String, Object> updateUser(Integer id, UserModel updatedUser) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserModel> existingUser = userRepository.findById(id);
            if (existingUser.isEmpty()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Utente con ID " + id + " non trovato nel database.");
                return response;
            }

            // Verifica email unica se cambiata
            if (!existingUser.get().getEmail().equals(updatedUser.getEmail())) {
                Optional<UserModel> emailCheck = userRepository.findByEmail(updatedUser.getEmail());
                if (emailCheck.isPresent()) {
                    response.put("success", false);
                    response.put("error", true);
                    response.put("message", "Email già esistente nel database.");
                    return response;
                }
            }

            updatedUser.setId(id);
            userRepository.save(updatedUser);

            response.put("success", true);
            response.put("error", false);
            response.put("message", "Utente aggiornato con successo.");
            response.put("data", updatedUser);
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", true);
            response.put("message", "Errore nell'aggiornamento dell'utente: " + e.getMessage());
            return response;
        }
    }

    // Elimina utente
    public Map<String, Object> deleteUser(Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserModel> user = userRepository.findById(id);
            if (user.isEmpty()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Utente con ID " + id + " non trovato nel database.");
                return response;
            }

            userRepository.deleteById(id);
            response.put("success", true);
            response.put("error", false);
            response.put("message", "Utente eliminato con successo.");
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", true);
            response.put("message", "Errore nell'eliminazione dell'utente: " + e.getMessage());
            return response;
        }
    }
}
