package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class StudentService {

    private final Firestore firestore;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Student> pobierzWszystkichStudentow() {
        List<Student> studenty = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("student").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Student student = document.toObject(Student.class);
                studenty.add(student);
                logger.info("Dodano studenta: {}", student.getNazwisko());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać studenta", e);
        }
        return studenty;
    }
    public String dodajStudenta(Student student) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("student").add(student);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }
}
