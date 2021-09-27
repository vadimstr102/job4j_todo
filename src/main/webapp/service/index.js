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
            let categories = "";
            for (let item of data) {
                if (item.done) {
                    if (!showAll) {
                        continue;
                    }
                    checked = "checked disabled";
                } else {
                    checked = "";
                }
                $.each(item.categories, function (index, value) {
                    if (index === item.categories.length - 1) {
                        categories += value.name;
                    } else {
                        categories += value.name + ", ";
                    }
                });
                str +=
                    `<tr>` +
                    `<th scope="row">${count++}</th>` +
                    `<td>${item.description}</td>` +
                    `<td>${categories}</td>` +
                    `<td>${item.user.name}</td>` +
                    `<td>${item.created}</td>` +
                    `<td class="text-center">` +
                    `<input class="form-check-input" type="checkbox" ${checked} onclick="update(${item.id})">` +
                    `</td>` +
                    `</tr>`;
                categories = "";
            }
            $('table tbody').html(str);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

function validate() {
    let description = $('#description').val();
    let categories = $('#categories').val();

    if (description === "") {
        alert($('#description').attr('title'));
        return false;
    } else if (categories.length === 0) {
        alert($('#categories').attr('title'));
        return false;
    }
    return true;
}

function update(id) {
    $.get($.ajax({
        type: 'PUT',
        url: '/job4j_todo/item.do',
        data: JSON.stringify({
            id: id
        }),
        error: function (err) {
            console.log(err);
        }
    })).done(getItems());
}
