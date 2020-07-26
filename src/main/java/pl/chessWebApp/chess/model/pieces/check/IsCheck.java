package pl.chessWebApp.chess.model.pieces.check;

import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.pieces.King;

public class IsCheck implements CheckValidation {
    protected Board board;

    public IsCheck (Board board) {
        this.board = board;
    }

    @Override
    public boolean isWhiteKingChecked () {
        PieceCheckingKing positions = new PieceCheckingKing();
        King whiteKing = new King(board.getWhiteKingPosition(), Color.WHITE, "&#9812;", 0);

        PossibleActions whiteKingCheckedPositions = positions.piecesPositionsCheckingWhiteKing(board, whiteKing.getPosition());
        return !whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    @Override
    public boolean isBlackKingChecked () {
        PieceCheckingKing positions = new PieceCheckingKing();
        King blackKing = new King(board.getBlackKingPosition(), Color.BLACK, "&#9818;", 0);

        PossibleActions blackKingCheckedPositions = positions.piecesPositionsCheckingBlackKing(board, blackKing.getPosition());
        return !blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    @Override
    public boolean isKingChecked (Color color) {
        if (color == Color.WHITE) {
            return isWhiteKingChecked();
        }
        return isBlackKingChecked();
    }
}
