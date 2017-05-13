class Puissance4 {
    public static IHM ihm = new IHM(7*80);
    public static int joueur;
    public static int t[][];
    public static int hauteur[];
    public static int nb_apercus[] = new int[2];
    public static int nb_rotations[] = new int[2];
    public static int nb_jetons;
    public static int stats[] = new int[3];
    public static int profondeurIA = 5;
    public static boolean rotaIA = true;

    public static void initPartie() {
	t = new int[7][7];
	hauteur = new int[7];
	joueur = 0;
	for (int i=0; i<2; ++i) {
	    nb_apercus[i] = 2;
	    nb_rotations[i] = 4;
	}
	nb_jetons = 49;
	ihm.majPlateau(t);
	actualiseTexte();
	ihm.activerColonnes(true);
	ihm.activerRotation(true);
	ihm.activerApercu(true);
	ihm.activerRetour(false);
    }

    public static void actualiseTexte() {
	ihm.majTexte("<html>Au tour du <b>joueur "+(joueur+1)+"</b><br>Rotations restantes : "+nb_rotations[joueur]+"<br>Visualisations restantes : "+nb_apercus[joueur]+"</html>");
    }

    public static void changeJoueur() {
	joueur = 1-joueur;
	actualiseTexte();
    }

    public static void jouerJeton(int c) {
	if (hauteur[c]<7) {
	    t[c][6-hauteur[c]++] = joueur+1;
	    ihm.majPlateau(t);
	    changeJoueur();
	    --nb_jetons;
	}
	else ihm.boiteMessage("La colonne est pleine !");
    }

    public static void rotationDroite() {
	if (nb_rotations[joueur] > 0) {
	    int tt[][] = new int[7][7];
	    int hh[] = new int[7];
	    for (int j=0; j<7; ++j)
		for (int i=0; i<7; ++i)
		    if (t[i][j]>0) tt[j][6-hh[j]++] = t[i][j];
	    t = tt;
	    hauteur = hh;
	    ihm.majPlateau(t);
	    --nb_rotations[joueur];
	    changeJoueur();
	}
	else ihm.boiteMessage("Il ne vous reste plus de rotations !");
    }

    public static void rotationGauche() {
	if (nb_rotations[joueur] > 0) {
	    int tt[][] = new int[7][7];
	    int hh[] = new int[7];
	    for (int j=0; j<7; ++j)
		for (int i=6; i>=0; --i)
		    if (t[i][j]>0) tt[6-j][6-hh[6-j]++] = t[i][j];
	    t = tt;
	    hauteur = hh;
	    ihm.majPlateau(t);
	    --nb_rotations[joueur];
	    changeJoueur();
	}
	else ihm.boiteMessage("Il ne vous reste plus de rotations !");
    }

    public static void apercuRotationDroite() {
	if (nb_apercus[joueur] > 0) {
	    ihm.activerColonnes(false);
	    ihm.activerRotation(false);
	    ihm.activerApercu(false);
	    ihm.majTexte("<html><b>VISUALISATION</b></html>");
	    int tt[][] = new int[7][7];
	    int hh[] = new int[7];
	    for (int j=0; j<7; ++j)
		for (int i=0; i<7; ++i)
		    if (t[i][j]>0) tt[j][6-hh[j]++] = t[i][j];
	    ihm.majPlateau(tt);
	    ihm.activerRetour(true);
	    ihm.ordreUtilisateur();
	    ihm.majPlateau(t);
	    --nb_apercus[joueur];
	    actualiseTexte();
	    ihm.activerRetour(false);
	    ihm.activerColonnes(true);
	    ihm.activerRotation(true);
	    ihm.activerApercu(true);
	}
	else ihm.boiteMessage("Il ne vous reste plus de visualisations !");
    }
    
    public static void apercuRotationGauche() {
	if (nb_apercus[joueur] > 0) {
	    ihm.activerColonnes(false);
	    ihm.activerRotation(false);
	    ihm.activerApercu(false);
	    ihm.majTexte("<html><b>VISUALISATION</b></html>");
	    int tt[][] = new int[7][7];
	    int hh[] = new int[7];
	    for (int j=0; j<7; ++j)
		for (int i=6; i>=0; --i)
		    if (t[i][j]>0) tt[6-j][6-hh[6-j]++] = t[i][j];
	    ihm.majPlateau(tt);
	    ihm.activerRetour(true);
	    ihm.ordreUtilisateur();
	    ihm.majPlateau(t);
	    --nb_apercus[joueur];
	    actualiseTexte();
	    ihm.activerRetour(false);
	    ihm.activerColonnes(true);
	    ihm.activerRotation(true);
	    ihm.activerApercu(true);
	}
	else ihm.boiteMessage("Il ne vous reste plus de visualisations !");
    }

    public static int detecteAlignementL(int i, int j) {
	if (t[i][j]==t[i+1][j] && t[i][j]==t[i+2][j] && t[i][j]==t[i+3][j]) return t[i][j];
	else return 0;
    }

    public static int detecteAlignementC(int i, int j) {
	if (t[i][j]==t[i][j+1] && t[i][j]==t[i][j+2] && t[i][j]==t[i][j+3]) return t[i][j];
	else return 0;
    }

    public static int detecteAlignementD1(int i, int j) {
	if (t[i][j]==t[i-1][j+1] && t[i][j]==t[i-2][j+2] && t[i][j]==t[i-3][j+3]) return t[i][j];
	else return 0;
    }

    public static int detecteAlignementD2(int i, int j) {
	if (t[i][j]==t[i+1][j+1] && t[i][j]==t[i+2][j+2] && t[i][j]==t[i+3][j+3]) return t[i][j];
	else return 0;
    }

    public static int detecteAlignement() {
	int res;
	int cpt[] = new int[2];
	for (int i=0; i<7; ++i)
	    for (int j=0; j<4; ++j) {
		res = detecteAlignementL(j,i);
		if (res>0) ++cpt[res-1];
		res = detecteAlignementC(i,j);
		if (res>0) ++cpt[res-1];
		if (i<4) {
		    res = detecteAlignementD2(i,j);
		    if (res>0) ++cpt[res-1];
		}
		if (i>2) {
		    res = detecteAlignementD1(i,j);
		    if (res>0) ++cpt[res-1];
		}
	    }
	if (cpt[0]>0 && cpt[1]==0) return 1;
	else if (cpt[0]==0 && cpt[1]>0) return 2;
	else if (cpt[0]>0 && cpt[1]>0) return -1;
	else return 0;
    }

    public static void bilan() {
	ihm.boiteMessage("<html><center><b>Bilan</b></center><br>Nb parties : "+(stats[0]+stats[1]+stats[2])+"<br><br>Victoires joueur 1 : "+stats[1]+"<br><br>Victoires joueur 2 : "+stats[2]+"<br><br>Nb nuls : "+stats[0]+"</html>");
    }

    public static void executeIA(int ordreIA) {
	if (ordreIA==7) rotationGauche();
	else if (ordreIA==8) rotationDroite();
	else jouerJeton(ordreIA);
    }

    public static void main(String[] argv) {
	if (argv.length>0) profondeurIA = Integer.parseInt(argv[0]);
	String choix[] = {"Joueur vs Joueur","Joueur vs IA (Joueur commence)","IA vs Joueur (IA commence)","IA vs IA"};	
	String typePartie;
	do typePartie = ihm.boiteChoixMultiples("Choisir le type de partie :",choix);
	while (typePartie==null);
	int idIA = -1;
	IA ia = null, ia2 = null;
	boolean duelIA = false;
	if (typePartie.equals(choix[1])) idIA = 1;
	else if (typePartie.equals(choix[2])) idIA = 0;
	else if (typePartie.equals(choix[3])) {
	    idIA = 0;
	    duelIA = true;
	    ihm.activerColonnes(false);
	    ihm.activerRotation(false);
	    ihm.activerApercu(false);
	}
	do {
	    int gagnant = 0;
	    initPartie();
	    if (idIA>=0) ia = new IA(idIA,profondeurIA,rotaIA);
	    if (duelIA) ia2 = new IA(1-idIA,profondeurIA,rotaIA);
	    do {
		if (joueur==idIA) executeIA(ia.jouerDeepMinMax(t,hauteur,nb_rotations[1-idIA]));
		else if (duelIA && joueur==1-idIA) executeIA(ia2.jouerDeepMinMax(t,hauteur,nb_rotations[idIA]));
		else {
		    IHM.Ordre ordre = ihm.ordreUtilisateur();
		    switch (ordre) {
		    case COLONNE0:
			jouerJeton(0);
			break;
		    case COLONNE1:
			jouerJeton(1);
			break;
		    case COLONNE2:
			jouerJeton(2);
			break;
		    case COLONNE3:
			jouerJeton(3);
			break;
		    case COLONNE4:
			jouerJeton(4);
			break;
		    case COLONNE5:
			jouerJeton(5);
			break;
		    case COLONNE6:
			jouerJeton(6);
			break;
		    case ROTATIONG:
			rotationGauche();
			break;
		    case ROTATIOND:
			rotationDroite();
			break;
		    case APROTAG:
			apercuRotationGauche();
			break;
		    case APROTAD:
			apercuRotationDroite();
			break;
		    default:
		    }
		}
		gagnant = detecteAlignement();
	    } while (gagnant==0 && nb_jetons>0);
	    if (gagnant<0) gagnant = 0; // match nul
	    if (gagnant>0) ihm.boiteMessage("<html>Victoire du <b>joueur "+gagnant+"</b> !</html>");
	    else ihm.boiteMessage("<html>Match <b>nul</b> !</html>");
	    ++stats[gagnant];
	} while (ihm.boiteQuestion("Voulez-vous rejouer ?"));
	bilan();
	System.exit(0);
    }
}
