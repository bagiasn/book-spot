'use strict';

$.fn.api.settings.api = {
    'get all books' : 'api/books'
};

$('.container .header.item')
    .api({
        action: 'get all books',
    onSuccess: function(response) {
        console.log(response);
    },
    onFailure: function(response) {
        console.log(response);
    },
    onError: function(errorMessage) {
        console.log(errorMessage);
    }
    });