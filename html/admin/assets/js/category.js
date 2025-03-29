$(document).ready(function() {
    const token = localStorage.getItem("token");

    $("#btn_save_category").click((e) => {
        e.preventDefault();
        const name = $("#category_name").val();
        const description = $("#category_description").val();
        const photoFile = $("#category_photo")[0].files[0]; // Get the actual file

        if (!photoFile) {
            alert("Please select an image file.");
            return;
        }

        let formData = new FormData();
        formData.append("name", name);
        formData.append("description", description);
        formData.append("photo", photoFile);

        $.ajax({
            url: "http://localhost:8080/api/v1/categories/save",
            type: "POST",
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: formData,
            processData: false,
            contentType: false,
            success: (resp) => {
                console.log('Success:', resp);
                alert("Category saved successfully.");
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
                            <td style="display: none;">${category.cid}</td>
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
                                   <a class="delete-table" href="javascript:void(0);" data-name="${category.name}" data-bs-toggle="modal" data-bs-target="#delete-item">
    <img src="assets/img/icons/delete.svg" alt="Delete">
</a>
                                </div>
                            </td>
                        </tr>
                    `;
                    tableBody.append(row);
                });

                $('.edit-table').on('click', function() {
                    let row = $(this).closest('tr');
                    let id=row.data('id');
                    let name = row.data('name');

                    let description = row.data('description');
                    let photo = row.data('photo');

                    $('#edit_category_id').val(id);
                    $('#edit_category_name').val(name);
                    $('#edit_category_description').val(description);
                    $('#current_category_photo').attr('src', `data:image/png;base64,${photo}`);
                });
            },
            error: function(err) {
                alert("Failed to load categories.");
            }
        });
    }

    loadCategories();
});

$(document).ready(function() {
    const token = localStorage.getItem("token");

    $('#edit-category-form').on('submit', function(e) {
        e.preventDefault();

        const formData = new FormData();
        formData.append('id', $('#edit_category_id').val());
        formData.append('name', $('#edit_category_name').val());
        formData.append('description', $('#edit_category_description').val());


        const  id=$('#edit_category_id').val();
        const photoFile = $('#edit_category_photo')[0].files[0];
        if (photoFile) {
            formData.append('photo', photoFile);
        }

        $.ajax({
            url: `http://localhost:8080/api/v1/categories/update/${id}`,
            type: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                alert('Category updated successfully.');
                $('#edit-category-modal').modal('hide');
                loadCategories();
            },
            error: function(error) {
                console.error('Error updating category:', error);
                alert('Failed to update category.');
            }
        });
    });
});


/*delete category*/
$(document).ready(function() {
    const token = localStorage.getItem("token");
    $('#delete-item').on('show.bs.modal', function(event) {
        const button = $(event.relatedTarget);
        const categoryName = button.data('name');
        const modal = $(this);

        modal.find('.btn-primary').off('click').on('click', function() {
            $.ajax({
                url: `http://localhost:8080/api/v1/categories/delete/${categoryName}`,
                type: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                success: function(response) {
                    alert('Category deleted successfully.');
                    modal.modal('hide');
                    loadCategories();
                },
                error: function(error) {
                    console.error('Error deleting category:', error);
                    alert('Failed to delete category.');
                }
            });
        });
    });
});