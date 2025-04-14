$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/api/v1/feedback/all",
        type: "GET",
        success: function (reviews) {
            let tbody = $("#reviewTable");
            tbody.empty();

            reviews.forEach(function (review, index) {
                let row = `
                    <tr>
                        <td>${review.title}</td>
                        <td>${review.name}</td>
                    
                        <td>${review.submittedAt}</td>
                        <td>${'⭐'.repeat(review.rating)}</td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function (xhr, status, error) {
            console.error("Failed to load reviews:", error);
        }
    });
});