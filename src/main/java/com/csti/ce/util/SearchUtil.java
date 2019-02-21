package com.csti.ce.util;


public class SearchUtil {
	public String cadenaDeBusqueda(String...list) {

	    String cadena = "";
	    for (String item : list) {
	    	cadena += item;
	    }
	    return cadena;

	}

	public String getSearchFechaAndEstado(String fecha, String fechaInicio,String fechaFinal, String estado) {
		String Cadena="";
		String cadenaEstado="";
		estado=(estado!=null?estado:"");
		if(estado.length()>0){
			cadenaEstado="p.estado='"+estado+"'";
		}
		if(fecha!="" && fecha!=null){
			Cadena=Cadena + "and p.fecha='"+fecha+"' ";
		}
		else{
			if((fechaInicio!=""&& fechaInicio!=null) && (fechaFinal!="" && fechaFinal!=null)){
				Cadena=Cadena + " and p.fecha between '"+fechaInicio+"' and '"+fechaFinal+"'" ;
			}
		}
		if(estado.length()>0){
			Cadena=(Cadena!="and("?Cadena+" and "+cadenaEstado:cadenaEstado);
		}
		return Cadena;
	}
	
}
