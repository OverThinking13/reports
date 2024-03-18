package app.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;

public final class Settings {
    private static final Settings instance = new Settings();
    private static final Preferences prefs = Preferences.userRoot().node("tomcat.webapp.reports");
    private static String login = "admin";
    private static String password = "admin";
    private static String address = "test";
    private static Path pathCatalogs;
    private static Path pathHolidays;
    private static long updateFrequency = 60 * 60 * 1000L;
    private static int redDay = 2;
    private static String redDayColor = "#f44336";
    private static int yellowDay = 5;
    private static String yellowDayColor = "#ff9800";
    private static String greenDayColor = "#4CAF50";
    private final FilePetition formed = new FilePetition();
    private final FilePetition approved = new FilePetition();

    private Settings() {
    }

    public static Settings getInstance() {
        return instance;
    }

    public FilePetition getFormed() {
        return formed;
    }

    public FilePetition getApproved() {
        return approved;
    }


    public class FilePetition {
        private Date fileTime = new Date(0);
        private Path pathPetitions;

        public Path getPathPetitions() {
            if (pathPetitions != null)
                setFileTime(pathPetitions);
            return pathPetitions;
        }

        public Calendar getFileTime() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fileTime);
            return calendar;
        }

        private void setFileTime(Path file) {
            try {
                fileTime = new Date(Files.readAttributes(file, BasicFileAttributes.class).lastModifiedTime().toMillis());
            } catch (IOException e) {
                fileTime = new Date(0);
            }
        }

        public void setPathPetitions(Path pathPetitions) {
            this.pathPetitions = pathPetitions;
        }

        public boolean checkUpdate() {
            long now = new Date().getTime();
            return now > fileTime.getTime() + updateFrequency;
        }
    }


    public static int getRedDay() {
        return redDay;
    }

    public static void setRedDay(int redDay) {
        Settings.redDay = redDay;
    }

    public static String getRedDayColor() {
        return redDayColor;
    }

    public static void setRedDayColor(String redDayColor) {
        Settings.redDayColor = redDayColor;
    }

    public static int getYellowDay() {
        return yellowDay;
    }

    public static void setYellowDay(int yellowDay) {
        Settings.yellowDay = yellowDay;
    }

    public static String getYellowDayColor() {
        return yellowDayColor;
    }

    public static void setYellowDayColor(String yellowDayColor) {
        Settings.yellowDayColor = yellowDayColor;
    }

    public static String getGreenDayColor() {
        return greenDayColor;
    }

    public static void setGreenDayColor(String greenDayColor) {
        Settings.greenDayColor = greenDayColor;
    }

    public static void setPathCatalogs(Path pathCatalogs) {
        Settings.pathCatalogs = pathCatalogs;
    }

    public static void setPathHolidays(Path pathHolidays) {
        Settings.pathHolidays = pathHolidays;
    }

    public static Path getPathCatalogs() {
        return pathCatalogs;
    }


    public static Path getPathHolidays() {
        return pathHolidays;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Settings.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Settings.password = password;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        Settings.address = address;
    }


    public static long getUpdateFrequency() {
        return updateFrequency / 60 / 1000;
    }

    public static void setUpdateFrequency(long updateFrequency) {
        if (updateFrequency == 0)
            Settings.updateFrequency = 1000L;
        else
            Settings.updateFrequency = updateFrequency * 60 * 1000;
    }


    public boolean setPaths() {

        if (formed.getPathPetitions() == null || !Files.isRegularFile(formed.getPathPetitions()))
            return false;

        if (approved.getPathPetitions() == null || !Files.isRegularFile(approved.getPathPetitions()))
            return false;

        if (pathCatalogs != null) {
            if (!Files.isRegularFile(pathCatalogs)) {
                Path path = Paths.get(formed.getPathPetitions().getParent() + File.separator + "petitionsDeadline.csv");
                if (!Files.isRegularFile(path)) {
                    try {
                        Files.createFile(path);
                    } catch (IOException e) {
                        return false;
                    }
                }
                pathCatalogs = path;
            }
        } else
            return false;


        if (pathHolidays != null) {
            if (!Files.isRegularFile(pathHolidays)) {
                Path path = Paths.get(formed.getPathPetitions().getParent() + File.separator + "holidays.csv");
                if (!Files.isRegularFile(path)) {
                    try {
                        Files.createFile(path);
                    } catch (IOException e) {
                        return false;
                    }
                }
                pathHolidays = path;
            }
        } else
            return false;


        return true;
    }

    public  boolean read() {
        String temp = prefs.get("login", "");
        if (temp.isEmpty())
            return false;
        else
            login = temp;

        temp = prefs.get("password", "");
        if (temp.isEmpty())
            return false;
        else
            password = temp;

        temp = prefs.get("address", "");
        if (temp.isEmpty())
            return false;
        else
            address = temp;

        temp = prefs.get("petitions_formed", "");
        if (temp.isEmpty())
            return false;
        else
            formed.setPathPetitions(Paths.get(temp));

        temp = prefs.get("petitions_approved", "");
        if (temp.isEmpty())
            return false;
        else
            approved.setPathPetitions(Paths.get(temp));

        pathCatalogs = Paths.get(prefs.get("catalogs", ""));
        pathHolidays = Paths.get(prefs.get("holidays", ""));

        temp = prefs.get("update_frequency", "");
        if (!temp.isEmpty())
            setUpdateFrequency(Long.parseLong(temp));//!!!

        temp = prefs.get("red_day", "");
        if (!temp.isEmpty())
            redDay = Integer.parseInt(temp);

        temp = prefs.get("red_day_color", "");
        if (!temp.isEmpty())
            redDayColor = temp;

        temp = prefs.get("yellow_day", "");
        if (!temp.isEmpty())
            yellowDay = Integer.parseInt(temp);

        temp = prefs.get("yellow_day_color", "");
        if (!temp.isEmpty())
            yellowDayColor = temp;

        temp = prefs.get("green_day_color", "");
        if (!temp.isEmpty())
            greenDayColor = temp;

        return true;
    }

    public void write() {
        prefs.put("login", login);
        prefs.put("password", password);
        prefs.put("address", address);
        prefs.put("petitions_formed", formed.getPathPetitions().toString());
        prefs.put("catalogs", pathCatalogs.toString());
        prefs.put("holidays", pathHolidays.toString());
        prefs.put("petitions_approved", approved.getPathPetitions().toString());
        prefs.put("update_frequency", String.valueOf(getUpdateFrequency()));
        prefs.put("red_day", String.valueOf(redDay));
        prefs.put("red_day_color", redDayColor);
        prefs.put("yellow_day", String.valueOf(yellowDay));
        prefs.put("yellow_day_color", yellowDayColor);
        prefs.put("green_day_color", greenDayColor);
    }

    public static String setDayColor(int day, boolean isFormed) {
        String teg = "<font color=\"%s\">%s</font>";
        if (!isFormed)
            return String.format(teg, redDayColor, day);

        if (day <= redDay)
            return String.format(teg, redDayColor, day);
        else if (day <= yellowDay)
            return String.format(teg, yellowDayColor, day);
        else
            return String.format(teg, greenDayColor, day);
    }
}