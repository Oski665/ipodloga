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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private String kolejnosc;
    private String nazwa;
    private String opis;

    public enum Type {
        TASK,
        ERROR
    }

    public enum Priority {
        HIGH,
        LOW
    }

    public enum Status {
        DONE,
        ON_GOING,
        NOT_ASSIGNED
    }

    private Type type;
    private Priority priority;
    private Status status;

    private String projektId;

    private String deadline;

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public String getProjektId() {
        return projektId;
    }

    public void setProjektId(String projektId) {
        this.projektId = projektId;
    }
}
