package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Position;

public interface MoveValidation {

    PossibleActions moveValidation(Position bishopPosition, Board board, PossibleActions possibleActions, int directionForRow, int directionForColumn);
}
