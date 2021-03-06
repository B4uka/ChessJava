package pl.chessWebApp.chess.model.pieces;

import lombok.Getter;
import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;

@Getter
public abstract class Piece {

    protected Position position;
    protected final Color color;
    private final String code;
    private int countMoves;

    public Piece(Position position, Color color, String code) {
        this.position = position;
        this.color = color;
        this.code = code;
        this.countMoves = 0;
    }

    public Piece(Position position, Color color, String code, int countMoves) {
        this.position = position;
        this.color = color;
        this.code = code;
        this.countMoves = countMoves;
    }

    public abstract PossibleActions generatePossibleActions(Board board);

    public int countMoveAdd() {
        return countMoves++;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int row, int column) {
        this.position.setRowAndColumn(row, column);
    }
}


