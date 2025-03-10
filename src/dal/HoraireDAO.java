package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Horaire;
import bo.Restaurant;
import bo.TableRestaurant;

public class HoraireDAO {
	String url = System.getenv("FIL_ROUGE_URL");
	String username = System.getenv("FIL_ROUGE_USERNAME");
	String password = System.getenv("FIL_ROUGE_PASSWORD");

	public List<Horaire> select() {
		List<Horaire> horaires = new ArrayList<>();
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password + ";trustservercertificate=true");

			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("SELECT * FROM horaires");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					horaires.add(convertResultSetToHoraire(rs));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return horaires;
	}
	
	
	public List<Horaire> selectFromRestaurant(Restaurant restaurant) {
		restaurant.getId()
;		List<Horaire> horaires = new ArrayList<>();
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("SELECT * FROM horaires WHERE id_restaurants=?");
				ps.setInt(1, restaurant.getId());
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					horaires.add(convertResultSetToHoraire(rs));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return horaires;
	}
	

	public List<Horaire> insert(List<Horaire> horaires, int idRestaurant) {
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password + ";trustservercertificate=true");
			if(!cnx.isClosed()) {
				for(Horaire horaireCurrent:horaires){
					PreparedStatement psHoraire = cnx.prepareStatement(
							"INSERT INTO horaires(jour, ouverture, fermeture, id_restaurants)"
							+ "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
					psHoraire.setString(1, horaireCurrent.getJour());
					psHoraire.setTime(2, java.sql.Time.valueOf(horaireCurrent.getOuverture()));
					psHoraire.setTime(3, java.sql.Time.valueOf(horaireCurrent.getFermeture()));
					psHoraire.setInt(4, idRestaurant);
					
					
					psHoraire.executeUpdate(); 
		
				}
			}
			
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return horaires;
	}
	
	
	public void update(Horaire horaire) {
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password + ";trustservercertificate=true");
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement(
						"UPDATE cartes SET jour = ?, ouverture = ?, fermeture = ?, WHERE id = ?");
				ps.setString(1, horaire.getJour());
				ps.setTime(2, java.sql.Time.valueOf(horaire.getOuverture()));
				ps.setTime(3, java.sql.Time.valueOf(horaire.getFermeture()));

				
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
				PreparedStatement ps = cnx.prepareStatement("DELETE FROM horaires WHERE id = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
 	private Horaire convertResultSetToHoraire(ResultSet rs) throws SQLException {
		Horaire horaire= new Horaire();
		horaire.setId(rs.getInt("id"));
		horaire.setJour(rs.getString("jour"));
		horaire.setOuverture(rs.getTime("ouverture").toLocalTime());
		horaire.setFermeture(rs.getTime("fermeture").toLocalTime());

		return horaire;
	} 

	
}