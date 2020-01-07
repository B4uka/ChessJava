package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public class Queen extends Piece {

    public Queen (Position position, Color color) {
        super(position, color);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        Position[] queenMove = new Position[8];
        queenMove[0] = this.position.getNewPositionByVector(1, 1);
        queenMove[1] = this.position.getNewPositionByVector(1, -1);
        queenMove[2] = this.position.getNewPositionByVector(-1, 1);
        queenMove[3] = this.position.getNewPositionByVector(-1, -1);

        queenMove[4] = this.position.getNewPositionByVector(0, 1);
        queenMove[5] = this.position.getNewPositionByVector(0, -1);
        queenMove[6] = this.position.getNewPositionByVector(1, 0);
        queenMove[7] = this.position.getNewPositionByVector(-1, 0);

        /** POSSIBLE MOVES */
        outloop:
        {
            for (int i = 1; i < 8; i++) {
                if (!queenMove[0].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[0], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[0], Color.WHITE))) {
                    break;
                } else if (!board.isOccupied(queenMove[0])) {
                    possibleActions.addPossibleMove(queenMove[0]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(queenMove[0], Color.BLACK)) {
                    possibleActions.addPossibleChecks(queenMove[0]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[0], Color.BLACK)) {
                    possibleActions.addPossibleCapture(queenMove[0]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(queenMove[0], Color.WHITE)) {
                    possibleActions.addPossibleChecks(queenMove[0]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[0], Color.WHITE)) {
                    possibleActions.addPossibleCapture(queenMove[0]);
                    break;
                }queenMove[0] = this.position.getNewPositionByVector(1 + i, 1 + i);
            }
            for (int i = 1; i < 8; i++) {
                if (!queenMove[1].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[1], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[1], Color.WHITE))) {
                    break;
                } else if (!board.isOccupied(queenMove[1])) {
                    possibleActions.addPossibleMove(queenMove[1]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(queenMove[1], Color.BLACK)) {
                    possibleActions.addPossibleChecks(queenMove[1]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[1], Color.BLACK)) {
                    possibleActions.addPossibleCapture(queenMove[1]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(queenMove[1], Color.WHITE)) {
                    possibleActions.addPossibleChecks(queenMove[1]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[1], Color.WHITE)) {
                    possibleActions.addPossibleCapture(queenMove[1]);
                    break;
                }queenMove[1] = this.position.getNewPositionByVector(1 + i, -1 - i);
            }
            for (int i = 1; i < 8; i++) {
                if (!queenMove[2].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[2], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[2], Color.WHITE))) {
                    break;
                } else if (!board.isOccupied(queenMove[2])) {
                    possibleActions.addPossibleMove(queenMove[2]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(queenMove[2], Color.BLACK)) {
                    possibleActions.addPossibleChecks(queenMove[2]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[2], Color.BLACK)) {
                    possibleActions.addPossibleCapture(queenMove[2]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(queenMove[2], Color.WHITE)) {
                    possibleActions.addPossibleChecks(queenMove[2]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[2], Color.WHITE)) {
                    possibleActions.addPossibleCapture(queenMove[2]);
                    break;
                }queenMove[2] = this.position.getNewPositionByVector(-1 - i, 1 + i);
            }

            for (int i = 1; i < 8; i++) {
                if (!queenMove[3].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[3], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[3], Color.WHITE))) {
                    break;
                }
                else if (!board.isOccupied(queenMove[3])) {
                    possibleActions.addPossibleMove(queenMove[3]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(queenMove[3], Color.BLACK)) {
                    possibleActions.addPossibleChecks(queenMove[3]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[3], Color.BLACK)) {
                    possibleActions.addPossibleCapture(queenMove[3]);
                    break;

                } else if (this.color == Color.BLACK && board.isOccupiedByKing(queenMove[3], Color.WHITE)) {
                    possibleActions.addPossibleChecks(queenMove[3]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[3], Color.WHITE)) {
                    possibleActions.addPossibleCapture(queenMove[3]);
                    break;
                }queenMove[3] = this.position.getNewPositionByVector(-1 - i, -1 - i);
            }

            for (int i = 1; i < 8; i++) {
                if (!queenMove[4].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[4], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[4], Color.WHITE))) {
                    break;
                }
                else if (!board.isOccupied(queenMove[4])) {
                    possibleActions.addPossibleMove(queenMove[4]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(queenMove[4], Color.BLACK)) {
                    possibleActions.addPossibleChecks(queenMove[4]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[4], Color.BLACK)) {
                    possibleActions.addPossibleCapture(queenMove[4]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(queenMove[4], Color.WHITE)) {
                    possibleActions.addPossibleChecks(queenMove[4]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[4], Color.WHITE)) {
                    possibleActions.addPossibleCapture(queenMove[4]);
                    break;
                } queenMove[4] = this.position.getNewPositionByVector(0, 1 + i);
            }

            for (int i = 1; i < 8; i++) {
                if (!queenMove[5].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[5], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[5], Color.WHITE))) {
                    break;
                }
                else if (!board.isOccupied(queenMove[5])) {
                    possibleActions.addPossibleMove(queenMove[5]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(queenMove[5], Color.BLACK)) {
                    possibleActions.addPossibleChecks(queenMove[5]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[5], Color.BLACK)) {
                    possibleActions.addPossibleCapture(queenMove[5]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(queenMove[5], Color.WHITE)) {
                    possibleActions.addPossibleChecks(queenMove[5]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[5], Color.WHITE)) {
                    possibleActions.addPossibleCapture(queenMove[5]);
                    break;
                }queenMove[5] = this.position.getNewPositionByVector(0, -1 - i);
            }
            for (int i = 1; i < 8; i++) {
                if (!queenMove[6].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[6], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[6], Color.WHITE))) {
                    break;
                }
                if (!board.isOccupied(queenMove[6])) {
                    possibleActions.addPossibleMove(queenMove[6]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(queenMove[6], Color.BLACK)) {
                    possibleActions.addPossibleChecks(queenMove[6]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[6], Color.BLACK)) {
                    possibleActions.addPossibleCapture(queenMove[6]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(queenMove[6], Color.WHITE)) {
                    possibleActions.addPossibleChecks(queenMove[6]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (queenMove[6].isOnBoard() && this.color == Color.BLACK && board.isOccupiedByColor(queenMove[6], Color.WHITE)) {
                    possibleActions.addPossibleCapture(queenMove[6]);
                    break;
                }queenMove[6] = this.position.getNewPositionByVector(+1 + i, 0);
            }

            for (int i = 1; i < 8; i++) {
                if (!queenMove[7].isOnBoard() || (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[7], Color.BLACK)) || (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[7], Color.WHITE))) {
                    break;
                }
                else if (!board.isOccupied(queenMove[7])) {
                    possibleActions.addPossibleMove(queenMove[7]);
                } else if (this.color == Color.WHITE && board.isOccupiedByKing(queenMove[7], Color.BLACK)) {
                    possibleActions.addPossibleChecks(queenMove[7]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenMove[7], Color.BLACK)) {
                    possibleActions.addPossibleCapture(queenMove[7]);
                    break;
                } else if (this.color == Color.BLACK && board.isOccupiedByKing(queenMove[7], Color.WHITE)) {
                    possibleActions.addPossibleChecks(queenMove[7]);
                    System.out.println("CHECK!!!!!!!!!!!!!!");
                    break outloop;
                } else if (this.color == Color.BLACK && board.isOccupiedByColor(queenMove[7], Color.WHITE)) {
                    possibleActions.addPossibleCapture(queenMove[7]);
                    break;
                } queenMove[7] = this.position.getNewPositionByVector(-1 - i, 0);
            }

        }
        return possibleActions;
    }
}
