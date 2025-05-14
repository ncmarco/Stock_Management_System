package dao;

import classes.*;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProdusDAOTest {
    private ProdusDAO produsDAO = new ProdusDAO();
    private CategorieDAO categorieDAO = new CategorieDAO();
    private FurnizorDAO furnizorDAO = new FurnizorDAO();
    private ProdusAlimentarDAO produsAlimentarDAO = new ProdusAlimentarDAO();

    private Categorie categorie = categorieDAO.findCategorieByName("Fructe");
    private Furnizor furnizor = furnizorDAO.findFurnizorByName("Furnizor Banane");
    private Locatie locatie = new Locatie(2, 5);

    public ProdusDAOTest() throws SQLException {
    }

    @Test
    void testExistaProdus() throws Exception {
        ProdusAlimentar produs = new ProdusAlimentar(
                "Produs Test Unitar", 10, 15.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 300
        );

        produsAlimentarDAO.insertProdusAlimentar(produs);
        Integer idProdus = ProdusDAO.existaProdus(produs.getNumeProdus(), categorie.getCategorieId(), furnizor.getFurnizorID());
        assertNotNull(idProdus, "Produsul ar trebui sa existe in baza de date");

        produsAlimentarDAO.deleteProdusAlimentarByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testUpdateProdus() throws Exception {
        ProdusAlimentar produs = new ProdusAlimentar(
                "Produs Test Unitar", 10, 15.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 300
        );

        produsAlimentarDAO.insertProdusAlimentar(produs);

        Integer idProdus = ProdusDAO.existaProdus(produs.getNumeProdus(), categorie.getCategorieId(), furnizor.getFurnizorID());
        assertNotNull(idProdus, "Produsul ar trebui sa existe in baza de date");

        ProdusDAO.updateProdus(idProdus, 5);

        List<Produs> produse = produsDAO.findProduseByName(produs.getNumeProdus());
        assertEquals(15, produse.get(0).getCantitate(), "Cantitatea ar trebui sa fie actualizata");

        produsAlimentarDAO.deleteProdusAlimentarByName(produs.getNumeProdus(), produse.get(0).getCantitate());
    }

    @Test
    void testFindProduseByName() throws Exception {
        ProdusAlimentar produs = new ProdusAlimentar(
                "Produs Test Unitar", 10, 15.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 300
        );

        produsAlimentarDAO.insertProdusAlimentar(produs);

        List<Produs> produse = produsDAO.findProduseByName(produs.getNumeProdus());
        assertFalse(produse.isEmpty(), "Produsul ar trebui sa fie gasit");
        assertEquals("Produs Test Unitar", produse.get(0).getNumeProdus(), "Numele produsului ar trebui sa se potriveasca");

        produsAlimentarDAO.deleteProdusAlimentarByName(produs.getNumeProdus(), produse.get(0).getCantitate());
    }

    @Test
    void testFindProduseByCategorie() throws Exception {
        ProdusAlimentar produs = new ProdusAlimentar(
                "Produs Test Unitar", 10, 15.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 300
        );

        produsAlimentarDAO.insertProdusAlimentar(produs);

        List<Produs> produse = produsDAO.findProduseByCategorie(categorie.getNumeCategorie());
        assertFalse(produse.isEmpty(), "Ar trebui sa existe produse in categoria specificata");
        assertEquals("Produs Test Unitar", produse.get(produse.size() - 1).getNumeProdus(), "Numele produsului ar trebui sa se potriveasca");

        produsAlimentarDAO.deleteProdusAlimentarByName(produs.getNumeProdus(), produse.get(0).getCantitate());
    }

    @Test
    void testFindAllProduse() throws Exception {
        ProdusAlimentar produs1 = new ProdusAlimentar(
                "Produs Test Unitar", 10, 15.5, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 300
        );
        ProdusAlimentar produs2 = new ProdusAlimentar(
                "Produs Test Unitar 2", 15, 20.0, categorie, furnizor, locatie, Date.valueOf("2025-12-31"), 500
        );

        produsAlimentarDAO.insertProdusAlimentar(produs1);
        produsAlimentarDAO.insertProdusAlimentar(produs2);

        List<Produs> produse = produsDAO.findAllProduse();
        assertTrue(produse.size() >= 2, "Ar trebui sa existe cel putin doua produse in baza de date");

        produsAlimentarDAO.deleteProdusAlimentarByName(produs1.getNumeProdus(), produs1.getCantitate());
        produsAlimentarDAO.deleteProdusAlimentarByName(produs2.getNumeProdus(), produs2.getCantitate());
    }
}
