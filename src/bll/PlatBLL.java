package bll;

import java.util.List;

import bo.Plat;
import dal.PlatDAO;
import exceptions.PlatException;

public class PlatBLL {
	public List<Plat> select() {
		PlatDAO dao = new PlatDAO();
		return dao.select();
	}

	public Plat insert(String nom, double prix, String description) throws Exception {
		Plat plat = new Plat(nom, prix, description);
		checkPlats(plat);
		
		PlatDAO dao = new PlatDAO();
		dao.insert(plat);
		
		return plat;
	}
	
	private void checkPlats(Plat plat) throws PlatException {
		//les if sont à déterminer !
		}
	
	public void update(Plat plat) throws PlatException {
		checkPlats(plat);
		
		PlatDAO dao = new PlatDAO();
		dao.update(plat);
	}
	
	public void delete (int id) {
		PlatDAO dao = new PlatDAO();
		dao.delete(id);
	}
}
