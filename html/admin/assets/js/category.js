$(document).ready(function() {
    const token = localStorage.getItem("token");

    /*add categories*/
    const categoryPhotoInput = document.getElementById('category_photo');
    if (categoryPhotoInput) {
        categoryPhotoInput.addEventListener('change', function() {
            const file = this.files[0];
            const reader = new FileReader();
            reader.onloadend = function() {
                categoryPhotoInput.setAttribute('data-base64', reader.result.split(',')[1]);
            }
            reader.readAsDataURL(file);
        });
    } else {
        console.error("Element with ID 'category_photo' not found.");
    }

    $("#btn_save_category").click((e) => {
        e.preventDefault();
        const name = $("#category_name").val();
        const description = $("#category_description").val();
        const photoBase64 = $("#category_photo").attr('data-base64');

        const categoryData = {
            name: name,
            description: description,
            photo: photoBase64
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/categories/save",
            type: "POST",
            headers: {
                'Content-Type': "application/json",
                'Authorization': `Bearer ${token}`
            },
            data: JSON.stringify(categoryData),
            success: (resp) => {
                console.log('Success:', resp);
                alert("Category saved.");
            },
            error: (err) => {
                console.error('Error:', err);
                alert("Category not saved.");
            }
        });
    });

    function loadCategories() {
        $.ajax({
            url: "http://localhost:8080/api/v1/categories/all",
            type: "GET",
            success: function(categories) {
                let tableBody = $("#categories-table");
                tableBody.empty();
                categories.forEach(category => {
                    let row = `
                        <tr class="category-row" data-id="${category.cid}" data-name="${category.name}" data-description="${category.description}" data-photo="${category.photo}">
                            <td>
                                <div class="table-imgname">
                                    <img src="data:image/png;base64,${category.photo}" class="me-2" alt="img">
                                </div>
                            </td>
                            <td>${category.name}</td>
                            <td>${category.description}</td>
                            <td>
                                <div class="table-actions d-flex">
                                    <a class="edit-table me-2" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#edit-category-modal">
                                        <img src="assets/img/icons/edit.svg" alt="svg">
                                    </a>
                                    <a class="delete-table" href="" data-bs-toggle="modal" data-bs-target="#delete-item">
                                        <img src="assets/img/icons/delete.svg" alt="svg">
                                    </a>
                                </div>
                            </td>
                        </tr>
                    `;
                    tableBody.append(row);
                });

                // Add click event listener to each edit button
                $('.edit-table').on('click', function() {
                    let row = $(this).closest('tr');
                    let id = row.data('id');
                    let name = row.data('name');
                    let description = row.data('description');
                    let photo = row.data('photo');

                    // Populate the form fields
                    $('#edit_category_name').val(name);
                    $('#edit_category_description').val(description);
                    $('#current_category_photo').attr('src', `data:image/png;base64,${photo}`);

                    // Store the category ID in the form
                    $('#edit-category-form').data('id', id);
                });
            },
            error: function(err) {
                alert("Failed to load categories.");
            }
        });
    }

    loadCategories();
});