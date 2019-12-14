'use strict';

$.fn.api.settings.api = {
    'get books' : 'api/catalog/books'
};

$('.container')
    .api({
        action: 'get books',
        on : 'now',
        onSuccess: function (response) {
            loadBooks(response);
        },
        onFailure: function(response) {
            console.log(response);
        },
        onError: function(errorMessage) {
            console.log(errorMessage);
        }
    });

function loadBooks(response) {
    const books = response["_embedded"]["books"];

    books.forEach(book => {
        let card = document.createElement("div");
        let header = document.createElement("div");
        let content = document.createElement("div");
        let extra = document.createElement("div");
        let meta = document.createElement("div");
        let rating = document.createElement("div");
        let ratingAttr = document.createAttribute("data-rating");
        let image = document.createElement("div");
        let img = document.createElement("img");

        // Add classes
        card.className = 'card';
        image.classList.add('ui', 'small', 'centered', 'rounded', 'image');
        content.className = 'content';
        header.className = 'header';
        meta.className = 'meta';
        extra.className = 'extra';
        rating.classList.add('ui', 'star', 'rating');
        ratingAttr.value = "5";

        // Populate element content
        header.innerText = book.title;
        img.src = `http://covers.openlibrary.org/b/id/${book.cover_id}-M.jpg`;
        img.alt = 'Book cover image';
        meta.innerText = 'Category';
        extra.innerText = 'Rating:';

        // Add element hierarchy
        extra.appendChild(rating);
        content.appendChild(header);
        content.appendChild(meta);
        image.appendChild(img);

        card.appendChild(image);
        card.appendChild(content);
        card.appendChild(extra);
        rating.setAttributeNode(ratingAttr);
        // Add to container
        const cards = document.getElementsByClassName('ui five doubling cards')[0];
        cards.appendChild(card);
    });
}