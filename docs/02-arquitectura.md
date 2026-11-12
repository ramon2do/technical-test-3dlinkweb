# Arquitectura de la Solución Modernizada

## 1. Decisión Arquitectónica: Monolito Modular
Se ha optado por un **Monolito Modular** en lugar de microservicios por las siguientes razones:
*   **Restricción de tiempo:** El plazo de 1 día no permite la sobrecarga operativa de gestionar múltiples servicios, comunicación distribuida y transacciones.
*   **Consistencia transaccional:** Un monolito modular garantiza la integridad referencial entre Owners, Pets y Visits sin necesidad de patrones como Saga.
*   **Delimitación clara:** Se mantienen límites estrictos entre módulos, lo que facilitaría una futura extracción a microservicios si fuera necesario.

## 2. Estructura de Módulos
El proyecto se divide en dos módulos funcionales claros:
*   **Owner Module:** Gestión de propietarios y sus mascotas (incluye PetTypes y Visits).
*   **Vet Module:** Consulta de veterinarios y especialidades.

## 3. Estructura Interna por Módulo (Arquitectura Hexagonal ligera)
Dentro de cada módulo, se aplica la separación sugerida:
*   `api/`: Controladores REST, DTOs de Request/Response y manejo de errores HTTP.
*   `application/`: Casos de uso (Servicios) y coordinación de operaciones.
*   `domain/`: Entidades JPA y reglas de negocio.
*   `infrastructure/`: Repositorios Spring Data y configuración de persistencia.

## 4. Contratos y Manejo de Errores
*   **DTOs:** No se exponen entidades JPA. Se usan DTOs (records) con anotaciones de validación.
*   **Errores:** Se utiliza `@RestControllerAdvice` con `ProblemDetail` (estándar de Spring 6) para devolver errores HTTP estandarizados (400, 404, 500).
*   **Documentación:** OpenAPI/Swagger generado automáticamente con `springdoc-openapi`.

## 5. Justificación de Agrupación de Entidades (Domain-Driven Design)

Se ha decidido agrupar las entidades `Pet`, `PetType` y `Visit` dentro del módulo `owner` (y no en paquetes separados) por las siguientes razones de diseño:

### 5.1. Raíz de Agregación (Aggregate Root)
En el dominio de PetClinic, el `Owner` (Propietario) es la **raíz de agregación** según los principios de Domain-Driven Design (DDD). Un propietario posee mascotas, y las visitas son eventos asociados a esas mascotas. No puede existir una mascota sin un dueño, ni una visita sin una mascota. Mantener estas entidades juntas garantiza la **consistencia transaccional** y evita dependencias circulares entre módulos.

### 5.2. Cohesión y Acoplamiento
Los servicios y controladores de `Visit` necesitan acceder a las entidades `Pet` y `Owner` para validar la existencia de la mascota antes de registrar una visita. Al estar en el mismo paquete (`owner`), se reduce el acoplamiento entre módulos y se facilita la navegación del código. Si se hubiera creado un módulo `visit` separado, este tendría que importar constantemente clases del módulo `owner`, generando una dependencia fuerte que rompería la modularidad.

### 5.3. Bounded Context (Contexto Delimitado)
Siguiendo el concepto de **Bounded Context** de DDD, todo lo relacionado con la atención de la mascota (incluyendo sus visitas) pertenece al mismo contexto del negocio: la gestión de propietarios y sus animales. El módulo `vet` sí se mantiene separado porque representa un contexto distinto (el personal médico y sus especialidades), que no depende directamente de las mascotas para existir.

### 5.4. Simplicidad para el Alcance de la Prueba
Para el alcance de esta prueba técnica (1 día), un monolito modular con un módulo `owner` que agrupa las entidades relacionadas es más mantenible y fácil de explicar que crear un módulo `visit` separado que dependa constantemente de `owner`. Esta decisión es **consciente y justificada**, no un descuido.

## 6. Nota sobre Entidades JPA en la Capa de Dominio

Se ha optado por mantener las entidades JPA (`Owner`, `Pet`, `Visit`, etc.) dentro del paquete `domain` por pragmatismo y simplicidad.

Si bien una arquitectura hexagonal "pura" sugiere separar el modelo de dominio (POJOs) del modelo de persistencia (entidades JPA) para desacoplar el negocio de la base de datos, esta separación implica una duplicación significativa de código y la creación de mappers bidireccionales entre ambas representaciones.

Dado el alcance de la prueba (1 día) y el tamaño del proyecto, se ha priorizado la **simplicidad y la mantenibilidad** sobre el purismo arquitectónico. La estructura actual sigue siendo un **Monolito Modular** claro, donde cada módulo tiene sus capas bien definidas (`api`, `application`, `domain`, `infrastructure`), y el uso de anotaciones JPA en el dominio es una concesión consciente y justificada para acelerar el desarrollo sin sacrificar la claridad del código.

En un escenario de producción a mayor escala, esta separación podría implementarse refactorizando las entidades a POJOs puros en `domain` y creando entidades de persistencia en `infrastructure`, pero ello excede el alcance y el tiempo disponible para esta prueba.

## 7. Dependencias entre Módulos
*   El módulo `owner` es el núcleo del sistema. Contiene las entidades `Owner`, `Pet`, `PetType` y `Visit`.
*   El módulo `vet` es independiente y no tiene dependencias con `owner` (solo comparte las clases base de `shared/domain`).
*   El módulo `shared` contiene clases transversales (`BaseEntity`, `Person`, `NamedEntity`) y el manejo global de excepciones, utilizadas por todos los módulos.
