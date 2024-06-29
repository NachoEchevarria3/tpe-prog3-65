package tpe;

public class Main {
	//Poner las condiciones que se requieran
	private static final int criticasMAX = 2;
	private static final int tiempoMAX = 1000;
	public static void main(String args[]) {

		Servicios servicios = new Servicios("./tpe/datasets/Procesadores.csv", "./tpe/datasets/Tareas.csv");
		Solucion greedy = servicios.greedy(criticasMAX, tiempoMAX);
		Solucion backtracking = servicios.backtracking(criticasMAX, tiempoMAX);

		if (backtracking.getProcesadores().size() > 0) {
			System.out.println("Backtracking:");
			for (Procesador procesador : backtracking.getProcesadores()) {
				System.out.println(procesador);
			}
			System.out.println("Tiempo máximo de ejecución: " + backtracking.calcularTiempoEjecucion());
			System.out.println("Métrica para analizar el costo de la solución: " + servicios.getCantEstados());
		} else {
			System.out.println("Backtracking: No hay solución");
		}
		if(greedy.getProcesadores().size() > 0) {
			System.out.println("Greedy:");
			for (Procesador procesador : greedy.getProcesadores()) {
				System.out.println(procesador);
			}
			System.out.println("Tiempo máximo de ejecución: " + greedy.calcularTiempoEjecucion());
			System.out.println("Métrica para analizar el costo de la solución: " + servicios.getCantCandidatos());
		} else {
			System.out.println("Greedy: No hay solución");
		}
	}
}