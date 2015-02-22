/*
 *  * Comentario
 * @author Lucas Brito <lucas080795@hotmail.com>
 */
package socketlis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.ini4j.Ini;
import org.ini4j.Wini;

/**
 *
 * @author lucas
 */
public class SocketLis {

    /**
     * @param args the command line arguments
     */
    
    public static BufferedReader input;
    public static int i= 0;
    public static String CallerIDNum=null;
    public static String CallerIDName=null;
    public static String ramal;
    
   
    
    public static void run(String ramal_e,String ip_e,String port_e) {
        ramal = ramal_e;
        try{
           
            Socket client = new Socket(ip_e,new Integer(port_e).intValue());
            System.out.println("Conectado: "+client.getInetAddress()+":"+client.getPort()+"!");
            PrintStream saida = new PrintStream(client.getOutputStream());
            saida.printf("Action: Login\n"
                    + "Username: admin\n"
                    + "Secret: secret5\n\n");
            
             input = new BufferedReader(new InputStreamReader(client.getInputStream()));
             
            Runnable run = new Runnable(){
                public void run(){
                    while(true){
                        try{
                            String line = input.readLine();
                            
                            String[] read;
                            // Separa elementos
                            read = line.split(":");
                            // Verifica se tem CallerIDNum
                            if(line.indexOf("CallerIDNum")!=-1){
                                CallerIDNum = read[1];
                            }
                            if(line.indexOf("CallerIDName")!=-1){
                                CallerIDName = read[1];
                            }
                            if(line.indexOf("Dialstring")!=-1){
                                if(read[1].trim().equals(ramal)){
                                    Janela jan = new Janela();
                                    jan.setCallerIDName(CallerIDName);
                                    jan.setCallerIDNum(CallerIDNum);
                                    jan.setVisible(true);
                                }
                            }
                        }catch(Exception e){System.out.println(e); System.exit(0);}

                    }
                }
            };
            new Thread(run).start();
            
        }catch(IOException e){
            System.out.println("Erro de conexão!");
        }
    }
    
}
