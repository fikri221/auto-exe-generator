const form = document.getElementById('generatorForm');
const addAppBtn = document.getElementById('addAppBtn');
const appsContainer = document.getElementById('appsContainer');
const statusMessage = document.getElementById('status');
const submitBtn = form.querySelector('button[type="submit"]');

// Basic Windows .exe path validation
// (UX validation only — backend MUST revalidate)
function validatePath(path) {
    const exePathRegex = /^[A-Za-z]:\\[^:*?"<>|]+\.exe$/;
    return exePathRegex.test(path);
}

function setStatus(message, isError = false) {
    statusMessage.textContent = message;
    statusMessage.style.color = isError ? '#dc2626' : '#16a34a';
}

// Add new application input
addAppBtn.addEventListener('click', () => {
    const div = document.createElement('div');
    div.className = 'input-group';
    div.innerHTML = `
        <input type="text" name="apps" placeholder="C:\\Program Files\\App\\App.exe" required />
        <button type="button" class="delete-btn">Delete</button>
    `;

    div.querySelector('.delete-btn').addEventListener('click', () => {
        div.remove();
    });

    appsContainer.appendChild(div);
});

// Handle form submission
form.addEventListener('submit', async (e) => {
    e.preventDefault();

    setStatus('Generating file...');
    submitBtn.disabled = true;

    const fileName = document.getElementById('fileName').value.trim();
    const appInputs = [...document.querySelectorAll('input[name="apps"]')];

    if (!fileName) {
        setStatus('❌ File name is required', true);
        submitBtn.disabled = false;
        return;
    }

    if (appInputs.length === 0) {
        setStatus('❌ Please add at least one application', true);
        submitBtn.disabled = false;
        return;
    }

    const apps = [];
    appInputs.forEach(input => {
        const path = input.value.trim();

        if (!validatePath(path)) {
            setStatus(`❌ Invalid application path: ${path}`, true);
            submitBtn.disabled = false;
            return;
        }

        apps.push({ path });
    });

    // Schema-aligned payload
    const payload = {
        version: "1.0",
        profileName: fileName,
        apps
    };

    // Send payload to backend
    try {
        const response = await fetch('http://localhost:8080/api/generate', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) throw new Error('Failed to generate file');

        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');
        a.href = url;
        a.download = `${fileName}.bat`;
        a.style.display = 'none';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);

        setTimeout(() => URL.revokeObjectURL(url), 5000);
        setStatus('✅ File created successfully');
    } catch (err) {
        console.error(err);
        setStatus('❌ Failed to generate file', true);
    } finally {
        submitBtn.disabled = false;
    }
});
