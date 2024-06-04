package tpe;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

	private LinkedList<Tarea> ordenarTareas() {
		LinkedList<Tarea> listaTareas = new LinkedList<>();
		for (Map.Entry<String, Tarea> tarea : tareas.entrySet()) {
			listaTareas.add(tarea.getValue());
		}
		Collections.sort(listaTareas, new ComparadorTareas());
		return listaTareas;
	}

	/*
		La estrategia que pensamos para la solución Greedy del problema fue la siguiente:
			Como primer paso se ordenaron las tareas de mayor a menor tiempo de ejecución, y
			luego se asigno cada una al procesador con menor tiempo de ejecución acumulado
			en ese momento. En la primer iteración, se van a llenar todos los procesadores
			con al menos una tarea en caso de que haya más tareas que procesadores.
	*/
	public LinkedList<Procesador> greedy(int criticasMAX, int tiempoMAX) {
		// Ordena Tareas por tiempo de ejecución de mayor a menor.
		LinkedList<Tarea> tareasOrdenadas = this.ordenarTareas();
		while (!tareasOrdenadas.isEmpty()) {
			// Selecciona primer tarea.
			Tarea t = tareasOrdenadas.getFirst();
			// Selecciona procesador con menor tiempo de ejecución acumulado.
			Procesador p = this.seleccionarProcesador();
			// Asigna la tareas seleccionada al procesador seleccionado.
			boolean asignada = p.asignarTarea(t, criticasMAX, tiempoMAX);
			if (!asignada) {
				System.out.println("No se encontró solución valida");
				return new LinkedList<Procesador>();
			} 
			// Se elimina la tarea de la lista de tareas. 
			tareasOrdenadas.removeFirst();
		}

		return this.procesadores;
	}

	public Procesador seleccionarProcesador() {
		// Se obtiene el primer procesador.
		Procesador resultado = procesadores.getFirst();
		for (Procesador p : procesadores) {
			// Si el tiempo de  ejecucion del actual procesador es menor al del resultado
			// resultado se vuelve procesador actual.
			if (p.getTiempo_ejecucion() < resultado.getTiempo_ejecucion()) {
				resultado = p;
			}
		}

		return resultado;
	}
}