package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StudentReader {
    private Firestore firestore;

    public StudentReader(Firestore firestore) {
        this.firestore = firestore;
    }

    public void wyswietlWszystkichStudentow() {
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("student").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Student student = document.toObject(Student.class);
                System.out.println("Email: " + student.getEmail());
                System.out.println("Imie: " + student.getImie());
                System.out.println("Nazwisko: " + student.getNazwisko());
                System.out.println("Nr indeksu: " + student.getNr_indeksu());
                System.out.println("Stacjonarny: " + student.getStacjonarny());
                System.out.println("Student ID: " + student.getStudent_id());
                System.out.println();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać projektów", e);
        }
    }
}
