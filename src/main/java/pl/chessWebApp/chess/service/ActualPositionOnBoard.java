package pl.chessWebApp.chess.service;

import com.google.gson.Gson;
import pl.chessWebApp.chess.model.ChessGame;

import java.util.HashMap;

public class ActualPositionOnBoard {

    private ChessGame chessGame;

    public ActualPositionOnBoard(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public String getActualPiecesPositions(int player) throws Exception {

        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();
        chessGame.getBoard().getBoard();
        codeOfTheFieldsWithPiecesOnThem.putAll(chessGame.getBoard().getBoardFieldAndCodes());

        return new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
    }
}
