# Bitácora de Uso de Inteligencia Artificial

## 1. Herramientas y Modelos Utilizados

- **Herramienta principal:** Asistente de IA (Modelo de Lenguaje Grande).
- **IDE:** IntelliJ IDEA / VS Code (con sugerencias de código básicas).
- **Propósito:** Apoyo en el análisis del código legacy, diseño arquitectónico, generación de código boilerplate (DTOs, Mappers), resolución de conflictos de versiones (Spring Boot 4) y redacción de documentación.

## 2. Objetivos de Uso

La IA se utilizó como un **copiloto técnico** en las siguientes áreas:
1.  **Análisis del Legacy:** Identificación de las entidades, validaciones y lógica de negocio del proyecto original Spring PetClinic.
2.  **Diseño Arquitectónico:** Discusión y justificación de la elección de un Monolito Modular frente a Microservicios para un plazo de 1 día.
3.  **Generación de Código:** Creación de la estructura base de los módulos (entidades, DTOs, mappers, servicios y controladores).
4.  **Resolución de Problemas:** Depuración de errores de compilación (ej. compatibilidad de Spring Boot 4 con Jackson 3, dependencias AOT, etc.).
5.  **Documentación:** Redacción de los archivos `README.md`, `01-diagnostico-legacy.md`, `02-arquitectura.md` y `03-plan-migracion.md`.

## 3. Prompts Representativos y Resultados

| Prompt (Resumen) | Resultado de la IA | Decisión Tomada |
|------------------|--------------------|-----------------|
| *"Analiza el código de Spring PetClinic y dime qué tecnologías y validaciones usa."* | Identificó Spring Boot, Thymeleaf, JPA, y validaciones como `@NotBlank` y `PetValidator`. | Se usó como base para el `01-diagnostico-legacy.md`. |
| *"¿Monolito modular o microservicios para una prueba de 1 día?"* | Recomendó monolito modular por consistencia transaccional y menor sobrecarga operativa. | **Aceptado.** Se documentó la justificación en `02-arquitectura.md`. |
| *"Dame los DTOs para Owner y Pet usando Java 21."* | Generó clases con getters/setters tradicionales. | **Rechazado parcialmente.** Se solicitó refactorizar a `records` de Java 21 para mayor inmutabilidad y limpieza. |
| *"¿Cómo migrar el proyecto a Spring Boot 4 y Java 21?"* | Proporcionó el `pom.xml` actualizado con `spring-boot-starter-webmvc` y `springdoc-openapi` 3.x. | **Aceptado.** Se aplicó y se verificó la compilación. |
| *"Error: cannot find symbol withJavaSerialization en PetClinicRuntimeHints"* | Explicó que era una API de AOT obsoleta en Spring Boot 4 y recomendó eliminar la clase. | **Aceptado.** Se eliminó la clase por no ser necesaria para el alcance REST. |
| *"Error: No qualifying bean of type ObjectMapper en los tests"* | Indicó que Spring Boot 4 usa Jackson 3 por defecto y que se debe usar `JsonMapper` en su lugar. | **Aceptado.** Se actualizó el test y los 9 tests pasaron. |

## 4. Sugerencias Aceptadas, Rechazadas o Corregidas

- **Aceptadas:**
    - La estructura de carpetas modular (`api`, `application`, `domain`, `infrastructure`).
    - El uso de `ProblemDetail` para el manejo global de errores.
    - La configuración de OpenAPI con `springdoc`.
- **Rechazadas:**
    - El uso de Lombok para los DTOs. Se prefirió usar `records` nativos de Java 21 para evitar dependencias externas y aprovechar las ventajas del lenguaje.
    - La implementación de microservicios, ya que el tiempo no permitía gestionar transacciones distribuidas ni comunicación entre servicios.
- **Corregidas:**
    - La IA generó inicialmente un `ObjectMapper` de Jackson 2 para los tests. Se corrigió manualmente a `JsonMapper` (Jackson 3) tras detectar el error en la ejecución.
    - La IA asumió que la clase `Pet` ya tenía los métodos `getOwner()` y `setOwner()`. Se tuvo que corregir la entidad para añadir la relación bidireccional.

## 5. Validación Humana del Resultado

**¿Cómo se validó el código generado por la IA?**
1.  **Compilación:** Cada bloque de código generado se compiló con `./mvnw clean compile` para detectar errores de sintaxis o dependencias.
2.  **Ejecución:** Se levantó la aplicación con `./mvnw spring-boot:run` y se verificó manualmente en Swagger UI que los endpoints respondían correctamente.
3.  **Pruebas Automatizadas:** Se ejecutaron los tests con `./mvnw test`. Los 9 tests (5 de integración, 4 unitarios) pasaron satisfactoriamente.
4.  **Revisión de Reglas de Negocio:** Se comparó la lógica implementada en los servicios con el código original (por ejemplo, la validación de que la fecha de nacimiento no sea futura, o que el teléfono tenga 10 dígitos).
5.  **Revisión de Código:** Se revisó manualmente cada archivo para asegurar que no hubiera dependencias circulares, código muerto o malas prácticas.

## 6. Limitaciones y Errores de IA Detectados

- **Desconocimiento de versiones específicas:** La IA asumió inicialmente que Spring Boot 3 era la versión estable para Java 21, pero se corrigió para usar Spring Boot 4, alineado con el proyecto original.
- **Problemas con Jackson 3:** La IA no detectó de inmediato que Spring Boot 4 migró a Jackson 3, lo que causó un error en los tests. Se resolvió tras la revisión del stack trace.
- **Sobrescritura de código:** En una ocasión, la IA sugirió reemplazar la entidad `Pet` sin tener en cuenta la relación con `Visit`, lo que causó un error de compilación. Se corrigió manualmente añadiendo la relación.

## 7. Conclusión

La IA fue una herramienta valiosa para acelerar el desarrollo y resolver dudas técnicas, pero **todas las decisiones fueron revisadas y validadas por el desarrollador**. El código final es el resultado de un proceso iterativo de generación, revisión, corrección y prueba, garantizando que se cumplen los requisitos de la prueba técnica.
