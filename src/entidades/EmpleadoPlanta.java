package entidades;

public class EmpleadoPlanta extends Empleado {

	public EmpleadoPlanta(String nombre, Double valor) {
		super(nombre, valor);
		// TODO Auto-generated constructor stub
	}
	double calcularSueldo(){
		return this.valor;
	}
}
