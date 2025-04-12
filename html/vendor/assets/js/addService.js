$(document).ready(function () {
    const token = localStorage.getItem("token");
    let currentEmail="";

    if(!token){
        alert("please log in first");
        window.location.href="login.html";
        return
    }

    // Save Service
    $("#btn_save_service").click((e) => {
        e.preventDefault();

        // Collect form data
        const title = $("#title").val();
        const description = $("#description").val();
        const category = $('input[name="category"]:checked').map(function () {
            return this.value;
        }).get();
        const tagline = $("#tagline").val();
        const basePrice = $("#basePrice").val();
        const address = $("#address").val();
        const mapAddress = $("#mapAddress").val();
        const email = $("#email").val();
        const website = $("#website").val();
        const phone = $("#phone").val();
        const facebook = $("#facebook").val();
        const twitter = $("#twitter").val();
        const google = $("#google").val();
        const instagram = $("#instagram").val();
        const imageFile = $("#image")[0].files[0];
        const logoFile = $("#logo")[0].files[0];

        if (!imageFile || !logoFile) {
            alert("Please select both an image and a logo file.");
            return;
        }

        let formData = new FormData();
        formData.append("title", title);
        formData.append("description", description);
        category.forEach(cat => formData.append("category", cat));
        formData.append("tagline", tagline);
        formData.append("basePrice", basePrice);
        formData.append("address", address);
        formData.append("mapAddress", mapAddress);
        formData.append("email", email);
        formData.append("website", website);
        formData.append("phone", phone);
        formData.append("facebook", facebook);
        formData.append("twitter", twitter);
        formData.append("google", google);
        formData.append("instagram", instagram);
        formData.append("image", imageFile);
        formData.append("logo", logoFile);

        $.ajax({
            url: "http://localhost:8080/api/v1/services/save",
            type: "POST",
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: formData,
            processData: false,
            contentType: false,
            success: (resp) => {
                alert("Service saved successfully.");
                window.location.href = "http://localhost:63342/wedding_planner_zip/html/vendor/my-services.html";
            },
            error: (err) => {
                console.error('Error saving service:', err);
                alert("Failed to save service.");
            }
        });
    });

    // Load Services
    function loadServices() {
        $.ajax({
            url: "http://localhost:8080/api/v1/services/all",
            type: "GET",
            headers: {
                "Authorization": "Bearer " + token
            },
            success: function (services) {
                let tableBody = $("#listdata-table-tbody");
                tableBody.empty();

                services.forEach(service => {
                    let row = `
                        <tr class="service-row" data-id="${service.id}">
                        <td style="display: none">${service.id}</td>
                            <td>
                                <div class="listingtable-img">
                                    <a href="service-details.html?id=${service.id}">
                                        <img class="img-fluid avatar-img" src="data:image/png;base64,${service.image}" alt="Service Image">
                                    </a>
                                </div>
                            </td>
                            <td>
                                <h6><a href="service-details.html?id=${service.id}">${service.title}</a></h6>
                                <div class="listingtable-rate">
                                    <a href="javascript:void(0)" class="cat-icon">
                                        <i class="fa-regular fa-circle-stop"></i> ${service.category}
                                    </a>
                                    <span class="discount-amt">$${service.basePrice}</span>
                                </div>
                                <p>${service.description.substring(0, 50)}...</p>
                            </td>
                            <td><span class="status-text">Published</span></td>
                            <td><span class="views-count">${service.views || 0}</span></td>
                            <td>
                                <div class="action">
                                    <a href="" class="action-btn btn-edit" data-id="${service.id}">
                                        <i class="feather-edit-3"></i>
                                    </a>
                                    <input type="hidden" id="deleteserviceId" value="${service.id}">
                                    <a href="javascript:void(0)" id="ddddd" class="delete-table" data-id="${service.id}" data-bs-toggle="modal" data-bs-target="#delete-item">
                                        <i class="feather-trash-2"></i>
                                    </a>
                                     <a href="edit-listing.html" class="action-btn btn-plus" data-id="${service.id}">
                                      <i class="feather-plus-circle"></i>
                                       </a>
                                </div>
                            </td>
                        </tr>
                    `;
                    tableBody.append(row);
                });

            },
            error: function (err) {
                console.error('Error loading services:', err);
                alert("Failed to load services.");
            }
        });
    }


    loadServices();

    const deleteService = document.getElementById('delete-item');

    deleteService.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget; // Button that triggered the modal
        const serviceId = button.getAttribute('data-id'); // Extract info from data-* attributes

        console.log('Service ID:', serviceId);
        // Update the modal's content

    });


    $('#delete-service-btn').click(function () {
        const id = $("#deleteserviceId").val();

        console.log('Delete service ID:', id);
        console.log('Delete service:', id);
        $.ajax({
            url: `http://localhost:8080/api/v1/services/delete/${id}`,
            type: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            success: function (response) {
                alert('Service deleted successfully.');
                $('#delete-item').modal('hide');
                loadServices();
            },
            error: function (error) {
                console.error('Error deleting service:', error);
                alert('Failed to delete service.');
            }
        });
    });
});

/////////////////////////////////////
$(document).ready(function () {
    let editMode = false; // Track edit mode
    const token = localStorage.getItem("token");

    // Open modal
    $(document).on("click", ".btn-plus", function (e) {
        e.preventDefault();
        $("#featureModal").modal("show");
    });
});
//////////////////////////////////////////////////////////////////////////////////

$(document).ready(function () {
    const token = localStorage.getItem("token");

    $("#saveFeatureBtn").click((e) => {
        e.preventDefault();
   console.log("save feature btn click")
        const serviceId = $("#deleteserviceId").val();
   console.log("servicesId", serviceId)
        const name = $("#featureName").val();
        const logoFile = $("#featureLogo")[0].files;
        console.log(logoFile)

        if (!logoFile) {
            alert("Please select a logo file.");
            return;
        }

        let formData = new FormData();
        formData.append("serviceId", serviceId);
        formData.append("name", name);
        formData.append("logo", logoFile[0]);

        $.ajax({
            url: "http://localhost:8080/api/v1/features/save",
            type: "POST",
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: formData,
            processData: false,
            contentType: false,
            success: (resp) => {
                alert("Feature saved successfully.");
            },
            error: (err) => {
                console.error('Error saving feature:', err);
                alert("Failed to save feature.");
            }
        });
    });
});

$(document).ready(function () {
    const token = localStorage.getItem("token");

    $("#uploadGalleryBtn").click((e) => {
        e.preventDefault();
        console.log("upload gallery btn click");

        const serviceId = $("#deleteserviceId").val();
        const image = $("#galleryImages")[0].files;


        let formData = new FormData();
        formData.append("serviceId", serviceId);
        formData.append("image", image[0]);

        $.ajax({
            url: "http://localhost:8080/api/v1/gallery/save",
            type: "POST",
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: formData,
            processData: false,
            contentType: false,
            success: (resp) => {
                alert("Gallery saved successfully.");
            },
            error: (err) => {
                console.error('Error saving feature:', err);
                alert("Failed to save gallery");
            }
        });
    });
});
