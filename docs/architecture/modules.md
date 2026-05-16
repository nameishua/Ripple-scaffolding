# Module Boundaries

Ripple keeps the first modernization step inside one Maven artifact, while separating code by module packages. This keeps the project easy to compile during migration and creates a clean path to future Maven multi-module extraction.

## Package Layout

- `top.coderak.core`: shared framework primitives, configuration, API response models, base mapper support and utilities.
- `top.coderak.modules.auth`: authentication and authorization endpoints.
- `top.coderak.modules.system`: system metadata, health-facing endpoints and future admin infrastructure.
- `top.coderak.web`: legacy controllers kept in place until they are migrated into feature modules.
- `top.coderak.service`, `top.coderak.mapper`, `top.coderak.entity`: legacy service/data packages kept stable for compatibility.

## Migration Rules

- New endpoints should be created under `top.coderak.modules.<module>.controller`.
- New shared infrastructure should live under `top.coderak.core`.
- Legacy controllers should move module by module, with routes preserved.
- Database mapper XML paths stay stable until MyBatis-Plus is upgraded.
- Security filters should expose public infrastructure endpoints explicitly and protect business endpoints by default.

## Candidate Modules

- `auth`: login, token lifecycle, permission policy and password strategy.
- `system`: framework info, configuration, dictionary, menu and organization.
- `generator`: metadata scanning and CRUD code generation.
- `audit`: operation logs and login logs.
- `job`: scheduled task registration and runtime control.
- `file`: local and object-storage file services.
