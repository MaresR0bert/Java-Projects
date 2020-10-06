package packeageRobert;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SvThread extends Thread {
    private Socket client;
    private static int noClients = 0;

    public SvThread(Socket socket){
        super("ServerThread"+ ++noClients);
        this.client = socket;
    }

    public void run(){
        try {
            LibrarieSteam lbs = new LibrarieSteam();
            lbs.createConnection();
            try {
                //lbs.createTable(); // In cazul in care este prima rulare sa se decomenteze aceasta linie.
                lbs.dropTable();
                lbs.createTable();
            }catch (Exception e){
                System.out.println("ATENTIE! IN CAZUL IN ARE ACEASTA ESTE PRIMA RULARE SE VA LASA DOAR LINIA DE CREATE TABLE");
                System.out.println("TABELELE VOR FI ATRIBUITE IN ORDINEA IN CARE CLIENTUL S-A CONECTAT LA SERVER");
                System.out.println("THREAD1 -> JOCURI1\nTHREAD2 -> JOCURI2 etc...");
            }
//            if(lbs.checkIfTableExists()) System.out.println(0);
//            else System.out.println(1);

//            if(!lbs.checkIfTableExists()) lbs.createTable();
//            else if(lbs.checkIfTableExists()) {
//                lbs.dropTable();
//                lbs.createTable();
//            }
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("Connecion accepted w/ client " + client.getRemoteSocketAddress() + ";" + this.getName());
//            String userTableType ="";
//            userTableType=in.readUTF();
//            if(userTableType.equalsIgnoreCase("0")){
//                lbs.createTable();
//            }else if(userTableType.equalsIgnoreCase("1")) {
//                lbs.dropTable();
//                lbs.createTable();
//            }else if(userTableType.equalsIgnoreCase("2")){
//                lbs.dropTable();
//            }else if(userTableType.equalsIgnoreCase("3")){
//
//            }
            BufferedReader commandline = new BufferedReader(new InputStreamReader(System.in));
            String msgClient = null;
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date dateNow = new Date(System.currentTimeMillis());
            FileWriter fw = new FileWriter("ChatLog"+noClients+".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Chat log from: "+dateNow +"\n");
            bw.close();
            fw.close();

            while (true) {
                fw = new FileWriter("ChatLog"+noClients+".txt", true);
                bw = new BufferedWriter(fw);

                msgClient = in.readUTF();
                String[] tokens = msgClient.split(",");
                System.out.println(msgClient + " (" + this.getName() + ")");
                sleep(0);
                String msgSv = "";

                if(tokens[0].equalsIgnoreCase("i")) {
                    Jocuri jocLocal = new Jocuri(tokens[1],Integer.valueOf(tokens[2]),EnumTipJoc.valueOf(tokens[3]));
                    //System.out.println(jocLocal.toString());
                    lbs.insertJoc(jocLocal);
                    msgSv="Game was added "+jocLocal.toString();
                }
                else if(tokens[0].equalsIgnoreCase("u")){
                    lbs.updateJoc(tokens[1],Integer.valueOf(tokens[2]));
                    msgSv="Game was Updated";
                }
                else if(tokens[0].equalsIgnoreCase("d")){
                    lbs.deleteJoc(tokens[1]);
                    msgSv="Game was deleted";
                }
                else if(msgClient.equalsIgnoreCase("s")){
//                    List<Jocuri> listaLocala = new ArrayList<Jocuri>();
                    String[] str = new String[lbs.noOfJocuri()+1];
                    str = lbs.selectJocuri();
//                    for(Jocuri j:listaLocala){
//                        out.writeUTF(j.toTableString());
//                    }
                    for(String s:str){
                        msgSv+=s+"\n";
                    }

                }
                else {
                    //out.writeUTF("Mesajul trimis nu este o comanda! Se asteapta un raspuns din partea serverului");
                    System.out.println("Se asteapta raspuns:");
                    msgSv = commandline.readLine();
                }
                out.writeUTF(msgSv);
                bw.append(msgClient+"\n");
                bw.append(msgSv+"\n");
                bw.close();
                fw.close();
            }
        } catch (EOFException eof){
            System.out.println("Disconnected (" + this.getName()+")");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
