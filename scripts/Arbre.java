package scripts;

import java.util.ArrayList;
import java.util.Collections;

public class Arbre {
    DonneesFichier donneesFichier;
    Noeud racine;

    public Arbre(DonneesFichier donneesFichier) {
        this.donneesFichier = donneesFichier;
    }

    public Noeud getRacine() {
        return racine;
    }

    public void creer_arbre() {
        ArrayList<ArrayList<String>> data = donneesFichier.getData();
        ArrayList<ArrayList<String>> possible_values = donneesFichier.getPossible_values();
        ArrayList<String> attributs_name = donneesFichier.getAttributs_name();
        ArrayList<Float> gain_racine = donneesFichier.set_gain_attributs(possible_values, attributs_name, data);

        int indiceAttributRacine = gain_racine.indexOf(Collections.max(gain_racine));
        Noeud noeud_racine = new Noeud(attributs_name.get(indiceAttributRacine));
        racine = noeud_racine;
        for (String valeur : possible_values.get(indiceAttributRacine)) {
            Branche branche = new Branche(noeud_racine, valeur);
            noeud_racine.addBranche(branche);
            ArrayList<ArrayList<String>> sousEnsemble = new ArrayList<>();
            for (ArrayList<String> exemple : data) {
                if (exemple.get(indiceAttributRacine).equals(valeur)) {
                    sousEnsemble.add(exemple);
                }
            }
            // Récursivement créer l'arbre pour chaque sous-ensemble
            creerSousArbre(branche, sousEnsemble, attributs_name, possible_values, data);

        }
    }

    private void creerSousArbre(Branche branche, ArrayList<ArrayList<String>> sousEnsemble,
            ArrayList<String> attributs_name, ArrayList<ArrayList<String>> possible_values,
            ArrayList<ArrayList<String>> AnciensousEnsemble) {
        // Condition d'arrêt 1 : l'ensemble d'exemples associés au noeud courant est
        // vide
        if (sousEnsemble.isEmpty()) {
            // Créer une feuille avec la classe majoritaire dans le sous-ensemble parent
            String classeMajoritaire = classeMajoritaire(AnciensousEnsemble);
            Noeud feuille = new Noeud(classeMajoritaire);
            branche.setNoeud_arrive(feuille);
            return;
        }

        // Condition d'arrêt 2 : tous les exemples associés au noeud courant ont la même
        // valeur de classe
        if (estPur(sousEnsemble)) {
            // Créer une feuille avec la classe unique dans le sous-ensemble
            String classeUnique = sousEnsemble.get(0).get(sousEnsemble.get(0).size() - 1);
            Noeud feuille = new Noeud(classeUnique);
            branche.setNoeud_arrive(feuille);
            return;
        }

        // Calculer le gain d'information pour chaque attribut dans le sous-ensemble
        ArrayList<Float> gain_attributs = donneesFichier.set_gain_attributs(possible_values, attributs_name,
                sousEnsemble);

        // Trouver l'attribut avec le gain d'information le plus élevé
        int indiceAttribut = gain_attributs.indexOf(Collections.max(gain_attributs));

        // Créer le noeud correspondant à cet attribut et le connecter à la branche
        // actuelle
        String attribut = attributs_name.get(indiceAttribut);
        Noeud noeud = new Noeud(attribut);
        branche.setNoeud_arrive(noeud);

        // Pour chaque valeur possible de l'attribut choisi, créer une nouvelle branche
        // et récursivement construire l'arbre
        for (String valeur : possible_values.get(indiceAttribut)) {
            Branche nouvelleBranche = new Branche(noeud, valeur);
            noeud.addBranche(nouvelleBranche);
            ArrayList<ArrayList<String>> nouveauSousEnsemble = new ArrayList<>();
            for (ArrayList<String> exemple : sousEnsemble) {
                if (exemple.get(indiceAttribut).equals(valeur)) {
                    nouveauSousEnsemble.add(exemple);
                }
            }
            creerSousArbre(nouvelleBranche, nouveauSousEnsemble, attributs_name, possible_values, sousEnsemble);

        }
    }

    // permet de verifier si le sous ensemble a que des yes ou que des no
    public boolean estPur(ArrayList<ArrayList<String>> sousEnsemble) {

        String classe = sousEnsemble.get(0).get(sousEnsemble.get(0).size() - 1);
        for (ArrayList<String> exemple : sousEnsemble) {
            if (!exemple.get(exemple.size() - 1).equals(classe)) {
                return false;
            }
        }
        return true;
    }

    // permet de determiner si il ya plus de yes ou de no dans une sous ensemble
    public String classeMajoritaire(ArrayList<ArrayList<String>> sousEnsemble) {
        int nbYes = 0;
        int nbNo = 0;
        for (ArrayList<String> exemple : sousEnsemble) {
            if (exemple.get(exemple.size() - 1).equals("yes")) {
                nbYes++;
            } else {
                nbNo++;
            }
        }
        return nbYes > nbNo ? "yes" : "no";
    }

    // permet d'afficher un arbre
    // methode faites par chatgpt pour pouvoir visualiser mes resultats
    public void afficherArbre(Noeud racine, String prefixe) {
        if (racine == null) {
            return;
        }
        // Afficher le noeud actuel
        System.out.println(prefixe + racine.getValue());

        // Récupérer les branches sortantes du noeud
        ArrayList<Branche> branches = racine.getBranches();

        // Afficher chaque branche avec son noeud et récursivement afficher ses
        // sous-arbres
        for (Branche branche : branches) {
            Noeud noeudArrive = branche.getNoeud_arrive();
            String valeurBranche = branche.getValeur_branche();
            String nouveauPrefixe = prefixe + "│  ";
            System.out.println(prefixe + "└─ " + valeurBranche);
            afficherArbre(noeudArrive, nouveauPrefixe);
        }
    }
}
