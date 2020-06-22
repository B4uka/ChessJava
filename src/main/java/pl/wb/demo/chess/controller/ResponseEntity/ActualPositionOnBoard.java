package pl.wb.demo.chess.controller.ResponseEntity;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.wb.demo.chess.model.ChessGame;

import java.util.HashMap;

public class ActualPositionOnBoard {

    private ChessGame chessGame;

    public ActualPositionOnBoard(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public ResponseEntity<String> getActualPiecesPositions(int player) throws Exception {

        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();
        chessGame.getBoard().getBoard();
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
