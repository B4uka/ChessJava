package com.example.demo.model;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;
import com.example.demo.model.pieces.King;
import com.example.demo.model.pieces.Piece;

public class ChessGame {

    public static Board board;
    public Piece currentlySelected, currentWhiteKing, currentBlackKing;
    public PossibleActions possibleActions, whiteKingCheckedPositions, blackKingCheckedPositions;
    public Position position, piecePositionNEW, piecePositionOLD;
    public Color color;
    public Piece piece;

    public ChessGame () {
        this.board = new Board();
    }

    public boolean isWhiteKingChecked (Board board) {
        this.board = board;
        this.board.getWhiteKingPosition();
        King whiteKing = new King(board.getWhiteKingPosition(), Color.WHITE);

        System.out.println("Positions of pieces that are checking white king: ");
        try {
            whiteKingCheckedPositions = whiteKing.isWhiteKingChecked(board);
            whiteKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
        } catch (NullPointerException e) {
            System.out.println("Probably no check for the WhiteKing");
        }
//        if (blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()){
//            System.out.println("0");
//        }else blackKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
//
//        if (whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()) {
//            System.out.println("0");
//        }else whiteKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
        return true;
    }

    public boolean isBlackKingChecked (Board board) {
        this.board = board;
        this.board.getBlackKingPosition();
        King blackKing = new King(board.getBlackKingPosition(), Color.BLACK);

        System.out.println("Positions of pieces that are checking black king: ");
        try {
            blackKingCheckedPositions = blackKing.isBlackKingChecked(board);
            blackKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
        } catch (NullPointerException e) {
            System.out.println("Probably no check for the BlackKing");
        }
        return true;
    }

    // -> mainController
    public PossibleActions selectWhitePiece (int row, int column) {

        currentlySelected = board.getWhitePiece(row, column);
        try {
            possibleActions = currentlySelected.generatePossibleActions(board);
            System.out.println("Piece from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
        } catch (NullPointerException e) {
            System.out.println(row + "\t" + column + "\t there is no WHITE piece on this position!");
        }
        return possibleActions;
    }

    // -> mainController
    public PossibleActions selectBlackPiece (int row, int column) {
        currentlySelected = board.getBlackPiece(row, column);
        try {
            possibleActions = currentlySelected.generatePossibleActions(board);
            System.out.println("Piece from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
        } catch (NullPointerException e) {
            System.out.println(row + "\t" + column + "\t there is no BLACK piece on this position!");
        }
        return possibleActions;
    }

    // Method that is used for testing
    public PossibleActions select (int row, int column) {

        currentlySelected = this.board.getPiece(row, column);

        try {
            possibleActions = currentlySelected.generatePossibleActions(board);
            System.out.println("Piece from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
        } catch (NullPointerException e) {
            System.out.println(row + "\t" + column + "\t there is no piece on this position!");

        }
//        printActualPositionAndGetPositionOfBothKings();
        System.out.println("----------");
        areKingsUnderCheck(possibleActions);
        System.out.println("----------");

        isWhiteKingChecked(board);
        isBlackKingChecked(board);
        return possibleActions;
    }

    public void newPiecePositionByMove (Position newPosition) throws Exception {
        System.out.println("NEXT MOVE: ");
        if (this.possibleActions.getPossibleMoves().contains(newPosition)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(newPosition);
            board.setPiece(this.currentlySelected);
            piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            System.out.println("wierszNowa:" + piecePositionNEW.getRow() + "kolumnaNowa: " + piecePositionNEW.getColumn());
            System.out.println("wierszStara:" + piecePositionOLD.getRow() + "kolumnaStara: " + piecePositionOLD.getColumn());
            isWhiteKingChecked(board);
            isBlackKingChecked(board);
            if ((!whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty() && currentlySelected.getColor() == Color.WHITE)
                    || (!blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty() && currentlySelected.getColor() == Color.BLACK)) {
                piecePositionNEW = piecePositionOLD;
                board.setEmpty(this.currentlySelected.getPosition());
                currentlySelected.setPosition(piecePositionNEW);
                board.setPiece(currentlySelected);

                System.out.println("wierszNowa:" + piecePositionNEW.getRow() + "kolumnaNowa: " + piecePositionNEW.getColumn());
                System.out.println("wierszStara:" + piecePositionOLD.getRow() + "kolumnaStara: " + piecePositionOLD.getColumn());

            }
            board.printBoard();
        }
    }

    public void newPiecePositionByCapture (Position newPosition) throws Exception {
        System.out.println("NEXT MOVE: ");
        if (this.possibleActions.getPossibleCaptures().contains(newPosition)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(newPosition);
            board.setPiece(this.currentlySelected);
            piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            System.out.println("wierszNowa:" + piecePositionNEW.getRow() + "kolumnaNowa: " + piecePositionNEW.getColumn());
            System.out.println("wierszStara:" + piecePositionOLD.getRow() + "kolumnaStara: " + piecePositionOLD.getColumn());
            isWhiteKingChecked(board);
            isBlackKingChecked(board);
            if ((!whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty() && currentlySelected.getColor() == Color.WHITE)
                    || (!blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty() && currentlySelected.getColor() == Color.BLACK)) {
                piecePositionNEW = piecePositionOLD;
                board.setEmpty(this.currentlySelected.getPosition());
                currentlySelected.setPosition(piecePositionNEW);
                board.setPiece(currentlySelected);

                System.out.println("wierszNowa:" + piecePositionNEW.getRow() + "kolumnaNowa: " + piecePositionNEW.getColumn());
                System.out.println("wierszStara:" + piecePositionOLD.getRow() + "kolumnaStara: " + piecePositionOLD.getColumn());

            }
            board.printBoard();
        }
    }

    public static boolean areKingsUnderCheck (PossibleActions possibleActions) {
        possibleActions.getPossibleChecks();
        System.out.println(possibleActions.isChecked());
        return possibleActions.isChecked();
    }

    public static void printActualPositionAndGetPositionOfBothKings () {
        board.printBoard();
        System.out.println("----------");

        board.getWhiteKingPosition();
        board.getBlackKingPosition();
        System.out.println("White King row: " + board.whiteKingPosition.getRow());
        System.out.println("White King column: " + board.whiteKingPosition.getColumn());
        System.out.println("Black King row: " + board.blackKingPosition.getRow());
        System.out.println("Black King column: " + board.blackKingPosition.getColumn());
    }

    public static void main (String[] args) throws Exception {

        ChessGame game = new ChessGame();
        printActualPositionAndGetPositionOfBothKings();
    }
}
        // Todo: tutaj jest dzialajaca metoda, ktora pokazuje czy dalismy szacha!
//        public void newPiecePositionByMove (Position newPosition) {
//            System.out.println("NEXT MOVE: ");
//            if (this.possibleActions.getPossibleMoves().contains(newPosition)) {
//                board.setEmpty(this.currentlySelected.getPosition());
//                this.currentlySelected.setPosition(newPosition);
//                board.setPiece(this.currentlySelected);
//
//                this.piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
//
//                System.out.println("------------------------------");
//
//                // We will not checking if opposite king will be under check if we will make our next move - PAWN PROMOTION
//                // To not get out of the board
////            if (currentlySelected.getClass() == Pawn.class) {
////                if (currentlySelected.getPosition().getRow() != 0 || currentlySelected.getPosition().getRow() != 7) {
////                    select(newPosition.getRow(), newPosition.getColumn());
////                } else {
////                    System.out.println("Problem1");
////                }
////            } else {
////                select(newPosition.getRow(), newPosition.getColumn());
////            }
//
//            } else {
//                System.out.println("cant move there");
//            }
//            board.printBoard();
////        printActualPositionAndGetPositionOfBothKings();
//            System.out.println("----------");
//            areKingsUnderCheck(possibleActions);
//            System.out.println("----------");
//
//            isWhiteKingChecked(board);
//            isBlackKingChecked(board);
//        }
//
//        public void newPiecePositionByCapture (Position newPosition) {
//            System.out.println("NEXT MOVE: ");
//            if (possibleActions.getPossibleCaptures().contains(newPosition)) {
//                board.setEmpty(currentlySelected.getPosition());
//                currentlySelected.setPosition(newPosition);
//                board.setPiece(currentlySelected);
//
//                this.piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
//
//                System.out.println();
//                System.out.println("------------------------------");
//                // We will not checking if opposite king will be under check if we will make our next move - PAWN PROMOTION
//                // To not get out of the board
////            if (currentlySelected.getClass() != Pawn.class && (currentlySelected.getPosition().getRow() != 7 || currentlySelected.getPosition().getRow() != 0)) {
////                select(newPosition.getRow(), newPosition.getColumn());
////            }
//
//            } else {
//                System.out.println("cant move there");
//            }
//            board.printBoard();
////        printActualPositionAndGetPositionOfBothKings();
//            System.out.println("----------");
//            areKingsUnderCheck(this.possibleActions);
//            System.out.println("----------");
//
//            isWhiteKingChecked(board);
//            isBlackKingChecked(board);
//        }
//    }

