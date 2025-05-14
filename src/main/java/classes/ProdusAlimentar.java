package classes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Nino-Coman Marco
 */
public class ProdusAlimentar extends Produs {
    private Date dataExpirarii;
    private int greutate;

    /**
     * Constructor non-parametrizabil
     */
    public ProdusAlimentar() {}

    /**
     * Constructor pentru a crea un produs alimentar cu id primit ca parametru
     * @param idProdus
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     * @param dataExpirarii
     * @param greutate
     */
    public ProdusAlimentar(int idProdus, String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc, Date dataExpirarii, int greutate) {
        super(idProdus, numeProdus, cantitate, pret, categorie, furnizor, loc);
        this.dataExpirarii = dataExpirarii;
        this.greutate = greutate;
    }

    /**
     * Constructor parametrizabil pentru a crea produse alimentare cu id UNIC
     * @param numeProdus
     * @param cantitate
     * @param pret
     * @param categorie
     * @param furnizor
     * @param loc
     * @param dataExpirarii
     */
    public ProdusAlimentar(String numeProdus, int cantitate, double pret, Categorie categorie, Furnizor furnizor, Locatie loc, Date dataExpirarii, int greutate) {
        super(numeProdus, cantitate, pret, categorie, furnizor, loc);
        this.dataExpirarii = dataExpirarii;
        this.greutate = greutate;
    }

    /**
     * Metoda getDataExpirarii
     * @return
     */
    public Date getDataExpirarii() {
        return dataExpirarii;
    }

    /**
     * Metoda setDataExpirarii
     * @param dataExpirarii
     */
    public void setDataExpirarii(Date dataExpirarii) {
        this.dataExpirarii = dataExpirarii;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return super.toString() + "Si are greutatea: " + this.greutate + " g" + " si expira la: " + sdf.format(this.dataExpirarii) + "\n";
    }
}
