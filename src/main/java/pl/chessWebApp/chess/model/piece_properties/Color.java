package pl.chessWebApp.chess.model.piece_properties;

public enum Color {
    WHITE,
    BLACK;

    public Color whiteToMove(){
        return Color.WHITE;
    }

    public Color blackToMove(){
        return Color.BLACK;
    }
}