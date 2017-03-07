package sample;

/**
 * Created by ellenbakksjo on 07.03.2017.
 */
public abstract class Exercise {

    protected int ovelsesID;
    protected static String name;
    protected static String description;
    protected static String group;
    protected static String goal;

    protected static int temp; //outdoor exercise
    protected static String weather; //outdoor exercise

    protected static String ventilation; //indoor exercise
    protected static int spectators; //indoor exercise


    public void setName(String name){
        this.name = name;
    }

    public void setGroup(String group){
        this.group = group;
    }

    public void setGoal(String goal){
        this.goal = goal;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setOvelsesID(int id){
        this.ovelsesID = id;
    }

    public int getOvelsesID() {
        return ovelsesID;
    }

    public String getDescription() {
        return description;
    }

    public static String getName() {
        return name;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setSpectators(int spectators) {
        this.spectators = spectators;
    }

    public void setVentilation(String ventilation) {
        this.ventilation = ventilation;
    }

    public static String getWeather() {
        return weather;
    }

    public static int getTemp() {
        return temp;
    }

    public static String getVentilation() {
        return ventilation;
    }

    public static int getSpectators() {
        return spectators;
    }

    public static String getGroup(){
        return group;
    }

    public static String getGoal(){
        return goal;
    }

    public void setOutdoor(int temp, String weather){
        this.temp = temp;
        this.weather = weather;
    }

    public void setIndoor(String ventilation, int spectators){
        this.ventilation = ventilation;
        this.spectators = spectators;
    }

}