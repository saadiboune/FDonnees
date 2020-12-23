package FDonnees.org.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class de modélisation d'un fichier .arff Un fichier .arff est construit d'un
 * header et de plusieurs commentaires
 */
public class CommentaireArff {

    /**
     * Attributs header et liste de commentaires du fichier
     */
    private static final String header = "@relation Classification-Opinion\n@attribute Commentaire String "
    		+ "\n@attribute Class {positif,negatif} \n@data\n";
    private List<Commentaire> listComments;

    /**
     * Constructeur sans param
     */
    public CommentaireArff() {
        super();
    }

    /**
     * Constructeur avec params
     *
     * @param listComments
     */
    public CommentaireArff(List<Commentaire> listComments) {
        super();
        this.listComments = listComments;
    }

    /**
     * Récupérer la liste des commentaire
     *
     * @return
     */
    public List<Commentaire> getListComments() {
        return listComments;
    }

    /**
     * Récupérer la liste des commentaires splités (Nécessaire lors de la
     * lemmatization)
     *
     * @return
     */
    public ArrayList<List<String>> getListCommentContent() {
        ArrayList<List<String>> listComSplit = new ArrayList<>();

        for (Commentaire comment : this.listComments) {
            List<String> commentSplted = Arrays.asList(comment.getCommentaire().split(" "));
            listComSplit.add(commentSplted);
        }

        return listComSplit;
    }

    /**
     * set la liste des commentaires
     *
     * @param listComments
     */
    public void setListComments(List<Commentaire> listComments) {
        this.listComments = listComments;
    }

    /**
     * Ajouter un commentaire à la liste des commentaires
     *
     * @param comment
     */
    public void addComment(Commentaire comment) {
        this.listComments.add(comment);
    }

    /**
     * Récupérer le getter du fichier .arff
     *
     * @return
     */
    public String getHeader() {
        return header;
    }

    /**
     * La méthode toString
     */
    @Override
    public String toString() {
        return "CommentaireArff [header=" + header + " , listComments=" + listComments + "] ";
    }

}
