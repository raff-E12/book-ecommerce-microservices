package com.book.rate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.rate.global.AccessResponse;
import com.book.rate.global.RegisterResponse;
import com.book.rate.models.UserModel;
import com.book.rate.repository.UserRepositery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepositery userRepository;

    public Map<String, Object> registerUser(RegisterResponse registerRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserModel> existingUser = userRepository.findByEmailNative(registerRequest.email());
            if (existingUser.isPresent()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Email già in uso.");
                return response;
            }

            UserModel newUser = new UserModel(
                registerRequest.fullname(),
                registerRequest.email(),
                registerRequest.password(),
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

    public Map<String, Object> loginUser(AccessResponse accessRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            String email = accessRequest.email().trim();
            String password = accessRequest.password().trim();
            Optional<UserModel> user = userRepository.findByEmailNative(email);

            if (user.isEmpty()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Utente non trovato.");
                return response;
            }

            if (!user.get().getPassword().equals(password)) {
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

    public Map<String, Object> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<UserModel> users = userRepository.findAllNative();
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

    public Map<String, Object> getUserById(Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserModel> user = userRepository.findByIdNative(id);
            if (user.isEmpty()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Utente non trovato.");
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

    public Map<String, Object> updateUser(Integer id, UserModel updatedUser) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserModel> existingUser = userRepository.findByIdNative(id);
            if (existingUser.isEmpty()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Utente non trovato.");
                return response;
            }

            if (!existingUser.get().getEmail().equalsIgnoreCase(updatedUser.getEmail())) {
                Optional<UserModel> emailUser = userRepository.findByEmailNative(updatedUser.getEmail());
                if (emailUser.isPresent()) {
                    response.put("success", false);
                    response.put("error", true);
                    response.put("message", "Email già in uso da un altro utente.");
                    return response;
                }
            }

            int updatedRows = userRepository.updateUserNative(
                id,
                updatedUser.getNomeCompleto(),
                updatedUser.getEmail(),
                updatedUser.getPassword(),
                updatedUser.getRole(),
                updatedUser.getVerified()
            );

            if (updatedRows == 0) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Aggiornamento fallito.");
                return response;
            }

            response.put("success", true);
            response.put("error", false);
            response.put("message", "Utente aggiornato con successo.");
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", true);
            response.put("message", "Errore durante l'aggiornamento: " + e.getMessage());
            return response;
        }
    }

    public Map<String, Object> deleteUser(Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserModel> existingUser = userRepository.findByIdNative(id);
            if (existingUser.isEmpty()) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Utente non trovato.");
                return response;
            }

            int deletedRows = userRepository.deleteByIdNative(id);
            if (deletedRows == 0) {
                response.put("success", false);
                response.put("error", true);
                response.put("message", "Eliminazione fallita.");
                return response;
            }

            response.put("success", true);
            response.put("error", false);
            response.put("message", "Utente eliminato con successo.");
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", true);
            response.put("message", "Errore durante l'eliminazione dell'utente: " + e.getMessage());
            return response;
        }
    }
}
