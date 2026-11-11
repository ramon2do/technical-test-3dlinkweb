# Arquitectura de la Solución Modernizada

## 1. Decisión Arquitectónica: Monolito Modular
Se ha optado por un **Monolito Modular** en lugar de microservicios por las siguientes razones:
*   **Restricción de tiempo:** El plazo de 1 día no permite la sobrecarga operativa de gestionar múltiples servicios, comunicación distribuida y transacciones.
*   **Consistencia transaccional:** Un monolito modular garantiza la integridad referencial entre Owners, Pets y Visits sin necesidad de patrones como Saga.
*   **Delimitación clara:** Se mantienen límites estrictos entre módulos, lo que facilitaría una futura extracción a microservicios si fuera necesario.

## 2. Estructura de Módulos
El proyecto se divide en tres módulos funcionales claros:
*   **Owner Module:** Gestión de propietarios y sus mascotas (incluye PetTypes).
*   **Visit Module:** Registro de visitas médicas.
*   **Vet Module:** Consulta de veterinarios y especialidades.

## 3. Estructura Interna por Módulo (Arquitectura Hexagonal ligera)
Dentro de cada módulo, se aplica la separación sugerida:
*   `api/`: Controladores REST, DTOs de Request/Response y manejo de errores HTTP.
*   `application/`: Casos de uso (Servicios) y coordinación de operaciones.
*   `domain/`: Entidades JPA y reglas de negocio puras.
*   `infrastructure/`: Repositorios Spring Data y configuración de persistencia.

## 4. Contratos y Manejo de Errores
*   **DTOs:** No se exponen entidades JPA. Se usan DTOs (records) con anotaciones de validación.
*   **Errores:** Se utiliza `@RestControllerAdvice` con `ProblemDetail` (estándar de Spring 6) para devolver errores HTTP estandarizados (400, 404, 500).
*   **Documentación:** OpenAPI/Swagger generado automáticamente con `springdoc-openapi`.
