package pl.wb.demo.chess.controller.ResponseEntity;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.wb.demo.chess.communication.field.Field;
import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.piece_properties.Position;

import java.util.HashMap;

public class Move {
    private ChessGame chessGame;

    public Move(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public ResponseEntity<String> makeMove(int player, String fieldId) throws Exception {

        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();

        Position positionWhereWeWantToMove = new Position(Field.valueOf(fieldId).getRow(), Field.valueOf(fieldId).getColumn());
        boolean isMovePossible = chessGame.movePieceAndSwitchColor(positionWhereWeWantToMove.getRow(), positionWhereWeWantToMove.getColumn());

        if (!isMovePossible) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Access-Control-Allow-Origin", "*");
            return ResponseEntity.status(403).
                    headers(responseHeaders).
                    body("You cant make that move!");
        }

        chessGame.getBoard().getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(chessGame.getBoard().boardFieldAndCodes);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(jsonResponse);
    }
}
