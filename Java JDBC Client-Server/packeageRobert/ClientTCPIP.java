package packeageRobert;
import java.io.*;
import java.net.Socket;

public class ClientTCPIP {
    public static void main(String[] args){
        if(args.length!=2){
            System.err.println("Use client like <server_name> <port>");
            System.exit(-1);
        }
        String svname = args[0];
        int port = Integer.valueOf(args[1]);
        try {
            System.out.println("Connecting to : "+svname+":"+port);
            Socket client = new Socket(svname,port);
            OutputStream outsv = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outsv);
            InputStream insv = client.getInputStream();
            DataInputStream in = new DataInputStream(insv);
            BufferedReader commandline = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Type exit to exit client");
            System.out.println("Comenzile se vor scrie astfel:");
            System.out.println("La inceput i,u,d,s pentru insert, update, delete, select ");
            System.out.println("Dupa care se va pune virgula si se va face refeinta la urmatoarele comenzi");
            System.out.println("Pentru insert : i,<denumire joc>,<dimensiune>,<tip joc> (TIP JOC FACE PARTE DIN: SHOOTER,STRATEGIE,PLATFORMER,SURVIVAL");
            System.out.println("Pentru update : u,<numele jocului de updatat>,<noua dimensiune>");
            System.out.println("Pentru delete : d,<id-ul jocului de sters>");
            System.out.println("Pentru select : s");
            System.out.println("ATENTIE! DACA MESAJUL TRIMIS NU ESTE O COMANDA PRECUM CELE AMINTITE MAI SUS, SE VA ASTEPTA UN RASPUNS DIN PARTEA SERVERULUI");
            while (true){
                String msj = commandline.readLine();
                if(msj.equalsIgnoreCase("exit")){
                    in.close();
                    out.close();
                    client.close();
                    System.exit(0);
                }else{
                    out.writeUTF(msj);
                }
                System.out.println(in.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
