package packeageRobert;

import java.io.Serializable;

public class Jocuri implements Serializable {
    private int idJoc;
    private String denumire;
    private int dimensiune;
    private EnumTipJoc tipJoc;
    private static int idCounter=0;

    ///region Constructori
    public Jocuri() {
        this.idJoc = ++idCounter;
        this.denumire="";
        this.dimensiune=0;
        this.tipJoc=EnumTipJoc.Nedefinit;
    }

    public Jocuri(int x){
        this.idJoc=0;
        this.denumire="";
        this.dimensiune=0;
        this.tipJoc=EnumTipJoc.Nedefinit;
    }

    public Jocuri(String denumire, int dimensiune, EnumTipJoc tipJoc) {
        this.idJoc = ++idCounter;
        this.denumire = denumire;
        this.dimensiune = dimensiune;
        this.tipJoc = tipJoc;
    }
    ///endregion

    ///region GET/SET
    public int getIdJoc() {
        return idJoc;
    }

    public String getDenumire() {
        return denumire;
    }

    public float getDimensiune() {
        return dimensiune;
    }

    public EnumTipJoc getTipJoc() {
        return tipJoc;
    }

    public void setIdJoc(int idJoc) {
        this.idJoc = idJoc;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setDimensiune(int dimensiune) {
        this.dimensiune = dimensiune;
    }

    public void setTipJoc(EnumTipJoc tipJoc) {
        this.tipJoc = tipJoc;
    }
    ///endregion

    @Override
    public String toString() {
        return this.idJoc+","+this.denumire+","+this.dimensiune+","+this.tipJoc.toString();
    }

    public String toTableString(){
        return this.idJoc+"\t\t\t"+this.denumire+"\t\t\t"+this.dimensiune+"\t\t\t"+this.tipJoc.toString();
    }
}
