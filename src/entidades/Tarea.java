package entidades;

import java.awt.List;

public class Tarea {
	public String titulo;
    private String descripcion;
    private double diasEstimados;
    public double diasTrabajados;
    public double diasRetraso;
    private boolean finalizada;
    public Empleado empleado;
    private List historialResponsables;




	public Tarea(String titulo, String descripcion, double diasEstimados) {
	
	    this.diasEstimados = (diasEstimados < 1) ? 1 : diasEstimados; 
	    this.titulo = titulo;
	    this.descripcion = descripcion;
	    this.empleado = null;
	    this.diasTrabajados = 0;
	    this.diasRetraso = 0;
	    this.finalizada = false;
	
	}
	
	
	public void asignarResponsable(Empleado empleado) {
	    this.empleado = empleado;
	}
	
	
	public boolean tieneResponsable() {
	    return this.empleado != null;
	}
	
	 public List  getHistorialResponsables(){
	     return historialResponsables; }
	
	
	public boolean estaFinalizada() {
	    return finalizada;
	}
	
	
	public void finalizar() {
	    this.finalizada = true;
	
	    if(this.diasTrabajados < 1) this.diasTrabajados = 1;
	    this.empleado = null; 
	}
	
	
	public void trabajar(double dias) {
	
	    if(dias <= 0) return;
	
	    this.diasTrabajados += dias;
	
	    if(this.diasTrabajados > diasEstimados) {
	        diasRetraso = diasTrabajados - diasEstimados;
	
	    }
	}
	
	
	public double getRetraso() {
	    return diasRetraso;
	}
	
	
	public String getTitulo() {
	    return titulo;
	}
	
	public double getDiasTrabajados() {
	    return (diasTrabajados < 1) ? 1 : diasTrabajados;
	}
	
	
	@Override
	public String toString() {
	    return "Tarea: " + titulo ;
	}
}
