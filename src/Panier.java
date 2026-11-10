public class Panier {
    private double total;

    public void ajouter(double prixHt) {
        total += prixHt;
    }

    public double totalTtc() {
        return Math.round(total * 1.20 * 100) / 100.0;
    }

    public static void main(String[] args) {
        Panier panier = new Panier();
        panier.ajouter(10.0);
        panier.ajouter(5.5);
        System.out.println(panier.totalTtc());
    }
}
