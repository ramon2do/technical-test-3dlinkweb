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
api/          # Controladores REST, DTOs (records) y Mappers
application/  # Servicios (lógica de negocio)
domain/       # Entidades JPA
infrastructure/ # Repositorios Spring Data

Los módulos implementados son:
- Owners/Pets: Gestión de propietarios y sus mascotas.
- Visits: Registro de visitas médicas.
- Vets: Consulta de veterinarios y especialidades.

## Prerrequisitos

- JDK 21 o superior.
- Maven (o usar el wrapper incluido ./mvnw).
- Docker (opcional, para base de datos persistente).

## Ejecución

### 1. Clonar el repositorio
git clone https://github.com/ramon2do/technical-test-3dlinkweb.git
cd technical-test-3dlinkweb

### 2. Compilar y ejecutar
./mvnw clean compile

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

## Documentación Adicional

- Diagnóstico del Legacy: docs/01-diagnostico-legacy.md
- Arquitectura de la Solución: docs/02-arquitectura.md
- Plan de Migración: docs/03-plan-migracion.md
- Bitácora de Uso de IA: docs/04-uso-ia.md

## Licencia

Este proyecto es una migración del Spring PetClinic original (https://github.com/spring-projects/spring-petclinic), bajo licencia Apache 2.0.
