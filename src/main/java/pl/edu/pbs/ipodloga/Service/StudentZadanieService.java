package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.StudentZadanie;
import pl.edu.pbs.ipodloga.Model.Zadanie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class StudentZadanieService {

    private final Firestore firestore;
    private final Logger logger = LoggerFactory.getLogger(StudentZadanieService.class);

    public StudentZadanieService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String przypiszZadanieDoStudenta(StudentZadanie studentZadanie) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("studentZadanie").add(studentZadanie);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }

    public List<Pair<Student, Zadanie>> pobierzZadaniaStudenta(String studentId) {
        List<Pair<Student, Zadanie>> studentZadania = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("studentZadanie").whereEqualTo("studentId", studentId).get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String zadanieId = document.getString("zadanieId");
                DocumentSnapshot zadanieSnapshot = firestore.collection("zadanie").document(zadanieId).get().get();
                DocumentSnapshot studentSnapshot = firestore.collection("student").document(studentId).get().get();
                if (zadanieSnapshot.exists() && studentSnapshot.exists()) {
                    Zadanie zadanie = zadanieSnapshot.toObject(Zadanie.class);
                    Student student = studentSnapshot.toObject(Student.class);
                    studentZadania.add(Pair.of(student, zadanie));
                    logger.info("Dodano zadanie: {} dla studenta: {}", zadanie.getNazwa(), student.getImie());
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać zadań", e);
        }
        return studentZadania;
    }

    public String usunStudentZadanie(String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = firestore.collection("studentZadanie").document(id).delete();
        return "Usunięto studenta zadanie o ID: " + id;
    }

}