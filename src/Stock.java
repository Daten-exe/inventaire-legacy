public class Stock {
    private int quantite;

    public void entrer(int n) {
        quantite += n;
    }

    public void sortir(int n) {
        if (n > quantite) { throw new IllegalArgumentException("stock insuffisant"); }
        quantite -= n;
    }

    public int quantite() {
        return quantite;
    }
}
