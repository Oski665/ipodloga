package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
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

    public Student getStudentById(String studentId) {
        try {
            DocumentReference docRef = firestore.collection("student").document(studentId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                // Konwertuj dokument na obiekt Student
                return document.toObject(Student.class);
            } else {
                logger.error("Nie znaleziono studenta o id: {}", studentId);
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać studenta", e);
        }
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

    public Student aktualizujStudenta(String id, Student updatedStudent) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection("student").document(id);
        Student existingStudent = documentReference.get().get().toObject(Student.class);

        if (existingStudent != null) {
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setImie(updatedStudent.getImie());
            existingStudent.setNazwisko(updatedStudent.getNazwisko());
            existingStudent.setNr_indeksu(updatedStudent.getNr_indeksu());
            existingStudent.setStacjonarny(updatedStudent.getStacjonarny());
            existingStudent.setStudent_id(updatedStudent.getStudent_id());
            existingStudent.setProjektyId(updatedStudent.getProjektyId());
            existingStudent.setZadaniaId(updatedStudent.getZadaniaId());

            ApiFuture<WriteResult> writeResult = documentReference.set(existingStudent);
            logger.info("Zaktualizowano studenta o ID: {}", id);
        } else {
            throw new RuntimeException("Student o ID: " + id + " nie istnieje");
        }

        return existingStudent;
    }

    public String usunStudenta(String id) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> writeResult = firestore.collection("student").document(id).delete();
        return "Usunięto studenta o ID: " + id;
    }

}