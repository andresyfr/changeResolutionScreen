package com.andresyfr.changeresolutions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Resolucion {

	private String ejecutarComando(String cmd) {
		try {
//			String cmd = "halt"; //Comando de apagado en linux
			Process salida = Runtime.getRuntime().exec(cmd); 
			return procesarSalida(salida);
		} catch (IOException ioe) {
			System.out.println (ioe);
		}
		return null;
	}
	
	private String procesarSalida(Process process) throws IOException {
		InputStream inputstream = process.getInputStream();
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));
		 
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + System.lineSeparator());
        }
        return sb.toString();
	}
	
	public String enviarResolucion(int width, int height) throws IOException {
		String ctv="cvt "+width+" "+height;
        String linea = ejecutarComando(ctv);
        System.out.println(castModeLine(linea));
        System.out.println(castResolucion(linea));
        
        String newMode="xrandr --newmode "+castModeLine(linea);
        String lineaNewMode = ejecutarComando(newMode);
        System.out.println(lineaNewMode);
        String addMode="xrandr --addmode VGA-1 "+castResolucion(linea);
        String lineaAddMode = ejecutarComando(addMode);
        System.out.println(lineaAddMode);
        String asigMode = "xrandr --output VGA-1 --mode "+castResolucion(linea);
        String lineaAsigMode=ejecutarComando(asigMode);
        System.out.println(lineaAsigMode);
        return null; 
	}
	
	private String castModeLine(String linea) {
		return linea.split("Modeline ")[1];
	}
	
	private String castResolucion(String linea) {
		return linea.split("Modeline ")[1].split("   ")[0];
	}
	
	public static void main(String[] args) {
		Resolucion resolucion = new Resolucion();
		try {
			resolucion.enviarResolucion(1366, 753);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
