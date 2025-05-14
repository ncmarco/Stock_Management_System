package dao;

import classes.Categorie;
import conn.DataBaseConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {
    /**
     * Metoda pentru inserarea unei categorii in baza de date
     * @param categorie
     * @throws SQLException
     */
    public void insertCategorie(Categorie categorie) throws SQLException {
        String sql = "INSERT INTO Categorii (numeCategorie) VALUES (?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, categorie.getNumeCategorie());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                categorie.setCategorieId(rs.getInt(1));
            }
        }
    }

    /**
     * Metoda pentru inserarea unei categorii dupa nume
     * @param numeCategorie
     * @throws SQLException
     */
    public void insertCategorieByName(String numeCategorie) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM Categorii WHERE numeCategorie = ?";
        String insertSql = "INSERT INTO Categorii (numeCategorie) VALUES (?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            checkStmt.setString(1, numeCategorie);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Categoria există deja în baza de date.");
            }

            insertStmt.setString(1, numeCategorie);
            insertStmt.executeUpdate();
        }
    }

    /**
     * Metoda pentru cautarea unei categorii dupa nume
     * @param numeCategorie
     * @return
     * @throws SQLException
     */
    public Categorie findCategorieByName(String numeCategorie) throws SQLException {
        String sql = "SELECT * FROM Categorii WHERE numeCategorie LIKE ?";


        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + numeCategorie + "%");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idCategorie");
                String nume = rs.getString("numeCategorie");
                return new Categorie(id, nume);
            }
            else{
                return adaugaCategorieNoua(numeCategorie);
            }

        }
    }

    /**
     * Metoda pentru adaugarea unei noi categorii
     * @param numeCategorieInitial
     * @return
     * @throws SQLException
     */
    private Categorie adaugaCategorieNoua(String numeCategorieInitial) throws SQLException {
        JTextField numeField = new JTextField(numeCategorieInitial);

        Object[] message = {
                "Introduceți numele categoriei:", numeField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Adaugă o categorie nouă", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String numeCategorie = numeField.getText().trim();

            if (!numeCategorie.isEmpty()) {
                CategorieDAO categorieDAO = new CategorieDAO();
                Categorie categorie = new Categorie(numeCategorie);
                categorieDAO.insertCategorie(categorie);
                return categorie;
            } else {
                JOptionPane.showMessageDialog(null, "Numele categoriei nu poate fi gol!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        }
        throw new SQLException("Adăugarea categoriei a fost anulată de utilizator.");
    }

    /**
     * Metoda pentru obtinerea tuturor categoriilor din baza de date
     * @return
     * @throws SQLException
     */
    public List<Categorie> findAllCategorii() throws SQLException {
        String sql = "SELECT * FROM Categorii";

        List<Categorie> categorii = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("idCategorie");
                String nume = rs.getString("numeCategorie");
                categorii.add(new Categorie(id, nume));
            }
        }

        return categorii;
    }

    /**
     * Metoda pentru stergerea unei categorii dupa ID
     * @param idCategorie
     * @throws SQLException
     */
    public void deleteCategorieById(int idCategorie) throws SQLException {
        String sql = "DELETE FROM Categorii WHERE idCategorie = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCategorie);
            pstmt.executeUpdate();
        }
    }

    /**
     * Metoda pentru maparea unui ResultSet la un obiect Categorie
     * @param rs
     * @return
     * @throws SQLException
     */
    private Categorie mapToCategorie(ResultSet rs) throws SQLException {
        int id = rs.getInt("idCategorie");
        String nume = rs.getString("numeCategorie");
        return new Categorie(id, nume);
    }
}
