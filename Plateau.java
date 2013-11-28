import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.Color;

public class Plateau extends JPanel {
    private int t[][] = new int[7][7];
    private int dim = 7*80; // Modifier ICI uniquement la dimension du plateau
    private Color coul_bleu = new Color((float)0.2,(float)0.2,(float)1.);
    private Color coul_rouge = new Color((float)0.8,(float)0.,(float)0.);

    public Plateau() {
	setBackground(coul_bleu);
	setPreferredSize(new Dimension(dim, dim));
    } 

    public int getDim() {
	return dim;
    }

    public void setTableau(int tableau[][]) {
	for (int i=0; i<7; ++i)
	    for (int j=0; j<7; ++j)
		t[i][j] = tableau[i][j];
	repaint();
    }

    public void dessiner(Graphics g) {
	Graphics2D g2 = (Graphics2D)g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	int s = dim/7;
	int D = 4*s/5;
	int d = (s-D)/2;
	for (int i=0; i<7; ++i) {
	    for (int j=0; j<7; ++j) {
		if (t[i][j]==0) g2.setColor(Color.WHITE);
		else if (t[i][j]==1) g2.setColor(coul_rouge);
		else g2.setColor(Color.ORANGE);
		g2.fillOval(s*i+d,s*j+d,D,D);
	    }
	}
    }
    
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	dessiner(g);
    }
}
