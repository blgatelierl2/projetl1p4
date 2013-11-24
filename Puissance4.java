class Puissance4 {
    public static void main(String[] arg) { 
	IHM ihm = new IHM();

	/* Exemples de manipulation de l'IHM */
	int t[][] = new int[7][7];
	for (int i=0; i<7; ++i) {
	    t[i][i] = 1; // rouge
	    t[i][6-i] = 2; // jaune
	}
	ihm.majPlateau(t); // à rappeler pour mettre à jour (le tableau est copié en interne)
	ihm.majTexte("<html>ceci est un petit <u>texte</u><br><b>ligne 2</b><br><i>ligne 3</i></html>");

	/* Activer/désactiver des boutons */
	//ihm.activerColonnes(false);
	//ihm.activerRotation(false);
	//ihm.activerApercu(false);
	//ihm.activerRetour(false);
	
	/* Boîtes de dialogue */
	//ihm.boiteMessage("<html>ligne 1<br>ligne 2</html>");
	//ihm.boiteQuestion("ok?"); // return true/false
	String choix[] = {"Joueur vs Joueur","Joueur vs IA (Joueur commence)","IA vs Joueur (IA commence)"};
	ihm.boiteMessage("-> "+ihm.boiteChoixMultiples("Choisir le type de partie :",choix));
	
	/* Ordres de l'utilisateur */
	while(true) { // ici, pour l'exemple,  on prend les ordres en boucle
	    // On attend un nouvel ordre
	    IHM.Ordre ordre = ihm.ordreUtilisateur();
	    // On traite l'ordre
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
		if (ihm.boiteQuestion("Visualiser rotation gauche ?")) ihm.boiteMessage("-> Oui");
		else ihm.boiteMessage("-> Non");
		break;
	    case APROTAD:
		if (ihm.boiteQuestion("Visualiser rotation droite ?")) ihm.boiteMessage("-> Oui");
		else ihm.boiteMessage("-> Non");
		break;
	    case RETOUR:
		if (ihm.boiteQuestion("Retour au jeu ?")) ihm.boiteMessage("-> Oui");
		else ihm.boiteMessage("-> Non");
		break;
	    default:
		ihm.boiteMessage("Hum, ordre inconnu ?!");
		// Ne devrait pas se produire, sauf si vous modifiez le code de la classe IHM !
	    }
	}
    }
}
