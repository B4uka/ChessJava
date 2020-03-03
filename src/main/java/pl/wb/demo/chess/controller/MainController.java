package pl.wb.demo.chess.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wb.demo.chess.communication.field.Field;
import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;

@RestController
public class MainController {

    private ChessGame chessGame = new ChessGame();
    private Boolean whitePlayer = true;
    protected Move nextMove;

    @RequestMapping(value = {"/selection"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> selection (@RequestParam int player, @RequestParam String fieldId) {

        ArrayList<String> fieldsToMark = new ArrayList<>();
        ArrayList<Position> allPossibleActions = new ArrayList<>();
        Field selectedPiece = Field.getFieldByString(fieldId);

        Position piecePositionThatIsSelected = new Position(selectedPiece.getRow(), selectedPiece.getColumn());

        Color color = ChessGame.board.getColorFromTheBoardOnCurrentPosition(piecePositionThatIsSelected);
        if (whitePlayer) {
            chessGame.selectWhitePiece(selectedPiece.getRow(), selectedPiece.getColumn());
            if (color == Color.WHITE) {
                whitePlayer = false;
            } else throw new EmptyStackException();
        } else {
            chessGame.selectBlackPiece(selectedPiece.getRow(), selectedPiece.getColumn());
            if (color == Color.BLACK) {
                whitePlayer = true;
            } else throw new EmptyStackException();
        }
        ArrayList<Position> possibleMovesToMake = chessGame.possibleActions.getPossibleMoves();
        ArrayList<Position> possibleCapturesToMake = chessGame.possibleActions.getPossibleCaptures();
        ArrayList<Position> possibleCastling = chessGame.possibleActions.getKingCastlingActions();

        allPossibleActions.addAll(possibleCapturesToMake);
        allPossibleActions.addAll(possibleMovesToMake);
        allPossibleActions.addAll(possibleCastling);

        for (Position position : allPossibleActions) {
            fieldsToMark.add(Field.getFieldByPosition(position.getRow(), position.getColumn()));
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(fieldsToMark);

        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @RequestMapping(value = {"/move"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> move (@RequestParam int player, @RequestParam String fieldId) throws Exception {

        nextMove = new Move(this.chessGame, this.whitePlayer);
        return nextMove.move(fieldId);
    }

    @RequestMapping(value = {"/mate"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> isMated (@RequestParam int player) {
        ArrayList<String> isMated = new ArrayList<>();

        if (whitePlayer) {
            Boolean isWhiteMated = chessGame.isKingMated(Color.WHITE);
            if (isWhiteMated) {
                isMated.add("true");
            } else isMated.add("false");
        } else {
            Boolean isBlackMated = chessGame.isKingMated(Color.BLACK);
            if (isBlackMated) {
                isMated.add("true");
            } else isMated.add("false");
        }
        if (ChessGame.isStalemate) {
            isMated.add("stalamate");
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(isMated);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @RequestMapping(value = {"/actualBoard"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> actualBoard (@RequestParam int player) throws Exception {
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();
        ChessGame.board.getBoard();
        ChessGame.board.getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(ChessGame.board.boardFieldAndCodes);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @RequestMapping(value = {"/newGame"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> newGame (@RequestParam int player) throws Exception {
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();
        new ChessGame();

        this.whitePlayer = true;
        ChessGame.board.getBoard();
        ChessGame.board.getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(ChessGame.board.boardFieldAndCodes);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
}