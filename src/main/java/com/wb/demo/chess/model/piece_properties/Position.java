package com.wb.demo.chess.model.piece_properties;

public class Position {

    private int row;
    private int column;

    public Position (int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setRowAndColumn (int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position getRowAndColumn (int row, int column) {
        return new Position(row, column);
    }

    public int getRow () {
        return this.row;
    }

    public int getColumn () {
        return this.column;
    }

    public Position getNewPositionByVector (int plusRow, int plusColumn) {
        return new Position(this.row + plusRow, this.column + plusColumn);
    }

    public Position getNewPosition (int plusRow, int plusColumn) {
        return new Position(plusRow, plusColumn);
    }

    public boolean isOnBoard () {
        return 0 <= row && row <= 7 && 0 <= column && column <= 7;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                column == position.column;
    }
}

