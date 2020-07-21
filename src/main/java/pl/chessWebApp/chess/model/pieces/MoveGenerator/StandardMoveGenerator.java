package pl.chessWebApp.chess.model.pieces.MoveGenerator;

import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Position;

public interface StandardMoveGenerator {

    PossibleActions moveGenerator (Position bishopPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift);
}
