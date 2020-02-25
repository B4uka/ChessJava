package pl.wb.demo.chess.model.piece_properties;

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

    public Position getRowAndColumn () {
        return new Position(this.row, this.column);
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

    public Position setNewPosition (int plusRow, int plusColumn) {
        return new Position(plusRow, plusColumn);
    }

    public boolean isOnBoard () {
        return 0 <= row && row <= 7 && 0 <= column && column <= 7;
    }
    public Position bishopMove1 (){
        return new Position(this.row + 1, this.column + 1);
    }
    public Position bishopMove2 (){
        return new Position(this.row + 1, this.column - 1);
    }
    public Position bishopMove3 (){
        return new Position(this.row - 1, this.column + 1);
    }
    public Position bishopMove4 (){
        return new Position(this.row - 1, this.column - 1);
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

