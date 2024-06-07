package tpe;

import java.util.LinkedList;

public class Main {
	private static final int criticasMAX = 6;
	private static final int tiempoMAX = 130;
	public static void main(String args[]) {
		Servicios servicios = new Servicios("./tpe/datasets/Procesadores.csv", "./tpe/datasets/Tareas.csv");
		LinkedList<Procesador> backtracking = servicios.backtracking(criticasMAX, tiempoMAX);
		LinkedList<Procesador> greedy = servicios.greedy(criticasMAX, tiempoMAX);
		System.out.println("Backtracking:");
		for (Procesador procesador : backtracking) {
			System.out.println(procesador.getId_procesador() + " - Tareas asignadas: " + procesador.getTareasAsignadas() + " - Tiempo de ejecución: " + procesador.getTiempo_ejecucion());
		}
		System.out.println("Tiempo máximo de ejecución: " + servicios.getMejorTiempoEjecBacktracking());
		System.out.println("Métrica para analizar el costo de la solución: " + servicios.getCantCandidatosBacktracking());
		System.out.println("Greedy:");
		for (Procesador procesador : greedy) {
			System.out.println(procesador.getId_procesador() + " - Tareas asignadas: " + procesador.getTareasAsignadas() + " - Tiempo de ejecución: " + procesador.getTiempo_ejecucion());
		}
		System.out.println("Tiempo máximo de ejecución: " + servicios.getMejorTiempoEjecGreedy());
		System.out.println("Métrica para analizar el costo de la solución: " + servicios.getCantCandidatosGreedy());
	}
}