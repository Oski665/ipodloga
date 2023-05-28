package pl.edu.pbs.ipodloga.Model;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirestoreReader {

    private Firestore firestore;

    public FirestoreReader(Firestore firestore) {
        this.firestore = firestore;
    }

    public void wyswietlWszystkieProjekty() {
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("projekt").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Projekt projekt = document.toObject(Projekt.class);
                System.out.println("Projekt ID: " + projekt.getId());
                System.out.println("Data utworzenia: " + projekt.getDataczas_utworzenia());
                System.out.println("Nazwa: " + projekt.getNazwa());
                System.out.println("Opis: " + projekt.getOpis());
                System.out.println("Projekt ID: " + projekt.getProjekt_id());
                System.out.println("Status: " + projekt.getStatus());
                System.out.println();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać projektów", e);
        }
    }
}
