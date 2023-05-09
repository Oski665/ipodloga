package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.Projekt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProjectService {

    private final Firestore firestore;
    private final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    public ProjectService(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Projekt> pobierzWszystkieProjekty() {
        List<Projekt> projekty = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("projekt").get().get().getDocuments();
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
    public String dodajProjekt(Projekt projekt) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("projekt").add(projekt);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }
}