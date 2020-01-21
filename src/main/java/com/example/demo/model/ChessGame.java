package com.example.demo.model;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;
import com.example.demo.model.pieces.King;
import com.example.demo.model.pieces.Piece;

public class ChessGame {

    public static Board board;
    public Piece currentlySelected, temporaryBeaten, currentlySelectedRookForCastle;
    public PossibleActions possibleActions, blackKingCheckedPositions, whiteKingCheckedPositions, possibleMOVESorCAPTURES;
    public Position position, piecePositionNEW, piecePositionOLD, rookCastlingMove, rookCastlingMoveOldPosition;
    public Color color;
    public static Boolean blackCastled, whiteCastled;
    private int rookCountMove;
    private Piece[] rookOnBoard;

    public ChessGame () {
        board = new Board();
        blackCastled = false;
        whiteCastled = false;
    }

    public static boolean isWhiteKingChecked () {
        PossibleActions whiteKingCheckedPositions = new PossibleActions();
        King whiteKing = new King(board.getWhiteKingPosition(), Color.WHITE, "&#9812;", 0);
        try {
            whiteKingCheckedPositions = whiteKing.piecesPositionsCheckingWhiteKing(board);
            if (!whiteKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()) {
                System.out.println("Positions of pieces that are checking white king: ");
                whiteKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();
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
        King blackKing = new King(board.getBlackKingPosition(), Color.BLACK, "&#9818;", 0);
        try {
            blackKingCheckedPositions = blackKing.piecesPositionsCheckingBlackKing(board);
            if (!blackKingCheckedPositions.listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty()) {
                System.out.println("Positions of pieces that are checking black king: ");
                blackKingCheckedPositions.printPositionsOfPiecesThatAreCheckingKing();
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
        possibleMOVESorCAPTURES = new PossibleActions();

        for (Position test : board.whitePiecesPositions) {
            selectWhitePiece(test.getRow(), test.getColumn());
            for (Position position : possibleActions.getPossibleMoves()) {
                if (isCheckAfterTheMove(position)) {
                    continue;
                } else {
                    possibleMOVESorCAPTURES.addPossibleMove(position);
                }
//                    System.out.println("mozliwe ruchy: " + possibleMOVESorCAPTURES.getPosition().getRow() + possibleMOVESorCAPTURES.getPosition().getColumn());
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (isCheckAfterTheCapture(position)) {
                    continue;
                } else {
                    possibleMOVESorCAPTURES.addPossibleCapture(position);
//                    System.out.println("mozliwe ruchy: " + possibleMOVESorCAPTURES.getPosition().getRow() + possibleMOVESorCAPTURES.getPosition().getColumn());
                }
            }
        }

        return possibleMOVESorCAPTURES;
    }

    // TODO all actions that black player can do
    public PossibleActions allBlackPiecesPossibleActions () {
        board.getAllBlackPiecesPosition();
        possibleMOVESorCAPTURES = new PossibleActions();

        for (Position test : board.blackPiecesPositions) {
            selectBlackPiece(test.getRow(), test.getColumn());
            for (Position position : possibleActions.getPossibleMoves()) {
                if (isCheckAfterTheMove(position)) {
                    continue;
                } else {
                    possibleMOVESorCAPTURES.addPossibleMove(position);
                }
//                    System.out.println("mozliwe ruchy: " + possibleMOVESorCAPTURES.getPosition().getRow() + possibleMOVESorCAPTURES.getPosition().getColumn());
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (isCheckAfterTheCapture(position)) {
                    continue;
                } else {
                    possibleMOVESorCAPTURES.addPossibleCapture(position);
//                    System.out.println("mozliwe ruchy: " + possibleMOVESorCAPTURES.getPosition().getRow() + possibleMOVESorCAPTURES.getPosition().getColumn());
                }
            }
        }

        return possibleMOVESorCAPTURES;
    }

    // TODO probably not good method for searching for the mate

    public Boolean isKingMated (Color color) {
        if (color == Color.WHITE) {
            try {
                allWhitePiecesPossibleActions();
            } catch (NullPointerException e) {
                return true;
            }
            System.out.println(possibleMOVESorCAPTURES.getAllPossibleMovesAndCaptures().isEmpty());
            return possibleMOVESorCAPTURES.getAllPossibleMovesAndCaptures().isEmpty();
        } else if (color == Color.BLACK) {
            try {
                allBlackPiecesPossibleActions();
            } catch (NullPointerException e) {
                return true;
            }
            System.out.println(possibleMOVESorCAPTURES.getAllPossibleMovesAndCaptures().isEmpty());
            return possibleMOVESorCAPTURES.getAllPossibleMovesAndCaptures().isEmpty();
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

    public PossibleActions selectPiece (int row, int column) {
        currentlySelected = board.getPiece(row, column);
        generateActionsForCurrentlySelected();
        System.out.println();
        return possibleActions;

    }

    public PossibleActions selectWhitePieceforValidation (int row, int column) {
        currentlySelected = board.getWhitePiece(row, column);
        return possibleActions;
    }

    public PossibleActions selectBlackPieceValidation (int row, int column) {
        currentlySelected = board.getBlackPiece(row, column);
        return possibleActions;
    }

    private void generateActionsForCurrentlySelected () {
        try {
            possibleActions = currentlySelected.generatePossibleActions(board);
//            System.out.println();
            System.out.println(currentlySelected.getColor() + " " + currentlySelected.getClass().getSimpleName() + " from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
            possibleActions.printPossibleCastlingKingMoves();
        } catch (NullPointerException e) {
//            System.out.println();
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
            //Piece move
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
            //castling -> King Move
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


    public void rookMoveWhenCastling () {
        //castle Rook Move
        Piece[] rookOnBoard = new Piece[4];
        rookOnBoard[0] = (board.getPiece(0, 0));
        rookOnBoard[1] = (board.getPiece(0, 7));
        rookOnBoard[2] = (board.getPiece(7, 0));
        rookOnBoard[3] = (board.getPiece(7, 7));
//        {&& board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).getCountMoves() == 0)
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
            System.out.println("ile razy ruszyla sie wieza?: " + rookCountMove);
        } else if (!this.possibleActions.getKingCastlingActions().contains(piecePositionNEW)) {
            revertNewMove(piecePositionNEW);
        }
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
            board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).countMoveAdd();
            System.out.println("ile razy ruszyla sie: " + currentlySelected.getClass().getSimpleName() + " " + currentlySelected.getPosition().getRow() + " " + currentlySelected.getPosition().getColumn()
                    + " -> " + board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).getCountMoves());
            board.printBoard();
            return true;
        } else if (rookCountMove == 0 && currentlySelected.getCountMoves() == 0) {
            if (newCastlingMoveIfIsPossible(piecePositionNEW)) {
                if ((isWhiteKingChecked() && currentlySelected.getColor() == Color.WHITE)
                        || (isBlackKingChecked() && currentlySelected.getColor() == Color.BLACK)) {
                    revertNewMove(piecePositionOLD);
                    System.out.println("canT move there!");
                    return false;
                }//rook move when castling
                if (currentlySelected.getColor() == Color.WHITE) {
                    rookMoveWhenCastling();
                    board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).countMoveAdd();
                    whiteCastled = true;
                } else if (currentlySelected.getColor() == Color.BLACK) {
                    rookMoveWhenCastling();
                    board.getPiece(currentlySelectedRookForCastle.getPosition().getRow(), currentlySelectedRookForCastle.getPosition().getColumn()).countMoveAdd();
                    blackCastled = true;
                }
                board.printBoard();
                board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).countMoveAdd();
                System.out.println("ile razy ruszył sie: " + currentlySelected.getClass().getSimpleName() + " " + currentlySelected.getPosition().getRow() + " " + currentlySelected.getPosition().getColumn()
                        + " -> " + board.getPiece(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn()).getCountMoves());
                return true;
            }
            System.out.println("canT move there!");
            return false;
        }
        System.out.println("canT move there!");
        return false;
    }

    public Boolean isCheckAfterTheCapture (Position piecePositionNEW) {
//        System.out.println("NEXT MOVE: ");
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
        System.out.println("NEXT MOVE: ");
        System.out.println(currentlySelected.getColor());
        if (newCaptureIfIsPossible(piecePositionNEW)) {
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
//        game.allBlackPiecesPossibleActions();
//        game.selectBlackPiece(0, 4);
//        if (game.newPiecePositionByMove(new Position(0, 2))) {
//           System.out.println(game.isKingMated(Color.BLACK));
//        }
//        game.selectBlackPiece(0, 2);
//        if (game.newPiecePositionByMove(new Position(0, 1))) {
//            System.out.println(game.isKingMated(Color.BLACK));
//        }
//        game.selectBlackPiece(0, 3);
//        if (game.newPiecePositionByMove(new Position(0, 5))) {
//            System.out.println(game.isKingMated(Color.BLACK));
//        }
//        game.selectWhitePiece(7, 0);
//        if (game.newPiecePositionByMove(new Position(7, 1))) {
//            System.out.println(game.isKingMated(Color.WHITE));
//        }
//        game.allWhitePiecesPossibleActions();
//        game.selectWhitePiece(6, 3);
//        if (game.newPiecePositionByMove(new Position(4, 3))) {
//            System.out.println(game.isKingMated(Color.BLACK));
//        }
//        game.selectBlackPiece(1, 3);
//        if (game.newPiecePositionByMove(new Position(3, 3))) {
//            System.out.println(game.isKingMated(Color.WHITE));
//        }
//        game.selectBlackPiece(0, 4);
//        if (game.newPiecePositionByMove(new Position(1, 3))) {
//            System.out.println(game.isKingMated(Color.WHITE));
//        }
//        board.printBoard();
////       game.allWhitePiecesPossibleActions();
//        game.selectWhitePiece(7, 3);
//        if (game.newPiecePositionByMove(new Position(6, 3))) {
//            System.out.println(game.isKingMated(Color.BLACK));
//        }
//        game.selectBlackPiece(1, 3);
//        if (game.newPiecePositionByMove(new Position(2, 4))) {
//            System.out.println(game.isKingMated(Color.WHITE));
//        }
//        game.selectWhitePiece(6, 3);
//        if (game.newPiecePositionByMove(new Position(5, 4))) {
//            System.out.println(game.isKingMated(Color.BLACK));
//        }
//        board.printBoard();

//        game.selectWhitePiece(6, 0);
//        if (game.newPiecePositionByMove(new Position(4, 0))) {
//            System.out.println(game.isKingMated(Color.BLACK));
//        }
//        game.selectBlackPiece(1, 1);
//        if (game.newPiecePositionByMove(new Position(3, 1))) {
//            System.out.println(game.isKingMated(Color.WHITE));
//            board.printBoard();
//        }


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
        game.selectBlackPiece(0, 3);
        if (game.newPiecePositionByMove(new Position(4, 7))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }
        board.printBoard();
//        board.printBoardWithCodes();
//        System.out.println();
//        board.getBoardWithPiecesActualCodes();
//        board.printBoardWithCodes();
//        System.out.println();
//        board.getBoardFieldAndCodes();

//        ArrayList<String> naa = new ArrayList<>();
//        for (Position position : ChessGame.board.getAllBlackPiecesPosition()){
//            naa.add(Field.getFieldByPosition(position.getRow(), position.getColumn()));
//        }
//        String jsonResponse2 = new Gson().toJson(naa);
//        System.out.println();
//        System.out.printf( "JSON: %s", jsonResponse2 );
    }
}
// Todo: tutaj jest dzialajaca metoda, ktora pokazuje czy dalismy szacha!
//        public void newPiecePositionByMove (Position newPosition) {
//            System.out.println("NEXT MOVE: ");
//            if (this.possibleActions.getPossibleMoves().contains(newPosition)) {
//                board.setEmptyByPosition(this.currentlySelected.getPosition());
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
//                board.setEmptyByPosition(currentlySelected.getPosition());
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