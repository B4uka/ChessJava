package pl.chessWebApp.chess.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.chessWebApp.chess.communication.field.Field;
import pl.chessWebApp.chess.model.ChessGame;
import pl.chessWebApp.chess.service.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static pl.chessWebApp.chess.ResponseHadler.ChessResponseEntityHandler.*;

@CrossOrigin(origins = "Access-Control-Allow-Origin")
@Slf4j
@RestController
@RequestMapping(path = "/",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        method = {RequestMethod.GET, RequestMethod.POST})
public class MainController {

    private ChessGame chessGame = new ChessGame();

    @PutMapping(value = {"/selection"})
    public ResponseEntity<String> selectPieceForNextMove (@RequestParam @NotNull Integer player,
                                                          @RequestParam("fieldId") @NotBlank String fieldId) {
        log.info("Selected " + chessGame.getBoard().getPieceNameByFieldID(Field.valueOf(fieldId)) + " on " + Field.valueOf(fieldId));

        Selection selection = new Selection(chessGame);
        return getSelectionResponseEntity(player, fieldId, selection);
    }

    @PostMapping(value = {"/move"})
    public ResponseEntity<String> movePieceToGivenField (@RequestParam @NotBlank String fieldId) {
        Move nextMove = new Move(chessGame);
        log.info("Moved to " + fieldId);
        return getResponseForMoveMethod(fieldId, nextMove);
    }

    @PostMapping(value = {"/mate"})
    public ResponseEntity<String> evaluateMateOnTheBoard () {
        Mate test = new Mate(chessGame);
        return responseOk(test.mate());
    }

    @PostMapping(value = {"/actualBoard"})
    public ResponseEntity<String> actualBoard (@RequestParam @NotNull Integer player) {
        ActualPositionOnBoard actualPositionOnBoard = new ActualPositionOnBoard(chessGame);
        return responseOk(actualPositionOnBoard.getActualPiecesPositions(player));
    }

    @PostMapping(value = {"/newGame"})
    public ResponseEntity<String> startNewGame (@RequestParam @NotNull Integer player) {
        NewGame nextGame = new NewGame(chessGame);
        log.info("New game started");
        return responseOk(nextGame.newGame(player));
    }
}