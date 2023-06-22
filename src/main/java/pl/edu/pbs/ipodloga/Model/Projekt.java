package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.annotation.DocumentId;

public class Projekt {
    @DocumentId
    private String id;
    private String nazwa;
    private String opis;
    private String status;
    private String dataczas_utworzenia;
    private String dataczas_ukonczenia;
    private String konwersacja;

    // Konstruktor bezargumentowy
    public Projekt() {
    }
        
    public String getDataczas_utworzenia() {
        return dataczas_utworzenia;
    }

    public void setDataczas_utworzenia(String dataczas_utworzenia) {
        this.dataczas_utworzenia = dataczas_utworzenia;
    }

    public String getDataczas_ukonczenia() {
        return dataczas_ukonczenia;
    }

    public void setDataczas_ukonczenia(String dataczas_ukonczenia) {
        this.dataczas_ukonczenia = dataczas_ukonczenia;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKonwersacja() {
        return konwersacja;
    }

    public void setKonwersacja(String konwersacja) {
        this.konwersacja = konwersacja;
    }
}
