package dao;

import classes.Categorie;
import classes.Furnizor;
import classes.Locatie;
import classes.ProdusElectronic;
import conn.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.ProdusDAO.existaProdus;
import static dao.ProdusDAO.updateProdus;

public class ProdusElectronicDAO {
    /**
     * Metoda pentru inserarea unui produs electronic in bazele de date Produse si ProduseElectronice
     * @param produsElectronic
     * @throws SQLException
     */
    public void insertProdusElectronic(ProdusElectronic produsElectronic) throws SQLException {
        Integer existingId = existaProdus(
                produsElectronic.getNumeProdus(),
                produsElectronic.getCategorie().getCategorieId(),
                produsElectronic.getFurnizor().getFurnizorID()
        );

        if (existingId != null) {
            updateProdus(existingId, produsElectronic.getCantitate());
        } else {

            String sqlGeneral = "INSERT INTO Produse (numeProdus, cantitate, pret, categorieId, furnizorId, raft, coloana, tipProdus) VALUES (?,?,?,?,?,?,?,?)";
            String sqlSpecific = "INSERT INTO ProduseElectronice (idProdusElectronic, latime, lungime, grosime, greutate) VALUES (?,?,?,?,?)";

            try (Connection conn = DataBaseConnection.getConnection()) {
                PreparedStatement pstmtGen = conn.prepareStatement(sqlGeneral, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmtSpec = conn.prepareStatement(sqlSpecific);

                pstmtGen.setString(1, produsElectronic.getNumeProdus());
                pstmtGen.setInt(2, produsElectronic.getCantitate());
                pstmtGen.setDouble(3, produsElectronic.getPret());
                pstmtGen.setInt(4, produsElectronic.getCategorie().getCategorieId());
                pstmtGen.setInt(5, produsElectronic.getFurnizor().getFurnizorID());
                pstmtGen.setInt(6, produsElectronic.getLoc().getRaft());
                pstmtGen.setInt(7, produsElectronic.getLoc().getColoana());
                pstmtGen.setString(8, "electronic");
                pstmtGen.executeUpdate();

                ResultSet rs = pstmtGen.getGeneratedKeys();
                if (rs.next()) {
                    int idProdusGeneral = rs.getInt(1);
                    pstmtSpec.setInt(1, idProdusGeneral);
                    pstmtSpec.setDouble(2, produsElectronic.getLatime());
                    pstmtSpec.setDouble(3, produsElectronic.getLungime());
                    pstmtSpec.setDouble(4, produsElectronic.getGrosime());
                    pstmtSpec.setInt(5, produsElectronic.getGreutate());
                    pstmtSpec.executeUpdate();
                }
            }
        }
    }

    /**
     * Metoda pentru actualizarea informatiilor unui produs electronic
     * @param produs
     * @throws SQLException
     */
    public void updateProdusElectronic(ProdusElectronic produs) throws SQLException {
        String sqlGeneral = "UPDATE Produse SET numeProdus = ?, cantitate = ?, pret = ?, categorieId = ?, furnizorId = ?, raft = ?, coloana = ? WHERE idProdusGeneral = ?";
        String sqlSpecific = "UPDATE ProduseElectronice SET latime = ?, lungime = ?, grosime = ?, greutate = ? WHERE idProdusElectronic = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmtGeneral = conn.prepareStatement(sqlGeneral);
             PreparedStatement stmtSpecific = conn.prepareStatement(sqlSpecific)) {

            stmtGeneral.setString(1, produs.getNumeProdus());
            stmtGeneral.setInt(2, produs.getCantitate());
            stmtGeneral.setDouble(3, produs.getPret());
            stmtGeneral.setInt(4, produs.getCategorie().getCategorieId());
            stmtGeneral.setInt(5, produs.getFurnizor().getFurnizorID());
            stmtGeneral.setInt(6, produs.getLoc().getRaft());
            stmtGeneral.setInt(7, produs.getLoc().getColoana());
            stmtGeneral.setInt(8, produs.getIdProdus());
            stmtGeneral.executeUpdate();

            stmtSpecific.setDouble(1, produs.getLatime());
            stmtSpecific.setDouble(2, produs.getLungime());
            stmtSpecific.setDouble(3, produs.getGrosime());
            stmtSpecific.setInt(4, produs.getGreutate());
            stmtSpecific.setInt(5, produs.getIdProdus());
            stmtSpecific.executeUpdate();
        }
    }

    /**
     * Metoda pentru cautarea produselor electronice dupa nume
     * @param numeProdus
     * @return
     * @throws SQLException
     */
    public List<ProdusElectronic> findProduseElectroniceByName(String numeProdus) throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, 
            pe.latime, pe.lungime, pe.grosime, pe.greutate
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        JOIN ProduseElectronice pe ON p.idProdusGeneral = pe.idProdusElectronic
        WHERE p.tipProdus = 'electronic' AND p.numeProdus LIKE ?;
        """;

        List<ProdusElectronic> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + numeProdus + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                produse.add(mapToProdusElectronic(rs));
            }
        }

        return produse;
    }

    /**
     * Metoda pentru obtinerea tuturor produselor electronice din baza de date
     * @return
     * @throws SQLException
     */
    public List<ProdusElectronic> findAllProduseElectronice() throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, 
            pe.latime, pe.lungime, pe.grosime, pe.greutate
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        JOIN ProduseElectronice pe ON p.idProdusGeneral = pe.idProdusElectronic
        WHERE p.tipProdus = 'electronic';
        """;

        List<ProdusElectronic> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                produse.add(mapToProdusElectronic(rs));
            }
        }

        return produse;
    }

    /**
     * Metoda de stergere a unui produs electronic dupa nume
     * @param numeProdus
     * @param cantitateDeSters
     * @throws SQLException
     */
    public void deleteProdusElectronicByName(String numeProdus, int cantitateDeSters) throws SQLException {
        String findSql = "SELECT idProdusGeneral, cantitate FROM Produse WHERE numeProdus = ? AND tipProdus = 'electronic'";
        String updateSql = "UPDATE Produse SET cantitate = cantitate - ? WHERE idProdusGeneral = ?";
        String deleteSql = "DELETE FROM Produse WHERE idProdusGeneral = ?";
        String deleteSpecificSql = "DELETE FROM ProduseElectronice WHERE idProdusElectronic = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement findStmt = conn.prepareStatement(findSql)) {

            findStmt.setString(1, numeProdus);
            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int idProdus = rs.getInt("idProdusGeneral");
                int cantitateExistenta = rs.getInt("cantitate");

                if (cantitateDeSters < cantitateExistenta) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, cantitateDeSters);
                        updateStmt.setInt(2, idProdus);
                        updateStmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement deleteGeneralStmt = conn.prepareStatement(deleteSql);
                         PreparedStatement deleteSpecificStmt = conn.prepareStatement(deleteSpecificSql)) {

                        deleteSpecificStmt.setInt(1, idProdus);
                        deleteSpecificStmt.executeUpdate();

                        deleteGeneralStmt.setInt(1, idProdus);
                        deleteGeneralStmt.executeUpdate();
                    }
                }
            } else {
                throw new SQLException("Produsul cu numele '" + numeProdus + "' nu există.");
            }
        }
    }

    /**
     * Metoda pentru maparea unui ResultSet la un obiect ProdusElectronic
     * @param rs
     * @return
     * @throws SQLException
     */
    private ProdusElectronic mapToProdusElectronic(ResultSet rs) throws SQLException {
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

        double latime = rs.getDouble("latime");
        double lungime = rs.getDouble("lungime");
        double grosime = rs.getDouble("grosime");
        int greutate = rs.getInt("greutate");

        return new ProdusElectronic(id, nume, cantitate, pret, categorie, furnizor, locatie, latime, lungime, grosime, greutate);
    }
}
