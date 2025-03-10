package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Carte;

public class CarteDAO {

	String url = System.getenv("FIL_ROUGE_URL");
	String username = System.getenv("FIL_ROUGE_USERNAME");
	String password = System.getenv("FIL_ROUGE_PASSWORD");
	
	public List<Carte> select() {
		List<Carte> carte = new ArrayList<>();

		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");

			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("SELECT * FROM cartes");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					carte.add(convertResultSetToCartes(rs));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carte;
	}

	public Carte insert(Carte carte) {	
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password + ";trustservercertificate=true");	

			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement(
						"INSERT INTO cartes(nom, description)"
						+ "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, carte.getNom());
				ps.setString(2, carte.getDescription());
			
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					carte.setId(rs.getInt(1));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carte;
	}

	public void update(Carte carte) {
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password + ";trustservercertificate=true");

			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement(
						"UPDATE cartes SET nom = ?, description = ? WHERE id = ?");
				ps.setString(1, carte.getNom());
				ps.setString(2, carte.getDescription());
				ps.setInt(3, carte.getId());
				
				ps.executeUpdate();
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void delete(int id) {
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password + ";trustservercertificate=true");	

			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("DELETE FROM cartes WHERE id = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
 	private Carte convertResultSetToCartes(ResultSet rs) throws SQLException {
		Carte carte = new Carte();
		carte.setId(rs.getInt("id"));
		carte.setNom(rs.getString("nom"));
		carte.setDescription(rs.getString("description"));
		return carte;
	} 	
}
