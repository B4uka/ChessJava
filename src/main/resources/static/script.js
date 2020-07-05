var state = true;
var possibleActions;
var currentPiece;
var currentCellToMove;
var currentCell;
var cells;

$(document).ready(function () {
    updateDisplay();
});

$(".ChessCell").click(function () {
    if (state == true && this.innerHTML != "") {
        while (state == true && this.innerHTML != "") {
            state = false;
            select($(this).attr('id'));
        }
    } else if (state == true && this.innerHTML == "") {
    } else {
        move($(this).attr('id'));
        state = true;
    }
});

function select(field_id) {
    $("td").css({
        'background': '',
        'border': 'solid 1px black'
    });
    currentCell = field_id;
    currentPiece = document.getElementById(currentCell).innerHTML;

    $.ajax({
        url: 'selection/',
        type: 'PUT',
        data: {player: '1', fieldId: field_id},
    }).done(function (fieldsArray, status, xhr) { // success callback function
        $.each(fieldsArray, function (index, fieldId) {
            $("#" + fieldId).css({
                'background': '#9dab86'
            });
            $("#" + field_id).css({
                'border': 'double 7px #fddb3a'
            });
            $('#info').empty();
        }, 'json')
    }).fail(function (data, status) {
        state = true;
        $("td").css({
            'background': '',
            'border': 'solid 1px black'
        });
        $('#info').empty();
        jQuery('<div/>', {
            text: "It's not your move!",
            class: 'alert alert-danger'
        }).appendTo('.information.text-center');
    });
}

function move(field_id) {
    $("td").css({
        'background': '',
        'border': 'solid 1px black'
    });

    $.post("/move", // url
        {
            player: 1,
            fieldId: field_id
        }, // data to be submit
        function (fieldsArray, status, xhr) { // success callback function
            mate();
            var response = {"test": "test1"};
            $.each(fieldsArray, function (fieldId, codeOfPiece) {
                $("#" + fieldId).html(codeOfPiece);
                $('#info').empty();
            });
        }, 'json')
        .fail(function (data, status) {
            $('#info').empty();
            jQuery('<div/>', {
                text: "You can't move there!",
                class: 'alert alert-danger'
            }).appendTo('.information.text-center');
            $("td").css({
                'background': '',
                'border': 'solid 1px black'
            });
            state = true;
        });
}

function updateDisplay() {
    $("td").css({
        'background': '',
        'border': 'solid 1px black'
    });
    $.post("/actualBoard",
        {
            player: 1,
        },
        function (fieldsArray, status, xhr) {
            $('#info').empty();
            var response = {"test": "test1"};
            $.each(fieldsArray, function (fieldId, codeOfPiece) {
                $("#" + fieldId).html(codeOfPiece);
            });
        }, 'json')
        .fail(function (data, status) {
            alert("error2: " + status);
        });
}

function newGame() {
    $("td").css({
        'background': '',
        'border': 'solid 1px black'
    });
    $.post("/newGame",
        {
            player: 1,
        },
        function (fieldsArray, status, xhr) {
            $('#info').empty();
            var response = {"test": "test1"};
            $.each(fieldsArray, function (fieldId, codeOfPiece) {
                $("#" + fieldId).html(codeOfPiece);
            });
        }, 'json')
        .fail(function (data, status) {
            alert("error2: " + status);
        });
}

function mate() {
    $("td").css({
        'background': '',
        'border': 'solid 1px black'
    });
    $.post("/mate",
        {
            player: 1,
        },
        function (mateOrStalemate, status, xhr) {
            if (mateOrStalemate == "true") {
                window.open('images/mate.jpg');
            } else if (mateOrStalemate == "stalemate") {
                window.open('images/stalemate.jpg');
            }
            ;
        }, 'json')
        .fail(function (data, status) {
            alert("error2: " + status);
        });
}

function showLiveStreamers() {
    $.get("/streamer", function (data, status) {
        $.each(data, function (i, item) {
            if (item.title == null) {
                item.title = " untitled";
            }
            let node = document.createElement("LI");
            $('<a>', {
                text: "lichess.org/streamer/" + item.id + ", chess title: " + item.title,
                href: "https://lichess.org/streamer/" + item.id,
            }).appendTo(node)
            document.getElementById("streamer").appendChild(node);
            disableButton();
        });
    });
}

function disableButton(){
    document.getElementById("switch").disabled = true;
}