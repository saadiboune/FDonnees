package FDonnees.org.model;

/**
 * Class qui permet de modéliser un Commentaire dans un fichier arff Un
 * Commenntaire est construit d'un contenu de commentaire, de sa pondération
 * (Positif ou négatif) séparé par une virgule
 */
public class Commentaire {

    /**
     * Attributs
     */
    private String commentaire;
    private String ponderation;
    private static final String separateur = ",";

    /**
     * Constructeur sans paramètres
     */
    public Commentaire() {
        super();
    }

    /**
     * Constructeur avec paramètres
     *
     * @param commentaire
     * @param ponderation
     */
    public Commentaire(String commentaire, String ponderation) {
        super();
        this.setCommentaire(commentaire);
        this.ponderation = ponderation;
    }

    /**
     * Getters et setters
     *
     * @return
     */
    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {

        this.commentaire = commentaire;
    }

    public String getPonderation() {
        return ponderation;
    }

    public void setPonderation(String ponderation) {
        this.ponderation = ponderation;
    }

    public String getSeparateur() {
        return separateur;
    }

    public void setPonderation(boolean ponderation) {
        if (ponderation) {
            this.ponderation = "positif";
        } else {
            this.ponderation = "negatif";
        }
    }

    /**
     * Méthode toString
     */
    @Override
    public String toString() {
        return "Commentaire [commentaire=" + commentaire + ", separateur=" + separateur + ", ponderation=" + ponderation
                + "]";
    }

}
