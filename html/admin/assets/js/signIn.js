$("#signIn").click(function (event) {
    event.preventDefault();

    let email = $("#email").val();
    let password = $("#password").val();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/v1/auth/authenticate",
        contentType: "application/json",
        data: JSON.stringify({ email: email, password: password }),
        success: function (response) {
            console.log("fghjkl")
            console.log("Response: ", response);
            alert("Login Successful!");
            localStorage.setItem("token", response.data.token);// Store JWT token

            const decodedToken = jwt_decode(response.data.token);

            // Extract the role from the token
            const role = decodedToken.role;

            console.log("User role:", role);
            if (role === "vendor") {
               // window.location.href = "add-listing.html"; // Redirect after login
                window.location.href = "http://localhost:63342/wedding_planner_zip/html/vendor/add-listing.html";
            } else if(role === "bride") {
                window.location.href = "http://localhost:63342/wedding_planner_zip/html/dashboard.html";
            } else{
                window.location.href = "http://localhost:63342/wedding_planner_zip/html/admin/account-settings.html";
            }
        },
        error: function (xhr) {
            console.log("error")
           alert("Login Failed: " + xhr.responseJSON.message);
        }
    });
});

