package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public class Rook extends Piece {

    public Rook(Position position, Color color, String code, int countMoves){
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        Position[] rookMove = new Position[4];
        rookMove[0] = this.position.getNewPositionByVector(0, 1);
        rookMove[1] = this.position.getNewPositionByVector(0, -1);
        rookMove[2] = this.position.getNewPositionByVector(1, 0);
        rookMove[3] = this.position.getNewPositionByVector(-1, 0);

        /* POSSIBLE MOVES */
        outloop:
        {
            for (int i = 1; i < 8; i++) {
                if (!rookMove[0].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(rookMove[0], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(rookMove[0], Color.WHITE))) {
                    break;
                } else if (!board.isOccupied(rookMove[0])) {
                    possibleActions.addPossibleMove(rookMove[0]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(rookMove[0], Color.BLACK)) {
                    possibleActions.addPossibleChecks(rookMove[0]);;
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(rookMove[0], Color.BLACK)) {
                    possibleActions.addPossibleCapture(rookMove[0]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(rookMove[0], Color.WHITE)) {
                    possibleActions.addPossibleChecks(rookMove[0]);
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(rookMove[0], Color.WHITE)) {
                    possibleActions.addPossibleCapture(rookMove[0]);
                    break;
                }rookMove[0] = this.position.getNewPositionByVector(0, 1 + i);
            }
            for (int i = 1; i < 8; i++) {
                if (!rookMove[1].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(rookMove[1], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(rookMove[1], Color.WHITE))) {
                    break;
                } else if (!board.isOccupied(rookMove[1])) {
                    possibleActions.addPossibleMove(rookMove[1]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(rookMove[1], Color.BLACK)) {
                    possibleActions.addPossibleChecks(rookMove[1]);
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(rookMove[1], Color.BLACK)) {
                    possibleActions.addPossibleCapture(rookMove[1]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(rookMove[1], Color.WHITE)) {
                    possibleActions.addPossibleChecks(rookMove[1]);
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(rookMove[1], Color.WHITE)) {
                    possibleActions.addPossibleCapture(rookMove[1]);
                    break;
                }rookMove[1] = this.position.getNewPositionByVector(0, -1 - i);
            }
            for (int i = 1; i < 8; i++) {
                if (!rookMove[2].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(rookMove[2], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(rookMove[2], Color.WHITE))) {
                    break;
                } else if (!board.isOccupied(rookMove[2])) {
                    possibleActions.addPossibleMove(rookMove[2]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(rookMove[2], Color.BLACK)) {
                    possibleActions.addPossibleChecks(rookMove[2]);
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(rookMove[2], Color.BLACK)) {
                    possibleActions.addPossibleCapture(rookMove[2]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(rookMove[2], Color.WHITE)) {
                    possibleActions.addPossibleChecks(rookMove[2]);
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(rookMove[2], Color.WHITE)) {
                    possibleActions.addPossibleCapture(rookMove[2]);
                    break;
                }rookMove[2] = this.position.getNewPositionByVector(1 + i, 0);
            }

            for (int i = 1; i < 8; i++) {
                if (!rookMove[3].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(rookMove[3], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(rookMove[3], Color.WHITE))) {
                    break;
                }
                else if (!board.isOccupied(rookMove[3])) {
                    possibleActions.addPossibleMove(rookMove[3]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(rookMove[3], Color.BLACK)) {
                    possibleActions.addPossibleChecks(rookMove[3]);
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(rookMove[3], Color.BLACK )&& !board.isOccupiedByKing(rookMove[3], color)) {
                    possibleActions.addPossibleCapture(rookMove[3]);
                    break;

                } else if (this.color == Color.BLACK && board.isOccupiedByKing(rookMove[3], Color.WHITE)) {
                    possibleActions.addPossibleChecks(rookMove[3]);
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(rookMove[3], Color.WHITE)&& !board.isOccupiedByKing(rookMove[3], color)) {
                    possibleActions.addPossibleCapture(rookMove[3]);
                    break;
                }rookMove[3] = this.position.getNewPositionByVector(-1 - i, 0);

            }
        }
        return possibleActions;
    }
}