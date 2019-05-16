const doneColor = '#a0a0a0';
let filterState = 'all';
let state = {
    tasks: [],
};
let color = '#ff7583';

let token = $('meta[name=\'_csrf\']').attr('content');
let header = $('meta[name=\'_csrf_header\']').attr('content');

function addTask(task) {
    $.ajax({
        url: '/todolist.json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType: 'json',
        contentType: 'application/json',
        type: 'POST',
        data: JSON.stringify(task),
        success: function (data, textStatus) {
            console.log(data);
        },
    });
}

function updateTask(task) {
    $.ajax({
        url: `/todolist.json/${task.id}`,
        beforeSend: (xhr) => {
            xhr.setRequestHeader(header, token);
        },
        dataType: 'json',
        contentType: 'application/json',
        type: 'PATCH',
        data: JSON.stringify(task),
        success: function (data, textStatus) {
            console.log(data);
        },
    });
}

$(document).ready(function () {

    $.ajax({
        url: '/todolist.json',
    }).then((data) => {
        state = data;
        if (state.tasks.length === 0) return;
        addTasks();
    });

    function addTasks() {
        state.tasks.forEach(function (task) {
            $(`<label for=${task.id} class='task' color='${task.color}' </label>`).appendTo('.task-list');

            let listElem = $('.task:last');

            let checked = (task.status == 'true') ? 'checked' : '';
            $(`<input type='checkbox' class='task-cb' id=${task.id} ${checked}>`).hide().appendTo(listElem);

            $(`<div class="check-toggle"></div>`).appendTo(listElem);

            $(`<span>${task.body}</span>`).appendTo(listElem);

            $(`<input type='button' class='edit-btn' id=${task.id} value='Edit'>`).appendTo(listElem);

            let taskClass = (task.status == 'true') ? 'done' : 'undone';
            listElem.addClass(taskClass);

            let taskColor = (task.status == 'true') ? doneColor : task.color;
            listElem.css('background-color', taskColor);
        });
    }

    $('.color-box').on('click', function () {
        color = $(this).css('background-color');
    });

    $('#add-btn').on('click', function () {
        let task = $('#new-task').val();

        // Check if empty input
        if (task === '') {
            $('.message').text('Empty input!');
            $('.message').css('display', 'inline');
            $('.message').delay(3000).fadeOut('slow');
            return;
        }

        $('#new-task').val('');

        let container = $('.task-list');
        let id = state.tasks.length + 1;

        // Add task to html
        $(`<label for=${id} class='task' color='${color}' </label>`).appendTo(container);

        let listElem = $('.task:last');

        $(`<input type='checkbox' class='task-cb' id=${id}>`).hide().appendTo(listElem);

        $(`<div class="check-toggle"></div>`).appendTo(listElem);

        $(`<span>${task}</span>`).appendTo(listElem);

        $(`<input type='button' class='edit-btn' id=${id} value='Edit'>`).appendTo(listElem);


        listElem.css('background-color', color);

        listElem.addClass('undone');
        if (filterState === 'done') {
            listElem.hide();
        }

        // Add task to state structure
        let newTask = {
            body: task,
            color: color,
            status: false,
            id: id,
        };

        state.tasks.push(newTask);

        addTask(newTask);
    });

    $('.filter').on('change', function () {
        let filterVal = $(this).val();
        filterState = filterVal;

        switch (filterVal) {
            case 'all': {
                $('.done').show('slow');
                $('.undone').show('slow');
                break;
            }
            case 'done': {
                $('.done').show('slow');
                $('.undone').hide('slow');
                break;
            }
            case 'todo': {
                $('.done').hide('slow');
                $('.undone').show('slow');
                break;
            }
        }
    });
});

$(document).on('change', 'input:checkbox', function () {
    let id = $(this).attr('id');
    let listElem = $(`label[for='${id}']`);
    let taskColor = listElem.attr('color');
    let doneStatus = false;

    if ($(this).prop('checked')) {
        taskColor = doneColor;
        doneStatus = true;

        listElem.removeClass('undone').addClass('done');
        if (state.filter === 'todo') {
            listElem.hide('slow');
        }
    } else {
        listElem.removeClass('done').addClass('undone');
        if (state.filter === 'done') {
            listElem.hide('slow');
        }
    }

    listElem.css('background-color', taskColor);

    // Update task done status
    let task = state.tasks.find(x => x.id == id);
    task.status = doneStatus;

    updateTask(task);
});

// Edit
$(document).on('click', '.edit-btn', function () {
    let taskDesc = $(this).parent().text();
    $('#edit-box').val(taskDesc);
    $('.popup-overlay').attr('label-id', $(this).parent().attr('for'));
    $('.popup-overlay, .popup-content').addClass('active');

});

//Cancel
$(document).on('click', '#cancel-btn', function () {
    $('.popup-overlay, .popup-content').removeClass('active');
});

//Save
$(document).on('click', '#save-btn', function () {
    let labelId = $('.popup-overlay').attr('label-id');
    let listElem = $(`label[for='${labelId}']`);
    let newDesc = $('#edit-box').val();

    listElem.find('span').text(newDesc);

    let task = state.tasks.find(x => x.id == labelId);

    task.body = newDesc;

    updateTask(task);

    $('.popup-overlay, .popup-content').removeClass('active');
});