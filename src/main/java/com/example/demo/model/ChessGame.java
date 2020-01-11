package com.example.demo.model;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;
import com.example.demo.model.pieces.King;
import com.example.demo.model.pieces.Piece;

public class ChessGame {

    public static Board board;
    public Piece currentlySelected, pieceWhichMayBeCaptured;
    public PossibleActions possibleActions;
    public Position position, piecePositionNEW, piecePositionOLD;
    public Color color;

    public ChessGame () {
        board = new Board();
    }

    public static boolean isWhiteKingChecked () {
        PossibleActions whiteKingCheckedPositions = new PossibleActions();
        board.getWhiteKingPosition();
        King whiteKing = new King(board.getWhiteKingPosition(), Color.WHITE);
        try {
            whiteKingCheckedPositions = whiteKing.piecesPositionsCheckingWhiteKing(board);
            if (!whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()) {
//                System.out.println("Positions of pieces that are checking white king: ");
//                whiteKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
            } else {
                //    System.out.println("King is not checked after this move!");
            }
        } catch (NullPointerException e) {
//            System.out.println("Probably no check for the WhiteKing");
        }
        return !whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    public static boolean isBlackKingChecked () {
        PossibleActions blackKingCheckedPositions = new PossibleActions();
        board.getBlackKingPosition();
        King blackKing = new King(board.getBlackKingPosition(), Color.BLACK);
        try {
            blackKingCheckedPositions = blackKing.piecesPositionsCheckingBlackKing(board);
            if (!blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()) {
//                System.out.println("Positions of pieces that are checking black king: ");
//                blackKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
            } else {
                //   System.out.println("King is not checked after this move!");
            }
        } catch (NullPointerException e) {
//            System.out.println("Probably no check for the BlackKing");
        }
        return !blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    // TODO all actions that white player can do
    public PossibleActions allWhitePiecesPossibleActions () {
        board.getAllWhitePiecesPosition();

        for (Position test : board.whitePiecesPositions) {
            selectWhitePiece(test.getRow(), test.getColumn());
            possibleActions.addPossibleMove(test);
            possibleActions.addPossibleCapture(test);
            possibleActions.addPossibleChecks(test);
        }
//        System.out.println("Is white king checked? " + isWhiteKingChecked());
//        System.out.println("Is white king mated? " + possibleActions.isMated());
//        System.out.println("Is white king stalamated? " + possibleActions.isStalamated());
//        System.out.println("__________________________________________________________________________________________");
        return possibleActions;
    }

    // TODO all actions that black player can do
    public PossibleActions allBlackPiecesPossibleActions () {
        board.getAllBlackPiecesPosition();
        for (Position test : board.blackPiecesPositions) {
            selectBlackPiece(test.getRow(), test.getColumn());
            possibleActions.addPossibleMove(test);
            possibleActions.addPossibleCapture(test);
            possibleActions.addPossibleChecks(test);
        }
//        System.out.println("Is black king checked? " + isBlackKingChecked());
//        System.out.println("Is black king mated? " + possibleActions.isMated());
//        System.out.println("Is black king stalameted? " + possibleActions.isStalamated());
//        System.out.println("__________________________________________________________________________________________");
        return possibleActions;
    }

    // TODO probably not good method for searching for the mate
    public Boolean isBlackKingMated () {
        allBlackPiecesPossibleActions();
        outloop:
        {
            for (Position test : possibleActions.possibleMoves) {
                newPiecePositionByMoveForMate(test);
                newPiecePositionByCaptureForMate(test);
//            newPiecePositionByCapture(test);
                if (!isBlackKingChecked()) {
                    System.out.println("BLACK KING IS NOT MATED");
                    break outloop;
                }
                System.out.println("BLACK KING IS MATED_______________________________!!!!!!!!!!!!!!!!!!!!!!");
                return true;
            }
        }
        return false;
    }

    public Boolean isWhiteKingMated () {
        allWhitePiecesPossibleActions();
        outloop:
        {
            for (Position test : possibleActions.possibleMoves) {
                newPiecePositionByMoveForMate(test);
                newPiecePositionByCaptureForMate(test);
//            newPiecePositionByCapture(test)
                if (!isWhiteKingChecked()) {
                    System.out.println("WHITE KING IS NOT MATED");
                    break outloop;
                }
                System.out.println("WHITE KING IS MATED_______________________________!!!!!!!!!!!!!!!!!!!!!!");
                return true;
            }
        }
        return false;
    }

    // -> mainController
    public PossibleActions selectWhitePiece (int row, int column) {

        currentlySelected = board.getWhitePiece(row, column);
        try {
            possibleActions = currentlySelected.generatePossibleActions(board);
            // ALL POSSIBLE ACTIONS WILL BE SHOWN IF YOU WILL USE LINES BELOW:
            System.out.println("__________________________________________________________________________________________");
            System.out.println("White " + currentlySelected.getClass().getSimpleName() + " from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
//            System.out.println();
            //
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
            // ALL POSSIBLE ACTIONS WILL BE SHOWN IF YOU WILL USE LINES BELOW:
            System.out.println("__________________________________________________________________________________________");
            System.out.println("Black " + currentlySelected.getClass().getSimpleName() + " from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
            System.out.println();
            //
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

        isWhiteKingChecked();
        isBlackKingChecked();
        return possibleActions;
    }




    public boolean newPiecePositionByMoveForMate (Position newPosition) {
//        System.out.println("NEXT MOVE: ");
        if (this.possibleActions.getPossibleMoves().contains(newPosition)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(newPosition);
            board.setPiece(this.currentlySelected);
            piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            if ((isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                    || (isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                piecePositionNEW = piecePositionOLD;
                board.setEmpty(this.currentlySelected.getPosition());
                currentlySelected.setPosition(piecePositionNEW);
                board.setPiece(currentlySelected);
//                System.out.println("cant move there!");
                return false;
            }
            piecePositionNEW = piecePositionOLD;
            board.setEmpty(this.currentlySelected.getPosition());
            currentlySelected.setPosition(piecePositionNEW);
            board.setPiece(currentlySelected);
//            board.printBoard();
            return true;
        }
        return false;
    }

    public boolean newPiecePositionByMove (Position newPosition) {
        System.out.println("NEXT MOVE: ");
        if (this.possibleActions.getPossibleMoves().contains(newPosition)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(newPosition);
            board.setPiece(this.currentlySelected);
            piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            if ((isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                    || (isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                piecePositionNEW = piecePositionOLD;
                board.setEmpty(this.currentlySelected.getPosition());
                currentlySelected.setPosition(piecePositionNEW);
                board.setPiece(currentlySelected);
                System.out.println("cant move there!");
                return false;
            }
            board.printBoard();
            return true;
        }
        return false;
    }

    public boolean newPiecePositionByCaptureForMate (Position newPosition) {
//        System.out.println("NEXT MOVE: ");

        if (this.possibleActions.getPossibleCaptures().contains(newPosition)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            pieceWhichMayBeCaptured = board.getPiece(newPosition.getRow(), newPosition.getColumn());

            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(newPosition);
            board.setPiece(this.currentlySelected);
            piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            if (isBlackKingChecked() && isWhiteKingChecked()) {
                piecePositionNEW = piecePositionOLD;
                board.setEmpty(this.currentlySelected.getPosition());
                board.setPiece(pieceWhichMayBeCaptured);
                currentlySelected.setPosition(piecePositionNEW);
                board.setPiece(currentlySelected);
//                System.out.println("cant move there!");
                return false;
            } else if (currentlySelected.getColor() != Color.WHITE) {
                if (isBlackKingChecked()) {
                    piecePositionNEW = piecePositionOLD;
                    board.setEmpty(this.currentlySelected.getPosition());
                    board.setPiece(pieceWhichMayBeCaptured);
                    currentlySelected.setPosition(piecePositionNEW);
                    board.setPiece(currentlySelected);
//                    System.out.println("cant move there!");
                    return false;
                }
            } else if (currentlySelected.getColor() != Color.BLACK) {
                if (isWhiteKingChecked()) {
                    piecePositionNEW = piecePositionOLD;
                    board.setEmpty(this.currentlySelected.getPosition());
                    board.setPiece(pieceWhichMayBeCaptured);
                    currentlySelected.setPosition(piecePositionNEW);
                    board.setPiece(currentlySelected);
//                    System.out.println("cant move there!");
                    return false;
                }
            } else
//                System.out.println(pieceWhichMayBeCaptured.getPosition().getRow() + " pozycja figury" + pieceWhichMayBeCaptured.getPosition().getRow());
                board.printBoard();
            return true;
        } else {
//            System.out.println("cant move there");
            return false;
        }
    }

    public boolean newPiecePositionByCapture (Position newPosition) {
        System.out.println("NEXT MOVE: ");

        if (this.possibleActions.getPossibleCaptures().contains(newPosition)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            pieceWhichMayBeCaptured = board.getPiece(newPosition.getRow(), newPosition.getColumn());

            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(newPosition);
            board.setPiece(this.currentlySelected);
            piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            if (isBlackKingChecked() && isWhiteKingChecked()) {
                piecePositionNEW = piecePositionOLD;
                board.setEmpty(this.currentlySelected.getPosition());
                board.setPiece(pieceWhichMayBeCaptured);
                currentlySelected.setPosition(piecePositionNEW);
                board.setPiece(currentlySelected);
                System.out.println("cant move there!");
                return false;
            } else if (currentlySelected.getColor() != Color.WHITE) {
                if (isBlackKingChecked()) {
                    piecePositionNEW = piecePositionOLD;
                    board.setEmpty(this.currentlySelected.getPosition());
                    board.setPiece(pieceWhichMayBeCaptured);
                    currentlySelected.setPosition(piecePositionNEW);
                    board.setPiece(currentlySelected);
                    System.out.println("cant move there!");
                    return false;
                }
            } else if (currentlySelected.getColor() != Color.BLACK) {
                if (isWhiteKingChecked()) {
                    piecePositionNEW = piecePositionOLD;
                    board.setEmpty(this.currentlySelected.getPosition());
                    board.setPiece(pieceWhichMayBeCaptured);
                    currentlySelected.setPosition(piecePositionNEW);
                    board.setPiece(currentlySelected);
                    System.out.println("cant move there!");
                    return false;
                }
            } else
                System.out.println(pieceWhichMayBeCaptured.getPosition().getRow() + " pozycja figury" + pieceWhichMayBeCaptured.getPosition().getRow());
            board.printBoard();
            return true;
        } else {
            System.out.println("cant move there");
            return false;
        }
    }

    public static boolean areKingsUnderCheck (PossibleActions possibleActions) {
        System.out.println(possibleActions.isChecked());
        return possibleActions.isChecked();
    }

    public static void printActualPositionAndGetPositionOfBothKings () {
        board.printBoard();
        board.getWhiteKingPosition();
        board.getBlackKingPosition();
//        System.out.println("White King row: " + board.whiteKingPosition.getRow());
//        System.out.println("White King column: " + board.whiteKingPosition.getColumn());
//        System.out.println("Black King row: " + board.blackKingPosition.getRow());
//        System.out.println("Black King column: " + board.blackKingPosition.getColumn());
    }

    public static void main (String[] args) throws Exception {

        ChessGame game = new ChessGame();
        printActualPositionAndGetPositionOfBothKings();

        game.selectWhitePiece(6, 6);
        game.newPiecePositionByCapture(new Position(1, 1));
        game.isBlackKingMated();
//        game.allBlackPiecesPossibleActions();
        board.printBoard();
        game.selectBlackPiece(0, 0);
        game.newPiecePositionByCapture(new Position(1, 1));
        game.isWhiteKingMated();
        board.printBoard();
//        System.out.println("------------------");
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
//            piecesPositionsCheckingWhiteKing(board);
//            piecesPositionsCheckingBlackKing(board);
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
//            piecesPositionsCheckingWhiteKing(board);
//            piecesPositionsCheckingBlackKing(board);
//        }
//    }

