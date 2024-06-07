package tpe;

import java.util.Collections;
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
	private int cantCandidatosBacktracking = 0;
	private int cantCandidatosGreedy = 0;
	private int mejorTiempoEjecBacktracking = Integer.MAX_VALUE;

	/*
     Complejidad computacional: O(n)
     */
	public Servicios(String pathProcesadores, String pathTareas) {
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

	public int getCantCandidatosBacktracking() {
		return cantCandidatosBacktracking;
	}

	public int getCantCandidatosGreedy() {
		return cantCandidatosGreedy;
	}

	/* 
		BACKTRACKING
	*/

	public LinkedList<Procesador> backtracking(int criticasMAX, int tiempoMAX) {
		LinkedList<Procesador> mejorSolucion = new LinkedList<>(this.procesadores);
		LinkedList<Tarea> tareasAsignar = new LinkedList<>(this.tareas.values());
		backtracking(new LinkedList<>(this.procesadores), mejorSolucion, 0, tareasAsignar, criticasMAX, tiempoMAX);
		return mejorSolucion;
	}

	private void backtracking(LinkedList<Procesador> solucionActual, LinkedList<Procesador> mejorSolucion, int indexTarea, LinkedList<Tarea> tareasAsignar, int criticasMAX, int tiempoMAX) {
		if (indexTarea == tareasAsignar.size()) {
			int maxTiempoEjecucionActual = obtenerMaxTiempoEjecucion(solucionActual);
			if (maxTiempoEjecucionActual < mejorTiempoEjecBacktracking) {
				mejorTiempoEjecBacktracking = maxTiempoEjecucionActual;
				copiarSolucion(solucionActual, mejorSolucion, criticasMAX, tiempoMAX);
			}
			return;
		}

		Tarea tareaActual = tareasAsignar.get(indexTarea);
		for (Procesador procesador : solucionActual) {
			procesador.asignarTarea(tareaActual, criticasMAX, tiempoMAX);
			backtracking(solucionActual, mejorSolucion, indexTarea + 1, tareasAsignar, criticasMAX, tiempoMAX);
			procesador.eliminarTarea(tareaActual);
		}
		return;
	}

	private int obtenerMaxTiempoEjecucion(LinkedList<Procesador> procesadores) {
		int maxTiempo = 0;
		for (Procesador procesador : procesadores) {
			// Se queda con el tiempo de ejecución del Procesador
			// con mayor tiempo de ejecución
			maxTiempo = Math.max(maxTiempo, procesador.getTiempo_ejecucion());
		}
		return maxTiempo;
	}

	public void copiarSolucion(LinkedList<Procesador> solucionActual, LinkedList<Procesador> mejorSolucion, int criticasMAX, int tiempoMAX) {
		mejorSolucion.clear();
		mejorSolucion.addAll(solucionActual);
		/*for (Procesador procesador : solucionActual) {
			Procesador copia = new Procesador(procesador.getId_procesador(), procesador.getCodigo_procesador(), procesador.isEsta_refrigerado(), procesador.getAño_funcionamiento());
			for (Tarea tarea : procesador.getTareasAsignadas()) {
				copia.asignarTarea(tarea, criticasMAX, tiempoMAX);
			}
			mejorSolucion.add(copia);
		}*/
	}

	public int getMejorTiempoEjecBacktracking() {
		return mejorTiempoEjecBacktracking;
	}

	/* 
		GREEDY
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
			cantCandidatosGreedy++;
			// Si el tiempo de  ejecucion del actual procesador es menor al del resultado
			// resultado se vuelve procesador actual.
			if (p.getTiempo_ejecucion() < resultado.getTiempo_ejecucion()) {
				resultado = p;
			}
		}

		return resultado;
	}

	public int getMejorTiempoEjecGreedy() {
		return obtenerMaxTiempoEjecucion(this.procesadores);
	}
}