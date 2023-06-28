package pl.edu.pbs.ipodloga.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
//import com.google.cloud.firestore.DocumentReference;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import pl.edu.pbs.ipodloga.Model.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProjectService {

    private final Firestore firestore;
    private final ZadanieProjektService zadanieProjektService;
    private final StudentZadanieService studentZadanieService;
    private final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    public ProjectService(Firestore firestore, ZadanieProjektService zadanieProjektService, StudentZadanieService studentZadanieService) {
        this.firestore = firestore;
        this.zadanieProjektService = zadanieProjektService;
        this.studentZadanieService = studentZadanieService;
    }

    public List<Projekt> pobierzWszystkieProjekty(int strona, int iloscNaStrone) {
        List<Projekt> projekty = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("projekt")
                    .orderBy("nazwa")  // sortuj projekty alfabetycznie po nazwie
                    .offset((strona - 1) * iloscNaStrone)
                    .limit(iloscNaStrone)
                    .get().get().getDocuments();

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


    public Projekt pobierzProjekt(String id) {
        try {
            DocumentReference documentReference = firestore.collection("projekt").document(id);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                Projekt projekt = document.toObject(Projekt.class);
                logger.info("Pobrano projekt: {}", projekt.getNazwa());
                return projekt;
            } else {
                return null; // Jeśli projekt nie istnieje, zwróć null lub możesz rzucić wyjątek NotFoundException
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać projektu", e);
        }
    }

    public String dodajProjekt(Projekt projekt) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("projekt").add(projekt);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }

    public Projekt aktualizujProjekt(String id, Projekt updatedProjekt) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection("projekt").document(id);
        Projekt existingProjekt = documentReference.get().get().toObject(Projekt.class);

        if (existingProjekt != null) {
            // tutaj aktualizujesz pola istniejącego projektu na podstawie updatedProjekt
            existingProjekt.setNazwa(updatedProjekt.getNazwa());
            existingProjekt.setOpis(updatedProjekt.getOpis());
            existingProjekt.setStatus(updatedProjekt.getStatus());
            existingProjekt.setDataczas_utworzenia(updatedProjekt.getDataczas_utworzenia());
            existingProjekt.setDataczas_ukonczenia(updatedProjekt.getDataczas_ukonczenia());
            existingProjekt.setKonwersacja(updatedProjekt.getKonwersacja());

            // aktualizacja dokumentu w bazie danych
            ApiFuture<WriteResult> writeResult = documentReference.set(existingProjekt);
            logger.info("Zaktualizowano projekt o ID: {} o czasie: {}", id, writeResult.get().getUpdateTime());
        } else {
            throw new NoSuchElementException("Projekt o ID: " + id + " nie istnieje");
        }

        return existingProjekt;
    }

    public String usunProjekt(String id) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> writeResult = firestore.collection("projekt").document(id).delete();
        return "Usunięto projekt o ID: " + id;
    }

    public String dodajKonwersacje(Konwersacja konwersacja) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("konwersacja").add(konwersacja);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }

    public Konwersacja pobierzKonwersacje(String id) {
        try {
            DocumentReference documentReference = firestore.collection("konwersacja").document(id);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                Konwersacja konwersacja = document.toObject(Konwersacja.class);
                logger.info("Pobrano konwersację o ID: {}", id);
                return konwersacja;
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać konwersacji", e);
        }
    }

    public String dodajWiadomosc(String konwersacjaId, Wiadomosc wiadomosc) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection("konwersacja").document(konwersacjaId)
                .collection("wiadomosci").add(wiadomosc);
        DocumentReference documentReference = future.get();
        return documentReference.getId();
    }

    public List<Wiadomosc> pobierzWiadomosci(String konwersacjaId) {
        List<Wiadomosc> wiadomosci = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("konwersacja").document(konwersacjaId)
                    .collection("wiadomosci")
                    .orderBy("date")
                    .get().get().getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                Wiadomosc wiadomosc = document.toObject(Wiadomosc.class);
                wiadomosci.add(wiadomosc);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Nie udało się pobrać wiadomości", e);
        }
        return wiadomosci;
    }

    public ProjektIZadaniamiIStudentami pobierzProjektIZadaniamiIStudentami(String projektId) throws ExecutionException, InterruptedException {
        Projekt projekt = pobierzProjekt(projektId);
        List<Zadanie> zadaniaProjektu = zadanieProjektService.pobierzZadaniaProjektu(projektId);

        Map<String, List<Student>> zadanieStudentMap = new HashMap<>();
        for (Zadanie zadanie : zadaniaProjektu) {
            List<Student> studenciZadania = studentZadanieService.pobierzStudentowDlaZadania(zadanie.getId());
            zadanieStudentMap.put(zadanie.getId(), studenciZadania);
        }


        return new ProjektIZadaniamiIStudentami(projekt, zadaniaProjektu, zadanieStudentMap);
    }
}