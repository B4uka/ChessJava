package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

public class Rook extends Piece implements MoveValidation{

    public Rook (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveValidation(this.position, board, possibleActions, 0, 1);
        moveValidation(this.position, board, possibleActions, 0, -1);
        moveValidation(this.position, board, possibleActions, 1, 0);
        moveValidation(this.position, board, possibleActions, -1, 0);

        return possibleActions;
    }

    @Override
    public PossibleActions moveValidation (Position rookMove, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {

        rookMove = rookMove.getNewPositionByVector(rowShift, columnShift);

        while (rookMove.isOnBoard()) {
            if(this.color == Color.BLACK && board.isOccupiedByColor(rookMove, Color.BLACK)
                    || this.color == Color.WHITE && board.isOccupiedByColor(rookMove, Color.WHITE)){
                break;
            } else if (this.color == Color.WHITE && board.isOccupiedByColor(rookMove, Color.BLACK)
                    || (this.color == Color.BLACK && board.isOccupiedByColor(rookMove, Color.WHITE))) {
                possibleActions.addPossibleCapture(rookMove);
                return possibleActions;
            } else if (!board.isOccupied(rookMove)) {
                possibleActions.addPossibleMove(rookMove);
                rookMove = rookMove.getNewPositionByVector(rowShift, columnShift);
            }
        }
        return possibleActions;
    }
}