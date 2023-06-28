package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
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
    public String getStudent_id() {
        return student_id;
    }
    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public List<String> getProjektyId() {
        return projektyId;
    }

    public void setProjektyId(List<String> projektyId) {
        this.projektyId = projektyId;
    }
    public List<String> getZadaniaId() {
        return zadaniaId;
    }

    public void setZadaniaId(List<String> zadaniaId) {
        this.zadaniaId = zadaniaId;
    }
    @DocumentId
    private String id;
    private String email;
    private String imie;
    private String nazwisko;
    private String nr_indeksu;
    private boolean stacjonarny;
    private String student_id;
    private List<String> projektyId;
    private List<String> zadaniaId;
    public Student(){
        this.projektyId = new ArrayList<>();
        this.zadaniaId = new ArrayList<>();
    }
}
