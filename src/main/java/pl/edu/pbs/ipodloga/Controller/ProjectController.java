package pl.edu.pbs.ipodloga.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projekty")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Projekt> pobierzWszystkieProjekty() {
        return projectService.pobierzWszystkieProjekty();
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
}
