package app.entities;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Set;

public final class HolidayRepository {

    private HolidayRepository() {
    }

    public static boolean readHoliday(Path pathHoliday, Set<Holiday> holidays) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(pathHoliday), StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                String[] line = reader.readLine().split(";");
                holidays.add(new Holiday(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2])));
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean isWorkingDay(Calendar date, Set<Holiday> holidays) {
        Holiday checkHoliday = new Holiday(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH));
        return !holidays.contains(checkHoliday);
    }

    public static boolean writeHolidays(Set<Holiday> holidays, Path pathHoliday) {
        try (BufferedWriter writer = Files.newBufferedWriter(pathHoliday, Charset.defaultCharset())) {
            for (Holiday holiday : holidays)
                writer.write(String.format("%s;%s;%s%n", holiday.getYear(), holiday.getMonth(), holiday.getDay()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void addHoliday(int year, int month, int day, Set<Holiday> holidays) {
        holidays.add(new Holiday(year, month, day));
    }
}
