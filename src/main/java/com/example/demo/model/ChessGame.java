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

    public ChessGame () {
        this.board = new Board();
    }

    public boolean isWhiteKingChecked (Board board) {
        this.board = board;
        this.board.getWhiteKingPosition();
        King whiteKing = new King(board.getWhiteKingPosition(), Color.WHITE);

        try {
            System.out.println("Positions of pieces that are checking white king: ");
            whiteKingCheckedPositions = whiteKing.piecesPositionsCheckingWhiteKing(board);
            whiteKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
        } catch (NullPointerException e) {
            System.out.println("Probably no check for the WhiteKing");
        }
        if (whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isBlackKingChecked (Board board) {
        this.board = board;
        this.board.getBlackKingPosition();
        King blackKing = new King(board.getBlackKingPosition(), Color.BLACK);

        try {
            System.out.println("Positions of pieces that are checking black king: ");
            blackKingCheckedPositions = blackKing.piecesPositionsCheckingBlackKing(board);
            blackKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
        } catch (NullPointerException e) {
            System.out.println("Probably no check for the BlackKing");
        }
        if (blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()) {
            return false;
        }
        return true;
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
        System.out.println("Is white king checked? " + isWhiteKingChecked(board));
//        System.out.println("Is white king mated? " + possibleActions.isMated());
//        System.out.println("Is white king stalamated? " + possibleActions.isStalamated());
        System.out.println("__________________________________________________________________________________________");
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
        System.out.println("Is black king checked? " + isBlackKingChecked(board));
//        System.out.println("Is black king mated? " + possibleActions.isMated());
//        System.out.println("Is black king stalameted? " + possibleActions.isStalamated());
        System.out.println("__________________________________________________________________________________________");
        return possibleActions;
    }
// TODO probably not good method for searching for the mate
    public boolean isMate () {
        allBlackPiecesPossibleActions();
        allWhitePiecesPossibleActions();

        for (Position test : possibleActions.possibleMoves) {
            newPiecePositionByMove(test);
            newPiecePositionByCapture(test);
            if (!isBlackKingChecked(board)) {
                break;
            }return true;
        }
        for (Position test : possibleActions.possibleMoves) {
            newPiecePositionByMove(test);
            newPiecePositionByCapture(test);
            isWhiteKingChecked(board);
            if (!isWhiteKingChecked(board)) {
                break;
            }return true;
        }
        System.out.println("KING IS MATED_______________________________!!!!!!!!!!!!!!!!!!!!!!");
        return false;
    }


    // -> mainController
    public PossibleActions selectWhitePiece (int row, int column) {
        isWhiteKingChecked(board);
        currentlySelected = board.getWhitePiece(row, column);
        try {
            possibleActions = currentlySelected.generatePossibleActions(board);
            // ALL POSSIBLE ACTIONS WILL BE SHOWN IF YOU WILL USE LINES BELOW:
            System.out.println("__________________________________________________________________________________________");
            System.out.println("White " + currentlySelected.getClass().getSimpleName() +" from: "  + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
            System.out.println();
            //
        } catch (NullPointerException e) {
            System.out.println(row + "\t" + column + "\t there is no WHITE piece on this position!");
        }
        return possibleActions;
    }

    // -> mainController
    public PossibleActions selectBlackPiece (int row, int column) {
        isWhiteKingChecked(board);
        currentlySelected = board.getBlackPiece(row, column);
        try {
            possibleActions = currentlySelected.generatePossibleActions(board);
            // ALL POSSIBLE ACTIONS WILL BE SHOWN IF YOU WILL USE LINES BELOW:
            System.out.println("__________________________________________________________________________________________");
            System.out.println("Black " + currentlySelected.getClass().getSimpleName() +" from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
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

        isWhiteKingChecked(board);
        isBlackKingChecked(board);
        return possibleActions;
    }

    public void newPiecePositionByMove (Position newPosition) {
        System.out.println("NEXT MOVE: ");
        if (this.possibleActions.getPossibleMoves().contains(newPosition)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(newPosition);
            board.setPiece(this.currentlySelected);
            piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
//            System.out.println("wierszNowa:" + piecePositionNEW.getRow() + "kolumnaNowa: " + piecePositionNEW.getColumn());
//            System.out.println("wierszStara:" + piecePositionOLD.getRow() + "kolumnaStara: " + piecePositionOLD.getColumn());
            isWhiteKingChecked(board);
            isBlackKingChecked(board);

            if ((!whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty() && currentlySelected.getColor() == Color.WHITE)
                    || (!blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty() && currentlySelected.getColor() == Color.BLACK)) {
                piecePositionNEW = piecePositionOLD;
                board.setEmpty(this.currentlySelected.getPosition());
                currentlySelected.setPosition(piecePositionNEW);
                board.setPiece(currentlySelected);
                System.out.println("cant move there!");
//                System.out.println("wierszNowa:" + piecePositionNEW.getRow() + "kolumnaNowa: " + piecePositionNEW.getColumn());
//                System.out.println("wierszStara:" + piecePositionOLD.getRow() + "kolumnaStara: " + piecePositionOLD.getColumn());
            }
            board.printBoard();
        }
    }

    public void newPiecePositionByCapture (Position newPosition) {
        System.out.println("NEXT MOVE: ");
        if (this.possibleActions.getPossibleCaptures().contains(newPosition)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(newPosition);
            board.setPiece(this.currentlySelected);
            piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
//            System.out.println("wierszNowa:" + piecePositionNEW.getRow() + "kolumnaNowa: " + piecePositionNEW.getColumn());
//            System.out.println("wierszStara:" + piecePositionOLD.getRow() + "kolumnaStara: " + piecePositionOLD.getColumn());
            isWhiteKingChecked(board);
            isBlackKingChecked(board);
            if ((!whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty() && currentlySelected.getColor() == Color.WHITE)
                    || (!blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty() && currentlySelected.getColor() == Color.BLACK)) {
                piecePositionNEW = piecePositionOLD;
                board.setEmpty(this.currentlySelected.getPosition());
                currentlySelected.setPosition(piecePositionNEW);
                board.setPiece(currentlySelected);
                System.out.println("cant move there!");
//                System.out.println("wierszNowa:" + piecePositionNEW.getRow() + "kolumnaNowa: " + piecePositionNEW.getColumn());
//                System.out.println("wierszStara:" + piecePositionOLD.getRow() + "kolumnaStara: " + piecePositionOLD.getColumn());
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

        game.selectWhitePiece(2,1);
        game.newPiecePositionByMove(new Position(1,1));
        game.allBlackPiecesPossibleActions();

        System.out.println("------------------");

        game.selectBlackPiece(0,0);
        game.newPiecePositionByCapture(new Position(1,1));
        game.allWhitePiecesPossibleActions();


//        game.newPiecePositionByMove(new Position(7,2));
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

