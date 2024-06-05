package tpe;

import java.util.LinkedList;

public class Main {
	private static final int criticasMAX = 2;
	public static void main(String args[]) {
		Servicios servicios = new Servicios("./tpe/datasets/Procesadores.csv", "./tpe/datasets/Tareas.csv");
		LinkedList<Procesador> procesadores = servicios.greedy(criticasMAX, 200);
		for (Procesador procesador : procesadores) {
			System.out.println("Procesador: " + procesador + " tareas Asignadas" + procesador.getTareasAsignadas() + ", Tiempo de ejecución: " + procesador.getTiempo_ejecucion());
		}
		System.out.println("El tiempo de ejecución máximo es = " + servicios.getTiempoEjecucionMAX());
		System.out.println("La cantidad de candidatos considerados fueron = " + servicios.getCantCandidatos());
	}
}	