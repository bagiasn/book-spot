'use strict';

window.onload = function() {
    let token = localStorage.getItem('userToken');
    let username = localStorage.getItem('userEmail');

    if (token !== null && token !== ''
        && username !== null && username !== '') {
        $('.ui.menu .logout').show();
    } else {
        $('.ui.menu .login').show();
        $('.ui.menu .sign-up').show();
    }

    sessionStorage.nextPage = 0;
    sessionStorage.selectedFilter = '';

    $.fn.api.settings.api = {
        'get books': 'api/catalog/books/search/{value}?page={numPage}&name={/name}',
        'search'   : 'api/catalog/books/search/byTitle?title={query}',
        'rate book': 'api/catalog/books/{id}',
        'sign up'  : 'api/user/signup',
        'login'    : 'api/user/login',
        'logout'   : 'api/user/logout'
    };

    let webSocket = new WebSocket('ws://localhost:8585/listen');

    webSocket.onmessage = function (event) {
        $('.ui.announcement span').fadeOut(function() {
            $(this).text(event.data).fadeIn();
        });
    };

    $('.ui.modal')
        .modal();

    $('.ui.search')
        .search({
            minCharacters: 4,
            maxResults: 5,
            showNoResults: true,
            searchDelay: 300,
            type : 'category',
            apiSettings: {
                onResponse: function (apiResponse) {
                    let response = { results : {}};

                    $.each(apiResponse._embedded.books, function (index, book) {
                        let categoryName = book._embedded.category.name;
                        if(response.results[categoryName] === undefined) {
                            response.results[categoryName] = {
                                name    : categoryName,
                                results : []
                            };
                        }

                        response.results[categoryName].results.push({
                            title : book.title,
                            description : book._embedded.author.name,
                            fullBook : JSON.stringify(book)
                        });
                    });

                    return response;
                }
            },
            onSelect: function (result) {
                // We are storing the whole object in a property to avoid querying again.
                let book = JSON.parse(result.fullBook);
                showBook(book);
                // Clear search text.
                $(this).search('set value', '');

                return false;
            }
        });

    $('.ui.dropdown.filter')
        .dropdown({
            allowReselection: false,
            onChange: function (value, text) {
                let selectedFilter = sessionStorage.selectedFilter;
                if (selectedFilter === '' || selectedFilter !== text) {
                    sessionStorage.selectedFilter = text;
                    sessionStorage.nextPage = 0;

                    $(this).api({
                        action: 'get books',
                        on: 'now',
                        beforeSend: function(settings) {
                            settings.urlData = {
                                numPage: parseInt(sessionStorage.nextPage),
                                name: value.includes("Category") ? text : ""
                            };
                            return settings;
                        },
                        onSuccess: function (response) {
                            clearBooks();
                            loadBooks(response);
                        },
                        onError: function (m) {
                            console.log(m);
                        }
                    });
                }
            }
        });

    $('.ui.dropdown.filter').dropdown('set selected', 'byYearDesc');

    $('.ui.menu .logout')
        .api({
            action: 'logout',
            method: 'POST',
            loadingDuration: 500,
            contentType: 'application/json',
            dataType: 'text',
            beforeSend: function (settings) {
                settings.data = getCredentials();
                return settings;
            },
            onSuccess: function () {
                console.log("Logout success");
                onLogoutSuccess();
                $(this).hide();
            }
        });

    $('.ui.form.sign-up')
        .form({
            fields: {
                username : ['maxLength[12]', 'empty'],
                password : ['minLength[5]', 'empty'],
                email:  ['minLength[3]', 'empty'],
                first_name: 'maxLength[24]',
                last_name: 'maxLength[24]'
            },
            inline : true,
            on     : 'blur'
        })
        .api({
                action: 'sign up',
                on: 'submit',
                method: 'POST',
                loadingDuration: 800,
                dataType: 'text',
                contentType: 'application/json',
                beforeSend: function (settings) {
                    settings.data = getFormData('sign-up');
                    return settings;
                },
                onSuccess: function () {
                    showFormSuccess('sign-up');
                    $(this).form('clear');
                },
                onError: function (message, element, xhr) {
                    showFormError(message, element, xhr, 'sign-up');
                }
            }
        );

    $('.ui.form.login')
        .form({
            fields: {
                password : ['minLength[5]', 'empty'],
            },
            inline : true,
            on     : 'blur'
        })
        .api({
                action: 'login',
                on: 'submit',
                method: 'POST',
                loadingDuration: 800,
                dataType: 'text',
                contentType: 'application/json',
                beforeSend: function (settings) {
                    settings.data = getFormData('login');
                    return settings;
                },
                onSuccess: function (response) {
                    onLoginSuccess(response);

                    $(this).form('clear');
                },
                onError: function (message, element, xhr) {
                    showFormError(message, element, xhr, 'login');
                }
            }
        );

    $('.ui.button.sign-up')
        .click(function () {
            $('.ui.modal.sign-up')
                .modal({
                    onHide: function () {
                        hideMessage('sign-up');
                        $(this).form('reset');
                    },
                    centered: false
                })
                .modal('show')
            ;
        });

    $('.ui.button.login')
        .click(function () {
            $('.ui.modal.login')
                .modal({
                    onHide: function () {
                        hideMessage('login');
                        $(this).form('reset');
                    },
                    centered: false
                })
                .modal('show')
            ;
        });

    $('.ui.button.load-more')
        .api({
            action: 'get books',
            on: 'click',
            beforeSend: function(settings) {
                settings.urlData = {
                    numPage: parseInt(sessionStorage.nextPage),
                    value: $('.ui.dropdown.filter').dropdown('get value'),
                    name: $('.ui.dropdown.filter').dropdown('get text')
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
};

function getFormData(formName) {
    let formData = new FormData($('.ui.form.'.concat(formName))[0]);
    console.log(formData);
    return  JSON.stringify(Object.fromEntries(formData));
}

function loadBooks(payload) {
    const books = payload["_embedded"]["books"];

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
        meta.innerText = book._embedded.category.name;
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
            showBook(book);
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

    sessionStorage.nextPage = parseInt(sessionStorage.nextPage) + 1;

    $('.card .rating').rating('disable');
}

function onLoginSuccess(response) {
    let credentials = JSON.parse(response);

    localStorage.setItem('userToken', credentials.token);
    localStorage.setItem('userEmail', credentials.email);

    showFormSuccess('login');

    $('.ui.menu .login').hide();
    $('.ui.menu .sign-up').hide();
    $('.ui.menu .logout').show();
}

function onLogoutSuccess() {
    localStorage.clear();

    $('.ui.menu .login').show();
    $('.ui.menu .sign-up').show();
}

function getCredentials() {
    let userEmail = localStorage.getItem('userEmail');
    let userToken = localStorage.getItem('userToken');
    console.log("Got " + userToken );
    return JSON.stringify({email: userEmail, token: userToken});
}

function showFormError(errorMessage, el, xhr, formName) {
    console.log(errorMessage);

    let message = $('.ui.message.'.concat(formName))[0];

    if (formName === 'sign-up') {
        if (xhr.status === 409) {
            message.innerHTML = 'This email already exists!';
        } else {
            message.innerHTML = errorMessage;
        }
    } else if (formName === 'login') {
        switch (xhr.status) {
            case 401:
                message.innerHTML = 'The password you entered is incorrect';
                break;
            case 404:
                message.innerHTML = 'The email you entered was not found';
                break;
            default:
                message.innerHTML = errorMessage;
                break;
        }
    }

    message.classList.remove('success');
    message.classList.add('error');
    message.classList.replace('hidden', 'visible');
}

function showFormSuccess(formName) {
    let message = $('.ui.message.'.concat(formName))[0];

    if (formName === 'sign-up') {
        message.innerHTML = 'Sign up completed! You can now log in and explore BookSpot.';
    } else {
        message.innerHTML = 'You are now logged-in.';
    }

    message.classList.replace('hidden', 'visible');
    message.classList.remove('error');
    message.classList.add('success');
}

function hideMessage(formName) {
    let message = $('.ui.message.'.concat(formName))[0];

    message.classList.replace('visible', 'hidden');
    message.classList.remove('success');
    message.classList.remove('error');
}

function clearBooks() {
    $('.ui.link.cards').empty();
}

function showBook(book) {
    let modalImg = document.getElementById("modal-img");
    modalImg.src = `http://covers.openlibrary.org/b/id/${book.cover_id}-M.jpg`;
    let modalDesc = document.getElementById("modal-desc");
    modalDesc.innerText = book.description;
    let modalHeader = document.getElementById("modal-header");
    modalHeader.innerText = book.title;

    let info = $('.ui.table.book-info')[0].tBodies[0].rows[0].cells;
    info[0].innerHTML = book._embedded.author.name;
    info[1].innerHTML = book._embedded.category.name;
    info[2].innerHTML = book._embedded.publisher.name;
    info[3].innerHTML = book.publication_year;
    info[4].innerHTML = book.page_count;

    let bookUri = book._links.self.href;
    let bookId = bookUri.substring(bookUri.lastIndexOf('/')  + 1);

    let ratingBar = $('.actions .rating');

    let token = localStorage.getItem('userToken');
    let username = localStorage.getItem('userEmail');

    if (token !== null && token !== '' && username !== null && username !== '') {
        ratingBar
            .rating('set rating', book.rating)
            .rating('enable')
            .popup({
                on: 'click'
            })
            .api({
                action: 'rate book',
                method: 'PATCH',
                contentType: 'application/json',
                urlData: {
                    id: bookId
                },
                beforeSend: function(settings) {
                    settings.data = JSON.stringify({rating: parseInt($(this).rating('get rating'))});
                    return settings;
                }
            });
    } else {
        ratingBar
            .rating('set rating', book.rating)
            .rating('disable')
            .popup('destroy');
    }

    $('.ui.modal.book')
        .modal({
            centered: false
        })
        .modal('show')
    ;
}