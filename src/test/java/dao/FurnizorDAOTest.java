package dao;

import classes.Furnizor;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FurnizorDAOTest {
    private FurnizorDAO furnizorDAO = new FurnizorDAO();

    @Test
    void testInsertFurnizor() throws Exception {
        Furnizor furnizor = new Furnizor("Furnizor Test Unitar", "test@furnizor.ro", "0723456789", "Strada Exemplu, Nr. 10");
        furnizorDAO.insertFurnizor(furnizor);

        Furnizor furnizorGasit = furnizorDAO.findFurnizorByName("Furnizor Test Unitar");
        assertNotNull(furnizorGasit.getFurnizorID(), "ID-ul ar trebui sa fie generat");

        furnizorDAO.deleteFurnizorById(furnizorGasit.getFurnizorID());
    }

    @Test
    void testFindFurnizorByName() throws Exception {
        Furnizor furnizor = new Furnizor("Furnizor Test Unitar", "test@furnizor.ro", "0723456789", "Strada Exemplu, Nr. 10");
        furnizorDAO.insertFurnizor(furnizor);

        Furnizor furnizorGasit = furnizorDAO.findFurnizorByName("Furnizor Test Unitar");
        assertNotNull(furnizorGasit, "Furnizorul ar trebui sa fie gasit!");
        assertEquals(furnizor.getNumeFurnizor(), furnizorGasit.getNumeFurnizor(), "Numele ar trebui sa se potriveasca");

        furnizorDAO.deleteFurnizorById(furnizorGasit.getFurnizorID());
    }

    @Test
    void testFindAllFurnizori() throws Exception {
        Furnizor furnizor = new Furnizor("Furnizor Test Unitar", "test@furnizor.ro", "0723456789", "Strada Exemplu, Nr. 10");
        furnizorDAO.insertFurnizor(furnizor);

        List<Furnizor> furnizori = furnizorDAO.findAllFurnizori();
        assertTrue(furnizori.size() > 0, "Ar trebui sa existe cel putin un furnizor in baza de date");

        furnizorDAO.deleteFurnizorById(furnizor.getFurnizorID());
    }

    @Test
    void testDeleteFurnizorById() throws Exception {
        Furnizor furnizor = new Furnizor("Furnizor Test Unitar", "test@furnizor.ro", "0723456789", "Strada Exemplu, Nr. 10");
        furnizorDAO.insertFurnizor(furnizor);

        furnizorDAO.deleteFurnizorById(furnizor.getFurnizorID());

        //Furnizor furnizorGasit = furnizorDAO.findFurnizorByName("Furnizor Test Unitar");
        //assertNull(furnizorGasit, "Furnizorul ar trebui să fie sters");
    }
}
