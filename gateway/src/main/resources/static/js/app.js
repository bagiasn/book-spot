'use strict';

$.fn.api.settings.api = {
    'get books' : 'api/catalog/books',
    'get page' : 'api/catalog/books?page={numPage}',
    'rate book' : 'api/catalog/books/{id}'
};

$('.ui.modal')
    .modal()
;

$('#btn-load-more')
    .api({
        action: 'get page',
        on: 'click',
        beforeSend: function(settings) {
            settings.urlData = {
                numPage: parseInt(sessionStorage.nextPage)
            };
            return settings;
        },
        onSuccess: function (response) {
            loadBooks(response);
        },
        onError: function (m) {
            console.log(m);
        }
    });

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
    const books = response["content"];

    books.forEach(book => {
        let card = document.createElement("div");
        let header = document.createElement("div");
        let content = document.createElement("div");
        let extra = document.createElement("div");
        let meta = document.createElement("div");
        let rating = document.createElement("div");
        let ratingAttr = document.createAttribute("data-rating");
        let maxAttr = document.createAttribute("data-max-rating");
        let image = document.createElement("div");
        let img = document.createElement("img");

        // Add classes
        card.classList.add( 'teal', 'card');
        image.classList.add('ui', 'small', 'centered', 'rounded', 'image');
        content.className = 'content';
        header.className = 'header';
        meta.className = 'meta';
        extra.className = 'extra';
        rating.classList.add('ui', 'star', 'rating');

        // Populate element content
        header.innerText = book.title;
        img.src = `http://covers.openlibrary.org/b/id/${book.cover_id}-M.jpg`;
        img.alt = 'Book cover image';
        meta.innerText = book.category_name;
        extra.innerText = 'Rating: ';
        ratingAttr.value = book.rating;
        maxAttr.value = "5";
        // Add element hierarchy
        extra.appendChild(rating);
        content.appendChild(header);
        content.appendChild(meta);
        image.appendChild(img);

        // Add event listeners
        card.addEventListener("click", function () {
            let modalImg = document.getElementById("modal-img");
            modalImg.src = img.src;
            let modalDesc = document.getElementById("modal-desc");
            modalDesc.innerText = book.description;
            let modalHeader = document.getElementById("modal-header");
            modalHeader.innerText = header.innerText;

            var ratingBar = $('.actions .rating');
            ratingBar
                .rating('set rating', rating.getAttribute("data-rating"))
                .popup({
                    on: 'click'
                })
                .rating('setting', 'onRate', function (value) {
                    ratingBar.api({
                        action: 'rate book',
                        on: 'now',
                        method: 'PATCH',
                        beforeXHR: (xhr) => {
                            xhr.setRequestHeader('Content-Type', 'application/json');
                        },
                        urlData: {
                            id: book.id
                        },
                        data : JSON.stringify({rating: parseInt(value)})
                    })
                });

            $('.ui.modal')
                .modal({
                    centered: false
                })
                .modal('show')
            ;
        });
        card.appendChild(image);
        card.appendChild(content);
        card.appendChild(extra);
        rating.setAttributeNode(ratingAttr);
        rating.setAttributeNode(maxAttr);
        // Add to container
        const cards = document.getElementsByClassName('ui five doubling link cards')[0];
        cards.appendChild(card);
    });

    sessionStorage.nextPage = parseInt(response["number"]) + 1;

    $('.card .rating').rating('disable');
}