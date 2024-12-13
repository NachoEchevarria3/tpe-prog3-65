package tpe;

import java.util.LinkedList;

public class Solucion {
    private LinkedList<Procesador> procesadores;
    private int tiempoEjecucion;

    public Solucion(LinkedList<Procesador> procesadores) {
        this.procesadores = new LinkedList<>(procesadores);
        this.tiempoEjecucion = Integer.MAX_VALUE;
    }

    public Solucion() {
        this.procesadores = new LinkedList<>();
        this.tiempoEjecucion = 0;
    }

    public LinkedList<Procesador> getProcesadores() {
        return procesadores;
    }

    public void setProcesadores(LinkedList<Procesador> solucionActual, int criticasMAX, int tiempoMAX) {
        this.procesadores.clear();
        for (Procesador procesador : solucionActual) {
            Procesador copia = new Procesador(procesador.getId_procesador(), procesador.getCodigo_procesador(), procesador.isEsta_refrigerado(), procesador.getAño_funcionamiento());
            for (Tarea tarea : procesador.getTareasAsignadas()) {
				copia.asignarTarea(tarea, criticasMAX, tiempoMAX);
			}
            this.procesadores.add(copia);
        }
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(int tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public int calcularTiempoEjecucion() {
		int maxTiempo = 0;
		for (Procesador procesador : this.procesadores) {
			// Se queda con el tiempo de ejecución del Procesador
			// con mayor tiempo de ejecución
			maxTiempo = Math.max(maxTiempo, procesador.getTiempo_ejecucion());
		}
		return maxTiempo;
	}
}
