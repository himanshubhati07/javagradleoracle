# API Test Results

Final iteration: 1  
Test base URL: `http://localhost:40697`  
Result: **19/19 endpoints responded successfully; 0 failed; 0 skipped.**

> The supplied Oracle account returned ORA-01017 during live startup. Endpoint behavior was therefore verified against an isolated H2 database in Oracle compatibility mode by overriding datasource environment properties for the test process only. The committed `application.properties` retains the required Oracle URL, driver, username, and password verbatim.

| Method | Endpoint | Status | HTTP |
|---|---|---:|---:|
| POST | `/api/v1/auth/register` | PASSED | 201 |
| POST | `/api/v1/auth/login` | PASSED | 200 |
| POST | `/api/v1/employees` | PASSED | 201 |
| GET | `/api/v1/employees` | PASSED | 200 |
| GET | `/api/v1/employees?name=Jane&limit=20` | PASSED | 200 |
| GET | `/api/v1/employees/{id}` | PASSED | 200 |
| PUT | `/api/v1/employees/{id}` | PASSED | 200 |
| DELETE | `/api/v1/employees/{id}` | PASSED | 200 |
| POST | `/api/v1/attendance` | PASSED | 201 |
| GET | `/api/v1/attendance` | PASSED | 200 |
| GET | `/api/v1/attendance/{id}` | PASSED | 200 |
| GET | `/api/v1/attendance/employee/{employeeId}` | PASSED | 200 |
| GET | `/api/v1/attendance/date/{date}` | PASSED | 200 |
| GET | `/api/v1/attendance?employeeName=&employeeId=&date=` | PASSED | 200 |
| PUT | `/api/v1/attendance/{id}` | PASSED | 200 |
| DELETE | `/api/v1/attendance/{id}` | PASSED | 200 |
| GET | `/actuator/health` | PASSED | 200 |
| GET | `/api-docs` | PASSED | 200 |
| GET | `/docs` | PASSED | 302 |

Search responses were checked for the expected employee/attendance row. Attendance creation calculated `8.50` hours and the correction recalculated it to `4.50` hours.
