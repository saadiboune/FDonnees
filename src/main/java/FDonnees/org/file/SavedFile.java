package FDonnees.org.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;

import FDonnees.org.model.Commentaire;
import FDonnees.org.model.CommentaireArff;

public class SavedFile {

	// Classe qui traite lescommentaires et sauvegarde dans les fichiers

	private static String commentLemmaContent = "";

	/**
	 * Construit un fichier arff (Compatible avec Weka) nom du fichier en sortie
	 * (Bruts-commentaires.arff)
	 *
	 * @return
	 * @throws IOException
	 */
	public void saveBrutArff(CommentaireArff commentaireArff) throws IOException {

		System.out.println(
				"Début de génération du fichier " + Constantes.RESSOURCES_PATH.concat(Constantes.BRUT_ARF_NAME));

		BufferedWriter writer = new BufferedWriter(
				new FileWriter(Constantes.RESSOURCES_PATH.concat(Constantes.BRUT_ARF_NAME)));

		// Ecriture du header format arff
		writer.append(commentaireArff.getHeader());

		// Ecriture des commentaires bruts dans le fichier
		commentaireArff.getListComments().forEach(comment -> {
			try {
				// On remplace les doubles quotes par de simples quotes pour respacter le
				// format arff
				String commentSansSimpleQuote = comment.getCommentaire().replace("'", "\"");
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(commentSansSimpleQuote);
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(Constantes.VIRGULE);
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(comment.getPonderation());
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(Constantes.BACK_SLASH);
			} catch (Exception e) {
				System.err.println("Erreur lors de la génération du fichier "
						+ Constantes.RESSOURCES_PATH.concat(Constantes.BRUT_ARF_NAME) + " Message d'erreur "
						+ e.getMessage());
			}
		});

		writer.close();

		System.out
				.println("Fin de génération du fichier " + Constantes.RESSOURCES_PATH.concat(Constantes.BRUT_ARF_NAME));

	}

	/**
	 * Traite les commentaires (En supprimant les mots vide récupérés du fichier en
	 * entrée ** stop-words-list.csv **) Création du fichier en sortie
	 * (SansMotsVides-commentaires.arff) au format arff pour Weka Retourne la liste
	 * des commentaire sans mots vides pour les traitements suivants
	 *
	 * @return
	 * @throws IOException
	 */
	public List<Commentaire> saveArffSansMotsVide(CommentaireArff commentaireArff) throws IOException {
		System.out.println("Début de génération du fichier " + Constantes.SANS_MOTS_VIDES_ARF_NAME);
		String[] caracteres = { ".", ",", ";", ":", "(", ")", "[", "]", "{", "}", "?", "!", "#", "\"", "`", "-", "@",
				"_", "~" };
		// Liste des commentaire sans mots vide à construire
		List<Commentaire> listCommentairesSansMotsVides = new ArrayList<>();

		// Récupération de la liste des mots vide du fichier mots_vide.txt
		List<String> listMotsVide = Files.readAllLines(Paths.get(Constantes.STOP_WORDS_FILE_PATH));

		// Création du fichier SansMotsVides-commentaires.arff
		BufferedWriter writer = new BufferedWriter(
				new FileWriter(Constantes.RESSOURCES_PATH.concat(Constantes.SANS_MOTS_VIDES_ARF_NAME)));

		// Ecriture du header du fichier arff
		writer.append(commentaireArff.getHeader());

		commentaireArff.getListComments().forEach(comment -> {
			// Vérifier et remplacer les mots vides pour chaque commentaire
			listMotsVide.forEach(motVide -> {

				if (comment.getCommentaire() != null) {
					String commentAfterReplace = comment.getCommentaire().replaceAll(" " + "(?i)" + motVide + " ", " ");
					commentAfterReplace = commentAfterReplace.replace(" " + motVide + " ", " ");
					if (commentAfterReplace.trim().startsWith("(?i)" + motVide + " ")) {
						commentAfterReplace = commentAfterReplace.trim().replace(motVide + " ", "");
					}
					for (String c : caracteres) {
						commentAfterReplace = commentAfterReplace.replace(" " + "(?i)" + motVide + c, c);
						commentAfterReplace = commentAfterReplace.replace(c + motVide + " ", c);
						commentAfterReplace = commentAfterReplace.replace(c + motVide + c, " ");
					}
//					commentAfterReplace = commentAfterReplace.replaceAll("(?i)" + " " + motVide + " ", " ");
					
//					commentAfterReplace = commentAfterReplace.replace(motVide.toUpperCase() + " ", "");
//					commentAfterReplace = commentAfterReplace.replace(" " + motVide.toUpperCase(), "");
//					commentAfterReplace = commentAfterReplace.replace(" " + motVide.toUpperCase() + " ", " ");
//					commentAfterReplace = commentAfterReplace
//							.replace(" " + motVide.substring(0, 1).toUpperCase() + motVide.substring(1) + " ", " ");
//					commentAfterReplace = commentAfterReplace
//							.replace(motVide.substring(0, 1).toUpperCase() + motVide.substring(1) + " ", "");
					commentAfterReplace = commentAfterReplace.replace("'", "\"");
					comment.setCommentaire(commentAfterReplace);
				}
			});

			// On stock dans la liste des commentaires sans mots vides pour la retourner à
			// la fin
			// du traitement pour la suite des traitements
			listCommentairesSansMotsVides.add(comment);
			// On écrit le ou les commentaire sans mots vides dans le fichier
			try {
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(comment.getCommentaire());
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(Constantes.VIRGULE);
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(comment.getPonderation());
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(Constantes.BACK_SLASH);
			} catch (Exception e) {
				System.err.println("Erreur lors de la génération du fichier "
						+ Constantes.RESSOURCES_PATH.concat(Constantes.SANS_MOTS_VIDES_ARF_NAME) + " Message d'erreur "
						+ e.getMessage());
			}
		});

		writer.close();

		System.out.println("Fin de génération du fichier " + Constantes.SANS_MOTS_VIDES_ARF_NAME);

		return listCommentairesSansMotsVides;

	}

	/**
	 * Lemmatization des commentaires avec l'outil TreeTagger et écriture dans le
	 * fichier de sortie (soit dans BrutLemma-commentaires.arff ou
	 * SansMotsVidesLemma-commentaires.arff)
	 *
	 * @return
	 * @throws IOException
	 */
	public void saveArffLemma(ArrayList<List<String>> commentsSplit, CommentaireArff commentArffSansMotsVides,
			String nomFichier) throws Exception {

		System.out.println("Début de génération du fichier " + nomFichier);

		// On récupère la liste des commentaires en objet
		List<Commentaire> commentsObj = commentArffSansMotsVides.getListComments();

		// Le dossier d'installation de TreeTagger
		System.setProperty("treetagger.home", "C:/TreeTagger");
		// TreeTagger
		TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<>();

		// Création du fichier de sortie
		BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier));
		// Ecriture du header du fichier arff
		writer.append(commentArffSansMotsVides.getHeader());

		try {
			tt.setModel("english.par");

			// On boucle sur tout les commentaires et on lemmatize
			int commentIndex = 0;

			for (List<String> commentSplit : commentsSplit) {

				tt.setHandler(new TokenHandler<String>() {

					public void token(String token, String pos, String lemma) {
						// Le commentaire après lemmatizaion
						commentLemmaContent = commentLemmaContent + " " + lemma;
					}

				});
				tt.process(commentSplit);
				
				// Ecriture du commentaire dans le fichier
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(commentLemmaContent.trim().replace("'", "\""));
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(Constantes.VIRGULE);
				writer.append(Constantes.SIMPLE_QUOTE);
				// On récupère la pondération du commentaire d'origine
				writer.append(commentsObj.get(commentIndex).getPonderation());
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(Constantes.BACK_SLASH);

				commentLemmaContent = "";
				commentIndex++;
			}

		} catch (IOException e) {
			System.err.println("Erreur lors de la génération du fichier " + nomFichier);
			System.err.println("IOException " + e.getMessage());
		} catch (TreeTaggerException e) {
			System.err.println("Erreur lors de la génération du fichier " + nomFichier);
			System.err.println("TreeTaggerException " + e.getMessage());
		} finally {
			tt.destroy();
			writer.close();
		}

		System.out.println("Fin de génération du fichier " + nomFichier);
	}

//	String commentAfterReplace = "";
	
	public void saveArffLemmaSansMotVide(ArrayList<List<String>> commentsSplit,
			CommentaireArff commentArffSansMotsVides, String nomFichier) throws Exception {

		System.out.println("Début de génération du fichier " + nomFichier);
		String[] caracteres = { ".", ",", ";", ":", "(", ")", "[", "]", "{", "}", "?", "!", "#", "\"", "`", "-", "@",
				"_", "~" };

		List<String> listMotsVide = Files.readAllLines(Paths.get(Constantes.STOP_WORDS_FILE_PATH));
		// On récupère la liste des commentaires en objet
		List<Commentaire> commentsObj = commentArffSansMotsVides.getListComments();

		// Le dossier d'installation de TreeTagger
		System.setProperty("treetagger.home", "C:/TreeTagger");
		// TreeTagger
		TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<>();

		// Création du fichier de sortie
		BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier));
		// Ecriture du header du fichier arff
		writer.append(commentArffSansMotsVides.getHeader());

		try {
			tt.setModel("english.par");

			// On boucle sur tout les commentaires et on lemmatize
			int commentIndex = 0;

			for (List<String> commentSplit : commentsSplit) {

				tt.setHandler(new TokenHandler<String>() {

					public void token(String token, String pos, String lemma) {
						// Le commentaire après lemmatizaion
						commentLemmaContent = commentLemmaContent + " " + lemma;
					}

				});
				tt.process(commentSplit);
				
				listMotsVide.forEach(motVide -> {
					if (commentLemmaContent.trim() != null) {
						commentLemmaContent = commentLemmaContent.trim().replace(" " + motVide + " ", " ");
						if (commentLemmaContent.trim().startsWith(motVide + " ")) {
							commentLemmaContent = commentLemmaContent.trim().replace(motVide + " ", "");
						}
						for (String c : caracteres) {
							commentLemmaContent = commentLemmaContent.replace(" " + motVide + c, c);
							commentLemmaContent = commentLemmaContent.replace(c + motVide + " ", c + "");
							commentLemmaContent = commentLemmaContent.replace(c + motVide + c, c + "" + c);
						}
						commentLemmaContent = commentLemmaContent.replaceAll("(?i) " + motVide + " ", " ");
//						commentLemmaContent = commentLemmaContent.replace(motVide.toUpperCase() + " ", "");
//						commentLemmaContent = commentLemmaContent.replace(" " + motVide.toUpperCase(), "");
//						commentLemmaContent = commentLemmaContent.replace(" " + motVide.toUpperCase() + " ", " ");
//						commentLemmaContent = commentLemmaContent
//								.replace(" " + motVide.substring(0, 1).toUpperCase() + motVide.substring(1) + " ", " ");
//						commentLemmaContent = commentLemmaContent
//								.replace(motVide.substring(0, 1).toUpperCase() + motVide.substring(1) + " ", "");
//					comment.setCommentaire(commentAfterReplace);
					}
				});
				// Ecriture du commentaire dans le fichier
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(commentLemmaContent.trim().replace("'", "\""));
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(Constantes.VIRGULE);
				writer.append(Constantes.SIMPLE_QUOTE);
				// On récupère la pondération du commentaire d'origine
				writer.append(commentsObj.get(commentIndex).getPonderation());
				writer.append(Constantes.SIMPLE_QUOTE);
				writer.append(Constantes.BACK_SLASH);

				commentLemmaContent = "";
				commentIndex++;
			}

		} catch (IOException e) {
			System.err.println("Erreur lors de la génération du fichier " + nomFichier);
			System.err.println("IOException " + e.getMessage());
		} catch (TreeTaggerException e) {
			System.err.println("Erreur lors de la génération du fichier " + nomFichier);
			System.err.println("TreeTaggerException " + e.getMessage());
		} finally {
			tt.destroy();
			writer.close();
		}

		System.out.println("Fin de génération du fichier " + nomFichier);
	}

}
