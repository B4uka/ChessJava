package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public class Bishop extends Piece {

    public Bishop (Position position, Color color) {
        super(position, color);
    }


    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        Position[] bishopMove = new Position[4];

        bishopMove[0] = this.position.getNewPositionByVector(1, 1);
        bishopMove[1] = this.position.getNewPositionByVector(1, -1);
        bishopMove[2] = this.position.getNewPositionByVector(-1, 1);
        bishopMove[3] = this.position.getNewPositionByVector(-1, -1);

        /** ALL POSSIBLE MOVES */
        outloop:{
            for (int i = 1; i < 8; i++) {
                if (!bishopMove[0].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(bishopMove[0], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(bishopMove[0], Color.WHITE))) {
                    break;
                } else if (!board.isOccupied(bishopMove[0])) {
                    possibleActions.addPossibleMove(bishopMove[0]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(bishopMove[0], Color.BLACK)) {
                    possibleActions.addPossibleChecks(bishopMove[0]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(bishopMove[0], Color.BLACK)) {
                    possibleActions.addPossibleCapture(bishopMove[0]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(bishopMove[0], Color.WHITE)) {
                    possibleActions.addPossibleChecks(bishopMove[0]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(bishopMove[0], Color.WHITE)) {
                    possibleActions.addPossibleCapture(bishopMove[0]);
                    break;
                }bishopMove[0] = this.position.getNewPositionByVector(1 + i, 1 + i);
            }
            for (int i = 1; i < 8; i++) {
                if (!bishopMove[1].isOnBoard()|| (this.color == Color.BLACK && board.isOccupiedByColor(bishopMove[1], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(bishopMove[1], Color.WHITE))) {
                    break;
                } else if (!board.isOccupied(bishopMove[1])) {
                    possibleActions.addPossibleMove(bishopMove[1]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(bishopMove[1], Color.BLACK)) {
                    possibleActions.addPossibleChecks(bishopMove[1]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(bishopMove[1], Color.BLACK)) {
                    possibleActions.addPossibleCapture(bishopMove[1]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(bishopMove[1], Color.WHITE)) {
                    possibleActions.addPossibleChecks(bishopMove[1]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(bishopMove[1], Color.WHITE)) {
                    possibleActions.addPossibleCapture(bishopMove[1]);
                    break;
                }bishopMove[1] = this.position.getNewPositionByVector(1 + i, -1 - i);
            }
            for (int i = 1; i < 8; i++) {
                if (!bishopMove[2].isOnBoard()|| (this.color == Color.BLACK && board.isOccupiedByColor(bishopMove[2], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(bishopMove[2], Color.WHITE))) {
                    break;
                } else if (!board.isOccupied(bishopMove[2])) {
                    possibleActions.addPossibleMove(bishopMove[2]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(bishopMove[2], Color.BLACK)) {
                    possibleActions.addPossibleChecks(bishopMove[2]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(bishopMove[2], Color.BLACK)) {
                    possibleActions.addPossibleCapture(bishopMove[2]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(bishopMove[2], Color.WHITE)) {
                    possibleActions.addPossibleChecks(bishopMove[2]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(bishopMove[2], Color.WHITE)) {
                    possibleActions.addPossibleCapture(bishopMove[2]);
                    break;
                }bishopMove[2] = this.position.getNewPositionByVector(-1 - i, 1 + i);
            }

            for (int i = 1; i < 8; i++) {
                if (!bishopMove[3].isOnBoard()|| (this.color == Color.BLACK && board.isOccupiedByColor(bishopMove[3], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(bishopMove[3], Color.WHITE))) {
                    break;
                }
                else if (!board.isOccupied(bishopMove[3])) {
                    possibleActions.addPossibleMove(bishopMove[3]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(bishopMove[3], Color.BLACK)) {
                    possibleActions.addPossibleChecks(bishopMove[3]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(bishopMove[3], Color.BLACK)) {
                    possibleActions.addPossibleCapture(bishopMove[3]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(bishopMove[3], Color.WHITE)) {
                    possibleActions.addPossibleChecks(bishopMove[3]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(bishopMove[3], Color.WHITE)) {
                    possibleActions.addPossibleCapture(bishopMove[3]);
                    break;
                }bishopMove[3] = this.position.getNewPositionByVector(-1 - i, -1 - i);
            }
        }
        return possibleActions;
    }
}