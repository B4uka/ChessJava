package pl.wb.demo.chess.model.pieces.MoveGenerator;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.SelectionAndMove.MoveOnTheChessBoard;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.Piece;

public class CastlingRookMovesValidation {
    protected Board board;
    protected PossibleActions possibleActions;
    protected Position piecePositionNEW, rookCastlingMove;
    protected Piece currentlySelectedRookForCastle;
    private MoveOnTheChessBoard move;

    public CastlingRookMovesValidation (Board board, PossibleActions possibleActions, Position piecePositionNEW, MoveOnTheChessBoard move) {
        this.board = board;
        this.possibleActions = possibleActions;
        this.piecePositionNEW = piecePositionNEW;
        this.move = move;
    }

    public Piece rookMoveWhenCastling () {
        Piece[] rookOnBoard = new Piece[4];
        rookOnBoard[0] = (board.getPiece(0, 0));
        rookOnBoard[1] = (board.getPiece(0, 7));
        rookOnBoard[2] = (board.getPiece(7, 0));
        rookOnBoard[3] = (board.getPiece(7, 7));

        if (this.possibleActions.getKingCastlingActions().contains(piecePositionNEW)) {
            if (piecePositionNEW.getRow() == 0 && piecePositionNEW.getColumn() == 2 && rookOnBoard[0].getCountMoves() == 0) {
                currentlySelectedRookForCastle = board.getPiece(0, 0);
                rookCastlingMove = currentlySelectedRookForCastle.getPosition();
                board.setEmptyByPosition(rookCastlingMove);
                this.currentlySelectedRookForCastle.setPosition(0, 3);
                board.setPiece(currentlySelectedRookForCastle);
                return currentlySelectedRookForCastle;
            } else move.revertNewMove(piecePositionNEW);
            if (piecePositionNEW.getRow() == 0 && piecePositionNEW.getColumn() == 6 && rookOnBoard[1].getCountMoves() == 0) {
                currentlySelectedRookForCastle = board.getPiece(0, 7);
                rookCastlingMove = currentlySelectedRookForCastle.getPosition();
                board.setEmptyByPosition(rookCastlingMove);
                this.currentlySelectedRookForCastle.setPosition(0, 5);
                board.setPiece(currentlySelectedRookForCastle);
                return currentlySelectedRookForCastle;
            } else move.revertNewMove(piecePositionNEW);
            if (piecePositionNEW.getRow() == 7 && piecePositionNEW.getColumn() == 2 && rookOnBoard[2].getCountMoves() == 0) {
                currentlySelectedRookForCastle = board.getPiece(7, 0);
                rookCastlingMove = currentlySelectedRookForCastle.getPosition();
                board.setEmptyByPosition(rookCastlingMove);
                this.currentlySelectedRookForCastle.setPosition(7, 3);
                board.setPiece(currentlySelectedRookForCastle);
                return currentlySelectedRookForCastle;
            } else move.revertNewMove(piecePositionNEW);
            if (piecePositionNEW.getRow() == 7 && piecePositionNEW.getColumn() == 6 && rookOnBoard[3].getCountMoves() == 0) {
                currentlySelectedRookForCastle = board.getPiece(7, 7);
                rookCastlingMove = currentlySelectedRookForCastle.getPosition();
                board.setEmptyByPosition(rookCastlingMove);
                this.currentlySelectedRookForCastle.setPosition(7, 5);
                board.setPiece(currentlySelectedRookForCastle);
                return currentlySelectedRookForCastle;
            } else move.revertNewMove(piecePositionNEW);
            return null;
            // System.out.println("How many times rook moved?: " + rookCountMove);
        } else if (!this.possibleActions.getKingCastlingActions().contains(piecePositionNEW)) {
            move.revertNewMove(piecePositionNEW);
            return null;
        }
        return null;
    }
}
