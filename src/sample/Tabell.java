package sample;

/**
 * Created by ellenbakksjo on 07.03.2017.
 */

public class Tabell {

    public static class INSERT {

        private static final String TODO = "INSERT INTO ";

        public static String INNENDØRSØVELSE(int øvelsesID, String luftventilasjon, int antallTilskuere) {
            return TODO + "Innendørsøvelse " + values(valueOf(øvelsesID), valueOf(luftventilasjon), valueOf(antallTilskuere));
        }

        public static String MÅL(String tidsperiode, String beskrivelse, int øvelsesID) {
            return TODO + "Mål " + values("DEFAULT", valueOf(tidsperiode), valueOf(beskrivelse), valueOf(øvelsesID));
        }

        public static String RESULTAT(int resultatNr, String besteResultat, String dato, int øvelsesID) {
            return TODO + "Resultat " + values(valueOf(resultatNr), valueOf(besteResultat), valueOf(dato), valueOf(øvelsesID));
        }

        public static String STYRKE_OG_KONDISJON(int øvelsesID, int belastning, int antallRep, int antallSett) {
            return TODO + "StyrkeOgKondisjon " + values(valueOf(øvelsesID), valueOf(belastning), valueOf(antallRep), valueOf(antallSett));
        }

        public static String TRENINGSØKT(String dato, String tidspunkt, int varighet, int personligForm, int prestasjon, String notat) {
            return TODO + "Treningsøkt " + values("DEFAULT" , valueOf(dato), valueOf(tidspunkt), valueOf(varighet), valueOf(personligForm), valueOf(prestasjon), valueOf(notat));
        }

        public static String UTENDØRSØVELSE(int øvelsesID, int temperatur, String værtype) {
            return TODO + "Utendørsøvelse " + values(valueOf(øvelsesID), valueOf(temperatur), valueOf(værtype));
        }

        public static String UTHOLDENHET(int øvelsesID, int lengdeKm, int minutter) {
            return TODO + "Utholdenhet " + values(valueOf(øvelsesID), valueOf(lengdeKm), valueOf(minutter));
        }

        public static String ØVELSE(String navn, String beskrivelse, String gruppe) {
            return TODO + "Øvelse " + values("DEFAULT" , valueOf(navn), valueOf(beskrivelse), valueOf(gruppe));
        }
        public static String BESTÅR_AV(int treningsid, int øvelsesid) {
            return TODO + "BestårAv " + values(valueOf(treningsid), valueOf(øvelsesid));
        }

        public static String ERSTATTES_AV(int øvelsesid_en, int øvelsesid_to){
            return TODO + "ErstattesAv " + values(valueOf(øvelsesid_en), valueOf(øvelsesid_to));
        }

    }

    public static class SELECT {

        private static final String TODO = "SELECT * FROM ";

        public static String ERSTATTES_AV_INSERT(String group){
            return "SELECT ØvelsesID, Navn FROM Øvelse WHERE Gruppe = " + valueOf(group) + ";";
        }

        public static String ERSTATTES_AV(){
            return "SELECT Navn FROM " + "ErstattesAv";
        }

        public static String INNENDØRSØVELSE() {
            return TODO + "Innendørsøvelse";
        }

        public static String MÅL() {
            return TODO + "Mål";
        }

        public static String RESULTAT() {
            return TODO + "Resultat";
        }

        public static String STYRKE_OG_KONDISJON() {
            return TODO + "StyrkeOgKondisjon";
        }

        public static String TRENINGSØKT() {
            return TODO + "Treningsøkt";
        }

        public static String UTENDØRSØVELSE() {
            return TODO + "Utendørsøvelse";
        }

        public static String UTHOLDENHET() {
            return TODO + "Utholdenhet";
        }

        public static String ØVELSE() {
            return TODO + "Øvelse";
        }
    }

    private static String valueOf(String value) {
        return "'" + value + "'";
    }

    private static String valueOf(int value) {
        return String.valueOf(value);
    }

    private static String values(String... values) {
        StringBuilder statement = new StringBuilder();

        for (String value : values) {
            if (statement.length() == 0) {
                statement.append("VALUES(");
            }
            else {
                statement.append(", ");
            }
            statement.append(value);
        }

        statement.append(");");
        return statement.toString();
    }

}
