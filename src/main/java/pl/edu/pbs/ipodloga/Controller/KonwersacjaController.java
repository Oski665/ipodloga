package pl.edu.pbs.ipodloga.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pbs.ipodloga.Model.Konwersacja;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.Wiadomosc;
import pl.edu.pbs.ipodloga.Service.ProjectService;
import java.util.List;
import java.util.Comparator;

@RestController
@RequestMapping("/im")
public class KonwersacjaController {

    private final ProjectService projectService;

    public KonwersacjaController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/conv/{id_projektu}")
    public ResponseEntity<List<Wiadomosc>> pobierzWiadomosciZKonwersacji(@PathVariable("id_projektu") String idProjektu) {
        try {
            Projekt projekt = projectService.pobierzProjekt(idProjektu);
            if (projekt != null) {
                String konwersacjaId = projekt.getKonwersacja();
                if (konwersacjaId != null) {
                    List<Wiadomosc> wiadomosci = projectService.pobierzWiadomosci(konwersacjaId);
                    wiadomosci.sort(Comparator.comparing(Wiadomosc::getDate));
                    return new ResponseEntity<>(wiadomosci, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/write/{id_projektu}")
    public ResponseEntity<String> dodajWiadomoscDoKonwersacji(
            @PathVariable("id_projektu") String idProjektu,
            @RequestBody Wiadomosc wiadomosc) {
        try {
            Projekt projekt = projectService.pobierzProjekt(idProjektu);
            if (projekt != null) {
                String konwersacjaId = projekt.getKonwersacja();
                if (konwersacjaId == null) {
                    konwersacjaId = utworzNowaKonwersacja();
                    projekt.setKonwersacja(konwersacjaId);
                    projectService.aktualizujProjekt(idProjektu, projekt);
                }
                String wiadomoscId = projectService.dodajWiadomosc(konwersacjaId, wiadomosc);
                return new ResponseEntity<>(wiadomoscId, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Projekt o podanym ID nie istnieje.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Wystąpił błąd serwera.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String utworzNowaKonwersacja() throws Exception {
        Konwersacja konwersacja = new Konwersacja();
        String konwersacjaId = projectService.dodajKonwersacje(konwersacja);
        return konwersacjaId;
    }
}
