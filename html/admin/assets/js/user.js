$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/api/v1/profile/vendors",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (vendors) {
            let tbody = $("#vendorTableBody");
            tbody.empty();

            vendors.forEach(function (vendor, index) {
                let row = `
                    <tr>
                        <td>${index + 1}</td>
                        <td>
                            <img src="data:image/jpeg;base64,${vendor.photo}" alt="img" style="width: 40px; height: 40px; border-radius: 50%;">
                        </td>
                        <td>${vendor.name}</td>
                        <td>${vendor.contact}</td>
                        <td>${vendor.email}</td>
                        <td><span class="badge bg-success">Active</span></td>
                        <td>
                            <div class="table-actions d-flex">
                                <a class="delete-table me-2" href="#">
                                   <img src="assets/img/icons/eye.svg" alt="svg">
                                </a>
                                <a class="delete-table me-2" href="#">
                                   <img src="assets/img/icons/edit.svg" alt="svg">
                                </a>
                                <a class="delete-table" href="#" data-bs-toggle="modal" data-bs-target="#delete-item">
                                    <img src="assets/img/icons/delete.svg" alt="svg">
                                </a>
                            </div>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function (xhr) {
            console.error("Failed to load vendors", xhr);
        }
    });

    $.ajax({
        url: "http://localhost:8080/api/v1/profile/brides",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (brides) {
            let tbody = $("#brideTableBody");
            tbody.empty();

            brides.forEach(function (bride, index) {
                let row = `
                    <tr>
                        <td>${index + 1}</td>
                        <td>
                            <img src="data:image/jpeg;base64,${bride.photo}" alt="img" style="width: 40px; height: 40px; border-radius: 50%;">
                        </td>
                        <td>${bride.name}</td>
                        <td>${bride.email}</td>
                        <td>${bride.contact}</td>
                        <td><span class="badge bg-success">Active</span></td>
                        <td>
                            <div class="table-actions d-flex">
                                <a class="delete-table me-2" href="#">
                                   <img src="assets/img/icons/eye.svg" alt="svg">
                                </a>
                                <a class="delete-table me-2" href="#">
                                   <img src="assets/img/icons/edit.svg" alt="svg">
                                </a>
                                <a class="delete-table" href="#" data-bs-toggle="modal" data-bs-target="#delete-item">
                                    <img src="assets/img/icons/delete.svg" alt="svg">
                                </a>
                            </div>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function (xhr) {
            console.error("Failed to load brides", xhr);
        }
    });
});

