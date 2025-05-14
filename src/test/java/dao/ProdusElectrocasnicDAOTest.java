package dao;

import classes.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProdusElectrocasnicDAOTest {
    private ProdusElectrocasnicDAO produsElectrocasnicDAO = new ProdusElectrocasnicDAO();
    private CategorieDAO categorieDAO = new CategorieDAO();
    private FurnizorDAO furnizorDAO = new FurnizorDAO();

    private Categorie categorie = categorieDAO.findCategorieByName("Fructe");
    private Furnizor furnizor = furnizorDAO.findFurnizorByName("FurnizorExemplu");
    private Locatie locatie = new Locatie(3, 6);

    public ProdusElectrocasnicDAOTest() throws SQLException {
    }

    @Test
    void testInsertProdusElectrocasnic() throws Exception {
        ProdusElectrocasnic produs = new ProdusElectrocasnic(
                "Produs Electrocasnic Test", 5, 499.99, categorie, furnizor, locatie, ClasaEnergetica.A
        );

        produsElectrocasnicDAO.insertProdusElectrocasnic(produs);
        List<ProdusElectrocasnic> produse = produsElectrocasnicDAO.findProduseElectrocasniceByName(produs.getNumeProdus());
        assertFalse(produse.isEmpty(), "Produsul ar trebui sa fie inserat in baza de date");

        produsElectrocasnicDAO.deleteProdusElectrocasnicByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testUpdateProdusElectrocasnic() throws Exception {
        ProdusElectrocasnic produs = new ProdusElectrocasnic(
                "Produs Electrocasnic Test", 5, 499.99, categorie, furnizor, locatie, ClasaEnergetica.A
        );

        produsElectrocasnicDAO.insertProdusElectrocasnic(produs);

        List<ProdusElectrocasnic> produse = produsElectrocasnicDAO.findProduseElectrocasniceByName(produs.getNumeProdus());
        ProdusElectrocasnic produsInBaza = produse.get(0);

        produsInBaza.setPret(549.99);
        produsInBaza.setClasaEnergetica(ClasaEnergetica.B);
        produsElectrocasnicDAO.updateProdusElectrocasnic(produsInBaza);

        List<ProdusElectrocasnic> produseActualizate = produsElectrocasnicDAO.findProduseElectrocasniceByName(produs.getNumeProdus());
        assertEquals(549.99, produseActualizate.get(0).getPret(), "Pretul ar trebui sa fie actualizat");
        assertEquals(ClasaEnergetica.B, produseActualizate.get(0).getClasaEnergetica(), "Clasa energetica ar trebui sa fie actualizata");

        produsElectrocasnicDAO.deleteProdusElectrocasnicByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testFindProduseElectrocasniceByName() throws Exception {
        ProdusElectrocasnic produs = new ProdusElectrocasnic(
                "Produs Electrocasnic Test", 5, 499.99, categorie, furnizor, locatie, ClasaEnergetica.A
        );

        produsElectrocasnicDAO.insertProdusElectrocasnic(produs);

        List<ProdusElectrocasnic> produse = produsElectrocasnicDAO.findProduseElectrocasniceByName(produs.getNumeProdus());
        assertFalse(produse.isEmpty(), "Produsul ar trebui sa fie gasit");
        assertEquals("Produs Electrocasnic Test", produse.get(0).getNumeProdus(), "Numele produsului ar trebui sa se potriveasca");

        produsElectrocasnicDAO.deleteProdusElectrocasnicByName(produs.getNumeProdus(), produs.getCantitate());
    }

    @Test
    void testFindAllProduseElectrocasnice() throws Exception {
        ProdusElectrocasnic produs1 = new ProdusElectrocasnic(
                "Produs Electrocasnic Test", 5, 499.99, categorie, furnizor, locatie, ClasaEnergetica.A
        );
        ProdusElectrocasnic produs2 = new ProdusElectrocasnic(
                "Produs Electrocasnic Test 2", 10, 799.99, categorie, furnizor, locatie, ClasaEnergetica.B
        );

        produsElectrocasnicDAO.insertProdusElectrocasnic(produs1);
        produsElectrocasnicDAO.insertProdusElectrocasnic(produs2);

        List<ProdusElectrocasnic> produse = produsElectrocasnicDAO.findAllProduseElectrocasnice();
        assertTrue(produse.size() >= 2, "Ar trebui sa existe cel putin doua produse electrocasnice in baza de date");

        produsElectrocasnicDAO.deleteProdusElectrocasnicByName(produs1.getNumeProdus(), produs1.getCantitate());
        produsElectrocasnicDAO.deleteProdusElectrocasnicByName(produs2.getNumeProdus(), produs2.getCantitate());
    }

    @Test
    void testDeleteProdusElectrocasnicByName() throws Exception {
        ProdusElectrocasnic produs = new ProdusElectrocasnic(
                "Produs Electrocasnic Test", 5, 499.99, categorie, furnizor, locatie, ClasaEnergetica.A
        );

        produsElectrocasnicDAO.insertProdusElectrocasnic(produs);

        produsElectrocasnicDAO.deleteProdusElectrocasnicByName(produs.getNumeProdus(), produs.getCantitate());

        List<ProdusElectrocasnic> produse = produsElectrocasnicDAO.findProduseElectrocasniceByName(produs.getNumeProdus());
        assertTrue(produse.isEmpty(), "Produsul ar trebui sa fie sters din baza de date");
    }
}
