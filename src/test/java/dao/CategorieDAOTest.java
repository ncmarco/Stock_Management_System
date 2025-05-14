package dao;
import dao.CategorieDAO;
import classes.Categorie;
import conn.DataBaseConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategorieDAOTest {
    private CategorieDAO categorieDAO = new CategorieDAO();

    @Test
    void testInsertCategorie() throws Exception {
        Categorie categorie = new Categorie("Orice");
        categorieDAO.insertCategorie(categorie);
        Categorie categCitita = categorieDAO.findCategorieByName(categorie.getNumeCategorie());
        assertNotNull(categCitita.getCategorieId(), "ID-ul ar trebui sa fie generat");

        categorieDAO.deleteCategorieById(categCitita.getCategorieId());
    }

    @Test
    void testInsertCategorieByName() throws Exception {
        String numeCategorie = "Orice";
        categorieDAO.insertCategorieByName(numeCategorie);

        Categorie categCitita = categorieDAO.findCategorieByName(numeCategorie);
        assertNotNull(categCitita.getCategorieId(), "ID-ul ar trebui sa fie generat");

        categorieDAO.deleteCategorieById(categCitita.getCategorieId());
    }

    @Test
    void testFindCategorieByName() throws Exception {
        Categorie categorie = new Categorie("Orice");
        categorieDAO.insertCategorie(categorie);

        Categorie foundCategorie = categorieDAO.findCategorieByName(categorie.getNumeCategorie());
        assertNotNull(foundCategorie, "Categoria ar trebui sa fie gasita!");
        assertEquals(categorie.getNumeCategorie(), foundCategorie.getNumeCategorie(), "Numele ar trebui sa se potriveasca");

        categorieDAO.deleteCategorieById(foundCategorie.getCategorieId());
    }

    @Test
    void testFindAllCategorii() throws Exception {
        Categorie categorie = new Categorie("Orice");
        categorieDAO.insertCategorie(categorie);

        List<Categorie> categorii = categorieDAO.findAllCategorii();
        assertNotNull(categorii.size(), "Ar trebui sa fie minim o categorie");

        categorieDAO.deleteCategorieById(categorie.getCategorieId());
    }

    @Test
    void testDeleteCategorieById() throws Exception {
        Categorie categorie = new Categorie("Orice");
        categorieDAO.insertCategorie(categorie);

        categorieDAO.deleteCategorieById(categorie.getCategorieId());

        //Categorie categorieGasita = categorieDAO.findCategorieByName(categorie.getNumeCategorie());
    }
}
