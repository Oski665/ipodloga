package pl.edu.pbs.ipodloga;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.junit.Test;
import org.mockito.Mockito;
import pl.edu.pbs.ipodloga.Model.StudentZadanie;
import pl.edu.pbs.ipodloga.Service.StudentZadanieService;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class StudentZadanieServiceTest {

//    @Test
//    public void testPrzypiszZadanieDoStudenta() {
//        Firestore firestore = Mockito.mock(Firestore.class);
//        CollectionReference collectionReference = Mockito.mock(CollectionReference.class);
//        ApiFuture<DocumentReference> future = Mockito.mock(ApiFuture.class);
//        DocumentReference documentReference = Mockito.mock(DocumentReference.class);
//
//        when(firestore.collection(anyString())).thenReturn(collectionReference);
//        when(collectionReference.add(any())).thenReturn(future);
//        try {
//            when(future.get()).thenReturn(documentReference);
//            when(documentReference.getId()).thenReturn("test-id");
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        StudentZadanieService studentZadanieService = new StudentZadanieService(firestore);
//        StudentZadanie studentZadanie = new StudentZadanie();
//
//        try {
//            String id = studentZadanieService.przypiszZadanieDoStudenta(studentZadanie);
//            assertEquals("test-id", id);
//        } catch (Exception e) {
//            e.printStackTrace();  // wydrukuje szczegóły wyjątku
//            fail("Nie powinno rzucać wyjątku");
//        }
//    }
}
