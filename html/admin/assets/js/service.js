$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/api/v1/services/loadall",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (services) {
            let tbody = $("#serviceTable");
            tbody.empty(); // Clear existing rows

            services.forEach(function (service, index) {
                let row = `
                        <tr>
                            <td>${index + 1}</td>
                            <td>
                                <a href="view-listing.html" class="table-imgname">
                                    <img src="data:image/jpeg;base64,${service.image}" class="me-2" alt="img" style="width: 40px; height: 40px;">
                                    <span>${service.title}</span>
                                </a>
                            </td>
                            <td>${service.category}</td>
                            <td>${service.tagline}</td>
                            <td>$${service.basePrice}</td>
                     
                            <td><h6 class="badge-active">Active</h6></td>
                          
                            <td>
                                <div class="table-actions d-flex">
                                    <a class="delete-table me-2" href="view-listing.html">
                                       <img src="assets/img/icons/eye.svg" alt="svg">
                                    </a>
                                    <a class="delete-table me-2" href="edit-listing.html">
                                       <img src="assets/img/icons/edit.svg" alt="svg">
                                    </a>
                                    <a class="delete-table" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#delete-item">
                                        <img src="assets/img/icons/delete.svg" alt="svg">
                                    </a>
                                </div>
                            </td>
                        </tr>
                    `;
                tbody.append(row);
            });
        },
        error: function (xhr, status, error) {
            console.error("Error loading services:", error);
        }
    });
});