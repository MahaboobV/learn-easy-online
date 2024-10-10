document.getElementById('LoginForm').addEventListener('submit', function (event) {
    event.preventDefault();
    handleLogin(event);
});
async function handleLogin(event) {

    // get form data
    const userData = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };

    try{
        const response = await fetch("/api/auth/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (!response.ok) {
            // Handle HTTP errors
            const errorResponse = await response.json();
            handleLoginError(response.status, errorResponse);
            return;
        }

        // Handle successful login
        const successMessage = await response.text();
        const splitMessage = successMessage.split(":");
        const username = splitMessage[1]
        const messageDiv = document.getElementById("responseMessage");
        messageDiv.innerText = successMessage;
        messageDiv.textContent = successMessage;
        messageDiv.style.color = "green";
        messageDiv.style.display = "block"; // Show the message
        setTimeout(() => {
            window.location.href = `/user/home?userName=${encodeURIComponent(username)}`; // redirect to home page
        }, 2000);

    }catch (error) {
        console.error('Error during login:', error);
        const messageDiv = document.getElementById("responseMessage");
        messageDiv.innerText = error;
        messageDiv.textContent = error;
        messageDiv.style.color = "red";
        messageDiv.style.display = "block"; // Show the message
    }
}

function handleLoginError(status, errorResponse) {
    switch(status) {
        case 400:
            console.error('Validation errors:', errorResponse.errors);
            break;
        case 401:
            console.error('Login failed:', errorResponse.errors);
             break;
        case 409:
            console.error('Error:', errorResponse.errors);
             break;

        default:
            alert('An unexpected error occurred: ' + errorResponse.message);
            break;
    }
}