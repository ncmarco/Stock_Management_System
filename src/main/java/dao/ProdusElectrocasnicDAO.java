package dao;

import classes.*;
import conn.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.ProdusDAO.existaProdus;
import static dao.ProdusDAO.updateProdus;


public class ProdusElectrocasnicDAO {
    /**
     * Metoda pentru inserarea unui produs electrocasnic in bazele de date Produse si ProduseElectrocasnice
     * @param produsElectrocasnic
     * @throws SQLException
     */
    public void insertProdusElectrocasnic(ProdusElectrocasnic produsElectrocasnic) throws SQLException {
        Integer existingId = existaProdus(
                produsElectrocasnic.getNumeProdus(),
                produsElectrocasnic.getCategorie().getCategorieId(),
                produsElectrocasnic.getFurnizor().getFurnizorID()
        );

        if (existingId != null) {
            updateProdus(existingId, produsElectrocasnic.getCantitate());
        } else {
            String sqlGeneral = "INSERT INTO Produse (numeProdus, cantitate, pret, categorieId, furnizorId, raft, coloana, tipProdus) VALUES (?,?,?,?,?,?,?,?)";
            String sqlSpecific = "INSERT INTO ProduseElectrocasnice (idProdusElectrocasnic, clasaEnergetica) VALUES (?,?)";

            try (Connection conn = DataBaseConnection.getConnection()) {
                PreparedStatement pstmtGen = conn.prepareStatement(sqlGeneral, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmtSpec = conn.prepareStatement(sqlSpecific);

                pstmtGen.setString(1, produsElectrocasnic.getNumeProdus());
                pstmtGen.setInt(2, produsElectrocasnic.getCantitate());
                pstmtGen.setDouble(3, produsElectrocasnic.getPret());
                pstmtGen.setInt(4, produsElectrocasnic.getCategorie().getCategorieId());
                pstmtGen.setInt(5, produsElectrocasnic.getFurnizor().getFurnizorID());
                pstmtGen.setInt(6, produsElectrocasnic.getLoc().getRaft());
                pstmtGen.setInt(7, produsElectrocasnic.getLoc().getColoana());
                pstmtGen.setString(8, "electrocasnic");
                pstmtGen.executeUpdate();

                ResultSet rs = pstmtGen.getGeneratedKeys();
                if (rs.next()) {
                    int idProdusGeneral = rs.getInt(1);
                    pstmtSpec.setInt(1, idProdusGeneral);
                    pstmtSpec.setString(2, String.valueOf(produsElectrocasnic.getClasaEnergetica()));
                    pstmtSpec.executeUpdate();
                }
            }
        }
    }

    /**
     * Metoda care actualizeaza informatiile unui produs electrocasnic
     * @param produs
     * @throws SQLException
     */
    public void updateProdusElectrocasnic(ProdusElectrocasnic produs) throws SQLException {
        String sqlGeneral = "UPDATE Produse SET numeProdus = ?, cantitate = ?, pret = ?, categorieId = ?, furnizorId = ?, raft = ?, coloana = ? WHERE idProdusGeneral = ?";
        String sqlSpecific = "UPDATE ProduseElectrocasnice SET clasaEnergetica = ? WHERE idProdusElectrocasnic = ?";

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

            stmtSpecific.setString(1, produs.getClasaEnergetica().toString());
            stmtSpecific.setInt(2, produs.getIdProdus());
            stmtSpecific.executeUpdate();
        }
    }

    /**
     * Metoda pentru cautarea produselor electrocasnice dupa nume
     * @param numeProdus
     * @return
     * @throws SQLException
     */
    public List<ProdusElectrocasnic> findProduseElectrocasniceByName(String numeProdus) throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, 
            pe.clasaEnergetica
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        JOIN ProduseElectrocasnice pe ON p.idProdusGeneral = pe.idProdusElectrocasnic
        WHERE p.tipProdus = 'electrocasnic' AND p.numeProdus LIKE ?;
        """;

        List<ProdusElectrocasnic> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + numeProdus + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                produse.add(mapToProdusElectrocasnic(rs));
            }
        }

        return produse;
    }

    /**
     * Metoda pentru obtinerea tuturor produselor electrocasnice din baza de date
     * @return
     * @throws SQLException
     */
    public List<ProdusElectrocasnic> findAllProduseElectrocasnice() throws SQLException {
        String sql = """
        SELECT 
            p.idProdusGeneral, p.numeProdus, p.cantitate, p.pret, 
            p.categorieId, c.numeCategorie, 
            p.furnizorId, f.numeFurnizor, f.emailFurnizor, f.telefon, f.adresa, 
            p.raft, p.coloana, 
            pe.clasaEnergetica
        FROM Produse p
        JOIN Categorii c ON p.categorieId = c.idCategorie
        JOIN Furnizori f ON p.furnizorId = f.idFurnizor
        JOIN ProduseElectrocasnice pe ON p.idProdusGeneral = pe.idProdusElectrocasnic
        WHERE p.tipProdus = 'electrocasnic';
        """;

        List<ProdusElectrocasnic> produse = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                produse.add(mapToProdusElectrocasnic(rs));
            }
        }

        return produse;
    }

    /**
     * Metoda pentru stergerea unui produs electrocasnic dupa nume
     * @param numeProdus
     * @param cantitateDeSters
     * @throws SQLException
     */
    public void deleteProdusElectrocasnicByName(String numeProdus, int cantitateDeSters) throws SQLException {
        String findSql = "SELECT idProdusGeneral, cantitate FROM Produse WHERE numeProdus = ? AND tipProdus = 'electrocasnic'";
        String updateSql = "UPDATE Produse SET cantitate = cantitate - ? WHERE idProdusGeneral = ?";
        String deleteSql = "DELETE FROM Produse WHERE idProdusGeneral = ?";
        String deleteSpecificSql = "DELETE FROM ProduseElectrocasnice WHERE idProdusElectrocasnic = ?";

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
     * Metoda pentru maparea unui ResultSet la un obiect ProdusElectrocasnic
     * @param rs
     * @return
     * @throws SQLException
     */
    private ProdusElectrocasnic mapToProdusElectrocasnic(ResultSet rs) throws SQLException {
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

        ClasaEnergetica clasaEnergetica = ClasaEnergetica.valueOf(rs.getString("clasaEnergetica"));

        return new ProdusElectrocasnic(id, nume, cantitate, pret, categorie, furnizor, locatie, clasaEnergetica);
    }
}
