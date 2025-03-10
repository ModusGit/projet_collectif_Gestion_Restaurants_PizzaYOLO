package bll;

import java.util.List;

import bo.Restaurant;
import bo.TableRestaurant;
import dal.TableRestaurantDAO;
import exceptions.TableRestaurantException;

public class TableRestaurantBLL {

	public List<TableRestaurant> insert(List<TableRestaurant> tablesRestaurant, int idRestaurant) throws TableRestaurantException {
		
		//checkTableRestaurant(tableRestaurant);
		
		TableRestaurantDAO dao = new TableRestaurantDAO();
		dao.insert(tablesRestaurant, idRestaurant);
		
		return tablesRestaurant;
	}
	
	
	public List<TableRestaurant> selectFromRestaurant(Restaurant restaurant) {
		
		TableRestaurantDAO dao = new TableRestaurantDAO();
		return dao.selectFromRestaurant(restaurant);
		 
	}
	
	//private void checkTableRestaurant(TableRestaurant tableRestaurant) throws TableRestaurantException {
		// if (cartes.getNom() == null || cartes.getNom().length() < 2 || cartes.getNom().length() > 20) {
			// throw new CartesException("Le nom doit faire entre 2 et 20 caractères.");
		// les if sont à déterminer !
		//}
	

	
	public void update(TableRestaurant tableRestaurant) throws TableRestaurantException {
		//checkTableRestaurant(tableRestaurant);
		
		TableRestaurantDAO dao = new TableRestaurantDAO();
		dao.update(tableRestaurant);
	}
	
	public void delete (int id) {
		TableRestaurantDAO dao = new TableRestaurantDAO();
		dao.delete(id);
	}
}