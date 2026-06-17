# 📚 Book E-Commerce Microservices

Applicazione e-commerce per la gestione di libri basata su architettura a microservizi con Spring Boot. Il backend espone API REST consumate da un frontend Vue 3.

**Repository Backend:** [book-ecommerce-microservices](https://github.com/raff-E12/book-ecommerce-microservices)
**Repository Frontend:** [vue-book-gestionale-frontend](https://github.com/raff-E12/vue-book-gestionale-frontend)

---

## 🗺️ Architettura

```
                         ┌────────────────────────┐
                         │   Vue 3 Frontend       │
                         │  (vue-book-gestionale) │
                         └────────┬───────────────┘
                                  │ HTTP
                         ┌────────▼────────────┐
                         │   Gateway Service   │  ← porta unica d'ingresso
                         │   (porta 8089)      │
                         └────────┬────────────┘
                                  │
              ┌───────────────────┼───────────────────┐
              │                   │                   │
   ┌──────────▼──────┐  ┌─────────▼──────┐  ┌────────▼──────────┐
   │  Book Service   │  │ Orders Service │  │  Book Rating      │
   │  (porta 8085)   │  │  (porta 8082)  │  │  Service (8081)   │
   └─────────────────┘  └───────┬────────┘  └───────────────────┘
                                │
                    ┌───────────▼────────────┐
                    │  Notification Service  │  ← avviato con Docker
                    │  Kafka + Zipkin        │
                    └────────────────────────┘

Tutti i microservizi si registrano su:
┌─────────────────────┐        ┌──────────────────────┐
│  Eureka Server      │        │  Config Server       │
│ (discovery-client)  │        │ (book-spring-config) │
│  (porta 8761)       │        │  (porta 8084)        │
└─────────────────────┘        └──────────────────────┘
```

---

## 📦 Microservizi

| Servizio | Modulo | Porta | Descrizione |
|---|---|---|---|
| Config Server | `book-spring-config` | 8084 | Configurazione centralizzata per tutti i microservizi |
| Eureka Server | `discovery-client-services` | 8761 | Service discovery e registrazione dei microservizi |
| API Gateway | `gateway-services` | 8089 | Punto d'ingresso unico, routing verso i servizi |
| Book Service | `book-services` | 8085 | CRUD catalogo libri |
| Orders Service | `orders-services` | 8082 | Gestione ordini, transazioni via Kafka |
| Book Rating | `book-rating` | 8081 | Recensioni e valutazioni dei libri |
| Notification Service | `services-notification` | — | Notifiche asincronie via Kafka; avviato con **Docker** |

---

## 🛠️ Tecnologie

**Backend**
- Java 17 + Spring Boot 3
- Spring Cloud (Eureka, Config Server, Gateway)
- Apache Kafka — messaggistica asincrona per gli ordini
- Zipkin — distributed tracing delle transazioni
- PostgreSQL — database relazionale
- Docker — containerizzazione del Notification Service

**Frontend**
- Vue 3 + TypeScript
- Vite
- Axios per le chiamate HTTP al Gateway

---

## ✅ Prerequisiti

Assicurati di avere installato:

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Node.js 18+ e npm
- PostgreSQL in esecuzione (oppure via Docker)
- Apache Kafka e Zookeeper in esecuzione localmente (oppure via Docker)

---

## 🚀 Avvio del progetto

### Ordine di avvio consigliato

I microservizi devono essere avviati **nell'ordine seguente** per garantire che le dipendenze (Config Server, Eureka) siano pronte prima degli altri servizi.

---

### 1. Config Server

```bash
cd book-spring-config
mvn spring-boot:run
```

Attendi che il servizio sia avviato (porta **8888**) prima di procedere.

---

### 2. Eureka Server

```bash
cd discovery-client-services
mvn spring-boot:run
```

Dashboard disponibile su: [http://localhost:8761](http://localhost:8761)

---

### 3. File .env

Mi raccomando imposta nel fil .env l'username e la password con il Postgress in collegamento:

```bash
DB_USERNAME= XXXXXXX
DB_PASSWORD= XXXXXXX
```

---

### 4. Microservizi principali

Avvia ciascuno in un terminale separato:

```bash
# Book Service
cd book-services
mvn spring-boot:run

# Orders Service
cd orders-services
mvn spring-boot:run

# Book Rating Service
cd book-rating
mvn spring-boot:run

# API Gateway
cd gateway-services
mvn spring-boot:run
```

---

### 5. Inizializzazione Database

Prima di avviare i microservizi, inizializza il database PostgreSQL con il tool CLI dedicato:

```bash
cd db-init
npm install
npm run db:init
```

Il tool ti guiderà in modo interattivo nella configurazione (host, utente, password, nome database) e nella scelta dell'operazione da eseguire:

| Comando | Descrizione |
|---|---|
| `npm run init` | Modalità interattiva completa |
| `npm run create` | Crea database + schema |
| `npm run migrate` | Applica modifiche incrementali |
| `npm run reset` | Drop e ricrea da zero |
| `npm run check` | Verifica solo la connessione |

> Il tool esegue automaticamente un health check su PostgreSQL prima di procedere, con retry in caso il database non sia ancora pronto.

---

### 6. Notification Service (Docker)

Il Notification Service (con Kafka consumer e Zipkin) si avvia tramite Docker Compose:

```bash
cd services-notification
docker compose up -d
```

Per visualizzare i log:

```bash
docker compose logs -f
```

Per fermare i container:

```bash
docker compose down
```

---

### 7. Frontend Vue 3

```bash
cd vue-book-gestionale-frontend
npm install
npm run dev
```

L'applicazione sarà disponibile su: [http://localhost:5173](http://localhost:5173)

> Il frontend comunica esclusivamente con il **Gateway** su `http://localhost:8080`.

---

## 🔍 Monitoring & Tracing

### Eureka Dashboard
Visualizza tutti i microservizi registrati:
```
http://localhost:8761
```

### Zipkin — Distributed Tracing
Visualizza le trace delle chiamate tra microservizi:
```
http://localhost:9411
```
> Zipkin è incluso nel `docker-compose.yml` del Notification Service.

---

## 📁 Struttura del repository

```
book-ecommerce-microservices/
├── book-spring-config/          # Config Server
├── discovery-client-services/   # Eureka Server
├── gateway-services/            # API Gateway
├── book-services/               # Microservizio Libri
├── orders-services/              # Microservizio Ordini
├── book-rating/                  # Microservizio Recensioni
├── DB/                       # Tool CLI inizializzazione DB
└── services-notification/        # Notification Service (Docker)
    └── docker-compose.yml
```

```
vue-book-gestionale-frontend/
├── src/
│   ├── components/
│   ├── views/
│   └── ...
├── .env                         # Configurazione URL Gateway
└── vite.config.ts
```

---

## ⚙️ Variabili d'ambiente principali

Nel file `.env` del frontend:

```env
VITE_API_BASE_URL=http://localhost:8080
```

Per i microservizi Spring Boot, la configurazione è centralizzata nel Config Server (`book-spring-config`). Ogni servizio punta al Config Server tramite `application.properties`:

```properties
  spring.cloud.config.discovery.enabled=true
  spring.cloud.config.discovery.service-id=spring-config-book
  spring.config.import=optional:configserver:http://localhost:8084
```

## Premessa

Questo progetto è sviluppato a scopo didattico.