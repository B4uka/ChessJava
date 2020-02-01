package com.wb.demo.chess.model.pieces;

import com.wb.demo.chess.model.board.Board;
import com.wb.demo.chess.model.board.PossibleActions;
import com.wb.demo.chess.model.piece_properties.Color;
import com.wb.demo.chess.model.piece_properties.Position;

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

    public Position getPosition () {
        return position;
    }

    public Color getColor () {
        return this.color;
    }

    public int countMoveAdd () {
        return countMoves++;
    }

    public String getCode () {
        return code;
    }

    public void setPosition (Position position) {
        this.position = position;
    }

    public void setPosition (int row, int column) {
        this.position.setRowAndColumn(row, column);
    }

    public int getCountMoves () {
        return countMoves;
    }
}


