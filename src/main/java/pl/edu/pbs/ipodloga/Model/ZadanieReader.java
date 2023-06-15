package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ZadanieReader {
    private Firestore firestore;

    public ZadanieReader(Firestore firestore) {
        this.firestore = firestore;
    }

    public void wyswietlWszystkiZadania() {
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("zadanie").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Zadanie zadanie = document.toObject(Zadanie.class);
                System.out.println("Id: " + zadanie.getId());
                System.out.println("Kolejność: " + zadanie.getKolejnosc());
                System.out.println("Nazwa: " + zadanie.getNazwa());
                System.out.println("Opis: " + zadanie.getOpis());
                System.out.println("Typ: " + zadanie.getType());
                System.out.println("Priorytet: " + zadanie.getPriority());
                System.out.println("Status: " + zadanie.getStatus());
                System.out.println("Deadline: " + zadanie.getDeadline());
                System.out.println();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać projektów", e);
        }
    }
}

