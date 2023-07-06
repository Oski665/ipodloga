package pl.edu.pbs.ipodloga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.Zadanie;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class IpodlogaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testGettersAndSettersProjekt() {
		Projekt projekt = new Projekt();

		projekt.setId("123");
		projekt.setNazwa("Testowy projekt");
		projekt.setOpis("To jest testowy opis");
		projekt.setStatus("on going");
		projekt.setDataczas_utworzenia("2023-06-27T10:00:00");

		Assertions.assertEquals("123", projekt.getId());
		Assertions.assertEquals("Testowy projekt", projekt.getNazwa());
		Assertions.assertEquals("To jest testowy opis", projekt.getOpis());
		Assertions.assertTrue(true);
		Assertions.assertEquals("2023-06-27T10:00:00", projekt.getDataczas_utworzenia());
	}

	@Test
	void testGettersAndSetterStudent() {
		Student student = new Student();

		student.setId("123");
		student.setEmail("test@example.com");
		student.setImie("Jan");
		student.setNazwisko("Kowalski");
		student.setNr_indeksu("123456");
		student.setStacjonarny(true);
		student.setStudent_id("1");

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
		Zadanie zadanie = new Zadanie();
		String expectedId = "123";

		zadanie.setId(expectedId);
		String actualId = zadanie.getId();

		assertEquals(expectedId, actualId, "Incorrect ID");
	}

	@Test
	void testSetAndGetKolejnoscZadanie() {
		Zadanie zadanie = new Zadanie();
		String expectedKolejnosc = "1";

		zadanie.setKolejnosc(expectedKolejnosc);
		String actualKolejnosc = zadanie.getKolejnosc();

		assertEquals(expectedKolejnosc, actualKolejnosc, "Incorrect kolejnosc");
	}

	@Test
	void testSetAndGetNazwaZadanie() {
		Zadanie zadanie = new Zadanie();
		String expectedNazwa = "Zadanie 1";

		zadanie.setNazwa(expectedNazwa);
		String actualNazwa = zadanie.getNazwa();

		assertEquals(expectedNazwa, actualNazwa, "Incorrect nazwa");
	}

	@Test
	void testSetAndGetOpisZadanie() {
		Zadanie zadanie = new Zadanie();
		String expectedOpis = "Opis zadania";

		zadanie.setOpis(expectedOpis);
		String actualOpis = zadanie.getOpis();

		assertEquals(expectedOpis, actualOpis, "Incorrect opis");
	}
}
