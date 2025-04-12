// Common variables
let serverOTP;
let countdownInterval;

// Document ready function
$(document).ready(function() {
    // Initialize any page-specific functionality
    initializePage();

    // Check login status if needed
    checkLoginStatus();

    // Add smooth scrolling to all links
    $(".fixed-side-navbar a, .primary-button a").on('click', function(event) {
        if (this.hash !== "") {
            event.preventDefault();
            var hash = this.hash;
            $('html, body').animate({
                scrollTop: $(hash).offset().top
            }, 800, function(){
                window.location.hash = hash;
            });
        }
    });
});

function initializePage() {
    // Set up event listeners based on current page
    if (document.getElementById('signIn')) {
        setupLoginPage();
    }

    if (document.getElementById('submitotp')) {
        setupForgotLoginPage();
    }

    if (document.getElementById('newsubmit')) {
        setupNewPasswordPage();
    }

    if (document.getElementById('forgot-link')) {
        // This is the forget-password.html page
        // No special setup needed as it just links to fogotLogin.html
    }
}


    // Password visibility toggle
    $(".toggle-password").on("click", function() {
        $(this).toggleClass("fa-eye fa-eye-slash");
        var input = $(this).siblings("input");
        if (input.attr("type") === "password") {
            input.attr("type", "text");
        } else {
            input.attr("type", "password");
        }
    });


// Forgot Login Page Functionality (OTP)
function setupForgotLoginPage() {
    $("#submitotp").on("click", function(event) {
        event.preventDefault();
        var email = $("#email").val();
        var otp = $("#otp").val();

        if (!email) {
            alert("Please enter your email address.");
            return;
        }

        if (!otp) {
            alert("Please enter the OTP.");
            return;
        }

        // In a real application, you would verify the OTP with the server
        // For now, we'll simulate a successful verification
        verifyOTPWithServer(email, otp, function(success) {
            if (success) {
                window.location.href = "newPassword.html?email=" + encodeURIComponent(email);
            } else {
                alert("Invalid OTP. Please try again.");
            }
        });
    });

    // Password visibility toggle
    $(".toggle-password").on("click", function() {
        $(this).toggleClass("fa-eye fa-eye-slash");
        var input = $(this).siblings("input");
        if (input.attr("type") === "password") {
            input.attr("type", "text");
        } else {
            input.attr("type", "password");
        }
    });
}

// New Password Page Functionality
function setupNewPasswordPage() {
    $("#newsubmit").on("click", function(event) {
        event.preventDefault();
        var email = getParameterByName('email') || $("#email").val();
        var newPassword = $("#newpassword").val();

        if (!newPassword) {
            alert("Please enter a new password.");
            return;
        }

        resetPassword(email, newPassword);
    });

    // Password visibility toggle
    $(".toggle-password").on("click", function() {
        $(this).toggleClass("fa-eye fa-eye-slash");
        var input = $(this).siblings("input");
        if (input.attr("type") === "password") {
            input.attr("type", "text");
        } else {
            input.attr("type", "password");
        }
    });
}

// Helper function to get URL parameters
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

// OTP Functions
function sendOTP(email) {
    if (!email) {
        alert("Please enter your email address.");
        return;
    }

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/v1/password/sentOTP",
        contentType: "application/json",
        data: JSON.stringify({email: email}),
        dataType: "json",
        success: function(response) {
            serverOTP = response;
            alert("OTP has been sent to " + email + ". Please check your inbox.");
            startCountdown();
        },
        error: function(xhr, status, error) {
            console.log(error);
            alert('Failed to send OTP. Please try again.');
        }
    });
}

function resendOTP(email) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/v1/password/sentOTP",
        contentType: "application/json",
        data: JSON.stringify({email: email}),
        dataType: "json",
        success: function(response) {
            serverOTP = response;
            alert("New OTP has been sent to " + email + ".");
            startCountdown();
        },
        error: function(xhr, status, error) {
            console.log(error);
            alert('Failed to resend OTP. Please try again.');
        }
    });
}

function verifyOTPWithServer(email, otp, callback) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/v1/password/verifyOTP",
        contentType: "application/json",
        data: JSON.stringify({email: email, otp: otp}),
        dataType: "json",
        success: function(response) {
            callback(response.success);
        },
        error: function(xhr, status, error) {
            console.log(error);
            callback(false);
        }
    });
}

function startCountdown() {
    let countdownTime = 30;
    clearInterval(countdownInterval);

    $("#countdown").text(countdownTime + "s");
    $("#resend").addClass("disabled");

    countdownInterval = setInterval(function() {
        countdownTime--;
        $("#countdown").text(countdownTime + "s");

        if (countdownTime <= 0) {
            clearInterval(countdownInterval);
            $("#resend").removeClass("disabled");
            $("#countdown").text('');
        }
    }, 1000);
}

// Password Reset Function
function resetPassword(email, newPassword) {
    var data = {
        email: email,
        password: newPassword
    };

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/v1/password/resetPassword",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        success: function(response) {
            alert("Password has been reset successfully. You can now login with your new password.");
            window.location.href = "signin.html";
        },
        error: function(xhr, status, error) {
            console.log(error);
            alert('Failed to reset password. Please try again.');
        }
    });
}

// Login Status Check
function checkLoginStatus() {
    var token = localStorage.getItem("token");
    if (token) {
        $(".join-btn").attr("disabled", true);
        $(".join-btn").css("cursor", "not-allowed");
        $("#loginIcon").css("display", "none");
    }
}

// Modal Functions
function openModal() {
    $("#loginModal").css("display", "block");
}

function closeModal() {
    $("#loginModal").css("display", "none");
}

function openRegModal() {
    $("#registrationModal").css("display", "block");
}

function closeRegModal() {
    $("#registrationModal").css("display", "none");
}

function closePopup() {
    $("#forget").css("display", "none");
}

function openForgetModal() {
    closeModal();
    $("#forget").css("display", "block");
}

// Click outside modal to close
$(window).on("click", function(event) {
    if ($(event.target).is("#loginModal")) {
        closeModal();
    }
    if ($(event.target).is("#registrationModal")) {
        closeRegModal();
    }
    if ($(event.target).is("#forget")) {
        closePopup();
    }
});