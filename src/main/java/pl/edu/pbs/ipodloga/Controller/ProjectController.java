package pl.edu.pbs.ipodloga.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.Zadanie;
import pl.edu.pbs.ipodloga.Service.ProjectService;
import pl.edu.pbs.ipodloga.Service.StudentProjektService;
import pl.edu.pbs.ipodloga.Service.ZadanieProjektService;
import pl.edu.pbs.ipodloga.Service.ZadanieService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/projekty")
public class ProjectController {

    private final ProjectService projectService;
    private final ZadanieService zadanieService;

    @Autowired
    private StudentProjektService studentProjektService;
    @Autowired
    private ZadanieProjektService zadanieProjektService;

    public ProjectController(ProjectService projectService, ZadanieService zadanieService) {
        this.projectService = projectService;
        this.zadanieService = zadanieService;
    }

    @GetMapping
    public ResponseEntity<List<Projekt>> pobierzWszystkieProjekty(
            @RequestParam(defaultValue = "1") int strona,
            @RequestParam(defaultValue = "10") int iloscNaStrone) {
        List<Projekt> projekty = projectService.pobierzWszystkieProjekty(strona, iloscNaStrone);
        return new ResponseEntity<>(projekty, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Projekt> pobierzProjekt(@PathVariable("id") String id) {
        try {
            Projekt projekt = projectService.pobierzProjekt(id);
            if (projekt != null) {
                return new ResponseEntity<>(projekt, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> dodajProjekt(@RequestBody Projekt projekt) {
        try {
            System.out.println(projekt.getNazwa());
            String projectId = projectService.dodajProjekt(projekt);
            return new ResponseEntity<>(projectId, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/projekt/{id}")
    public ResponseEntity<Projekt> aktualizujProjekt(@PathVariable("id") String id, @RequestBody Projekt projekt) {
        try {
            Projekt aktualizowanyProjekt = projectService.aktualizujProjekt(id, projekt);
            return new ResponseEntity<>(aktualizowanyProjekt, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{projektId}/zadania")
    public Map<Zadanie, Student> pobierzZadaniaProjektu(@PathVariable String projektId) {
        return zadanieService.pobierzZadaniaProjektu(projektId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> usunProjekt(@PathVariable String id) {
        try {
            String message = projectService.usunProjekt(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/studentProjekt/{id}")
    public ResponseEntity<String> usunStudentProjekt(@PathVariable String id) {
        try {
            String message = studentProjektService.usunStudentProjekt(id);
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
}
