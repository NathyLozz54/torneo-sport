# Torneo Sports - Gestión de Torneos y Ventas

Proyecto desarrollado en Java con Spring Boot que permite la gestión de torneos deportivos y ventas de tickets. Incluye monitoreo con Prometheus y Grafana.

---

## Tecnologías

- Java 17
- Spring Boot (WebFlux, Actuator, Security)
- Reactor (programación reactiva)
- Docker (Prometheus, Grafana)
- Gradle 
- JUnit 5 + Mockito para pruebas unitarias

---

## Estructura del proyecto

- `domain` - Modelo de dominio, casos de uso y repositorios (interfaces)
- `infrastructure` - Adaptadores, implementaciones de repositorios, puntos de entrada (API REST)
- `application` - Configuraciones y orquestación de casos de uso
- `tests` - Pruebas unitarias y de integración

---

## Requisitos

- Java 17 instalado
- Docker instalado y corriendo (para Prometheus y Grafana)
- Gradle 

---

## Configuración y ejecución

### 1. Backend Spring Boot

Ejecuta la aplicación localmente:

```bash
./gradlew bootRun
```
La API estará disponible en: http://localhost:8080

### 2. Docker - Prometheus y Grafana
Corre los contenedores Prometheus y Grafana:

```bash
docker run -d --name prometheus -p 9090:9090 -v /ruta/local/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
docker run -d --name grafana -p 3000:3000 grafana/grafana
```
Accede a:

Prometheus: http://localhost:9090

Grafana: http://localhost:3000 (user: admin, pass: admin)
Endpoints principales
/actuator/prometheus - Métricas para Prometheus

#### API REST para:
- torneos http://localhost:8080/torneos
- ventas http://localhost:8080/ventas 
- etapas de ventas http://localhost:8080/torneos/{ventas}/etapas
- Authorización http://localhost:8080/auth/login


#### Pruebas
Para ejecutar pruebas unitarias:
```bash
./gradlew test
```

#### Monitoreo
Se usa Prometheus para scraping de métricas expuestas en /actuator/prometheus y Grafana para visualización.

#### Contacto
Desarrollado por Nathaly Lozano
