document.addEventListener('DOMContentLoaded', function () {
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
      <a href="listing-list-sidebar.html?category=${encodeURIComponent(category.name)}" class="category-card text-center aos">
        <img src="data:${category.contentType};base64,${category.photo}" alt="${category.name}">
        <h6>${category.name}</h6>
        <span>${category.description}</span>
      </a>
    </div>
  </div>
`;

                container.insertAdjacentHTML('beforeend', categoryHTML);
            });
        })
        .catch(error => console.error('menna me redden error eka paninne:', error));
}


document.addEventListener('DOMContentLoaded', function () {
    const params = new URLSearchParams(window.location.search);
    const category = params.get('category');
    if (category) {
        console.log("Loading services for category:", category);
        loadServicesByCategory(category);
    }
});


function loadServicesByCategory(category) {
    fetch(`http://localhost:8080/api/v1/services/category/${category}`)
        .then(response => response.json())
        .then(services => {
            const container = document.querySelector('.blog-listview');
            container.innerHTML = '';


            services.forEach(service => {
                console.log("email enawa" + services.email)
                const serviceHTML = `
                    <div class="card">
                        <div class="blog-widget">
                            <div class="blog-img">
                                <a href="service-details.html?id=${service.id}">
                                    <img src="data:image/png;base64,${service.image}" class="img-fluid" alt="blog-img">
                                </a>
                                <div class="fav-item">
                                    <span class="featured-text">Featured</span>
                                   <a class="fav-icon" data-service-id="${service.id}" onclick="bookmarkService('${service.id}')">
    <i class="feather-heart"></i>
</a>
										
                                </div>															    
                            </div>
                            <div class="bloglist-content">
                                <div class="card-body">
                                    <div class="blogfeaturelink">
                                        <div class="blog-features">
                                            <a href="#"><span><i class="fa-regular fa-circle-stop"></i> ${service.category}</span></a>
                                        </div>																	  
                                        <div class="blog-author"> 
                                            <div class="blog-author-img">
                                                <h6 style="display:none;">${service.id}</h6>
                                                
                                            </div>
                                            <a href="#">${service.email || 'Service Provider'}</a>
                                        </div>
                                    </div> 
                                    <h6><a href="service-details.html?id=${service.id}">${service.title}</a></h6>																	 
                                    <div class="blog-location-details">
                                        <div class="location-info"><i class="feather-map-pin"></i> ${service.address}</div>
                                        <div class="location-info"><i class="feather-phone-call"></i> ${service.phone}</div>
                                        <div class="location-info"><i class="feather-eye"></i> ${service.views || 0}</div>
                                    </div>
                                    <p class="ratings">
                                        <span>4.0</span> (50 Reviews)
                                    </p>
                                    <div class="amount-details">
                                        <div class="amount">
                                            <span class="validrate">$${service.basePrice}</span>
                                            <span>$${parseFloat(service.basePrice) + 100}</span>
                                        </div>
                                        <a href="service-details.html?id=${service.id}">View details</a>
                                    </div>	
                                </div>	
                            </div>			 
                        </div> 
                    </div>
                `;
                container.insertAdjacentHTML('beforeend', serviceHTML);
            });
        })
        .catch(error => console.error('Error loading services:', error));
}
function bookmarkService(serviceId) {
    const token = localStorage.getItem("accessToken");

    const formData = new FormData();
    formData.append("serviceId", serviceId);

    fetch("http://localhost:8080/api/v1/bookmarks/save", {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                // Get the error message from the response if possible
                return response.json().then(errData => {
                    throw new Error(errData.message || "Failed to save bookmark");
                }).catch(() => {
                    throw new Error(`HTTP error! status: ${response.status}`);
                });
            }
            return response.text();
        })
        .then(msg => {
            alert("Service bookmarked!");
            const heart = document.querySelector(`.fav-icon[data-service-id='${serviceId}'] i`);
            if (heart) {
                heart.classList.add("text-danger");
            }
        })
        .catch(err => {
            console.error("Bookmark error:", err);
            alert(`Error bookmarking service: ${err.message}`);
        });
}



function getServiceIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get('id');
}


function loadGalleryImages(galleryImages) {
    const galleryContainer = document.getElementById("gallery-slider");
    galleryContainer.innerHTML = '';

    galleryImages.forEach(image => {

        const galleryWidget = `
            <div class="gallery-widget">
                <a href="data:image/jpeg;base64,${image.image}" data-fancybox="gallery1">
                    <img class="img-fluid" alt="Image" src="data:image/jpeg;base64,${image.image}">
                </a>
            </div>
        `;

        galleryContainer.insertAdjacentHTML('beforeend', galleryWidget);
    });
}

function loadGallerysection(galleryImages) {
    const galleryRow = document.querySelector(".gallery-content .row");
    galleryRow.innerHTML = ''; // Clear existing content

    galleryImages.forEach(image => {
        const galleryItem = `
            <div class="col-lg-3 col-md-4 col-sm-6 mb-3">
                <div class="gallery-widget">
                    <a href="data:image/jpeg;base64,${image.image}" data-fancybox="gallery1">
                        <img class="img-fluid" alt="Gallery Image" src="data:image/jpeg;base64,${image.image}">
                    </a>
                </div>
            </div>
        `;
        galleryRow.insertAdjacentHTML('beforeend', galleryItem);
    });
}


document.addEventListener("DOMContentLoaded", function () {
    const serviceId = getServiceIdFromUrl();
    if (serviceId) {
        fetch(`http://localhost:8080/api/v1/gallery/all/${serviceId}`)
            .then(response => response.json())
            .then(data => {
                loadGalleryImages(data);
                loadGallerysection(data);
            })
            .catch(err => console.error("Failed to load gallery images", err));
    }
});


document.addEventListener("DOMContentLoaded", function () {
    const serviceId = getServiceIdFromUrl();
    if (serviceId) {
        fetch(`http://localhost:8080/api/v1/profile/byId/${serviceId}`)
            .then(response => response.json())
            .then(data => {
                loadVendorProfile(data);
            })
            .catch(err => console.error("Failed to load gallery images", err));
    }
});


function loadVendorProfile(data) {
    const user = data[0];
    const base64Image = user && user.photo ? `data:image/png;base64,${user.photo}` : "assets/img/profile-img.jpg";
    $("#serviceVendor").attr("src", base64Image);
    $("#vendorEmails").val(user.email);
}




document.addEventListener("DOMContentLoaded", function () {
    const serviceId = getServiceIdFromUrl();
    if (serviceId) {
        fetch(`http://localhost:8080/api/v1/services/load/${serviceId}`)
            .then(response => response.json())
            .then(data => {
                loadServiceDetails(data);
                loadDescription(data);
                loadHeadlineData(data);
            })
            .catch(err => console.error("Failed to load gallery images", err));
    }
});

function loadServiceDetails(data) {
    const serviceHtml = `
      
            <div class="card">
                <h4><img src="assets/img/breifcase.svg" alt=""> Business Info</h4>
                <div class="map-details">
                    <div class="map-frame">
                       
                        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3958.524486525694!2d79.853897!3d6.927079!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3ae25949c1e3bca7%3A0xf8e0c722b2a89a6!2sGalle%20Road%2C%20Colombo%20003!5e0!3m2!1sen!2slk!4v1711523456789"
                          " 
                                width="200" height="160" style="border:0;" 
                                allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                    </div>
                    <ul class="info-list">
                        
                        <li><i class="feather-map-pin"></i> ${data.mapAddress || "Default Address"}</li>

                       
                        <li><i class="feather-phone-call"></i> ${data.phone || "Default Phone Number"}</li>

                       
                        <li><i class="feather-mail"></i> <a href="mailto:${data.email || "default@example.com"}">${data.email || "default@example.com"}</a></li>

                       
                        <li><img src="assets/img/website.svg" alt="website"> <a href="${data.website || '#'}" target="_blank">${data.website || "www.defaultwebsite.com"}</a></li>

                       
                        <li class="socialicons pb-0">
                            <a href="${data.socialLinks?.facebook || '#'}" target="_blank"><i class="fab fa-facebook-f"></i></a>
                            <a href="${data.socialLinks?.twitter || '#'}" target="_blank"><i class="fab fa-twitter"></i></a>
                            <a href="${data.socialLinks?.instagram || '#'}" target="_blank"><i class="fab fa-instagram"></i></a>
                            <a href="${data.socialLinks?.linkedin || '#'}" target="_blank"><i class="fab fa-linkedin-in"></i></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    `;


    document.querySelector('.service-details-container').innerHTML = serviceHtml;
}


function loadDescription(data) {

    const descriptionText = data.description || "Default description text if no data is available.";


    const descriptionCardBody = document.querySelector('.card .card-body p');


    if (descriptionCardBody) {
        descriptionCardBody.textContent = descriptionText;
    }
}

function loadHeadlineData(data) {

    const serviceTitle = document.querySelector('[data-title]');
    const serviceSubtitle = document.querySelector('[data-tagline]');

    serviceTitle.textContent = data.title || "Default Title";
    serviceSubtitle.textContent = data.tagline || "Default Tagline";
}


    document.addEventListener('DOMContentLoaded', () => {
    const stars = document.querySelectorAll('.star-rating i');
    const ratingInput = document.getElementById('ratingInput');

    stars.forEach((star, index) => {
    star.addEventListener('click', () => {
    const rating = star.getAttribute('data-value');
    ratingInput.value = rating;

    stars.forEach((s, i) => {
    if (i < rating) {
    s.classList.remove('far'); // empty star
    s.classList.add('fas');    // solid star
} else {
    s.classList.remove('fas');
    s.classList.add('far');
}
});
});
});
});

$(document).ready(function () {
    $("#contactForm").submit(function (e) {
        e.preventDefault();

        const to = $("#vendorEmails").val();
        const subject = "New Vendor Message From Website";
        const text = $("#messageText").val();

        $.ajax({
            url: "http://localhost:8080/api/v1/email/submit",
            method: "GET",
            data: {
                to: to,
                subject: subject,
                text: text
            },
            success: function (response) {
                alert("Email sent successfully!");
            },
            error: function (xhr, status, error) {
                console.error("Error sending email:", error);
                alert("send email.");
            }
        });
    });
});
