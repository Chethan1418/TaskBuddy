# TaskBuddy

A Spring Boot task manager with email/password accounts and browser-only guest mode.

## Run locally
1. Open this folder in IntelliJ IDEA as a Maven project.
2. Use Java 21 or newer.
3. Run `src/main/java/TaskBuddy/TaskbuddyApplication.java`.
4. Visit http://localhost:8080.

## Current build notes
- Google OAuth is intentionally disabled in this build until valid Google OAuth credentials are configured.
- Guest tasks are stored in the current browser's local storage and do not sync between devices.
- Account tasks use the configured database. The default local database is H2.
- The refreshed UI includes responsive styling and a light/dark theme toggle.

Do not commit local database files, credentials, or environment-specific secrets to a public repository.
