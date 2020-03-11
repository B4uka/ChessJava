package pl.wb.demo.chess.model.pieces.Check;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.pieces.King;

public class CheckValidation {
    protected Board board;

    public CheckValidation(Board board){
        this.board =  board;
    }

    public boolean isWhiteKingChecked () {
        CheckSearch positions = new PieceCheckingKing();
        King whiteKing = new King(board.getWhiteKingPosition(), Color.WHITE, "&#9812;", 0);

        PossibleActions whiteKingCheckedPositions = positions.piecesPositionsCheckingWhiteKing(board, whiteKing.getPosition());

        whiteKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();
        return !whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    public  boolean isBlackKingChecked () {
        CheckSearch positions = new PieceCheckingKing();
        King blackKing = new King(board.getBlackKingPosition(), Color.BLACK, "&#9818;", 0);

        PossibleActions blackKingCheckedPositions = positions.piecesPositionsCheckingBlackKing(board, blackKing.getPosition());

        blackKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();
        return !blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }
}
