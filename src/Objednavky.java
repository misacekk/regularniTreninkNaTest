public class Objednavky {
    private String jmeno;
    private String email;
    private String produkt;
    private double cena;
    private int pocetKusu;

    public Objednavky(String jmeno, String email, String produkt, double cena, int pocetKusu) {
        this.jmeno = jmeno;
        this.email = email;
        this.produkt = produkt;
        this.cena = cena;
        this.pocetKusu = pocetKusu;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getEmail() {
        return email;
    }

    public String getProdukt() {
        return produkt;
    }

    public double getCena() {
        return cena;
    }

    public int getPocetKusu() {
        return pocetKusu;
    }

    public double getCelkovaCena() {
        return cena * pocetKusu;
    }


    @Override
    public String toString() {
        return String.format("%s | %s | %s | %.2f Kč | %d ks",
                jmeno, email, produkt, cena, pocetKusu);
    }
}