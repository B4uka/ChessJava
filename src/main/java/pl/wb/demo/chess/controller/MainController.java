package pl.wb.demo.chess.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wb.demo.chess.communication.field.Field;
import pl.wb.demo.chess.controller.ResponseEntity.*;
import pl.wb.demo.chess.model.ChessGame;

@RestController
@Slf4j
public class MainController {

    private ChessGame chessGame = new ChessGame();


    @PutMapping(value = {"/selection"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> selection(@RequestParam int player, @RequestParam("fieldId") String fieldId) {

        log.info("Selected " + chessGame.getBoard().getPieceNameByFieldID(Field.valueOf(fieldId)) + " on " + Field.valueOf(fieldId));
        Selection selection = new Selection(chessGame);

        return selection.select(player, fieldId);
    }

    @PostMapping(value = {"/move"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> move(@RequestParam int player, @RequestParam String fieldId) throws Exception {

        Move nextMove = new Move(chessGame);
        log.info("Moved to " + fieldId);
        return nextMove.makeMove(player, fieldId);
    }

    @PostMapping(value = {"/mate"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> isMated(@RequestParam int player) {

        Mate test = new Mate(chessGame);
        return test.mate();
    }

    @PostMapping(value = {"/actualBoard"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> actualBoard(@RequestParam int player) throws Exception {

        ActualPositionOnBoard actualPositionOnBoard = new ActualPositionOnBoard(chessGame);
        return actualPositionOnBoard.getActualPiecesPositions(player);
    }

    @PostMapping(value = {"/newGame"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newGame(@RequestParam int player) throws Exception {

        NewGame nextGame = new NewGame(chessGame);
        log.info("New Game had started");
        return nextGame.newGame(player);
    }
}