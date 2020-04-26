package pl.wb.demo.chess.controller.ResponseEntity;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.wb.demo.chess.communication.field.Field;
import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.piece_properties.Position;

import java.util.EmptyStackException;
import java.util.HashMap;

public class Move {
    protected ChessGame chessGame;
    protected Boolean whitePlayer;

    protected Move (ChessGame chessGame, Boolean whitePlayer) {
        this.chessGame = chessGame;
        this.whitePlayer = whitePlayer;
    }

    protected ResponseEntity<String> move (String fieldId) {

        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();

        Position positionWhereWeWantToMove = new Position(Field.getFieldByString(fieldId).getRow(), Field.getFieldByString(fieldId).getColumn());
        if (chessGame.getBoard().isOccupied(positionWhereWeWantToMove)) {
            if (!chessGame.getMove().newPiecePositionByCapture(positionWhereWeWantToMove)) {
                this.whitePlayer = !whitePlayer;
                throw new EmptyStackException();
            }
        } else if (!chessGame.getMove().newPiecePositionByMove(positionWhereWeWantToMove)) {
            this.whitePlayer = !whitePlayer;
            throw new EmptyStackException();
        }
        chessGame.getBoard().getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(chessGame.getBoard().boardFieldAndCodes);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
}
