package com.example.demo.model;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;
import com.example.demo.model.pieces.King;
import com.example.demo.model.pieces.Piece;

public class ChessGame {

    public static Board board;
    public Piece currentlySelected, temproraryBeaten;
    public PossibleActions possibleActions, blackKingCheckedPositions, whiteKingCheckedPositions;
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
                System.out.println("Positions of pieces that are checking white king: ");
                whiteKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
            } else {
                    System.out.println("King is not checked after this move!");
            }
        } catch (NullPointerException e) {
            System.out.println("Probably no check for the WhiteKing");
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
                System.out.println("Positions of pieces that are checking black king: ");
                blackKingCheckedPositions.printPositionsofPiecesThatAreCheckingKing();
            } else {
                   System.out.println("King is not checked after this move!");
            }
        } catch (NullPointerException e) {
            System.out.println("Probably no check for the BlackKing");
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

    public Boolean isKingMated (Color color) {
        if(color == Color.WHITE) {
            allWhitePiecesPossibleActions();
        }else if (color == Color.BLACK){
            allBlackPiecesPossibleActions();
        }
        for (Position test : possibleActions.getPossibleMoves()) {
            if (isCheckAfterTheMove(test)) {
            }else
                return false;
        }
        for (Position test2: possibleActions.getPossibleCaptures()){
            if(isCheckAfterTheCapture(test2)){
                return true;
            }else {
                System.out.println("BLACK KING IS MATED_______________________________!!!!!!!!!!!!!!!!!!!!!!");
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
            System.out.println();
            System.out.println(currentlySelected.getColor() +" "+ currentlySelected.getClass().getSimpleName() + " from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
        } catch (NullPointerException e) {
            System.out.println(currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn() + "\t there is no piece on this position!");
        }
//        printActualPositionAndGetPositionOfBothKings();
//        System.out.println("----------");
//        areKingsUnderCheck(possibleActions);
//        System.out.println("----------");
    }

    private void revertNewMove (Position piecePositionOLD) {
        this.piecePositionOLD = piecePositionOLD;

        piecePositionNEW = piecePositionOLD;
        board.setEmpty(this.currentlySelected.getPosition());
        currentlySelected.setPosition(piecePositionNEW);
        board.setPiece(currentlySelected);
    }

    private void revertNewMoveAndRestoreTempBeaten(Position piecePositionOLD) {
        revertNewMove(piecePositionOLD);
        board.setPiece(temproraryBeaten);
    }


    private Boolean newCaptureIfIsPossible (Position piecePositionNEW) {
        this.piecePositionNEW = piecePositionNEW;
        temproraryBeaten = board.getPiece(piecePositionNEW.getRow(),piecePositionNEW.getColumn());
        if (this.possibleActions.getPossibleCaptures().contains(piecePositionNEW)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(piecePositionNEW);
            board.setPiece(this.currentlySelected);
            return true;
        } else
            return false;
    }
    private Boolean newMoveIfIsPossible (Position piecePositionNEW) {
        this.piecePositionNEW = piecePositionNEW;

        if (this.possibleActions.getPossibleMoves().contains(piecePositionNEW)) {
            piecePositionOLD = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());
            board.setEmpty(this.currentlySelected.getPosition());
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
            }
            revertNewMove(piecePositionOLD);
            return false;
        }
        return false;
    }
    public boolean newPiecePositionByMove (Position piecePositionNEW) {
        System.out.println("NEXT MOVE: ");
        if (newMoveIfIsPossible(piecePositionNEW)) {
            if ((isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                    || (isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                    revertNewMove(piecePositionOLD);
                System.out.println("canT move there!");
                return false;
            }
            board.printBoard();
            return true;
        }
        System.out.println("canT move there!");
        return false;
    }

    public Boolean isCheckAfterTheCapture (Position piecePositionNEW) {
//        System.out.println("NEXT MOVE: ");

        if (newCaptureIfIsPossible(piecePositionNEW)) {
            if (isBlackKingChecked() && isWhiteKingChecked()) {
                revertNewMove(piecePositionOLD);
                return true;
            }
            if (currentlySelected.getColor() == Color.WHITE) {
                if (isBlackKingChecked()) {
                    revertNewMove(piecePositionOLD);
                    return true;
                }
            } else if (currentlySelected.getColor() == Color.BLACK) {
                if (isWhiteKingChecked()) {
                    revertNewMove(piecePositionOLD);
                    return true;
                }
            } else
                revertNewMove(piecePositionOLD);
            return true;
        } else
            return false;
    }

    public boolean newPiecePositionByCapture (Position piecePositionNEW) {
        System.out.println("NEXT MOVE: ");
        System.out.println(currentlySelected.getColor());
        if (newCaptureIfIsPossible(piecePositionNEW)){
            if (isBlackKingChecked() && isWhiteKingChecked()) {
                revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                System.out.println("cant move there!");
                return false;
            } else if (currentlySelected.getColor() != Color.WHITE) {
                if (isBlackKingChecked()) {
                    System.out.println(currentlySelected.getColor());
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    System.out.println("cant move there!");
                    return false;
                }
            } else if (currentlySelected.getColor() != Color.BLACK) {
                if (isWhiteKingChecked()) {
                    revertNewMoveAndRestoreTempBeaten(piecePositionOLD);
                    System.out.println("cant move there!");
                    return false;
                }
            }
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
//
//        game.selectWhitePiece(1, 1);
//        if (game.newPiecePositionByMove(new Position(1, 0))) {
//            System.out.println(game.isKingMated(Color.WHITE));
//        }
//        game.allBlackPiecesPossibleActions();
//        board.printBoard();
        game.selectBlackPiece(0, 4);
        if(game.newPiecePositionByCapture(new Position(1, 5))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }

        board.printBoard();

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
//                // We will not check if opposite king will be under the check after we will make our next move - PAWN PROMOTION
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

