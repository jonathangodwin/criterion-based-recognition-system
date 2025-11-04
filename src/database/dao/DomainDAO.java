package database.dao;

import java.util.*;
import java.sql.*;

import model.*;

public class DomainDAO {
    private final Connection connection;

    public DomainDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Domain> getAll() throws SQLException {
        String query = "SELECT * from domain";
        List<Domain> domains = new ArrayList <Domain> ();
        
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                String domainName = rs.getString("name");
                Domain currentDomain = new Domain(domainName);
                domains.add (currentDomain);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données");
            e.printStackTrace();
        }

        return domains;
    }

    public Domain getDomainFromId(int id) throws SQLException {
        Domain domain = null;
        String query = "SELECT * FROM domain WHERE id = '" + id  + "';";
        
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

             if (!rs.next()) System.out.println ("Le domaine indiqué n'existe pas !");
             else {  
                String domainName = rs.getString("name");
                domain = new Domain (domainName);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données");
            e.printStackTrace();
        }

        return domain;
    }

    public int getIdFromDomain(Domain domain) {
        String sql = "SELECT id FROM domain WHERE name = ?";
        int id = -1; // valeur par défaut si aucun résultat trouvé

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, domain.getDomainName());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                } else {
                    System.out.println("Aucun domaine trouvé avec le nom : " + domain.getDomainName());
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de l'exécution de la requête ! ");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'id du domaine : " + e.getMessage());
            e.printStackTrace();
        }

        return id;
    }

    public void insert(Domain domain) throws SQLException { 
        String domainName = domain.getDomainName();
        if (domain != null && (domainName != null && !domainName.equals(""))){
            String query = "INSERT INTO domain(name) VALUES(?);";
            try { 
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, domainName);
                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) 
                    System.out.println("Insertion réussie !"); 
                else 
                    System.out.println("Échec de l'insertion du domaine.");    
            } catch (SQLException e) {
                System.out.println("Problème avec la connexion à la base de données ! ");
                e.printStackTrace();            
            }  

        } else 
            System.out.println ("Veuillez vérifier votre domaine est bien valide !\n");
        
    }

    public void update(Domain domain, String newDomainName) throws SQLException {
        if (domain == null || newDomainName == null || newDomainName.isEmpty()) {
            System.out.println("Paramètres invalides pour la mise à jour !");
        } else { 

            String query = "UPDATE domain SET name = ? WHERE name = ?";

            try{

                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, newDomainName);
                pstmt.setString(2, domain.getDomainName());
                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Mise à jour réussie. '" + domain.getDomainName() + "' devient :  '" + newDomainName + "'");
                } else {
                    System.out.println("Aucun domaine trouvé à mettre à jour : " + domain.getDomainName());
                }

            } catch (SQLException e) {
                System.err.println("Erreur lors de la mise à jour du domaine : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public void delete(int id) throws SQLException {
        String query = "DELETE FROM domain WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Domaine supprimé avec succès (ID = " + id + ")");
            } else {
                System.out.println("Aucun domaine trouvé avec l'ID : " + id);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du domaine : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
