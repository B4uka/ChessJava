package pl.chessWebApp.chess.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.chessWebApp.chess.liveStreamerLichess.StreamersLichess;
import pl.chessWebApp.chess.utils.JsonUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
public class LichessApiController {

    @GetMapping("/streamer")
    public StreamersLichess[] getTopStreamers () throws URISyntaxException, ExecutionException, InterruptedException {
        JsonUtils jsonUtils = new JsonUtils();

        String gsonString = jsonUtils.JSONBody(new URI("https://lichess.org/streamer/live"));
        StreamersLichess[] allStreamersFromLichess = new Gson().fromJson(gsonString, StreamersLichess[].class);

        return Arrays.copyOf(allStreamersFromLichess, 10);
    }
}
