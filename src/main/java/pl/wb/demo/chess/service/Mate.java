package pl.wb.demo.chess.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import pl.wb.demo.chess.model.ChessGame;

@Slf4j
public class Mate {
    private ChessGame chessGame;
    private String isMated;

    public Mate(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public String mate() {
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
