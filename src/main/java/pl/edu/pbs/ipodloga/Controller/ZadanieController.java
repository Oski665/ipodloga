package pl.edu.pbs.ipodloga.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.Zadanie;
import pl.edu.pbs.ipodloga.Model.ZadanieProjekt;
import pl.edu.pbs.ipodloga.Service.StudentService;
import pl.edu.pbs.ipodloga.Service.StudentZadanieService;
import pl.edu.pbs.ipodloga.Service.ZadanieProjektService;
import pl.edu.pbs.ipodloga.Service.ZadanieService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/zadania")
public class ZadanieController {

    private final ZadanieService zadanieService;
    private final ZadanieProjektService zadanieProjektService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentZadanieService studentZadanieService;

    public ZadanieController(ZadanieService zadanieService, ZadanieProjektService zadanieProjektService) {
        this.zadanieService = zadanieService;
        this.zadanieProjektService = zadanieProjektService;
    }

    @GetMapping
    public List<Zadanie> pobierzWszystkieZadania() {
        return zadanieService.pobierzWszystkieZadania();
    }


    //ZMIANA NA SZYBKO
    @PostMapping
    public ResponseEntity<String> dodajZadanie(@RequestBody Zadanie zadanie) {
        try {
            System.out.println(zadanie.getNazwa());
            String zadanieId = zadanieService.dodajZadanie(zadanie);
            return new ResponseEntity<>(zadanieId, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{projektId}")
    public ResponseEntity<String> dodajZadanie2(@PathVariable String projektId, @RequestBody Zadanie zadanie) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> usunZadanie(@PathVariable String id) {
        try {
            String message = zadanieService.usunZadanie(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/studentZadanie/{id}")
    public ResponseEntity<String> usunStudentZadanie(@PathVariable String id) {
        try {
            String message = studentZadanieService.usunStudentZadanie(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/zadanieProjekt/{id}")
    public ResponseEntity<String> usunZadanieProjekt(@PathVariable String id) {
        try {
            String message = zadanieProjektService.usunZadanieProjekt(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/zadanie/{zadanieId}/przypisz-studenta/{studentId}")
    public ResponseEntity<Zadanie> przypiszStudentaDoZadania(@PathVariable String zadanieId, @PathVariable String studentId) {
        try {
            Zadanie zadanie = zadanieService.getTaskById(zadanieId);
            if (zadanie != null) {
                Student student = studentService.getStudentById(studentId);
                if (student != null) {
                    student.getZadaniaId().add(zadanieId);
                    studentService.aktualizujStudenta(studentId, student);
                    return ResponseEntity.ok(zadanie);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
