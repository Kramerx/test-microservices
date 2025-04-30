# test-microsites

## Instrucciones para ejecutar los proyectos

### Requisitos previos
1. Asegúrate de tener instalados los siguientes programas:
   - [Docker](https://www.docker.com/)
   - [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
   - [Maven](https://maven.apache.org/)
   - [MySQL](https://www.mysql.com/)

2. Configura las variables de entorno necesarias:
   - `JAVA_HOME` apuntando a la instalación de Java 17.
   - `PATH` debe incluir el binario de Maven.

3. Asegúrate de que los puertos `8080`, `8081`, `3307`, y `3308` estén disponibles.

### Pasos para ejecutar los microservicios

#### 1. Configurar la base de datos
- Crea una base de datos MySQL para cada microservicio:
  - Para el `client-microservice`: `BDD_CLIENT`
  - Para el `transactional-microservice`: `BDD_TRANSACTIONAL`
- Actualiza las credenciales de conexión en los archivos `application.properties`:
  - [`client-microservice/src/main/resources/application.properties`](client-microservice/src/main/resources/application.properties)
  - [`transactional-microservice/src/main/resources/application.properties`](transactional-microservice/src/main/resources/application.properties)

#### 2. Construir los proyectos
Ejecuta los siguientes comandos en la raíz de cada microservicio:

```bash
# Para client-microservice
cd client-microservice
./mvnw clean package

# Para transactional-microservice
cd transactional-microservice
./mvnw clean package
```

#### 3. Ejecutar los microservicios con Docker
##### Opción 1: Usar `docker-compose`
1. Asegúrate de que los proyectos estén construidos (ver paso 2).
2. Ejecuta el siguiente comando en la raíz del proyecto donde se encuentra el archivo `docker-compose.yml`:

```bash
docker-compose up --build
```

3. Esto levantará los siguientes servicios:
   - `client-microservice` en `http://localhost:8080`
   - `transactional-microservice` en `http://localhost:8081`
   - Bases de datos MySQL:
     - `mysql-client` en el puerto `3307`
     - `mysql-transactional` en el puerto `3308`

##### Opción 2: Ejecutar manualmente con Docker
1. Construye las imágenes Docker para cada microservicio:
   ```bash
   # Para client-microservice
   cd client-microservice
   docker build -t client-microservice .

   # Para transactional-microservice
   cd transactional-microservice
   docker build -t transactional-microservice .
   ```

2. Levanta los contenedores de las bases de datos MySQL:
   ```bash
   docker run -d --name mysql-client -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=BDD_CLIENT -p 3307:3306 mysql:latest
   docker run -d --name mysql-transactional -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=BDD_TRANSACTIONAL -p 3308:3306 mysql:latest
   ```

3. Ejecuta los contenedores de los microservicios:
   ```bash
   docker run -d --name client-microservice --link mysql-client -p 8080:8080 client-microservice
   docker run -d --name transactional-microservice --link mysql-transactional --link client-microservice -p 8081:8081 transactional-microservice
   ```

#### 4. Probar los endpoints
- Importa el archivo [`Project test endpoints.postman_collection.json`](Project%20test%20endpoints.postman_collection.json) en Postman para probar los endpoints disponibles.
- Los microservicios estarán disponibles en:
  - `http://localhost:8080` para el `client-microservice`
  - `http://localhost:8081` para el `transactional-microservice`

### Notas adicionales
- Si necesitas regenerar la base de datos, puedes usar el archivo [`BaseDatos.sql`](BaseDatos.sql).
- Consulta la documentación OpenAPI en [`openapi.yml`](openapi.yml) para más detalles sobre los endpoints.