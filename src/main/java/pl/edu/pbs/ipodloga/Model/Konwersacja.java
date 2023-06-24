package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.annotation.DocumentId;
import java.util.List;


public class Konwersacja {
    @DocumentId
    private String id;
    private List<Wiadomosc> wiadomosci;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Wiadomosc> getWiadomosci() {
        return wiadomosci;
    }

    public void setWiadomosci(List<Wiadomosc> wiadomosci) {
        this.wiadomosci = wiadomosci;
    }
}
