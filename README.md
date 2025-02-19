﻿# conexa-swapi-integration

## Descripción

Este proyecto consiste en una aplicación Java desarrollada con el objetivo de consumir la API de Star Wars ([SWAPI](https://www.swapi.tech)).

## Requisitos Previos

Para ejecutar este proyecto, necesitas tener instalado lo siguiente:

*   **Java Development Kit (JDK) 8:** Este proyecto fue desarrollado y probado con JDK 8. Se recomienda utilizar una versión de Oracle JDK 8 o una distribución OpenJDK compatible como Adoptium/Temurin. Puedes descargar una versión adecuada desde [https://adoptium.net/temurin/releases](https://adoptium.net/temurin/releases).

Asegúrate de configurar correctamente la variable de entorno `JAVA_HOME` para que apunte al directorio de instalación de tu JDK 8.

Puedes verificar tu versión de Java abriendo una terminal o línea de comandos y ejecutando el siguiente comando:

```
java -version
```
* **Apache Maven:** Se recomienda tener Maven instalado para gestionar las dependencias del proyecto y facilitar su construcción. Puedes descargarlo desde [https://maven.apache.org/download.cgi](https://www.google.com/url?sa=E&source=gmail&q=https://maven.apache.org/download.cgi).

Asegúrate de configurar correctamente la variable de entorno `M2_HOME` para que apunte al directorio de instalación de Maven y añadir `%M2_HOME%\bin` (en Windows) o `$M2_HOME/bin` (en Linux/macOS) a la variable de entorno `PATH`.

Puedes verificar tu versión de Maven abriendo una terminal o línea de comandos y ejecutando el siguiente comando:
```
mvn -v
```


### Principales funciones:

*   **Listado:** Permite ver los datos de *Personajes*, *Películas*, *Naves espaciales*, *Planetas*, *Especies* y *Vehículos*.
*   **Filtrado:** Permite filtrar los resultados por *ID* y/o *nombre* (y/o *modelo* en el caso de naves y vehículos), permitiendo búsquedas más específicas.
*   **Paginación:** Los listados de recursos se implementan con paginación para mejorar el rendimiento y la experiencia del usuario al manejar grandes cantidades de datos.
*   **Autenticación:** Implementa un sistema de autenticación básica HTTP para controlar el acceso a la información, garantizando la seguridad de la aplicación (configurable para desarrollo).
*   **Documentación API:** Se genera documentación interactiva de la API utilizando Swagger (OpenAPI 3), facilitando la exploración y el uso de los endpoints.

### Tecnologías utilizadas

*   **Java 8**
*   **Spring Boot**
*   **Spring Security**
*   **RestTemplate**
*   **Jackson**
*   **JUnit**
*   **Mockito**
*   **Swagger (OpenAPI 3)**

### Clonar Repositorio

1.  Dirígete a la carpeta donde quieres que se almacene el proyecto y haz clic derecho. Selecciona *Open in Terminal* (o *Abrir en Terminal*). También puedes abrir una terminal cmd y dirigirte con el comando `cd` hasta el directorio deseado.

2.  Pega el siguiente comando:

    ```bash
    git clone https://github.com/guido9821/conexa-swapi-integration.git
    ```

### Construir y ejecutar el proyecto

1.  Navega hasta el directorio del proyecto clonado usando la terminal:

    ```bash
    cd <ruta donde se encuentra el repositorio>
    ```

2.  Construye el proyecto usando Maven:

    ```bash
    mvn clean install
    ```

3.  Ejecuta la aplicación Spring Boot:

    ```bash
    mvn spring-boot:run
    ```

### Acceder a la API

Una vez que la aplicación se esté ejecutando, puedes acceder a los endpoints de la API.

*   **Ejemplo de listado paginado (Personas):**

    ```
    GET http://localhost:8080/api/people/?page=2&limit=10
    ```

*   **Ejemplo de búsqueda por ID (Planeta):**

    ```
    GET http://localhost:8080/api/planets/3
    ```

*   **Ejemplo de búsqueda por nombre (Nave Espacial):**

    ```
    GET http://localhost:8080/api/starships/searchByName/?name=Millennium Falcon
    ```
    *   **Ejemplo de búsqueda por modelo (Vehículo):**

    ```
     GET http://localhost:8080/api/vehicles/searchByModel/?model=Digger Crawler
    ``` 
*   **Ejemplo de búsqueda en heroku:**

  ```
   GET https://conexa-swapi-integration-01aa72428493.herokuapp.com/api/starships/2
  ```
*   **Documentación Swagger:** Accede a la documentación interactiva de la API en:

    ```
    http://localhost:8080/swagger-ui/index.html
    ```

    (Ajusta el puerto `8080` si tu aplicación se ejecuta en un puerto diferente).

### Autenticación

La API está protegida con autenticación básica HTTP. Para acceder a los endpoints protegidos, deberás proporcionar credenciales válidas.

Por razones de facilitar las pruebas, se han dejado los datos de las credeciales en el archivo application.properties.

Nombre de usuario: conexa

Contraseña: 9821

Para desarrollo, la autenticación puede ser desactivada en la configuración de Spring Security (revisar la clase de configuración de seguridad).

### Pruebas

El proyecto incluye pruebas unitarias utilizando JUnit y Mockito. Para ejecutar las pruebas, utiliza el siguiente comando Maven:

```bash
mvn test
