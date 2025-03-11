package bo;

import java.time.LocalDate;

public class Horaire {
	private int id;
	private String jour;
	private LocalDate ouverture;
	private LocalDate fermeture;
	private int idRestaurant;
	 
	public Horaire(int id, String jour, LocalDate ouverture, LocalDate fermeture, int idRestaurant) {
		this.id = id;
		this.jour = jour;
		this.ouverture = ouverture;
		this.fermeture = fermeture;
		this.idRestaurant = idRestaurant;
	}

	public Horaire(String jour, LocalDate ouverture, LocalDate fermeture, int idRestaurant) {
		this.jour = jour;
		this.ouverture = ouverture;
		this.fermeture = fermeture;
		this.idRestaurant = idRestaurant;
	}
	 	
	public Horaire() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public LocalDate getOuverture() {
		return ouverture;
	}

	public void setOuverture(LocalDate ouverture) {
		this.ouverture = ouverture;
	}

	public LocalDate getFermeture() {
		return fermeture;
	}

	public void setFermeture(LocalDate fermeture) {
		this.fermeture = fermeture;
	}
	
	public int getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(int idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	@Override
	public String toString() {
			return String.format("| %-4s | %-20s | %-10s | %-14s |\n","ID","Jour","Ouverture","Fermeture")+String.format("| %-4d | %-20s | %-10s | %-14s |\n",
					id,
					jour,
					ouverture,
					fermeture);
	}

}
