package scripts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("unused")

public class DonneesFichier {

    // Attributs de classe pour stocker les noms d'attributs, les valeurs possibles
    // et les données
    private ArrayList<String> attributs_name = new ArrayList<String>();
    private ArrayList<ArrayList<String>> possible_values = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    private String filePath;

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
}
