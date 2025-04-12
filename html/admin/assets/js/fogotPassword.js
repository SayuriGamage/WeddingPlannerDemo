// forgot-password.js
document.getElementById('forgot-submit').addEventListener('click', function (e) {
    e.preventDefault(); // Prevent default form submission

    const email = document.getElementById('forgot-email').value.trim();
    if (!email) {
        alert("Please enter an email.");
        return;
    }

    fetch('http://localhost:8080/api/v1/auth/forgot-password?email=' + email, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw err; });
            }
            return response.json();
        })
        .then(data => {
            alert(data); // Show the response message
            if (data === "Reset link sent to email") {
                // Store email in localStorage for later use
                localStorage.setItem('resetEmail', email);
                // Redirect to OTP verification page
                window.location.href = 'otp-verification.html';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message || "Failed to send OTP");
        });
});

// otp-verification.js
document.getElementById('submit-otp').addEventListener('click', function(e) {
    e.preventDefault();

    const otp = document.getElementById('otp').value.trim();
    const email = localStorage.getItem('resetEmail');

    if (!otp) {
        alert("Please enter OTP.");
        return;
    }

    // Verify OTP with backend
    fetch(`http://localhost:8080/api/v1/auth/verify-otp?email=${email}&otp=${otp}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw err; });
            }
            return response.json();
        })
        .then(data => {
            if (data === "OTP verified successfully") {
                // Store OTP in localStorage for password reset
                localStorage.setItem('otpToken', otp);
                window.location.href = 'new-password.html';
            } else {
                alert("Invalid OTP");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message || "OTP verification failed");
        });
});

// new-password.js
document.getElementById('new-password-submit').addEventListener('click', function(e) {
    e.preventDefault();

    const newPassword = document.getElementById('new-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const email = localStorage.getItem('resetEmail');
    const otpToken = localStorage.getItem('otpToken');

    if (!newPassword || !confirmPassword) {
        alert("Please fill all fields");
        return;
    }

    if (newPassword !== confirmPassword) {
        alert("Passwords don't match");
        return;
    }

    if (newPassword.length < 8) {
        alert("Password must be at least 8 characters");
        return;
    }

    fetch(`http://localhost:8080/api/v1/auth/reset-password?token=${otpToken}&newPassword=${newPassword}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw err; });
            }
            return response.json();
        })
        .then(data => {
            alert(data);
            if (data === "Password reset successful") {
                // Clean up stored data
                localStorage.removeItem('resetEmail');
                localStorage.removeItem('otpToken');
                window.location.href = 'login.html';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message || "Password reset failed");
        });
});