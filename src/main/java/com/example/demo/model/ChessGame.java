package com.example.demo.model;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;
import com.example.demo.model.pieces.Pawn;
import com.example.demo.model.pieces.Piece;

import java.io.IOException;

public class ChessGame {

    public static Board board;
    public Piece currentlySelected;
    public PossibleActions possibleActions;
    public Position position, piecePositionNEW;
    public Color color;

    public ChessGame () {
        this.board = new Board();
    }

    public PossibleActions selectWhitePiece (int row, int column) {

            currentlySelected = board.getWhitePiece(row, column);
            try {
                possibleActions = currentlySelected.generatePossibleActions(board);
                System.out.println("Piece from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
                possibleActions.printPossibleMoves();
                possibleActions.printPossibleCaptures();
                possibleActions.printPossibleChecks();
            } catch (NullPointerException e) {
                System.out.println(row + "\t" + column + "\t there is no piece on this position!");
            }
        return possibleActions;
    }

    public PossibleActions selectBlackPiece (int row, int column) {
            currentlySelected = board.getBlackPiece(row, column);
            try {
                possibleActions = currentlySelected.generatePossibleActions(board);
                System.out.println("Piece from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
                possibleActions.printPossibleMoves();
                possibleActions.printPossibleCaptures();
                possibleActions.printPossibleChecks();
            } catch (NullPointerException e) {
                System.out.println(row + "\t" + column + "\t there is no piece on this position!");
            }
        return possibleActions;
    }
    public PossibleActions select (int row, int column) {
        currentlySelected = board.getPiece(row, column);
        try {
            possibleActions = currentlySelected.generatePossibleActions(board);
            System.out.println("Piece from: " + currentlySelected.getPosition().getRow() + "\t" + currentlySelected.getPosition().getColumn());
            possibleActions.printPossibleMoves();
            possibleActions.printPossibleCaptures();
            possibleActions.printPossibleChecks();
        } catch (NullPointerException e) {
            System.out.println(row + "\t" + column + "\t there is no piece on this position!");

        }
        printActualPositionAndGetPositionOfBothKings();
        System.out.println("----------");
        areKingsUnderCheck(this.possibleActions);
        System.out.println("----------");
        return possibleActions;
    }

    public void newPiecePositionByMove (Position newPosition) {

        if (this.possibleActions.getPossibleMoves().contains(newPosition)) {
            board.setEmpty(this.currentlySelected.getPosition());
            this.currentlySelected.setPosition(newPosition);
            board.setPiece(this.currentlySelected);

            this.piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            System.out.println("------------------------------");

            // We will not checking if opposite king will be under check if we will make our next move - PAWN PROMOTION
            // To not get out of the board
            if (currentlySelected.getClass() == Pawn.class) {
                if (currentlySelected.getPosition().getRow() != 0 || currentlySelected.getPosition().getRow() != 7) {
                    select(newPosition.getRow(), newPosition.getColumn());
                }else {
                    System.out.println("Problem");
                }
            }else {
                System.out.println("Problem");
            }

        } else {
            System.out.println("cant move there");
        }
        printActualPositionAndGetPositionOfBothKings();
        System.out.println("----------");
        areKingsUnderCheck(this.possibleActions);
        System.out.println("----------");
    }

    public void newPiecePositionByCapture (Position newPosition) {
        if (possibleActions.getPossibleCaptures().contains(newPosition)) {
            board.setEmpty(currentlySelected.getPosition());
            currentlySelected.setPosition(newPosition);
            board.setPiece(currentlySelected);

            this.piecePositionNEW = new Position(currentlySelected.getPosition().getRow(), currentlySelected.getPosition().getColumn());

            System.out.println();
            System.out.println("------------------------------");
            // We will not checking if opposite king will be under check if we will make our next move - PAWN PROMOTION
            // To not get out of the board
            if (currentlySelected.getClass() != Pawn.class && (currentlySelected.getPosition().getRow() != 7 || currentlySelected.getPosition().getRow() != 0)) {
                select(newPosition.getRow(), newPosition.getColumn());
            }

        } else {
            System.out.println("cant move there");
        }
        printActualPositionAndGetPositionOfBothKings();
        System.out.println("----------");
        areKingsUnderCheck(this.possibleActions);
        System.out.println("----------");

    }
    public static boolean areKingsUnderCheck(PossibleActions possibleActions){
        possibleActions.getPossibleChecks();
        System.out.println(possibleActions.isChecked());
        return possibleActions.isChecked();
    }

    public static void printActualPositionAndGetPositionOfBothKings(){
        board.printBoard();
        System.out.println("----------");

        board.getWhiteKingPosition();
        board.getBlackKingPosition();
        System.out.println("White King row: " + board.whiteKingPosition.getRow());
        System.out.println("White King column: " + board.whiteKingPosition.getColumn());
        System.out.println("Black King row: " + board.blackKingPosition.getRow());
        System.out.println("Black King column: " + board.blackKingPosition.getColumn());

        System.out.println("----------");
    }

    public static void main (String[] args) throws IOException {

        ChessGame game = new ChessGame();

        game.select(1, 3);

        game.newPiecePositionByMove(new Position(2, 3));

//        ArrayList<String> fieldsToMark = new ArrayList<>();
//
//        ArrayList<Position> allPossibleActions = new ArrayList<>();
//
//
//        game.select(Field.getFieldByString("A2").getRow(), Field.getFieldByString("A2").getColumn());
//
//        ArrayList<Position> possibleMovesToMake  = game.possibleActions.possibleMoves;
//        ArrayList<Position> possibleCapturesToMake = game.possibleActions.possibleCaptures;
//
//        allPossibleActions.addAll(possibleCapturesToMake);
//        allPossibleActions.addAll(possibleMovesToMake);
//
//        for (Position position: allPossibleActions) {
//            fieldsToMark.add(Field.getFieldByPosition(position.getRow(), position.getColumn()));
//        }
//
//        System.out.println(fieldsToMark.contains("A3"));
//        System.out.println(fieldsToMark.size());
//        for (String string: fieldsToMark){
//            System.out.println(string);
//        }
    }
}
