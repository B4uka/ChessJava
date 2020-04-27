package pl.wb.demo.chess.controller.ResponseEntity;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.wb.demo.chess.model.ChessGame;

import java.util.ArrayList;

public class Mate {
    private ChessGame chessGame;

    public Mate (ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public ResponseEntity<String> mate () {
        ArrayList<String> isMated = new ArrayList<>();

        if (chessGame.isKingMated()) {
            isMated.add("true");
        } else isMated.add("false");

        if (ChessGame.isStalemate) {
            isMated.add("stalemate");
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(isMated);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
}
