package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Categorie;

public class CategorieDAO {
	String url = System.getenv("FIL_ROUGE_URL");
	String username = System.getenv("FIL_ROUGE_USERNAME");
	String password = System.getenv("FIL_ROUGE_PASSWORD");
	
	public List<Categorie> select() {
		List<Categorie> liste_categorie = new ArrayList<>();

		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("SELECT * FROM categories");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					liste_categorie.add(convertResultSetToCategorie(rs));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste_categorie;
	}

	public Categorie insert(Categorie categorie) {	
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement(
						"INSERT INTO categories(libelle)"
						+ "VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, categorie.getLibelle());

			
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					categorie.setId(rs.getInt(1));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorie;
	}

	public void update(Categorie categorie) {
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement(
						"UPDATE categorie SET categorie = ? WHERE id = ?");
				ps.setString(1, categorie.getLibelle());
				ps.setInt(2, categorie.getId());
				
				ps.executeUpdate();
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void delete(int id) {
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("DELETE FROM categories WHERE id = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
 	private Categorie convertResultSetToCategorie(ResultSet rs) throws SQLException {
		Categorie categorie = new Categorie();
		categorie.setId(rs.getInt("id"));
		categorie.setLibelle(rs.getString("categories"));
		return categorie;
	} 	
}
