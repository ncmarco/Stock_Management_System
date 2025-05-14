package dao;

import classes.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProdusIgienaCuratareDAOTest {
    private ProdusIgienaCuratareDAO produsIgienaCuratareDAO = new ProdusIgienaCuratareDAO();
    private CategorieDAO categorieDAO = new CategorieDAO();
    private FurnizorDAO furnizorDAO = new FurnizorDAO();

    private Categorie categorie = categorieDAO.findCategorieByName("Fructe");
    private Furnizor furnizor = furnizorDAO.findFurnizorByName("FurnizorExemplu");
    private Locatie locatie = new Locatie(2, 5);

    public ProdusIgienaCuratareDAOTest() throws SQLException {
    }

    @Test
    void testInsertProdusIgienaCuratare() throws Exception {
        ProdusIgienaCuratare produs = new ProdusIgienaCuratare(
                "Produs Igiena Test", 10, 19.99, categorie, furnizor, locatie, 2.5
        );

        produsIgienaCuratareDAO.insertProdusIgienaCuratare(produs);
        List<ProdusIgienaCuratare> produse = produsIgienaCuratareDAO.findProduseIgienaCuratareByName(produs.getNumeProdus());
        assertFalse(produse.isEmpty(), "Produsul ar trebui sa fie inserat in baza de date");

        produsIgienaCuratareDAO.deleteProdusIgienaCuratareByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testUpdateProdusIgienaCuratare() throws Exception {
        ProdusIgienaCuratare produs = new ProdusIgienaCuratare(
                "Produs Igiena Test", 10, 19.99, categorie, furnizor, locatie, 2.5
        );

        produsIgienaCuratareDAO.insertProdusIgienaCuratare(produs);

        List<ProdusIgienaCuratare> produse = produsIgienaCuratareDAO.findProduseIgienaCuratareByName(produs.getNumeProdus());
        ProdusIgienaCuratare produsInBaza = produse.get(0);

        produsInBaza.setPret(24.99);
        produsInBaza.setGreutate(3.0);
        produsIgienaCuratareDAO.updateProdusIgienaCuratare(produsInBaza);

        List<ProdusIgienaCuratare> produseActualizate = produsIgienaCuratareDAO.findProduseIgienaCuratareByName(produs.getNumeProdus());
        assertEquals(24.99, produseActualizate.get(0).getPret(), "Pretul ar trebui sa fie actualizat");
        assertEquals(3.0, produseActualizate.get(0).getGreutate(), "Greutatea ar trebui sa fie actualizata");

        produsIgienaCuratareDAO.deleteProdusIgienaCuratareByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testFindProduseIgienaCuratareByName() throws Exception {
        ProdusIgienaCuratare produs = new ProdusIgienaCuratare(
                "Produs Igiena Test", 10, 19.99, categorie, furnizor, locatie, 2.5
        );

        produsIgienaCuratareDAO.insertProdusIgienaCuratare(produs);

        List<ProdusIgienaCuratare> produse = produsIgienaCuratareDAO.findProduseIgienaCuratareByName(produs.getNumeProdus());
        assertFalse(produse.isEmpty(), "Produsul ar trebui sa fie gasit");
        assertEquals("Produs Igiena Test", produse.get(0).getNumeProdus(), "Numele produsului ar trebui sa se potriveasca");

        produsIgienaCuratareDAO.deleteProdusIgienaCuratareByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testFindAllProduseIgienaCuratare() throws Exception {
        ProdusIgienaCuratare produs1 = new ProdusIgienaCuratare(
                "Produs Igiena Test", 10, 19.99, categorie, furnizor, locatie, 2.5
        );
        ProdusIgienaCuratare produs2 = new ProdusIgienaCuratare(
                "Produs Igiena Test 2", 15, 29.99, categorie, furnizor, locatie, 3.0
        );

        produsIgienaCuratareDAO.insertProdusIgienaCuratare(produs1);
        produsIgienaCuratareDAO.insertProdusIgienaCuratare(produs2);

        List<ProdusIgienaCuratare> produse = produsIgienaCuratareDAO.findAllProduseIgienaCuratare();
        assertTrue(produse.size() >= 2, "Ar trebui sa existe cel putin doua produse de igiena si curatare in baza de date");

        produsIgienaCuratareDAO.deleteProdusIgienaCuratareByName(produs1.getNumeProdus(), produs1.getCantitate());
        produsIgienaCuratareDAO.deleteProdusIgienaCuratareByName(produs2.getNumeProdus(), produs2.getCantitate());
    }

    @Test
    void testDeleteProdusIgienaCuratareByName() throws Exception {
        ProdusIgienaCuratare produs = new ProdusIgienaCuratare(
                "Produs Igiena Test", 10, 19.99, categorie, furnizor, locatie, 2.5
        );

        produsIgienaCuratareDAO.insertProdusIgienaCuratare(produs);

        produsIgienaCuratareDAO.deleteProdusIgienaCuratareByName(produs.getNumeProdus(), produs.getCantitate());

        List<ProdusIgienaCuratare> produse = produsIgienaCuratareDAO.findProduseIgienaCuratareByName(produs.getNumeProdus());
        assertTrue(produse.isEmpty(), "Produsul ar trebui sa fie sters din baza de date");
    }
}
