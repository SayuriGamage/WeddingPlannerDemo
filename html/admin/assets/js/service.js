

$(document).ready(function () {
    const token = localStorage.getItem("token");

    function loadServices() {
        $.ajax({
            url: "http://localhost:8080/api/v1/services/all",
            type: "GET",
            headers: {
                "Authorization": "Bearer " + token
            },
            success: function (services) {
                let tableBody = $("#servicesTableBody");
                tableBody.empty();

                services.forEach(service => {
                    let row = `
                    <tr>
                        <td>
                            <a href="view-service.html?id=${service.title}" class="table-imgname">
                                <img src="data:image/png;base64,${service.image}" class="me-2" alt="img" style="width: 40px; height: 40px;">
                                <span>${service.title}</span>
                            </a>
                        </td>
                        <td>${service.category}</td>
                        <td>$${service.basePrice}</td>
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
});
