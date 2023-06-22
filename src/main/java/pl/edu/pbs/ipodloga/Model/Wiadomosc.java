package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.annotation.ServerTimestamp;
import java.util.Date;

public class Wiadomosc {
    private String author_s;
    private String author_u;
    private String author_d;
    @ServerTimestamp
    private Date date;
    private String message;

    public Wiadomosc() {
    }

    public Wiadomosc(String author_s, String author_u, String author_d, String message) {
        this.author_s = author_s;
        this.author_u = author_u;
        this.author_d = author_d;
        this.message = message;
    }

    public String getAuthor_s() {
        return author_s;
    }

    public void setAuthor_s(String author_s) {
        this.author_s = author_s;
    }

    public String getAuthor_u() {
        return author_u;
    }

    public void setAuthor_u(String author_u) {
        this.author_u = author_u;
    }

    public String getAuthor_d() {
        return author_d;
    }

    public void setAuthor_d(String author_d) {
        this.author_d = author_d;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
