public class Stock {
    private int quantite;

    public void entrer(int n) {
        quantite += n;
    }

    public void sortir(int n) {
        quantite -= n;
    }

    public int quantite() {
        return quantite;
    }
}
