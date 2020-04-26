package pl.wb.demo.chess.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wb.demo.chess.communication.field.Field;
import pl.wb.demo.chess.controller.ResponseEntity.Mate;
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

    @RequestMapping(value = {"/selection"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody // can delete all of this response body probably!
    ResponseEntity<String> selection (@RequestParam int player, @RequestParam String fieldId) {

        ArrayList<String> fieldsToMark = new ArrayList<>();
        ArrayList<Position> allPossibleActions = new ArrayList<>();
        Field selectedPiece = Field.getFieldByString(fieldId);

        Position piecePositionThatIsSelected = new Position(selectedPiece.getRow(), selectedPiece.getColumn());

        Color color = chessGame.getBoard().getColorFromTheBoardOnCurrentPosition(piecePositionThatIsSelected);
        if (whitePlayer) {
            chessGame.selectPiece(selectedPiece.getRow(), selectedPiece.getColumn(), Color.WHITE);
            if (color == Color.WHITE) {
                whitePlayer = false;
            } else throw new EmptyStackException();
        } else {
            chessGame.selectPiece(selectedPiece.getRow(), selectedPiece.getColumn(), Color.BLACK);
            if (color == Color.BLACK) {
                whitePlayer = true;
            } else throw new EmptyStackException();
        }
        ArrayList<Position> possibleMovesToMake = chessGame.getPossibleActions().getPossibleMoves();
        ArrayList<Position> possibleCapturesToMake = chessGame.getPossibleActions().getPossibleCaptures();
        ArrayList<Position> possibleCastling = chessGame.getPossibleActions().getKingCastlingActions();

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

    @RequestMapping(value = {"/mate"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> isMated (@RequestParam int player) {
        Mate mate = new Mate(this.chessGame, this.whitePlayer);
        return mate.mate();
    }

    @RequestMapping(value = {"/actualBoard"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> actualBoard (@RequestParam int player) throws Exception {
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();
        chessGame.getBoard().getBoard();
        chessGame.getBoard().getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(chessGame.getBoard().boardFieldAndCodes);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @RequestMapping(value = {"/newGame"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> newGame (@RequestParam int player) throws Exception {
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();
        chessGame = new ChessGame();

        this.whitePlayer = true;
        chessGame.getBoard();
        chessGame.getBoard().getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(chessGame.getBoard().boardFieldAndCodes);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
}