package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.annotation.DocumentId;

public class StudentZadanie {

    @DocumentId
    private String id;
    private String studentId;
    private String zadanieId;

    public StudentZadanie() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getZadanieId() {
        return zadanieId;
    }

    public void setZadanieId(String zadanieId) {
        this.zadanieId = zadanieId;
    }
}

