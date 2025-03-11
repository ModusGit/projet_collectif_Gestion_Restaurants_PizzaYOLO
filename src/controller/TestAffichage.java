package controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import bll.CarteBLL;
import bll.PlatBLL;
import bll.RestaurantBLL;
import bo.Carte;
import bo.Plat;
import bo.Restaurant;
import exceptions.CarteException;
import exceptions.PlatException;
import exceptions.RestaurantException;

public class TestAffichage {
	private static Scanner scan;
	private static RestaurantBLL RestaurantBLL = new RestaurantBLL();
	private static CarteBLL CarteBLL = new CarteBLL();
	//private static HoraireBLL HoraireBLL = new HoraireBLL();
	//private static TableRestaurantBLL TableRestaurantBLL = new TableRestaurantBLL();
	private static PlatBLL PlatBLL = new PlatBLL();
	//private static CategorieBLL CategorieBLL = new CategorieBLL();
	
	public static void main(String[] args) throws Exception {
		scan = new Scanner(System.in);
		System.out.println("Bienvenue sur l'application de gestion de vos restaurants !");
		int choix;
		do {
			choix = afficherMenu();
			
			switch(choix) {
			case 1: afficherMenuAjoutRestaurant(); break;
			case 2: afficherRestaurant(RestaurantBLL.select()); break;
			case 3: afficherMenuModificationRestaurant(); break;
			case 4: afficherMenuSuppressionRestaurant(); break;
			case 5: afficherMenuAjoutCarte(); break;
			case 6: afficherMenuModificationCarte(); break;
			case 7: afficherMenuAjoutPlat(); break;
			case 8: afficherMenuModificationPlat(); break;
			}
		} while (choix != 9);
		System.out.println("Merci de votre visite et à bientôt !\r\n"
				+ " ____                   \r\n"
				+ "/    \\			\r\n"
				+ "  u  u|      _______    \r\n"
				+ "    \\ |  .-''#%&#&%#``-.   \r\n"
				+ "   = /  ((%&#&#&%&VK&%&))  \r\n"
				+ "    |    `-._#%&##&%_.-'   \r\n"
				+ " /\\/\\`--.   `-.\"\".-'\r\n"
				+ " |  |    \\   /`./          \r\n"
				+ " |\\/|  \\  `-'  /\r\n"
				+ " || |   \\     /            Pizza YOLO");
		scan.close();
	}
	
	private static void afficherMenuAjoutRestaurant() throws RestaurantException {
		boolean insertionFailed;
		do {
			System.out.print("Veuillez saisir le nom du nouveau restaurant : ");
			String nom = scan.nextLine();
			
			System.out.print("Veuillez saisir l'adresse du nouveau restaurant : ");
			String adresse = scan.nextLine();
			
			System.out.print("Veuillez saisir l'url de l'image du nouveau restaurant : ");
			String url_image = scan.nextLine();
			
			Carte carte = new Carte(4,"un nom","une description");		
		
			try {
				RestaurantBLL.insert(nom, adresse, url_image, carte);
				insertionFailed = false;
			} catch (RestaurantException e) {
				insertionFailed = true;
				System.err.println("Echec de la création du nouveau restaurant :");
				System.err.println(e.getMessage());
			}
		} while (insertionFailed);
	}
	
	private static void afficherMenuModificationRestaurant() throws RestaurantException {
		System.out.println("Saisissez l'id du restaurant à modifier, ou tapez 0 pour retourner au menu principal.");
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
						System.err.println("L'identifiant sélectionné n'existe pas.");
						choix = -1;
					}
				}
				
			} catch (InputMismatchException e) {
				System.err.println("Choix invalide.");
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
				System.err.println("Echec de la modification du restaurant : ");
				System.err.println(e.getMessage());
			}
		} while (updateFailed);
	}
		
	private static void afficherMenuSuppressionRestaurant() {
		System.out.println("Saisissez l'id du restaurant à supprimer, ou tapez 0 pour retourner au menu principal.");
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
				System.err.println("Choix invalide.");
				choix = -1;
			}
		} while (choix != 0);
	}

	private static void afficherRestaurant(List<Restaurant> Restaurants) {
	    int totalLength = 4 + 20 + 50 + 30 + 70;
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	    System.out.format("%-4s %-20s %-50s %-30s %-70s\n", "id", "nom", "adresse", "cartes", "url_image");
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	    for (Restaurant current : Restaurants) {
	        System.out.print(current);
//	        List<Horaire> horaires = HoraireBLL.selectByRestaurantId(current.getId());
//	        for (Horaire horaire : horaires) {
//	            System.out.print(horaire);
//	        }
	    }
	    
	    System.out.println("+" + "-".repeat(totalLength) + "+");
	}
	
	private static int afficherMenu() {
		int choix;
		do {
			System.out.println();
			System.out.println("Quelle action souhaitez-vous réaliser ?");
			System.out.println("\t1. Enregistrer un nouveau restaurant");
			System.out.println("\t2. Consulter la liste des restaurants existants");
			System.out.println("\t3. Modifier un restaurant");
			System.out.println("\t4. Supprimer un restaurant");
			System.out.println("\t5. Enregistrer une nouvelle carte");
			System.out.println("\t6. Modifier une carte");
			System.out.println("\t7. Enregistrer un nouveau plat");
			System.out.println("\t8. Modifier un plat");
			System.out.println("\t9. Quitter l'application");
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
		System.out.println("Saisissez l'id de la carte à modifier, ou tapez 0 pour retourner au menu principal.");
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
						System.err.println("L'identifiant sélectionné n'existe pas.");
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
				System.err.println("Echec de la modification de la carte : ");
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
			
			System.out.println("Veuillez saisir le prix du plat : ");
			double prix = scan.nextDouble();
			
			System.out.print("Veuillez saisir la description du plat : ");
			String description = scan.nextLine();
			
			try {
				PlatBLL.insert(nom, prix, description);
				insertionFailed = false;
			} catch (PlatException e) {
				insertionFailed = true;
				System.err.println("Echec de la création du plat : ");
				System.err.println(e.getMessage());
			}
		} while (insertionFailed);
	}
	
	private static void afficherMenuModificationPlat() {
		System.out.println("Saisissez l'id du plat à modifier, ou tapez 0 pour retourner au menu principal.");
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
						System.err.println("L'identifiant sélectionné n'existe pas.");
						choix = -1;
					}
				}
				
			} catch (InputMismatchException e) {
				System.err.println("Choix invalide.");
				choix = -1;
			}
			
		} while (choix != 0);
		
		boolean updateFailed;
		do {
			System.out.print("Veuillez saisir le nouveau nom du plat : ");
			String nom = scan.nextLine();
			
			System.out.println("Veuillez saisir le nouveau prix du plat : ");
			double prix = scan.nextDouble();
						
			System.out.print("Veuillez saisir la nouvelle description du plat : ");
			String description = scan.nextLine();
			
		
			Plat platTemp = new Plat(choix, nom, prix, description);
			
			try {
				PlatBLL.update(platTemp);
				updateFailed = false;
			} catch (PlatException e) {
				updateFailed = true;
				System.err.println("Echec de la modification du plat : ");
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