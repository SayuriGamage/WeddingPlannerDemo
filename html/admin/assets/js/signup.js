

$("#signUp").click(function (event) {
    event.preventDefault();

    let fullName = $("#name").val();
    let email = $("#email").val();
    let password = $("#password").val();
    let role = $("#role").val();

    let user = {
        email: email,
        password: password,
        name: fullName,
        role: role
    };

    if (role === "vendor") {
        localStorage.setItem("pendingUser", JSON.stringify(user));
        window.location.href = "http://localhost:63342/WeddingPlannerDemo/html/admin/payment-settings.html?_ijt=6ljled46fm7l3pqumjv852hkhe&_ij_reload=RELOAD_ON_SAVE";

    } else {
        registerUser(user);
    }
});

function registerUser(user) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/v1/user/register",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(user),
        success: function (response) {
            alert("Signup Successful!");
            window.location.href = "signin.html";
        },
        error: function (xhr) {
            let errorMessage = xhr.responseJSON ? xhr.responseJSON.message : "Something went wrong!";
            alert("Signup Failed: " + errorMessage);
        }
    });
}

