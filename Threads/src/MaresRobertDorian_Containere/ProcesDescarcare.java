package MaresRobertDorian_Containere;

public class ProcesDescarcare extends Thread{
    private portContainer pt;
    private Macara m;

    public ProcesDescarcare(portContainer pt, Macara m){
        this.pt=pt;
        this.m=m;
    }

    public synchronized void seDescarca(GrupContainere gpt, Macara m){
        if(this.m.isInManipulare()==true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.m.intraInManipulare();
        System.out.println("Macaraua " + m.getEtichetaMacara() + " a descarcat 1 container de tip: " + m.getTipContainer() + " din portcontainer: " + pt.getEticheta());
        this.pt.DescarcaContainer(gpt, this.m);
        System.out.println("---------------------->Au mai ramas: " + gpt.getNrContainere() + " containere de tip:"+gpt.getTipContainer());
        this.m.ieseDinnManipulare();
        notifyAll();
    }

    public void run(){
        for(int i=0;i<4;i++) {
            if(this.pt.getGrupContainere(i).getTipContainer()==this.m.getTipContainer()) {
                while (this.pt.getGrupContainere(i).getNrContainere() > 0) {
                    try {
                        sleep(this.m.getMilisecunde());
                        seDescarca(this.pt.getGrupContainere(i),this.m);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
