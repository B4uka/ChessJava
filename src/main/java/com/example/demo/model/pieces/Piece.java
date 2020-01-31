package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public abstract class Piece {

    protected Position position;
    protected final Color color;

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
    public int countMoveAdd(){
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


