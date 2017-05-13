import java.util.Random;
import java.util.Stack;

class IA {
    private static final int MAX = 100000;
    private static final int MIN = -MAX;
    private static final int NUL = 0;
    private boolean flagNUL;

    /* Problème du nul :
         Si le score du nul est négatif : 
           - il est perçu comme un bon coup pour l'adversaire, ce qui n'est pas nécessairement le cas ;
           - s'il est très négatif (comme MIN/2), il peut pousser l'IA à rester dans des scores très négatifs (qui peuvent éventuellement la conduire à perdre) plutôt que de forcer le nul.
	 Pour des raisons de symétrie, le nul devrait plutôt rester de score 0.
	 Le problème est qu'avec les rotations, il est facile de forcer le nul.
	 Si l'IA reste dans des scores faiblement négatifs, elle peut être tentée de forcer le nul trop facilement au lieu de continuer la partie.
	 Pour cette raison, il faut que l'IA n'évalue à sa profondeur max que des plateaux équilibrés (ou déséquilibrés en sa faveur),
	 mai surtout pas des plateaux déséquilibrés en la faveur de l'adversaire.
	 Pour cette raison, si l'IA joue en second, sa profondeur DOIT être IMPAIRE (sinon elle sous-évalue son score à cause du déséquilibre).
    */

    private int t[][];
    private int h[];
    private int sumh;
    private int tour;
    private int valIA;
    private int depth;
    private boolean rota;
    private int nbRotaIA;
    private int nbRotaAdv;

    private Random rGen;

    public IA(int idIA, int maxDepth, boolean Rotations) {
	t = new int[7][7];
	h = new int[7];
	tour = 1;
	valIA = idIA+1;
	rGen = new Random();
	depth = maxDepth;
	if (idIA==1 && depth%2==0) --depth;
	rota = Rotations;
	nbRotaIA = 4;
    }

    private boolean jouerJeton(int c, int v) {
	if (h[c]<7) {
	    t[c][6-h[c]++] = v;
	    ++sumh;
	    return true;
	}
	else return false;
    }

    private boolean jouerJetonIA(int c) {
	return jouerJeton(c,1);
    }
    
    private boolean jouerJetonAdv(int c) {
	return jouerJeton(c,5);
    }

    private void annulerJeton(int c) {
	t[c][6-(--h[c])] = 0;
	--sumh;
    }

    class ConfPlat {
	public final int[][] t;
	public final int[] h;
	public ConfPlat(int[][] t, int[] h) {this.t=t; this.h=h;}
    }

    private Stack<ConfPlat> pile;

    private void rotationDroite() {
	int tt[][] = new int[7][7];
	int hh[] = new int[7];
	for (int j=0; j<7; ++j)
	    for (int i=0; i<7; ++i)
		if (t[i][j]>0) tt[j][6-hh[j]++] = t[i][j];
	pile.push(new ConfPlat(t,h));
	t = tt;
	h = hh;
    }

    private boolean rotationDroiteIA() {
	if (nbRotaIA>0) {
	    rotationDroite();
	    --nbRotaIA;
	    return true;
	}
	return false;
    }

    private boolean rotationDroiteAdv() {
	if (nbRotaAdv>0) {
	    rotationDroite();
	    --nbRotaAdv;
	    return true;
	}
	return false;
    }

    private void rotationGauche() {
	int tt[][] = new int[7][7];
	int hh[] = new int[7];
	for (int j=0; j<7; ++j)
	    for (int i=6; i>=0; --i)
		if (t[i][j]>0) tt[6-j][6-hh[6-j]++] = t[i][j];
	pile.push(new ConfPlat(t,h));
	t = tt;
	h = hh;
    }

    private boolean rotationGaucheIA() {
	if (nbRotaIA>0) {
	    rotationGauche();
	    --nbRotaIA;
	    return true;
	}
	return false;
    }

    private boolean rotationGaucheAdv() {
	if (nbRotaAdv>0) {
	    rotationGauche();
	    --nbRotaAdv;
	    return true;
	}
	return false;
    }

    private void annulerRotation(boolean coupIA) {
	ConfPlat cp = pile.pop();
	t = cp.t;
	h = cp.h;
	if (coupIA) ++nbRotaIA;
	else ++nbRotaAdv;
    }

    private void initTableau(int tin[][], int hin[]) {
	int valJoueur = 3-valIA;
	sumh = 0;
	for (int i=0; i<7; ++i) {
	    h[i] = hin[i];
	    sumh += h[i];
	    for (int j=0; j<7; ++j)
		if (tin[i][j]==valJoueur) t[i][j] = 5;
		else if (tin[i][j]==valIA) t[i][j] = 1;
		else t[i][j] = 0;
	}
    }
    /*
    class Score {
	public int score;
	public boolean nul;
	}*/

    private int score(int t[][]) {
	flagNUL = false;
	int s = 0;
	boolean v1 = false;
	boolean v2 = false;
	for (int i=0; i<7; ++i)
	    for (int j=0; j<4; ++j) {
		int val = 0;
		for (int k=0; k<4; ++k) val += t[i][j+k];
		if (val>0) {
		    if (val==4) {
			if (v2) {
			    flagNUL = true;
			    return NUL;
			}
			v1 = true;
		    }
		    else if (val==20) {
			if (v1) {
			    flagNUL = true;
			    return NUL;
			}
			v2  = true;
		    }
		    else if (val<5) s+=val*val;
		    else if (val%5==0) s-=(val/5)*(val/5);
		}
	    }
	for (int i=0; i<4; ++i)
	    for (int j=0; j<7; ++j) {
		int val = 0;
		for (int k=0; k<4; ++k) val += t[i+k][j];
		if (val>0) {
		    if (val==4) {
			if (v2) {
			    flagNUL = true;
			    return NUL;
			}
			v1 = true;
		    }
		    else if (val==20) {
			if (v1) {
			    flagNUL = true;
			    return NUL;
			}
			v2  = true;
		    }
		    else if (val<5) s+=val*val;
		    else if (val%5==0) s-=(val/5)*(val/5);
		}
	    }
	for (int i=0; i<4; ++i)
	    for (int j=0; j<4; ++j) {
		int val = 0;
		for (int k=0; k<4; ++k) val += t[i+k][j+k];
		if (val>0) {
		    if (val==4) {
			if (v2) {
			    flagNUL = true;
			    return NUL;
			}
			v1 = true;
		    }
		    else if (val==20) {
			if (v1) {
			    flagNUL = true;
			    return NUL;
			}
			v2  = true;
		    }
		    else if (val<5) s+=val*val;
		    else if (val%5==0) s-=(val/5)*(val/5);
		}
	    }
	for (int i=3; i<7; ++i)
	    for (int j=0; j<4; ++j) {
		int val = 0;
		for (int k=0; k<4; ++k) val += t[i-k][j+k];	
		if (val>0) {
		    if (val==4) {
			if (v2) {
			    flagNUL = true;
			    return NUL;
			}
			v1 = true;
		    }
		    else if (val==20) {
			if (v1) {
			    flagNUL = true;
			    return NUL;
			}
			v2  = true;
		    }
		    else if (val<5) s+=val*val;
		    else if (val%5==0) s-=(val/5)*(val/5);
		}
	    }
	if (v1) return MAX;
	else if (v2) return MIN;
	else if (sumh==49) {
	    flagNUL = true;
	    return NUL;
	}
	return s;
    }

    private int score() {
	return score(t);
    }

    class Coup {
	public final int score;
	public final int position;
	public Coup(int s, int p) {score = s; position = p;}
    }

    private Coup minMax(int n) {
	boolean coupIA = ((depth-n)%2==0);
	int s0 = score();
	if (n==0 || s0==MAX || s0==MIN || flagNUL) return new Coup(s0,-1);
	int Sbest = MAX+1;
	if (coupIA) Sbest = MIN-1;
	int Ibest = 0;
	for (int i=0; i<7; ++i) {
	    if ((coupIA && jouerJetonIA(i)) || (!coupIA && jouerJetonAdv(i))) {
		int s = minMax(n-1).score;
		if ((coupIA && s>Sbest) || (!coupIA && s<Sbest)) {
		    Sbest = s;
		    Ibest = i;
		}
		else if (s==Sbest && rGen.nextBoolean()) Ibest = i;
		annulerJeton(i);
	    }
	}
	if (rota) {
	    if ((coupIA && rotationGaucheIA()) || (!coupIA && rotationGaucheAdv())) {
		int s = minMax(n-1).score;
		if ((coupIA && s>Sbest) || (!coupIA && s<Sbest)) {
		    Sbest = s;
		    Ibest = 7;
		}
		// pas d'else pour les rotations pour les économiser
		annulerRotation(coupIA);
	    }
	    if ((coupIA && rotationDroiteIA()) || (!coupIA && rotationDroiteAdv())) {
		int s = minMax(n-1).score;
		if ((coupIA && s>Sbest) || (!coupIA && s<Sbest)) {
		    Sbest = s;
		    Ibest = 8;
		}
		annulerRotation(coupIA);		
	    }
	}
	return new Coup(Sbest,Ibest);
    }
    
    public int jouerDeepMinMax(int t[][], int[] h, int nbRA) {
	initTableau(t,h);
	nbRotaAdv = nbRA;
	if (rota) pile = new Stack<ConfPlat>();
	Coup m = minMax(depth);
	if (m.position>6) --nbRotaIA;
	System.out.println("Tour "+(tour++)+" (IA"+depth+"-J"+valIA+") : "+m.score);
	return m.position;
    }
}
