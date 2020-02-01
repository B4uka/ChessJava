package com.wb.demo.chess.model.pieces;

import com.wb.demo.chess.model.board.Board;
import com.wb.demo.chess.model.board.PossibleActions;
import com.wb.demo.chess.model.piece_properties.Color;
import com.wb.demo.chess.model.piece_properties.Position;

public class Knight extends Piece {

    public Knight (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
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
                } else if (test.isOnBoard() && board.isOccupiedByColor(test, Color.BLACK) && !board.isOccupiedBySpecificPiece(test, Color.BLACK, King.class)) {
                    possibleActions.addPossibleCapture(test);
                } else if (test.isOnBoard() && board.isOccupiedBySpecificPiece(test, Color.BLACK, King.class)) {
                    possibleActions.addPossibleChecks(test);
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
                } else if (test.isOnBoard() && board.isOccupiedByColor(test, Color.WHITE) && !board.isOccupiedBySpecificPiece(test, Color.WHITE, King.class)) {
                    possibleActions.addPossibleCapture(test);
                } else if (test.isOnBoard() && board.isOccupiedBySpecificPiece(test, Color.WHITE, King.class)) {
                    possibleActions.addPossibleChecks(test);
                }
            }
        }
        return possibleActions;
    }
}