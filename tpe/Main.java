package tpe;

public class Main {
	public static void main(String args[]) {
		Servicios servicios = new Servicios("D:/Escritorio/Facultad/2024/Prog-3/src/tpe/datasets/Procesadores.csv", "D:/Escritorio/Facultad/2024/Prog-3/src/tpe/datasets/Tareas.csv");
		System.out.println(servicios.servicio3(20, 85));
	}
}