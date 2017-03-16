package sample;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    private static void setup(Controller controller) throws SQLException {
        try {
            // Register some distance-based endurance activities
            controller.addEnduranceActivity("10000 meter", "Løp 10000-meter", 10000, true);
            controller.addEnduranceActivity("5000 meter", "Løp 5000-meter", 5000, true);
            controller.addEnduranceActivity("2000 meter", "Løp 2000-meter", 2000, true);
            controller.addEnduranceActivity("1000 meter", "Løp 1000-meter", 1000, true);

            controller.addActivityToCategory("10000 meter", "Jogging");
            controller.addActivityToCategory("5000 meter", "Jogging");
            controller.addActivityToCategory("2000 meter", "Jogging");
            controller.addActivityToCategory("1000 meter", "Jogging");

            // Register some strength activities
            controller.addStrengthOrConditionActivity(
                    "5x10 Pushups", "Ta 5 sett à 10 pushups", "Kroppsvekt", 10, 5
            );
            controller.addStrengthOrConditionActivity(
                    "5x15 Pushups", "Ta 5 sett à 15 pushups", "Kroppsvekt", 15, 5
            );
            controller.addStrengthOrConditionActivity(
                    "5x20 Pushups", "Ta 5 sett à 20 pushups", "Kroppsvekt", 20, 5
            );
            controller.addStrengthOrConditionActivity(
                    "5x25 Pushups", "Ta 5 sett à 25 pushups", "Kroppsvekt", 25, 5
            );

            controller.addActivityToCategory("5x10 Pushups", "Pushups");
            controller.addActivityToCategory("5x15 Pushups", "Pushups");
            controller.addActivityToCategory("5x20 Pushups", "Pushups");
            controller.addActivityToCategory("5x25 Pushups", "Pushups");

            // Register some other random inside activities
            controller.addStrengthOrConditionActivity(
                    "5x20x50kg Squats", "Ta 5 sett à 20 squats med 50kg på stangen",
                    "50kg", 20, 5
            );
            controller.addStrengthOrConditionActivity(
                    "5x10x15kg Bicep Curls", "Ta 5 sett à 10 bicep curls (per arm) med 15kg-manualer",
                    "15kg", 10, 5
            );

            controller.addActivityToCategory("5x20x50kg Squats", "Squats");
            controller.addActivityToCategory("5x10x15kg Bicep Curls", "Bicep Curls");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) throws SQLException {
        Controller controller = new Controller();
        try {
            // Setup
            //setup(controller);

            // Register an inside exercise session
            int oktid = controller.addInsideSession(
                    LocalDateTime.now(), 45, 8, 10,
                    "Kort trening, men var skikkelig i slaget i dag. Økt max i alle øvelser!",
                    10, 150000
            );
            controller.addActivityToSession("5x25 Pushups", oktid);
            controller.addActivityToSession("5x20x50kg Squats", oktid);
            controller.addActivityToSession("5x10x15kg Bicep Curls", oktid);

            // Register an outside exercise session with gps data
            int oktid2 = controller.addOutsideSession(
                    LocalDateTime.now(), 22, 8, 10,
                    "Jogga en tur på banen, fin og lett i formen. God performance!",
                    22, "Sol med noen skyer"
            );
            controller.addActivityToSession("5000 meter", oktid2);
            controller.addEnduranceResult(oktid2, "5000 meter", 1287);
            controller.addGpsToSession(
                    "5000 meter", oktid2, LocalDateTime.now().minusMinutes(36),
                    139, 63.426413, 10.378880, 7
            );
            controller.addGpsToSession(
                    "5000 meter", oktid2, LocalDateTime.now().minusMinutes(27),
                    148, 63.426745, 10.380942, 7
            );

            // Find data about all activities in a session
            ResultSet sessionActivities = controller.getAllSessionActivities(oktid);
            System.out.println("Aktiviteter i økt 1:\n");
            while (sessionActivities.next()) {
                System.out.println(sessionActivities.getString(1) + " - " + sessionActivities.getString(2));
            }

            // Show all notes
            ArrayList<String> notes = controller.getAllSessionNotes();
            System.out.println("\n\nNotater\n");
            for (String note : notes) {
                System.out.println(note);
            }

            // Show Gps tracking of a single activity in a session
            ResultSet sessionActivityGps = controller.getAllGpsOfSessionActivity(oktid2, "5000 meter");
            System.out.println("\n\nGPS-koordinater for aktivitet i økt:\n");
            while (sessionActivityGps.next()) {
                System.out.println(
                                "Tidspunkt: " + sessionActivityGps.getString(1) +
                                ", puls: " + sessionActivityGps.getString(2) +
                                ", lengdegrad: " + sessionActivityGps.getString(3) +
                                ", breddegrad: " + sessionActivityGps.getString(4) +
                                ", moh: " + sessionActivityGps.getString(5)
                );
            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }
}
