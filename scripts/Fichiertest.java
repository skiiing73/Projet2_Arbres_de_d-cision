package scripts;

class Fichiertest {
    public static void main(String[] args) {
        DonneesFichier fichier_golf = new DonneesFichier("data/golf.csv");
        fichier_golf.lire_donnees();
        fichier_golf.set_entropie();

        fichier_golf.set_gain_attributs();
        System.out.println(fichier_golf.getgain_attributs());
    }
}