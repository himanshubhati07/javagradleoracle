# Office Attendance Management System

Spring Boot 3 / Java 17 backend for employee administration and daily attendance tracking, backed by Oracle Database. Attendance supports Present, Absent, and Half Day states, check-in/out times, automatically calculated working hours, corrections, deletion, and search by employee ID, employee name, or date.

## Architecture

- **Controllers:** REST/validation/OpenAPI boundary under `/api/v1`
- **Services:** transactional business rules and DTO mapping
- **Repositories:** Spring Data JPA persistence and cursor searches
- **Entities:** `Employee` (one-to-many) → `Attendance`; `AppUser` supports configured JWT login
- **Security:** HS256 JWTs expire after 30 minutes; endpoints are permit-all as configured for development
- **Errors:** consistent JSON responses for validation, not-found, conflict, and business-rule failures

## Run locally

Requirements: Java 17+, Oracle at the configured JDBC URL.

```bash
chmod +x gradlew start.sh
./gradlew compileJava -q
bash ./start.sh
```

Service: `http://localhost:40697`  
Swagger UI: `http://localhost:40697/docs`  
OpenAPI: `http://localhost:40697/api-docs`  
Health: `http://localhost:40697/actuator/health`

Windows: `start.bat`

Docker is intentionally not included because Docker infrastructure is disabled for this project.

## API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/v1/auth/register` | Register and issue JWT |
| POST | `/api/v1/auth/login` | Login and issue JWT |
| POST | `/api/v1/employees` | Add employee |
| GET | `/api/v1/employees/{id}` | Employee details |
| GET | `/api/v1/employees?name=&afterId=&limit=20` | List/search employees |
| PUT | `/api/v1/employees/{id}` | Update employee |
| DELETE | `/api/v1/employees/{id}` | Delete employee and attendance |
| POST | `/api/v1/attendance` | Mark attendance |
| GET | `/api/v1/attendance/{id}` | Get one attendance record |
| GET | `/api/v1/attendance` | List/search by `employeeId`, `employeeName`, `date`, cursor |
| GET | `/api/v1/attendance/employee/{employeeId}` | Attendance by employee |
| GET | `/api/v1/attendance/date/{yyyy-MM-dd}` | Attendance by date |
| PUT | `/api/v1/attendance/{id}` | Correct attendance |
| DELETE | `/api/v1/attendance/{id}` | Delete attendance |

Cursor pagination accepts `afterId` and clamps `limit` to 1–20.

## Environment variables

| Variable | Required | Description |
|---|---|---|
| `JWT_SECRET` | No | HS256 key; a safe startup fallback is provided |

Database values and port are in `application.properties` as required.

## Validation and working hours

Emails must be valid and unique. Required employee fields are validated. One attendance record is allowed per employee/date. Check-in and check-out must be supplied together, and check-out cannot precede check-in. Total working hours are calculated in decimal hours to two places.

## Tests

```bash
./gradlew test
./gradlew compileJava -q
./gradlew bootJar -q
```

Final direct-curl endpoint results are documented in `api_tests/test_results.md` and the generated platform report `api_test_report.xlsx`.
