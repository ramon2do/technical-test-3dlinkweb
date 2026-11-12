# Bitácora de Uso de Inteligencia Artificial (Actualizada)

## 1. Herramientas y Modelos Utilizados
- **Herramienta principal:** Asistente de IA (Modelo de Lenguaje Grande).
- **IDE:** IntelliJ IDEA / VS Code (con sugerencias de código básicas).
- **Propósito:** Apoyo en el análisis del código legacy, diseño arquitectónico, generación de código boilerplate, resolución de conflictos de versiones (Spring Boot 4), limpieza de código y redacción de documentación.

## 2. Objetivos de Uso
La IA se utilizó como un **copiloto técnico** en las siguientes áreas:
1.  **Análisis del Legacy:** Identificación de entidades, validaciones y lógica de negocio.
2.  **Diseño Arquitectónico:** Discusión y justificación de la elección de un Monolito Modular, agrupación de entidades (DDD) y ubicación de entidades JPA.
3.  **Generación de Código:** Creación de la estructura base de los módulos (entidades, DTOs, mappers, servicios y controladores).
4.  **Resolución de Problemas:** Depuración de errores de compilación (Spring Boot 4, Jackson 3, orden de ejecución de `data.sql`, validaciones de teléfono).
5.  **Limpieza y Refactorización:** Identificación de archivos legacy innecesarios (SCSS, i18n, configuraciones de MySQL/PostgreSQL) y comandos de eliminación.
6.  **Documentación:** Redacción de `README.md`, `01-diagnostico-legacy.md`, `02-arquitectura.md`, `03-plan-migracion.md` y esta bitácora.

## 3. Prompts Representativos y Resultados (Sesión Completa)

| Prompt (Resumen) | Resultado de la IA | Decisión Tomada |
|------------------|--------------------|-----------------|
| *"Analiza el código de Spring PetClinic..."* | Identificó tecnologías y validaciones. | Base para el diagnóstico. |
| *"¿Monolito modular o microservicios?"* | Recomendó monolito modular por tiempo y consistencia. | Aceptado. |
| *"Dame los DTOs usando Java 21."* | Generó clases con getters/setters. | Rechazado parcialmente: se usaron `records`. |
| *"Migrar a Spring Boot 4 y Java 21."* | Proporcionó el `pom.xml` actualizado. | Aceptado y verificado. |
| *"Error con `PetClinicRuntimeHints`."* | Recomendó eliminar la clase AOT obsoleta. | Aceptado. |
| *"Error con `ObjectMapper` en tests."* | Indicó usar `JsonMapper` (Jackson 3). | Aceptado y corregido. |
| *"Error de validación de teléfono."* | Sugirió ajustar la regla `@Pattern` y crear `messages.properties`. | Aceptado. |
| *"Error en `data.sql` (tabla no encontrada)."* | Recomendó `spring.jpa.defer-datasource-initialization=true`. | Aceptado. |
| *"Error de integridad referencial en `data.sql`."* | Sugirió simplificar el script con IDs controlados. | Aceptado. |
| *"¿Deberían las entidades JPA ir en infraestructura?"* | Explicó el ideal hexagonal y el pragmatismo de mantenerlas en `domain`. | Aceptado: se mantuvieron en `domain` y se justificó en la documentación. |
| *"¿Por qué agrupar Visit en owner?"* | Explicó los conceptos de Aggregate Root y Bounded Context (DDD). | Aceptado y documentado en `02-arquitectura.md`. |
| *"¿Qué archivos legacy sobran?"* | Identificó SCSS, i18n, configuraciones de MySQL/PostgreSQL, Checkstyle, JMeter. | Aceptado: se eliminaron. |

## 4. Sugerencias Aceptadas, Rechazadas o Corregidas

- **Aceptadas:** Estructura modular, `ProblemDetail` para errores, `springdoc` para OpenAPI, `JsonMapper` para tests, `defer-datasource-initialization` para el orden de carga de BD, justificación DDD.
- **Rechazadas:** Uso de Lombok (se prefirieron `records`), implementación de microservicios (por tiempo), separación estricta de entidades JPA (por pragmatismo).
- **Corregidas:**
    - La IA asumió Jackson 2 en los tests; se corrigió a Jackson 3 (`JsonMapper`).
    - La IA asumió que `Pet` ya tenía `getOwner()`/`setOwner()`; se corrigió la entidad.
    - La IA no detectó el conflicto de orden en `data.sql`; se corrigió con la propiedad de Spring.
    - La IA no previó el error de integridad referencial en el script; se simplificó el `data.sql`.

## 5. Validación Humana del Resultado
1.  **Compilación:** Cada bloque se compiló con `./mvnw clean compile`.
2.  **Ejecución:** Se levantó la app y se verificó en Swagger UI que los endpoints funcionan.
3.  **Pruebas Automatizadas:** `./mvnw test` pasó con 9 tests (5 integración, 4 unitarios).
4.  **Revisión de Reglas de Negocio:** Se comparó la lógica con el código original (validaciones de teléfono, fechas, etc.).
5.  **Revisión de Código:** Se revisó manualmente cada archivo para asegurar que no hubiera dependencias circulares, código muerto o malas prácticas.

## 6. Limitaciones y Errores de IA Detectados
- **Desconocimiento de versiones específicas:** Asumió inicialmente Spring Boot 3; se corrigió a Spring Boot 4.
- **Problemas con Jackson 3:** No detectó de inmediato la migración de Jackson 2 a 3 en Spring Boot 4.
- **Sobrescritura de código:** En una ocasión, sugirió reemplazar `Pet` sin tener en cuenta la relación con `Visit`, causando un error de compilación.
- **Falta de contexto en scripts SQL:** No previó el orden de ejecución ni las dependencias entre tablas en `data.sql`.

## 7. Conclusión
La IA fue una herramienta valiosa para acelerar el desarrollo y resolver dudas técnicas, pero **todas las decisiones fueron revisadas y validadas por el desarrollador**. El código final es el resultado de un proceso iterativo de generación, revisión, corrección y prueba.
