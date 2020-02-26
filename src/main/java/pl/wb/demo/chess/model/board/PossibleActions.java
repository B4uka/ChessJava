package pl.wb.demo.chess.model.board;

import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

import java.util.ArrayList;

public class PossibleActions {

    public ArrayList<Position> possibleMoves, possibleCaptures, possibleChecks, listOfPiecesPositionsWhichAreCheckingTheKing, possibleMovesAndCaptures, kingCastlingActions, rookCastlingActions;
    public Color color;
    public Position position;

    public PossibleActions () {
        this.possibleCaptures = new ArrayList<>();
        this.possibleMoves = new ArrayList<>();
        this.possibleChecks = new ArrayList<>();
        this.kingCastlingActions = new ArrayList<>();
        this.rookCastlingActions = new ArrayList<>();
        this.listOfPiecesPositionsWhichAreCheckingTheKing = new ArrayList<>();
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

    public void addPossibleChecks (Position position) {
        this.possibleChecks.add(position);
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

    public Position getPosition () {
        return position;
    }

    public void printPossibleMoves () {
        for (Position position : possibleMoves) {
            System.out.print("Possible moves: " + position.getRow() + "\t" + position.getColumn() + "\t");
        }
    }

    public void printPossibleCaptures () {
        for (Position position : possibleCaptures) {
            System.out.print("Possible captures: " + position.getRow() + "\t" + position.getColumn() + "\t");
        }
    }

    public void printPossibleChecks () {
        for (Position position : possibleChecks) {
            System.out.print("Possible checks: " + position.getRow() + "\t" + position.getColumn() + "\t");
        }
    }

    public void printPossibleCastlingKingMoves () {
        for (Position position : kingCastlingActions) {
            System.out.print("Possible castlings King moves: " + position.getRow() + "\t" + position.getColumn() + "\t");
        }
    }

    public void printPositionsOfPiecesThatAreCheckingKing () {
        for (Position position : listOfPiecesPositionsWhichAreCheckingTheKing) {
            System.out.println("Row: " + position.getRow() + "\t Column: " + position.getColumn());
        }
    }
}