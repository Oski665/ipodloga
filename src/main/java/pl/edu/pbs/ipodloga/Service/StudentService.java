package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.Zadanie;

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

    public void przypiszProjekt(String studentId, String projektId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection("student").document(studentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            Student student = document.toObject(Student.class);
            if (student.getProjektyId().contains(projektId)) {
                logger.info("Student: {} już jest przypisany do projektu: {}", studentId, projektId);
            } else {
                student.getProjektyId().add(projektId);
                firestore.collection("student").document(studentId).set(student);
                logger.info("Projekt: {} został przypisany do studenta: {}", projektId, studentId);
            }
        } else {
            throw new RuntimeException("Student o podanym id nie istnieje");
        }
    }

    public void przypiszZadanie(String studentId, String zadanieId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection("student").document(studentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            Student student = document.toObject(Student.class);

            DocumentReference zadanieReference = firestore.collection("zadanie").document(zadanieId);
            ApiFuture<DocumentSnapshot> zadanieFuture = zadanieReference.get();
            DocumentSnapshot zadanieDocument = zadanieFuture.get();
            if (zadanieDocument.exists()) {
                Zadanie zadanie = zadanieDocument.toObject(Zadanie.class);
                if (student.getProjektyId().contains(zadanie.getProjektId())) {
                    if (!student.getZadaniaId().contains(zadanieId)) {
                        student.getZadaniaId().add(zadanieId);
                        firestore.collection("student").document(studentId).set(student);
                        logger.info("Zadanie: {} zostało przypisane do studenta: {}", zadanieId, studentId);
                    } else {
                        logger.info("Zadanie: {} jest już przypisane do studenta: {}", zadanieId, studentId);
                    }
                } else {
                    throw new RuntimeException("Student nie jest przypisany do projektu, do którego należy zadanie");
                }
            } else {
                throw new RuntimeException("Zadanie o podanym id nie istnieje");
            }
        } else {
            throw new RuntimeException("Student o podanym id nie istnieje");
        }
    }
}