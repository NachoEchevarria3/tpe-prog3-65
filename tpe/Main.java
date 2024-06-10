package tpe;

import java.util.LinkedList;

public class Main {
	
	//Poner las condiciones que se requieran
	private static final int criticasMAX = 2;
	private static final int tiempoMAX = 160;
	public static void main(String args[]) {

		Servicios servicios = new Servicios("./tpe/datasets/Procesadores.csv", "./tpe/datasets/Tareas.csv");
		LinkedList<Procesador> backtracking = servicios.backtracking(criticasMAX, tiempoMAX);
		LinkedList<Procesador> greedy = servicios.greedy(criticasMAX, tiempoMAX);

		if (backtracking.size() > 0) {
			System.out.println("Backtracking:");
			for (Procesador procesador : backtracking) {
				System.out.println(procesador);
			}
			System.out.println("Tiempo máximo de ejecución: " + servicios.getMejorTiempoEjecBacktracking());
			System.out.println("Métrica para analizar el costo de la solución: " + servicios.getCantEstados());
		} else {
			System.out.println("Backtracking: No hay solución");
		}
		if(greedy.size() > 0) {
			System.out.println("Greedy:");
			for (Procesador procesador : greedy) {
				System.out.println(procesador);
			}
			System.out.println("Tiempo máximo de ejecución: " + servicios.getMejorTiempoEjecGreedy());
			System.out.println("Métrica para analizar el costo de la solución: " + servicios.getCantCandidatos());
		} else {
			System.out.println("Greedy: No hay solución");
		}
	}
}