package bo;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
	private int id;
	private String nom;
	private String adresse;
	private String url_image;
	private Carte carte;
	private List<Horaire> horaires = new ArrayList<>();
	private List<TableRestaurant> tables = new ArrayList<>();
	
	public Restaurant(int id, String nom, String adresse, String url_image, Carte carte) {
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.url_image = url_image;
		this.carte = carte;
	}
	
	public Restaurant(String nom, String adresse, String url_image, Carte carte) {
		this.nom = nom;
		this.adresse = adresse;
		this.url_image = url_image;
		this.carte = carte;
	}
	
	public Restaurant() {}
	
	public Restaurant(int id, String nom, String adresse, String url_image) {
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.url_image = url_image;
	}

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

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public List<Horaire> getHoraires() {
		return horaires;
	}

	public void setHoraires(List<Horaire> horaires) {
		this.horaires = horaires;
	}

	public List<TableRestaurant> getTables() {
		return tables;
	}

	public void setTables(List<TableRestaurant> tables) {
		this.tables = tables;
	}

	@Override
	public String toString() {
		return String.format("%-4d %-20s %-50s %-30s %-70s\n", id, nom, adresse, carte.getNom(), url_image);
	}
	
}
