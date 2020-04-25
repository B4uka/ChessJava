package pl.wb.demo.chess.controller.ResponseEntity;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.piece_properties.Color;

import java.util.ArrayList;

public class Mate {
    private ChessGame chessGame;
    private Boolean whitePlayer;

    public Mate(ChessGame chessGame, Boolean whitePlayer){
        this.chessGame = chessGame;
        this.whitePlayer = whitePlayer;
    }

    public ResponseEntity<String> mate(){
        ArrayList<String> isMated = new ArrayList<>();

        if (whitePlayer) {
            boolean isWhiteMated = chessGame.isKingMated(Color.WHITE);
            if (isWhiteMated) {
                isMated.add("true");
            } else isMated.add("false");
        } else {
            boolean isBlackMated = chessGame.isKingMated(Color.BLACK);
            if (isBlackMated) {
                isMated.add("true");
            } else isMated.add("false");
        }
        if (ChessGame.isStalemate) {
            isMated.add("stalemate");
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(isMated);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
}
