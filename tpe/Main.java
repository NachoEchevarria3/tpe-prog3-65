package tpe;

import java.util.LinkedList;

public class Main {
	public static void main(String args[]) {
		Servicios servicios = new Servicios("./tpe/datasets/Procesadores.csv", "./tpe/datasets/Tareas.csv");
		LinkedList<Procesador> procesadores = servicios.greedy();
		for (Procesador procesador : procesadores) {
			System.out.println("Procesador: " + procesador + ", Tiempo de ejecución: " + procesador.getTiempo_ejecucion());
		}
	}
}