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
		while (!this.tareas.isEmpty()) {
			// Selecciona tarea con mayor tiempo de ejecución.
			Tarea t = this.seleccionarTarea();
			// Selecciona procesador con menor tiempo de ejecución acumulado.
			Procesador p = this.seleccionarProcesador();
			// Asigna la tareas seleccionada al procesador seleccionado.
			p.asignarTarea(t);
			// Se elimina la tarea de la lista de tareas.
			this.tareas.remove(t.getId_tarea());
		}

		return this.procesadores;
	}

	public Procesador seleccionarProcesador() {
		// Se obtiene el primer procesador.
		Procesador resultado = procesadores.getFirst();
		for (Procesador procesador : procesadores) {
			// Si el tiempo de  ejecucion del actual procesador es menor al del resultado
			// resultado se vuelve procesador actual.
			if (procesador.getTiempo_ejecucion() < resultado.getTiempo_ejecucion()) {
				resultado = procesador;
			}
		}

		return resultado;
	}

	public Tarea seleccionarTarea() {
		String idPrimeraTarea = tareas.keySet().iterator().next();
		Tarea resultado = tareas.get(idPrimeraTarea);
		for (String id : tareas.keySet()) {
			// Si el tiempo de  ejecucion de la actual tarea es mayor al del resultado
			// resultado se vuelve tarea actual.
			Tarea t = tareas.get(id);
			if (t.getTiempo_ejecucion() > resultado.getTiempo_ejecucion()) {
				resultado = t;
			}
		}

		return resultado;
	}
}