# Plan de Migración

## 1. Alcance Migrado

Se ha migrado la totalidad de las capacidades funcionales del proyecto original Spring PetClinic, transformando una aplicación MVC con vistas Thymeleaf en una API REST/JSON.

**Capacidades migradas:**
- **Owners (Propietarios):** CRUD completo (crear, leer, actualizar, eliminar) y búsqueda por ID.
- **Pets (Mascotas):** CRUD completo, asociación con propietarios y tipos de mascota.
- **Visits (Visitas):** Registro y consulta de visitas médicas asociadas a mascotas.
- **Vets (Veterinarios):** Consulta de veterinarios y sus especialidades.

**Capacidades no migradas (fuera de alcance):**
- Vistas Thymeleaf (reemplazadas por respuestas JSON).
- Formularios HTML y validación del lado del cliente.
- Funcionalidades específicas de la interfaz web (como la paginación visual).

## 2. Secuencia de Migración

La migración se realizó de forma incremental, siguiendo el flujo de GitFlow:

1.  **Fase A - Diagnóstico:** Análisis del código legacy, identificación de entidades, reglas de negocio y validaciones (documentado en `01-diagnostico-legacy.md`).
2.  **Fase B - Arquitectura:** Definición del monolito modular y la estructura de carpetas (`02-arquitectura.md`).
3.  **Fase C - Implementación:**
    - **Setup:** Configuración de Spring Boot 4, Java 21 y dependencias REST.
    - **Módulo Owners/Pets:** Migración de entidades, creación de DTOs (records), mappers, servicios y controladores.
    - **Módulo Visits:** Implementación del registro de visitas.
    - **Módulo Vets:** Implementación de la consulta de veterinarios.
    - **Limpieza:** Eliminación de archivos legacy (controladores MVC, vistas, formateadores).
4.  **Fase D - Pruebas y Validación:** Creación de tests unitarios (Mockito) y de integración (MockMvc) para el módulo de Owners.
5.  **Fase E - Documentación:** Redacción de README, arquitectura y bitácora de IA.

## 3. Riesgos y Mitigación

| Riesgo | Impacto | Mitigación |
|--------|---------|------------|
| Pérdida de validaciones de negocio al migrar de MVC a REST | Alto | Se migraron las validaciones a los DTOs usando Jakarta Validation (`@NotBlank`, `@Size`, `@PastOrPresent`, etc.) y se probaron con tests unitarios. |
| Incompatibilidad de Spring Boot 4 con APIs de versiones anteriores | Medio | Se actualizaron las dependencias y se adaptaron los tests (uso de `JsonMapper` en lugar de `ObjectMapper`, `@MockitoBean` en lugar de `@MockBean`). |
| Complejidad de la arquitectura de microservicios en tiempo limitado | Alto | Se optó por un monolito modular bien delimitado, que ofrece los mismos beneficios de separación sin la sobrecarga operativa. |
| Falta de pruebas automatizadas | Medio | Se implementaron tests unitarios y de integración para el módulo más crítico (Owners). |

## 4. Matriz de Correspondencia: Original vs. Nuevo

| Funcionalidad Original (MVC) | Endpoint Nuevo (REST) | Método HTTP | Estado |
|-------------------------------|------------------------|-------------|--------|
| Listar propietarios (`/owners`) | `/api/v1/owners` | GET | ✅ Migrado |
| Ver propietario por ID (`/owners/{id}`) | `/api/v1/owners/{id}` | GET | ✅ Migrado |
| Crear propietario (`/owners/new`) | `/api/v1/owners` | POST | ✅ Migrado |
| Actualizar propietario (`/owners/{id}/edit`) | `/api/v1/owners/{id}` | PUT | ✅ Migrado |
| Eliminar propietario (lógica original) | `/api/v1/owners/{id}` | DELETE | ✅ Migrado |
| Listar mascotas de un propietario | `/api/v1/pets/owner/{ownerId}` | GET | ✅ Migrado |
| Ver mascota por ID | `/api/v1/pets/{id}` | GET | ✅ Migrado |
| Crear mascota | `/api/v1/pets` | POST | ✅ Migrado |
| Actualizar mascota | `/api/v1/pets/{id}` | PUT | ✅ Migrado |
| Eliminar mascota | `/api/v1/pets/{id}` | DELETE | ✅ Migrado |
| Registrar visita | `/api/v1/visits` | POST | ✅ Migrado |
| Listar visitas de una mascota | `/api/v1/visits/pet/{petId}` | GET | ✅ Migrado |
| Ver visita por ID | `/api/v1/visits/{id}` | GET | ✅ Migrado |
| Eliminar visita | `/api/v1/visits/{id}` | DELETE | ✅ Migrado |
| Listar veterinarios | `/api/v1/vets` | GET | ✅ Migrado |
| Ver veterinario por ID | `/api/v1/vets/{id}` | GET | ✅ Migrado |

## 5. Decisiones Técnicas

- **DTOs como `records`:** Se utilizaron `records` de Java 21 para los DTOs de entrada y salida, garantizando inmutabilidad y reduciendo el código boilerplate.
- **Separación de responsabilidades:** Cada módulo tiene sus propias capas (`api`, `application`, `domain`, `infrastructure`), evitando el acoplamiento entre dominios.
- **Manejo global de errores:** Se implementó un `@RestControllerAdvice` con `ProblemDetail` para estandarizar las respuestas de error (400, 404, 500).
- **Documentación con OpenAPI:** Se añadió `springdoc-openapi` para generar automáticamente la documentación de la API, facilitando su consumo y prueba.
