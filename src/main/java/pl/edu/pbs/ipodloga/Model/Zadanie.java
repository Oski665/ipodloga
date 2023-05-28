package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.annotation.DocumentId;

public class Zadanie {
    public Zadanie(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @DocumentId
    private String id;

    public String getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(String kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    private String kolejnosc;
    private String nazwa;
    private String opis;

    private String projektId;

    public String getProjektId() {
        return projektId;
    }

    public void setProjektId(String projektId) {
        this.projektId = projektId;
    }
}
