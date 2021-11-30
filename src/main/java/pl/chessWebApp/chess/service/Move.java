package pl.chessWebApp.chess.service;

import com.google.gson.Gson;
import pl.chessWebApp.chess.model.ChessGame;
import pl.chessWebApp.chess.communication.field.Field;
import pl.chessWebApp.chess.model.piece_properties.Position;

import java.util.HashMap;
import java.util.Optional;

public class Move {
    private ChessGame chessGame;

    public Move(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public Optional<String> makeMove(String fieldId) {

        Position positionWhereWeWantToMove = new Position(Field.valueOf(fieldId).getRow(), Field.valueOf(fieldId).getColumn());
        boolean isMovePossible = chessGame.movePieceAndSwitchColor(positionWhereWeWantToMove.getRow(), positionWhereWeWantToMove.getColumn());

        if (!isMovePossible) {
            return Optional.empty();
        }
        chessGame.getBoard().getBoardFieldAndCodes();
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>(chessGame.getBoard().getBoardFieldAndCodes());

        return Optional.ofNullable(new Gson().toJson(codeOfTheFieldsWithPiecesOnThem));
    }
}
