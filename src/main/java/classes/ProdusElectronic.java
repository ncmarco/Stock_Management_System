package classes;

public class ProdusElectronic extends Produs {
    private double latime;
    private double lungime;
    private double grosime;
    private int greutate;

    /**
     * Constructor non-parametrizabil
     */
    public ProdusElectronic() {}

    /**
     * Constructor pentru a crea produse electronice cu id primit ca parametru
     * @param idProdus
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     * @param latime
     * @param lungime
     * @param grosime
     * @param greutate
     */
    public ProdusElectronic(int idProdus, String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc, double latime, double lungime, double grosime, int greutate) {
        super(idProdus, numeProdus, cantitate, pret, categorie, furnizor, loc);
        this.latime = latime;
        this.lungime = lungime;
        this.grosime = grosime;
        this.greutate = greutate;
    }

    /**
     * Constructor parametrizabil pentru a crea produse electronice cu id UNIC
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     * @param latime
     * @param lungime
     * @param grosime
     * @param greutate
     */
    public ProdusElectronic(String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc, double latime, double lungime, double grosime, int greutate) {
        super(numeProdus, cantitate, pret, categorie, furnizor, loc);
        this.latime = latime;
        this.lungime = lungime;
        this.grosime = grosime;
        this.greutate = greutate;
    }

    /**
     * Metoda getLatime
     * @return
     */
    public double getLatime() {
        return latime;
    }

    /**
     * Metoda setLatime
     * @param latime
     */
    public void setLatime(double latime) {
        this.latime = latime;
    }

    /**
     * Metoda getLungime
     * @return
     */
    public double getLungime() {
        return lungime;
    }

    /**
     * Metoda setLungime
     * @param lungime
     */
    public void setLungime(double lungime) {
        this.lungime = lungime;
    }

    /**
     * Metoda getGrosime
     * @return
     */
    public double getGrosime() {
        return grosime;
    }

    /**
     * Metoda setGrosime
     * @param grosime
     */
    public void setGrosime(double grosime) {
        this.grosime = grosime;
    }

    /**
     * Metoda getGreutate
     * @return
     */
    public int getGreutate() {
        return greutate;
    }

    /**
     * Metoda setGreutate
     * @param greutate
     */
    public void setGreutate(int greutate) {
        this.greutate = greutate;
    }

    /**
     * Metoda toString
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + "Si are latimea: " + this.latime + " cm" + ", lungimea: " + this.lungime + " cm" +", grosimea: " + this.grosime + " cm" + " si greutatea: " + this.greutate + " g" + "\n";
    }

}
