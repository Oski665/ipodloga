package pl.edu.pbs.ipodloga.Controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pbs.ipodloga.Model.*;
import pl.edu.pbs.ipodloga.Service.ProjectService;
import pl.edu.pbs.ipodloga.Service.StudentProjektService;
import pl.edu.pbs.ipodloga.Service.StudentService;
import pl.edu.pbs.ipodloga.Service.StudentZadanieService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentProjektService studentProjektService;
    private final StudentZadanieService studentZadanieService;
    private final Firestore firestore;


    @Autowired
    public StudentController(Firestore firestore, StudentService studentService, StudentProjektService studentProjektService, StudentZadanieService studentZadanieService) {
        this.firestore = firestore;
        this.studentProjektService = studentProjektService;
        this.studentZadanieService = studentZadanieService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> pobierzWszystkichStudentow() {
        List<Student> studenty = studentService.pobierzWszystkichStudentow();
        return new ResponseEntity<>(studenty, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> dodajStudenta(@RequestBody Student student) {
        try {
            String id = studentService.dodajStudenta(student);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/{studentId}/zadania")
//    public List<Pair<Student, Zadanie>> pobierzZadaniaStudenta(@PathVariable String studentId) {
//        return studentZadanieService.pobierzZadaniaStudenta(studentId);
//    }

    @PostMapping("/{studentId}/projekty/{projektId}")
    public ResponseEntity<String> przypiszProjekt(@PathVariable String studentId, @PathVariable String projektId) {
        try {
            // Pobierz studenta na podstawie przekazanego studentId
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                return new ResponseEntity<>("Nie znaleziono studenta", HttpStatus.NOT_FOUND);
            }

            StudentProjekt studentProjekt = new StudentProjekt();
            studentProjekt.setStudentId(student.getId());  // Użyj pobranego id studenta
            studentProjekt.setProjektId(projektId);
            studentProjektService.przypiszProjektDoStudenta(studentProjekt);

            // Aktualizacja listy projektów studenta
            student.getProjektyId().add(projektId);
            studentService.aktualizujStudenta(studentId, student);

            return new ResponseEntity<>("Projekt został przypisany do studenta", HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{studentId}/zadania/{zadanieId}")
    public ResponseEntity<String> przypiszZadanie(@PathVariable String studentId, @PathVariable String zadanieId) {
        try {
            // Pobierz studenta na podstawie przekazanego studentId
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                return new ResponseEntity<>("Nie znaleziono studenta", HttpStatus.NOT_FOUND);
            }

            StudentZadanie studentZadanie = new StudentZadanie();
            studentZadanie.setStudentId(student.getId());
            studentZadanie.setZadanieId(zadanieId);
            studentZadanieService.przypiszZadanieDoStudenta(studentZadanie);
            return new ResponseEntity<>("Zadanie zostało przypisane do studenta", HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> aktualizujStudenta(@PathVariable String id, @RequestBody Student updatedStudent) {
        try {
            Student student = studentService.aktualizujStudenta(id, updatedStudent);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{projectId}/students")
    public List<Student> getStudentsForProject(@PathVariable String projectId) throws Exception {
        ApiFuture<QuerySnapshot> query = firestore.collection("studentProjekt").whereEqualTo("projektId", projectId).get();

        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        List<Student> students = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String studentId = document.getString("studentId");

            DocumentSnapshot studentSnapshot = firestore.collection("student").document(studentId).get().get();

            if (studentSnapshot.exists()) {
                Student student = studentSnapshot.toObject(Student.class);
                students.add(student);
            }
        }

        return students;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> usunStudenta(@PathVariable String id) {
        try {
            String message = studentService.usunStudenta(id);
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

    @DeleteMapping("/studentProjekt/{id}")
    public ResponseEntity<String> usunStudentProjekt(@PathVariable String id) {
        try {
            String message = studentProjektService.usunStudentProjekt(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{studentId}/projects")
    public List<Projekt> getProjectsForStudents(@PathVariable String studentId) throws Exception {
        ApiFuture<QuerySnapshot> query = firestore.collection("studentProjekt").whereEqualTo("studentId", studentId).get();

        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        List<Projekt> projects = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String projektId = document.getString("projektId");

            DocumentSnapshot projectSnapshot = firestore.collection("projekt").document(projektId).get().get();

            if (projectSnapshot.exists()) {
                Projekt projekt = projectSnapshot.toObject(Projekt.class);
                projects.add(projekt);
            }
        }

        return projects;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> pobierzStudentId(@PathVariable("studentId") String studentId) {
        try {
            Student student = studentService.pobierzStudentaPoStudentId(studentId);
            if (student != null) {
                return new ResponseEntity<>(student, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{studentId}/zadania")
    public List<Zadanie> getStudentTasks(@PathVariable String studentId) {
        return studentService.getTasksForStudent(studentId);
    }

    @GetMapping("/{studentId}/projekty")
    public ResponseEntity<List<Projekt>> pobierzProjektyStudenta(@PathVariable String studentId) {
        List<Projekt> projekty = studentService.getProjectsForStudent(studentId);
        if (projekty.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projekty, HttpStatus.OK);
    }
}