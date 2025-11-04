package database.dao;

import java.sql.*;
import java.util.*;

/**
 * Classe générique pour la gestion CRUD d'entités JDBC.
 * @param <T> le type d'entité manipulé (ex: Domain, Typology…)
 */
public abstract class BaseDAO<T> {
    protected final Connection connection;

    public BaseDAO(Connection connection) {
        this.connection = connection;
    }

    /** Récupère tous les objets d'une table. */
    public abstract List<T> getAll() throws SQLException;

    /** Récupère un objet selon son ID. */
    public abstract T getById(int id) throws SQLException;

    /** Insère un nouvel objet. */
    public abstract void insert(T obj) throws SQLException;

    /** Met à jour un objet existant. */
    public abstract void update(T obj, String... params) throws SQLException;

    /** Supprime un objet via son ID. */
    public void delete(String tableName, int id) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0)
                System.out.println("Suppression réussie dans " + tableName + " (ID=" + id + ")");
            else
                System.out.println("Aucun enregistrement trouvé avec l'ID : " + id);
        }
    }
}
