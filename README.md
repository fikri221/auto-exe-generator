# Secure App Launcher (Auto EXE) Generator

A **secure, declarative launcher generator** for Windows that creates `.bat`, `.ps1`, or `.exe` launchers **without executing user-provided code**.

This project allows users to define *what applications to open* using a **strict JSON configuration**, and generates launchers that safely start existing applications on the user’s machine.

---

## ✨ Why This Project Exists

Tools that convert scripts into executables are often abused by malware.

This project takes the opposite approach:

> Users describe intent, not code.
> 

There is **no arbitrary script execution**, no downloads, no persistence, and no privilege escalation.

This makes the generated launchers:

- Safer
- Easier to audit
- More trustworthy

---

## 🔐 Security-First Design

Security is the **primary design constraint**, not an afterthought.

### Core principles:

- ❌ No script uploads
- ❌ No code execution on the server
- ❌ No arbitrary PowerShell or CMD
- ❌ No network access from launchers
- ❌ No persistence (startup / registry / scheduled tasks)

### ✅ What *is* allowed:

- Declarative JSON configuration
- Starting existing `.exe` files
- Optional delays
- Optional arguments (sanitized)

See [`SECURITY.md`](https://github.com/fikri221/auto-exe-generator/blob/main/SECURITY.md) for full details.

---

## 🧠 How It Works (High Level)

```
UserConfiguration (JSON)
        ↓
Schema Validation (Strict)
        ↓
Script / Launcher Generation
        ↓
Download (BAT / PS1 / EXE)
```

- The backend **never executes** user input
- The EXE launcher is generated from a **static, audited wrapper**
- Only the embedded configuration changes

---

## 📄 Example Configuration

```json
{
"version":"1.0",
"profileName":"dev-work",
"runMode":"normal",
"delayBetweenAppsMs":2000,
"apps":[
		{
		"path":"C:\\Program Files\\Postman\\Postman.exe"
		},
		{
		"path":"C:\\Program Files\\DBeaver\\dbeaver.exe"
		}
	]
}
```

This configuration:

- Opens Postman
- Waits 2 seconds
- Opens DBeaver

Nothing else.

---

## 🧱 Supported Outputs

| Type | Description |
| --- | --- |
| `.bat` | Simple Windows batch launcher |
| `.ps1` | PowerShell launcher (restricted commands only) |
| `.exe` | Signed launcher built from a static wrapper |

> The EXE does not embed scripts — only validated configuration.
> 

---

## 🛠 Tech Stack

### Backend

- Java 17+
- Spring Boot
- JSON Schema validation
- Stateless API (no database)

### Frontend

- HTML
- CSS
- Vanilla JavaScript

No frameworks required.

---

## 🚫 What This Project Will Never Do

This project will **never**:

- Execute uploaded scripts
- Download or bundle files
- Modify registry or startup
- Elevate privileges
- Run background services
- Collect telemetry or analytics

If a feature requires any of the above, it is considered **out of scope**.

---

## 🗂 Project Structure (Simplified)

```
/backend
  ├─ controller
  ├─ service
  ├─ schema
  └─ generator

/frontend
  ├─ index.html
  ├─ styles.css
  └─ app.js

```

---

## 🚀 Getting Started (Local Development)

### Prerequisites

- Java 17+
- Maven or Gradle

### Run Backend

```bash
./mvnw spring-boot:run
```

### Frontend

Serve `frontend/` using any static server or open `index.html` directly.

---

## 🔍 Transparency & Trust

- No user data is stored
- No database required
- No tracking
- Deterministic generation
- Full source code available

You can inspect **exactly** what is generated before downloading.

---

## 🧪 CI & Reproducibility

This project uses CI to:

- Validate schemas
- Run tests
- Ensure deterministic builds
- Generate checksums

This helps users trust that distributed binaries match the source code.

---

## 📎 Documentation

- [`SECURITY.md`](https://github.com/fikri221/auto-exe-generator/blob/main/SECURITY.md) – Threat model & safeguards
- JSON Schema – Input validation rules
- Source code – Fully open

---

## 🤝 Contributing

Contributions are welcome, but must follow the security model.

PRs that introduce:

- Script execution
- Network access
- Persistence
- Privilege escalation

will be rejected.

---

## 📄 License

MIT License

---

## ⭐ Final Note

This project is intentionally **boring by design**.

Boring means:

- Predictable behavior
- Small attack surface
- High trust

That’s the goal.
