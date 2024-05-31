package tpe;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tpe.utils.CSVReader;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
	private Map<String, Tarea> tareas = new HashMap<>();
	private LinkedList<Tarea> tareasCriticas = new LinkedList<>();
	private LinkedList<Tarea> tareasNoCriticas = new LinkedList<>();
	private LinkedList<Procesador> procesadores = new LinkedList<>();

	/*
     Complejidad computacional: O(n)
     */
	public Servicios(String pathProcesadores, String pathTareas)
	{
		CSVReader reader = new CSVReader();
		this.procesadores = reader.readProcessors(pathProcesadores);
		this.tareas = reader.readTasks(pathTareas);
		this.tareasCriticas = getTareasCriticas(this.tareas, true);
		this.tareasNoCriticas = getTareasCriticas(this.tareas, false);
	}
	
	public LinkedList<Tarea> getTareasCriticas(Map<String, Tarea> listaTareas, boolean esCritica) {
		LinkedList<Tarea> resultado = new LinkedList<>();

		if (esCritica) {
			for (String id : listaTareas.keySet()) {
				Tarea tarea = listaTareas.get(id);
				if (tarea.isEs_critica()) {
					resultado.add(tarea);
				}
			}
		} else {
			for (String id : listaTareas.keySet()) {
				Tarea tarea = listaTareas.get(id);
				if (!tarea.isEs_critica()) {
					resultado.add(tarea);
				}
			}
		}

		return resultado;
	}

	/*
     Complejidad computacional: O(1)
    */
	public Tarea servicio1(String ID) {
		return tareas.get(ID);
	}
    
    /*
	  Complejidad computacional: O(1)
     */
	public List<Tarea> servicio2(boolean esCritica) {
		if (esCritica) return this.tareasCriticas;
		return this.tareasNoCriticas;
	}

    /*
      Complejidad computacional: O(n)
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		List<Tarea> resultado = new LinkedList<>();

		if (prioridadInferior > prioridadSuperior) {
			System.out.println("La prioridad inferior debe ser menor que la prioridad superior");
			return resultado;
		}

		for (String id : tareas.keySet()) {
			Tarea tarea = tareas.get(id);
			if (tarea.getNivel_prioridad() >= prioridadInferior && tarea.getNivel_prioridad() <= prioridadSuperior) {
				resultado.add(tarea);
			}
		}

		return resultado;
	}

	public LinkedList<Procesador> greedy() {
		// Guarda el mayor el tiempo de ejecucion (procesador con mayor tiempo de ejecucion)
		int mayorTiempoEjecucion = 0;

		// Si hay un solo procesador se le asigna todas las tareas.
		if (procesadores.size() == 1) {
			Procesador p = procesadores.get(0);
			while (!tareas.isEmpty()) {
				Tarea t = this.seleccionar();
				p.asignarTarea(t);
				this.tareas.remove(t.getId_tarea());
			}
		}
		while (!this.tareas.isEmpty()) {
			for (Procesador procesador : procesadores) {
				Tarea t = this.seleccionar(); // Selecciona tarea de mayor tiempo de ejecucion
				if(procesador.estaVacio()) {
					// Si el procesador esta vacio se le asigna la tarea.
					procesador.asignarTarea(t);
					this.tareas.remove(t.getId_tarea());
					if (procesador.getTiempo_ejecucion() > mayorTiempoEjecucion) {
						mayorTiempoEjecucion = procesador.getTiempo_ejecucion();
					}
				} else {
					// En caso contrario se asigna al procesador de menor tiempo de ejecucion
					if (procesador.getTiempo_ejecucion() < mayorTiempoEjecucion) {
						procesador.asignarTarea(t);
						this.tareas.remove(t.getId_tarea());
					}
				}
			}
		}

		return this.procesadores;
	}

	public Tarea seleccionar() {
		Tarea resultado = new Tarea(null, null, 0, false, 0);
		for (String id : tareas.keySet()) {
			Tarea t = tareas.get(id);
			if (t.getTiempo_ejecucion() > resultado.getTiempo_ejecucion()) {
				resultado = t;
			}
		}

		return resultado;
	}
}