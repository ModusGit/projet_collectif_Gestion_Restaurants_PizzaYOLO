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

public class MainController {
    // Constantes
    private static final String SEPARATOR_LINE = "+";
    private static final String MESSAGE_NO_ELEMENT = "Aucun élément à afficher.";
    private static final String MESSAGE_INVALID_ID = "L'identifiant sélectionné n'existe pas.";
    private static final String MESSAGE_INVALID_CHOICE = "Choix invalide.";
    
    // Attributs
    private static Scanner scan;
    private static RestaurantBLL restaurantBLL = new RestaurantBLL();
    private static CarteBLL carteBLL = new CarteBLL();
    private static PlatBLL platBLL = new PlatBLL();
    
    public static void main(String[] args) throws Exception {
        scan = new Scanner(System.in);
        afficherBienvenue();
        
        int choix;
        do {
            choix = afficherMenuPrincipal();
            
            switch(choix) {
                case 1: gererAjoutRestaurant(); break;
                case 2: afficherRestaurants(); break;
                case 3: gererModificationRestaurant(); break;
                case 4: gererSuppressionRestaurant(); break;
                case 5: gererAjoutCarte(); break;
                case 6: gererModificationCarte(); break;
                case 7: gererAjoutPlat(); break;
                case 8: gererModificationPlat(); break;
            }
        } while (choix != 9);
        
        afficherAuRevoir();
        scan.close();
    }
    
    // Méthodes d'affichage génériques
    
    private static void afficherBienvenue() {
        System.out.println("        _....._\r\n"
                + "    _.:`.--|--.`:._\r\n"
                + "  .: .'\\o  | o /'. '.\r\n"
                + " // '.  \\ o|  /  o '.\\\r\n"
                + "//'._o'. \\ |o/ o_.-'o\\\\\r\n"
                + "|| o '-.'.\\|/.-' o   ||\r\n"
                + "||--o--o-->|"
                + "Bienvenue sur l'application de gestion de vos restaurants !");
    }
    
    private static void afficherAuRevoir() {
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
    }
    
    private static int afficherMenuPrincipal() {
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
                System.err.println(MESSAGE_INVALID_CHOICE);
                choix = -1;
            } finally {
                scan.nextLine();
            }
        } while (choix < 1 || choix > 9);
        return choix;
    }
    
    // Méthodes de gestion des restaurants
    
    private static void afficherRestaurants() {
        List<Restaurant> restaurants = restaurantBLL.select();
        if (restaurants.isEmpty()) {
            System.err.println(MESSAGE_NO_ELEMENT);
            return;
        }
        afficherListeRestaurants(restaurants);
    }
    
    private static void afficherListeRestaurants(List<Restaurant> restaurants) {
        int totalLength = 4 + 20 + 50 + 30 + 70;
        System.out.println(SEPARATOR_LINE + "-".repeat(totalLength) + SEPARATOR_LINE);
        System.out.format("%-4s %-20s %-50s %-30s %-70s\n", "id", "nom", "adresse", "cartes", "url_image");
        System.out.println(SEPARATOR_LINE + "-".repeat(totalLength) + SEPARATOR_LINE);
        
        for (Restaurant current : restaurants) {
            System.out.print(current);
        }
        
        System.out.println(SEPARATOR_LINE + "-".repeat(totalLength) + SEPARATOR_LINE);
    }
    
    private static void gererAjoutRestaurant() throws RestaurantException {
        boolean insertionFailed;
        do {
            System.out.print("Veuillez saisir le nom du nouveau restaurant : ");
            String nom = scan.nextLine();
            
            System.out.print("Veuillez saisir l'adresse du nouveau restaurant : ");
            String adresse = scan.nextLine();
            
            System.out.print("Veuillez saisir l'url de l'image du nouveau restaurant : ");
            String urlImage = scan.nextLine();
            
            Carte carte = new Carte(4, "un nom", "une description");        
        
            try {
                restaurantBLL.insert(nom, adresse, urlImage, carte);
                insertionFailed = false;
            } catch (RestaurantException e) {
                insertionFailed = true;
                System.err.println("Echec de la création du nouveau restaurant :");
                System.err.println(e.getMessage());
            }
        } while (insertionFailed);
    }
    
    private static void gererModificationRestaurant() throws RestaurantException {
        System.out.println("Saisissez l'id du restaurant à modifier, ou tapez 0 pour retourner au menu principal.");
        List<Restaurant> restaurants = restaurantBLL.select();
        
        if (restaurants.isEmpty()) {
            System.err.println(MESSAGE_NO_ELEMENT);
            return;
        }
        
        afficherListeRestaurants(restaurants);
        int restaurantId = selectionnerIdDansListe(restaurants, Restaurant::getId);
        
        if (restaurantId == 0) {
            return;
        }
        
        modifierRestaurant(restaurantId);
    }
    
    private static void modifierRestaurant(int restaurantId) {
        boolean updateFailed;
        do {
            System.out.print("Veuillez saisir le nouveau nom du restaurant : ");
            String nom = scan.nextLine();
            
            System.out.print("Veuillez saisir la nouvelle adresse du restaurant : ");
            String adresse = scan.nextLine();
            
            System.out.print("Veuillez saisir la nouvelle url de l'image du restaurant : ");
            String urlImage = scan.nextLine();
            
            Restaurant restoTemp = new Restaurant(restaurantId, nom, adresse, urlImage);
            
            try {
                restaurantBLL.update(restoTemp);
                updateFailed = false;
            } catch (RestaurantException e) {
                updateFailed = true;
                System.err.println("Echec de la modification du restaurant : ");
                System.err.println(e.getMessage());
            }
        } while (updateFailed);
    }
    
    private static void gererSuppressionRestaurant() {
        System.out.println("Saisissez l'id du restaurant à supprimer, ou tapez 0 pour retourner au menu principal.");
        List<Restaurant> restaurants = restaurantBLL.select();
        
        if (restaurants.isEmpty()) {
            System.err.println(MESSAGE_NO_ELEMENT);
            return;
        }
        
        afficherListeRestaurants(restaurants);
        int restaurantId = selectionnerIdDansListe(restaurants, Restaurant::getId);
        
        if (restaurantId != 0) {
            restaurantBLL.delete(restaurantId);
        }
    }
    
    // Méthodes de gestion des cartes
    
    private static void gererAjoutCarte() throws CarteException {
        boolean insertionFailed;
        do {
            System.out.print("Veuillez saisir le nom de la carte : ");
            String nom = scan.nextLine();
            
            System.out.print("Veuillez saisir la description de la carte : ");
            String description = scan.nextLine();
            
            try {
                carteBLL.insert(nom, description);
                insertionFailed = false;
            } catch (CarteException e) {
                insertionFailed = true;
                System.err.println("Echec de la création de la carte :");
                System.err.println(e.getMessage());
            }
        } while (insertionFailed);
    }
    
    private static void afficherListeCartes(List<Carte> cartes) {
        int totalLength = 4 + 10 + 10 + 30;
        System.out.println(SEPARATOR_LINE + "-".repeat(totalLength) + SEPARATOR_LINE);
        System.out.format("%-4s %-10s %-25s %-30s\n", "id", "nom", "description", "plats");
        System.out.println(SEPARATOR_LINE + "-".repeat(totalLength) + SEPARATOR_LINE);
        
        for (Carte current : cartes) {
            System.out.print(current);
        }
        
        System.out.println(SEPARATOR_LINE + "-".repeat(totalLength) + SEPARATOR_LINE);
    }
    
    private static void gererModificationCarte() {
        System.out.println("Saisissez l'id de la carte à modifier, ou tapez 0 pour retourner au menu principal.");
        List<Carte> cartes = carteBLL.select();
        
        if (cartes.isEmpty()) {
            System.err.println(MESSAGE_NO_ELEMENT);
            return;
        }
        
        afficherListeCartes(cartes);
        int carteId = selectionnerIdDansListe(cartes, Carte::getId);
        
        if (carteId == 0) {
            return;
        }
        
        modifierCarte(carteId);
    }
    
    private static void modifierCarte(int carteId) {
        boolean updateFailed;
        do {
            System.out.print("Veuillez saisir le nouveau nom de la carte : ");
            String nom = scan.nextLine();
            
            System.out.print("Veuillez saisir la nouvelle description de la carte : ");
            String description = scan.nextLine();
            
            Carte carteTemp = new Carte(carteId, nom, description);
            
            try {
                carteBLL.update(carteTemp);
                updateFailed = false;
            } catch (CarteException e) {
                updateFailed = true;
                System.err.println("Echec de la modification de la carte : ");
                System.err.println(e.getMessage());
            }
        } while (updateFailed);
    }
    
    // Méthodes de gestion des plats
    
    private static void gererAjoutPlat() throws Exception {
        boolean insertionFailed;
        do {
            System.out.print("Veuillez saisir le nom du plat : ");
            String nom = scan.nextLine();
            
            System.out.print("Veuillez saisir le prix du plat : ");
            double prix = lireDouble();
            
            scan.nextLine(); // Consommer le retour à la ligne
            
            System.out.print("Veuillez saisir la description du plat : ");
            String description = scan.nextLine();
            
            try {
                platBLL.insert(nom, prix, description);
                insertionFailed = false;
            } catch (PlatException e) {
                insertionFailed = true;
                System.err.println("Echec de la création du plat : ");
                System.err.println(e.getMessage());
            }
        } while (insertionFailed);
    }
    
    private static void afficherListePlats(List<Plat> plats) {
        int totalLength = 4 + 10 + 10 + 30;
        System.out.println(SEPARATOR_LINE + "-".repeat(totalLength) + SEPARATOR_LINE);
        System.out.format("%-4s %-10s %-5s %-20s %-15s\n", "id", "nom", "prix", "description", "catégorie");
        System.out.println(SEPARATOR_LINE + "-".repeat(totalLength) + SEPARATOR_LINE);
        
        for (Plat current : plats) {
            System.out.print(current);
        }
        
        System.out.println(SEPARATOR_LINE + "-".repeat(totalLength) + SEPARATOR_LINE);
    }
    
    private static void gererModificationPlat() {
        System.out.println("Saisissez l'id du plat à modifier, ou tapez 0 pour retourner au menu principal.");
        List<Plat> plats = platBLL.select();
        
        if (plats.isEmpty()) {
            System.err.println(MESSAGE_NO_ELEMENT);
            return;
        }
        
        afficherListePlats(plats);
        int platId = selectionnerIdDansListe(plats, Plat::getId);
        
        if (platId == 0) {
            return;
        }
        
        modifierPlat(platId);
    }
    
    private static void modifierPlat(int platId) {
        boolean updateFailed;
        do {
            System.out.print("Veuillez saisir le nouveau nom du plat : ");
            String nom = scan.nextLine();
            
            System.out.print("Veuillez saisir le nouveau prix du plat : ");
            double prix = lireDouble();
            
            scan.nextLine(); // Consommer le retour à la ligne
            
            System.out.print("Veuillez saisir la nouvelle description du plat : ");
            String description = scan.nextLine();
            
            Plat platTemp = new Plat(platId, nom, prix, description);
            
            try {
                platBLL.update(platTemp);
                updateFailed = false;
            } catch (PlatException e) {
                updateFailed = true;
                System.err.println("Echec de la modification du plat : ");
                System.err.println(e.getMessage());
            }
        } while (updateFailed);
    }
    
    // Méthodes utilitaires
    
    private static <T> int selectionnerIdDansListe(List<T> liste, java.util.function.Function<T, Integer> extractId) {
        int choix;
        do {
            try {
                choix = scan.nextInt();
                
                if (liste.stream().map(extractId).collect(Collectors.toList()).contains(choix)) {
                    scan.nextLine(); // Consommer le retour à la ligne
                    return choix;
                } else {
                    if (choix != 0) {
                        System.err.println(MESSAGE_INVALID_ID);
                        choix = -1;
                    }
                }
                
            } catch (InputMismatchException e) {
                System.err.println(MESSAGE_INVALID_CHOICE);
                choix = -1;
                scan.nextLine(); // Consommer l'entrée invalide
            }
            
        } while (choix != 0);
        
        return choix;
    }
    
    private static double lireDouble() {
        double valeur = 0;
        boolean valide = false;
        
        do {
            try {
                valeur = scan.nextDouble();
                valide = true;
            } catch (InputMismatchException e) {
                System.err.println("Veuillez saisir un nombre valide.");
                scan.nextLine(); // Consommer l'entrée invalide
            }
        } while (!valide);
        
        return valeur;
    }
}