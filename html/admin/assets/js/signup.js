$("#signUp").click(function (event) {
    event.preventDefault();

    let fullName = $("#name").val()
    let email = $("#email").val();
    let password = $("#password").val();
    let role = $("#role").val();
    console.log("meka"+ email,password,fullName,role)

    let user = {
        "email": email,
        "password": password,
        "name": fullName,
        "role": role
    }
    console.log("meka"+ email,password,fullName,role)

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/v1/user/register", // Your register API endpoint
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(user),
        success: function (response) {
            console.log("Response: ", response);
            console.log("code"+response.code)

            if (response.code == "201") { // Check if status is "Created"
                alert("Signup Successful!");
                window.location.href = "signin.html";
            } else {
                alert("Signup Failed: " + response.message);
            }
        },
        error: function (xhr) {
            let errorMessage = xhr.responseJSON ? xhr.responseJSON.message : "Something went wrong!";
            alert("Signup Failed: " + errorMessage);
        }
    });

});