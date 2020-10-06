package MaresRobertDorian_Containere;

public class Macara implements Cloneable {
    private String etichetaMacara;
    private EnumTipContainer tipContainer;
    private int timpManipulare;
    private boolean inManipulare;

    public Macara(String EtichetaMacara,EnumTipContainer tipContainer, int milisecunde) {
        this.etichetaMacara=EtichetaMacara;
        this.tipContainer = tipContainer;
        this.timpManipulare = milisecunde;
        this.inManipulare=false;
    }

    public Macara() {
        this.tipContainer=null;
        this.timpManipulare=0;
        this.inManipulare=false;
    }

    public EnumTipContainer getTipContainer() {
        return tipContainer;
    }

    public int getMilisecunde() {
        return timpManipulare;
    }

    public void setTipContainer(EnumTipContainer tipContainer) {
        this.tipContainer = tipContainer;
    }

    public void setMilisecunde(int milisecunde) {
        this.timpManipulare = milisecunde;
    }

    public String getEtichetaMacara() {
        return etichetaMacara;
    }

    public void setEtichetaMacara(String etichetaMacara) {
        this.etichetaMacara = etichetaMacara;
    }

    public boolean isInManipulare(){
        return this.inManipulare;
    }

    public void intraInManipulare(){
        this.inManipulare=true;
    }

    public void ieseDinnManipulare(){
        this.inManipulare=false;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public String toString(){
        return this.etichetaMacara +","+this.tipContainer.toString()+","+ this.timpManipulare;
    }
}
