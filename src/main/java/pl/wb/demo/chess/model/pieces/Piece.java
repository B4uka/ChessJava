package pl.wb.demo.chess.model.pieces;

import lombok.Getter;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

@Getter
public abstract class Piece {

    protected Position position;
    protected final Color color;
    private String code;
    private int countMoves;

    public Piece (Position position, Color color, String code, int countMoves) {
        this.position = position;
        this.color = color;
        this.code = code;
        this.countMoves = countMoves;
    }

    public abstract PossibleActions generatePossibleActions (Board board);

    public int countMoveAdd () {
        return countMoves++;
    }

    public void setPosition (Position position) {
        this.position = position;
    }

    public void setPosition (int row, int column) {
        this.position.setRowAndColumn(row, column);
    }
}


