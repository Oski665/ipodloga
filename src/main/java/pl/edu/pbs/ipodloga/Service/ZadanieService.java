package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.Zadanie;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class ZadanieService {

    private final Firestore firestore;
    private final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    public ZadanieService(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Zadanie> pobierzWszystkieZadania() {
        List<Zadanie> zadania = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("zadanie").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Zadanie zadanie = document.toObject(Zadanie.class);
                zadania.add(zadanie);
                logger.info("Dodano zadanie: {}", zadanie.getNazwa());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać zadań", e);
        }
        return zadania;
    }

    public List<Zadanie> pobierzTaskiProjektu(String id) {
        List<Zadanie> taski = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("zadanie").whereEqualTo("projektId", id).get().get().getDocuments();;
            for (QueryDocumentSnapshot document : documents) {
                Zadanie zadanie = document.toObject(Zadanie.class);
                taski.add(zadanie);
                logger.info("Dodano task: {}", zadanie.getNazwa());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać projektów", e);
        }
        return taski;
    }

    public String dodajZadanie(Zadanie zadanie) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("zadanie").add(zadanie);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }

    public Zadanie aktualizujZadanie(String id, Zadanie updatedZadanie) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection("zadanie").document(id);
        Zadanie existingZadanie = documentReference.get().get().toObject(Zadanie.class);

        if (existingZadanie != null) {
            existingZadanie.setKolejnosc(updatedZadanie.getKolejnosc());
            existingZadanie.setNazwa(updatedZadanie.getNazwa());
            existingZadanie.setOpis(updatedZadanie.getOpis());
            existingZadanie.setType(updatedZadanie.getType());
            existingZadanie.setPriority(updatedZadanie.getPriority());
            existingZadanie.setStatus(updatedZadanie.getStatus());
            existingZadanie.setProjektId(updatedZadanie.getProjektId());
            existingZadanie.setDeadline(updatedZadanie.getDeadline());
            ApiFuture<WriteResult> writeResult = documentReference.set(existingZadanie);
            logger.info("Zaktualizowano zadanie o ID: {} o czasie: {}", id, writeResult.get().getUpdateTime());

        } else {
            throw new NoSuchElementException("Zadanie o ID: " + id + " nie istnieje");
        }

        return existingZadanie;
    }

    public Map<Zadanie, Student> pobierzZadaniaProjektu(String projektId) {
        Map<Zadanie, Student> zadaniaStudentMap = new HashMap<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("zadanie").whereEqualTo("projektId", projektId).get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Zadanie zadanie = document.toObject(Zadanie.class);

                List<QueryDocumentSnapshot> studentDocuments = firestore.collection("studentZadanie").whereEqualTo("zadanieId", zadanie.getId()).get().get().getDocuments();

                for (QueryDocumentSnapshot studentDocument : studentDocuments) {
                    String studentId = studentDocument.getString("studentId");
                    DocumentSnapshot studentSnapshot = firestore.collection("student").document(studentId).get().get();
                    if (studentSnapshot.exists()) {
                        Student student = studentSnapshot.toObject(Student.class);
                        zadaniaStudentMap.put(zadanie, student);
                        logger.info("Dodano zadanie: {} dla studenta: {}", zadanie.getNazwa(), student.getImie());
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać zadań", e);
        }
        return zadaniaStudentMap;
    }

    public String usunZadanie(String id) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> writeResult = firestore.collection("zadanie").document(id).delete();
        return "Usunięto zadanie o ID: " + id;
    }

    public Zadanie getTaskById(String id) throws InterruptedException, ExecutionException {
        DocumentSnapshot document = firestore.collection("zadanie").document(id).get().get();
        if (document.exists()) {
            return document.toObject(Zadanie.class);
        } else {
            throw new NoSuchElementException("Zadanie o ID: " + id + " nie istnieje");
        }
    }

    public List<Zadanie> getTasksForStudent(String studentId) {
        List<Zadanie> zadania = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("studentZadanie").whereEqualTo("studentId", studentId).get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String zadanieId = document.getString("zadanieId");
                DocumentSnapshot zadanieDocument = firestore.collection("zadanie").document(zadanieId).get().get();
                if (zadanieDocument.exists()) {
                    Zadanie zadanie = zadanieDocument.toObject(Zadanie.class);
                    zadania.add(zadanie);
                    logger.info("Dodano zadanie: {} dla studenta: {}", zadanie.getNazwa(), studentId);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać zadań", e);
        }
        return zadania;
    }
}