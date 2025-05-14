package dao;

import classes.Categorie;
import classes.Furnizor;
import classes.Locatie;
import classes.ProdusAlimentar;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProdusAlimentarDAOTest {
    CategorieDAO categorieDAO = new CategorieDAO();
    FurnizorDAO furnizorDAO = new FurnizorDAO();

    private ProdusAlimentarDAO produsAlimentarDAO = new ProdusAlimentarDAO();
    private Categorie categorie = categorieDAO.findCategorieByName("Fructe");
    private Furnizor furnizor = furnizorDAO.findFurnizorByName("Furnizor Banane");
    private Locatie locatie = new Locatie(10, 1);

    public ProdusAlimentarDAOTest() throws SQLException {
    }


    @Test
    void testInsertProdusAlimentar() throws Exception {
        ProdusAlimentar produs = new ProdusAlimentar(
                "Produs Test Unitar", 10, 5.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 500
        );

        produsAlimentarDAO.insertProdusAlimentar(produs);
        List<ProdusAlimentar> produse = produsAlimentarDAO.findProduseAlimentareByName("Produs Test Unitar");
        assertFalse(produse.isEmpty(), "Produsul ar trebui sa fie adaugat");
        produsAlimentarDAO.deleteProdusAlimentarByName(produs.getNumeProdus(), 10);
    }

    @Test
    void testUpdateProdusAlimentar() throws Exception {
        ProdusAlimentar produs1 = new ProdusAlimentar(
                "Produs Test Unitar", 10, 5.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 500
        );

        produsAlimentarDAO.insertProdusAlimentar(produs1);
        List<ProdusAlimentar> produse1 = produsAlimentarDAO.findProduseAlimentareByName("Produs Test Unitar");
        ProdusAlimentar produs = produse1.get(0);

        produs.setCantitate(20);
        produs.setPret(6.0);

        produsAlimentarDAO.updateProdusAlimentar(produs);

        List<ProdusAlimentar> produse = produsAlimentarDAO.findProduseAlimentareByName("Produs Test Unitar");
        assertEquals(20, produse.get(0).getCantitate(), "Cantitatea ar trebui sa fie actualizata");
        assertEquals(6.0, produse.get(0).getPret(), "Pretul ar trebui sa fie actualizat");

        produsAlimentarDAO.deleteProdusAlimentarByName("Produs Test Unitar", 20);
    }

    @Test
    void testFindProduseAlimentareByName() throws Exception {
        ProdusAlimentar produs = new ProdusAlimentar(
                "Produs Test Unitar", 10, 5.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 500
        );

        produsAlimentarDAO.insertProdusAlimentar(produs);
        List<ProdusAlimentar> produse = produsAlimentarDAO.findProduseAlimentareByName("Produs Test Unitar");

        assertFalse(produse.isEmpty(), "Produsul ar trebui sa fie gasit");
        assertEquals("Produs Test Unitar", produse.get(0).getNumeProdus(), "Numele produsului ar trebui sa se potriveasca");

        produsAlimentarDAO.deleteProdusAlimentarByName("Produs Test Unitar", 10);
    }

    @Test
    void testFindAllProduseAlimentare() throws Exception {
        ProdusAlimentar produs1 = new ProdusAlimentar(
                "Produs Test Unitar", 10, 5.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 500
        );
        ProdusAlimentar produs2 = new ProdusAlimentar(
                "Produs Test Unitar 2", 15, 7.0, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 1000
        );

        produsAlimentarDAO.insertProdusAlimentar(produs1);
        produsAlimentarDAO.insertProdusAlimentar(produs2);

        List<ProdusAlimentar> produse = produsAlimentarDAO.findAllProduseAlimentare();
        assertTrue(produse.size() >= 2, "Ar trebui sa existe cel putin doua produse alimentare");

        produsAlimentarDAO.deleteProdusAlimentarByName("Produs Test Unitar", 10);
        produsAlimentarDAO.deleteProdusAlimentarByName("Produs Test Unitar 2", 15);
    }

    @Test
    void testDeleteProdusAlimentarByName() throws Exception {
        ProdusAlimentar produs = new ProdusAlimentar(
                "Produs Test Unitar", 10, 5.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 500
        );

        produsAlimentarDAO.insertProdusAlimentar(produs);

        produsAlimentarDAO.deleteProdusAlimentarByName("Produs Test Unitar", 10);

        List<ProdusAlimentar> produse = produsAlimentarDAO.findProduseAlimentareByName("Produs Test Unitar");
        assertTrue(produse.isEmpty(), "Produsul ar trebui sa fie sters");

    }



}
