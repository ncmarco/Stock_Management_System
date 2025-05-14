package dao;

import classes.Furnizor;
import conn.DataBaseConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FurnizorDAO {
    /**
     * Metoda pentru inserarea unui furnizor in baza de date
     * @param furnizor
     * @throws SQLException
     */
    public void insertFurnizor(Furnizor furnizor) throws SQLException {
        String sql = "INSERT INTO Furnizori (numeFurnizor, emailFurnizor, telefon, adresa) VALUES (?,?,?,?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, furnizor.getNumeFurnizor());
            pstmt.setString(2, furnizor.getEmailFurnizor());
            pstmt.setString(3, furnizor.getTelefonFurnizor());
            pstmt.setString(4, furnizor.getAdresaFurnizor());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                furnizor.setFurnizorID(rs.getInt(1));
            }
        }
    }

    /**
     * Metoda pentru cautarea unui furnizor dupa nume
     * @param numeFurnizor
     * @return
     * @throws SQLException
     */
    public Furnizor findFurnizorByName(String numeFurnizor) throws SQLException {
        String sql = "SELECT * FROM Furnizori WHERE numeFurnizor LIKE ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + numeFurnizor + "%");
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return mapToFurnizor(rs);
            }
            else{
                return adaugaFurnizorNou(numeFurnizor);
            }

        }

    }

    /**
     * Metoda pentru adaugarea unui nou furnizor
     * @param numeFurnizorInitial
     * @return
     * @throws SQLException
     */
    private Furnizor adaugaFurnizorNou(String numeFurnizorInitial) throws SQLException {
        JTextField numeField = new JTextField(numeFurnizorInitial);
        JTextField emailField = new JTextField();
        JTextField telefonField = new JTextField();
        JTextField adresaField = new JTextField();

        Object[] message = {
                "Nume furnizor:", numeField,
                "Email:", emailField,
                "Telefon:", telefonField,
                "Adresă:", adresaField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Adaugă un furnizor nou", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nume = numeField.getText().trim();
            String email = emailField.getText().trim();
            String telefon = telefonField.getText().trim();
            String adresa = adresaField.getText().trim();

            if (!nume.isEmpty() && !email.isEmpty() && !telefon.isEmpty() && !adresa.isEmpty()) {
                FurnizorDAO furnizorDAO = new FurnizorDAO();
                Furnizor furnizor = new Furnizor(nume, email, telefon, adresa);
                furnizorDAO.insertFurnizor(furnizor);
                return furnizor; // Returnează furnizorul nou creat
            } else {
                JOptionPane.showMessageDialog(null, "Toate câmpurile sunt obligatorii!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        }
        throw new SQLException("Adăugarea furnizorului a fost anulată de utilizator.");
    }

    /**
     * Metoda pentru obtinerea tuturor furnizorilor din baza de date
     * @return
     * @throws SQLException
     */
    public List<Furnizor> findAllFurnizori() throws SQLException {
        String sql = "SELECT * FROM Furnizori";

        List<Furnizor> furnizori = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                furnizori.add(mapToFurnizor(rs));
            }
        }

        return furnizori;
    }

    /**
     * Metoda pentru stergerea unui furnizor dupa ID
     * @param idFurnizor
     * @throws SQLException
     */
    public void deleteFurnizorById(int idFurnizor) throws SQLException {
        String sql = "DELETE FROM Furnizori WHERE idFurnizor = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idFurnizor);
            pstmt.executeUpdate();
        }
    }

    /**
     * Metoda pentru maparea unui ResultSet la un obiect Furnizor
     * @param rs
     * @return
     * @throws SQLException
     */
    private Furnizor mapToFurnizor(ResultSet rs) throws SQLException {
        int id = rs.getInt("idFurnizor");
        String nume = rs.getString("numeFurnizor");
        String email = rs.getString("emailFurnizor");
        String telefon = rs.getString("telefon");
        String adresa = rs.getString("adresa");
        return new Furnizor(id, nume, email, telefon, adresa);
    }
}
