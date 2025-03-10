package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Plat;

public class PlatDAO {
		String url = System.getenv("FIL_ROUGE_URL");
		String username = System.getenv("FIL_ROUGE_USERNAME");
		String password = System.getenv("FIL_ROUGE_PASSWORD");
		
		public List<Plat> select() {
			List<Plat> liste_plats = new ArrayList<>();

			try {
				Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
				if(!cnx.isClosed()) {
					PreparedStatement ps = cnx.prepareStatement("SELECT * FROM plats");
					ResultSet rs = ps.executeQuery();
					
					while (rs.next()) {
						liste_plats.add(convertResultSetToPlats(rs));
					}
				}
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return liste_plats;
		}

		public Plat insert(Plat plat) {	
			try {
				Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
				if(!cnx.isClosed()) {
					PreparedStatement ps = cnx.prepareStatement(
							"INSERT INTO plats(nom, prix, description, id_categories)"
							+ "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
					ps.setString(1, plat.getNom());
					ps.setDouble(2, plat.getPrix());
					ps.setString(3, plat.getDescription());
					ps.setInt(4, plat.getCategorie().getId());
				
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						plat.setId(rs.getInt(1));
					}
				}
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return plat;
		}

		public void update(Plat plat) {
			try {
				Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
				if(!cnx.isClosed()) {
					PreparedStatement ps = cnx.prepareStatement(
							"UPDATE plats SET nom = ?, prix = ?, description = ? WHERE id = ?");
					ps.setString(1, plat.getNom());
					ps.setDouble(2, plat.getPrix());
					ps.setString(3, plat.getDescription());
					ps.setInt(4, plat.getId());
					
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
					PreparedStatement ps = cnx.prepareStatement("DELETE FROM plats WHERE id = ?");
					ps.setInt(1, id);
					ps.executeUpdate();
				}
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	 	private Plat convertResultSetToPlats(ResultSet rs) throws SQLException {
			Plat plat = new Plat();
			plat.setId(rs.getInt("id"));
			plat.setNom(rs.getString("nom"));
			plat.setPrix(rs.getDouble("prix"));
			plat.setDescription(rs.getString("description"));
			return plat;
		} 	
	}
