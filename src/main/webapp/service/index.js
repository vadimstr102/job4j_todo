$(document).ready(
    function () {
        getItems();
        $('#showAll').change(function () {
            getItems();
        });
    }
);

function getItems() {
    $.ajax({
        type: 'GET',
        url: '/job4j_todo/item.do',
        dataType: 'json',
        success: function (data) {
            let showAll = $('#showAll').is(':checked');
            let count = 1;
            let checked;
            let str = "";
            for (let item of data) {
                if (item.done) {
                    if (!showAll) {
                        continue;
                    }
                    checked = "checked disabled";
                } else {
                    checked = "";
                }
                str +=
                    `<tr>` +
                    `<th scope="row">${count++}</th>` +
                    `<td>${item.description}</td>` +
                    `<td>${item.user.name}</td>` +
                    `<td>${item.created}</td>` +
                    `<td class="text-center">` +
                    `<input class="form-check-input" type="checkbox" ${checked} onclick="update(${item.id})">` +
                    `</td>` +
                    `</tr>`;
            }
            $('table tbody').html(str);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

function validateAndCreate() {
    let description = $('#description').val();

    if (description === "") {
        alert($('#description').attr('title'));
    } else {
        $.ajax({
            type: 'POST',
            url: '/job4j_todo/item.do',
            data: JSON.stringify({
                description: description
            }),
            async: false,
            error: function (err) {
                console.log(err);
            }
        });
    }
    getItems();
}

function update(id) {
    $.ajax({
        type: 'POST',
        url: '/job4j_todo/item.do',
        data: JSON.stringify({
            id: id
        }),
        async: false,
        error: function (err) {
            console.log(err);
        }
    });
    getItems();
}
