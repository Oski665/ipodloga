package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.StudentZadanie;
import pl.edu.pbs.ipodloga.Model.Zadanie;

import java.util.ArrayList;
import java.util.List;
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

    public List<Zadanie> pobierzZadaniaStudenta(String studentId) {
        List<Zadanie> zadania = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("studentZadanie").whereEqualTo("studentId", studentId).get().get().getDocuments();
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
}