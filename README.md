# 📚 Literalura
Aplicación de consola desarrollada en **Java + Spring Boot** que permite buscar libros desde la API de Gutendex, almacenarlos en una base de datos PostgreSQL y realizar consultas avanzadas sobre libros y autores.

Este proyecto fue desarrollado como parte del challenge **Literalura** de Alura Latam + Oracle Next Education.

---

## 🚀 Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **Hibernate**
- **Jackson**
- **Gutendex API**
- **Maven**

---

## 🧱 Arquitectura del proyecto

El proyecto sigue una arquitectura limpia y modular:
src/main/java/com/alura/literalura │ ├── model/           → Entidades JPA (Libro, Autor) ├── repository/      → Repositorios Spring Data JPA ├── service/         → Lógica de negocio (LibroService) ├── principal/       → Menú interactivo de consola └── config/          → Configuración de la aplicación

---

## 📌 Funcionalidades principales (obligatorias)

### ✔ Buscar libro por título
Consulta la API de Gutendex, obtiene el libro y lo guarda en la base de datos.

### ✔ Listar libros registrados
Muestra todos los libros almacenados en la base de datos.

### ✔ Listar autores registrados
Muestra todos los autores almacenados.

### ✔ Listar autores de los libros registrados
Relaciona libros con sus autores.

### ✔ Listar autores vivos en un año específico
Consulta mediante **derived queries** quiénes estaban vivos en un año dado.

### ✔ Mostrar cantidad de libros por idioma
Consulta estadística usando derived queries.

---

## ⭐ Funcionalidades opcionales implementadas

### ✔ Estadísticas de descargas
Usando `DoubleSummaryStatistics` para mostrar:
- Máximo
- Mínimo
- Promedio
- Cantidad total

### ✔ Top 10 libros más descargados
Consulta mediante derived query:


findTop10ByOrderByDescargasDesc()

### ✔ Buscar autor por nombre
Búsqueda parcial e insensible a mayúsculas:


findByNombreContainingIgnoreCase()

### ✔ Consultas adicionales por nacimiento/fallecimiento
- Autores nacidos antes de un año
- Autores fallecidos después de un año
- Autores nacidos entre dos años

---

## 🗄 Base de datos

El proyecto utiliza **PostgreSQL**.  
Ejemplo de configuración en `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true



▶️ Cómo ejecutar el proyecto
- Clona el repositorio:
git clone https://github.com/tuusuario/literalura.git


- Entra al proyecto:
cd literalura


- Ejecuta con Maven:
mvn spring-boot:run


- El menú aparecerá en consola.

🧪 Ejemplo del menú principal
==========================
LITERALURA - MENÚ PRINCIPAL
==========================
1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores de los libros registrados
5 - Listar autores vivos en un año específico
6 - Mostrar cantidad de libros por idioma
7 - Mostrar estadísticas de descargas
8 - Mostrar Top 10 libros más descargados
9 - Buscar autor por nombre
0 - Salir



👨‍💻 Autor
Proyecto desarrollado por Jorge (Korzo) como parte del programa
Oracle Next Education + Alura Latam.

🏁 Estado del proyecto
✔ Completado
✔ Incluye todas las funcionalidades opcionales
✔ Listo para portafolio