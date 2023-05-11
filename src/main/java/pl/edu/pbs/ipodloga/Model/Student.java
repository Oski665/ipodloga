package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.annotation.DocumentId;

public class Student {
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getImie() {
        return imie;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }
    public String getNr_indeksu() {
        return nr_indeksu;
    }
    public void setNr_indeksu(String nr_indeksu) {
        this.nr_indeksu = nr_indeksu;
    }
    public boolean getStacjonarny() {
        return stacjonarny;
    }
    public void setStacjonarny(boolean stacjonarny) {
        this.stacjonarny = stacjonarny;
    }
    public Long getStudent_id() {
        return student_id;
    }
    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    @DocumentId
    private String id;
    private String email;
    private String imie;
    private String nazwisko;
    private String nr_indeksu;
    private boolean stacjonarny;
    private Long student_id;
    public Student(){}
}
