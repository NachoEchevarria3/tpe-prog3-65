package tpe;

import java.util.Comparator;

public class ComparadorTareas implements Comparator<Tarea> {
    @Override
    public int compare(Tarea t1, Tarea t2) {
        return t2.getTiempo_ejecucion() - t1.getTiempo_ejecucion();
    }
}
