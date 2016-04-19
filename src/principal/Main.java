package principal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import clases.Persona;

public class Main {
	
	public static void main(String args[]) throws NoSuchFieldException, SecurityException{			
		Persona persona=new Persona(1,"Luis","Espinel");		
		obtenerInfoMetodos(persona);						
		ejecutarMetodoAtributo(persona);		
		ejecutarMetodosGet(persona);
		obtenerCampos(persona);				
	}
	
	public static void obtenerInfoMetodos(Object object){
		/* Creamos un objeto de tipo Class y lo inicializamos
		 	obteniendo la clase del objeto que entra como parametro. 
		 	con el objeto de tipo Class será con el que se obtendra
		 	la información de la clase y de sus metodos. */		 
		Class clase=object.getClass();		
		/*
		 * Obtenemos los metodos de la clase, con el metodo geMethods()
		 * Este nos devuelve un arreglo de tipo Method, la clase Method
		 * Se encuentra en el paquete java.lang.reflect
		 */
		Method metodos[]=clase.getMethods();	
		
		// Recorremos el arreglo 
		for(Method metodo : metodos){
			// Obtenemos el nombre del metodo.
			String nombreParametro=metodo.getName();	
			/* Obtenemos los paramatros del metodo con getParameterTypes()
			 * este devuelve un arreglo de tipo Class.
			 */
			Class parametros[]=metodo.getParameterTypes();
			System.out.println("Metodo : "+nombreParametro);
			if(parametros.length > 0){
				System.out.print("parametros: ");
				// recorremos el arreglo los parametros
				for(Class parametro : parametros)
					System.out.print(parametro.getName()+" ,");
			}else System.out.println("El metodo no tiene parametros.");
			System.out.print("\nTipo de retorno : "+metodo.getReturnType().toString());
			System.out.println("\n_____________________________");
		}				
	}
	
	/*
	 * Este metodo ejecuta los metodos cuyo nombre contiene la palabra get.
	 * por lo general los metodos get se usan para obtener atributos de la clase.	
	*/
	public static void ejecutarMetodosGet(Object object){
		Class clase=object.getClass();
		Method metodos[]=clase.getMethods();
		if(metodos.length > 0){
			for(Method metodo : metodos){
				if(metodo.getName().indexOf("get") != -1){
					try {				
						// Se ejecuta el metodo, pasanado como parametro el mismo object recibido
						// y se convierte a String con el fin de imprimir de una vez.
						System.out.println("Ejecución metodo : "+ metodo.getName()+ " -> "+
						String.valueOf(metodo.invoke(object, null)));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {				
						e.printStackTrace();
					}
				}			
			}
		}else System.out.println("La clase no tiene metodos.");
		
	}
	
	public static void ejecutarMetodoAtributo(Object object){
		String nuevoNombre="Eduardo";
		Method metodos[]=object.getClass().getMethods();
		try {
			for(Method metodo : metodos){
				if(metodo.getName().indexOf("setNombre") != -1){
					metodo.invoke(object, nuevoNombre);
				}
			}			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {			
			e.printStackTrace();
		}
	}
	
	public static void obtenerCampos(Object object){
		Class clase=object.getClass();
		// Guardamos el arreglo de Filds que retorna el metodo getFields();
		Field atributos[]=clase.getFields();
		System.out.println("Lista de atributos de la clase : ");
		//Recorrer el arreglo de atributos
		for(Field campo : atributos)
			System.out.println("Nombre atributo : "+campo.getName()+"\tTipo de dato : "+campo.getType().toString());
	}		
}
