package controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import bll.CarteBLL;
import bll.HoraireBLL;
import bll.PlatBLL;
import bll.RestaurantBLL;
import bll.TableRestaurantBLL;
import bo.Carte;
import bo.Categorie;
import bo.Horaire;
import bo.Plat;
import bo.Restaurant;
import bo.TableRestaurant;
import exceptions.CarteException;
import exceptions.HoraireException;
import exceptions.PlatException;
import exceptions.RestaurantException;
import exceptions.TableRestaurantException;

public class TestAffichageMaud {
	private static Scanner scan;
	private static RestaurantBLL RestaurantBLL = new RestaurantBLL();
	private static CarteBLL CarteBLL = new CarteBLL();
	// private static HoraireBLL HoraireBLL = new HoraireBLL();
	// private static TableRestaurantBLL TableRestaurantBLL = new TableRestaurantBLL();
	private static PlatBLL PlatBLL = new PlatBLL();
	// private static CategorieBLL CategorieBLL = new CategorieBLL();

	
	public static void main(String[] args) throws Exception {
		scan = new Scanner(System.in);
		System.out.println("Bienvenue sur l'application de gestion de vos restaurants !");
		int choix;
		do {
			choix = afficherMenu();
			
			switch(choix) {

			case 1: afficherMenuAjoutRestaurant(); break;
			case 2: afficherMenuModificationRestaurant(); break;
			case 3: afficherMenuSuppressionRestaurant(); break;
			case 4: afficherMenuAjoutCarte(); break;
			case 5: afficherMenuModificationCarte(); break;
			
//			case 1: afficherMenuAjoutRestaurant(); break;
//			case 2: afficherRestaurant(RestaurantBLL.select()); break;
//			case 3: afficherMenuModificationRestaurant(); break;
//			case 4: afficherMenuSuppressionRestaurant(); break;
//			case 5: afficherMenuAjoutCarte(); break;
//			case 6: afficherMenuModificationCarte(); break;
			case 7: afficherMenuAjoutPlat(); break;
			case 8: afficherMenuModificationPlat(); break;
			}
		} while (choix != 9);
		System.out.println("Merci de votre visite, bonne journée !");
		scan.close();
	}
	
	private static void afficherMenuAjoutRestaurant() throws RestaurantException, HoraireException, TableRestaurantException {
		boolean insertionFailed;
		int idRestaurant=0;
		Restaurant restaurant = new Restaurant();
		do {
			System.out.print("Veuillez saisir le nom du restaurant : ");
			String nom = scan.nextLine();
			
			System.out.print("Veuillez saisir l'adresse du restaurant : ");
			String adresse = scan.nextLine();
			
			System.out.print("Veuillez saisir l'url de l'image du restaurant : ");
			String url_image = scan.nextLine();
			
	
		
			try {
				restaurant=RestaurantBLL.insert(nom, adresse, url_image);
				insertionFailed = false;
				idRestaurant = restaurant.getId();
			} catch (RestaurantException e) {
				insertionFailed = true;
				System.err.println("Echec de la création du restaurant :");
				System.err.println(e.getMessage());
			}
			
			
			
		} while (insertionFailed);
		
		ajoutHoraire(idRestaurant);
		afficherMenuAjoutTablesDansRestaurant(restaurant, idRestaurant);
	}
	
	private static void ajoutHoraire(int idRestaurant) throws HoraireException {
		List<String> semaine = Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");
		List<Horaire> horaires = new ArrayList<>();
		
		for(String jour:semaine) {
			System.out.println("Rentrez les horaires du "+jour);
			
			System.out.print("ouverture? :");
			String ouverture = scan.nextLine() ;
			
			System.out.print("fermeture? :");
			String fermeture = scan.nextLine();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		    LocalTime ouvert = LocalTime.parse(ouverture, formatter);
		    LocalTime ferme = LocalTime.parse(fermeture, formatter);
			Horaire horaire = new Horaire (jour, ouvert, ferme);
			System.out.println(horaire.toString());
			horaires.add(horaire);
		}
		
		HoraireBLL horaireBLL = new HoraireBLL();
		horaireBLL.insert(horaires, idRestaurant);
		
	}
	
	private static void afficherMenuAjoutTablesDansRestaurant(Restaurant restaurant, int idRestaurant) throws TableRestaurantException {
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
	
	private static void afficherMenuModificationRestaurant() throws RestaurantException {
		System.out.println("Saisissez l'id du resto à modifier, ou 0 pour retourner au menu principal.");
		List<Restaurant> Restaurants = RestaurantBLL.select();
		
		if (Restaurants.isEmpty()) {
			System.err.println("Aucun élément à afficher.");
			return;
		}
		
		afficherRestaurant(Restaurants);
		int choix;
		do {
			try {
				choix = scan.nextInt();
				
				if (Restaurants.stream().map(Restaurant::getId).collect(Collectors.toList()).contains(choix)) {
					scan.nextLine();
					break;
				} else {
					if (choix != 0) {
						System.err.println("L'identifiant sélectionné n'existe.");
						choix = -1;
					}
				}
				
			} catch (InputMismatchException e) {
				System.err.println("Choix invalide");
				choix = -1;
			}
			
		} while (choix != 0);
		
		boolean updateFailed;
		do {
			System.out.print("Veuillez saisir le nouveau nom du restaurant : ");
			String nom = scan.nextLine();
			
			System.out.print("Veuillez saisir la nouvelle adresse du restaurant : ");
			String adresse = scan.nextLine();
			
			System.out.print("Veuillez saisir la nouvelle url de l'image du restaurant : ");
			String url_image = scan.nextLine();
			
			Restaurant restoTemp = new Restaurant(choix, nom, adresse, url_image);
			
			try {
				RestaurantBLL.update(restoTemp);
				updateFailed = false;
			} catch (RestaurantException e) {
				updateFailed = true;
				System.err.println("Echec de la modification du resto :");
				System.err.println(e.getMessage());
			}
		} while (updateFailed);
	}
		
	private static void afficherMenuSuppressionRestaurant() {
		System.out.println("Saisissez l'id du resto à supprimer, ou 0 pour retourner au menu principal.");
		List<Restaurant> Restaurants = RestaurantBLL.select();
		
		if (Restaurants.isEmpty()) {
			System.err.println("Aucun élément à afficher.");
			return;
		}
		
		afficherRestaurant(Restaurants);
		int choix;
		do {
			try {
				choix = scan.nextInt();
				
				if (Restaurants.stream().map(Restaurant::getId).collect(Collectors.toList()).contains(choix)) {
					RestaurantBLL.delete(choix);
					choix = 0;
				} else {
					if (choix != 0) {
						System.err.println("L'identifiant sélectionné n'existe pas.");
						choix = -1;
					}
				}
				
			} catch (InputMismatchException e) {
				System.err.println("Choix invalide");
				choix = -1;
			}
		} while (choix != 0);
	}

	private static void afficherRestaurant(List<Restaurant> Restaurants) {
	    int totalLength = 4 + 30 + 30 + 20 + 50 + 20 + 50;
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	    System.out.format("%-4s %-30s %-30s %-20s %-50s %-20s %-50s\n", "id", "nom", "adresse", "cartes", "horaires", "tables", "url_image");
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	    for (Restaurant current : Restaurants) {
	        System.out.print(current);
	    }
	    
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	    
	    
	    
	}

	private static int afficherMenu() {
		int choix;
		do {
			System.out.println();
			System.out.println("Quelle action souhaitez-vous réaliser ?");
			System.out.println("\t1. Ajouter un restaurant.");
			System.out.println("\t2. Modifier un restaurant existant.");
			System.out.println("\t3. Supprimer un restaurant existant.");
			System.out.println("\t4. Créer une carte.");
			System.out.println("\t5. Modifier une carte.");
			System.out.println("\t6. Quitter l'application");
			
			System.out.println("\t2. Consulter le restaurant existant.");
			System.out.println("\t7. Enregistrer un nouveau plat");
			System.out.println("\t8. Modifier un plat");
			System.out.println("");
			System.out.println("Faites votre choix : ");
			try {
				choix = scan.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Choix invalide.");
				choix = -1;
			} finally {
				scan.nextLine();
			}
		} while (choix < 1 || choix > 9);
		return choix;
	}
	
	private static void afficherMenuModificationCarte() {
		System.out.println("Saisissez l'id de la carte à modifier, ou 0 pour retourner au menu principal.");
		List<Carte> Cartes = CarteBLL.select();
		
		if (Cartes.isEmpty()) {
			System.err.println("Aucun élément à afficher.");
			return;
		}
		
		afficherCarte(Cartes);
		int choix;
		do {
			try {
				choix = scan.nextInt();
				
				if (Cartes.stream().map(Carte::getId).collect(Collectors.toList()).contains(choix)) {
					scan.nextLine();
					break;
				} else {
					if (choix != 0) {
						System.err.println("L'identifiant sélectionné n'existe pas en base.");
						choix = -1;
					}
				}
				
			} catch (InputMismatchException e) {
				System.err.println("Choix invalide");
				choix = -1;
			}
			
		} while (choix != 0);
		
		boolean updateFailed;
		do {
			System.out.print("Veuillez saisir le nouveau nom de la carte : ");
			String nom = scan.nextLine();
			
			System.out.print("Veuillez saisir la nouvelle description de la carte : ");
			String description = scan.nextLine();
			
		
			Carte carteTemp = new Carte(choix, nom, description);
			
			try {
				CarteBLL.update(carteTemp);
				updateFailed = false;
			} catch (CarteException e) {
				updateFailed = true;
				System.err.println("Echec de la modification de la carte :");
				System.err.println(e.getMessage());
			}
		} while (updateFailed);
	}

	private static void afficherMenuAjoutCarte() throws CarteException {
		boolean insertionFailed;
		do {
			System.out.print("Veuillez saisir le nom de la carte : ");
			String nom = scan.nextLine();
			
			System.out.print("Veuillez saisir la description de la carte : ");
			String description = scan.nextLine();
			
			try {
				CarteBLL.insert(nom, description);
				insertionFailed = false;
			} catch (CarteException e) {
				insertionFailed = true;
				System.err.println("Echec de la création de la carte :");
				System.err.println(e.getMessage());
			}
		} while (insertionFailed);
	}	
	
	private static void afficherCarte(List<Carte> Cartes) {
	    int totalLength = 4 + 10 + 10 + 30;
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	    System.out.format("%-4s %-10s %-25s %-30s\n", "id", "nom", "description", "plats");
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	    for (Carte current : Cartes) {
	        System.out.print(current);
	    }
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	}

	private static void afficherMenuAjoutPlat() throws Exception {
		boolean insertionFailed;
		do {
			System.out.print("Veuillez saisir le nom du plat : ");
			String nom = scan.nextLine();
			
			System.out.println("Vaillez saisir le prix du plat : ");
			double prix = scan.nextDouble();
			scan.nextLine();
			
			System.out.print("Veuillez saisir la description du plat : ");
			String description = scan.nextLine();
			
			System.out.print("Veuillez saisir la categorie (entree, plat, dessert, boisson) : ");
			String choixCategorie = scan.nextLine();
			Categorie categorie = new Categorie(3, choixCategorie);
			
			try {
				PlatBLL.insert(nom, prix, description, categorie);
				insertionFailed = false;
			} catch (PlatException e) {
				insertionFailed = true;
				System.err.println("Echec de la création du plat :");
				System.err.println(e.getMessage());
			}
		} while (insertionFailed);
	}
	
	private static void afficherMenuModificationPlat() {
		System.out.println("Saisissez l'id du plat à modifier, ou 0 pour retourner au menu principal.");
		List<Plat> plats = PlatBLL.select();
		
		if (plats.isEmpty()) {
			System.err.println("Aucun élément à afficher.");
			return;
		}
		
		afficherPlat(plats);
		int choix;
		do {
			try {
				choix = scan.nextInt();
				
				if (plats.stream().map(Plat::getId).collect(Collectors.toList()).contains(choix)) {
					scan.nextLine();
					break;
				} else {
					if (choix != 0) {
						System.err.println("L'identifiant sélectionné n'existe pas en base.");
						choix = -1;
					}
				}
				
			} catch (InputMismatchException e) {
				System.err.println("Choix invalide");
				choix = -1;
			}
			
		} while (choix != 0);
		
		boolean updateFailed;
		do {
			System.out.print("Veuillez saisir le nouveau nom du plat : ");
			String nom = scan.nextLine();
			
			System.out.println("Vaillez saisir le prix du plat : ");
			double prix = scan.nextDouble();
						
			System.out.print("Veuillez saisir la nouvelle description du plat : ");
			String description = scan.nextLine();
			
		
			Plat platTemp = new Plat(choix, nom, prix, description, null);
			
			try {
				PlatBLL.update(platTemp);
				updateFailed = false;
			} catch (PlatException e) {
				updateFailed = true;
				System.err.println("Echec de la modification du plat :");
				System.err.println(e.getMessage());
			}
		} while (updateFailed);
	}

	private static void afficherPlat(List<Plat> plats) {
	    int totalLength = 4 + 10 + 10 + 30;
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	    System.out.format("%-4s %-10s %-5s %-20s %-15s\n", "id", "nom", "prix", "description", "catégorie");
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	    for (Plat current : plats) {
	        System.out.print(current);
	    }
	    System.out.println("+" + "-".repeat(totalLength) + "+");

	}
	
}