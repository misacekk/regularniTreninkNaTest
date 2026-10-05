import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ObjednavkyValidator {

    private static final List<String> POVOLE_PRODUKTY = Arrays.asList(
            "Herní myš", "Klávesnice", "Monitor", "Grafická karta"
    );

    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[A-ZÁČĎÉĚÍŇÓŘŠŤÚŮÝŽ][a-záčďéěíňóřšťúůýž]+\\s+[A-ZÁČĎÉĚÍŇÓŘŠŤÚŮÝŽ][a-záčďéěíňóřšťúůýž]+$"
    );

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern CENA_PATTERN = Pattern.compile("^\\d+,\\d{2}$");

    public static List<String> validuj(String radek) {
        List<String> chyby = new ArrayList<>();

        if (radek == null || radek.trim().isEmpty()) {
            chyby.add("Prázdný řádek.");
            return chyby;
        }

        String[] casti = radek.split(";", -1);

        if (casti.length != 5) {
            chyby.add("Nespravný počet polí (očekáváno 5, nalezeno " + casti.length + ").");
            return chyby;
        }

        String jmeno = casti[0].trim();
        String email = casti[1].trim();
        String produkt = casti[2].trim();
        String cenaStr = casti[3].trim();
        String pocetKusuStr = casti[4].trim();

        if (jmeno.isEmpty()) {
            chyby.add("Jméno: Pole je prázdné.");
        } else if (!NAME_PATTERN.matcher(jmeno).matches()) {
            chyby.add("Jméno: Musí obsahovat dvě slova začínající velkým písmenem (např. Jan Novák).");
        }

        if (email.isEmpty()) {
            chyby.add("Email: Pole je prázdné.");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            chyby.add("Email: Neplatný formát emailu.");
        }

        if (produkt.isEmpty()) {
            chyby.add("Produkt: Pole je prázdné.");
        } else if (!POVOLE_PRODUKTY.contains(produkt)) {
            chyby.add("Produkt: Neplatný název produktu.");
        }

        if (cenaStr.isEmpty()) {
            chyby.add("Cena: Pole je prázdné.");
        } else if (!CENA_PATTERN.matcher(cenaStr).matches()) {
            chyby.add("Cena: Musí mít formát s čárkou a 2 desetinnými místy (např. 2599,99).");
        } else {
            double cena = Double.parseDouble(cenaStr.replace(',', '.'));
            if (cena <= 0) {
                chyby.add("Cena: Musí být kladné číslo.");
            }
        }

        if (pocetKusuStr.isEmpty()) {
            chyby.add("Počet kusů: Pole je prázdné.");
        } else {
            try {
                int pocet = Integer.parseInt(pocetKusuStr);
                if (pocet < 1 || pocet > 10) {
                    chyby.add("Počet kusů: Musí být v rozmezí 1 až 10.");
                }
            } catch (NumberFormatException e) {
                chyby.add("Počet kusů: Musí být celé číslo.");
            }
        }

        return chyby;
    }
}