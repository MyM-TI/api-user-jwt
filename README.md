# Api Rest with Sprint Boot and JWT

![](https://img.shields.io/badge/build-success-brightgreen.svg)

# Stack

![](https://img.shields.io/badge/java_8-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/hibernate-✓-blue.svg)
![](https://img.shields.io/badge/mysql-✓-blue.svg)
![](https://img.shields.io/badge/h2database-✓-blue)
![](https://img.shields.io/badge/jwt-✓-blue.svg)
![](https://img.shields.io/badge/swagger_2-✓-blue.svg)



***


### Documentación de referencia
Para mayor referencia, considere las siguientes secciones:

* [Documentación oficial de Gradle](https://docs.gradle.org)
* [Guía de referencia del complemento Spring Boot Gradle](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/gradle-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Data JDBC](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-security)

### Guías
Las siguientes guías ilustran cómo usar algunas características concretamente:

* [Construyendo un servicio web RESTful](https://spring.io/guides/gs/rest-service/)
* [Sirviendo contenido web con Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Construyendo servicios REST con Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Acceso a datos con JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Usando Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Asegurar una aplicación web](https://spring.io/guides/gs/securing-web/)

### Enlaces Adicionales
Estas referencias adicionales también deberían ayudarte, ya que también me ayudaron:

* [Gradle Build Scans: información para la construcción de su proyecto](https://scans.gradle.com#gradle)
* [Spring Boot JWT](https://github.com/murraco/spring-boot-jwt)
* [demo-jwt](https://github.com/ajmorgang/demo-jwt)

# Estructura de los archivos

```
api-user-jwt/
 │
 ├── src/main/java/
 │   └── cl.mym.api.user.jwt
 │       ├── configure
 │       │   ├── JwtTokenFilter.java
 │       │   ├── JwtTokenFilterConfigurer.java
 │       │   ├── SimpleCORSFilter.java
 │       │   ├── SwaggerConfig.java
 │       │   └── WebSecurityConfig.java
 │       │
 │       ├── controller
 │       │   ├── AuthenticationController.java
 │       │   └── UserController.java
 │       │
 │       ├── entity
 │       │   ├── PhoneEntity.java
 │       │   └── UserEntity.java
 │       │
 │       ├── exception
 │       │   ├── ApiException.java
 │       │   ├── CustomAuthenticationEntryPoint.java
 │       │   ├── CustomExceptionHandler.java
 │       │   ├── RecordNotFoundException.java
 │       │   └── TokenException.java
 │       │
 │       ├── model
 │       │   ├── JwtResponse.java
 │       │   ├── LoginRequest.java
 │       │   ├── Phone.java
 │       │   ├── ResponseMessage.java
 │       │   └── User.java
 │       │
 │       ├── repository
 │       │   ├── PhoneRepository.java
 │       │   └── UserRepository.java
 │       │
 │       ├── security
 │       │   └── JwtTokenProvider.java
 │       │
 │       ├── service
 │       │   ├── UserService.java
 │       │   └── UserServiceImpl.java
 │       │
 │       └── ApiUserJwtApplication.java
 │
 ├── src/main/resources/
 │   ├── application.yml
 │   └── logback-spring.xml
 │
 ├── .gitignore
 ├── README.md
 ├── build.gradle
 ├── gradlew
 ├── gradlew.bat
 └── settings.gradle
```

# ¿Cómo usar este código?

1. Asegúrese de tener [Java 8] (https://www.java.com/download/) y [Gradle] (https://gradle.org/) instalado

2. Clona el repositorio
  
  ```
  $ git clone https://github.com/MyM-TI/api-user-jwt
  ```
  
3. Navega a la carpeta

  ```
  $ cd api-user-jwt
  ```

4. instala las dependencias

  ```
  $ gradle build
  ```

5. Ejecutar el proyecto

  ```
  $ gradle bootRun
  ```

6. Navegue a `http://localhost:8085/swagger-ui.html` en su navegador para verificar que todo funciona correctamente. Puede cambiar la raíz de contexto o el puerto predeterminado en el siguiente archivo `application.properties`

  ```
  server.servlet.context-path=/api-user-jwt/
  server.port= 8085
  ```

7. Realice una solicitud GET a `/api/users/` para verificar que no esta autenticado. Debería recibir una respuesta con un `401` con un mensaje `Acceso denegado` ya que aún no ha configurado su token JWT válido

  ```
  $ curl -X GET http://localhost:8085/api-user-jwt/api/users
  ```

8. Realice una solicitud POST a `/api/users/` para crear un usuario dentro de la api

  ```
  $ curl --location --request POST 'http://localhost:8085/api-user-jwt/api/users' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	    "email": "admin@admin.com",
	    "name": "admin",
	    "password": "Admin11",
	    "phones": [
	        {
	            "cityCode": 2,
	            "countryCode": 56,
	            "number": 999999999
	        }
	    ]
	}'
  ```
    
9. Realice una solicitud POST a `/api/login` con el usuario `admin@admin.com` y la clave `Admin11` creado en el paso anterior para obtener un token JWT válido

  ```
  $ curl --location --request POST 'http://localhost:8085/api-user-jwt/api/login' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	  "email": "admin@admin.com",
	  "password": "Admin11"
	}'
  ```


10. Agregue el JWT como parámetro de encabezado y vuelva a realizar la solicitud GET inicial a `/ api / users`

  ```
  $ curl -X GET http://localhost:8085/api-user-jwt/api/users -H 'Authorization: Bearer <JWT_TOKEN>'
  ```

10. Y eso es todo, felicidades! Debería obtener una respuesta similar a esta, lo que significa que ahora está autenticado

  ```javascript
	[
	    {
	        "id": "ab753bd8-e377-4c0d-a3ce-d40da0c7edce",
	        "name": "admin",
	        "email": "admin@admin.com",
	        "created": "2020-02-02T18:49:01.919+0000",
	        "modified": "2020-02-02T18:49:01.919+0000",
	        "lastLogin": "2020-02-02T18:49:01.903+0000",
	        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpYXQiOjE1ODA2NjkzNDEsImV4cCI6MTU4MDY3Mjk0MX0.rf2ciRwvfuDP-QFtI-rzgw_NSGo2LY1w8ufX87oDAws",
	        "isActive": true
	    }
	]
  ``` 

