package entidades;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import entidades.Empleado;
import entidades.EmpleadoExperto;
import entidades.EmpleadoInicial;
import entidades.EmpleadoTecnico;
import entidades.IHomeSolution;
import entidades.Proyecto;
import entidades.Tupla;
public class HomeSolution implements IHomeSolution{
	private int generateIdEmpleado = 1000;
	private  int numeroDeProyecto = 1000;
	private Map  <Integer, Empleado> listaDeEmpleados = new HashMap<>();
	private Map <Integer, Proyecto> listaDeProyectos = new HashMap<>();
	
	
	@Override
	public void registrarEmpleado(String nombre, double valor) throws IllegalArgumentException {
		/*
		 *  añadimos los empleados en un Hash Map para poder acceder a ellos de forma rapida 
		 *  en los proximos metodos  y la clave de cada empleado es su legajo 
		 * */
		if(nombre == null  || nombre.isEmpty() ){ 
			throw new IllegalArgumentException("El nombre del empleado no puede estar vacío.");
			} 
		if(valor <= 0) {throw new IllegalArgumentException("El salario debe ser mayor que 0.");}
	
		Empleado e = new Empleado(nombre, valor);
		listaDeEmpleados.put(generateIdEmpleado, e);
		generateIdEmpleado++;
	}

	
	private Empleado buscarEmpleadoConMenosRetraso() {
		Empleado empleado = null;
		int menorRetraso = Integer.MAX_VALUE;
	    for (Empleado e : listaDeEmpleados.values()) {
	        if (e.retrasos == 0) {
	            return e;
	        }
	        if (e.retrasos < menorRetraso) {
	            menorRetraso = e.retrasos;
	            empleado = e;
	        }
	    }
	    
	    return empleado; 
	}

	@Override
	public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
		
		
		if( nombre == null  || nombre.isEmpty() ) { 
			throw new IllegalArgumentException("El nombre del empleado no puede estar vacío.");
		}
		if(valor <= 0) {throw new IllegalArgumentException("El salario debe ser mayor que 0.");}
		
		
		if(categoria.toUpperCase().equals("INICIAL")) {
			EmpleadoInicial e = new EmpleadoInicial(categoria, valor);
			listaDeEmpleados.put(generateIdEmpleado, e);
			generateIdEmpleado++;
		}else
		if(categoria.toUpperCase().equals("TECNICO")) {
			EmpleadoTecnico e = new EmpleadoTecnico(categoria, valor);
			listaDeEmpleados.put(generateIdEmpleado, e);
			generateIdEmpleado++;
		}else
		if(categoria.toUpperCase().equals("EXPERTO")) {
			EmpleadoExperto e = new EmpleadoExperto(categoria, valor);
			listaDeEmpleados.put(generateIdEmpleado, e);
			generateIdEmpleado++;
		}else {
			
		    	throw new IllegalArgumentException("La categoria invalida");
		    
		}
		
	}

	@Override
	public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias, String domicilio,
			String[] cliente, String inicio, String fin) throws IllegalArgumentException {
			
			if (inicio == null || fin == null) {
		        throw new IllegalArgumentException("Las fechas de inicio y fin no pueden ser nulas");
		    }
	
		    
		    if (fin.compareTo(inicio) < 0) {
		        throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la de inicio");
		    }
		
			
			Proyecto p = new Proyecto(titulos,descripcion,dias,domicilio,cliente,inicio,fin);
			p.estado = "PENDIENTE";
			listaDeProyectos.put(numeroDeProyecto, p);
			numeroDeProyecto++;
		
	}

	@Override
	public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {
		Proyecto p = listaDeProyectos.get(numero);
		Tarea t = p.buscarTarea(titulo);
		Empleado e = this.buscarEmpleadoConMenosRetraso();
		
		if(e == null ) throw new Exception("numero de legajo no corresponde a un empleado");
		e.asignarTarea(t);
		
		
	}

	@Override
	public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
	   Empleado empleado = this.buscarEmpleadoConMenosRetraso();
	   Proyecto proyecto = listaDeProyectos.get(numero);
	   System.out.println(empleado);
	   if(empleado == null) {
		   proyecto.estado="PENDIENTE";
		   throw new Exception("no hay empleados disponibles");
	   };
	   
	  
	   
	   Tarea tarea = proyecto.buscarTarea(titulo);
	   empleado.asignarTarea(tarea);
	   for(Tarea t : proyecto.listaDeTareas) {
		   if(t.titulo.equals(titulo)) {
			   t.asignarResponsable(empleado);
		   }
	   }
		
	}

	@Override
	public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias)
			throws IllegalArgumentException {
			Proyecto p = listaDeProyectos.get(numero);
			
			for(Tarea t : p.listaDeTareas) {
				if(t.titulo.equals(titulo)) {
					t.diasRetraso+=cantidadDias;
				}
			}
		
		
	}

	@Override
	public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias)
			throws IllegalArgumentException {
			Proyecto p = listaDeProyectos.get(numero);
			if (p == null) {
		        throw new IllegalArgumentException("No existe el proyecto con número: " + numero);
		    }
			
			
			
		    p.titulos = Arrays.copyOf(p.titulos, p.titulos.length + 1);
		    p.descripciones = Arrays.copyOf(p.descripciones, p.descripciones.length + 1);
		    p.duracion = Arrays.copyOf(p.duracion, p.duracion.length + 1);

		   
		    int pos = p.titulos.length - 1;

		    p.titulos[pos] = titulo;
		    p.descripciones[pos] = descripcion;
		    p.duracion[pos] = dias;
			
		
	}

	@Override
	public void finalizarTarea(Integer numero, String titulo) throws Exception {
		Proyecto  p = listaDeProyectos.get(numero);
		
		Tarea tarea = null;
		for(Tarea t : p.listaDeTareas) {
			if(t.titulo.equals(titulo)) {
				tarea = t;
			}
		}
		if(tarea.estaFinalizada()) throw new Exception("Tarea ya esta finalizada");
		int legajo = tarea.empleado.legajo;
		Empleado e = listaDeEmpleados.get(legajo);
		e.removerTarea(tarea);
		tarea.finalizar();
	}

	@Override
	public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Proyecto p = listaDeProyectos.get(numero);
		for(Tarea t : p.listaDeTareas) {
			if(!t.estaFinalizada()) {
				throw new  IllegalArgumentException("se debe finalizar todas las tareas");
			}
		}
		
		p.finalizarProyecto();;
	}

	@Override
	public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double costoProyecto(Integer numero) {
		Proyecto p = listaDeProyectos.get(numero);
		double total = 0;
		for(Tarea t : p.listaDeTareas) {
			if(t.estaFinalizada()) {
				total += (t.diasTrabajados * t.empleado.valor); 
			}
		}
		
		return 0;
	}

	@Override
	public List<Tupla<Integer, String>> proyectosFinalizados() {
		List <Tupla<Integer,String>> finalizados = new ArrayList<>();
		
		for(Entry<Integer, Proyecto> p : listaDeProyectos.entrySet()) {
			if(p.getValue().estado.toUpperCase().equals("FINALIZADO")) {
				finalizados.add(new Tupla<>(p.getKey(), p.getValue().titulos[0]));
			}
		}
		
		return finalizados;
	}

	@Override
	public List<Tupla<Integer, String>> proyectosPendientes() {
		List <Tupla<Integer,String>> pendientes = new ArrayList<>();
		for(Entry<Integer, Proyecto> p : listaDeProyectos.entrySet()) {
			if(p.getValue().estado.toUpperCase().equals("PENDIENTE")) {
				pendientes.add(new Tupla<>(p.getKey(), p.getValue().domicilio));
			}
		}
		
		return pendientes;
	}

	@Override
	public List<Tupla<Integer, String>> proyectosActivos() {
		List <Tupla<Integer,String>> activos = new ArrayList<>();
		
		for(Entry<Integer, Proyecto> p : listaDeProyectos.entrySet()) {
			if(p.getValue().estado.toUpperCase().equals("ACTIVO")) {
				activos.add(new Tupla<>(p.getKey(), p.getValue().titulos[0]));
			}
		}
		
		return activos;
	}

	@Override
	public Object[] empleadosNoAsignados() {
		List<Object> empleados = new ArrayList<>();
		for(Empleado e : listaDeEmpleados.values()) {
			if(e.tareas.isEmpty()) {
				empleados.add(e);
			}
		}
		
		return empleados.toArray();
	}

	@Override
	public boolean estaFinalizado(Integer numero) {
		Proyecto p = listaDeProyectos.get(numero);
		
		return p.estado.equals("FINALIZADO");
	}

	@Override
	public int consultarCantidadRetrasosEmpleado(Integer legajo) {
		Empleado e = listaDeEmpleados.get(legajo);
		return e.retrasos;
	}

	@Override
	public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] tareasProyectoNoAsignadas(Integer numero) {
		Proyecto p =  listaDeProyectos.get(numero);
		List<Tarea> tareasNoasignadas = new ArrayList<>();
		for(Tarea t : p.listaDeTareas) {
			if( t.tieneResponsable()) {
				tareasNoasignadas.add(t);
			}
		}
		return tareasNoasignadas.toArray();
	}

	@Override
	public Object[] tareasDeUnProyecto(Integer numero) {
		Proyecto p = listaDeProyectos.get(numero);
		List<Tarea> tareas = p.listaDeTareas;
		List<Tarea> noAsignadas = new ArrayList<>();
		
		for(Tarea t :tareas) {
			if(t.empleado == null) {
				noAsignadas.add(t);
			}
		}
		
		return  noAsignadas.toArray();
	}

	@Override
	public String consultarDomicilioProyecto(Integer numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tieneRetrasos(String legajo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Tupla<Integer, String>> empleados() {
		List<Tupla<Integer,String>> empleados = new ArrayList<>();
		for(Entry<Integer, Empleado> e : listaDeEmpleados.entrySet()) {
			empleados.add(new Tupla<>(e.getKey(), e.getValue().nombre));
		}
		return empleados;
	}

	@Override
	public String consultarProyecto(Integer numero) {
		Proyecto p = listaDeProyectos.get(numero);
		if(p == null) throw new IllegalArgumentException("No existe proyecto con el número " + numero);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Proyecto N° ").append(numero).append("\n");
	    sb.append("Domicilio: ").append(p.domicilio).append("\n");
	    sb.append("Estado: ").append(p.estado).append("\n");
	    sb.append("Fecha inicio: ").append(p.fechaInicio).append("\n");
	    sb.append("Fecha finalización: ").append(p.fechaFinalizacion).append("\n");
	    sb.append("Clientes: ");
	    for (String c : p.clientes) {
	         sb.append(c).append(" ");
	    }
	    sb.append("\nTareas:\n");
	    for (String t : p.titulos) {
	        sb.append("- ").append(t).append("\n");
	    }

	    return sb.toString();
	}

	@Override
	public boolean tieneRestrasos(Integer legajo) {
		Empleado e = listaDeEmpleados.get(legajo);
		return e.tieneRetraso;
	}

}
