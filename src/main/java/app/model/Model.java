package app.model;

import app.entities.*;
import java.util.*;

public class Model {
    private static List<Petition> petitionsFormed;
    private static List<Petition> petitionsApproved;
    private static Map<Integer, Catalog> catalogs;
    private static Set<Holiday> holidays;
    private static State state;
    private static Event event;
    private static final Settings settings = Settings.getInstance();

    private Model(){
    }

    static {
        init();
    }

    public static void init() {
        event = Event.READ_CONFIG;
        catalogs = new HashMap<>();
        holidays = new TreeSet<>();
        petitionsApproved = new ArrayList<>();
        petitionsFormed = new ArrayList<>();

        if(!readConfig())
            return;

        if (!settings.setPaths())
            return;

        state=State.COMPLETE;
    }

    public static State getState() {
        return state;
    }

    public static Event getEvent() {
        return event;
    }


    public static synchronized List<Petition> getPetitionsFormed() {
        switch (event) {
            case READ_CONFIG:

                if (!readSecondFiles())
                    break;

                if (!updateFormed())
                    break;
                if (!updateApproved())
                    break;

                state = State.COMPLETE;

                return petitionsFormed;

            case READ_HOLIDAYS:
            case WRITE_HOLIDAYS:
            case WRITE_CATALOGS:
            case COUNT:
                if (settings.getFormed().checkUpdate()) {
                    updateFormed();
                }

                return petitionsFormed;

            default:
                state = State.ERROR;
                return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public static synchronized List<Petition> getPetitionsApproved() {
        switch (event) {
            case READ_CONFIG:

                if (!readSecondFiles())
                    break;

                if (!updateFormed())
                    break;

                if (!updateApproved())
                    break;

                state = State.COMPLETE;

                return petitionsApproved;

            case READ_HOLIDAYS:
            case WRITE_HOLIDAYS:
            case WRITE_CATALOGS:
            case COUNT:
                if (settings.getApproved().checkUpdate()) {
                    updateApproved();
                }
                return petitionsApproved;

            default:
                state = State.ERROR;
                return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public static boolean updateFormed() {
        event = Event.READ_PETITIONS_FORMED;
        petitionsFormed.clear();
        if (!PetitionRepository.readPetition(settings.getFormed().getPathPetitions(), petitionsFormed))
            return false;

        CatalogRepository.add(petitionsFormed, catalogs);

        event = Event.WRITE_CATALOGS;
        if (!CatalogRepository.writeCatalog(catalogs, Settings.getPathCatalogs()))
            return false;

        event = Event.COUNT;
        Counter.countDays(catalogs, petitionsFormed, holidays, true);
        return true;
    }

    public static boolean updateApproved() {
        event = Event.READ_PETITIONS_APPROVED;
        petitionsApproved.clear();
        if (!PetitionRepository.readPetition(settings.getApproved().getPathPetitions(), petitionsApproved))
            return false;

        CatalogRepository.add(petitionsApproved, catalogs);

        event = Event.WRITE_CATALOGS;
        if (!CatalogRepository.writeCatalog(catalogs, Settings.getPathCatalogs()))
            return false;

        event = Event.COUNT;
        Counter.countDays(catalogs, petitionsApproved, holidays, false);
        return true;
    }


    public static Map<Integer, Catalog> getCatalogs() {
        switch (event) {
            case READ_CONFIG:
                if (!readConfig())
                    break;

                if (!settings.setPaths())
                    break;

                if (!readSecondFiles())
                    break;

                state = State.COMPLETE;
                return catalogs;
            case READ_HOLIDAYS:
            case WRITE_HOLIDAYS:
            case WRITE_CATALOGS:
            case COUNT:
                return catalogs;
            default:
                state = State.ERROR;
                return new HashMap<>();
        }
        return new HashMap<>();
    }


    public static Set<Holiday> getHolidays() {
        switch (event) {
            case READ_CONFIG:
                if (!readConfig())
                    break;

                if (!settings.setPaths())
                    break;

                if (!readSecondFiles())
                    break;

                state = State.COMPLETE;
                return holidays;
            case COUNT:
            case WRITE_CONFIG:
            case WRITE_HOLIDAYS:
            case WRITE_CATALOGS:
            case READ_CATALOGS:
            case READ_HOLIDAYS:
                writeHolidays();
                return holidays;
            default:
                state = State.ERROR;
                return new TreeSet<>();
        }

        return new TreeSet<>();
    }

    public static void writeCatalogs(){
        event = Event.WRITE_CATALOGS;
        if (!CatalogRepository.writeCatalog(catalogs, Settings.getPathCatalogs()))
            state=State.ERROR;
    }

    public static void writeHolidays() {
        event = Event.WRITE_HOLIDAYS;
        if (!HolidayRepository.writeHolidays(holidays, Settings.getPathHolidays()))
            state = State.ERROR;
    }

    private static boolean readSecondFiles() {
        event = Event.READ_CATALOGS;
        if (!CatalogRepository.readCatalog(Settings.getPathCatalogs(), catalogs))
            return false;

        event = Event.READ_HOLIDAYS;
        return HolidayRepository.readHoliday(Settings.getPathHolidays(), holidays);
    }

    private static boolean readConfig() {
        event = Event.READ_CONFIG;
        return settings.read();
    }
}