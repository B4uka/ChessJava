package pl.chessWebApp.chess.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.chessWebApp.chess.model.piece_properties.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PossibleActionsTest {
    private PossibleActions test;
    private Position testPosition = new Position(1,1);
    private Position testPosition2 = new Position(4,1);

    @BeforeEach
    void setUp () {
        test = new PossibleActions();
        test.addPossibleMove(testPosition);
        test.addPossibleMove(testPosition2);
    }

    @Test
    void getPossibleMoves () {
        assertTrue(test.getPossibleMoves().contains(testPosition));
    }

    @Test
    void getAllPossibleMovesAndCaptures () {
        assertTrue(test.getPossibleMoves().contains(testPosition) && test.getPossibleMoves().contains(testPosition2));
    }
}