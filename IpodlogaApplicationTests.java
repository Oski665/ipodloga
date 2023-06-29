package pl.edu.pbs.ipodloga;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.Zadanie;



@SpringBootTest
class IpodlogaApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testGettersAndSettersProjekt() {
        // Tworzenie obiektu testowego
        Projekt projekt = new Projekt();

        // Ustawianie wartości pól
        projekt.setId("123");
        projekt.setNazwa("Testowy projekt");
        projekt.setOpis("To jest testowy opis");
        projekt.setProjekt_id(1L);
        projekt.setStatus(true);
        projekt.setDataczas_utworzenia("2023-06-27T10:00:00");

        // Sprawdzanie poprawności pobierania wartości pól
        Assertions.assertEquals("123", projekt.getId());
        Assertions.assertEquals("Testowy projekt", projekt.getNazwa());
        Assertions.assertEquals("To jest testowy opis", projekt.getOpis());
        Assertions.assertEquals(1L, projekt.getProjekt_id());
        Assertions.assertTrue(projekt.getStatus());
        Assertions.assertEquals("2023-06-27T10:00:00", projekt.getDataczas_utworzenia());
    }

	@Test
    void testGettersAndSetterStudent() {
        // Tworzenie obiektu testowego
        Student student = new Student();

        // Ustawianie wartości pól
        student.setId("123");
        student.setEmail("test@example.com");
        student.setImie("Jan");
        student.setNazwisko("Kowalski");
        student.setNr_indeksu("123456");
        student.setStacjonarny(true);
        student.setStudent_id(1L);

        // Sprawdzanie poprawności pobierania wartości pól
        Assertions.assertEquals("123", student.getId());
        Assertions.assertEquals("test@example.com", student.getEmail());
        Assertions.assertEquals("Jan", student.getImie());
        Assertions.assertEquals("Kowalski", student.getNazwisko());
        Assertions.assertEquals("123456", student.getNr_indeksu());
        Assertions.assertTrue(student.getStacjonarny());
        Assertions.assertEquals(1L, student.getStudent_id());
    }

    @Test
    void testSetAndGetIdZadanie() {
        // Arrange
        Zadanie zadanie = new Zadanie();
        String expectedId = "123";

        // Act
        zadanie.setId(expectedId);
        String actualId = zadanie.getId();

        // Assert
        assertEquals(expectedId, actualId, "Incorrect ID");
    }

    @Test
    void testSetAndGetKolejnoscZadanie() {
        // Arrange
        Zadanie zadanie = new Zadanie();
        String expectedKolejnosc = "1";

        // Act
        zadanie.setKolejnosc(expectedKolejnosc);
        String actualKolejnosc = zadanie.getKolejnosc();

        // Assert
        assertEquals(expectedKolejnosc, actualKolejnosc, "Incorrect kolejnosc");
    }

    @Test
    void testSetAndGetNazwaZadanie() {
        // Arrange
        Zadanie zadanie = new Zadanie();
        String expectedNazwa = "Zadanie 1";

        // Act
        zadanie.setNazwa(expectedNazwa);
        String actualNazwa = zadanie.getNazwa();

        // Assert
        assertEquals(expectedNazwa, actualNazwa, "Incorrect nazwa");
    }

    @Test
    void testSetAndGetOpisZadanie() {
        // Arrange
        Zadanie zadanie = new Zadanie();
        String expectedOpis = "Opis zadania";

        // Act
        zadanie.setOpis(expectedOpis);
        String actualOpis = zadanie.getOpis();

        // Assert
        assertEquals(expectedOpis, actualOpis, "Incorrect opis");
    }
}