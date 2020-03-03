package pl.wb.demo.chess.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.piece_properties.Color;

import java.util.ArrayList;

public class Mate {
    protected ChessGame chessGame;
    protected Boolean whitePlayer;

    protected Mate(ChessGame chessGame, Boolean whitePlayer){
        this.chessGame = chessGame;
        this.whitePlayer = whitePlayer;
    }

    protected ResponseEntity<String> mate(){
        ArrayList<String> isMated = new ArrayList<>();

        if (whitePlayer) {
            Boolean isWhiteMated = chessGame.isKingMated(Color.WHITE);
            if (isWhiteMated) {
                isMated.add("true");
            } else isMated.add("false");
        } else {
            Boolean isBlackMated = chessGame.isKingMated(Color.BLACK);
            if (isBlackMated) {
                isMated.add("true");
            } else isMated.add("false");
        }
        if (ChessGame.isStalemate) {
            isMated.add("stalamate");
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(isMated);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
}
