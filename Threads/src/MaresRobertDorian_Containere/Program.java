package MaresRobertDorian_Containere;

import javax.swing.*;
import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    public static void main(String args[]){
        portContainer[] flota = new portContainer[4];
        int[][] v = new int[][]{{20,15,10,5},{17,13,8,2},{21,9,4,2},{14,6,3,1}};
        EnumTipContainer[] enumvekt = new EnumTipContainer[]{EnumTipContainer.Mic_10mc,EnumTipContainer.Mediu_25mc,EnumTipContainer.Mare_50mc,EnumTipContainer.Jumbo_100mc};
        for(int i=0;i<4;i++) {
            flota[i] = new portContainer("PortCont" + (i + 1), enumvekt, v[i]);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for(int i=0;i<4;i++){
            System.out.println(flota[i].toString());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        portContainer portClonat = new portContainer();
        try {
            portClonat =(portContainer) flota[0].clone();
            System.out.println("Clona: "+portClonat.toString());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        try{
            FileWriter outfisier = new FileWriter("PortContainere.csv", false);
            BufferedWriter bf = new BufferedWriter(outfisier);
            for(portContainer i : flota){
                bf.write(i.toStringCsvFormat());
                bf.newLine();
            }
            bf.close();
            outfisier.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayDeque<portContainer> FlotaRescrisa = new ArrayDeque<portContainer>();
        try{
            FileReader readfisier = new FileReader("PortContainere.csv");
            BufferedReader br = new BufferedReader(readfisier);
            Scanner scanfile = new Scanner(br);
            while(scanfile.hasNext()){
                String linie = scanfile.nextLine();
                Scanner lineScanner = new Scanner(linie);
                lineScanner.useDelimiter(",");
                String etichetaLocala = lineScanner.next();
                EnumTipContainer[] enumvectorlocal = new EnumTipContainer[4];
                int[] vectorLocal = new int[4];
                for(int i=0;i<4;i++) {
                    enumvectorlocal[i]=EnumTipContainer.valueOf(lineScanner.next());
                    vectorLocal[i]=lineScanner.nextInt();
                }
                portContainer local = new portContainer(etichetaLocala,enumvectorlocal,vectorLocal);
                FlotaRescrisa.add(local);
                br.close();
                readfisier.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(FileSystemNotFoundException e){
            e.printStackTrace();
        }

        portContainer iter=null;
        while(!FlotaRescrisa.isEmpty()){
            iter = FlotaRescrisa.pollFirst();
            System.out.println(iter.toStringCsvFormat());
            System.out.println(iter.getCapacitate());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Macara[] macarale = new Macara[4];
        for(int i=0;i<4;i++){
            macarale[i]=new Macara("Mac"+(i+1),enumvekt[i],(i+1)*150);
        }

        for(int i=0;i<4;i++){
            System.out.println(macarale[i].toString());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Thread[] p1 = new ProcesDescarcare[4];
        for(int i=0;i<4;i++){
            p1[i]=new ProcesDescarcare(flota[0],macarale[i]);
            p1[i].start();
        }
    }
}
