$(document).ready(function () {
    const token = localStorage.getItem("token");
    let currentEmail = "";

    if (!token) {
        alert("Please log in first.");
        window.location.href = "login.html";
        return;
    }

    // Load the user profile and store the email
    $.ajax({
        url: "http://localhost:8080/api/v1/profile/profile",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + token
        },
        success: function (data) {
            console.log("✅ Profile loaded:", data);
            $("#fullName").val(data.name || "");
            $("#emailAddress").val(data.email || "");
            $("#phoneNumber").val(data.contact || "");
            $("#headName").text(data.name || "User");
            const base64Image = data.photo ? `data:image/png;base64,${data.photo}` : "assets/img/profile-img.jpg";

            $("#headImage").attr("src", base64Image || "assets/img/profile-img.jpg");
            $("#profileImage").attr("src", base64Image || "assets/img/profile-img.jpg");

            $("#editFullName").val(data.name || "");
            $("#editEmailAddress").val(data.email || "");
            $("#editPhoneNumber").val(data.phone || "");


            currentEmail = data.email;
        },
        error: function (err) {
            console.error("❌ Failed to load profile:", err);
            if (err.status === 401 || err.status === 403) {
                alert("Session expired. Please log in again.");
                localStorage.removeItem("token");
                window.location.href = "login.html";
            } else {
                alert("Failed to load profile.");
            }
        }
    });


    document.addEventListener("DOMContentLoaded", function () {
        document.querySelector(".btn-submit").addEventListener("click", function () {
            const modal = new bootstrap.Modal(document.getElementById('editProfileModal'));
            modal.show();
        });
    });

    $('#editProfile').on('click', function (e) {
        e.preventDefault();

        const name = $("#editFullName").val();
        const email = $("#editEmailAddress").val();
        const contact = $("#editPhoneNumber").val();
        const photo = $("#editProfileImage")[0].files[0];

        const formData = new FormData();
        formData.append("name", name);
        formData.append("email", email);
        formData.append("contact", contact);
        if (photo) {
            formData.append("photo", photo);
        }


        $.ajax({
            url: `http://localhost:8080/api/v1/profile/update/${currentEmail}`,
            type: "PUT",
            headers: {
                "Authorization": "Bearer " + token
            },
            data: formData,
            processData: false,
            contentType: false,
            success: function (updatedUser) {
                alert("Profile updated successfully!");
            },

        });
    });
});

