package FDonnees.org.fdonnees;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import FDonnees.org.file.Constantes;
import FDonnees.org.file.ReadFile;
import FDonnees.org.file.SavedFile;
import FDonnees.org.model.Commentaire;
import FDonnees.org.model.CommentaireArff;

/**
 * Class main qui permet d'appeler les différentes fonctions de taitement des
 * commentaires
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Début programme de transformation des avis");

        /**
         * Récupération des commentaires et leurs labels
         */
        // Une instance de la class qui regroupes les fonctions de traitement des
        // commentaire et
        // génération des fichiers .arff
        SavedFile sf = new SavedFile();

        // Lecture des deux fichiers dataset.csv et labels.csv (pour récupérer les
        // commentaires et leurs pondérations)
        ReadFile rf = new ReadFile();
        ArrayList<Commentaire> listComments = new ArrayList<Commentaire>();
        try {
            // Récupération des commentaire brut des deux fichier dataset et labels
            listComments = rf.getCommentsFromFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Construction du fichier Bruts-commentaires.arff
         */
        CommentaireArff commentArffBrut = new CommentaireArff();
        commentArffBrut.setListComments(listComments);

        // Enregistrement dans un fichier arff des commentaires bruts
        try {
            sf.saveBrutArff(commentArffBrut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Construction du fichier BrutLemma-commentaires.arff
         */
        String fichierBrutLemma = Constantes.RESSOURCES_PATH.concat(Constantes.ARF_BRUT_LEMMA);
        ArrayList<List<String>> listCommentsBrutSplited = commentArffBrut.getListCommentContent();
        try {
            sf.saveArffLemma(listCommentsBrutSplited, commentArffBrut, fichierBrutLemma);
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de la génération du fichier. Erreur : "
                    + fichierBrutLemma + " " + e.getMessage());
        }

        /**
         * Construction du model de fichier SansMotsVides-commentaires.arff
         */

        List<Commentaire> commentaireSansMotsVides = new ArrayList<>();
        try {
            // SUppression des mots vides et génération du fichier
            commentaireSansMotsVides = sf.saveArffSansMotsVide(commentArffBrut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Construction du model de fichier SansMotsVidesLemma-commentaires.arff
         */
        CommentaireArff commentArffSansMotsVides = new CommentaireArff();
        commentArffSansMotsVides.setListComments(commentaireSansMotsVides);

        String fichierSansMotsVideLemma = Constantes.RESSOURCES_PATH.concat(Constantes.SANS_MOTS_VIDES_ARF_LEMMA);
        try {
            // Lemmatization des commentaire et construction du fichier
            // SansMotsVidesLemma-commentaires.arff
            ArrayList<List<String>> listCommentsSansMotsVidesSplited = commentArffSansMotsVides.getListCommentContent();
            sf.saveArffLemmaSansMotVide(listCommentsSansMotsVidesSplited, commentArffSansMotsVides, fichierSansMotsVideLemma);

        } catch (Exception e) {
            System.err.println("Une erreur s'est produite lors de la génération du fichier. Erreur : "
                    + fichierSansMotsVideLemma + " " + e.getMessage());
        }

        System.out.println("Fin de traitement");
    	
    }
}
