package pl.chessWebApp.chess.service;

import com.google.gson.Gson;
import pl.chessWebApp.chess.model.ChessGame;
import pl.chessWebApp.chess.communication.field.Field;
import pl.chessWebApp.chess.model.piece_properties.Position;

import java.util.HashMap;

public class Move {
    private ChessGame chessGame;

    public Move(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public String makeMove(int player, String fieldId) throws Exception {

        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();

        Position positionWhereWeWantToMove = new Position(Field.valueOf(fieldId).getRow(), Field.valueOf(fieldId).getColumn());
        boolean isMovePossible = chessGame.movePieceAndSwitchColor(positionWhereWeWantToMove.getRow(), positionWhereWeWantToMove.getColumn());

        if (!isMovePossible) {
            return "You can't make that move!";
        }
        chessGame.getBoard().getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(chessGame.getBoard().getBoardFieldAndCodes());

        return new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
    }
}
