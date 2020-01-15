package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public class Pawn extends Piece {

    public Pawn (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }


    @Override
    public PossibleActions generatePossibleActions (Board board) {

        PossibleActions possibleActions = new PossibleActions();
        Position[] pawnPossibleMove = new Position[2];
        Position[] pawnPossibleCapture = new Position[2];

        //possible moves by Black
        if (this.color == Color.BLACK) {
            pawnPossibleMove[0] = this.position.getNewPositionByVector(1, 0);
            if (this.position.getRow() == 1 && !board.isOccupied(pawnPossibleMove[0])) {
                pawnPossibleMove[1] = this.position.getNewPositionByVector(2, 0);
            } else {
                pawnPossibleMove[1] = pawnPossibleMove[0];
            }

            for (Position test : pawnPossibleMove) {
                if (test.isOnBoard() && !board.isOccupied(test)) {
                    possibleActions.addPossibleMove(test);
                } else {
                    continue;
                }
            }
            //possible captures by BLACK
            pawnPossibleCapture[0] = this.position.getNewPositionByVector(1, 1);
            pawnPossibleCapture[1] = this.position.getNewPositionByVector(1, -1);
            for (Position test : pawnPossibleCapture) {
                if (test.isOnBoard() && !board.isOccupiedByColor(test, Color.BLACK) && !board.isOccupiedByColor(test, Color.WHITE)) {
                    continue;
                } else if (test.isOnBoard() && board.isOccupiedByColor(test, Color.WHITE) && !board.isOccupiedByKing(test, Color.WHITE)) {
                    possibleActions.addPossibleCapture(test);
                } else if (test.isOnBoard() && board.isOccupiedByKing(test, Color.WHITE)) {
                    possibleActions.addPossibleChecks(test);
                }
            }
        } else if (this.color == Color.WHITE) {

            //possible moves by WHITE
            pawnPossibleMove[0] = this.position.getNewPositionByVector(-1, 0);
            if (this.position.getRow() == 6  && !board.isOccupied(pawnPossibleMove[0])) {
                pawnPossibleMove[1] = this.position.getNewPositionByVector(-2, 0);
            } else {
                pawnPossibleMove[1] = pawnPossibleMove[0];
            }
            for (Position test : pawnPossibleMove) {
                if (test.isOnBoard() && !board.isOccupied(test)) {
                    possibleActions.addPossibleMove(test);
                } else {
                    continue;
                }
            }
            // possible captures by WHITE
            pawnPossibleCapture[0] = this.position.getNewPositionByVector(-1, 1);
            pawnPossibleCapture[1] = this.position.getNewPositionByVector(-1, -1);
            for (Position test : pawnPossibleCapture) {
                if (test.isOnBoard() && !board.isOccupiedByColor(test, Color.WHITE) && !board.isOccupiedByColor(test, Color.BLACK)) {
                    continue;
                } else if (test.isOnBoard() && board.isOccupiedByColor(test, Color.BLACK) && !board.isOccupiedByKing(test, Color.BLACK)) {
                    possibleActions.addPossibleCapture(test);
                } else if (test.isOnBoard() && board.isOccupiedByKing(test, Color.BLACK)) {
                    possibleActions.addPossibleChecks(test);
                }
            }
        }
        return possibleActions;
    }
}
