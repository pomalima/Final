package Entidades;

public class Nota {
    private String nota;
    private String observacion;

    public Nota() {
    }

    public Nota(String nota, String observacion) {
        this.nota = nota;
        this.observacion = observacion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "nota='" + nota + '\'' +
                ", observacion='" + observacion + '\'' +
                '}';
    }
}
