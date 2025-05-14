package dao;

import classes.Categorie;
import classes.Furnizor;
import classes.Locatie;
import classes.ProdusIgienaCuratare;
import conn.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.ProdusDAO.existaProdus;
import static dao.ProdusDAO.updateProdus;

public class ProdusIgienaCuratareDAO {
    /**
     * Metoda pentru inserarea unui produs de igiena si curatare in bazele de date Produse si ProduseIgienaCuratare
     * @param produs
     * @throws SQLException
     */
    public void insertProdusIgienaCuratare(ProdusIgienaCuratare produs) throws SQLException {
        Integer existingId = existaProdus(
                produs.getNumeProdus(),
                produs.getCategorie().getCategorieId(),
                produs.getFurnizor().getFurnizorID()
        );

        if (existingId != null) {
            updateProdus(existingId, produs.getCantitate());
        } else {
            String sqlGeneral = "INSERT INTO Produse (numeProdus, cantitate, pret, categorieId, furnizorId, raft, coloana, tipProdus) VALUES (?,?,?,?,?,?,?,?)";
            String sqlSpecific = "INSERT INTO ProduseIgienaCuratare (idProdusIgienaCuratare, greutate) VALUES (?,?)";

            try (Connection conn = DataBaseConnection.getConnection()) {
                PreparedStatement pstmtGen = conn.prepareStatement(sqlGeneral, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmtSpec = conn.prepareStatement(sqlSpecific);

                pstmtGen.setString(1, produs.getNumeProdus());
                pstmtGen.setInt(2, produs.getCantitate());
                pstmtGen.setDouble(3, produs.getPret());
                pstmtGen.setInt(4, produs.getCategorie().getCategorieId());
                pstmtGen.setInt(5, produs.getFurnizor().getFurnizorID());
                pstmtGen.setInt(6, produs.getLoc().getRaft());
                pstmtGen.setInt(7, produs.getLoc().getColoana());
                pstmtGen.setString(8, "igiena_curatare");
                pstmtGen.executeUpdate();

                ResultSet rs = pstmtGen.getGeneratedKeys();
                if (rs.next()) {
                    int idProdusGeneral = rs.getInt(1);
                    pstmtSpec.setInt(1, idProdusGeneral);
                    pstmtSpec.setDouble(2, produs.getGreutate());
                    pstmtSpec.executeUpdate();
                }
            }
        }
    }

    /**
     * Metoda pentru actualizarea informatiilor unui produs de igiena si curatare
     * @param produs
     * @throws SQLException
     */
    public void updateProdusIgienaCuratare(ProdusIgienaCuratare produs) throws SQLException {
        String sqlGeneral = "UPDATE Produse SET numeProdus = ?, cantitate = ?, pret = ?, categorieId = ?, furnizorId = ?, raft = ?, coloana = ? WHERE idProdusGeneral = ?";
        String sqlSpecific = "UPDATE ProduseIgienaCuratare SET greutate = ? WHERE idProdusIgienaCuratare = ?";

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

            stmtSpecific.setDouble(1, produs.getGreutate());
            stmtSpecific.setInt(2, produs.getIdProdus());
            stmtSpecific.executeUpdate();
        }
    }

    /**
     * Metoda pentru cautarea produselor de igiena si curatare dupa nume
     * @param numeProdus
     * @return
     * @throws SQLException
     */
    public List<ProdusIgienaCuratare> findProduseIgienaCuratareByName(String numeProdus) throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, 
            pic.greutate
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        JOIN ProduseIgienaCuratare pic ON p.idProdusGeneral = pic.idProdusIgienaCuratare
        WHERE p.tipProdus = 'igiena_curatare' AND p.numeProdus LIKE ?;
        """;

        List<ProdusIgienaCuratare> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + numeProdus + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                produse.add(mapToProdusIgienaCuratare(rs));
            }
        }

        return produse;
    }

    /**
     * Metoda pentru obtinerea tuturor produselor de igiena si curatare din baza de date
     * @return
     * @throws SQLException
     */
    public List<ProdusIgienaCuratare> findAllProduseIgienaCuratare() throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, 
            pic.greutate
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        JOIN ProduseIgienaCuratare pic ON p.idProdusGeneral = pic.idProdusIgienaCuratare
        WHERE p.tipProdus = 'igiena_curatare';
        """;

        List<ProdusIgienaCuratare> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                produse.add(mapToProdusIgienaCuratare(rs));
            }
        }

        return produse;
    }

    /**
     * Metoda de stergere a unui produs de igiena si curatare dupa nume
     * @param numeProdus
     * @param cantitateDeSters
     * @throws SQLException
     */
    public void deleteProdusIgienaCuratareByName(String numeProdus, int cantitateDeSters) throws SQLException {
        String findSql = "SELECT idProdusGeneral, cantitate FROM Produse WHERE numeProdus = ? AND tipProdus = 'igiena_curatare'";
        String updateSql = "UPDATE Produse SET cantitate = cantitate - ? WHERE idProdusGeneral = ?";
        String deleteSql = "DELETE FROM Produse WHERE idProdusGeneral = ?";
        String deleteSpecificSql = "DELETE FROM ProduseIgienaCuratare WHERE idProdusIgienaCuratare = ?";

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
     * Metoda pentru maparea unui ResultSet la un obiect ProdusIgienaCuratare
     * @param rs
     * @return
     * @throws SQLException
     */
    private ProdusIgienaCuratare mapToProdusIgienaCuratare(ResultSet rs) throws SQLException {
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

        double greutate = rs.getDouble("greutate");

        return new ProdusIgienaCuratare(id, nume, cantitate, pret, categorie, furnizor, locatie, greutate);
    }
}
