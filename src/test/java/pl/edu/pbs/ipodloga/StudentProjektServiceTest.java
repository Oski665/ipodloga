package pl.edu.pbs.ipodloga;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.google.api.core.ApiFuture;
import org.junit.Test;
import org.mockito.Mockito;
import com.google.cloud.firestore.*;
import pl.edu.pbs.ipodloga.Model.StudentProjekt;
import pl.edu.pbs.ipodloga.Service.StudentProjektService;

import java.util.concurrent.ExecutionException;

public class StudentProjektServiceTest {

    @Test
    public void testPrzypiszProjektDoStudenta() {
        Firestore firestore = Mockito.mock(Firestore.class);
        CollectionReference collectionReference = Mockito.mock(CollectionReference.class);
        ApiFuture<DocumentReference> future = Mockito.mock(ApiFuture.class);
        DocumentReference documentReference = Mockito.mock(DocumentReference.class);

        when(firestore.collection(anyString())).thenReturn(collectionReference);
        when(collectionReference.add(any())).thenReturn(future);
        try {
            when(future.get()).thenReturn(documentReference);
            when(documentReference.getId()).thenReturn("test-id");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        StudentProjektService studentProjektService = new StudentProjektService(firestore);
        StudentProjekt studentProjekt = new StudentProjekt();

        try {
            String id = studentProjektService.przypiszProjektDoStudenta(studentProjekt);
            assertEquals("test-id", id);
        } catch (Exception e) {
            fail("Nie powinno rzucać wyjątku");
        }
    }
}
