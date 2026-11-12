# Technical Test - 3DLinkWeb (PetClinic Modernization)

## Descripción

Migración del proyecto original Spring PetClinic (MVC + Thymeleaf) a una API REST moderna con Java 21 y Spring Boot 4, aplicando una arquitectura de Monolito Modular con separación por dominios (Owners/Pets, Visits, Vets).

## Tecnologías Utilizadas

- Java 21
- Spring Boot 4.0.0
- Spring Data JPA (H2 en memoria)
- SpringDoc OpenAPI 3 (Swagger UI)
- JUnit 5 + Mockito (Pruebas)
- Maven (Wrapper incluido)

## Arquitectura

El proyecto sigue una arquitectura de Monolito Modular con la siguiente estructura por cada dominio:

owner/
api/           # Controladores REST, DTOs (records) y Mappers
application/   # Servicios (lógica de negocio)
domain/        # Entidades JPA
infrastructure/# Repositorios Spring Data

Los módulos implementados son:
- Owner Module: Gestión de propietarios, mascotas, tipos de mascota y visitas.
- Vet Module: Consulta de veterinarios y especialidades.

Para más detalles sobre las decisiones arquitectónicas (DDD, agrupación de entidades, etc.), consulta docs/02-arquitectura.md.

## Prerrequisitos

- JDK 21 o superior.
- Maven (o usar el wrapper incluido ./mvnw).
- Docker (opcional, para base de datos persistente).

## Perfiles de Spring

La aplicación utiliza el perfil por defecto de Spring Boot, configurado para usar una base de datos H2 en memoria. Esto permite ejecutar la aplicación sin necesidad de instalar un motor de base de datos externo.

Para entornos de producción, se podría configurar un perfil adicional (ej. prod) con PostgreSQL o MySQL, pero para el alcance de esta prueba se ha priorizado la simplicidad y la reproducibilidad en cualquier entorno.

## Ejecución

### 1. Clonar el repositorio
git clone https://github.com/ramon2do/technical-test-3dlinkweb.git
cd technical-test-3dlinkweb

### 2. Compilar y ejecutar
# Compilar el proyecto
./mvnw clean compile

# Ejecutar la aplicación
./mvnw spring-boot:run

La aplicación estará disponible en: http://localhost:8080

### 3. Acceder a Swagger UI
Una vez iniciada la aplicación, accede a la documentación interactiva de la API:
http://localhost:8080/swagger-ui.html

### 4. Ejecutar las pruebas
./mvnw test

## Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/v1/owners | Listar todos los propietarios |
| GET | /api/v1/owners/{id} | Obtener propietario por ID |
| POST | /api/v1/owners | Crear nuevo propietario |
| PUT | /api/v1/owners/{id} | Actualizar propietario |
| DELETE | /api/v1/owners/{id} | Eliminar propietario |
| GET | /api/v1/pet-types | Listar tipos de mascota |
| GET | /api/v1/pets/owner/{ownerId} | Listar mascotas por propietario |
| GET | /api/v1/pets/{id} | Obtener mascota por ID |
| POST | /api/v1/pets | Crear nueva mascota |
| PUT | /api/v1/pets/{id} | Actualizar mascota |
| DELETE | /api/v1/pets/{id} | Eliminar mascota |
| GET | /api/v1/visits/pet/{petId} | Listar visitas por mascota |
| GET | /api/v1/visits/{id} | Obtener visita por ID |
| POST | /api/v1/visits | Registrar nueva visita |
| DELETE | /api/v1/visits/{id} | Eliminar visita |
| GET | /api/v1/vets | Listar todos los veterinarios |
| GET | /api/v1/vets/{id} | Obtener veterinario por ID |

## Escenarios de Prueba Completos

A continuación se muestran ejemplos prácticos para probar la API desde la terminal (cURL) o desde Swagger UI.

### Escenario 1: Crear un Propietario (Éxito)
Petición:
curl -X POST http://localhost:8080/api/v1/owners \
-H "Content-Type: application/json" \
-d '{
"firstName": "Juan",
"lastName": "Perez",
"address": "Calle Falsa 123",
"city": "Springfield",
"telephone": "1234567890"
}'

Respuesta esperada (201 Created):
{
"id": 3,
"firstName": "Juan",
"lastName": "Perez",
"address": "Calle Falsa 123",
"city": "Springfield",
"telephone": "1234567890"
}

### Escenario 2: Crear un Propietario con Datos Inválidos (Error 400)
Petición:
curl -X POST http://localhost:8080/api/v1/owners \
-H "Content-Type: application/json" \
-d '{
"firstName": "",
"lastName": "Perez",
"address": "Calle Falsa 123",
"city": "Springfield",
"telephone": "123"
}'

Respuesta esperada (400 Bad Request): Un ProblemDetail con los campos inválidos (nombre vacío, teléfono con formato incorrecto).

### Escenario 3: Consultar un Propietario Inexistente (Error 404)
Petición:
curl -X GET http://localhost:8080/api/v1/owners/9999

Respuesta esperada (404 Not Found): Un ProblemDetail indicando que el propietario no existe.

### Escenario 4: Registrar una Visita (Éxito)
Petición:
curl -X POST http://localhost:8080/api/v1/visits \
-H "Content-Type: application/json" \
-d '{
"date": "2025-01-15",
"description": "Vacunación anual",
"petId": 1
}'

Respuesta esperada (201 Created): El JSON de la visita creada.

### Escenario 5: Consultar Veterinarios (Éxito)
Petición:
curl -X GET http://localhost:8080/api/v1/vets

Respuesta esperada (200 OK): La lista de veterinarios con sus especialidades.

### Escenario 6: Listar Tipos de Mascota (Éxito)
Petición:
curl -X GET http://localhost:8080/api/v1/pet-types

Respuesta esperada (200 OK): La lista de tipos de mascota (cat, dog, etc.).

### Escenario 7: Listar Mascotas por Dueño (Éxito)
Petición:
curl -X GET http://localhost:8080/api/v1/pets/owner/1

Respuesta esperada (200 OK): La lista de mascotas del propietario con ID 1 (ej. "Leo").

## Uso de la Colección de Postman

El proyecto incluye una colección de Postman con todos los endpoints para facilitar las pruebas manuales:

1. Abre Postman.
2. Haz clic en "Import" (Importar) en la esquina superior izquierda.
3. Selecciona el archivo docs/petclinic-api.postman_collection.json.
4. Una vez importada, verás las carpetas "Owners", "Pets", "Visits", "Vets" y "Pet Types" con las peticiones listas para ejecutar.

## Restablecer los Datos de Prueba

Para restablecer la base de datos a su estado inicial (con los datos de data.sql), simplemente detén y vuelve a iniciar la aplicación:

# Detener con Ctrl+C y luego:
./mvnw spring-boot:run

La base de datos H2 es en memoria, por lo que se recrea y repuebla automáticamente en cada arranque.

## Documentación Adicional

- Diagnóstico del Legacy: docs/01-diagnostico-legacy.md
- Arquitectura de la Solución: docs/02-arquitectura.md
- Plan de Migración: docs/03-plan-migracion.md
- Bitácora de Uso de IA: docs/04-uso-ia.md
- Colección de Postman: docs/petclinic-api.postman_collection.json

## Licencia

Este proyecto es una migración del Spring PetClinic original (https://github.com/spring-projects/spring-petclinic), bajo licencia Apache 2.0.
