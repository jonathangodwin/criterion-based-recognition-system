package database.dao;

import java.sql.*;
import java.util.*;
import model.Typology;

public class TypologyDAO extends BaseDAO<Typology> {

    public TypologyDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Typology> getAll() throws SQLException {
        List<Typology> typologies = new ArrayList<>();
        String query = "SELECT * FROM typology";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Typology t = new Typology(
                    rs.getInt("id_typology"),
                    rs.getString("name"),
                    rs.getString("description")
                );
                typologies.add(t);
            }
        }
        return typologies;
    }

    @Override
    public Typology getById(int id) throws SQLException {
        Typology t = null;
        String query = "SELECT * FROM typology WHERE id_typology = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                t = new Typology(
                    rs.getInt("id_typology"),
                    rs.getString("name"),
                    rs.getString("description")
                );
            }
        }
        return t;
    }

    @Override
    public void insert(Typology t) throws SQLException {
        String query = "INSERT INTO typology(name, description) VALUES(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, t.getTypologyName());
            ps.setString(2, t.getDescription());
            ps.executeUpdate();
            System.out.println("Typology insérée : " + t.getNom());
        }
    }

    @Override
    public void update(Typology t, String... params) throws SQLException {
        if (params.length == 0) return;
        String query = "UPDATE typology SET name = ?, description = ? WHERE id_typology = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, params[0]);
            ps.setString(2, params.length > 1 ? params[1] : t.getDescription());
            ps.setInt(3, t.getId());
            ps.executeUpdate();
        }
    }
}
