package pl.wb.demo.chess.model;

import pl.wb.demo.chess.model.board.Move.Selection;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.King;
import pl.wb.demo.chess.model.pieces.Mate.IsMate;
import pl.wb.demo.chess.model.pieces.Mate.MateValidation;
import pl.wb.demo.chess.model.pieces.MoveGenerator.CastlingRookMovesValidation;
import pl.wb.demo.chess.model.pieces.Piece;
import pl.wb.demo.chess.model.pieces.check.CheckValidation;
import pl.wb.demo.chess.model.pieces.check.IsCheck;


public class ChessGame {

    public static Board board;
    private Piece currentlySelected, temporaryBeaten;
    public PossibleActions possibleActions, possibleMovesOrCaptures;
    public Position position, piecePositionNEW, piecePositionOLD;
    public Color whoIsUpToMove = Color.WHITE;
    public static boolean blackCastled, whiteCastled, isStalemate;
    public Selection playersSelection;

    public ChessGame () {
        board = new Board();
        blackCastled = false;
        whiteCastled = false;
    }

    // Mate is returned. Inside method from class IsMate is also validation for stalemate -> for know class Mate in controller catches this event
    public boolean isKingMated (Color color) {
        MateValidation test = new IsMate(board, color, possibleMovesOrCaptures, possibleActions, ChessGame.this);
        return test.isKingMated(color);
    }

    public PossibleActions selectPiece (int row, int column, Color color) {
        this.playersSelection = new Selection(ChessGame.this, board);
        this.possibleActions = playersSelection.selectedPiecePossibleActions(row, column, color);
        return possibleActions;
    }

    public void revertNewMove (Position piecePositionOLD) {
        this.piecePositionOLD = piecePositionOLD;

        piecePositionNEW = piecePositionOLD;
        board.setEmptyByPosition(this.currentlySelected.getPosition());
        currentlySelected.setPosition(piecePositionNEW);
        board.setPiece(currentlySelected);
    }

    private void revertNewMoveAndRestoreTempBeaten (Position piecePositionOLD) {
        revertNewMove(piecePositionOLD);
        board.setPiece(temporaryBeaten);
    }

    private Boolean newCaptureIfIsPossible (Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();
        this.piecePositionNEW = piecePositionNEW;
        temporaryBeaten = board.getPiece(piecePositionNEW.getRow(), piecePositionNEW.getColumn());
        if (this.possibleActions.getPossibleCaptures().contains(piecePositionNEW)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            board.setEmptyByPosition(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(piecePositionNEW);
            board.setPiece(this.currentlySelected);
            return true;
        } else
            return false;
    }

    private Boolean newMoveIfIsPossible (Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();
        this.piecePositionNEW = piecePositionNEW;

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

    private Boolean newCastlingMoveIfIsPossible (Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();
        this.piecePositionNEW = piecePositionNEW;

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

    public boolean newPiecePositionByMove (Position piecePositionNEW) {
        currentlySelected = playersSelection.getCurrentlySelected();
        CheckValidation test = new IsCheck(board);
        CastlingRookMovesValidation rookMoves = new CastlingRookMovesValidation(board, possibleActions, piecePositionNEW, ChessGame.this);
        //  NEXT MOVE - validation
        if (newMoveIfIsPossible(piecePositionNEW)) {
            if ((test.isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                    || (test.isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                revertNewMove(piecePositionOLD);
                return false;
            }
            board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).countMoveAdd();
            // Printing board after legal move!
            board.printBoard();
            return true;
        } else if (currentlySelected.getCountMoves() == 0) {
            if (newCastlingMoveIfIsPossible(piecePositionNEW)) {
                if ((test.isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                        || (test.isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                    revertNewMove(piecePositionOLD);
                    return false;
                } // rook move when castling
                if (currentlySelected.getColor() == Color.WHITE) {
                    Piece currentlySelectedRookForCastle = rookMoves.rookMoveWhenCastling();
                    board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).countMoveAdd();
                    whiteCastled = true;
                } else if (currentlySelected.getColor() == Color.BLACK) {
                    Piece currentlySelectedRookForCastle = rookMoves.rookMoveWhenCastling();
                    board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).countMoveAdd();
                    blackCastled = true;
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

    private static void printActualPositionAndGetPositionOfBothKings () {
        board.printBoard();
        board.getWhiteKingPosition();
        board.getBlackKingPosition();
    }

    public static void main (String[] args) {

        ChessGame game = new ChessGame();
        printActualPositionAndGetPositionOfBothKings();

        game.selectPiece(1, 4, Color.BLACK);
        if (game.newPiecePositionByMove(new Position(3, 4))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }

        System.out.println();
        game.selectPiece(6, 6, Color.WHITE);
        if (game.newPiecePositionByMove(new Position(4, 6))) {
//            System.out.println(game.isKingMated(Color.BLACK));
        }
        System.out.println();
        game.selectPiece(6, 5, Color.WHITE);
        if (game.newPiecePositionByMove(new Position(4, 5))) {
//            System.out.println(game.isKingMated(Color.BLACK));
        }

        game.selectPiece(3, 4, Color.BLACK);
        if (game.newPiecePositionByCapture(new Position(4, 5))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }

        System.out.println();
        game.selectPiece(0, 3, Color.BLACK);
        game.newPiecePositionByMove(new Position(4, 7));
        System.out.println(game.isKingMated(Color.WHITE));

        System.out.println();
        game.selectPiece(1, 1, Color.BLACK);
        if (game.newPiecePositionByMove(new Position(3, 1))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }

        board.printBoard();
    }
}
