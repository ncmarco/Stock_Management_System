package classes;

public class ProdusElectrocasnic extends Produs {
    private ClasaEnergetica clasaEnergetica;

    /**
     * Cosntructor non-parametrizabil
     */
    public ProdusElectrocasnic() {}

    /**
     * Cosntructor parametrizabil pentru a crea produse electrocasnice cu id primit ca parametru
     * @param idProdus
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     * @param clasaEnergetica
     */
    public ProdusElectrocasnic(int idProdus, String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc, ClasaEnergetica clasaEnergetica) {
        super(idProdus, numeProdus, cantitate, pret, categorie, furnizor, loc);
        this.clasaEnergetica = clasaEnergetica;
    }

    /**
     * Cosntructor parametrizabil pentru a crea produse electrocasnice cu id UNIC
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     * @param clasaEnergetica
     */
    public ProdusElectrocasnic(String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc, ClasaEnergetica clasaEnergetica) {
        super(numeProdus, cantitate, pret, categorie, furnizor, loc);
        this.clasaEnergetica = clasaEnergetica;
    }

    /**
     * Metoda getClasaEnergetica
     * @return
     */
    public ClasaEnergetica getClasaEnergetica() {
        return clasaEnergetica;
    }

    /**
     * Metoda setClasaEnergetica
     * @param clasaEnergetica
     */
    public void setClasaEnergetica(ClasaEnergetica clasaEnergetica) {
        this.clasaEnergetica = clasaEnergetica;
    }

    /**
     * Metoda toString
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + "Face parte din clasa energetica: " + this.clasaEnergetica + "\n";
    }
}
