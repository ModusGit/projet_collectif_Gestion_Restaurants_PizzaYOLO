package bll;

import java.util.List;

import bo.Carte;
import dal.CarteDAO;
import exceptions.CarteException;

public class CarteBLL {
	
	public List<Carte> select() {
		CarteDAO dao = new CarteDAO();
		return dao.select();
	}

	public Carte insert(String nom, String description) throws CarteException {

		Carte carte = new Carte(nom, description);
		checkCartes(carte);
		
		CarteDAO dao = new CarteDAO();
		dao.insert(carte);
		
		return carte;
	}
	
	/*
	 * Pour avoir le droit de réaliser l'insertion, une carte doit respecter les contraintes suivantes :
	 * nom compris entre 2 et 30 caractères,
	 * description compris entre 2 et 255 caractères,
	 */
	private void checkCartes(Carte carte) throws CarteException {
		 if (carte.getNom() == null || carte.getNom().length() < 2 || carte.getNom().length() > 30) {
			 throw new CarteException("Le nom doit faire entre 2 et 30 caractères.");
		}
		if (carte.getDescription() == null || carte.getDescription().length() < 2 || carte.getDescription().length() > 255) {
			 throw new CarteException("La description doit faire entre 2 et 255 caractères.");
		}
	}
	
	public void update(Carte carte) throws CarteException {
		checkCartes(carte);
		
		CarteDAO dao = new CarteDAO();
		dao.update(carte);
	}
	
	public void delete (int id) {
		CarteDAO dao = new CarteDAO();
		dao.delete(id);
	}
}