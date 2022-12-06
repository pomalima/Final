package Entidades;

public class Materia {

    private String id;
    private String regmateria;
    private String nrc;

    public Materia(String id, String regmateria, String nrc) {
        this.id = id;
        this.regmateria = regmateria;
        this.nrc = nrc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegmateria() {
        return regmateria;
    }

    public void setRegmateria(String regmateria) {
        this.regmateria = regmateria;
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
                "id='" + id + '\'' +
                ", regmateria='" + regmateria + '\'' +
                ", nrc='" + nrc + '\'' +
                '}';
    }
}
