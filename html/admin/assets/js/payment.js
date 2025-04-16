const amount = 20.00;
const currency = "USD";
const orderId = "ORDER_" + Date.now();

document.addEventListener("DOMContentLoaded", function () {
    const pendingUser = JSON.parse(localStorage.getItem("pendingUser"));
    if (!pendingUser) {
        alert("No pending user found. Please register again.");
        window.location.href = "signup.html";
        return;
    }
    document.getElementById("userName").textContent = pendingUser.name;
});

payhere.onCompleted = function (orderId) {
    const user = JSON.parse(localStorage.getItem("pendingUser"));
    const payment = {
        orderId: orderId,
        status: "success",
        amount: amount,
        currency: currency,
        paidAt: new Date().toISOString()
    };

    const payload = { user: user, payment: payment };

    fetch("http://localhost:8080/api/v1/user/register-vendor", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(() => {
            alert("Vendor registration successful!");
            localStorage.removeItem("pendingUser");
            window.location.href = "signin.html";
        })
        .catch(err => {
            console.error("Registration error", err);
            alert("Something went wrong during vendor registration.");
        });
};

payhere.onDismissed = () => alert("Payment dismissed.");
payhere.onError = err => alert("Payment error: " + err);

document.getElementById("payNowBtn").addEventListener("click", function () {
    const user = JSON.parse(localStorage.getItem("pendingUser"));
    const payment = {
        sandbox: true,
        merchant_id: "122XXXX", // Replace with your merchant_id
        return_url: undefined,
        cancel_url: undefined,
        notify_url: "http://localhost:8080/payments/notify",

        order_id: orderId,
        items: "Vendor Monthly Subscription",
        amount: amount,
        currency: currency,

        first_name: user.name.split(" ")[0],
        last_name: user.name.split(" ")[1] || "Vendor",
        email: user.email,
        phone: user.contact || "0000000000",
        address: user.address || "Address",
        city: "Colombo",
        country: "Sri Lanka"
    };

    payhere.startPayment(payment);
});