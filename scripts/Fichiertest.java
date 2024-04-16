package scripts;

import java.util.ArrayList;

class Fichiertest {
    public static void main(String[] args) {
        DonneesFichier fichier_golf = new DonneesFichier("data/golf.csv");
        fichier_golf.lire_donnees();
        System.out.println(fichier_golf.getAttributs_name());
        System.out.println(fichier_golf.getPossible_values());
        System.out.println(fichier_golf.getData());
        fichier_golf.set_entropie();
        System.out.println("gain des attributs:");

        ArrayList<Float> gain_attributs = fichier_golf.set_gain_attributs(fichier_golf.getPossible_values(),
                fichier_golf.getAttributs_name(),
                fichier_golf.getData());
        System.out.println(gain_attributs);
        Arbre arbre = new Arbre(fichier_golf);
        arbre.creer_arbre();
        arbre.afficherArbre(arbre.getRacine(), " ");

    }
}