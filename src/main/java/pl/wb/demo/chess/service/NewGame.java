package pl.wb.demo.chess.service;

import com.google.gson.Gson;

import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.piece_properties.Color;

import java.util.HashMap;

public class NewGame {

    private ChessGame chessGame;

    public NewGame (ChessGame chessGame){
        this.chessGame = chessGame;
    }

    public String newGame(int player) throws Exception {
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();

        chessGame.getBoard().clearBoardAndSetPieceOnStartingPositions();
        chessGame.setWhoIsUpToMove(Color.WHITE);

        codeOfTheFieldsWithPiecesOnThem.putAll(chessGame.getBoard().getBoardFieldAndCodes());
        return new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
    }
}
