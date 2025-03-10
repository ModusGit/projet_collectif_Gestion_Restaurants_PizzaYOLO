package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Restaurant;
import bo.TableRestaurant;

public class TableRestaurantDAO {
	String url = System.getenv("FIL_ROUGE_URL");
	String username = System.getenv("FIL_ROUGE_USERNAME");
	String password = System.getenv("FIL_ROUGE_PASSWORD");
	
	public List<TableRestaurant> select() {
		List<TableRestaurant> tablesRestaurant = new ArrayList<>();
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("SELECT * FROM tables_restaurant");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					tablesRestaurant.add(convertResultSetToTableRestaurant(rs));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tablesRestaurant;
	}
	
	public List<TableRestaurant> selectFromRestaurant(Restaurant restaurant) {
		restaurant.getId()
;		List<TableRestaurant> tablesRestaurant = new ArrayList<>();
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("SELECT * FROM tables_restaurant WHERE id_restaurants=?");
				ps.setInt(1, restaurant.getId());
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					tablesRestaurant.add(convertResultSetToTableRestaurant(rs));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tablesRestaurant;
	}

	public List<TableRestaurant> insert(List<TableRestaurant> tablesRestaurant, int idRestaurant) {
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password + ";trustservercertificate=true");
			if(!cnx.isClosed()) {
				for(TableRestaurant tableCurrent : tablesRestaurant){
					PreparedStatement psTables = cnx.prepareStatement(
							"INSERT INTO tables_restaurant(nb_places, numero_table, id_restaurants)"
							+ "VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
					psTables.setInt(1, tableCurrent.getNbPlaces());
					psTables.setInt(2, tableCurrent.getNumeroTable());
					psTables.setInt(3, idRestaurant);
					
					psTables.executeUpdate(); 
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tablesRestaurant;
	}

	
	public void update(TableRestaurant tableRestaurant) {
		try {
			Connection cnx = DriverManager.getConnection(url + ";username=" + username + ";password=" + password +";trustservercertificate=true");
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement(
						"UPDATE tables_restaurant SET nb_places = ?, numero_table = ? WHERE id = ?");
				ps.setInt(1, tableRestaurant.getNbPlaces());
				ps.setInt(2, tableRestaurant.getNumeroTable());
				ps.setInt(3, tableRestaurant.getId());
				
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
				PreparedStatement ps = cnx.prepareStatement("DELETE FROM tables_restaurant WHERE id = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


 	private TableRestaurant convertResultSetToTableRestaurant(ResultSet rs) throws SQLException {
		TableRestaurant table = new TableRestaurant();
		table.setId(rs.getInt("id"));
		table.setNbPlaces(rs.getInt("nb_places"));
		table.setNumeroTable(rs.getInt("numero_table"));
		return table;
	}
}
