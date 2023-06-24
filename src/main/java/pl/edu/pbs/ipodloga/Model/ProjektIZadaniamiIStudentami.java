package pl.edu.pbs.ipodloga.Model;

import java.util.List;
import java.util.Map;

public class ProjektIZadaniamiIStudentami {
    private Projekt projekt;
    private List<Zadanie> zadania;
    private Map<String, List<Student>> zadanieStudentMap;

    public ProjektIZadaniamiIStudentami(Projekt projekt, List<Zadanie> zadania, Map<String, List<Student>> zadanieStudentMap) {
        this.projekt = projekt;
        this.zadania = zadania;
        this.zadanieStudentMap = zadanieStudentMap;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public List<Zadanie> getZadania() {
        return zadania;
    }

    public void setZadania(List<Zadanie> zadania) {
        this.zadania = zadania;
    }

    public Map<String, List<Student>> getZadanieStudentMap() {
        return zadanieStudentMap;
    }

    public void setZadanieStudentMap(Map<String, List<Student>> zadanieStudentMap) {
        this.zadanieStudentMap = zadanieStudentMap;
    }
}
