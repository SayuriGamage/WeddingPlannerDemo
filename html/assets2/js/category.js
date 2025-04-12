document.addEventListener('DOMContentLoaded', function() {
    fetchCategories();
});

function fetchCategories() {
    fetch('http://localhost:8080/api/v1/categories/all')
        .then(response => response.json())
        .then(categories => {
            const container = document.querySelector('.categorieslist-section .row');
            container.innerHTML = ''; // Clear existing content
            categories.forEach(category => {
                const categoryHTML = `
                    <div class="col-lg-3 col-md-4">
                        <div class="categories-content">
                            <a href="listing-list-sidebar.html" class="text-center aos aos-init aos-animate" data-aos="fade-up">
                                <img src="../../html/images/${category.photo}" alt="${category.name}">
                                <h6>${category.name}</h6>
                                <span>${category.description}</span>
                            </a>
                        </div>
                    </div>
                `;
                container.insertAdjacentHTML('beforeend', categoryHTML);
            });
        })
        .catch(error => console.error('Error fetching categories:', error));
}