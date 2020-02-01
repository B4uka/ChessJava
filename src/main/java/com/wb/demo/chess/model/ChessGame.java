package com.wb.demo.chess.model;

import com.wb.demo.chess.model.board.Board;
import com.wb.demo.chess.model.board.PossibleActions;
import com.wb.demo.chess.model.piece_properties.Color;
import com.wb.demo.chess.model.piece_properties.Position;
import com.wb.demo.chess.model.pieces.King;
import com.wb.demo.chess.model.pieces.Piece;

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
        PossibleActions whiteKingCheckedPositions = new PossibleActions();
        King whiteKing = new King(board.getWhiteKingPosition(), Color.WHITE, "&#9812;", 0);
        try {
            whiteKingCheckedPositions = whiteKing.piecesPositionsCheckingWhiteKing(board);
            if (!whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()) {
                // Positions of pieces that are checking white king
                whiteKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();
            }
            // else {
            //      King is not checked after this move!
            // }

        } catch (NullPointerException e) {
            // Probably no checks
        }
        return !whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    public static boolean isBlackKingChecked () {
        PossibleActions blackKingCheckedPositions = new PossibleActions();
        King blackKing = new King(board.getBlackKingPosition(), Color.BLACK, "&#9818;", 0);
        try {
            blackKingCheckedPositions = blackKing.piecesPositionsCheckingBlackKing(board);
            if (!blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()) {
                // Positions of pieces that are checking black king
                blackKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();
            }
            //else {
            //   King is not checked after this move
            //}
        } catch (NullPointerException e) {
            //Probably no checks
        }
        return !blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    // all actions that white player can do
    public PossibleActions allWhitePiecesPossibleActions () {
        board.getAllWhitePiecesPosition();
        possibleMovesOrCaptures = new PossibleActions();

        for (Position test : board.whitePiecesPositions) {
            selectWhitePiece(test.getRow(), test.getColumn());
            for (Position position : possibleActions.getPossibleMoves()) {
                if (isCheckAfterTheMove(position)) {
                    continue;
                } else {
                    possibleMovesOrCaptures.addPossibleMove(position);
                }
                // System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (isCheckAfterTheCapture(position)) {
                    continue;
                } else {
                    possibleMovesOrCaptures.addPossibleCapture(position);
                    // System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
                }
            }
        }
        return possibleMovesOrCaptures;
    }

    //all actions that black player can do
    public PossibleActions allBlackPiecesPossibleActions () {
        board.getAllBlackPiecesPosition();
        possibleMovesOrCaptures = new PossibleActions();

        for (Position test : board.blackPiecesPositions) {
            selectBlackPiece(test.getRow(), test.getColumn());
            for (Position position : possibleActions.getPossibleMoves()) {
                if (isCheckAfterTheMove(position)) {
                    continue;
                } else {
                    possibleMovesOrCaptures.addPossibleMove(position);
                }
                // System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (isCheckAfterTheCapture(position)) {
                    continue;
                } else {
                    possibleMovesOrCaptures.addPossibleCapture(position);
                    //  System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
                }
            }
        }

        return possibleMovesOrCaptures;
    }

    // mate or stalemate
    public Boolean isKingMated (Color color) {
        if (color == Color.WHITE) {
            allWhitePiecesPossibleActions();
            if (isWhiteKingChecked()) {
                return possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().isEmpty();
            } else if (!isWhiteKingChecked() && possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().size() == 0) {
                isStalemate = true;
                return false;
            }
        } else if (color == Color.BLACK) {
            allBlackPiecesPossibleActions();
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
        currentlySelected = board.getWhitePiece(row, column);
        generateActionsForCurrentlySelected();
        System.out.println();
        return possibleActions;
    }

    public PossibleActions selectBlackPiece (int row, int column) {
        currentlySelected = board.getBlackPiece(row, column);
        generateActionsForCurrentlySelected();
        System.out.println();
        return possibleActions;
    }

    private void generateActionsForCurrentlySelected () {
        try {
            possibleActions = currentlySelected.generatePossibleActions(board);
            // possible actions without check validation
            //  System.out.println(currentlySelected.getColor() + " " + currentlySelected.getClass().getSimpleName() + " from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
            possibleActions.printPossibleCastlingKingMoves();
        } catch (NullPointerException e) {
            // No piece on this cell
            // System.out.println(currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn() + "\t there is no piece on this position!");
        }
    }

    private void revertNewMove (Position piecePositionOLD) {
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

    private boolean isCheckAfterTheMove (Position piecePositionNEW) {
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

    private void rookMoveWhenCastling () {
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
            } else revertNewMove(piecePositionNEW);
            if (piecePositionNEW.getRow() == 0 && piecePositionNEW.getColumn() == 6 && rookOnBoard[1].getCountMoves() == 0) {
                currentlySelectedRookForCastle = board.getPiece(0, 7);
                rookCastlingMove = currentlySelectedRookForCastle.getPosition();
                board.setEmptyByPosition(rookCastlingMove);
                this.currentlySelectedRookForCastle.setPosition(0, 5);
                board.setPiece(currentlySelectedRookForCastle);
            } else revertNewMove(piecePositionNEW);
            if (piecePositionNEW.getRow() == 7 && piecePositionNEW.getColumn() == 2 && rookOnBoard[2].getCountMoves() == 0) {
                currentlySelectedRookForCastle = board.getPiece(7, 0);
                rookCastlingMove = currentlySelectedRookForCastle.getPosition();
                board.setEmptyByPosition(rookCastlingMove);
                this.currentlySelectedRookForCastle.setPosition(7, 3);
                board.setPiece(currentlySelectedRookForCastle);
            } else revertNewMove(piecePositionNEW);
            if (piecePositionNEW.getRow() == 7 && piecePositionNEW.getColumn() == 6 && rookOnBoard[3].getCountMoves() == 0) {
                currentlySelectedRookForCastle = board.getPiece(7, 7);
                rookCastlingMove = currentlySelectedRookForCastle.getPosition();
                board.setEmptyByPosition(rookCastlingMove);
                this.currentlySelectedRookForCastle.setPosition(7, 5);
                board.setPiece(currentlySelectedRookForCastle);
            } else revertNewMove(piecePositionNEW);
            // System.out.println("How many times rook moved?: " + rookCountMove);
        } else if (!this.possibleActions.getKingCastlingActions().contains(piecePositionNEW)) {
            revertNewMove(piecePositionNEW);
        }
    }

    public boolean newPiecePositionByMove (Position piecePositionNEW) {
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
                    rookMoveWhenCastling();
                    board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).countMoveAdd();
                    whiteCastled = true;
                } else if (currentlySelected.getColor() == Color.BLACK) {
                    rookMoveWhenCastling();
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

        game.selectWhitePiece(6, 5);
        if (game.newPiecePositionByMove(new Position(4, 5))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        game.selectBlackPiece(1, 4);
        if (game.newPiecePositionByMove(new Position(3, 4))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }
        game.selectWhitePiece(6, 6);
        if (game.newPiecePositionByMove(new Position(4, 6))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        game.selectWhitePiece(4, 5);
        if (game.newPiecePositionByCapture(new Position(3, 4))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        game.selectBlackPiece(0, 3);
        if (game.newPiecePositionByMove(new Position(4, 7))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }
        board.printBoard();
    }
}
