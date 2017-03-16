package sample;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

class Controller {
    private Connection conn;

    Controller() {
        String dbURL = "jdbc:mysql://mysql.stud.ntnu.no:3306/martsime_databaseprosjekt";
        String username = "martsime";
        String password = "Orakelerbest123";

        try {

            conn = DriverManager.getConnection(dbURL, username, password);

            if (conn != null) {
                System.out.println("Connected");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void addCategory(String name, Integer parent) throws SQLException {
        if (this.categoryExists(name)) {
            throw new SQLException("Denne kategorien eksisterer allerede");
        }

        String sql = "INSERT INTO Kategori (navn, forelder) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);

        if (parent == null) {
            statement.setString(1, name);
            statement.setNull(2, Types.INTEGER);
        } else {
            statement.setString(1, name);
            statement.setInt(2, parent);
        }

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("En ny kategori ble laget!");
        } else {
            throw new SQLException("Kunne ikke lage kategori!");
        }

    }


    private boolean queryForExistence(String sql, String name) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);

        final ResultSet resultSet = statement.executeQuery();
        int count = 0;
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count > 0;
    }


    boolean categoryExists(String name) throws SQLException {
        String sql = "SELECT Count(*) FROM Kategori WHERE navn=?";
        return queryForExistence(sql, name);
    }


    private int addSession(
            LocalDateTime startTime, Integer duration, Integer shape, Integer performance, String description
    ) throws SQLException {
        String sql = "INSERT INTO Okt (tidspunkt, varighet, form, prestasjon, notat, er_maal) VALUES (?, ?, ?, ?, ?, NULL)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setTimestamp(1, Timestamp.valueOf(startTime));
        statement.setInt(2, duration);
        statement.setInt(3, shape);
        statement.setInt(4, performance);
        statement.setString(5, description);

        statement.executeUpdate();

        int oktid;
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                oktid = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Ingen økt ble laget");
            }
        }
        return oktid;
    }


    void addInsideSession(
            LocalDateTime startTime, Integer duration, Integer shape, Integer performance, String description,
            Integer temperature, String weather
    ) throws SQLException {
        int oktid = addSession(startTime, duration, shape, performance, description);

        String sql = "INSERT INTO Innendors (oktid, temp, vær) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, oktid);
        statement.setInt(2, temperature);
        statement.setString(3, weather);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("En ny innendørs økt ble lagret!");
        } else {
            throw new SQLException("Ingen innendørs økt ble lagret!");
        }
    }


    void addOutsideSession(
            LocalDateTime startTime, Integer duration, Integer shape, Integer performance, String description,
            Integer airQuality, Integer spectators
    ) throws SQLException {
        int oktid = addSession(startTime, duration, shape, performance, description);

        String sql = "INSERT INTO Utendors (oktid, luftkvalitet, tilskuere) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, oktid);
        statement.setInt(2, airQuality);
        statement.setInt(3, spectators);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("En ny utendørs økt ble lagret!");
        } else {
            throw new SQLException("Ingen utendørs økt ble lagret!");
        }
    }


    void makeSessionTemplate(Integer sessionID, String name) throws SQLException {
        String sql = "INSERT INTO Mal (oktid, navn) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, sessionID);
        statement.setString(2, name);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Økten ble gjort til en mal!");
        } else {
            throw new SQLException("Økten kunne ikke gjøres til en mal!");
        }
    }


    private int addActivity(String name, String description) throws SQLException {
        if (this.activityExists(name)) {
            throw new SQLException("Denne aktiviteten eksisterer allerede");
        }

        String sql = "INSERT INTO Ovelse (navn, beskrivelse) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setString(2, description);

        statement.executeUpdate();

        int ovid;
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                ovid = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Ingen økt ble laget");
            }
        }
        return ovid;
    }


    boolean activityExists(String name) throws SQLException {
        String sql = "SELECT Count(*) FROM Ovelse WHERE navn=?";
        return queryForExistence(sql, name);
    }


    void addActivityToCategory(Integer activityID, Integer categoryID) throws SQLException {
        String sql = "INSERT INTO OvelseKategori (ovid, katid) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, activityID);
        statement.setInt(2, categoryID);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Øvelsen ble satt inn i kategorien!");
        } else {
            throw new SQLException("Øvelsen kunne ikke settes inn i kategorien!");
        }
    }


    void addActivityToSession(Integer activityID, Integer sessionID) throws SQLException {
        String sql = "INSERT INTO OvelseUtfort (oktid, ovid) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(2, activityID);
        statement.setInt(1, sessionID);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Øvelsen ble satt inn i økten!");
        } else {
            throw new SQLException("Øvelsen kunne ikke settes inn i økten!");
        }
    }


    void addGpsToSession(
            Integer activityID, Integer sessionID, LocalDateTime time, Integer pulse,
            Double latitude, Double longitude, Integer height
    ) throws SQLException {
        String sql = "INSERT INTO Pulsgps (oktid, ovid, tid, puls, lengdegrad, breddegrad, hoyde) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, sessionID);
        statement.setInt(2, activityID);
        statement.setTimestamp(3, Timestamp.valueOf(time));
        statement.setInt(4, pulse);
        statement.setDouble(5, latitude);
        statement.setDouble(6, longitude);
        statement.setInt(7, height);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("GPS-koordinater registrert!");
        } else {
            throw new SQLException("GPS-koordinater kunne ikke registreres!");
        }
    }


    void addActivityRelation(Integer activityID1, Integer activityID2) throws SQLException {
        String sql = "INSERT INTO RelatertOvelse (ovid1, ovid2) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, activityID1);
        statement.setInt(2, activityID2);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Relasjon mellom aktiviteter registrert!");
        } else {
            throw new SQLException("Relasjon mellom aktiviteter kunne ikke registreres!");
        }
    }


    void addEnduranceResult(Integer sessionID, Integer activityID, Integer value) throws SQLException {
        String sql = "INSERT INTO Resultat (oktid, ovid, verdi) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, sessionID);
        statement.setInt(2, activityID);
        statement.setInt(3, value);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Resultat av utholdenhetsøvelse registrert!");
        } else {
            throw new SQLException("Resultat av utholdenhetsøvelse kunne ikke registreres!");
        }
    }


    void addStrengthOrConditionActivity(String name, String description, String load, Integer reps, Integer sets) throws SQLException {
        int ovid = addActivity(name, description);

        String sql = "INSERT INTO StyrkeKondis (ovid, belastning, reps, sett) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, ovid);
        statement.setString(2, load);
        statement.setInt(3, reps);
        statement.setInt(4, sets);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Styrke/kondisjonsøvelse registrert!");
        } else {
            throw new SQLException("Styrke/kondisjonsøvelse kunne ikke registreres!");
        }
    }


    void addEnduranceActivity(String name, String description, Integer value, boolean isDistance) throws SQLException {
        int ovid = addActivity(name, description);

        String sql = "INSERT INTO Utholdenhet (ovid, verdi, is_lengde) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, ovid);
        statement.setInt(2, value);
        statement.setBoolean(3, isDistance);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Utholdenhetsøvelse registrert!");
        } else {
            throw new SQLException("Utholdenhetsøvelse kunne ikke registreres!");
        }
    }

    ArrayList<String> getAllSessionNotes() throws SQLException {
        String sql = "SELECT beskrivelse FROM Ovelse";
        PreparedStatement statement = conn.prepareStatement(sql);

        ResultSet commandResults = statement.executeQuery();
        ArrayList<String> results = new ArrayList<>();
        while (commandResults.next()) {
            results.add(commandResults.getString(1));
        }
        return results;
    }
}