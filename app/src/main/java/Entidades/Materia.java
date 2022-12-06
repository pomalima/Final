package Entidades;

public class Materia {

    private String nombre_materia;
    private String nrc;

    public Materia() {
    }

    public Materia(String nombre_materia, String nrc) {
        this.nombre_materia = nombre_materia;
        this.nrc = nrc;
    }

    public String getNombre_materia() {
        return nombre_materia;
    }

    public void setNombre_materia(String nombre_materia) {
        this.nombre_materia = nombre_materia;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "nombre_materia='" + nombre_materia + '\'' +
                ", nrc='" + nrc + '\'' +
                '}';
    }
}
