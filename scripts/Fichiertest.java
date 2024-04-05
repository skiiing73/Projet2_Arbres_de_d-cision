package scripts;

class Fichiertest {
    public static void main(String[] args) {
        DonneesFichier fichier_golf = new DonneesFichier("data/golf.csv");
        fichier_golf.lire_donnees();
        System.out.println(fichier_golf.getAttributs_name());
        System.out.println(fichier_golf.getPossible_values());
        System.out.println(fichier_golf.getData());
    }
}