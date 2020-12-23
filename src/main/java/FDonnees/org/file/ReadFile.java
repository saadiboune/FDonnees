package FDonnees.org.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import FDonnees.org.model.Commentaire;

public class ReadFile {

    // Class de lecture des deux fichiers en entrée (Labels.csv et dataset.csv)

    /**
     * Lit le fichier, récupère les commentaires et les labels associés (positif ou
     * négatit) et construit une liste de {@link Commentaire}}
     *
     * @return
     * @throws IOException
     */
    public ArrayList<Commentaire> getCommentsFromFiles() throws IOException {
        System.out.println("Début de traitement du fichier dataset et labels");

        // Contenu du commentaire en chainde de caractère
        ArrayList<String> commentFromFile = new ArrayList<>();
        // Liste des pondérations (Positif ou négatif)
        ArrayList<String> ponderationFromFile = new ArrayList<>();

        // List des commentaire (comments et leurs pondérations)
        ArrayList<Commentaire> listComment = new ArrayList<>();

        // Lecture fichier des commentaires (dataset.csv)
        try {
            BufferedReader readerDataSet = new BufferedReader(new FileReader(Constantes.DATASET_PATH));
            // Lecture ligne par ligne
            String commentLine;
            while ((commentLine = readerDataSet.readLine()) != null) {
                    commentFromFile.add(commentLine);
            }
            readerDataSet.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier " + Constantes.DATASET_PATH + " Message d'erreur "
                    + e.getMessage());
        }

        // Lecture fichier des pondération (labels.csv)
        try {
            BufferedReader readerLabels = new BufferedReader(new FileReader(Constantes.LABELS_PATH));
            // Lecture des lignes
            String labelLine;
            while ((labelLine = readerLabels.readLine()) != null) {
                ponderationFromFile.add(labelLine);
            }
            readerLabels.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la leture du fichier " + Constantes.LABELS_PATH + " Message d'erreur "
                    + e.getMessage());
        }

        // Construction des objet commentaire (label et sa pondération)
        int i = 0;
        for (String comment : commentFromFile) {
            Commentaire commentaire = new Commentaire();
            commentaire.setCommentaire(comment);

            String ponderation = ponderationFromFile.get(i);
            if ("1".equals(ponderation)) {
                commentaire.setPonderation(Constantes.POSITIF);
            } else {
                commentaire.setPonderation(Constantes.NEGATIF);
            }

            listComment.add(commentaire);

            i++;
        }

        System.out.println("Fin de traitement du fichier dataset et labels");

        return listComment;
    }
}
