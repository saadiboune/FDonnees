1.	Projet Mave avec version java min 8

1. Installation Weka sur PC est Obligatoire pour lemmatization : 

	Process d'installation (Lien : https://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/#parfiles) 
	Si non pour Mac Os : 
		*Copier le répértoire TreeTagger (Présent dans le répértore TreeTagerToInstall)
			et coller le dans le répertoire (/Applications/TreeTagger)
		*Ouvrir un terminal à l'intérieur du répértoire (/Applications/TreeTagger/) et executer la cmd "sh install-tagger.sh"
		*Si jamais Mac bloque l'install ou l'exécution (via Eclipse) de TreeTagger, aller dans préférence/Système 
		et Autoriser l'execution.
			
2. 	pom.xml doit contenir l'extension org.annolab.tt4j (Obligatoire pour TreeTagger)

3.	Les fichier nécessaires au traitement sans dans le répertoire (src/main/ressources/in_put/)
	* Fichier des commentaires bruts (dataset.csv)
	* Fichier des lables (labels.csv)
	* Fichier des mots vide (mots_vide.txt)
	
4.	Les fichiers généré (4 fichier arff : commentairesBrut, CommetairesBrutsLemmatizé, CommenataireSansMotsVides et CommenataireSansMotsVidesLemmatizé)
	sont stockés dans le répértoire (src/main/ressources/out_put/)
	
5.	Lancement de l'application pour la génération des fichiers :
	* Aller dans src/main/java, package : FDonnes.org.file.fdonnees, clic droit sur App.java et run as java application
	* Des log s'affichent dans la console pour les différentes étapes de traitement
	

	