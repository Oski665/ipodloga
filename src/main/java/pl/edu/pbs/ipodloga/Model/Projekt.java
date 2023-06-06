package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.annotation.DocumentId;

public class Projekt {
    @DocumentId
    private String id;
    //private LocalDateTime dataczas_utworzenia;
    private String nazwa;
    private String opis;
    private Boolean status;
    private String dataczas_utworzenia;
    private String dataczas_ukonczenia;

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

    // Konstruktor bezargumentowy
    public Projekt() {
    }

    // Gettery i settery dla każdego pola
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public LocalDateTime getDataczas_utworzenia() {
//        return dataczas_utworzenia;
//    }

//    @JsonDeserialize(using = TimestampDeserializer.class)
//    public void setDataczas_utworzenia(Timestamp dataczas_utworzenia) {
//        this.dataczas_utworzenia = dataczas_utworzenia.toDate().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
//    }
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
