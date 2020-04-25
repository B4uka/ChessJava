var state = true;
var possibleActions;
var currentPiece;
var currentCellToMove;
var currentCell;
var cells;

$(document).ready(function() {
    updateDisplay();
});

$(".ChessCell").click(function() {
  if (state == true && this.innerHTML != "") {
    while (state == true && this.innerHTML != "") {
        state = false;
        select($(this).attr('id'));
    }
  }
  else if (state == true && this.innerHTML == ""){
  }
  else {
    move($(this).attr('id'));
    state = true;
  }
});

function select(field_id) {
  $("td").css({'background' : '',
                'border' : 'solid 1px black'
  });

  currentCell = field_id;
  currentPiece = document.getElementById(currentCell).innerHTML;

  $.post("http://195.181.247.79:8087/chessWebApp/selection", // url
      {
        player: 1,
        fieldId: field_id
      }, // data to be submit
      function(fieldsArray, status, xhr) { // success callback function
        $.each(fieldsArray, function(index, fieldId) {
            $("#" + fieldId).css({
                                'background' : '#9dab86'
             });
             $("#" + field_id).css({
                                   'border' : 'double 7px #fddb3a'
                          });
             $('#info').empty();
        });
      }, 'json')
    .fail(function(data, status) {
        state = true;
        $("td").css({'background' : '',
                      'border' : 'solid 1px black'
        });
        $('#info').empty();
        jQuery('<div/>', {
                text: "It's not your move!",
                class: 'alert alert-danger'
        }).appendTo('.information.text-center');
    });
}

function move(field_id) {
    $("td").css({'background' : '',
                  'border' : 'solid 1px black'
    });

  $.post("http://195.181.247.79:8087/chessWebApp/move", // url
      {
        player: 1,
        fieldId: field_id
      }, // data to be submit
      function(fieldsArray, status, xhr) { // success callback function
             mate();
            var response =  {"test": "test1"};
            $.each(fieldsArray, function(fieldId, codeOfPiece) {
            $("#" + fieldId).html(codeOfPiece);
            $('#info').empty();
        });
      }, 'json')
    .fail(function(data, status) {
        $('#info').empty();
        jQuery('<div/>', {
            text:  "You can't move there!",
            class: 'alert alert-danger'
          }).appendTo('.information.text-center');
        $("td").css({'background' : '',
                      'border' : 'solid 1px black'
        });
      state = true;
    });
}

function updateDisplay() {
  $("td").css({'background' : '',
                'border' : 'solid 1px black'
  });
  $.post("http://195.181.247.79:8087/chessWebApp/actualBoard",
      {
         player: 1,
      },
      function(fieldsArray, status, xhr){
          $('#info').empty();
          var response =  {"test": "test1"};
          $.each(fieldsArray, function(fieldId, codeOfPiece) {
          $("#" + fieldId).html(codeOfPiece);
         });
    }, 'json')
  .fail(function(data, status) {
    alert("error2: " + status);
  });
}

function newGame() {
  $("td").css({'background' : '',
                'border' : 'solid 1px black'
  });
  $.post("http://195.181.247.79:8087/chessWebApp/newGame",
      {
      player: 1,
      },
      function(fieldsArray, status, xhr){
          $('#info').empty();
          var response =  {"test": "test1"};
          $.each(fieldsArray, function(fieldId, codeOfPiece) {
          $("#" + fieldId).html(codeOfPiece);
         });
    }, 'json')
  .fail(function(data, status) {
    alert("error2: " + status);
  });
}

function mate() {
  $("td").css({'background' : '',
                'border' : 'solid 1px black'
  });
  $.post("http://195.181.247.79:8087/chessWebApp/mate",
      {
        player: 1,
      },
      function(mateOrStalemate, status, xhr){
          $.each(mateOrStalemate, function(index, mate) {
          if (mate == "true"){
           window.open('images/mate.jpg');
          } else if (mate == "stalemate"){
            window.open('images/stalemate.jpg');
          }
         });
    }, 'json')
  .fail(function(data, status) {
    alert("error2: " + status);
  });
}