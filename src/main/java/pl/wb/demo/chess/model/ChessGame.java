package pl.wb.demo.chess.model;

import pl.wb.demo.chess.model.board.AllPossibleActions.WhiteOrBlackAllActions;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.Check.CheckValidation;
import pl.wb.demo.chess.model.pieces.King;
import pl.wb.demo.chess.model.pieces.MoveGenerator.CastlingRookMovesValidation;
import pl.wb.demo.chess.model.pieces.Piece;

public class ChessGame {

    public static Board board;
    private Piece currentlySelected, temporaryBeaten, currentlySelectedRookForCastle;
    public PossibleActions possibleActions, possibleMovesOrCaptures;
    public Position position, piecePositionNEW, piecePositionOLD, rookCastlingMove;
    public Color color;
    public static Boolean blackCastled, whiteCastled, isStalemate;

    public ChessGame () {
        board = new Board();
        blackCastled = false;
        whiteCastled = false;
        isStalemate = false;
    }

    public static boolean isWhiteKingChecked () {
        CheckValidation test = new CheckValidation(board);
        return test.isWhiteKingChecked();
    }

    public static boolean isBlackKingChecked () {
        CheckValidation test = new CheckValidation(board);
        return test.isBlackKingChecked();
    }

    // mate or stalemate
    public Boolean isKingMated (Color color) {
        WhiteOrBlackAllActions allPossibleActionsByWhiteOrBlack = new WhiteOrBlackAllActions(board, possibleMovesOrCaptures, possibleActions, ChessGame.this);

        if (color == Color.WHITE) {
            possibleMovesOrCaptures = allPossibleActionsByWhiteOrBlack.allWhitePiecesPossibleActions();
            if (isWhiteKingChecked()) {
                return possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().isEmpty();
            } else if (!isWhiteKingChecked() && possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().size() == 0) {
                isStalemate = true;
                return false;
            }
        } else if (color == Color.BLACK) {
            possibleMovesOrCaptures = allPossibleActionsByWhiteOrBlack.allBlackPiecesPossibleActions();
            if (isBlackKingChecked()) {
                return possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().isEmpty();
            } else if (!isBlackKingChecked() && possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().size() == 0) {
                isStalemate = true;
                return false;
            }
        }
        return false;
    }

    public PossibleActions selectWhitePiece (int row, int column) {
        try {currentlySelected = board.getPieceByColor(row, column, Color.WHITE);
            generateActionsForCurrentlySelected();
        }catch (NullPointerException e) {
            System.out.println("There is no piece on this cell!");
            // No piece on this cell!
        }
        System.out.println();
        return possibleActions;
    }

    public PossibleActions selectBlackPiece (int row, int column) {
        try { currentlySelected = board.getPieceByColor(row, column, Color.BLACK);
            generateActionsForCurrentlySelected();
        }catch (NullPointerException e) {
            System.out.println("There is no piece on this cell!");
            // No piece on this cell!
        }
        System.out.println();
        return possibleActions;
    }

    private void generateActionsForCurrentlySelected () {
            possibleActions = currentlySelected.generatePossibleActions(board);
            // possible actions without check validation
            //  System.out.println(currentlySelected.getColor() + " " + currentlySelected.getClass().getSimpleName() + " from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
            possibleActions.printPossibleCastlingKingMoves();
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
        if (newMoveIfIsPossible(piecePositionNEW)) {
            if ((isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                    || (isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
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


    public boolean newPiecePositionByMove (Position piecePositionNEW) {
        CastlingRookMovesValidation rookMoves = new CastlingRookMovesValidation(board, possibleActions, piecePositionNEW, ChessGame.this);
        //  NEXT MOVE - validation
        if (newMoveIfIsPossible(piecePositionNEW)) {
            if ((isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                    || (isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                revertNewMove(piecePositionOLD);
                return false;
            }
            board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).countMoveAdd();
            // System.out.println("Current countMoves: " + currentlySelected.getClass().getSimpleName() + " " + currentlySelected.getPosition().getRow() + " " + currentlySelected.getPosition().getColumn()
            //    + " -> " + board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).getCountMoves());
            board.printBoard(); // Printing board after legal move!
            return true;
        } else if (currentlySelected.getCountMoves() == 0) {
            if (newCastlingMoveIfIsPossible(piecePositionNEW)) {
                if ((isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                        || (isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                    revertNewMove(piecePositionOLD);
                    return false;
                } // rook move when castling
                if (currentlySelected.getColor() == Color.WHITE) {
                    currentlySelectedRookForCastle = rookMoves.rookMoveWhenCastling();
                    board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).countMoveAdd();
                    whiteCastled = true;
                } else if (currentlySelected.getColor() == Color.BLACK) {
                    currentlySelectedRookForCastle = rookMoves.rookMoveWhenCastling();
                    board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).countMoveAdd();
                    blackCastled = true;
                }
                board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).countMoveAdd();
                //  System.out.println("Current countMoves: " + currentlySelected.getClass().getSimpleName() + " " + currentlySelected.getPosition().getRow() + " " + currentlySelected.getPosition().getColumn()
                //      + " -> " + board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).getCountMoves());
                return true;
            }
            // canT move there!
            return false;
        }
        // canT move there!
        return false;
    }

    public Boolean isCheckAfterTheCapture (Position piecePositionNEW) {
        //  NEXT MOVE - validation
        if (newCaptureIfIsPossible(piecePositionNEW)) {
            if (isBlackKingChecked() && isWhiteKingChecked()) {
                revertNewMove(piecePositionOLD);
                revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                return true;
            }
            if (currentlySelected.getColor() != Color.WHITE) { //because currently piece color  changed after we captured!
                if (isBlackKingChecked()) {
                    revertNewMove(piecePositionOLD);
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return true;
                }
            } else if (currentlySelected.getColor() != Color.BLACK) {  //because currently piece color  changed after we captured!
                if (isWhiteKingChecked()) {
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

    public boolean newPiecePositionByCapture (Position piecePositionNEW) {
        // NEXT MOVE
        // System.out.println(currentlySelected.getColor());
        if (newCaptureIfIsPossible(piecePositionNEW)) {
            if (isBlackKingChecked() && isWhiteKingChecked()) {
                revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                return false;
            } else if (currentlySelected.getColor() != Color.WHITE) {
                if (isBlackKingChecked()) {
                    System.out.println(currentlySelected.getColor());
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    return false;
                }
            } else if (currentlySelected.getColor() != Color.BLACK) {
                if (isWhiteKingChecked()) {
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

//        game.selectWhitePiece(7, 0);
//        if (game.newPiecePositionByMove(new Position(7, 3))) {
//            System.out.println(game.isKingMated(Color.BLACK));
//        }
//        game.selectWhitePiece(7, 6);
//        if (game.newPiecePositionByMove(new Position(5, 7))) {
//            System.out.println(game.isKingMated(Color.BLACK));
//        }
        game.selectBlackPiece(1, 4);
        if (game.newPiecePositionByMove(new Position(3, 4))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }
        game.selectWhitePiece(6, 6);
        if (game.newPiecePositionByMove(new Position(4, 6))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        game.selectWhitePiece(6, 5);
        if (game.newPiecePositionByMove(new Position(4, 5))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        game.selectBlackPiece(1, 1);
        if (game.newPiecePositionByMove(new Position(3, 1))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }
        game.selectWhitePiece(6, 4);
        if (game.newPiecePositionByMove(new Position(5, 4))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        game.selectWhitePiece(7, 5);
        if (game.newPiecePositionByMove(new Position(5, 7))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        game.selectWhitePiece(7, 6);
        if (game.newPiecePositionByMove(new Position(5, 5))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        game.selectWhitePiece(7, 4);
        if (game.newPiecePositionByMove(new Position(7, 6))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        game.selectWhitePiece(4, 1);
        if (game.newPiecePositionByMove(new Position(4, 7))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }
//        game.selectWhitePiece(7, 4);
//        if (game.newPiecePositionByMove(new Position(7, 5))) {
//            System.out.println(game.isKingMated(Color.BLACK));
//        }
        board.printBoard();
    }
}
