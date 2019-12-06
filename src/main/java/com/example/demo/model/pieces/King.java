package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public class King extends Piece {


    public King (Position position, Color color) {
        super(position, color);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {

        PossibleActions possibleActions = new PossibleActions();
        Position[] kingMove = new Position[8];

        kingMove[0] = this.position.getNewPositionByVector(0, 1);
        kingMove[1] = this.position.getNewPositionByVector(0, -1);

        kingMove[2] = this.position.getNewPositionByVector(1, 0);
        kingMove[3] = this.position.getNewPositionByVector(-1, 0);

        kingMove[4] = this.position.getNewPositionByVector(1, 1);
        kingMove[5] = this.position.getNewPositionByVector(1, -1);

        kingMove[6] = this.position.getNewPositionByVector(-1, 1);
        kingMove[7] = this.position.getNewPositionByVector(-1, -1);

        for (Position test : kingMove) {
            if (test.isOnBoard()) {
                if (this.color == Color.WHITE && board.isOccupiedByColor(test, Color.BLACK) && !board.cantKillTheKing(test)) {
                    possibleActions.addPossibleCapture(test);
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(test, Color.WHITE) && !board.cantKillTheKing(test)) {
                    possibleActions.addPossibleCapture(test);
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(test, Color.WHITE)) {
                    continue;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(test, Color.BLACK)) {
                    continue;
                } else if (this.color == Color.WHITE && !board.isOccupied(test)) {
                    possibleActions.addPossibleMove(test);
                } else if (this.color == Color.BLACK && !board.isOccupied(test)) {
                    possibleActions.addPossibleMove(test);
                }
            }
        }return possibleActions;
    }
}