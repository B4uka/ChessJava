package com.example.demo.model.board;

import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

import java.util.ArrayList;

public class PossibleActions {

    public ArrayList<Position> possibleMoves, possibleCaptures, possibleChecks, listOfPiecesPositionsWhichAreCheckingTheKing, possibleMovesandCaptures;
    public Color color;

    public Position position;

    public PossibleActions () {
        this.possibleCaptures = new ArrayList<>();
        this.possibleMoves = new ArrayList<>();
        this.possibleChecks = new ArrayList<>();
        this.listOfPiecesPositionsWhichAreCheckingTheKing = new ArrayList<>();
    }

    public boolean isMated () {
        return this.possibleMoves.isEmpty() && this.possibleCaptures.isEmpty() && !listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    public boolean isStalamated () {
        return this.possibleMoves.isEmpty() && this.possibleCaptures.isEmpty() && !listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    public boolean isChecked () {
        return !listOfPiecesPositionsWhichAreCheckingTheKing.isEmpty();
    }

    public void addPossibleCapture (Position position) {
        this.possibleCaptures.add(position);
    }

    public void addPossibleMove (Position position) {
        this.possibleMoves.add(position);
    }

    public void addPossibleChecks (Position position) {
        this.possibleChecks.add(position);
    }

    public void addPiecesPositionsWhichAreCheckingTheKing (Position position) {
        this.listOfPiecesPositionsWhichAreCheckingTheKing.add(position);
    }

    public ArrayList<Position> getPossibleCaptures () {
        return possibleCaptures;
    }

    public ArrayList<Position> getPossibleMoves () {
        return possibleMoves;
    }

    public ArrayList<Position> getPossibleChecks () {
        return possibleChecks;
    }

    public ArrayList<Position> getAllPossibleMovesAndCaptures () {
        possibleMoves.addAll(possibleCaptures);
        possibleMovesandCaptures = possibleMoves;
        return possibleMovesandCaptures;
    }

    public Position getPosition () {
        return position;
    }

    public void printPossibleMoves () {
        for (Position position : possibleMoves) {
            System.out.print("Possible moves: " + position.getRow() + "\t" + position.getColumn() + "\t");
        }
    }

    public void printPossibleCaptures () {
        for (Position position : possibleCaptures) {
            System.out.print("Possible captures: " + position.getRow() + "\t" + position.getColumn() + "\t");
        }
    }

    public void printPossibleChecks () {
        for (Position position : possibleChecks) {
            System.out.print("Possible checks: " + position.getRow() + "\t" + position.getColumn() + "\t");
        }
    }

    public void printPositionsofPiecesThatAreCheckingKing () {
        for (Position position : listOfPiecesPositionsWhichAreCheckingTheKing) {
            System.out.println("Row: " + position.getRow() + "\t Column: " + position.getColumn());
        }
    }
}