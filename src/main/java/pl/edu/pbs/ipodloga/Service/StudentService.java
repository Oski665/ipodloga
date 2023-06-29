package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.Zadanie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Firestore firestore;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private ZadanieService zadanieService;
    @Autowired
    private ProjectService projectService;

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

    public Student pobierzStudentaPoStudentId(String studentId) {
        try {
            CollectionReference students = firestore.collection("student");
            Query query = students.whereEqualTo("student_id", studentId);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            if (!querySnapshot.get().getDocuments().isEmpty()) {
                DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
                Student student = document.toObject(Student.class);
                logger.info("Pobrano studenta: {}", student.getStudent_id());
                return student;
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać studenta", e);
        }
    }

    public List<Zadanie> getTasksForStudent(String studentId) {
        return zadanieService.getTasksForStudent(studentId);
    }

    public List<Projekt> getProjectsForStudent(String studentId) {
        List<Projekt> projekty = new ArrayList<>();
        try {
            // Znajdź wszystkie dokumenty "studentProjekt" dla danego studenta
            List<QueryDocumentSnapshot> studentProjektDocuments =
                    firestore.collection("studentProjekt").whereEqualTo("studentId", studentId).get().get().getDocuments();

            logger.info("Znaleziono {} dokumentów 'studentProjekt' dla studenta o id: {}", studentProjektDocuments.size(), studentId);

            // Dla każdego dokumentu "studentProjekt" znajdź odpowiadający mu dokument "projekt"
            for (QueryDocumentSnapshot studentProjektDocument : studentProjektDocuments) {
                String projektId = studentProjektDocument.getString("projektId");
                logger.info("Znaleziono 'projektId': {} dla dokumentu 'studentProjekt'", projektId);

                DocumentSnapshot projektDocument = firestore.collection("projekt").document(projektId).get().get();
                if (projektDocument.exists()) {
                    Projekt projekt = projektDocument.toObject(Projekt.class);
                    projekty.add(projekt);
                    logger.info("Dodano projekt: {} dla studenta: {}", projekt.getNazwa(), studentId);
                } else {
                    logger.warn("Dokument 'projekt' o id: {} nie istnieje", projektId);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać projektów", e);
        }
        return projekty;
    }

}
