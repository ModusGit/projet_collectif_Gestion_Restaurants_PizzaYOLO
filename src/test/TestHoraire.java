package test;

//public class TestHoraire {
//	private static Scanner scan;
//	private static RestaurantBLL RestaurantBLL = new RestaurantBLL();
//
//	public static void main(String[] args) throws Exception {
//		scan = new Scanner(System.in);
//		int idRestaurant = 0;
//		boolean insertionFailed;
//
//		do {
//			System.out.print("Veuillez saisir le nom du nouveau resto : ");
//			String nom = scan.nextLine();
//
//			System.out.print("Veuillez saisir la adresse du resto : ");
//			String adresse = scan.nextLine();
//
//			System.out.print("Veuillez saisir l'url de l'image du resto : ");
//			String url_image = scan.nextLine();
//
//			Carte carte = new Carte();
//			try {
//				Restaurant restaurant = RestaurantBLL.insert(nom, adresse, url_image);
//				insertionFailed = false;
//				idRestaurant = restaurant.getId();
//			} catch (RestaurantException e) {
//				insertionFailed = true;
//				System.err.println("Echec de la création du resto :");
//				System.err.println(e.getMessage());
//			}
//
//		} while (insertionFailed);
//
//		ajoutHoraire(idRestaurant);
//
//	}
//
//	private static void ajoutHoraire(int idRestaurant) throws Exception {
//		List<String> semaine = Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");
//		List<Horaire> horaires = new ArrayList<>();
//
//		for (String jour : semaine) {
//			System.out.println("Rentrez les horaires du " + jour);
//
//			System.out.print("ouverture? :");
//			String ouverture = scan.nextLine();
//
//			System.out.print("fermeture? :");
//			String fermeture = scan.nextLine();
//
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//			LocalTime ouvert = LocalTime.parse(ouverture, formatter);
//			LocalTime ferme = LocalTime.parse(fermeture, formatter);
//			Horaire horaire = new Horaire(jour, ouvert, ferme);
//			System.out.println(horaire.toString());
//			horaires.add(horaire);
//		}
//
//		HoraireBLL horaireBLL = new HoraireBLL();
//		horaireBLL.insert(horaires, idRestaurant);
//
//	}
//
//}
