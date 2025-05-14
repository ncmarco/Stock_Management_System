package dao;

import classes.*;
import conn.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdusDAO {

    /**
     * Metoda pentru a verifica daca exista un produs in baza de date
     * @param numeProdus
     * @param categorieId
     * @param furnizorId
     * @return
     * @throws SQLException
     */
    public static Integer existaProdus(String numeProdus, int categorieId, int furnizorId) throws SQLException {
        String sql = "SELECT idProdusGeneral FROM Produse WHERE numeProdus = ? AND categorieId = ? AND furnizorId = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeProdus);
            pstmt.setInt(2, categorieId);
            pstmt.setInt(3, furnizorId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idProdusGeneral");
            }
        }
        return null;
    }

    /**
     * Metoda pentru actualizarea cantitatii unui produs
     * @param idProdus
     * @param cantitate
     * @throws SQLException
     */
    public static void updateProdus(int idProdus, int cantitate) throws SQLException {
        String sql = "UPDATE Produse SET cantitate = cantitate + ? WHERE idProdusGeneral = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cantitate);
            pstmt.setInt(2, idProdus);
            pstmt.executeUpdate();
        }
    }

    /**
     * Metoda pentru a cauta produse dupa nume
     * @param numeProdus
     * @return
     * @throws SQLException
     */
    public List<Produs> findProduseByName(String numeProdus) throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, p.tipProdus,
            pa.dataExpirarii, pa.greutate AS greutateAlimentar, 
            pe.latime, pe.lungime, pe.grosime, pe.greutate AS greutateElectronic,
            el.clasaEnergetica,
            pic.greutate AS greutateIgiena
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        LEFT JOIN ProduseAlimentare pa ON p.idProdusGeneral = pa.idProdusAlimentar
        LEFT JOIN ProduseElectronice pe ON p.idProdusGeneral = pe.idProdusElectronic
        LEFT JOIN ProduseElectrocasnice el ON p.idProdusGeneral = el.idProdusElectrocasnic
        LEFT JOIN ProduseIgienaCuratare pic ON p.idProdusGeneral = pic.idProdusIgienaCuratare
        WHERE p.numeProdus LIKE ?;
    """;

        List<Produs> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + numeProdus + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String tipProdus = rs.getString("tipProdus");
                produse.add(mapToProdus(rs, tipProdus));
            }
        }

        return produse;
    }

    /**
     * Metoda pentru a cauta produse dupa numele categoriei
     * @param numeCategorie
     * @return
     * @throws SQLException
     */
    public List<Produs> findProduseByCategorie(String numeCategorie) throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, p.tipProdus,
            pa.dataExpirarii, pa.greutate AS greutateAlimentar, 
            pe.latime, pe.lungime, pe.grosime, pe.greutate AS greutateElectronic,
            el.clasaEnergetica,
            pic.greutate AS greutateIgiena
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        LEFT JOIN ProduseAlimentare pa ON p.idProdusGeneral = pa.idProdusAlimentar
        LEFT JOIN ProduseElectronice pe ON p.idProdusGeneral = pe.idProdusElectronic
        LEFT JOIN ProduseElectrocasnice el ON p.idProdusGeneral = el.idProdusElectrocasnic
        LEFT JOIN ProduseIgienaCuratare pic ON p.idProdusGeneral = pic.idProdusIgienaCuratare
        WHERE c.numeCategorie LIKE ?;
    """;

        List<Produs> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + numeCategorie + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String tipProdus = rs.getString("tipProdus");
                produse.add(mapToProdus(rs, tipProdus));
            }
        }

        return produse;
    }

    /**
     * Metoda pentru maparea unui produs in functie de tip
     * @param rs
     * @param tipProdus
     * @return
     * @throws SQLException
     */
    private Produs mapToProdus(ResultSet rs, String tipProdus) throws SQLException {
        int id = rs.getInt("idProdusGeneral");
        String nume = rs.getString("numeProdus");
        int cantitate = rs.getInt("cantitate");
        double pret = rs.getDouble("pret");

        int categorieId = rs.getInt("categorieId");
        String numeCategorie = rs.getString("numeCategorie");
        Categorie categorie = new Categorie(categorieId, numeCategorie);

        int furnizorId = rs.getInt("furnizorId");
        String numeFurnizor = rs.getString("numeFurnizor");
        String emailFurnizor = rs.getString("emailFurnizor");
        String telefon = rs.getString("telefon");
        String adresa = rs.getString("adresa");
        Furnizor furnizor = new Furnizor(furnizorId, numeFurnizor, emailFurnizor, telefon, adresa);

        int raft = rs.getInt("raft");
        int coloana = rs.getInt("coloana");
        Locatie locatie = new Locatie(raft, coloana);

        switch (tipProdus) {
            case "alimentar":
                Date dataExpirarii = rs.getDate("dataExpirarii");
                int greutateAlimentar = rs.getInt("greutateAlimentar");
                return new ProdusAlimentar(id, nume, cantitate, pret, categorie, furnizor, locatie, dataExpirarii, greutateAlimentar);

            case "electronic":
                double latime = rs.getDouble("latime");
                double lungime = rs.getDouble("lungime");
                double grosime = rs.getDouble("grosime");
                int greutateElectronic = rs.getInt("greutateElectronic");
                return new ProdusElectronic(id, nume, cantitate, pret, categorie, furnizor, locatie, latime, lungime, grosime, greutateElectronic);

            case "electrocasnic":
                String clasaEnergetica = rs.getString("clasaEnergetica");
                return new ProdusElectrocasnic(id, nume, cantitate, pret, categorie, furnizor, locatie, ClasaEnergetica.valueOf(clasaEnergetica));

            case "igiena_curatare":
                double greutate = rs.getDouble("greutateIgiena");
                return new ProdusIgienaCuratare(id, nume, cantitate, pret, categorie, furnizor, locatie, greutate);

            default:
                throw new IllegalArgumentException("Tip produs necunoscut: " + tipProdus);
        }
    }

    /**
     * Metoda generala pentru a gasi toate produsele
     * @return
     * @throws SQLException
     */
    public List<Produs> findAllProduse() throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, p.tipProdus,
            pa.dataExpirarii, pa.greutate AS greutateAlimentar, 
            pe.latime, pe.lungime, pe.grosime, pe.greutate AS greutateElectronic,
            el.clasaEnergetica,
            pic.greutate AS greutateIgiena
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        LEFT JOIN ProduseAlimentare pa ON p.idProdusGeneral = pa.idProdusAlimentar
        LEFT JOIN ProduseElectronice pe ON p.idProdusGeneral = pe.idProdusElectronic
        LEFT JOIN ProduseElectrocasnice el ON p.idProdusGeneral = el.idProdusElectrocasnic
        LEFT JOIN ProduseIgienaCuratare pic ON p.idProdusGeneral = pic.idProdusIgienaCuratare;
    """;

        List<Produs> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tipProdus = rs.getString("tipProdus");
                produse.add(mapToProdus(rs, tipProdus));
            }
        }

        return produse;
    }

    /**
     * Metoda pentru a gasi tipul unui produs
     * @param idProdus
     * @return
     * @throws SQLException
     */
    public String getTipProdusById(int idProdus) throws SQLException {
        String sql = "SELECT tipProdus FROM Produse WHERE idProdusGeneral = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProdus);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tipProdus");
                }
            }
        }
        throw new SQLException("Tipul produsului nu a fost găsit pentru acest id.");
    }

    /**
     * Metoda pentru actualizarea informatiilor unui produs
     * @param produs
     * @param tipProdus
     * @throws SQLException
     */
    public void actualizeazaProdus(Produs produs, String tipProdus) throws SQLException {
        switch (tipProdus) {
            case "alimentar": {
                if (produs.getCantitate() > 0) {
                    ProdusAlimentarDAO produsAlimentarDAO = new ProdusAlimentarDAO();
                    produsAlimentarDAO.updateProdusAlimentar((ProdusAlimentar) produs);
                    break;
                }
                else {
                    ProdusAlimentarDAO produsAlimentarDAO = new ProdusAlimentarDAO();
                    produsAlimentarDAO.deleteProdusAlimentarByName(produs.getNumeProdus(), 1000000);
                    break;
                }
            }
            case "electronic": {
                if (produs.getCantitate() > 0) {
                    ProdusElectronicDAO produsElectronicDAO = new ProdusElectronicDAO();
                    produsElectronicDAO.updateProdusElectronic((ProdusElectronic) produs);
                    break;
                }
                else {
                    ProdusElectronicDAO produsElectronicDAO = new ProdusElectronicDAO();
                    produsElectronicDAO.deleteProdusElectronicByName(produs.getNumeProdus(), 1000000);
                    break;
                }
            }
            case "electrocasnic": {
                if (produs.getCantitate() > 0) {
                    ProdusElectrocasnicDAO produsElectrocasnicDAO = new ProdusElectrocasnicDAO();
                    produsElectrocasnicDAO.updateProdusElectrocasnic((ProdusElectrocasnic) produs);
                    break;
                }
                else {
                    ProdusElectrocasnicDAO produsElectrocasnicDAO = new ProdusElectrocasnicDAO();
                    produsElectrocasnicDAO.deleteProdusElectrocasnicByName(produs.getNumeProdus(), 1000000);
                    break;
                }
            }
            case "igiena_curatare": {
                if (produs.getCantitate() > 0) {
                    ProdusIgienaCuratareDAO produsIgienaCuratareDAO = new ProdusIgienaCuratareDAO();
                    produsIgienaCuratareDAO.updateProdusIgienaCuratare((ProdusIgienaCuratare) produs);
                    break;
                }
                else {
                    ProdusIgienaCuratareDAO produsIgienaCuratareDAO = new ProdusIgienaCuratareDAO();
                    produsIgienaCuratareDAO.deleteProdusIgienaCuratareByName(produs.getNumeProdus(), 1000000);
                    break;
                }
            }
            default:
                throw new IllegalArgumentException("Tip produs necunoscut: " + tipProdus);
        }
    }

}

