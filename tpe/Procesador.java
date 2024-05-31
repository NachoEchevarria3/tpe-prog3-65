package tpe;

public class Procesador {
    private String id_procesador;
    private String codigo_procesador;
    private boolean esta_refrigerado;
    private int año_funcionamiento;

    public Procesador(String id_procesador, String codigo_procesador, boolean esta_refrigerado, int año_funcionamiento) {
        this.id_procesador = id_procesador;
        this.codigo_procesador = codigo_procesador;
        this.esta_refrigerado = esta_refrigerado;
        this.año_funcionamiento = año_funcionamiento;
    }

    public String getId_procesador() {
        return this.id_procesador;
    }

    public String getCodigo_procesador() {
        return this.codigo_procesador;
    }

    public boolean isEsta_refrigerado() {
        return this.esta_refrigerado;
    }

    public int getAño_funcionamiento() {
        return this.año_funcionamiento;
    }

    @Override
    public String toString() {
        return this.id_procesador;
    }
}
