package pl.wb.demo.chess.model.board;

import pl.wb.demo.chess.model.piece_properties.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PossibleActions {

    public ArrayList<Position> possibleMoves, possibleCaptures, possibleChecks, possibleMovesAndCaptures, kingCastlingActions, rookCastlingActions;
    public Set<Position> listOfPiecesPositionsWhichAreCheckingTheKing;
    public Position position;

    public PossibleActions () {
        this.possibleCaptures = new ArrayList<>();
        this.possibleMoves = new ArrayList<>();
        this.possibleChecks = new ArrayList<>();
        this.kingCastlingActions = new ArrayList<>();
        this.rookCastlingActions = new ArrayList<>();
        this.listOfPiecesPositionsWhichAreCheckingTheKing = new HashSet<>();
    }

    public void addPossibleCapture (Position position) {
        this.possibleCaptures.add(position);
    }

    public void addPossibleMove (Position position) {
        this.possibleMoves.add(position);
    }

    public void addPossibleCastlingKingMove (Position position) {
        this.kingCastlingActions.add(position);
    }

    public void addPiecesPositionsWhichAreCheckingTheKing (Position position) {
        this.listOfPiecesPositionsWhichAreCheckingTheKing.add(position);
    }

    public ArrayList<Position> getPossibleCaptures () {
        return possibleCaptures;
    }

    public ArrayList<Position> getPossibleMoves () {
        return possibleMoves;
    }

    public ArrayList<Position> getKingCastlingActions () {
        return kingCastlingActions;
    }

    public ArrayList<Position> getAllPossibleMovesAndCaptures () {
        possibleMoves.addAll(possibleCaptures);
        possibleMovesAndCaptures = possibleMoves;
        return possibleMovesAndCaptures;
    }
}