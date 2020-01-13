package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public abstract class Piece {

    protected Position position;
    protected final Color color;
    protected final String code;


    public Piece (Position position, Color color, String code) {
        this.position = position;
        this.color = color;
        this.code = code;
    }

    public Position getPosition () {
        return position;
    }

    public Color getColor () {
        return this.color;
    }

    public abstract PossibleActions generatePossibleActions (Board board);

    public void setPosition (Position position) {
        this.position = position;
    }

    public String getCode () {
        return code;
    }

    public void setPosition (int row, int column) {
        this.position.setRowAndColumn(row, column);
    }
}


