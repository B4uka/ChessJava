package pl.chessWebApp.chess.service;

import com.google.gson.Gson;

import pl.chessWebApp.chess.model.ChessGame;
import pl.chessWebApp.chess.model.piece_properties.Color;

import java.util.HashMap;

public class NewGame {

    private ChessGame chessGame;

    public NewGame (ChessGame chessGame){
        this.chessGame = chessGame;
    }

    public String newGame(int player) {
        chessGame.getBoard().clearBoardAndSetPieceOnStartingPositions();
        chessGame.setWhoIsUpToMove(Color.WHITE);
        new ChessGame();
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>(chessGame.getBoard().getBoardFieldAndCodes());
        return new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
    }
}
