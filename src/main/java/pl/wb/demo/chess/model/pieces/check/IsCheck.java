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
        PieceCheckingKing positions = new PieceCheckingKing();
        King whiteKing = new King(board.getWhiteKingPosition(), Color.WHITE, "&#9812;", 0);

        PossibleActions whiteKingCheckedPositions = positions.piecesPositionsCheckingWhiteKing(board, whiteKing.getPosition());
 //       whiteKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();            row and column of piece that are checking king
        return !whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    public boolean isBlackKingChecked () {
        PieceCheckingKing positions = new PieceCheckingKing();
        King blackKing = new King(board.getBlackKingPosition(), Color.BLACK, "&#9818;", 0);

        PossibleActions blackKingCheckedPositions = positions.piecesPositionsCheckingBlackKing(board, blackKing.getPosition());
//        blackKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();            row and column of piece that are checking king
        return !blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }
}
