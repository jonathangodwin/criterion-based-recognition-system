import java.util.*;
import java.sql.*;

import model.*;
import attribute.*;
import engine.*;

//import database.*;
import database.dao.*;

public class Main {
    public static void main(String[] args) {

    /*     // Création d'un domaine fictif
        Domain plantDomain = new Domain("PlantDomain");

        // --- Création de l'observation ---
        ArrayList<Attribute> obsAttributes = new ArrayList<>();
        obsAttributes.add(new NumericalAttribute("height", 180.0, 1.0, 150.0, 200.0));
        obsAttributes.add(new BooleanAttribute("hasLeaves", true));
        obsAttributes.add(new CategoricalAttribute("color", "green"));

        Observation obs = new Observation(obsAttributes, plantDomain, 1, 101, "Notes d'observation");

        // --- Création des typologies ---
        List<Attribute> treeAttributes = new ArrayList<>();
        treeAttributes.add(new NumericalAttribute("height", 170.0, 1.0, 150.0, 200.0));
        treeAttributes.add(new BooleanAttribute("hasLeaves", true));
        treeAttributes.add(new CategoricalAttribute("color", "green"));
        Typology treeType = new Typology(treeAttributes, plantDomain, "Oak", "Oak tree");

        List<Attribute> bushAttributes = new ArrayList<>();
        bushAttributes.add(new NumericalAttribute("height", 100.0, 1.0, 50.0, 150.0));
        bushAttributes.add(new BooleanAttribute("hasLeaves", true));
        bushAttributes.add(new CategoricalAttribute("color", "green"));
        Typology bushType = new Typology(bushAttributes, plantDomain, "Bush", "Bush plant");

        List<Typology> typologies = Arrays.asList(treeType, bushType);

        // --- Création du moteur et reconnaissance ---
        RecognitionEngine engine = new RecognitionEngine(typologies);

        List<Typology> results = engine.recognize(obs);

        // --- Affichage des résultats ---
        System.out.println("Typologies les plus proches de l'observation :");
        for (Typology t : results)
            System.out.println(t.getTypologyName() + " (" + t.getDescription() + ")");
 */
            Domain nouveauDomaine = new Domain("Paléanthologie");

    
        try {
            DomainDAO domainDAO = new DomainDAO(DatabaseManager.getConnection());
            System.out.println(domainDAO.getAll());
            System.out.println(domainDAO.getDomainFromId(1));
            System.out.println(domainDAO.getIdFromDomain(domainDAO.getAll().get(1)));
            
            domainDAO.insert(nouveauDomaine);
            System.out.println(domainDAO.getAll());

            
            domainDAO.update(nouveauDomaine, "Jardinage");
            System.out.println(domainDAO.getAll());

            

        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la DB ! ");
        }
         

    }
}
