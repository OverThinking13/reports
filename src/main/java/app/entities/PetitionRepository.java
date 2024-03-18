package app.entities;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class PetitionRepository {
    private PetitionRepository() {
    }

    public static String russianDate(Calendar date) {
        return new SimpleDateFormat("EEEE MMMM d H:m:s y",
                new Locale("ru")).format(date.getTime());
    }

    public static boolean readPetition(Path file, List<Petition> petitions) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file), "Windows-1251"))) {
            String line;
            while (reader.ready() && (line = reader.readLine()).length() > 5) {
                String[] values = line.split(";");
                int categoryId = -1;
                if (!values[2].equals("NULL"))
                    categoryId = Integer.parseInt(values[2]);
                Calendar date = Calendar.getInstance();
                Calendar finalDate = Calendar.getInstance();
                date.setTime(sdf.parse(values[5]));

                if (!values[6].equals("NULL"))
                    finalDate.setTime(sdf.parse(values[6]));

                //int msp_id, String msp_name, int category_id, String category_name, String link, Calendar date,Calendar finalDate
                petitions.add(new Petition(Integer.parseInt(values[0]), values[1], categoryId,
                        values[3], values[4], date, finalDate));
            }
        } catch (IOException | ParseException e) {
            return false;
        }
        return true;
    }

}
