package pl.edu.pbs.ipodloga.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pbs.ipodloga.Model.Zadanie;
import pl.edu.pbs.ipodloga.Service.ZadanieService;

import java.util.List;

@RestController
@RequestMapping("/zadania")
public class ZadanieController {

    private final ZadanieService zadanieService;

    public ZadanieController(ZadanieService zadanieService) {
        this.zadanieService = zadanieService;
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
}
