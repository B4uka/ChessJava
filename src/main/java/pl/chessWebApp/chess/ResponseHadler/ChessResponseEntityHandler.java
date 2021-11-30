package pl.chessWebApp.chess.ResponseHadler;

import org.springframework.http.ResponseEntity;
import pl.chessWebApp.chess.service.Move;
import pl.chessWebApp.chess.service.Selection;

import java.util.Optional;

public final class ChessResponseEntityHandler {

    public static ResponseEntity<String> getResponseForMoveMethod (String fieldId, Move nextMove) {
        Optional<String> jsonResponseOpt = nextMove.makeMove(fieldId);
        if (jsonResponseOpt.isEmpty()) {
            return ResponseEntity
                    .status(403)
                    .body("You cant make that move!");
        } else {
            return responseOk(jsonResponseOpt.get());
        }
    }

    public static ResponseEntity<String> responseOk (String response) {
        return ResponseEntity.ok().body(response);
    }

    public static ResponseEntity<String> getSelectionResponseEntity (int player, String fieldId, Selection selection) {
        Optional<String> jsonResponseOpt = selection.select(player, fieldId);
        if (jsonResponseOpt.isEmpty()) {
            return ResponseEntity
                    .status(403)
                    .body("It is not your move or you already lost!");
        } else {
            return ResponseEntity
                    .ok()
                    .body(jsonResponseOpt.get());
        }
    }
}
