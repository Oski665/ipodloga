package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.annotation.DocumentId;

public class ZadanieProjekt {

    @DocumentId
    private String id;
    private String zadanieId;
    private String projektId;

    public ZadanieProjekt() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZadanieId() {
        return zadanieId;
    }

    public void setZadanieId(String zadanieId) {
        this.zadanieId = zadanieId;
    }

    public String getProjektId() {
        return projektId;
    }

    public void setProjektId(String projektId) {
        this.projektId = projektId;
    }
}
