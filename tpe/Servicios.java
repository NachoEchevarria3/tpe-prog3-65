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

	/*
     Complejidad computacional: O(n)
     */
	public Servicios(String pathProcesadores, String pathTareas)
	{
		CSVReader reader = new CSVReader();
		reader.readProcessors(pathProcesadores);
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
}