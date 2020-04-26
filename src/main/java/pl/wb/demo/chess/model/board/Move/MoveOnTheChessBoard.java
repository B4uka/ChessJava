package pl.wb.demo.chess.model.board.Move;

import lombok.Getter;
import lombok.Setter;
import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.King;
import pl.wb.demo.chess.model.pieces.MoveGenerator.CastlingRookMovesValidation;
import pl.wb.demo.chess.model.pieces.Piece;
import pl.wb.demo.chess.model.pieces.check.CheckValidation;
import pl.wb.demo.chess.model.pieces.check.IsCheck;

@Getter
@Setter
public class MoveOnTheChessBoard {
    private Selection playersSelection;
    private Piece currentlySelected, temporaryBeaten;
    private Position piecePositionOLD, piecePositionNEW;
    private Board board;
    private PossibleActions possibleActions;
    private RevertMoveIfIncorrect moveBack;

    public MoveOnTheChessBoard (Selection playersSelection, Board board, PossibleActions possibleActions){
        this.playersSelection = playersSelection;
        this.board = board;
        this.possibleActions = possibleActions;
    }

    public boolean newPiecePositionByMove (Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();

        CheckValidation checkTest = new IsCheck(board);

        moveBack = new RevertMoveIfIncorrect(board, piecePositionOLD, piecePositionNEW, currentlySelected, temporaryBeaten);

        CastlingRookMovesValidation rookMoves = new CastlingRookMovesValidation(board, possibleActions, piecePositionNEW);

        //  NEXT MOVE - validation
        if (newMoveIfIsPossible(piecePositionNEW)) {
            if ((checkTest.isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                    || (checkTest.isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                moveBack.revertNewMove(piecePositionOLD);
                return false;
            }
            board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).countMoveAdd();
            // Printing board after legal move!
            board.printBoard();
            return true;
        } else if (currentlySelected.getCountMoves() == 0) {
            if (newCastlingMoveIfIsPossible(piecePositionNEW)) {
                if ((checkTest.isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                        || (checkTest.isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                    moveBack.revertNewMove(piecePositionOLD);
                    return false;
                } // rook move when castling
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
            // can't move there!
            return false;
        }
        // canT move there!
        return false;
    }

    public boolean newPiecePositionByCapture (Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();

        moveBack = new RevertMoveIfIncorrect(board, piecePositionOLD, piecePositionNEW, currentlySelected, temporaryBeaten);

        CheckValidation test = new IsCheck(board);

        // NEXT MOVE
        if (newCaptureIfIsPossible(piecePositionNEW)) {
            if (test.isBlackKingChecked() && test.isWhiteKingChecked()) {
                revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                return false;
            } else if (currentlySelected.getColor() != Color.WHITE) {
                if (test.isBlackKingChecked()) {
                    System.out.println(currentlySelected.getColor());
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return false;
                }
            } else if (currentlySelected.getColor() != Color.BLACK) {
                if (test.isWhiteKingChecked()) {
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return false;
                }
            }
            board.printBoard(); // show board after legal capture
            return true;
        } else {
            return false;
        }
    }

    public Boolean newCastlingMoveIfIsPossible (Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();
        moveBack = new RevertMoveIfIncorrect(board, piecePositionOLD, piecePositionNEW, currentlySelected, temporaryBeaten);

        if (this.possibleActions.getKingCastlingActions().contains(piecePositionNEW)
                && ((currentlySelected.getPosition().getRow() == 0 && currentlySelected.getPosition().getColumn() == 4 && currentlySelected.getClass() == King.class && currentlySelected.getColor() == Color.BLACK)
                || (currentlySelected.getPosition().getRow() == 7 && currentlySelected.getPosition().getColumn() == 4 && currentlySelected.getClass() == King.class && currentlySelected.getColor() == Color.WHITE))) {
            // castling -> King Move
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            board.setEmptyByPosition(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(piecePositionNEW);
            board.setPiece(this.currentlySelected);
            return true;
        } else
            return false;
    }

    public Boolean newMoveIfIsPossible (Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();

        if (this.possibleActions.getPossibleMoves().contains(piecePositionNEW)) {
            // Piece move
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            board.setEmptyByPosition(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(piecePositionNEW);
            board.setPiece(this.currentlySelected);
            return true;
        } else
            return false;
    }

    public Boolean newCaptureIfIsPossible (Position piecePositionNEW) {
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
    public boolean isCheckAfterTheMove (Position piecePositionNEW) {
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

    public Boolean isCheckAfterTheCapture (Position piecePositionNEW) {
        CheckValidation test = new IsCheck(board);
        //  NEXT MOVE - validation
        if (newCaptureIfIsPossible(piecePositionNEW)) {

            if (test.isBlackKingChecked() && test.isWhiteKingChecked()) {
                revertNewMove(piecePositionOLD);
                revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                return true;
            }
            if (currentlySelected.getColor() != Color.WHITE) { //because currently piece color  changed after we captured!
                if (test.isBlackKingChecked()) {
                    revertNewMove(piecePositionOLD);
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return true;
                }
            } else if (currentlySelected.getColor() != Color.BLACK) {  //because currently piece color  changed after we captured!
                if (test.isWhiteKingChecked()) {
                    revertNewMove(piecePositionOLD);
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return true;
                }
            }
            revertNewMove(piecePositionOLD);
            revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
            return false;
        } else {
            revertNewMove(piecePositionOLD);
            revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
            return false;
        }
    }

    public void revertNewMove (Position piecePositionOLD) {
        this.piecePositionOLD = piecePositionOLD;

        this.piecePositionNEW = piecePositionOLD;
        board.setEmptyByPosition(this.currentlySelected.getPosition());
        currentlySelected.setPosition(piecePositionNEW);
        board.setPiece(currentlySelected);
    }

    public void revertNewMoveAndRestoreTempBeaten (Position piecePositionOLD) {
        revertNewMove(piecePositionOLD);
        board.setPiece(temporaryBeaten);
    }
}
