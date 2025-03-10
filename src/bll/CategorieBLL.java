package bll;

import java.util.List;

import bo.Categorie;
import dal.CategorieDAO;
import exceptions.CategorieException;

public class CategorieBLL {
	public List<Categorie> select() {
		CategorieDAO dao = new CategorieDAO();
		return dao.select();
	}

	public Categorie insert(String libelle) throws CategorieException {
		Categorie categorie = new Categorie(libelle);
		checkCategorie(categorie);
		
		CategorieDAO dao = new CategorieDAO();
		dao.insert(categorie);
		
		return categorie;
	}
	
	private void checkCategorie(Categorie categorie) throws CategorieException {
		// les if sont à déterminer !
		}
	
	public void update(Categorie categorie) throws CategorieException {
		checkCategorie(categorie);
		
		CategorieDAO dao = new CategorieDAO();
		dao.update(categorie);
	}
	
	public void delete (int id) {
		CategorieDAO dao = new CategorieDAO();
		dao.delete(id);
	}
}