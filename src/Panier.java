public class Panier {
    private double total;

    public void ajouter(double prixHt) {
        total += prixHt;
    }

    public double totalTtc() {
        return total * 1.20;
    }

    public static void main(String[] args) {
        Panier panier = new Panier();
        panier.ajouter(10.0);
        panier.ajouter(5.5);
        System.out.println(panier.totalTtc());
    }
}
