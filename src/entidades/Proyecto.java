package entidades;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Proyecto {
	 public String titulos[];
     public String descripciones[];
     public double duracion[];
     public String clientes[];
     public String fechaInicio;
     public String fechaFinalizacion;
     public String domicilio;
     public String estado;
     public boolean finalizado;
     public List <Tarea>  listaDeTareas;
     public Map <Integer , Tarea> listaDeTareasAsignadas;
     public Map <Integer , Empleado> listaDeEmpleados;



     public Proyecto(String[] titulos,String[] descripciones,double[] duracion,String domicilio,String[] clientes, String fechaInicio, String fechaFinalizacion){
         this.titulos = titulos;
         this.descripciones = descripciones;
         this.duracion = duracion;
         this.domicilio = domicilio;
         this.clientes = clientes;
         this.fechaInicio = fechaInicio;
         this.fechaFinalizacion = fechaFinalizacion;
         this.estado = "ACTIVO";
         this.listaDeEmpleados = new HashMap<>();
         this.listaDeTareas = this.crearListaDeTareas(titulos,descripciones, duracion);
         this.finalizado = this.estado.equals("FINALIZADO");

     }
     public void finalizarProyecto () {
    	 this.estado = "FINALIZADO";
    	 for(Tarea t : listaDeTareas) {
    		 t.finalizar();
    	 }
     }
     public void dejarProyectoPendiente () {
    	 this.estado = "PENDIENTE";
     }
     public void activarProyecto () {
    	 this.estado = "ACTIVO";
  
     }
     
     
     
     public List <Tarea> crearListaDeTareas (String[] titulos,String[]descripciones,double[] duracion) {
    	 List <Tarea> listaDeTareas = new ArrayList<>();
    	 for(int i = 0; i < titulos.length; i++) {
    		 Tarea t =new Tarea(titulos[i],descripciones[i],duracion[i]);
    		 listaDeTareas.add(t);
    	 }
		return listaDeTareas;
     }
     
     public void crearListaDeEmpleadosAsignados(Empleado e){
 
    	 this.listaDeEmpleados.put(e.legajo, e); 
     }
     
     public void asignarTareasAPersonal(int legajo, Tarea tarea){
    	 listaDeTareasAsignadas.put(legajo, tarea);
     }	
     
     public Tarea buscarTarea (String titulo) throws Exception{
    	 Tarea tarea = null;
    	 for(Tarea t : listaDeTareas) {
    		 if(t.titulo.equals(titulo)) {
    			 tarea = t;
    			 break;
    		 }
    	 }
    	 
    	 if (tarea == null ) throw new Exception ("Tarea no existe");
    	 return tarea;
     }
}
