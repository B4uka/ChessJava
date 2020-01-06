package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public class Knight extends Piece {

    public Knight (Position position, Color color) {
        super(position, color);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

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
        else if (this.color == Color.BLACK) {
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
                if (test.isOnBoard() && !board.isOccupiedByColor(test, Color.BLACK) && !board.isOccupiedByColor(test, Color.WHITE)) {
                    possibleActions.addPossibleMove(test);
                } else if (test.isOnBoard() && board.isOccupiedByColor(test, Color.WHITE) && !board.isOccupiedByKing(test, Color.WHITE)) {
                    possibleActions.addPossibleCapture(test);
                } else if (test.isOnBoard() && board.isOccupiedByKing(test, Color.WHITE)) {
                    possibleActions.addPossibleChecks(test);
                    System.out.println("CHECK!!!!!!!!!!!");
                }
            }
        }
        return possibleActions;
    }
}