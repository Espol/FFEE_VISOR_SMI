package com.csti.ce.util;

 
import com.csti.ce.monitor.domain.Parametro;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List; 

public abstract class util {

    private String getIds(List<Integer> ids) {
        String resultado = "'0'";
        for (Integer integer : ids) {
            resultado += ",'" + integer + "'";
        }
        return resultado;
    }

    public static String findMD5(String arg) {

        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
//            throw new JatifException("Cannot find digest algorithm");
        }
        byte[] defaultBytes = arg.getBytes();
        algorithm.reset();
        algorithm.update(defaultBytes);
        byte messageDigest[] = algorithm.digest();
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xff & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public Date pasarStringAFecha(String fecha) {
        Date date = null;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-mm-dd");
        try {
            date = formatoDelTexto.parse(fecha);
        } catch (Exception e) {
        }
        return date;
    } 
    
    public static Parametro getParametro(List<Parametro> list, String campo){
        for (Parametro parametro : list) {
            if(campo.equals(parametro.getCampo())){
                return parametro;
            }
        }
        return null;
    }

    public static BigDecimal getPorcentageClave(String strTotal, String strParcial) {
        double totalPorcentaje = Double.parseDouble(strTotal);
        double parcialPorcentaje = Double.parseDouble(strParcial);
        double valorActual = 0d;

        BigDecimal porcentajeActual = BigDecimal.ZERO;
        if (totalPorcentaje > 0) {

            try {
                BigDecimal total = BigDecimal.valueOf(totalPorcentaje);
                BigDecimal parcial = BigDecimal.valueOf(parcialPorcentaje);
                BigDecimal parcialXcien = parcial.multiply(new BigDecimal(100));
                porcentajeActual = parcialXcien.divide(total, 2, RoundingMode.HALF_UP);
            } catch (Exception e) {
                valorActual = (parcialPorcentaje * 100) / totalPorcentaje;
                valorActual = Math.rint(valorActual * 100) / 100;
                porcentajeActual = BigDecimal.valueOf(valorActual);
            }

            AplicacionUtil.DosDecimales(porcentajeActual);

        }
        return porcentajeActual;
    }

    public static String obtenerDias(String fechaCaducidad) {
        System.out.println("fecha: " + fechaCaducidad);
        Date dateCaducidad ;
        Date dateActual;
        String response = "";
        
        dateCaducidad = deStringADate(fechaCaducidad);
        dateActual = deStringADate(getFechaActual());

        int anioCaducidad = dateCaducidad.getYear();
        int anioActual = dateActual.getYear();

        int mesCaducidad = dateCaducidad.getMonth();
        int mesActual = dateActual.getMonth();

        int diaCaducidad = dateCaducidad.getDate();
        int diaActual = dateActual.getDate();

        if (anioCaducidad > anioActual) {
            if (mesCaducidad > mesActual) {
                response += "" + (anioCaducidad - anioActual) + " a�o, ";
            } else {
                response += "" + ((12 - mesActual) + mesCaducidad) + " meses, ";
            }
        } else if(mesCaducidad > mesActual && (mesCaducidad - mesActual) > 1){
            if (diaCaducidad > diaActual) {
                response += (mesCaducidad - mesActual) + " meses y " + (diaCaducidad - diaActual) + " d�as ";
            } else {
                response += (mesCaducidad - mesActual - 1) + " meses y " +((diasDelMes(mesActual, anioActual) - diaActual) + diaCaducidad) + " d�as ";
            }
        }else if (mesCaducidad > mesActual) {
            if (diaCaducidad > diaActual) {
                response += (mesCaducidad - mesActual) + " meses y " + (diaCaducidad - diaActual) + " d�as ";
            } else {
                response += ((diasDelMes(mesActual, anioActual) - diaActual) + diaCaducidad) + " d�as ";
            }
        } else if (diaCaducidad > diaActual) {
            response += (diaCaducidad - diaActual) + " d�as";
        }
        return response;
    }

    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(ahora);
    }

    public static int diasDelMes(int mes, int anio) {
        int dias = 0;
        switch (mes) {
            case 1:  // Enero
            case 3:  // Marzo
            case 5:  // Mayo
            case 7:  // Julio
            case 8:  // Agosto
            case 10:  // Octubre
            case 12: // Diciembre
                dias = 31;
                break;
            case 4:  // Abril
            case 6:  // Junio
            case 9:  // Septiembre
            case 11: // Noviembre
                dias = 30;
                break;
            case 2:  // Febrero
                if (((anio % 100 == 0) && (anio % 400 == 0))
                        || ((anio % 100 != 0) && (anio % 4 == 0))) {
                    dias = 29;  // A�o Bisiesto
                } else {
                    dias = 28;
                }
                break;
        }
        return dias;
    }

    public static Date deStringADate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha = fecha;
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(strFecha);
            System.out.println(fechaDate.toString());
            return fechaDate;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return fechaDate;
        }
    }

}
