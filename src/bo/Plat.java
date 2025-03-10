package bo;

public class Plat {
	private int id;
	private String nom;
	private double prix;
	private String description;
	private Categorie categorie;
	
	public Plat(int id, String nom, double prix, String description, Categorie categorie) {
		this.id = id;
		this.nom = nom;
		this.prix = prix;
		this.description = description;
	}

	public Plat(String nom, double prix, String description, Categorie categorie) {
		this.nom = nom;
		this.prix = prix;
		this.description = description;
		this.categorie = categorie;
	}

	public Plat() {}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	@Override
	public String toString() {
		return String.format("%-4s %-10s %-5s %-20s %-15s\n", id, nom, prix, description, categorie);
	}
}
