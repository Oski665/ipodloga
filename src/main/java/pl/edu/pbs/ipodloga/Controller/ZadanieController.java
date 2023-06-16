package pl.edu.pbs.ipodloga.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.Zadanie;
import pl.edu.pbs.ipodloga.Model.ZadanieProjekt;
import pl.edu.pbs.ipodloga.Service.ZadanieProjektService;
import pl.edu.pbs.ipodloga.Service.ZadanieService;

import java.util.List;

@RestController
@RequestMapping("/zadania")
public class ZadanieController {

    private final ZadanieService zadanieService;
    private final ZadanieProjektService zadanieProjektService;

    public ZadanieController(ZadanieService zadanieService, ZadanieProjektService zadanieProjektService) {
        this.zadanieService = zadanieService;
        this.zadanieProjektService = zadanieProjektService;
    }

    @GetMapping
    public List<Zadanie> pobierzWszystkieZadania() {
        return zadanieService.pobierzWszystkieZadania();
    }

    @PostMapping("/{projektId}")
    public ResponseEntity<String> dodajZadanie(@PathVariable String projektId, @RequestBody Zadanie zadanie) {
        try {
            zadanie.setProjektId(projektId);
            System.out.println(zadanie.getNazwa());
            String zadanieId = zadanieService.dodajZadanie(zadanie);
            return new ResponseEntity<>(zadanieId, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/projekty/{id}")
    public List<Zadanie> pobierzTaskiProjektu(@PathVariable("id") String id)  {
        return zadanieService.pobierzTaskiProjektu(id);
    }

    @PostMapping("/zadanieProjekt")
    public ResponseEntity<String> przypiszZadanieDoProjektu(@RequestBody ZadanieProjekt zadanieProjekt) {
        try {
            String id = zadanieProjektService.przypiszZadanieDoProjektu(zadanieProjekt);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/projekty/{projektId}/zadania")
    public List<Zadanie> pobierzZadaniaProjektu(@PathVariable("projektId") String projektId) {
        return zadanieProjektService.pobierzZadaniaProjektu(projektId);
    }

    @PutMapping("/zadanie/{id}")
    public ResponseEntity<Zadanie> aktualizujZadanie(@PathVariable("id") String id, @RequestBody Zadanie zadanie) {
        try {
            Zadanie aktualizowaneZadanie = zadanieService.aktualizujZadanie(id, zadanie);
            return new ResponseEntity<>(aktualizowaneZadanie, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
