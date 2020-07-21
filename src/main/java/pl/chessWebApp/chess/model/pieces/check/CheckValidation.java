package pl.chessWebApp.chess.model.pieces.check;

import pl.chessWebApp.chess.model.piece_properties.Color;

public interface CheckValidation {

    boolean isWhiteKingChecked ();

    boolean isBlackKingChecked ();

    boolean isKingChecked (Color color);
}
