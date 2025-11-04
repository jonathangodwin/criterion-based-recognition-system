package database.dao;

import java.sql.*;
import java.util.*;
import model.Attribute;

public class AttributeDAO extends BaseDAO<Attribute> {

    public AttributeDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Attribute> getAll() throws SQLException {
        List<Attribute> list = new ArrayList<>();
        String query = "SELECT * FROM typology_attribute";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Attribute a = new Attribute(
                    rs.getInt("id_attribute"),
                    rs.getString("name"),
                    rs.getString("valeur"),
                    rs.getString("type")
                );
                list.add(a);
            }
        }
        return list;
    }

    @Override
    public Attribute getById(int id) throws SQLException {
        Attribute a = null;
        String query = "SELECT * FROM typology_attribute WHERE id_attribute = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a = new Attribute(
                    rs.getInt("id_attribute"),
                    rs.getString("name"),
                    rs.getString("valeur"),
                    rs.getString("type")
                );
            }
        }
        return a;
    }

    @Override
    public void insert(Attribute a) throws SQLException {
        String query = "INSERT INTO typology_attribute(nom, valeur, type) VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, a.getName());
            ps.setString(2, a.getValue());
            ps.setString(3, a.getType());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Attribute a, String[] params) throws SQLException {
        if (params.length < 2) return;
        String query = "UPDATE typology_attribute SET valeur = ?, type = ? WHERE id_attribute = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, params[0]);
            ps.setString(2, params[1]);
            ps.setInt(3, a.getId());
            ps.executeUpdate();
        }
    }
}
