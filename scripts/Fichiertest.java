package scripts;

import java.util.ArrayList;

class Fichiertest {
    public static void main(String[] args) {
        // affichage des données du fichier golf
        DonneesFichier fichier_golf = new DonneesFichier("data/golf_bis.csv");
        fichier_golf.lire_donnees();
        System.out.println(fichier_golf.getAttributs_name());
        System.out.println(fichier_golf.getPossible_values());
        System.out.println(fichier_golf.getData());

        // Nous choissisons de créer l'arbre a partir de données d'apprentissage qui
        // seront les 8 premieres données du fichier
        int nb_exemples = 10;
        fichier_golf.setdataapp(nb_exemples);

        // et donc les données de predictions qui seront les autres
        fichier_golf.setdatapred(nb_exemples);

        // calcul du gain des attributs
        fichier_golf.set_entropie();
        System.out.println("gain des attributs:");

        ArrayList<Float> gain_attributs = fichier_golf.set_gain_attributs(fichier_golf.getPossible_values(),
                fichier_golf.getAttributs_name(),
                fichier_golf.getData());
        System.out.println(gain_attributs);

        // creation de l'arbre id3 de décision
        Arbre arbre = new Arbre(fichier_golf);
        arbre.creer_arbre();
        arbre.afficherArbre(arbre.getRacine(), " ");

        // création et affichage de la matrice de confusion
        matrice_confusion matrice_apprentissage = new matrice_confusion(fichier_golf, arbre);
        matrice_apprentissage.calcul_coeff_apprentissage();
        System.out.println("\nMatrice de confusion d'apprentissage: \n" + matrice_apprentissage);

        matrice_confusion matrice_prediction = new matrice_confusion(fichier_golf, arbre);
        matrice_prediction.calcul_coeff_prediction();
        System.out.println("\nMatrice de confusion de prédiction: \n" + matrice_prediction);

    }

}