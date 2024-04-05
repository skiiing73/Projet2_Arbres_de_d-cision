package scripts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DonneesFichier {

    // Attributs de classe pour stocker les noms d'attributs, les valeurs possibles
    // et les données
    private ArrayList<String> attributs_name = new ArrayList<String>();
    private ArrayList<ArrayList<String>> possible_values = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    private ArrayList<Float> gain_attributs = new ArrayList<Float>();
    private String filePath;

    private float entropie_globale;

    // Constructeur par défaut
    public DonneesFichier(String filePath) {
        this.filePath = filePath;
    }

    // Méthode pour récupérer les noms d'attributs
    public ArrayList<String> getAttributs_name() {
        return attributs_name;
    }

    // Méthode pour récupérer les valeurs possibles
    public ArrayList<ArrayList<String>> getPossible_values() {
        return possible_values;
    }

    // Méthode pour récupérer les données
    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    public ArrayList<Float> getgain_attributs() {
        return gain_attributs;
    }

    // Méthode pour lire les données à partir d'un fichier
    public void lire_donnees() {
        try {
            // Scanner pour lire le fichier
            Scanner scanner = new Scanner(new File(filePath));

            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
                // Scanner pour lire chaque ligne
                Scanner lineScanner = new Scanner(line);
                if (lineNumber == 1) {
                    // Si c'est la première ligne (contenant les noms d'attributs)
                    while (lineScanner.hasNext()) {
                        // Lire chaque mot de la ligne et supprimer les virgules
                        String mot = lineScanner.next().replaceAll(",", "");
                        // Ajouter le mot (nom d'attribut) à la liste des attributs
                        attributs_name.add(mot);
                        // Créer une liste vide pour stocker les valeurs possibles de cet attribut
                        ArrayList<String> possible_value = new ArrayList<>();
                        possible_values.add(possible_value);
                    }
                    lineScanner.close();
                } else {
                    // Si ce n'est pas la première ligne (contenant les données)
                    ArrayList<String> data_temp = new ArrayList<String>();
                    int nb_attribut = 0;
                    // Lire chaque mot de la ligne et supprimer les virgules
                    while (lineScanner.hasNext()) {
                        String mot = lineScanner.next().replaceAll(",", "");
                        // Ajouter le mot à la liste des données temporaires
                        data_temp.add(mot);
                        // Vérifier si cette valeur n'est pas déjà dans la liste des valeurs possibles
                        if (!possible_values.get(nb_attribut).contains(mot)) {
                            // Si non, l'ajouter à la liste des valeurs possibles pour cet attribut
                            possible_values.get(nb_attribut).add(mot);
                        }
                        nb_attribut++;
                    }
                    // Ajouter les données temporaires à la liste des données
                    data.add(data_temp);
                }

            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Afficher un message lorsque les données sont lues avec succès
        System.out.println("Données lues avec succès");
    }

    public void set_entropie() {
        // permet de calculer l'entropie globale du système
        entropie_globale = calcul_entropie_sous_ensemble(data)[0];
    }

    public float[] calcul_entropie_sous_ensemble(ArrayList<ArrayList<String>> sous_ensemble) {
        // permet de calculer l'entropie d'un sous ensemble
        float nb_yes = 0;
        float nb_no = 0;
        for (ArrayList<String> donnees : sous_ensemble) { // parcours du sous_ensemble pour determiner le nb de yes et
                                                          // de no
            if (donnees.get(4).equals("yes")) {
                nb_yes++;
            } else {
                nb_no++;
            }
        }
        float nb_donnes = nb_no + nb_yes; // nombre de choix totaux
        float p_nb_yes = nb_yes / nb_donnes; // probabilité d'avoir yes
        float p_nb_no = nb_no / nb_donnes;// probabilité d'avoir no
        float entropie_sous_ensemble = (float) -(p_nb_yes * (Math.log(p_nb_yes) / Math.log(2))
                + p_nb_no * (Math.log(p_nb_no) / Math.log(2)));
        float[] resultats = { entropie_sous_ensemble, nb_no, nb_yes };

        return resultats;
    }

    public void set_gain_attributs() {
        // Parcours de tous les attributs sauf le dernier (qui est la classe cible)
        for (int nb_attributs = 0; nb_attributs < attributs_name.size() - 1; nb_attributs++) {
            float somme_entropies = 0;

            // Parcours des valeurs possibles de l'attribut actuel
            for (String possible_values_temp : possible_values.get(nb_attributs)) {
                ArrayList<ArrayList<String>> sous_ensemble = new ArrayList<ArrayList<String>>();

                // Création des sous-ensembles associés à une valeur possible de l'attribut
                for (ArrayList<String> donnees : data) {
                    if (donnees.contains(possible_values_temp)) {
                        sous_ensemble.add(donnees);
                    }
                }

                // Calcul de l'entropie du sous-ensemble et du nombre d'occurrences de "yes" et
                // "no"
                float[] resultats_entropie = calcul_entropie_sous_ensemble(sous_ensemble);
                float entropie_sous_ensemble = resultats_entropie[0];
                float nb_no = resultats_entropie[1];
                float nb_yes = resultats_entropie[2];

                // Calcul de la somme des entropies pondérées
                if (nb_no == 0.0 || nb_yes == 0) {
                    somme_entropies += 0;
                } else {
                    somme_entropies += ((nb_no + nb_yes) / (data.size())) * entropie_sous_ensemble;
                }

            }
            // Calcul du gain pour cet attribut
            float gain = entropie_globale - somme_entropies;
            gain_attributs.add(gain);
        }
    }

}
