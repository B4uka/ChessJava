package pl.chessWebApp.chess.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import pl.chessWebApp.chess.model.ChessGame;

@Slf4j
public class Mate {
    private ChessGame chessGame;

    public Mate(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public String mate() {
        String isMated;
        if (chessGame.isKingMated()) {
            log.info("Check Mate! Game Over");
            isMated = "true";
        } else isMated = "false";

        if (ChessGame.isStalemate) {
            log.info("Draw by stalemate!");
            isMated = "stalemate";
        }

        return new Gson().toJson(isMated);
    }
}
