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
               $("#reviewName").val(data.name || "");
               $("#reviewEmail").val(data.email || "");

            // Set form values for edit modal
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

    // Trigger modal
    $('.profile-img-edit').on('click', function (e) {
        e.preventDefault();
        $('#editProfileModal').modal('show');
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

    function getServiceIdFromUrl() {
        const params = new URLSearchParams(window.location.search);
        return params.get('id');
    }

    $("#reviewForm").submit(function (e) {
        console.log("feedback save eka click wenawa ")
        e.preventDefault();

        const serviceId= getServiceIdFromUrl();
        const title = $("#reviewTitle").val();
        const name = $("#reviewName").val();
        const email = $("#reviewEmail").val();
        const review = $("#reviewText").val();
        const rating = $("#ratingInput").val();


        let formData = new FormData();
        formData.append("serviceId", serviceId);
        formData.append("title", title);
        formData.append("name", name);
        formData.append("email", email);
        formData.append("review", review);
        formData.append("rating", rating);


        $.ajax({
            url: "http://localhost:8080/api/v1/feedback/save", // example endpoint
            type: "POST",
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {

                $("#reviewForm")[0].reset();
                $("#ratingInput").val(0);
                $(".star-rating i").removeClass("fas").addClass("far");
                alert(" Thank you for your feedback");
            },
            error: function (xhr) {
                console.error("Review submission failed:", xhr);
                alert("Error submitting review. Check if you're logged in as a vendor.");
            }
        });
    });

    $(".star-rating i").click(function () {
        const rating = $(this).data("value");
        $("#ratingInput").val(rating);
        $(".star-rating i").each(function (i) {
            $(this).toggleClass("fas", i < rating).toggleClass("far", i >= rating);
        });
    });

    function loadReviews() {
        console.log("review eke load eka enawa")
        const serviceId = getServiceIdFromUrl();

        $.ajax({
            url: `http://localhost:8080/api/v1/feedback/getbyId/${serviceId}`,
            type: "GET",
            success: function (reviews) {
                const reviewContainer = $("#reviewList");
                reviewContainer.empty();

                if (reviews.length === 0) {
                    reviewContainer.append("<p>No reviews yet.</p>");
                    return;
                }

                // Append the feedback form card LAST (will move it below all reviews)
                const feedbackForm = $("#feedbackFormCard").detach();

                let remaining = reviews.length;

                reviews.forEach(review => {
                    $.ajax({
                        url: `http://localhost:8080/api/v1/user/feedback/search/${review.email}`,
                        type: "GET",
                        success: function (user) {
                            const profileImage = user.photo
                                ? `data:image/jpeg;base64,${user.photo}`
                                : 'assets/img/profiles/default-avatar.jpg';

                            const reviewHtml = generateReviewHTML(review, profileImage);
                            $(reviewHtml).insertBefore("#feedbackFormCard");

                            if (--remaining === 0) {
                                reviewContainer.append(feedbackForm);
                            }
                        },
                        error: function () {
                            const reviewHtml = generateReviewHTML(review, 'assets/img/profiles/default-avatar.jpg');
                            $(reviewHtml).insertBefore("#feedbackFormCard");

                            if (--remaining === 0) {
                                reviewContainer.append(feedbackForm);
                            }
                        }
                    });
                });
            },
            error: function (xhr) {
                console.error("Error loading reviews:", xhr);
                $("#reviewList").html("<p>Failed to load reviews.</p>");
            }
        });
    }
    function generateReviewHTML(review, profileImage) {
        const profileImages = profileImage;

        return `
        <li class="review-box">
            <div class="review-profile">
                <div class="review-img">
                    <img src="${profileImages}" class="img-fluid" alt="img">
                </div>															
            </div>
            <div class="review-details">
                <h6>${review.name}</h6>
                <div class="rating">
                    <div class="rating-star">
                        ${renderStars(review.rating)}
                    </div>							        
                    <div><i class="fa-sharp fa-solid fa-calendar-days"></i> Just now</div>
                    <div>by: ${review.email}</div>
                </div>	
                <p>${review.review}</p>																	
                <div class="reply-box">
                    <p>Was This Review...? 
                        <a href="#" class="thumbsup"><i class="feather-thumbs-up"></i> Like </a>
                        <a href="#" class="thumbsdown"><i class="feather-thumbs-down"></i> Dislike </a>
                    </p>
                    <a href="#" class="replylink"><i class="feather-corner-up-left"></i> Reply</a>													
                </div>
            </div>							
        </li>
    `;
    }


    loadReviews();


});




