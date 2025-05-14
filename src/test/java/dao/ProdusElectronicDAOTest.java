package dao;

import classes.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProdusElectronicDAOTest {
    private ProdusElectronicDAO produsElectronicDAO = new ProdusElectronicDAO();
    private CategorieDAO categorieDAO = new CategorieDAO();
    private FurnizorDAO furnizorDAO = new FurnizorDAO();

    private Categorie categorie = categorieDAO.findCategorieByName("Fructe");
    private Furnizor furnizor = furnizorDAO.findFurnizorByName("FurnizorExemplu");
    private Locatie locatie = new Locatie(2, 5);

    public ProdusElectronicDAOTest() throws SQLException {
    }

    @Test
    void testInsertProdusElectronic() throws Exception {
        ProdusElectronic produs = new ProdusElectronic(
                "Produs Electronic Test", 10, 299.99, categorie, furnizor, locatie, 20.0, 15.0, 5.0, 1500
        );

        produsElectronicDAO.insertProdusElectronic(produs);
        List<ProdusElectronic> produse = produsElectronicDAO.findProduseElectroniceByName(produs.getNumeProdus());
        assertFalse(produse.isEmpty(), "Produsul ar trebui sa fie inserat in baza de date");

        produsElectronicDAO.deleteProdusElectronicByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testUpdateProdusElectronic() throws Exception {
        ProdusElectronic produs = new ProdusElectronic(
                "Produs Electronic Test", 10, 299.99, categorie, furnizor, locatie, 20.0, 15.0, 5.0, 1500
        );

        produsElectronicDAO.insertProdusElectronic(produs);

        List<ProdusElectronic> produse = produsElectronicDAO.findProduseElectroniceByName(produs.getNumeProdus());
        ProdusElectronic produsInBaza = produse.get(0);

        produsInBaza.setPret(349.99);
        produsInBaza.setLatime(22.0);
        produsElectronicDAO.updateProdusElectronic(produsInBaza);

        List<ProdusElectronic> produseActualizate = produsElectronicDAO.findProduseElectroniceByName(produs.getNumeProdus());
        assertEquals(349.99, produseActualizate.get(0).getPret(), "Pretul ar trebui sa fie actualizat");
        assertEquals(22.0, produseActualizate.get(0).getLatime(), "Latimea ar trebui sa fie actualizata");

        produsElectronicDAO.deleteProdusElectronicByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testFindProduseElectroniceByName() throws Exception {
        ProdusElectronic produs = new ProdusElectronic(
                "Produs Electronic Test", 10, 299.99, categorie, furnizor, locatie, 20.0, 15.0, 5.0, 1500
        );

        produsElectronicDAO.insertProdusElectronic(produs);

        List<ProdusElectronic> produse = produsElectronicDAO.findProduseElectroniceByName(produs.getNumeProdus());
        assertFalse(produse.isEmpty(), "Produsul ar trebui sa fie gasit");
        assertEquals("Produs Electronic Test", produse.get(0).getNumeProdus(), "Numele produsului ar trebui sa se potriveasca");

        produsElectronicDAO.deleteProdusElectronicByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testFindAllProduseElectronice() throws Exception {
        ProdusElectronic produs1 = new ProdusElectronic(
                "Produs Electronic Test", 10, 299.99, categorie, furnizor, locatie, 20.0, 15.0, 5.0, 1500
        );
        ProdusElectronic produs2 = new ProdusElectronic(
                "Produs Electronic Test 2", 15, 399.99, categorie, furnizor, locatie, 25.0, 20.0, 7.0, 2000
        );

        produsElectronicDAO.insertProdusElectronic(produs1);
        produsElectronicDAO.insertProdusElectronic(produs2);

        List<ProdusElectronic> produse = produsElectronicDAO.findAllProduseElectronice();
        assertTrue(produse.size() >= 2, "Ar trebui sa existe cel putin doua produse electronice in baza de date");

        produsElectronicDAO.deleteProdusElectronicByName(produs1.getNumeProdus(), produs1.getCantitate());
        produsElectronicDAO.deleteProdusElectronicByName(produs2.getNumeProdus(), produs2.getCantitate());
    }

    @Test
    void testDeleteProdusElectronicByName() throws Exception {
        ProdusElectronic produs = new ProdusElectronic(
                "Produs Electronic Test", 10, 299.99, categorie, furnizor, locatie, 20.0, 15.0, 5.0, 1500
        );

        produsElectronicDAO.insertProdusElectronic(produs);

        produsElectronicDAO.deleteProdusElectronicByName(produs.getNumeProdus(), produs.getCantitate());

        List<ProdusElectronic> produse = produsElectronicDAO.findProduseElectroniceByName(produs.getNumeProdus());
        assertTrue(produse.isEmpty(), "Produsul ar trebui sa fie sters din baza de date");
    }
}
