package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.sun.org.apache.xerces.internal.xs.StringList;

import controller.Serial;
import gnu.io.CommPortIdentifier;

public class Model {
	private Serial serial = new Serial();
	
	public ArrayList<String> ListarPortas()
	{
		ArrayList<String> p = new ArrayList<String>();
		Enumeration portas = serial.PortasDisponiveis();
		
		 while (portas.hasMoreElements()) {
	            CommPortIdentifier portId = (CommPortIdentifier) portas.nextElement();
	            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	                p.add(portId.getName());
	            }
	        }
		 return p;
		
	}

	public void AbrirPorta(String selectedItem) {
		try {
			serial.openPort(selectedItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public String LerPorta()
	{
		String resposta = "";
		try {
			resposta = serial.ReadSerial();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resposta;
	}

	public void closePort() {
		serial.close();
	}
}
