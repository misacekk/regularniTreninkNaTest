import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("trenink_objednavky_testovaci_data.txt");
        List<Objednavky> platneObjednavky = new ArrayList<>();

        Map<String, Integer> kusyPodleProdukticku = new LinkedHashMap<>();
        kusyPodleProdukticku.put("Herní myš", 0);
        kusyPodleProdukticku.put("Klávesnice", 0);
        kusyPodleProdukticku.put("Monitor", 0);
        kusyPodleProdukticku.put("Grafická karta", 0);

        List<String> radky;
        try {
            radky = Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Chyba při čtení souboru: " + e.getMessage());
            return;
        }

        System.out.println("=== ZPRACOVÁNÍ OBJEDNÁVEK ===\n");

        for (int i = 0; i < radky.size(); i++) {
            String radek = radky.get(i);
            int cisloRadku = i + 1;

            List<String> chyby = ObjednavkyValidator.validuj(radek);

            if (chyby.isEmpty()) {
                String[] casti = radek.split(";");
                String jmeno = casti[0].trim();
                String email = casti[1].trim();
                String produkt = casti[2].trim();
                double cena = Double.parseDouble(casti[3].trim().replace(',', '.'));
                int pocetKusu = Integer.parseInt(casti[4].trim());

                Objednavky objednavka = new Objednavky(jmeno, email, produkt, cena, pocetKusu);
                platneObjednavky.add(objednavka);

                kusyPodleProdukticku.put(produkt, kusyPodleProdukticku.get(produkt) + pocetKusu);

                System.out.printf("Řádek %2d: OK [%s - %s]%n", cisloRadku, jmeno, produkt);
            } else {
                System.out.printf("Řádek %2d: NEPLATNÝ%n", cisloRadku);
                for (String chyba : chyby) {
                    System.out.println("          - " + chyba);
                }
            }
        }

        double celkovaCena = 0.0;
        for (Objednavky o : platneObjednavky) {
            celkovaCena += o.getCelkovaCena();
        }

        System.out.println("\n========================================");
        System.out.println("=== SOUHRNNÉ VÝSLEDKY ===");
        System.out.println("========================================");
        System.out.printf("Celková cena platných objednávek: %.2f Kč%n%n", celkovaCena);
        System.out.println("Počet objednaných kusů podle produktů:");

        for (Map.Entry<String, Integer> entry : kusyPodleProdukticku.entrySet()) {
            System.out.printf(" - %-15s: %d ks%n", entry.getKey(), entry.getValue());
        }
    }
}