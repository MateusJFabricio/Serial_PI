package controller;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoopbackTest {
 
    private SerialPort serialPort;
    private OutputStream outStream;
    private InputStream inStream;

    // Abrindo a porta serial 
    public void open(String portName) throws IOException {
        try {            
            CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
            serialPort = (SerialPort) portId.open("serial", 5000);
            setSerialPortParameters(); 
            outStream = serialPort.getOutputStream();
            inStream = serialPort.getInputStream();
        } catch (NoSuchPortException | PortInUseException e) {
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            serialPort.close();
            throw e;
        }
    }
 
    public InputStream getSerialInputStream() {
        return inStream;
    }
 
    public OutputStream getSerialOutputStream() {
        return outStream;
    }
 
    private void closeSafely(Closeable resource) {
        try {
            resource.close();
       } catch (IOException ex) {}                
    }
 
    public void close() {
        if (serialPort != null) {
            closeSafely(outStream);
           closeSafely(inStream);
            serialPort.close();
        }
    }
 
    private void setSerialPortParameters() throws IOException {
        int baudRate = 1200; 
        try {           
            serialPort.setSerialPortParams(
                    baudRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE); 
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
   }

    public static void main(String[] args) {
       
        String testString = "O rato roeu a roupa do rei de Roma";
        
        final int TIMEOUT_VALUE = 1000;

        LoopbackTest loopbackTest = new LoopbackTest();
        try {
        // A porta Serial é referenciada por /dev/ttyUSB0
            loopbackTest.open("/dev/ttyUSB0");
 
        // Obtem os streams para leitura/escrita
            InputStream inStream = loopbackTest.getSerialInputStream();
            OutputStream outStream = loopbackTest.getSerialOutputStream();
            
        // Envia caracter por caracter do testString
            for (int i = 0; i < testString.length(); i++) {
                
                outStream.write(testString.charAt(i));
                
               long startTime = System.currentTimeMillis();
               long elapsedTime;
 
            // Aguarda um tempo e testa se ha dado disponível na porta (inStream.available)
                do {
                    elapsedTime = System.currentTimeMillis() - startTime;
                } while ((elapsedTime < TIMEOUT_VALUE) && (inStream.available() == 0));
 
            // Verifica se a leitura foi feita antes do tempo expirar
                if (elapsedTime < TIMEOUT_VALUE) {
                   int readChar = inStream.read();
                    System.err.println("Received " + readChar + " Sent:" + testString.charAt(i));
                }
                else {
                    System.err.println("Sem dados na porta Serial");
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
 
       System.out.println("Fim\n");
       loopbackTest.close();
   }    
}