
document.querySelectorAll('.nav-item').forEach(item => {
    item.addEventListener('click', () => {
        document.querySelectorAll('.nav-item').forEach(nav => nav.classList.remove('active'));
        item.classList.add('active');

        const sectionId = item.getAttribute('data-section');
        document.querySelectorAll('.dashboard-section').forEach(section => {
            section.classList.remove('active');
        });
        document.getElementById(sectionId).classList.add('active');
    });
});


const ctx = document.getElementById('analyticsChart').getContext('2d');
const analyticsChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [{
            label: 'Site Traffic',
            data: [30, 50, 40, 60, 70, 90],
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 2,
            fill: false
        }]
    },
    options: {
        responsive: true,
        scales: {
            x: {
                display: true,
                title: {
                    display: true,
                    text: 'Month'
                }
            },
            y: {
                display: true,
                title: {
                    display: true,
                    text: 'Visitors'
                }
            }
        }
    }
});


$("#menu-toggle").click(function(e) {
    e.preventDefault();
    $(".sidebar").toggleClass("toggled");
    $(".content").toggleClass("toggled");
});

document.addEventListener('DOMContentLoaded', () => {
    loadUsers();
});

function loadUsers() {

    const users = [
        { id: 1, name: 'Jane Doe', email: 'jane@example.com', role: 'Bride', status: 'Active', registrationDate: '2023-01-01' },
    ];
    populateUserTable(users);
}

function populateUserTable(users) {
    const tableBody = document.getElementById('userTableBody');
    tableBody.innerHTML = '';
    users.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>${user.status}</td>
            <td>${user.registrationDate}</td>
            <td>
                <button onclick="viewUser(${user.id})">View</button>
                <button onclick="deleteUser(${user.id})">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

function filterUsers() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const roleFilter = document.getElementById('roleFilter').value;
}

function sortTable(columnIndex) {
    const table = document.querySelector('.user-table');
    const rows = Array.from(table.rows).slice(1);
    const sortedRows = rows.sort((a, b) => {
        const aText = a.cells[columnIndex].innerText;
        const bText = b.cells[columnIndex].innerText;
        return aText.localeCompare(bText);
    });
    sortedRows.forEach(row => table.appendChild(row));
}

function viewUser(userId) {

    const user = { id: userId, name: 'Jane Doe', email: 'jane@example.com', role: 'Bride', status: 'Active' };
    document.getElementById('userId').value = user.id;
    document.getElementById('userName').value = user.name;
    document.getElementById('userEmail').value = user.email;
    document.getElementById('userRole').value = user.role;
    document.getElementById('userStatus').value = user.status;
    document.getElementById('userModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('userModal').style.display = 'none';
}

function saveUser() {
    const userId = document.getElementById('userId').value;
    const userName = document.getElementById('userName').value;
    const userEmail = document.getElementById('userEmail').value;
    const userRole = document.getElementById('userRole').value;
    const userStatus = document.getElementById('userStatus').value;
    console.log(`Saving user ${userId} with name ${userName}, email ${userEmail}, role ${userRole}, status ${userStatus}`);
    closeModal();
    loadUsers();
}

function deleteUser(userId) {
    if (confirm('Are you sure you want to delete this user?')) {
        console.log(`Deleting user ${userId}`);
        loadUsers();
    }
}

function viewUser(userId) {
    const user = { id: userId, name: 'Jane Doe', email: 'jane@example.com', role: 'Bride', status: 'Active' };
    document.getElementById('userId').value = user.id;
    document.getElementById('userName').value = user.name;
    document.getElementById('userEmail').value = user.email;
    document.getElementById('userRole').value = user.role;
    document.getElementById('userStatus').value = user.status;
    document.getElementById('userModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('userModal').style.display = 'none';
}

function enableEdit() {
    document.getElementById('userName').disabled = false;
    document.getElementById('userEmail').disabled = false;
    document.getElementById('userRole').disabled = false;
    document.getElementById('userStatus').disabled = false;
    document.getElementById('editButton').style.display = 'none';
    document.getElementById('saveButton').style.display = 'block';
}

function saveUser() {
    const userId = document.getElementById('userId').value;
    const userName = document.getElementById('userName').value;
    const userEmail = document.getElementById('userEmail').value;
    const userRole = document.getElementById('userRole').value;
    const userStatus = document.getElementById('userStatus').value;
    // Save user details to the server (replace with actual API call)
    console.log(`Saving user ${userId} with name ${userName}, email ${userEmail}, role ${userRole}, status ${userStatus}`);
    closeModal();
    loadUsers();
}

function deleteUser(userId) {
    if (confirm('Are you sure you want to delete this user?')) {
        console.log(`Deleting user ${userId}`);
        loadUsers();
    }
}

function loadProfileImage(event) {
    const image = document.getElementById('profileImage');
    image.src = URL.createObjectURL(event.target.files[0]);
}
function viewService(serviceId) {
    const service = { id: serviceId, name: 'Floral Dreams', description: 'Floral Decoration', category: 'Decoration', status: 'Active' };
    document.getElementById('serviceId').value = service.id;
    document.getElementById('serviceName').value = service.name;
    document.getElementById('serviceDescription').value = service.description;
    document.getElementById('serviceCategory').value = service.category;
    document.getElementById('serviceStatus').value = service.status;
    document.getElementById('serviceModal').style.display = 'block';
}

function closeServiceModal() {
    document.getElementById('serviceModal').style.display = 'none';
}

function enableServiceEdit() {
    document.getElementById('serviceName').disabled = false;
    document.getElementById('serviceDescription').disabled = false;
    document.getElementById('serviceCategory').disabled = false;
    document.getElementById('serviceStatus').disabled = false;
    document.getElementById('editServiceButton').style.display = 'none';
    document.getElementById('saveServiceButton').style.display = 'block';
}

function saveService() {
    const serviceId = document.getElementById('serviceId').value;
    const serviceName = document.getElementById('serviceName').value;
    const serviceDescription = document.getElementById('serviceDescription').value;
    const serviceCategory = document.getElementById('serviceCategory').value;
    const serviceStatus = document.getElementById('serviceStatus').value;
    console.log(`Saving service ${serviceId} with name ${serviceName}, description ${serviceDescription}, category ${serviceCategory}, status ${serviceStatus}`);
    closeServiceModal();
    loadServices();
}

function deleteService(serviceId) {
    if (confirm('Are you sure you want to delete this service?')) {
        console.log(`Deleting service ${serviceId}`);
        loadServices();
    }
}

function loadServiceImage(event) {
    const image = document.getElementById('serviceImage');
    image.src = URL.createObjectURL(event.target.files[0]);
}

function viewBooking(bookingId) {
    const booking = { id: bookingId, name: 'John Doe', date: '2023-12-01', status: 'Confirmed' };
    document.getElementById('bookingId').value = booking.id;
    document.getElementById('bookingName').value = booking.name;
    document.getElementById('bookingDate').value = booking.date;
    document.getElementById('bookingStatus').value = booking.status;
    document.getElementById('bookingModal').style.display = 'block';
}

function closeBookingModal() {
    document.getElementById('bookingModal').style.display = 'none';
}

function enableBookingEdit() {
    document.getElementById('bookingName').disabled = false;
    document.getElementById('bookingDate').disabled = false;
    document.getElementById('bookingStatus').disabled = false;
    document.getElementById('editBookingButton').style.display = 'none';
    document.getElementById('saveBookingButton').style.display = 'block';
}

function saveBooking() {
    const bookingId = document.getElementById('bookingId').value;
    const bookingName = document.getElementById('bookingName').value;
    const bookingDate = document.getElementById('bookingDate').value;
    const bookingStatus = document.getElementById('bookingStatus').value;
    console.log(`Saving booking ${bookingId} with name ${bookingName}, date ${bookingDate}, status ${bookingStatus}`);
    closeBookingModal();
    loadBookings();
}

function deleteBooking(bookingId) {
    if (confirm('Are you sure you want to delete this booking?')) {
        console.log(`Deleting booking ${bookingId}`);
        loadBookings();
    }
}

function loadServices() {
    const services = [
        { id: 1, name: 'Floral Dreams', description: 'Floral Decoration', category: 'Decoration', status: 'Active' },
        { id: 2, name: 'Gourmet Catering', description: 'Catering Services', category: 'Catering', status: 'Pending' }
    ];

    const serviceTableBody = document.getElementById('serviceTableBody');
    serviceTableBody.innerHTML = '';

    services.forEach(service => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${service.id}</td>
            <td>${service.name}</td>
            <td>${service.description}</td>
            <td>${service.category}</td>
            <td>${service.status}</td>
            <td>
                <button onclick="viewService(${service.id})">View</button>
                <button onclick="deleteService(${service.id})">Delete</button>
            </td>
        `;
        serviceTableBody.appendChild(row);
    });
}

document.addEventListener('DOMContentLoaded', loadServices);
function viewUser(userId) {
    const user = { id: userId, name: 'Jane Doe', email: 'jane@example.com', role: 'Bride', status: 'Active' };
    document.getElementById('userId').value = user.id;
    document.getElementById('userName').value = user.name;
    document.getElementById('userEmail').value = user.email;
    document.getElementById('userRole').value = user.role;
    document.getElementById('userStatus').value = user.status;
    document.getElementById('userModal').style.display = 'block';
}

function closeUserModal() {
    document.getElementById('userModal').style.display = 'none';
}

function enableUserEdit() {
    document.getElementById('userName').disabled = false;
    document.getElementById('userEmail').disabled = false;
    document.getElementById('userRole').disabled = false;
    document.getElementById('userStatus').disabled = false;
    document.getElementById('editUserButton').style.display = 'none';
    document.getElementById('saveUserButton').style.display = 'block';
}

function saveUser() {
    const userId = document.getElementById('userId').value;
    const userName = document.getElementById('userName').value;
    const userEmail = document.getElementById('userEmail').value;
    const userRole = document.getElementById('userRole').value;
    const userStatus = document.getElementById('userStatus').value;
    console.log(`Saving user ${userId} with name ${userName}, email ${userEmail}, role ${userRole}, status ${userStatus}`);
    closeUserModal();
    loadUsers();
}

function viewService(serviceId) {
    const service = { id: serviceId, name: 'Floral Dreams', description: 'Floral Decoration', category: 'Decoration', status: 'Active' };
    document.getElementById('serviceId').value = service.id;
    document.getElementById('serviceName').value = service.name;
    document.getElementById('serviceDescription').value = service.description;
    document.getElementById('serviceCategory').value = service.category;
    document.getElementById('serviceStatus').value = service.status;
    document.getElementById('serviceModal').style.display = 'block';
}

function closeServiceModal() {
    document.getElementById('serviceModal').style.display = 'none';
}

function enableServiceEdit() {
    document.getElementById('serviceName').disabled = false;
    document.getElementById('serviceDescription').disabled = false;
    document.getElementById('serviceCategory').disabled = false;
    document.getElementById('serviceStatus').disabled = false;
    document.getElementById('editServiceButton').style.display = 'none';
    document.getElementById('saveServiceButton').style.display = 'block';
}

function saveService() {
    const serviceId = document.getElementById('serviceId').value;
    const serviceName = document.getElementById('serviceName').value;
    const serviceDescription = document.getElementById('serviceDescription').value;
    const serviceCategory = document.getElementById('serviceCategory').value;
    const serviceStatus = document.getElementById('serviceStatus').value;
    console.log(`Saving service ${serviceId} with name ${serviceName}, description ${serviceDescription}, category ${serviceCategory}, status ${serviceStatus}`);
    closeServiceModal();
    loadServices();
}

function loadBookings() {
    const bookings = [
        { id: 1, name: 'John Doe', date: '2023-12-01', status: 'Confirmed' },
        { id: 2, name: 'Jane Smith', date: '2023-12-15', status: 'Pending' }
    ];

    const bookingTableBody = document.getElementById('bookingTableBody');
    bookingTableBody.innerHTML = '';

    bookings.forEach(booking => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${booking.id}</td>
            <td>${booking.name}</td>
            <td>${booking.date}</td>
            <td>${booking.status}</td>
            <td>
                <button onclick="viewBooking(${booking.id})">View</button>
                <button onclick="deleteBooking(${booking.id})">Delete</button>
            </td>
        `;
        bookingTableBody.appendChild(row);
    });
}

function viewBooking(bookingId) {
    const booking = { id: bookingId, name: 'John Doe', date: '2023-12-01', status: 'Confirmed' };
    document.getElementById('bookingId').value = booking.id;
    document.getElementById('bookingName').value = booking.name;
    document.getElementById('bookingDate').value = booking.date;
    document.getElementById('bookingStatus').value = booking.status;
    document.getElementById('bookingModal').style.display = 'block';
}

function closeBookingModal() {
    document.getElementById('bookingModal').style.display = 'none';
}

function enableBookingEdit() {
    document.getElementById('bookingName').disabled = false;
    document.getElementById('bookingDate').disabled = false;
    document.getElementById('bookingStatus').disabled = false;
    document.getElementById('editBookingButton').style.display = 'none';
    document.getElementById('saveBookingButton').style.display = 'block';
}

function saveBooking() {
    const bookingId = document.getElementById('bookingId').value;
    const bookingName = document.getElementById('bookingName').value;
    const bookingDate = document.getElementById('bookingDate').value;
    const bookingStatus = document.getElementById('bookingStatus').value;
    console.log(`Saving booking ${bookingId} with name ${bookingName}, date ${bookingDate}, status ${bookingStatus}`);
    closeBookingModal();
    loadBookings();
}

function deleteBooking(bookingId) {
    if (confirm('Are you sure you want to delete this booking?')) {
        console.log(`Deleting booking ${bookingId}`);
        loadBookings();
    }
}

document.addEventListener('DOMContentLoaded', loadBookings);