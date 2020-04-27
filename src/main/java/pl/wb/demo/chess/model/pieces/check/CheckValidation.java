package pl.wb.demo.chess.model.pieces.check;

import pl.wb.demo.chess.model.piece_properties.Color;

public interface CheckValidation {

    boolean isWhiteKingChecked ();

    boolean isBlackKingChecked ();

    boolean isKingChecked (Color color);
}
