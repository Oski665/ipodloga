package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.Zadanie;

import java.util.ArrayList;
import java.util.List;
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

    public String dodajZadanie(Zadanie zadanie) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("zadanie").add(zadanie);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }
}
