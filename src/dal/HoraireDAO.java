package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Horaire;

public class HoraireDAO {
    String url = System.getenv("FIL_ROUGE_URL");
    String username = System.getenv("FIL_ROUGE_USERNAME");
    String password = System.getenv("FIL_ROUGE_PASSWORD");

    public List<Horaire> select() {
        List<Horaire> liste_horaire = new ArrayList<>();
        try (Connection cnx = DriverManager.getConnection(url + ";trustServerCertificate=true", username, password)) {
            if (!cnx.isClosed()) {
                try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM dbo.horaires");
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        liste_horaire.add(convertResultSetToHoraires(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste_horaire;
    }

    public List<Horaire> selectByRestaurantId(int restaurantId) {
        List<Horaire> horaires = new ArrayList<>();
        try (Connection cnx = DriverManager.getConnection(url + ";trustServerCertificate=true", username, password)) {
            if (!cnx.isClosed()) {
                try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM dbo.horaires WHERE id_restaurants = ?")) {
                    ps.setInt(1, restaurantId);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Horaire horaire = convertResultSetToHoraires(rs);
                            horaires.add(horaire);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horaires;
    }

    public Horaire insert(Horaire horaire) {
        try (Connection cnx = DriverManager.getConnection(url + ";trustServerCertificate=true", username, password)) {
            if (!cnx.isClosed()) {
                try (PreparedStatement ps = cnx.prepareStatement(
                        "INSERT INTO dbo.horaires(jour, ouverture, fermeture, id_restaurants) VALUES (?, ?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, horaire.getJour());
                    ps.setDate(2, Date.valueOf(horaire.getOuverture()));
                    ps.setDate(3, Date.valueOf(horaire.getFermeture()));
                    ps.setInt(4, horaire.getIdRestaurant());

                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            horaire.setId(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horaire;
    }

    public void update(Horaire horaire) {
        try (Connection cnx = DriverManager.getConnection(url + ";trustServerCertificate=true", username, password)) {
            if (!cnx.isClosed()) {
                try (PreparedStatement ps = cnx.prepareStatement(
                        "UPDATE dbo.horaires SET jour = ?, ouverture = ?, fermeture = ? WHERE id = ?")) {
                    ps.setString(1, horaire.getJour());
                    ps.setDate(2, Date.valueOf(horaire.getOuverture()));
                    ps.setDate(3, Date.valueOf(horaire.getFermeture()));
                    ps.setInt(4, horaire.getId());

                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection cnx = DriverManager.getConnection(url + ";trustServerCertificate=true", username, password)) {
            if (!cnx.isClosed()) {
                try (PreparedStatement ps = cnx.prepareStatement("DELETE FROM dbo.horaires WHERE id = ?")) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Horaire convertResultSetToHoraires(ResultSet rs) throws SQLException {
        Horaire horaire = new Horaire();
        horaire.setId(rs.getInt("id"));
        horaire.setJour(rs.getString("jour"));
        horaire.setOuverture(rs.getDate("ouverture").toLocalDate());
        horaire.setFermeture(rs.getDate("fermeture").toLocalDate());
        horaire.setIdRestaurant(rs.getInt("id_restaurants"));
        return horaire;
    }
}