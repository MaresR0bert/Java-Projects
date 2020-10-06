package packeageRobert;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerTCPIP {
    public static void  main(String[] args){
        if(args.length!=1){
            System.err.println("Use server like: <port>" );
            System.exit(-1);
        }
        int port = Integer.valueOf(args[0]);
        try {
            ServerSocket svsck = new ServerSocket(port);
            System.out.println("Waiting for connection @port " + svsck.getLocalPort() + "...");
            while(true){
                new SvThread(svsck.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
