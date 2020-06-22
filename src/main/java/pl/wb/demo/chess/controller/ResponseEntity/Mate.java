package pl.wb.demo.chess.controller.ResponseEntity;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.wb.demo.chess.model.ChessGame;

import java.util.ArrayList;

@Slf4j
public class Mate {
    private ChessGame chessGame;
    private String isMated;

    public Mate(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public ResponseEntity<String> mate() {
        if (chessGame.isKingMated()) {
            log.info("Check Mate! Game Over");
            isMated = "true";
        } else isMated = "false";

        if (ChessGame.isStalemate) {
            log.info("Draw by stalemate!");
            isMated = "stalemate";
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(isMated);
        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(jsonResponse);
    }
}
