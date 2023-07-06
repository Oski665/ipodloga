package pl.edu.pbs.ipodloga;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.StudentReader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class StudentReaderTest {

    @Mock
    private Firestore firestore;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private ApiFuture<QuerySnapshot> querySnapshotApiFuture;

    @Mock
    private QuerySnapshot querySnapshot;

    @Mock
    private QueryDocumentSnapshot documentSnapshot1;

    @Mock
    private QueryDocumentSnapshot documentSnapshot2;

    private PrintStream standardOut;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setup() throws ExecutionException, InterruptedException {
        MockitoAnnotations.openMocks(this);

        when(firestore.collection("student")).thenReturn(collectionReference);
        when(collectionReference.get()).thenReturn(querySnapshotApiFuture);
        when(querySnapshotApiFuture.get()).thenReturn(querySnapshot);

        when(querySnapshot.getDocuments()).thenReturn(List.of(documentSnapshot1, documentSnapshot2));

        when(documentSnapshot1.toObject(Student.class)).thenReturn(createStudent("test1@example.com", "Jan", "Kowalski", "123456", true));
        when(documentSnapshot2.toObject(Student.class)).thenReturn(createStudent("test2@example.com", "Anna", "Nowak", "654321", false));

        standardOut = System.out;
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);

        System.out.println("Captured output: " + outputStreamCaptor.toString());
    }

    @Test
    void testWyswietlWszystkichStudentow() {
        StudentReader studentReader = new StudentReader(firestore);
        studentReader.wyswietlWszystkichStudentow();

        String expectedOutput1 = "Email: test1@example.com\n" +
                "Imie: Jan\n" +
                "Nazwisko: Kowalski\n" +
                "Nr indeksu: 123456\n" +
                "Stacjonarny: true\n" +
                "Student ID: 1\n";
        String expectedOutput2 = "Email: test2@example.com\n" +
                "Imie: Anna\n" +
                "Nazwisko: Nowak\n" +
                "Nr indeksu: 654321\n" +
                "Stacjonarny: false\n" +
                "Student ID: 2\n";
        String actualOutput = outputStreamCaptor.toString();

        System.out.println("expectedOutput1: " + expectedOutput1);
        System.out.println("expectedOutput2: " + expectedOutput2);
        System.out.println("actualOutput: " + actualOutput);

        String[] expectedOutput1Parts = expectedOutput1.split("\\s+");
        String[] expectedOutput2Parts = expectedOutput2.split("\\s+");
        String[] actualOutputParts = actualOutput.split("\\s+");

        String joinedExpectedOutput1 = String.join(" ", expectedOutput1Parts);
        String joinedExpectedOutput2 = String.join(" ", expectedOutput2Parts);
        String joinedActualOutput = String.join(" ", actualOutputParts);


        assertTrue(joinedActualOutput.contains(joinedExpectedOutput1), "Expected output 1 not found in actual output");
        assertTrue(joinedActualOutput.contains(joinedExpectedOutput2), "Expected output 2 not found in actual output");
    }



    private Student createStudent(String email, String imie, String nazwisko, String nr_indeksu, boolean stacjonarny) {
        Student student = new Student();
        student.setEmail(email);
        student.setImie(imie);
        student.setNazwisko(nazwisko);
        student.setNr_indeksu(nr_indeksu);
        student.setStacjonarny(stacjonarny);
        return student;
    }
}
