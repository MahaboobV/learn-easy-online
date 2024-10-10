
// Call fetchCourses when the DOM is fully loaded
document.addEventListener('DOMContentLoaded', fetchCourses);
async function fetchCourses() {

    try{
        const response = await fetch("/api/course/fetch", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            // Handle HTTP errors
            const errorResponse = await response.json();
            handleError(response.status, errorResponse);
            return;
        }

        // Handle successful login
        const courses = await response.json();
        displayCourses(courses);

    }catch (error) {
        console.error('Error during login:', error);
        const messageDiv = document.getElementById("responseMessage");
        messageDiv.innerText = error;
        messageDiv.textContent = error;
        messageDiv.style.color = "red";
        messageDiv.style.display = "block"; // Show the message
    }
}

function displayCourses(courses) {
    const courseList = document.getElementById('courses-list');
    courseList.innerHTML = '';  // Clear any existing courses

    courses.forEach(course => {
        const row = document.createElement('tr');
        row.innerHTML =`
            <td>${course.title}</td>
            <td>${course.description}</td>
            <td>${course.instructor}</td>
            <td>${course.duration}</td>
            <td>${course.courseType}</td>
            <td>${course.price}</td>
            `;

        courseList.appendChild(row);
    })
}

function handleError(status, errorResponse) {
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