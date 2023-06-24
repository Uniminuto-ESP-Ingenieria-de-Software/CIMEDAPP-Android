package co.edu.uniminuto.cimedapp.model;

public class ModelCita {
    private int id;
    private String tipo;
    private String fecha;
    private String medio;
    private String idPaciente;

    public ModelCita(int id, String tipo, String fecha, String medio, String idPaciente)
    {
        this.setId(id);
        this.setTipo(tipo);
        this.setFecha(fecha);
        this.setMedio(medio);
        this.setIdPaciente(idPaciente);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }
}
