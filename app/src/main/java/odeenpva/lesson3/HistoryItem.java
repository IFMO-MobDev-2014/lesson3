package odeenpva.lesson3;

import java.util.Date;

/**
 * Created by pva701 on 27.09.14.
 */
public class HistoryItem {
    private int id;
    private String word;
    private Date time;

    public HistoryItem() {}
    public HistoryItem(int id, String word, Date time) {
        this.id = id;
        this.word = word;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public Date getTime() {
        return time;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return word;
    }
}
