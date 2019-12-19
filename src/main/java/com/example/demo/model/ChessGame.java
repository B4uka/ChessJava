package com.example.demo.model;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Position;
import com.example.demo.model.pieces.Piece;

import java.io.IOException;

public class ChessGame {

    public static Board board;
    public Piece currentlySelected;
    public PossibleActions possibleActions;
    public Position position;

    public ChessGame () {
        this.board = new Board();
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
            System.out.println(row + "\t" +  column +  "\t there is no piece on this position!");
        }
        return possibleActions;
    }

    public void newPiecePositionByMove (Position newPosition) {

        if(possibleActions.getPossibleMoves().contains(newPosition)) {
            board.setEmpty(currentlySelected.getPosition());
            currentlySelected.setPosition(newPosition);
            board.setPiece(currentlySelected);

            System.out.println("------------------------------");
            select(newPosition.getRow(), newPosition.getColumn());

        } else {
            System.out.println("cant move there");
        }
        board.printBoard();
        System.out.println("----------");
        System.out.println(possibleActions.isChecked());
        System.out.println("----------");
    }

    public void newPiecePositionByCapture (Position newPosition) {
        if(possibleActions.getPossibleCaptures().contains(newPosition)) {
            board.setEmpty(currentlySelected.getPosition());
            currentlySelected.setPosition(newPosition);
            board.setPiece(currentlySelected);
            System.out.println();

            System.out.println("------------------------------");
            select(newPosition.getRow(), newPosition.getColumn());
        } else {
            System.out.println("cant move there");
        }
        board.printBoard();
        System.out.println("----------");

        this.board.getWhiteKingPosition();
        this.board.getBlackKingPosition();
        System.out.println(board.whiteKingPosition.getRow());


        System.out.println(possibleActions.isChecked());
        System.out.println("----------");
    }

    public static void main (String[] args) throws IOException {

        ChessGame game = new ChessGame();

        game.select(4, 4);
        game.newPiecePositionByMove(new Position(2,2));

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
