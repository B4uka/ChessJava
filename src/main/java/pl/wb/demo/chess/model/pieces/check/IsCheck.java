package pl.wb.demo.chess.model.pieces.check;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.pieces.King;

public class IsCheck implements CheckValidation {
    protected Board board;

    public IsCheck (Board board){
        this.board =  board;
    }

    public boolean isWhiteKingChecked () {
        pieceCheckingKing positions = new pieceCheckingKing();
        King whiteKing = new King(board.getWhiteKingPosition(), Color.WHITE, "&#9812;", 0);

        PossibleActions whiteKingCheckedPositions = positions.piecesPositionsCheckingWhiteKing(board, whiteKing.getPosition());

        whiteKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();
        return !whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    public boolean isBlackKingChecked () {
        pieceCheckingKing positions = new pieceCheckingKing();
        King blackKing = new King(board.getBlackKingPosition(), Color.BLACK, "&#9818;", 0);

        PossibleActions blackKingCheckedPositions = positions.piecesPositionsCheckingBlackKing(board, blackKing.getPosition());

        blackKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();
        return !blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }
}
