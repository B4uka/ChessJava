package pl.chessWebApp.chess.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import pl.chessWebApp.chess.model.ChessGame;
import pl.chessWebApp.chess.communication.field.Field;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;

import java.util.ArrayList;

@Slf4j
public class Selection {
    private ChessGame chessGame;

    public Selection (ChessGame chessGame){
        this.chessGame = chessGame;
    }

    public String select(int player, String fieldId) {
        log.info("processing " + fieldId);

        ArrayList<String> fieldsToMark = new ArrayList<>();
        ArrayList<Position> allPossibleActions = new ArrayList<>();
        Field selectedPiece = Field.valueOf(fieldId);
        Color selectedPieceColor = chessGame.getBoard().getColorFromTheBoardOnCurrentPosition(new Position(selectedPiece.getRow(), selectedPiece.getColumn()));

        if (!chessGame.selectPiece(selectedPiece.getRow(), selectedPiece.getColumn(), selectedPieceColor)) {
            return "It is not your move or you already lost!";
        }

        ArrayList<Position> possibleMovesToMake = chessGame.getPossibleActions().getPossibleMoves();
        ArrayList<Position> possibleCapturesToMake = chessGame.getPossibleActions().getPossibleCaptures();
        ArrayList<Position> possibleCastling = chessGame.getPossibleActions().getKingCastlingActions();

        allPossibleActions.addAll(possibleCapturesToMake);
        allPossibleActions.addAll(possibleMovesToMake);
        allPossibleActions.addAll(possibleCastling);

        for (Position position : allPossibleActions) {
            fieldsToMark.add(Field.getFieldByPosition(position.getRow(), position.getColumn()));
        }
        return  new Gson().toJson(fieldsToMark);
    }
}
