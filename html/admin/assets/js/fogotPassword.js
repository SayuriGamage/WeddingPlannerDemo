// forgot-password.js


document.getElementById('forgot-submit').addEventListener('click', function (e) {
    e.preventDefault();

    const email = document.getElementById('forgot-email').value.trim();
    if (!email) {
        alert("Please enter an email.");
        return;
    }

    fetch(`http://localhost:8080/api/v1/auth/forgot-password?email=${email}`, {
        method: 'POST'
        // No need for headers or body since we send email as query param
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(err => { throw new Error(err); });
            }
            return response.text();
        })
        .then(data => {
            alert(data);

            if (data === "Reset link sent to email") {
                localStorage.setItem('resetEmail', email);
                console.log("meka methanata wenkn eno reset ek")
                window.location.href = 'fogotLogin.html';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message || "Failed to send OTP");
        });
});


// otp-verification.js

    $("#submit-otp").click(function (e) {
        e.preventDefault();
        console.log("OTP Submit clicked!");

        const otp = $("#otp").val().trim();
        const email = localStorage.getItem("resetEmail");

        if (!otp) {
            alert("Please enter OTP.");
            return;
        }

        fetch(`http://localhost:8080/api/v1/auth/verify-otp?email=${email}&otp=${otp}`, {
            method: 'POST'
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(err => { throw new Error(err); });
                }
                return response.text();
            })
            .then(data => {
                if (data === "OTP verified successfully") {
                    localStorage.setItem('otpToken', otp);
                    window.location.href = 'newPassword.html';
                } else {
                    alert(data);
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
        method: 'POST'

    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(err => { throw new Error(err); });
            }
            return response.text();
        })
        .then(data => {
            alert(data);
            if (data === "Password reset successful") {
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
