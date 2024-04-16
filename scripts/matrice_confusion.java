package scripts;

import java.util.ArrayList;

public class matrice_confusion {
    DonneesFichier donneesFichier;
    Arbre arbre;
    private ArrayList<Integer> coeff_matrice = new ArrayList<Integer>();

    public matrice_confusion(DonneesFichier donneesFichier, Arbre arbre) {
        this.donneesFichier = donneesFichier;
        this.arbre = arbre;
    }

    public void calcul_coeff() {
        ArrayList<ArrayList<String>> data = donneesFichier.getData();
        ArrayList<ArrayList<String>> possible_values = donneesFichier.getPossible_values();
        ArrayList<String> attributs_name = donneesFichier.getAttributs_name();
        int truepositive = 0, truenegative = 0, falsepositive = 0, falsenegative = 0;
        int nb_attribut = data.get(0).size() - 1;

        // on parcours toutes les données
        for (ArrayList<String> donnees : data) {
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
        coeff_matrice.add(truepositive);
        coeff_matrice.add(truenegative);
        coeff_matrice.add(falsepositive);
        coeff_matrice.add(falsenegative);
    }

    public String toString() {
        return coeff_matrice.get(0) + "   " + coeff_matrice.get(1) + "\n" + coeff_matrice.get(2) + "   "
                + coeff_matrice.get(3) + "\n";
    }
}
