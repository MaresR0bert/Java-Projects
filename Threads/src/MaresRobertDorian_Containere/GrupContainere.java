package MaresRobertDorian_Containere;

public class GrupContainere {
    private EnumTipContainer tipContainer;
    private int nrContainere;

    public GrupContainere(EnumTipContainer tipContainer, int nrContainere) {
        this.tipContainer = tipContainer;
        this.nrContainere = nrContainere;
    }

    public EnumTipContainer getTipContainer() {
        return tipContainer;
    }

    public int getNrContainere() {
        return nrContainere;
    }

    public void setTipContainer(EnumTipContainer tipContainer) {
        this.tipContainer = tipContainer;
    }

    public void setNrContainere(int nrContainere) {
        this.nrContainere = nrContainere;
    }
}
