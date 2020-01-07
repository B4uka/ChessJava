package com.example.demo.model.board;

import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

import java.util.ArrayList;

public class PossibleActions {

    public ArrayList<Position> possibleMoves, possibleCaptures, possibleChecks, listOfPiecesPositionsWhichAreCheckingTheKing;
    public Color color;

    public Position position;

    public PossibleActions () {
        this.possibleCaptures = new ArrayList<>();
        this.possibleMoves = new ArrayList<>();
        this.possibleChecks = new ArrayList<>();
        this.listOfPiecesPositionsWhichAreCheckingTheKing = new ArrayList<>();
    }
    public boolean isChecked(){
        return !this.possibleChecks.isEmpty();
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
    public void addPiecesPositionsWhichAreCheckingTheKing (Position position){
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

    public Position getPosition () {
        return position;
    }


    public void printPossibleMoves () {
        for (Position position : possibleMoves) {
            System.out.println("Possible moves: " + position.getRow() + "\t" + position.getColumn());
        }
    }
    public void printPossibleCaptures () {
        for (Position position : possibleCaptures) {
            System.out.println("Possible captures: " + position.getRow() + "\t" + position.getColumn());
        }
    }
    public void printPossibleChecks () {
        for (Position position : possibleChecks) {
            System.out.println("Possible checks: " + position.getRow() + "\t" + position.getColumn());
        }
    }
    public void printPositionsofPiecesThatAreCheckingKing () {
        for (Position position : listOfPiecesPositionsWhichAreCheckingTheKing) {
            System.out.println("Row: " + position.getRow() + "\t Column: " + position.getColumn());
        }
    }
}