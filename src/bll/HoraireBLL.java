package bll;

import java.time.LocalDate;
import java.util.List;

import bo.Horaire;
import dal.HoraireDAO;
import exceptions.HoraireException;

public class HoraireBLL {
    private HoraireDAO dao = new HoraireDAO();
	
	public List<Horaire> select() {
		return dao.select();
	}
	
    public List<Horaire> selectByRestaurantId(int restaurantId) {
    	return dao.selectByRestaurantId(restaurantId);
    }

	public Horaire insert(String jour, LocalDate ouverture, LocalDate fermeture, int idRestaurant) throws Exception {
		Horaire horaire = new Horaire(jour, ouverture, fermeture, idRestaurant);
		checkHoraire(horaire);
		
		HoraireDAO dao = new HoraireDAO();
		dao.insert(horaire);
		
		return horaire;
	}
	
	private void checkHoraire(Horaire horaire) throws HoraireException {
		// les if sont à déterminer !
		}
	
	public void update(Horaire horaire) throws HoraireException {
		checkHoraire(horaire);
		
		HoraireDAO dao = new HoraireDAO();
		dao.update(horaire);
	}
	
	public void delete (int id) {
		HoraireDAO dao = new HoraireDAO();
		dao.delete(id);
	}
}