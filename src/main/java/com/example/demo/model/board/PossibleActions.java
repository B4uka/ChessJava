package com.example.demo.model.board;

import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

import java.util.ArrayList;

public class PossibleActions {

    public ArrayList<Position> possibleCaptures;
    public ArrayList<Position> possibleMoves;
    public ArrayList<Position> possibleChecks;
    public Color color;

    public Position position;

    public PossibleActions () {
        this.possibleCaptures = new ArrayList<>();
        this.possibleMoves = new ArrayList<>();
        this.possibleChecks = new ArrayList<>();
    }
    public boolean isChecked(){
        return !this.possibleChecks.isEmpty();
    }
    public PossibleActions isWhiteKingChecked(Board board) {

        PossibleActions possibleActions = new PossibleActions();
        Position[] kingedCheckedByKnight = new Position[8];
        if (this.color == Color.WHITE) {
            Position[] knightsPossibleMove = new Position[8];
            knightsPossibleMove[0] = this.position.getNewPositionByVector(1, 2);
            knightsPossibleMove[1] = this.position.getNewPositionByVector(1, -2);
            knightsPossibleMove[2] = this.position.getNewPositionByVector(2, 1);
            knightsPossibleMove[3] = this.position.getNewPositionByVector(2, -1);
            knightsPossibleMove[4] = this.position.getNewPositionByVector(-1, 2);
            knightsPossibleMove[5] = this.position.getNewPositionByVector(-1, -2);
            knightsPossibleMove[6] = this.position.getNewPositionByVector(-2, 1);
            knightsPossibleMove[7] = this.position.getNewPositionByVector(-2, -1);
            for (Position test : knightsPossibleMove) {
                if (test.isOnBoard() && !board.isOccupiedByColor(test, Color.WHITE) && !board.isOccupiedByColor(test, Color.BLACK)) {
                    possibleActions.addPossibleMove(test);
                } else if (test.isOnBoard() && board.isOccupiedByColor(test, Color.BLACK) && !board.isOccupiedByKing(test, Color.BLACK)) {
                    possibleActions.addPossibleCapture(test);
                } else if (test.isOnBoard() && board.isOccupiedByKing(test, Color.BLACK)) {
                    possibleActions.addPossibleChecks(test);
                    System.out.println("CHECK!!!!!!!!!!!");
                }
            }
        }
        return possibleActions;

    }    public PossibleActions isBlackKingChecked(Board board) {

        PossibleActions possibleActions = new PossibleActions();
        Position[] kingedCheckedByKnight = new Position[8];

        if (this.color == Color.WHITE) {
            Position[] knightsPossibleMove = new Position[8];
            knightsPossibleMove[0] = this.position.getNewPositionByVector(1, 2);
            knightsPossibleMove[1] = this.position.getNewPositionByVector(1, -2);
            knightsPossibleMove[2] = this.position.getNewPositionByVector(2, 1);
            knightsPossibleMove[3] = this.position.getNewPositionByVector(2, -1);
            knightsPossibleMove[4] = this.position.getNewPositionByVector(-1, 2);
            knightsPossibleMove[5] = this.position.getNewPositionByVector(-1, -2);
            knightsPossibleMove[6] = this.position.getNewPositionByVector(-2, 1);
            knightsPossibleMove[7] = this.position.getNewPositionByVector(-2, -1);
            for (Position test : knightsPossibleMove) {
                if (test.isOnBoard() && !board.isOccupiedByColor(test, Color.WHITE) && !board.isOccupiedByColor(test, Color.BLACK)) {
                    possibleActions.addPossibleMove(test);
                } else if (test.isOnBoard() && board.isOccupiedByColor(test, Color.BLACK) && !board.isOccupiedByKing(test, Color.BLACK)) {
                    possibleActions.addPossibleCapture(test);
                } else if (test.isOnBoard() && board.isOccupiedByKing(test, Color.BLACK)) {
                    possibleActions.addPossibleChecks(test);
                    System.out.println("CHECK!!!!!!!!!!!");
                }
            }
        }
        return possibleActions;
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
}