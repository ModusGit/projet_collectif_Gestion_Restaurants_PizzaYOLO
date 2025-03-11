package bll;

import java.util.List;

import bo.Carte;
import bo.Restaurant;
import dal.RestaurantDAO;
import exceptions.RestaurantException;

public class RestaurantBLL {
	public List<Restaurant> select() {
		RestaurantDAO dao = new RestaurantDAO();
		return dao.select();
	}

	public Restaurant insert(String nom, String adresse, String url_image, Carte carte) throws RestaurantException {
		Restaurant restaurant = new Restaurant(nom, adresse, url_image, carte);
		checkRestaurants(restaurant);
		
		RestaurantDAO dao = new RestaurantDAO();
		dao.insert(restaurant);
		
		return restaurant;
	}
	
	private void checkRestaurants(Restaurant restaurant) throws RestaurantException {
		if (restaurant.getNom() == null || restaurant.getNom().length() < 2 || restaurant.getNom().length() > 20) {
			throw new RestaurantException("Le nom doit faire entre 2 et 20 caractères.");
		}
		if (restaurant.getUrl_image() == null || restaurant.getUrl_image().length() < 10) {
			throw new RestaurantException("L'url de l'image doit faire au moins 10 caractères.");
		}
	}
	
	public void update(Restaurant restaurant) throws RestaurantException {
		checkRestaurants(restaurant);
		
		RestaurantDAO dao = new RestaurantDAO();
		dao.update(restaurant);
	}
	
	public void delete (int id) {
		RestaurantDAO dao = new RestaurantDAO();
		dao.delete(id);
	}
	
}