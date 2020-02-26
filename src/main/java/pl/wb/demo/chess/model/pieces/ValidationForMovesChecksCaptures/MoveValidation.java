package pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Position;

public interface MoveValidation {

    PossibleActions moveValidation(Position bishopPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift);
}
