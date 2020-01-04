package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public class Pawn extends Piece {

    public Pawn (Position position, Color color) {
        super(position, color);
    }


    @Override
    public PossibleActions generatePossibleActions (Board board) {

        PossibleActions possibleActions = new PossibleActions();
        Position[] pawnPossibleMove = new Position[2];
        Position[] pawnPossibleCature = new Position[2];

        if (this.color == Color.BLACK) {
            pawnPossibleMove[0] = this.position.getNewPositionByVector(1, 0);
            if (!board.isOccupied(pawnPossibleMove[0])) {
                possibleActions.addPossibleMove(pawnPossibleMove[0]);
                if (this.position.getRow() == 1) {
                    // Statement is here because if it would be at the beginning, we would get "out of boundary exception"
                    pawnPossibleMove[1] = this.position.getNewPositionByVector(2, 0);
                    if (!board.isOccupied(pawnPossibleMove[1])) {
                        possibleActions.addPossibleMove(pawnPossibleMove[1]);
                    }
                }
            }
            pawnPossibleCature[0] = this.position.getNewPositionByVector(1, 1);
            pawnPossibleCature[1] = this.position.getNewPositionByVector(1, -1);
            if (pawnPossibleCature[0].getRow() != 7 && pawnPossibleCature[0].isOnBoard() && board.isOccupiedByColor(pawnPossibleCature[0], Color.WHITE)) {
                possibleActions.addPossibleCapture(pawnPossibleCature[0]);
            }
            if (pawnPossibleCature[1].getRow() != 7 && pawnPossibleCature[1].isOnBoard() && board.isOccupiedByColor(pawnPossibleCature[1], Color.WHITE)) {
                possibleActions.addPossibleCapture(pawnPossibleCature[1]);
            }
            //Capture on the last line
            if (pawnPossibleCature[0].getRow() == 7 && pawnPossibleCature[0].isOnBoard() && board.isOccupiedByColor(pawnPossibleCature[0], Color.WHITE)) {
                possibleActions.addPossibleCapture(pawnPossibleCature[0]);
            }
            if (pawnPossibleCature[1].getRow() == 7 && pawnPossibleCature[1].isOnBoard() && board.isOccupiedByColor(pawnPossibleCature[1], Color.WHITE)) {
                possibleActions.addPossibleCapture(pawnPossibleCature[1]);
            }
            for (Position test : possibleActions.getPossibleMoves()) {
                if (test.isOnBoard() && board.isOccupiedByKing(test, Color.WHITE)) {
                    possibleActions.addPossibleChecks(test);
                    System.out.println("CHECK!!!!!!!!!!!");
                }
            }
            for (Position test : possibleActions.getPossibleCaptures()) {
                if (test.isOnBoard() && board.isOccupiedByKing(test, Color.WHITE)) {
                    possibleActions.addPossibleChecks(test);
                    System.out.println("CHECK!!!!!!!!!!!");
                }
            }
        }

        if (this.color == Color.WHITE) {
            pawnPossibleMove[0] = this.position.getNewPositionByVector(-1, 0);
            if (!board.isOccupied(pawnPossibleMove[0])) {
                possibleActions.addPossibleMove(pawnPossibleMove[0]);
                if (this.position.getRow() == 6) {
                    pawnPossibleMove[1] = this.position.getNewPositionByVector(-2, 0);
                    // Statement is here because if it would be at the beginning, we would get "out of boundary exception"
                    if (!board.isOccupied(pawnPossibleMove[1])) {
                        possibleActions.addPossibleMove(pawnPossibleMove[1]);
                    }
                }
            }

            pawnPossibleCature[0] = this.position.getNewPositionByVector(-1, 1);
            pawnPossibleCature[1] = this.position.getNewPositionByVector(-1, -1);
            if (pawnPossibleCature[0].getRow() != 0 && pawnPossibleCature[0].isOnBoard() && board.isOccupiedByColor(pawnPossibleCature[0], Color.BLACK)) {
                possibleActions.addPossibleCapture(pawnPossibleCature[0]);
            }
            if (pawnPossibleCature[1].getRow() != 0 && pawnPossibleCature[1].isOnBoard() && board.isOccupiedByColor(pawnPossibleCature[1], Color.BLACK)) {
                possibleActions.addPossibleCapture(pawnPossibleCature[1]);
            }
            //Capture on the last line
            if (pawnPossibleCature[0].getRow() == 0 && pawnPossibleCature[0].isOnBoard() && board.isOccupiedByColor(pawnPossibleCature[0], Color.BLACK)) {
                possibleActions.addPossibleCapture(pawnPossibleCature[0]);
            }
            if (pawnPossibleCature[1].getRow() == 0 && pawnPossibleCature[1].isOnBoard() && board.isOccupiedByColor(pawnPossibleCature[1], Color.BLACK)) {
                possibleActions.addPossibleCapture(pawnPossibleCature[1]);
            }
            // Will be opponents king checked after the  move?
            for (Position testMoves : pawnPossibleMove) {
                try {
                    if (testMoves.isOnBoard() && board.isOccupiedByKing(testMoves, Color.BLACK)) {
                        possibleActions.addPossibleChecks(testMoves);
                        System.out.println("CHECK!!!!!!!!!!!");
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
            for (Position testCaptures : pawnPossibleCature) {
                try {
                    if (testCaptures.isOnBoard() && board.isOccupiedByKing(testCaptures, Color.BLACK)) {
                        possibleActions.addPossibleChecks(testCaptures);
                        System.out.println("CHECK!!!!!!!!!!!");
                    }
                } catch (NullPointerException e) {
                    continue;
                }

            }
        }
        return possibleActions;
    }
}
