package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.Zadanie;
import pl.edu.pbs.ipodloga.Model.ZadanieProjekt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ZadanieProjektService {
    private final Firestore firestore;
    private final Logger logger = LoggerFactory.getLogger(ZadanieProjektService.class);

    public ZadanieProjektService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String przypiszZadanieDoProjektu(ZadanieProjekt zadanieProjekt) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("zadanieProjekt").add(zadanieProjekt);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }

    public List<Zadanie> pobierzZadaniaProjektu(String projektId) {
        List<Zadanie> zadania = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("zadanieProjekt").whereEqualTo("projektId", projektId).get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Zadanie zadanie = document.toObject(Zadanie.class);
                zadania.add(zadanie);
                logger.info("Dodano zadanie do projektu: {}", zadanie.getNazwa());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać zadań projektu", e);
        }
        return zadania;
    }
}
