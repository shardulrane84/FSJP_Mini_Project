// Runs on every page that includes this script
document.addEventListener("DOMContentLoaded", function () {

    // 1. Confirm before any destructive action (delete / cancel / reject)
    document.querySelectorAll("form[data-confirm]").forEach(function (form) {
        form.addEventListener("submit", function (e) {
            const message = form.getAttribute("data-confirm") || "Are you sure?";
            if (!confirm(message)) {
                e.preventDefault();
            }
        });
    });

    // 2. Auto-dismiss alert banners after 4 seconds
    document.querySelectorAll(".alert").forEach(function (alertBox) {
        setTimeout(function () {
            alertBox.style.transition = "opacity .4s";
            alertBox.style.opacity = "0";
            setTimeout(function () { alertBox.remove(); }, 400);
        }, 4000);
    });

    // 3. Highlight the current page's nav link
    const links = document.querySelectorAll(".navbar nav a");
    links.forEach(function (link) {
        if (window.location.pathname.startsWith(link.getAttribute("href"))) {
            link.classList.add("active");
        }
    });

    // 4. Disable submit button after click (prevents duplicate form submits)
    document.querySelectorAll("form").forEach(function (form) {
        form.addEventListener("submit", function () {
            const btn = form.querySelector("button[type=submit]");
            if (btn) {
                btn.disabled = true;
                btn.dataset.originalText = btn.innerText;
                btn.innerText = "Please wait...";
            }
        });
    });
});