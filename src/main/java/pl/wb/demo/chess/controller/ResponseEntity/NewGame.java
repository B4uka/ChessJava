package pl.wb.demo.chess.controller.ResponseEntity;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.piece_properties.Color;

import java.util.HashMap;

public class NewGame {

    private ChessGame chessGame;

    public NewGame (ChessGame chessGame){
        this.chessGame = chessGame;
    }

    public ResponseEntity<String> newGame(int player) throws Exception {
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();

        chessGame.getBoard().clearBoardAndSetPieceOnStartingPositions();
        chessGame.setWhoIsUpToMove(Color.WHITE);

        new ChessGame();
        codeOfTheFieldsWithPiecesOnThem.putAll(chessGame.getBoard().getBoardFieldAndCodes());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(jsonResponse);
    }
}
