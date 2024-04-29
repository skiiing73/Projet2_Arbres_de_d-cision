package scripts;

import java.util.ArrayList;

public class matrice_confusion {
    DonneesFichier donneesFichier;
    Arbre arbre;
    int truepositive = 0, truenegative = 0, falsepositive = 0, falsenegative = 0;

    public matrice_confusion(DonneesFichier donneesFichier, Arbre arbre) {
        this.donneesFichier = donneesFichier;
        this.arbre = arbre;
    }

    // permet de calculer les coefficients de la matrice d'apprentissage
    public void calcul_coeff_apprentissage() {
        ArrayList<ArrayList<String>> data_app = donneesFichier.getDataapp();// recupere les données d'apprentissage
        ArrayList<String> attributs_name = donneesFichier.getAttributs_name();
        int nb_attribut = data_app.get(0).size() - 1;
        // on parcours toutes les données
        for (ArrayList<String> donnees : data_app) {
            Noeud noeud = arbre.getRacine();

            // parcours de l'arbre pour la donnée en cours qui s'arrete quand la feuille est
            // oui ou non
            while (!noeud.getValue().equals("yes") && !noeud.getValue().equals("no")) {
                int index = 0;

                // parcours des attributs pour savoir la position de l'attribut dans la liste
                for (int i = 0; i < (attributs_name.size() - 1); i++) {
                    if (noeud.getValue() == attributs_name.get(i)) {
                        index = i;
                    }
                }
                // parcoursdes branches pour connaitre la valeur de l'attribut correspondant a
                // la branche
                for (Branche branche : noeud.getBranches()) {
                    if (branche.getValeur_branche().equals(donnees.get(index))) {
                        noeud = branche.getNoeud_arrive();
                    }
                }
            }
            String valeur_predite = noeud.getValue();
            // on compare la valeur que l'arbre a predit a la vraie valeur
            if (donnees.get(nb_attribut).equals("no") && valeur_predite.equals("yes")) {
                falsepositive++;
            }
            if (donnees.get(nb_attribut).equals("yes") && valeur_predite.equals("yes")) {
                truepositive++;
            }
            if (donnees.get(nb_attribut).equals("no") && valeur_predite.equals("no")) {
                truenegative++;
            }
            if (donnees.get(nb_attribut).equals("yes") && valeur_predite.equals("no")) {
                falsenegative++;
            }

        }

    }

    // permet de calculer les coefficients de la matrice de prédiction
    public void calcul_coeff_prediction() {
        ArrayList<ArrayList<String>> data_pred = donneesFichier.getDatapred();// recupere les données de prédictions

        for (ArrayList<String> exemple : data_pred) {
            String classe_exemple = exemple.get(exemple.size() - 1);

            String classe_predite = arbre.predire(arbre.getRacine(), exemple);

            if (classe_exemple.equals("no") && classe_predite.equals("yes")) {
                falsepositive++;
            }
            if (classe_exemple.equals("yes") && classe_predite.equals("yes")) {
                truepositive++;
            }
            if (classe_exemple.equals("no") && classe_predite.equals("no")) {
                truenegative++;
            }
            if (classe_exemple.equals("yes") && classe_predite.equals("no")) {
                falsenegative++;
            }
        }

    }

    public String toString() {
        return "    no " + " yes\n" + "no  " + truenegative + "   " + falsenegative + "\n" + "yes " + falsepositive
                + "   "
                + truepositive + "\n";
    }
}
