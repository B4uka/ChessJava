package pl.chessWebApp.chess.liveStreamerLichess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class StreamersLichess {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("playing")
    private Boolean playing;

    @SerializedName("patron")
    private Boolean patron;

    public StreamersLichess(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("title", title).
                toString();
    }
}
