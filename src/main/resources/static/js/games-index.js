document.querySelectorAll(".game-option").forEach((button) => {
    button.addEventListener("click", () => {
        document.querySelectorAll(".game-option").forEach((item) => {
            item.classList.remove("active");
        });

        button.classList.add("active");

        const price = button.dataset.price
            ? `${Number(button.dataset.price).toFixed(2)} €`
            : "Prezzo non disponibile";

        document.getElementById("featuredGameLink").href = button.dataset.url;
        document.getElementById("featuredGameTitle").textContent = button.dataset.title;
        document.getElementById("featuredGameDescription").textContent = button.dataset.description;
        document.getElementById("featuredGameDeveloper").textContent = button.dataset.developer;
        document.getElementById("featuredGamePrice").textContent = price;
        document.getElementById("featuredGameDate").textContent = button.dataset.date;
    });
});