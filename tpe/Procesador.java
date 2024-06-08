package tpe;

import java.util.LinkedList;

public class Procesador {
    private String id_procesador;
    private String codigo_procesador;
    private boolean esta_refrigerado;
    private int año_funcionamiento;
    private LinkedList<Tarea> tareasAsignadas;
    private int tiempo_ejecucion, cant_criticas;

    public Procesador(String id_procesador, String codigo_procesador, boolean esta_refrigerado, int año_funcionamiento) {
        this.id_procesador = id_procesador;
        this.codigo_procesador = codigo_procesador;
        this.esta_refrigerado = esta_refrigerado;
        this.año_funcionamiento = año_funcionamiento;
        this.tareasAsignadas = new LinkedList<>();
        this.tiempo_ejecucion = 0;
        this.cant_criticas = 0;
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

    public boolean estaVacio() {
        return this.tareasAsignadas.size() == 0;
    }

    public boolean asignarTarea(Tarea tarea, int criticasMAX, int tiempoMAX) {
        if (tarea.isEs_critica() && this.cant_criticas == criticasMAX) return false;
        if (!this.isEsta_refrigerado() && (this.getTiempo_ejecucion() + tarea.getTiempo_ejecucion()) > tiempoMAX) return false;
        this.tareasAsignadas.add(tarea);
        this.tiempo_ejecucion += tarea.getTiempo_ejecucion();
        if (tarea.isEs_critica()) cant_criticas++;
        return true;
    }

    public void eliminarTarea(Tarea tarea) {
        if (tareasAsignadas.isEmpty()) return;
        for (Tarea t : tareasAsignadas) {
            if(t.getId_tarea() == tarea.getId_tarea()) {
                tareasAsignadas.remove(t);
                this.tiempo_ejecucion -= tarea.getTiempo_ejecucion();
                if (tarea.isEs_critica()) cant_criticas--;
            }
        }
    }

    public LinkedList<Tarea> getTareasAsignadas() {
        return this.tareasAsignadas;
    }

    public int getTiempo_ejecucion() {
        return this.tiempo_ejecucion;
    }
    
    public int getCant_criticas() {
        return cant_criticas;
    }

    @Override
    public String toString() {
        return this.id_procesador;
    }
}
   
