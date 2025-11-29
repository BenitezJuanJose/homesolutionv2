package entidades;

import java.util.ArrayList;
import java.util.List;

public class Empleado {
	public Integer legajo;
    public Integer retrasos;
    public String nombre;
    public List<Tarea> tareas;
    public Double valor;
    public boolean tieneRetraso;

    public Empleado(String nombre, Double valor) {
        this.valor = valor;
        this.nombre = nombre;
        this.retrasos = 0;
        this.tareas = new ArrayList<>(); 
        this.tieneRetraso = this.retrasos > 0;

    } 

    public void asignarTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }
    public void removerTarea(Tarea tarea) {
    	this.tareas.remove(tarea); 
    }

    public Double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return nombre + " (legajo " + legajo + ")";
    }

}
