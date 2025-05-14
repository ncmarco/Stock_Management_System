package classes;

public class ProdusIgienaCuratare extends Produs {
    private double greutate;

    /**
     * Cosntructor non-parametrizabil
     */
    public ProdusIgienaCuratare() {}

    /**
     * Constructor parametrizabil pentru a crea produse de igiena si curatare cu id primit ca parametru
     * @param idProdus
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     * @param greutate
     */
    public ProdusIgienaCuratare(int idProdus, String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc, double greutate) {
        super(idProdus, numeProdus, cantitate, pret, categorie, furnizor, loc);
        this.greutate = greutate;
    }

    /**
     * Constructor parametrizabil pentru a crea produse de igiena si curatare cu id UNIC
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     * @param greutate
     */
    public ProdusIgienaCuratare(String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc, double greutate) {
        super(numeProdus, cantitate, pret, categorie, furnizor, loc);
        this.greutate = greutate;
    }

    /**
     * Metoda getGreutate
     * @return
     */
    public double getGreutate() {
        return greutate;
    }

    /**
     * Metoda setGreutate
     * @param greutate
     */
    public void setGreutate(double greutate) {
        this.greutate = greutate;
    }

    /**
     * Metoda toString
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + "Si are greutatea: " + this.greutate + " g" + "\n";
    }
}
