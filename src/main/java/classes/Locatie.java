package classes;

/**
 * @author Nino-Coman Marco
 */
public class Locatie {
    private int raft;
    private int coloana;

    /**
     * Constructor non-parametrizabil
     */
    public Locatie() {}

    /**
     * Constructor parametrizabil
     * @param raft
     * @param coloana
     */
    public Locatie(int raft, int coloana) {
        this.raft = raft;
        this.coloana = coloana;
    }

    /**
     * Metoda getRaft
     * @return
     */
    public int getRaft() {
        return raft;
    }

    /**
     * Metoda setRaft
     * @param raft
     */
    public void setRaft(int raft) {
        this.raft = raft;
    }

    /**
     * Metoda getColoana
     * @return
     */
    public int getColoana() {
        return coloana;
    }

    /**
     * Metoda setColoana
     * @param coloana
     */
    public void setColoana(int coloana) {
        this.coloana = coloana;
    }

    /**
     * Metoda de afisare a locatiei
     * @return
     */
    @Override
    public String toString() {
        return "raftul: " + this.raft + ", coloana: " + this.coloana;
    }
}
