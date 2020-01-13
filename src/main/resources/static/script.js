var state = true;
var possibleActions;
var currentPiece;
var currentCellToMove;
var currentCell;

//SELECTION

$("td").click(function() {
  if (state == true && this.innerHTML != "") {
    state = false;
    select($(this).attr('id'));
  }
  else if (state == true && this.innerHTML == ""){
  }
  else {
    move($(this).attr('id'));
    state = true;
  }
});

function select(field_id) {
  $("td").css("background", "");
  currentCell = field_id;
  currentPiece = document.getElementById(currentCell).innerHTML;


  $.post("http://195.181.247.79:8087/chessWebApp/selection", // url
      {
        player: 1,
        fieldId: field_id
      }, // data to be submit
      function(fieldsArray, status, xhr) { // success callback function
        alert("status: " + status);
        $.each(fieldsArray, function(index, fieldId) {
            $("#" + fieldId).css("background", "lightGray");
        });
      }, 'json')
    .fail(function(data, status) {
      alert("error1 " + status);
      state = true;
    });
}

//MOVING PIECES

function move(field_id) {
  $("td").css("background", "");

  $.post("http://195.181.247.79:8087/chessWebApp/move", // url
      {
        player: 1,
        fieldId: field_id
      }, // data to be submit
      function(fieldsArray, status, xhr) { // success callback function
        alert("status2: " + status);
        if (fieldsArray.includes(field_id)){
          $.each(fieldsArray, function(index, field_id) {
              $("#" + field_id).html(currentPiece);
              $("#" + currentCell).html("");
            });
          }
      }, 'json')
    .fail(function(data, status) {
      alert("error2: " + status);
      state = true;
    });
}

// var state = false; //false if no piece has been selected
// var currentPiece;
// var currentCell;

// var cells = document.getElementsByTagName("td");
// for (var i = 0; i < cells.length; i++) {
//   cells[i].onclick = function() {
//     getCell(this);
//   };
// }
//
//
// function getCell(that) {
//   if (!state2) { //this means if the state is false (i.e. no piece selected
//     state2 = true; //piece has been selected
//     currentPiece = that.innerHTML; //get the current piece selected
//     currentCell = that; //get the current cell selection
//     // alert($(that).attr('id'));
//   } else { //else, you are moving a piece
//     that.innerHTML = currentPiece; //Set the selected space to the piece that was grabbed
//     currentCell.innerHTML = ""; //remove the piece from its old location
//     // alert($(that).attr('id'));
//     state2 = false; //piece has been placed, so set state back to false
//   }
// }
// $("td").click(function() {
//   select($(this).attr('id'));
// });
