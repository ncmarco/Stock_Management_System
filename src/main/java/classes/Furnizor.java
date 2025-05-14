package classes;

public class Furnizor {
    private int furnizorID;
    private String numeFurnizor;
    private String emailFurnizor;
    private String telefonFurnizor;
    private String adresaFurnizor;

    /**
     * Constructor non-parametrizabil
     */
    public Furnizor() {}

    /**
     * Constructor parametrizabil
     * @param furnizorID
     * @param numeFurnizor
     * @param emailFurnizor
     * @param telefonFurnizor
     * @param adresaFurnizor
     */
    public Furnizor(int furnizorID,String numeFurnizor, String emailFurnizor, String telefonFurnizor, String adresaFurnizor) {
        this.furnizorID = furnizorID;
        this.numeFurnizor = numeFurnizor;
        this.emailFurnizor = emailFurnizor;
        this.telefonFurnizor = telefonFurnizor;
        this.adresaFurnizor = adresaFurnizor;
    }

    /**
     * Constructor parametrizabil fara id
     * @param numeFurnizor
     * @param emailFurnizor
     * @param telefonFurnizor
     * @param adresaFurnizor
     */
    public Furnizor(String numeFurnizor, String emailFurnizor, String telefonFurnizor, String adresaFurnizor) {
        this.numeFurnizor = numeFurnizor;
        this.emailFurnizor = emailFurnizor;
        this.telefonFurnizor = telefonFurnizor;
        this.adresaFurnizor = adresaFurnizor;
    }

    /**
     * Metoda getFurnizorId
     * @return
     */
    public int getFurnizorID() {
        return furnizorID;
    }

    public void setFurnizorID(int furnizorID) {
        this.furnizorID = furnizorID;
    }

    /**
     * Metoda getNumeFurnizor
     * @return
     */
    public String getNumeFurnizor() {
        return numeFurnizor;
    }

    /**
     * Metoda setNumeFurnizor
     * @param numeFurnizor
     */
    public void setNumeFurnizor(String numeFurnizor) {
        this.numeFurnizor = numeFurnizor;
    }

    /**
     * Metoda getEmailFurnizor
     * @return
     */
    public String getEmailFurnizor() {
        return emailFurnizor;
    }

    /**
     * Metoda setEmailFurnizor
     * @param emailFurnizor
     */
    public void setEmailFurnizor(String emailFurnizor) {
        this.emailFurnizor = emailFurnizor;
    }

    /**
     * Metoda getTelefonFurnizor
     * @return
     */
    public String getTelefonFurnizor() {
        return telefonFurnizor;
    }

    /**
     * Metoda setTelefonFurnizor
     * @param telefonFurnizor
     */
    public void setTelefonFurnizor(String telefonFurnizor) {
        this.telefonFurnizor = telefonFurnizor;
    }

    /**
     * Metoda getAdresaFurnizor
     * @return
     */
    public String getAdresaFurnizor() {
        return adresaFurnizor;
    }

    /**
     * Metoda setAdresaFurnizor
     * @param adresaFurnizor
     */
    public void setAdresaFurnizor(String adresaFurnizor) {
        this.adresaFurnizor = adresaFurnizor;
    }

    /**
     * Metoda toString
     * @return
     */
    @Override
    public String toString() {
        return "* Furnizorul: " + this.numeFurnizor
                + "\n* Email: " + this.emailFurnizor
                + "\n* Telefon: " + this.telefonFurnizor
                + "\n* Adresa: " + this.adresaFurnizor;

    }
}
