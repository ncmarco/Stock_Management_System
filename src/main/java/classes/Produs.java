package classes;

/**
 * @author Nino-Coman Marco
 */
public abstract class Produs {
    protected int idProdus;
    protected String numeProdus;
    protected int cantitate;
    protected double pret;
    protected Categorie categorie;
    protected Furnizor furnizor;
    protected Locatie loc;

    /**
     * Constructor non-parametrizabil
     */
    public Produs() {}

    /**
     * Constructor parametrizabil pentru a crea un produs cu acelasi id
     * @param idProdus
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     */
    public Produs(int idProdus, String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc) {
        this.idProdus = idProdus;
        this.numeProdus = numeProdus;
        this.cantitate = cantitate;
        this.pret = pret;
        this.categorie = categorie;
        this.furnizor = furnizor;
        this.loc = loc;
    }

    /**
     * Constructor parametrizabil, pentru a crea produse cu id UNIC
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     */
    public Produs(String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc) {
        this.numeProdus = numeProdus;
        this.cantitate = cantitate;
        this.pret = pret;
        this.categorie = categorie;
        this.furnizor = furnizor;
        this.loc = loc;
    }

    /**
     * Metoda getIdProdus
     * @return
     */
    public int getIdProdus() {
        return idProdus;
    }

    /**
     * Metoda setIdProdus
     * @param idProdus
     */
    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    /**
     * Metoda getNumeProdus
     * @return
     */
    public String getNumeProdus() {
        return numeProdus;
    }

    /**
     * Metoda setNumeProdus
     * @param numeProdus
     */
    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    /**
     * Metoda getCantitate
     * @return
     */
    public int getCantitate() {
        return cantitate;
    }

    /**
     * Metoda setCantitate
     * @param cantitate
     */
    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    /**
     * Metoda getPret
     * @return
     */
    public double getPret() {
        return pret;
    }

    /**
     * Metoda setPret
     * @param pret
     */
    public void setPret(double pret) {
        this.pret = pret;
    }

    /**
     * Metoda getCategorie
     * @return
     */
    public Categorie getCategorie() {
        return categorie;
    }

    /**
     * Metoda setCategorie
     * @param categorie
     */
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    /**
     * Metoda getFurnizor
     * @return
     */
    public Furnizor getFurnizor() {
        return furnizor;
    }

    /**
     * Metoda setFurnizor
     * @param furnizor
     */
    public void setFurnizor(Furnizor furnizor) {
        this.furnizor = furnizor;
    }

    /**
     * Metoda getLoc
     * @return
     */
    public Locatie getLoc() {
        return loc;
    }

    /**
     * Metoda setLoc
     * @param loc
     */
    public void setLoc(Locatie loc) {
        this.loc = loc;
    }

    public String toString() {
        return "Produsul: " + this.numeProdus + " are cantitatea: " + this.cantitate +
                ", pretul: " + this.pret + " RON" + " si face parte din categoria: "
                + this.categorie.getNumeCategorie() + "\nEste furnizat de: "
                + this.furnizor.getNumeFurnizor() + "\nIl puteti gasi pe: " + this.loc.toString() + "\n";
    }
}
