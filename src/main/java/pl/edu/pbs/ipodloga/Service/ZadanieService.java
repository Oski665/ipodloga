package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.Zadanie;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
            // tutaj aktualizujesz pola istniejącego zadania na podstawie updatedZadanie
            existingZadanie.setKolejnosc(updatedZadanie.getKolejnosc());
            existingZadanie.setNazwa(updatedZadanie.getNazwa());
            existingZadanie.setOpis(updatedZadanie.getOpis());
            existingZadanie.setType(updatedZadanie.getType());
            existingZadanie.setPriority(updatedZadanie.getPriority());
            existingZadanie.setStatus(updatedZadanie.getStatus());
            existingZadanie.setProjektId(updatedZadanie.getProjektId());
            existingZadanie.setDeadline(updatedZadanie.getDeadline());
            // aktualizacja dokumentu w bazie danych
            ApiFuture<WriteResult> writeResult = documentReference.set(existingZadanie);
            logger.info("Zaktualizowano zadanie o ID: {} o czasie: {}", id, writeResult.get().getUpdateTime());

        } else {
            throw new NoSuchElementException("Zadanie o ID: " + id + " nie istnieje");
        }

        return existingZadanie;
    }
}