# SkillHire Backend

Spring Boot 3.5 + Java 25 + MySQL backend built to match your `skill-hire` React
frontend's data model (`Category`, `Worker`, `Booking`) field-for-field.

## 1. Create the database

Open MySQL and run:

```sql
CREATE DATABASE skillhire_db;
```

(Or skip this — `createDatabaseIfNotExist=true` in the connection URL will create
it automatically the first time the app starts.)

## 2. Configure your credentials

Copy the example file and fill in your real MySQL password:

```
copy src\main\resources\application-example.properties src\main\resources\application-local.properties
```

Edit `application-local.properties` with your MySQL username/password. This file
is gitignored, so your password never gets committed.

Spring Boot doesn't read `application-local.properties` as env vars automatically —
the simplest route on Windows is to just set env vars in your terminal before running:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
```

Then run the app (below) in that same terminal session.

## 3. Run the backend

From the `skillhire-backend` folder, in VS Code's integrated terminal:

```powershell
mvn spring-boot:run
```

First run will:
- Create the `skillhire_db` database if it doesn't exist
- Auto-create all tables from the JPA entities (`ddl-auto=update`)
- Seed the 21 service categories (electrician, plumber, cleaner, etc.) to match
  `src/lib/mock-data.ts` exactly

Confirm it's up: open `http://localhost:8080/api/health` — you should see
`{"status":"UP","service":"skillhire-backend"}`.

## 4. API endpoints

| Method | Endpoint                                  | Purpose                              |
|--------|--------------------------------------------|---------------------------------------|
| POST   | `/api/auth/register`                       | Create account (customer/worker/admin)|
| POST   | `/api/auth/login`                          | Login                                 |
| GET    | `/api/categories`                          | List all categories + live worker counts |
| GET    | `/api/workers?category=&location=&query=`  | Search/browse workers                 |
| GET    | `/api/workers/{id}`                        | Single worker profile                 |
| PUT    | `/api/workers/profile/{userId}`            | Worker creates/edits their profile    |
| POST   | `/api/bookings`                            | Create a booking                      |
| GET    | `/api/bookings/customer/{customerId}`      | A customer's bookings                 |
| GET    | `/api/bookings/worker/{workerId}`          | A worker's bookings                   |
| PATCH  | `/api/bookings/{id}/status`                | Update booking status                 |
| POST   | `/api/messages`                            | Send a message                        |
| GET    | `/api/messages/conversation?userA=&userB=` | Chat thread between two users         |

All response JSON field names match your frontend's TypeScript interfaces in
`src/lib/mock-data.ts` and `src/lib/auth.ts` exactly (e.g. `reviewsCount`,
`completedJobs`, `initials`, `color`) — the intent is that swapping mock data for
real fetch calls requires no reshaping on the frontend side.

## 5. Connect the React frontend

Your frontend currently reads from `src/lib/mock-data.ts` and `src/lib/auth.ts`
(which use `localStorage`). To wire it to this real backend:

1. Add a `.env` in the frontend root (Vite picks up `VITE_` prefixed vars):
   ```
   VITE_API_URL=http://localhost:8080/api
   ```
2. Replace the body of `login()`/`register()` in `src/lib/auth.ts` with a `fetch`
   call to `${import.meta.env.VITE_API_URL}/auth/login` (or `/register`), and
   store the returned `AuthResponse` JSON in place of the current mock user object.
3. In pages that currently import from `mock-data.ts` (search, categories, worker
   profile, dashboards), replace the mock array reads with `fetch` calls to the
   matching endpoint above. TanStack Query (already in your dependencies) is a
   good fit — wrap each endpoint in a `useQuery` hook.

I can write that `auth.ts` rewrite and a typed `api-client.ts` for you next if
you want — just say the word once the backend is running locally and you've
confirmed `/api/health` responds.

## 6. What's intentionally not built yet

- **Spring Security + JWT** — you flagged this as a later phase. Right now,
  `/api/**` endpoints are open (no token required), and `login`/`register` just
  return the user record. This is fine for local development but is NOT
  production-safe — don't deploy this publicly as-is.
- **Role-based authorization** — e.g. nothing currently stops a customer account
  from calling the worker-profile-update endpoint. Add this alongside Spring
  Security.
- **Database migrations** — using `ddl-auto=update` for now. Switch to Flyway or
  Liquibase before this touches production data.
