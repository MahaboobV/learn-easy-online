document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("registrationForm").addEventListener("submit", function(event) {
        event.preventDefault();

        let formData = {
            fullName: document.getElementById("fullName").value,
            email: document.getElementById("email").value,
            password: document.getElementById("password").value,
            phoneNumber: document.getElementById("phoneNumber").value,
            age: document.getElementById("age").value,
        };

         // Perform AJAX request using fetch API

         fetch("/api/user/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData),
         })
         .then(response => response.text())
         .then(data => {
           // Clear the form fields upon successful registration
            document.getElementById("registrationForm").reset();
            // Show success message
            document.getElementById("responseMessage").innerText = data;
            const messageDiv = document.getElementById("responseMessage");
            messageDiv.textContent = "Registration successful! Redirecting to login...";
            messageDiv.style.color = "green"; // Optional styling
            messageDiv.style.display = "block"; // Show the message

            // Redirect to login page after 2 seconds
            setTimeout(() => {
                window.location.href = "/user/login"; // Adjust the path as per your application
            }, 2000);
         })
         .catch(error => {
            console.error("Error:", error);
            document.getElementById("responseMessage").innerText = "Registration failed.";
         })
    })

 })