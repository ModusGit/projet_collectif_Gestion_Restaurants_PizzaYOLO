package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bll.RestaurantBLL;
import bll.TableRestaurantBLL;
import bo.Carte;
import bo.Restaurant;
import bo.TableRestaurant;
import exceptions.RestaurantException;

public class TestTables {
	private static Scanner scan;
	private static RestaurantBLL RestaurantBLL = new RestaurantBLL();
	

	public static void main(String[] args) throws Exception {
		
		scan = new Scanner(System.in);
				
		afficherMenuAjoutRestaurant();
		
		
	}
	
	
	
	private static void afficherMenuAjoutRestaurant() throws Exception {
		boolean insertionFailed;
		do {
			System.out.print("Veuillez saisir le nom du nouveau resto : ");
			String nom = scan.nextLine();
			
			System.out.print("Veuillez saisir la adresse du resto : ");
			String adresse = scan.nextLine();
			
			System.out.print("Veuillez saisir l'url de l'image du resto : ");
			String url_image = scan.nextLine();
			
			Restaurant restaurant = new Restaurant();
			int idRestaurant = 0;			
			
			Carte carte = new Carte();
			
			try {

				restaurant = RestaurantBLL.insert(nom, adresse, url_image);

				insertionFailed = false;
				System.out.println(restaurant.getId());
				idRestaurant = restaurant.getId();
				
			} catch (RestaurantException e) {
				insertionFailed = true;
				System.err.println("Echec de la création du resto :");
				System.err.println(e.getMessage());
			} 
			
			
			afficherMenuAjoutTablesDansRestaurant(restaurant, idRestaurant);
			
			
		} while (insertionFailed);
	}



	private static void afficherMenuAjoutTablesDansRestaurant(Restaurant restaurant, int idRestaurant) throws Exception {
		System.out.print("combien de tables dans votre resto ? :");
		int nbTables = scan.nextInt();
		scan.nextLine();
		
		List<TableRestaurant> tablesRestaurant = new ArrayList<>();
		
		do {
			System.out.print("nb de places de la table ? :");
			int nbPlaces = scan.nextInt();
			scan.nextLine();
			
			System.out.print("numero de la table ? :");
			int numeroTable = scan.nextInt();
			scan.nextLine();
			
			TableRestaurant table = new TableRestaurant(nbPlaces, numeroTable);
			tablesRestaurant.add(table);
			
			nbTables--;
		} while (nbTables > 0);
		
		
		TableRestaurantBLL tableRestaurantBLL = new TableRestaurantBLL();
		tableRestaurantBLL.insert(tablesRestaurant, idRestaurant);
		
		restaurant.setTables(tablesRestaurant);
		
		System.out.println(restaurant.getTables());
	}
	
	
	
	
	
	

}
