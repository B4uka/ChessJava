package pl.chessWebApp.chess.model.board.SelectionAndMove;

import lombok.Getter;
import lombok.Setter;
import pl.chessWebApp.chess.model.ChessGame;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;
import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.pieces.King;
import pl.chessWebApp.chess.model.pieces.MoveGenerator.CastlingRookMovesValidation;
import pl.chessWebApp.chess.model.pieces.Piece;
import pl.chessWebApp.chess.model.pieces.check.CheckValidation;
import pl.chessWebApp.chess.model.pieces.check.IsCheck;

@Getter
@Setter
public class MoveOnTheChessBoard {
    private Selection playersSelection;
    private Piece currentlySelected, temporaryBeaten;
    private Position piecePositionOLD, piecePositionNEW;
    private Board board;
    private PossibleActions possibleActions;

    public MoveOnTheChessBoard() {
    }

    public MoveOnTheChessBoard(Selection playersSelection, Board board, PossibleActions possibleActions) {
        this.playersSelection = playersSelection;
        this.board = board;
        this.possibleActions = possibleActions;
    }

    public boolean newPiecePositionByMove(Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();

        CheckValidation checkTest = new IsCheck(board);

        CastlingRookMovesValidation rookMoves = new CastlingRookMovesValidation(board, possibleActions, piecePositionNEW, this);

        if (newMoveIfIsPossible(piecePositionNEW)) {
            if ((checkTest.isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                    || (checkTest.isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                revertNewMove(piecePositionOLD);
                return false;
            }
            board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).countMoveAdd();
            return true;
        } else if (currentlySelected.getCountMoves() == 0) {
            if (newCastlingMoveIfIsPossible(piecePositionNEW)) {
                if ((checkTest.isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                        || (checkTest.isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                    revertNewMove(piecePositionOLD);
                    return false;
                } // rook searchForCheckOrMate when castling
                if (currentlySelected.getColor() == Color.WHITE) {
                    Piece currentlySelectedRookForCastle = rookMoves.rookMoveWhenCastling();
                    board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).countMoveAdd();
                    ChessGame.whiteCastled = true;
                } else if (currentlySelected.getColor() == Color.BLACK) {
                    Piece currentlySelectedRookForCastle = rookMoves.rookMoveWhenCastling();
                    board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).countMoveAdd();
                    ChessGame.blackCastled = true;
                }
                board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).countMoveAdd();
                return true;
            }
            // can't searchForCheckOrMate there!
            return false;
        }
        // canT searchForCheckOrMate there!
        return false;
    }

    public boolean newPiecePositionByCapture(Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();

        CheckValidation test = new IsCheck(board);

        if (newCaptureIfIsPossible(piecePositionNEW)) {
            if (test.isBlackKingChecked() && test.isWhiteKingChecked()) {
                revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                return false;
            } else if (currentlySelected.getColor() != Color.WHITE) {
                if (test.isBlackKingChecked()) {
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return false;
                }
            } else if (currentlySelected.getColor() != Color.BLACK) {
                if (test.isWhiteKingChecked()) {
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public Boolean newCastlingMoveIfIsPossible(Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();

        if (this.possibleActions.getKingCastlingActions().contains(piecePositionNEW)
                && ((currentlySelected.getPosition().getRow() == 0 && currentlySelected.getPosition().getColumn() == 4 && currentlySelected.getClass() == King.class && currentlySelected.getColor() == Color.BLACK)
                || (currentlySelected.getPosition().getRow() == 7 && currentlySelected.getPosition().getColumn() == 4 && currentlySelected.getClass() == King.class && currentlySelected.getColor() == Color.WHITE))) {
            // castling -> King SelectionAndMove
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            board.setEmptyByPosition(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(piecePositionNEW);
            board.setPiece(this.currentlySelected);
            return true;
        } else
            return false;
    }

    public Boolean newMoveIfIsPossible(Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();

        if (this.possibleActions.getPossibleMoves().contains(piecePositionNEW)) {
            // Piece searchForCheckOrMate
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            board.setEmptyByPosition(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(piecePositionNEW);
            board.setPiece(this.currentlySelected);
            return true;
        } else
            return false;
    }

    public Boolean newCaptureIfIsPossible(Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();

        this.temporaryBeaten = board.getPiece(piecePositionNEW.getRow(), piecePositionNEW.getColumn());
        if (this.possibleActions.getPossibleCaptures().contains(piecePositionNEW)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            board.setEmptyByPosition(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(piecePositionNEW);
            board.setPiece(this.currentlySelected);
            return true;
        } else
            return false;
    }

    public boolean isCheckAfterTheMove(Position piecePositionNEW) {
        CheckValidation test = new IsCheck(board);

        if (newMoveIfIsPossible(piecePositionNEW)) {

            if ((test.isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                    || (test.isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                revertNewMove(piecePositionOLD);
                return true;
            } else {
                revertNewMove(piecePositionOLD);
            }
            revertNewMove(piecePositionOLD);
            return false;
        }
        revertNewMove(piecePositionOLD);
        return false;
    }

    public Boolean isCheckAfterTheCapture(Position piecePositionNEW) {
        CheckValidation test = new IsCheck(board);

        if (newCaptureIfIsPossible(piecePositionNEW)) {

            if (test.isBlackKingChecked() && test.isWhiteKingChecked()) {
                revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                return true;
            }
            if (currentlySelected.getColor() != Color.WHITE) { //because currently piece color  changed after we captured!
                if (test.isBlackKingChecked()) {
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return true;
                }
            } else if (currentlySelected.getColor() != Color.BLACK) {  //because currently piece color  changed after we captured!
                if (test.isWhiteKingChecked()) {
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return true;
                }
            }
            revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
            return false;
        } else {
            revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
            return false;
        }
    }

    public void revertNewMove(Position piecePositionOLD) {
        this.piecePositionOLD = piecePositionOLD;
        this.piecePositionNEW = piecePositionOLD;

        board.setEmptyByPosition(this.currentlySelected.getPosition());
        currentlySelected.setPosition(piecePositionNEW);
        board.setPiece(currentlySelected);
    }

    public void revertNewMoveAndRestoreTempBeaten(Position piecePositionOLD) {
        revertNewMove(piecePositionOLD);
        board.setPiece(temporaryBeaten);
    }
}
