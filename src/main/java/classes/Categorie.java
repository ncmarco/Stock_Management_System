package classes;

public class Categorie {
    private int categorieId;
    private String numeCategorie;

    /**
     * Constructor parametrizabil
     * @param numeCategorie
     */
    public Categorie(int categorieId, String numeCategorie) {
        this.categorieId = categorieId;
        this.numeCategorie = numeCategorie;
    }

    /**
     * Constructor parametrizabil doar cu un parametru
     * @param numeCategorie
     */
    public Categorie(String numeCategorie) {
        this.numeCategorie = numeCategorie;
    }

    /**
     * Metoda getCategorieId
     * @return
     */
    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }
    /**
     * Metoda getNumeCategorie
     * @return numele categoriei
     */
    public String getNumeCategorie() {
        return numeCategorie;
    }

    /**
     * Metoda setNumeCategorie
     * @param numeCategorie
     */
    public void setNumeCategorie(String numeCategorie) {
        this.numeCategorie = numeCategorie;
    }

    /**
     * Suprascrierea metodei toString pentru afisare
     * @return numele categoriei
     */
    @Override
    public String toString() {
        return this.numeCategorie;
    }
}
