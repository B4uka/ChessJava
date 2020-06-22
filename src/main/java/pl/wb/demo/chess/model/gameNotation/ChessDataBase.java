package pl.wb.demo.chess.model.gameNotation;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;

public class ChessDataBase {

    public List<Games> chessBase;
    String whitePlayerAccountName;
    String blackPlayerAccountName;

    public void displayToStringMethod() {
        System.out.println(chessBase.toString());
    }

    public void streamListWhite(String whitePlayerAccountName) {

        this.whitePlayerAccountName = whitePlayerAccountName;
        this.chessBase.stream()
                .filter(games -> games.getWhitePlayerAccountName().equals(whitePlayerAccountName))
                .map(games -> games.getWhitePlayerAccountName() + " " + games.getBlackPlayerAccountName() + " " + games.getECO() + " " + games.getWhitePlayerRating() + " " +
                        games.getBlackPlayerRating() + " " + games.getResult() + " " + games.getWhenGameWasPlayed())
                .forEach(System.out::println);
    }
    public void streamListBlack(String blackPlayerAccountName) {

        this.blackPlayerAccountName = whitePlayerAccountName;
        this.chessBase.stream()
                .filter(games -> games.getBlackPlayerAccountName().equals(blackPlayerAccountName))
                .map(games -> games.getWhitePlayerAccountName() + " " + games.getBlackPlayerAccountName() + " " + games.getECO() + " " + games.getWhitePlayerRating() + " " +
                        games.getBlackPlayerRating() + " " + games.getResult() + " " + games.getWhenGameWasPlayed())
                .forEach(System.out::println);
    }

    public ChessDataBase () {
        chessBase = Arrays.asList(
                new Games("Wojtek", "Tadeusz", 2022, 2032, Result.DRAW, "B14", DateFormat.getDateInstance()),
                new Games("Zofia", "Macin", 2011, 2052, Result.WhiteWon, "A14", DateFormat.getDateInstance()),
                new Games("Matylda", "Agata", 2011, 2022, Result.BlackWon, "B14", DateFormat.getDateInstance())
        );
    }
}
