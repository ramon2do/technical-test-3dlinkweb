# Diagnóstico del Código Legacy (Spring PetClinic)

## 1. Tecnologías y Versiones Encontradas
*   **Framework:** Spring Boot (versión original basada en Spring MVC).
*   **Vistas:** Thymeleaf (renderizado en servidor).
*   **Persistencia:** Spring Data JPA con Hibernate.
*   **Base de Datos:** H2 (en memoria) por defecto. Soporte para MySQL y PostgreSQL vía perfiles.
*   **Build:** Maven (Wrapper incluido).
*   **Java:** Versión original 17+.

## 2. Componentes y Dependencias Principales
*   **Modelo de Dominio:** `Owner`, `Pet`, `Visit`, `Vet`, `Specialty`, `PetType`.
*   **Controladores MVC:** `OwnerController`, `PetController`, `VisitController`, `VetController`.
*   **Repositorios:** Interfaces que extienden de `JpaRepository`.
*   **Validaciones:** Uso de anotaciones de Jakarta Validation (`@NotBlank`, `@Size`, etc.) y validadores personalizados como `PetValidator`.

## 3. Funcionalidades y Reglas de Negocio Identificadas
*   **Owners (Propietarios):** Búsqueda por apellido, creación, actualización. Regla: El teléfono debe tener al menos 10 dígitos.
*   **Pets (Mascotas):** Creación asociada a un dueño. Regla: El nombre no puede estar vacío; la fecha de nacimiento no puede ser futura.
*   **Visits (Visitas):** Registro de visitas asociadas a una mascota. Regla: La descripción no puede estar vacía.
*   **Vets (Veterinarios):** Consulta de listado y especialidades.

## 4. Deuda Técnica y Riesgos de Migración
*   **Lógica en Controladores:** La lógica de negocio está acoplada en los controladores MVC (no hay capa de servicio explícita).
*   **Uso de Entidades en Vistas:** Las entidades JPA se usan directamente en Thymeleaf, lo que no es apto para APIs REST.
*   **Riesgo:** Perder validaciones al migrar de MVC a REST. Se mitigará utilizando DTOs con Bean Validation y pruebas unitarias.

## 5. Elementos a Mantener y Reemplazar
*   **Mantener:** Modelo de datos, reglas de validación, configuración de base de datos.
*   **Reemplazar:** Controladores MVC por Controladores REST, vistas Thymeleaf por respuestas JSON (DTOs).
