package pl.wb.demo.chess.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wb.demo.chess.communication.field.Field;
import pl.wb.demo.chess.service.*;
import pl.wb.demo.chess.model.ChessGame;

@RestController
@Slf4j
public class MainController {

    private ChessGame chessGame = new ChessGame();

    @PutMapping(value = {"/selection"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> selection(@RequestParam int player, @RequestParam("fieldId") String fieldId) {

        log.info("Selected " + chessGame.getBoard().getPieceNameByFieldID(Field.valueOf(fieldId)) + " on " + Field.valueOf(fieldId));

        Selection selection = new Selection(chessGame);
        String jsonResponse = selection.select(player, fieldId);
        if (jsonResponse.equals("It is not your move or you already lost!")) {
            return ResponseEntity
                    .status(403)
                    .header("Access-Control-Allow-Origin", "*")
                    .body(jsonResponse);
        }

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "*")
                .body(jsonResponse);
    }

    @PostMapping(value = {"/move"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> move(@RequestParam int player, @RequestParam String fieldId) throws Exception {

        Move nextMove = new Move(chessGame);
        log.info("Moved to " + fieldId);

        String jsonResponse = nextMove.makeMove(player, fieldId);
        if (jsonResponse.equals("You cant make that move!")) {
            return ResponseEntity.status(403).
                    header("Access-Control-Allow-Origin", "*").
                    body("You cant make that move!");
        }
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "*")
                .body(jsonResponse);
    }

    @PostMapping(value = {"/mate"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> isMated(@RequestParam int player) {

        Mate test = new Mate(chessGame);

        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .body(test.mate());
    }

    @PostMapping(value = {"/actualBoard"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> actualBoard(@RequestParam int player) throws Exception {

        ActualPositionOnBoard actualPositionOnBoard = new ActualPositionOnBoard(chessGame);

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "*")
                .body(actualPositionOnBoard.getActualPiecesPositions(player));
    }

    @PostMapping(value = {"/newGame"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newGame(@RequestParam int player) throws Exception {

        NewGame nextGame = new NewGame(chessGame);
        log.info("New Game had started");

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "*")
                .body(nextGame.newGame(player));
    }
}