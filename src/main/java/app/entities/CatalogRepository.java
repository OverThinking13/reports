package app.entities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class CatalogRepository {
    private CatalogRepository() {
    }

    public static void add(List<Petition> petitions, Map<Integer, Catalog> catalogs) {
        for (Petition petition : petitions) {
            Catalog catalog = new Catalog(petition.getMspId(), petition.getMspName(), petition.getCategoryId(),
                    petition.getCategoryName());
            int hash = Objects.hash(petition.getMspId(), petition.getCategoryId());
            if (!catalogs.containsKey(hash))
                catalogs.put(hash, catalog);
        }
    }

    public static boolean readCatalog(Path pathCatalog, Map<Integer, Catalog> catalogs) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(pathCatalog), StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                String[] values = reader.readLine().split(";");
                Catalog catalog = new Catalog(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]),
                        values[3], Boolean.parseBoolean(values[4]), Integer.parseInt(values[5]));
                catalogs.put(catalog.hashCode(), catalog);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean writeCatalog(Map<Integer, Catalog> catalogs, Path pathCatalog) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(pathCatalog), StandardCharsets.UTF_8))) {
            for (Map.Entry<Integer, Catalog> integer : catalogs.entrySet()) {
                writer.write(catalogs.get(integer.getKey()).toString() + '\n');
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
