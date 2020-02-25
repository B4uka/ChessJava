package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

public class Queen extends Piece implements MoveValidation {

    public Queen (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveValidation(this.position, board, possibleActions, 0, 1);
        moveValidation(this.position, board, possibleActions, 0, -1);
        moveValidation(this.position, board, possibleActions, 1, 0);
        moveValidation(this.position, board, possibleActions, -1, 0);

        moveValidation(this.position, board, possibleActions, 1, 1);
        moveValidation(this.position, board, possibleActions, 1, -1);
        moveValidation(this.position, board, possibleActions, -1, 1);
        moveValidation(this.position, board, possibleActions, -1, -1);

        return possibleActions;
    }

    @Override
    public PossibleActions moveValidation (Position queenMove, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {

            queenMove = queenMove.getNewPositionByVector(rowShift, columnShift);

            while (queenMove.isOnBoard()) {
                if(this.color == Color.BLACK && board.isOccupiedByColor(queenMove, Color.BLACK)
                        || this.color == Color.WHITE && board.isOccupiedByColor(queenMove, Color.WHITE)){
                    break;
                } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenMove, Color.BLACK)
                        || (this.color == Color.BLACK && board.isOccupiedByColor(queenMove, Color.WHITE))) {
                    possibleActions.addPossibleCapture(queenMove);
                    return possibleActions;
                } else if (!board.isOccupied(queenMove)) {
                    possibleActions.addPossibleMove(queenMove);
                    queenMove = queenMove.getNewPositionByVector(rowShift, columnShift);
                }
            }
            return possibleActions;
        }
}
