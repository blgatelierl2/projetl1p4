class Puissance4 {
    public static void main(String[] arg) { 
	IHM ihm = new IHM();

	/* Exemples de manipulation de l'IHM */
	int t[][] = new int[7][7];
	t[0][0] = 1;
	t[1][0] = 2;
	ihm.majPlateau(t); // à rappeler pour mettre à jour (le tableau est copié en interne)
	ihm.majTexte("<html>ceci est un petit <u>texte</u><br><b>ligne 2</b><br><i>ligne 3</i></html>");

	/* Activer/désactiver des boutons */
	//ihm.activerColonnes(true);
	//ihm.activerRotation(true);
	//ihm.activerApercu(false);
	//ihm.activerRetour(false);
	
	/* Boîtes de dialogue */
	//ihm.boiteMessage("<html>ligne 1<br>ligne 2</html>");
	//ihm.boiteQuestion("ok?"); // return true/false
	String choix[] = {"Joueur vs Joueur","Joueur vs IA (Joueur commence)","IA vs Joueur (IA commence)"};
	ihm.boiteMessage(ihm.boiteChoixMultiples("Choisir le type de partie :",choix));
	
	/* Ordres de l'utilisateur */
	while(true) { // on prend les ordres en boucle
	    // On attend un nouvel ordre
	    IHM.Ordre ordre = ihm.ordreUtilisateur();
	    switch (ordre) {
	    case COLONNE0:
		ihm.boiteMessage("Colonne 0");
		break;
	    case COLONNE1:
		ihm.boiteMessage("Colonne 1");
		break;
	    case COLONNE2:
		ihm.boiteMessage("Colonne 2");
		break;
	    case COLONNE3:
		ihm.boiteMessage("Colonne 3");
		break;
	    case COLONNE4:
		ihm.boiteMessage("Colonne 4");
		break;
	    case COLONNE5:
		ihm.boiteMessage("Colonne 5");
		break;
	    case COLONNE6:
		ihm.boiteMessage("Colonne 6");
		break;
	    case ROTATIONG:
		ihm.boiteMessage("Rotation gauche");
		break;
	    case ROTATIOND:
		ihm.boiteMessage("Rotation droite");
		break;
	    case APROTAG:
		ihm.boiteQuestion("Visualiser rotation gauche ?");
		break;
	    case APROTAD:
		ihm.boiteQuestion("Visualiser rotation droite ?");
		break;
	    case RETOUR:
		ihm.boiteQuestion("Retour au jeu ?");
		break;
	    default:
		ihm.boiteMessage("Hum, ordre inconnu ?!");
		// Ne devrait pas se produire !
	    }
	}
    }
}
