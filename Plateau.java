import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.Color;

public class Plateau extends JPanel {
    private int t[][] = new int[7][7];

    public Plateau() {
	setBackground(Color.BLUE);
	setPreferredSize(new Dimension(700, 700));
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
	for (int i=0; i<7; ++i) {
	    for (int j=0; j<7; ++j) {
		if (t[i][j]==0) g2.setColor(Color.WHITE);
		else if (t[i][j]==1) g2.setColor(Color.RED);
		else g2.setColor(Color.YELLOW);
		g2.fillOval(100*i+12,100*j+12,76,76);
	    }
	}
    }
    
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	dessiner(g);
    }
}
