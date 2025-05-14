package dao;

import classes.Categorie;
import classes.Furnizor;
import classes.Locatie;
import classes.ProdusAlimentar;
import conn.DataBaseConnection;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static dao.ProdusDAO.existaProdus;
import static dao.ProdusDAO.updateProdus;

public class ProdusAlimentarDAO {
    /**
     * Metoda pentru inserarea unui produs alimentar in bazele de date produse si produsealimentare
     *
     * @param produsAlimentar
     * @throws SQLException
     */
    public void insertProdusAlimentar(ProdusAlimentar produsAlimentar) throws SQLException {
        Integer existingId = existaProdus(
                produsAlimentar.getNumeProdus(),
                produsAlimentar.getCategorie().getCategorieId(),
                produsAlimentar.getFurnizor().getFurnizorID()
        );

        if (existingId != null) {
            updateProdus(existingId, produsAlimentar.getCantitate());
        } else {
            String sqlGeneral = "INSERT INTO produse (numeProdus, cantitate, pret, categorieId, furnizorId, raft, coloana, tipProdus) VALUES (?,?,?,?,?,?,?,?)";
            String sqlSpecific = "INSERT INTO produsealimentare (idProdusAlimentar, dataExpirarii, greutate) VALUES (?,?,?)";

            try (Connection conn = DataBaseConnection.getConnection()) {
                PreparedStatement pstmtGen = conn.prepareStatement(sqlGeneral, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmtSpecific = conn.prepareStatement(sqlSpecific);

                pstmtGen.setString(1, produsAlimentar.getNumeProdus());
                pstmtGen.setInt(2, produsAlimentar.getCantitate());
                pstmtGen.setDouble(3, produsAlimentar.getPret());
                pstmtGen.setInt(4, produsAlimentar.getCategorie().getCategorieId());
                pstmtGen.setInt(5, produsAlimentar.getFurnizor().getFurnizorID());
                pstmtGen.setInt(6, produsAlimentar.getLoc().getRaft());
                pstmtGen.setInt(7, produsAlimentar.getLoc().getColoana());
                pstmtGen.setString(8, "alimentar");

                pstmtGen.executeUpdate();

                ResultSet rs = pstmtGen.getGeneratedKeys();
                if (rs.next()) {
                    int idProdusGeneral = rs.getInt(1);
                    pstmtSpecific.setInt(1, idProdusGeneral);
                    pstmtSpecific.setDate(2, new java.sql.Date(produsAlimentar.getDataExpirarii().getTime()));
                    pstmtSpecific.setInt(3, produsAlimentar.getGreutate());
                    pstmtSpecific.executeUpdate();
                }
            }
        }
    }

    /**
     * Metoda de actualizare a informatiilor unui produs alimentar
     * @param produs
     * @throws SQLException
     */
    public void updateProdusAlimentar(ProdusAlimentar produs) throws SQLException {
        String sqlGeneral = "UPDATE Produse SET numeProdus = ?, cantitate = ?, pret = ?, categorieId = ?, furnizorId = ?, raft = ?, coloana = ? WHERE idProdusGeneral = ?";
        String sqlSpecific = "UPDATE ProduseAlimentare SET dataExpirarii = ?, greutate = ? WHERE idProdusAlimentar = ?";



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

            stmtSpecific.setDate(1, new java.sql.Date(produs.getDataExpirarii().getTime()));
            stmtSpecific.setInt(2, produs.getGreutate());
            stmtSpecific.setInt(3, produs.getIdProdus());
            stmtSpecific.executeUpdate();
        }
    }


    /**
     * Metoda care cauta produse dupa nume si returneaza lista acestora
     *
     * @param numeProdus
     * @return
     * @throws SQLException
     */
    public List<ProdusAlimentar> findProduseAlimentareByName(String numeProdus) throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, 
            pa.dataExpirarii, pa.greutate
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        JOIN ProduseAlimentare pa ON p.idProdusGeneral = pa.idProdusAlimentar
        WHERE p.tipProdus = 'alimentar' AND p.numeProdus LIKE ?;
    """;

        List<ProdusAlimentar> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + numeProdus + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                produse.add(mapToProdusAlimentar(rs));
            }
        }

        return produse;
    }

    /**
     * Metoda pentru a returna toate produsele alimentare.
     *
     * @return
     * @throws SQLException
     */
    public static List<ProdusAlimentar> findAllProduseAlimentare() throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, 
            pa.dataExpirarii, pa.greutate
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        JOIN ProduseAlimentare pa ON p.idProdusGeneral = pa.idProdusAlimentar
        WHERE p.tipProdus = 'alimentar';
    """;

        List<ProdusAlimentar> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                produse.add(mapToProdusAlimentar(rs));
            }
        }

        return produse;
    }

    /**
     * Metoda de stergere a unui produs dupa nume
     * @param numeProdus
     * @param cantitateDeSters
     * @throws SQLException
     */
    public void deleteProdusAlimentarByName(String numeProdus, int cantitateDeSters) throws SQLException {
        String findSql = "SELECT idProdusGeneral, cantitate FROM Produse WHERE numeProdus = ? AND tipProdus = 'alimentar'";
        String updateSql = "UPDATE Produse SET cantitate = cantitate - ? WHERE idProdusGeneral = ?";
        String deleteSql = "DELETE FROM Produse WHERE idProdusGeneral = ?";
        String deleteSpecificSql = "DELETE FROM ProduseAlimentare WHERE idProdusAlimentar = ?";

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
     * Metoda pentru maparea unui resultSet la un produsAlimentar
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private static ProdusAlimentar mapToProdusAlimentar(ResultSet rs) throws SQLException {
        int id = rs.getInt("idProdusGeneral");
        String nume = rs.getString("numeProdus");
        int cantitate = rs.getInt("cantitate");
        double pret = rs.getDouble("pret");

        int categorieId = rs.getInt("categorieid");
        String numeCategorie = rs.getString("numeCategorie");
        Categorie categorie = new Categorie(categorieId, numeCategorie);

        int furnizorId = rs.getInt("furnizorId");
        String numeFurnizor = rs.getString("numeFurnizor");
        String emailFurnizor = rs.getString("emailFurnizor");
        String telefonFurnizor = rs.getString("telefon");
        String adresaFurnizor = rs.getString("adresa");
        Furnizor furnizor = new Furnizor(furnizorId, numeFurnizor, emailFurnizor, telefonFurnizor, adresaFurnizor);

        int raft = rs.getInt("raft");
        int coloana = rs.getInt("coloana");
        Locatie locatie = new Locatie(raft, coloana);

        Date dataExpirarii = rs.getDate("dataExpirarii");
        int greutate = rs.getInt("greutate");

        return new ProdusAlimentar(id, nume, cantitate, pret, categorie, furnizor, locatie, dataExpirarii, greutate);
    }
}
