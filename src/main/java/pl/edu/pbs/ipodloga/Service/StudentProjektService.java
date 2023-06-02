package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.StudentProjekt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class StudentProjektService {

    private final Firestore firestore;
    private final Logger logger = LoggerFactory.getLogger(StudentProjektService.class);

    public StudentProjektService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String przypiszProjektDoStudenta(StudentProjekt studentProjekt) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("studentProjekt").add(studentProjekt);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }

    public List<Projekt> pobierzProjektyStudenta(String studentId) {
        List<Projekt> projekty = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("studentProjekt").whereEqualTo("studentId", studentId).get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Projekt projekt = document.toObject(Projekt.class);
                projekty.add(projekt);
                logger.info("Dodano projekt: {}", projekt.getNazwa());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać projektów", e);
        }
        return projekty;
    }

}