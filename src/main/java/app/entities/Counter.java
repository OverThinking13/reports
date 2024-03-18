package app.entities;

import java.util.*;

public class Counter {

    private Counter() {
    }

    public static void countDays(Map<Integer, Catalog> catalogs, List<Petition> petitions, Set<Holiday> holidays, boolean isFormed) {
        Calendar today = Calendar.getInstance();
        List<Petition> bufferForApproved = new ArrayList<>();
        for (Petition petition : petitions) {
            Catalog catalog;
            int hashKey = Objects.hash(petition.getMspId(),petition.getCategoryId());
            if (catalogs.containsKey(hashKey)) {
                catalog = catalogs.get(hashKey);
                if (catalog.isCalendar())
                    countCalendarDay(petition, catalog,holidays);
                else
                    countWorkingDay(petition, catalog, holidays);

                Date d1;
                Date d2;
                if (isFormed) {
                    d1 = petition.getFinalDate().getTime();
                    d2 = today.getTime();
                } else {
                    d1 = petition.getDateDone().getTime();
                    d2 = petition.getFinalDate().getTime();
                }
                int cnt = (int) ((d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));


                if (!isFormed && cnt > 0)
                    bufferForApproved.add(petition);

                petition.setDays(cnt);
            }
        }
        if (!isFormed) {
            petitions.clear();
            petitions.addAll(bufferForApproved);
        }
            Collections.sort(petitions);
    }


    private static void countCalendarDay(Petition petition, Catalog catalog,Set<Holiday> holidays) {
        petition.getFinalDate().add(Calendar.DAY_OF_YEAR, catalog.getDays()+1);

        while (!HolidayRepository.isWorkingDay(petition.getFinalDate(), holidays)) {
            petition.getFinalDate().add(Calendar.DAY_OF_YEAR, -1);
        }

    }

    private static void countWorkingDay(Petition petition, Catalog catalog, Set<Holiday> holidays) {
        if(catalog.getDays()==0)
            return;

        int workingDays = 0;

        if (HolidayRepository.isWorkingDay(petition.getFinalDate(), holidays))
            workingDays++;

        while (catalog.getDays() != workingDays) {
            petition.getFinalDate().add(Calendar.DAY_OF_YEAR, 1);
            if (HolidayRepository.isWorkingDay(petition.getFinalDate(), holidays))
                workingDays++;
        }
    }
}