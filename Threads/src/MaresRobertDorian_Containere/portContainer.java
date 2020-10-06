package MaresRobertDorian_Containere;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class portContainer implements Numarabil, Cloneable, Descarcare {
    private String eticheta;
    private GrupContainere[] grupContainere;
    private boolean inDescarcare;

    public portContainer(String eticheta, GrupContainere[] grupContainere) {
        this.eticheta = eticheta;
        this.grupContainere = new GrupContainere[4];
        for(int i=0;i<4;i++){
            this.grupContainere[i]=new GrupContainere(grupContainere[i].getTipContainer(),grupContainere[i].getNrContainere());
        }
    }

    public portContainer(String eticheta, EnumTipContainer e[], int a[]){
        this.eticheta=eticheta;
        this.grupContainere=new GrupContainere[4];
        for(int i=0;i<4;i++){
            this.grupContainere[i]=new GrupContainere(e[i],a[i]);
        }
        this.inDescarcare=false;
    }

    public portContainer() {
    }

    public String getEticheta() {
        return eticheta;
    }

    public void setEticheta(String eticheta) {
        this.eticheta = eticheta;
    }

    public void setNrGrupContainere(int a[]){
        for(int i=0;i<4;i++){
            this.grupContainere[i].setNrContainere(a[i]);
        }
    }

    public void setTupuriGrupContainere(EnumTipContainer e[]){
        for(int i=0;i<4;i++){
            this.grupContainere[i].setTipContainer(e[i]);
        }
    }

    public GrupContainere getGrupContainere(int index) {
        return this.grupContainere[index];
    }

    public void intraInDescarcare(){
        this.inDescarcare=true;
    }

    public void oprestDescarcare(){
        this.inDescarcare=false;
    }

    public boolean isInDescarcare(){
        return this.inDescarcare;
    }

    public Object clone() throws CloneNotSupportedException{
        portContainer clona = (portContainer) super.clone();
        clona = new portContainer(this.eticheta,this.grupContainere);
        return clona;
    }

    @Override
    public int getCapacitate() {
        int sum=0;
        int k=25;
        sum+=(this.grupContainere[0].getNrContainere())*10;
        for(int i=1;i<4;i++){
            sum+=(this.grupContainere[i].getNrContainere())*k;
            k=k*2;
        }
        return sum;
    }

    @Override
    public String toString() {
        String s=new String();
        for(int i=0;i<4;i++){
            s+=this.grupContainere[i].getTipContainer().toString() + ": " + this.grupContainere[i].getNrContainere() + "; ";
        }
        return this.eticheta + ";" + s;
    }

    public String toStringCsvFormat(){
        String localstring=new String();
        for(int i=0;i<4;i++)
            localstring +="," + this.grupContainere[i].getTipContainer().toString() + "," + this.grupContainere[i].getNrContainere();
        return this.eticheta + localstring;
    }

    public synchronized int DescarcaContainer(GrupContainere gpt, Macara m) {
        int x = 0;
        x = gpt.getNrContainere();
        gpt.setNrContainere(--x);
        return gpt.getNrContainere();
    }
}
