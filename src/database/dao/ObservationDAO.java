package database.dao;

import java.sql.*;
import java.util.*;
import model.Observation;

public class ObservationDAO extends BaseDAO<Observation> {

    public ObservationDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Observation> getAll() throws SQLException {
        List<Observation> observations = new ArrayList<>();
        String query = "SELECT * FROM observation";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Observation obs = new Observation(
                    rs.getInt("id_observation"),
                    rs.getString("name"),
                    rs.getString("description")
                );
                observations.add(obs);
            }
        }
        return observations;
    }

    @Override
    public Observation getById(int id) throws SQLException {
        Observation obs = null;
        String query = "SELECT * FROM observation WHERE id_observation = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obs = new Observation(
                    rs.getInt("id_observation"),
                    rs.getString("name"),
                    rs.getString("description")
                );
            }
        }
        return obs;
    }

    @Override
    public void insert(Observation obs) throws SQLException {
        String query = "INSERT INTO observation(name, description) VALUES(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, obs.getNom());
            ps.setString(2, obs.getDescription());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Observation obs, String[] params) throws SQLException {
        if (params.length == 0) return;
        String query = "UPDATE observation SET name = ?, description = ? WHERE id_observation = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, params[0]);
            ps.setString(2, params.length > 1 ? params[1] : obs.getDescription());
            ps.setInt(3, obs.getId());
            ps.executeUpdate();
        }
    }
}
