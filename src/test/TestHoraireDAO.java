package test;

import java.time.LocalDate;
import java.util.List;

import bo.Horaire;
import dal.HoraireDAO;

public class TestHoraireDAO {
    public static void main(String[] args) {
        HoraireDAO dao = new HoraireDAO();

        // Insert a new horaire
        Horaire newHoraire = new Horaire("Lundi", LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 10), 1);
        dao.insert(newHoraire);

        // Select all horaires
        List<Horaire> horaires = dao.select();
        for (Horaire horaire : horaires) {
            System.out.println(horaire);
        }

        // Select horaires by restaurantId
        List<Horaire> horairesByRestaurant = dao.selectByRestaurantId(1);
        for (Horaire horaire : horairesByRestaurant) {
            System.out.println(horaire);
        }

        // Update an existing horaire
        if (!horaires.isEmpty()) {
            Horaire horaireToUpdate = horaires.get(0);
            horaireToUpdate.setJour("Mardi");
            dao.update(horaireToUpdate);
        }

        // Delete an existing horaire
        if (!horaires.isEmpty()) {
            Horaire horaireToDelete = horaires.get(0);
            dao.delete(horaireToDelete.getId());
        }
    }
}
