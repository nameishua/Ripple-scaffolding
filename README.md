# Ripple Scaffolding

Ripple is a Java enterprise scaffolding project based on Spring Boot, MyBatis-Plus, Shiro, Druid, Redis and Kafka.

This fork is being modernized with one clear goal: become a cleaner, safer and more extensible framework than a traditional admin scaffold.

## Current Baseline

- Java 8 compatible source level
- Spring Boot 2.1.x
- MyBatis-Plus and XML mapper support
- Shiro authentication
- Druid datasource monitoring
- Redis and Kafka integration points
- Unified JSON response wrapper for new APIs
- Global REST exception handling
- Actuator health/info endpoints
- Environment-variable based configuration
- Security dependency refresh and modular package boundaries

## Quick Start

1. Create a MySQL database named `ripple`.
2. Import `src/main/resources/sql/ripple.sql`.
3. Configure environment variables when your local defaults differ:

```powershell
$env:RIPPLE_DATASOURCE_URL="jdbc:mysql://127.0.0.1:3306/ripple?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai"
$env:RIPPLE_DATASOURCE_USERNAME="root"
$env:RIPPLE_DATASOURCE_PASSWORD="your-password"
```

4. Start the application:

```powershell
mvn spring-boot:run
```

5. Open:

- `http://localhost:8883/ripple/framework/info`
- `http://localhost:8883/ripple/actuator/health`

## Architecture

Module boundaries are documented in `docs/architecture/modules.md`. New code should prefer `top.coderak.modules.<module>` packages, while existing legacy packages remain stable during migration.

## Modernization Strategy

Ripple is evolving into a practical enterprise foundation with a focus on:

- secure-by-default configuration with no committed passwords
- plugin-ready backend modules
- code-generation-ready metadata
- clean API contracts
- progressive migration instead of risky rewrites
- cloud-native defaults for observability, health checks and externalized config

## Modernization Roadmap

### Phase 1: Foundation

- Replace hard-coded infrastructure addresses with environment variables.
- Add unified API response and global exception handling.
- Add framework metadata and health endpoints.
- Stabilize Maven dependency resolution.
- Repair README and broken source encoding issues.
- Refresh high-risk dependencies within the current Spring Boot compatibility range.
- Establish module package boundaries for incremental extraction.

### Phase 2: Framework Core

- Upgrade authentication from legacy Shiro-only flows to token-ready security.
- Add module boundaries for system, auth, generator, file, job and audit.
- Add Flyway database migrations.
- Add operation log and login log persistence.
- Add OpenAPI documentation.

### Phase 3: Productivity

- Add code generator for entity, mapper, service, controller and CRUD pages.
- Add plugin SPI and starter-style module packaging.
- Add multi-datasource support.
- Add background job management.
- Add server and cache monitoring endpoints.

### Phase 4: Product Experience

- Add a Vue 3 or React admin console.
- Add dashboard, user, role, menu, organization, dictionary and config management.
- Add deployment templates for Docker and CI.

## Maven

Recommended local Maven repository:

`D:/MavenRepository`

The current development machine is configured with Maven under:

`D:/DevTools/apache-maven-3.9.9`
