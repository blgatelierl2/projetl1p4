import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;

public class IHM extends JFrame implements ActionListener {
    private JPanel panel = new JPanel();
    private Plateau plateau = new Plateau();
    private JButton b0 = new JButton(new ImageIcon("img/flcol.png"));
    private JButton b1 = new JButton(new ImageIcon("img/flcol.png"));
    private JButton b2 = new JButton(new ImageIcon("img/flcol.png"));
    private JButton b3 = new JButton(new ImageIcon("img/flcol.png"));
    private JButton b4 = new JButton(new ImageIcon("img/flcol.png"));
    private JButton b5 = new JButton(new ImageIcon("img/flcol.png"));
    private JButton b6 = new JButton(new ImageIcon("img/flcol.png"));
    private JButton brg = new JButton(new ImageIcon("img/rotag.png"));
    private JButton brd = new JButton(new ImageIcon("img/rotad.png"));
    private JLabel zonetexte = new JLabel();
    private JButton bapg = new JButton("< Visualiser rotation");
    private JButton bapd = new JButton("Visualiser rotation >");
    private JButton bret = new JButton("Retour au jeu");
    private ReentrantLock mutex = new ReentrantLock();

    public enum Ordre {
	INCONNU,COLONNE0,COLONNE1,COLONNE2,COLONNE3,COLONNE4,COLONNE5,COLONNE6,ROTATIONG,ROTATIOND,APROTAG,APROTAD,RETOUR;
    }
    private Ordre ordre;

    public IHM() {
	// On fabrique le contenu
	int dimp = plateau.getDim();
	panel.setPreferredSize(new Dimension(dimp+2*60, dimp+150));
	JPanel boutons = new JPanel(new GridLayout(1,7,25,10));
	boutons.setPreferredSize(new Dimension(dimp,26));
	boutons.add(b0);
	b0.addActionListener(this);
	boutons.add(b1);
	b1.addActionListener(this);
	boutons.add(b2);
	b2.addActionListener(this);
	boutons.add(b3);
	b3.addActionListener(this);
	boutons.add(b4);
	b4.addActionListener(this);
	boutons.add(b5);
	b5.addActionListener(this);
	boutons.add(b6);
	b6.addActionListener(this);
	panel.add(boutons,BorderLayout.NORTH);
	JPanel plateaurota = new JPanel();
	plateaurota.add(brg);
	brg.setPreferredSize(new Dimension(50,50));
	brg.addActionListener(this);
	plateaurota.add(plateau);
	plateaurota.add(brd);
	brd.setPreferredSize(new Dimension(50,50));
	brd.addActionListener(this);
	panel.add(plateaurota,BorderLayout.SOUTH);
	JPanel paneltexte = new JPanel();
	paneltexte.setPreferredSize(new Dimension(dimp,55));
	paneltexte.add(zonetexte);
	panel.add(paneltexte,BorderLayout.SOUTH);
	JPanel panelap = new JPanel();
	panelap.add(bapg);
	bapg.addActionListener(this);
	panelap.add(bret);
	bret.addActionListener(this);
	panelap.add(bapd);
	bapd.addActionListener(this);
	panel.add(panelap,BorderLayout.SOUTH);
	// On affiche la fenêtre
	setTitle("Puissance 4");
	setContentPane(panel);
	pack();
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
    }

    public Ordre ordreUtilisateur() {
	try {
	    // On demande à l'EventDispatchThread de prendre le mutex et on attend qu'il le fasse
	    SwingUtilities.invokeAndWait(new Runnable() {
		    public void run() {
			mutex.lock();
			// L'IHM freezera s'il est déjà pris
		    }
		});
	    // On attend passivement qu'un événement le libère
	    mutex.lock();
	    // On le libère tout de suite car on s'en fiche
	    mutex.unlock();
	    return ordre;
	}
	catch(Exception e) {
	    System.err.println("Erreur : problème de prise du mutex par l'EventDispatchThread !");
	    return Ordre.INCONNU;
	}
    }

    public void majPlateau(int tableau[][]) {
	plateau.setTableau(tableau);
    }

    public void majTexte(String m) {
	zonetexte.setText(m);
    }

    public void boiteMessage(String m) {
	JOptionPane.showMessageDialog(this, m, "Puissance 4", JOptionPane.INFORMATION_MESSAGE);
    }

    public void boiteMessageAttention(String m) {
	JOptionPane.showMessageDialog(this, m, "Puissance 4", JOptionPane.WARNING_MESSAGE);
    }

    public void boiteMessageErreur(String m) {
	JOptionPane.showMessageDialog(this, m, "Puissance 4", JOptionPane.ERROR_MESSAGE);
    }

    public boolean boiteQuestion(String q) {
	return (JOptionPane.showConfirmDialog(this, q, "Puissance 4", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
    }

    public String boiteChoixMultiples(String m, String choix[]) {
	return (String)JOptionPane.showInputDialog(this,m,"Puissance 4",JOptionPane.QUESTION_MESSAGE,null,choix,choix[0]);
    }
    
    public String boiteQuestionTexte(String q) {
	return (String)JOptionPane.showInputDialog(this,q,"Puissance 4",JOptionPane.QUESTION_MESSAGE);
    }
    
    public void activerColonnes(boolean b) {
	b0.setEnabled(b);
	b1.setEnabled(b);
	b2.setEnabled(b);
	b3.setEnabled(b);
	b4.setEnabled(b);
	b5.setEnabled(b);
	b6.setEnabled(b);
    }
    
    public void activerRotation(boolean b) {
	brg.setEnabled(b);
	brd.setEnabled(b);
    }

    public void activerApercu(boolean b) {
	bapg.setEnabled(b);
	bapd.setEnabled(b);
    }

    public void activerRetour(boolean b) {
	bret.setEnabled(b);
    }
    
    public void actionPerformed(ActionEvent e) {
	if (mutex.isLocked()) {
	    if (e.getSource() == b0) ordre = Ordre.COLONNE0;
	    else if (e.getSource() == b1) ordre = Ordre.COLONNE1;
	    else if (e.getSource() == b2) ordre = Ordre.COLONNE2;
	    else if (e.getSource() == b3) ordre = Ordre.COLONNE3;
	    else if (e.getSource() == b4) ordre = Ordre.COLONNE4;
	    else if (e.getSource() == b5) ordre = Ordre.COLONNE5;
	    else if (e.getSource() == b6) ordre = Ordre.COLONNE6;
	    else if (e.getSource() == brg) ordre = Ordre.ROTATIONG;
	    else if (e.getSource() == brd) ordre = Ordre.ROTATIOND;
	    else if (e.getSource() == bapg) ordre = Ordre.APROTAG;
	    else if (e.getSource() == bapd) ordre = Ordre.APROTAD;
	    else if (e.getSource() == bret) ordre = Ordre.RETOUR;
	    else {
		ordre = Ordre.INCONNU;
		System.err.println("Erreur : ordre utilisateur imprévu !");
	    }
	    try {
		mutex.unlock();
	    }
	    catch(Exception ee) {
		System.err.println("Erreur : mutex blocké par autre chose que l'EventDispatchThread !");
	    }
	}
    }
}
