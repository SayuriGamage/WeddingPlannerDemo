// Initialize AOS
AOS.init({
    duration: 1000,
    once: true,
    offset: 100
});

$(document).ready(function () {
    $('.counter').counterUp({
        delay: 10,
        time: 1000
    });

    // Initialize AOS
    AOS.init({
        duration: 1000,
        once: true
    });
});

    // Counter Animation
    document.addEventListener('DOMContentLoaded', function() {
    const counters = document.querySelectorAll('.counter');
    const speed = 200; // The lower the faster

    function animateCounters() {
    counters.forEach(counter => {
    const target = +counter.getAttribute('data-count');
    const count = +counter.innerText;
    const increment = target / speed;

    if (count < target) {
    counter.innerText = Math.ceil(count + increment);
    setTimeout(animateCounters, 1);
} else {
    counter.innerText = target;
}
});
}

    // Start animation when element is in viewport
    const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
    if (entry.isIntersecting) {
    animateCounters();
    observer.unobserve(entry.target);
}
});
}, {threshold: 0.5}); // Trigger when 50% of element is visible

    // Observe each counter element
    counters.forEach(counter => {
    observer.observe(counter);
});
});

