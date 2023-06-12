package ihm;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import controleur.*;
import metier.*;

public class PanelCarte extends JPanel implements MouseListener,ActionListener,MouseMotionListener
{

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur   ctrl;

	private FrameCarte frame;

	private JButton     btnPasserTour;
	private JButton     btnQuitter;

	private Image        imgDosDeCarte = this.getToolkit().getImage("donnees/imagesCartes/dos_carte.png");
	private int          inutile;

	private int      bHover;

	/** Constructeur de PanelGraph
	 * @param ctrl de type Controleur
	 * 
	 */
	public PanelCarte ( Controleur ctrl,FrameCarte frame)
	{
		this.ctrl   = ctrl;
		this.frame  = frame;

		this.setBackground(new Color(172,209,232)) ;
		this.setLayout(new BorderLayout());

		this.inutile = 0;
		this.bHover = -1;

		//Bouton passer tour
		JPanel panelDroit = new JPanel(new GridLayout(5,1));
		panelDroit.setOpaque(false);
		this.btnPasserTour = new JButton("Passer Tour");
		this.btnQuitter = new JButton("Quitter");

		panelDroit.add(new JLabel());
		panelDroit.add(this.btnPasserTour);
		panelDroit.add(this.btnQuitter);
		panelDroit.add(new JLabel());
		this.add(panelDroit,BorderLayout.EAST);

		this.btnPasserTour.addActionListener(this);
		this.btnQuitter.addActionListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}

	@Override
	public void paintComponent ( Graphics g )
	{

		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;
		
		this.dessinerCartes(g2);

		Carte c = this.ctrl.getJoueur1().getPartie().getCarteEnCours();

		if (c == null) 
			g2.drawImage(this.imgDosDeCarte,500,20,this.imgDosDeCarte.getWidth(null)/3,this.imgDosDeCarte.getHeight(null)/3,null);
		else
		{
			Image image = this.getToolkit().getImage ("donnees/imagesCartes/" + c.getTypeCarte() + c.getCouleurCarte() +".png");
			g2.drawImage(image,500,20,this.imgDosDeCarte.getWidth(null)/3,this.imgDosDeCarte.getHeight(null)/3,null);
		}

		if (this.inutile < 5)
		{
			this.setSize(0,0);
			this.setSize(this.frame.getSize());
			this.inutile++;	
		}
	}

	public void dessinerCartes(Graphics2D g2)
	{
		Paquet paquetDeCartes = this.ctrl.getJoueur1().getPartie().getPaquet();
		int y = 20;
		for (int cpt = 0; cpt < paquetDeCartes.taillePaquet(); cpt++)
		{
			if (cpt == this.bHover) 
				y = 30;
			else
				y = 20;
			
			String typeC = "" + paquetDeCartes.getCarte(cpt).getTypeCarte   ();
			String coulC = "" + paquetDeCartes.getCarte(cpt).getCouleurCarte();
			Image image = this.getToolkit().getImage ("donnees/imagesCartes/" + typeC + coulC   +".png");
			g2.drawImage(image,50+20*cpt,y,this.imgDosDeCarte.getWidth(null)/3,this.imgDosDeCarte.getHeight(null)/3,null);
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		for (int cpt = 0; cpt < this.ctrl.getJoueur1().getPartie().getPaquet().taillePaquet(); cpt++)
		{
			Rectangle2D zoneCarte = new Rectangle2D.Double(50+20*cpt,20,20,this.imgDosDeCarte.getHeight(null)/3);
			if (zoneCarte.contains(e.getPoint()))
			{
				this.ctrl.getJoueur1().getPartie().setCarteEnCours(this.ctrl.getJoueur1().getPartie().getPaquet().piocher(cpt));
			}
		}
		this.repaint();
	}

	public void mouseMoved(MouseEvent e)
	{

		for (int cpt = 0; cpt < this.ctrl.getJoueur1().getPartie().getPaquet().taillePaquet(); cpt++)
		{
			Rectangle2D zoneCarte = new Rectangle2D.Double(50+20*cpt,20,20,this.imgDosDeCarte.getHeight(null)/3);

			if (zoneCarte.contains(e.getPoint()))
			{
				this.bHover = cpt;
				break;
			}
			else
				this.bHover = -1;
		}
		this.repaint();
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.btnPasserTour)
		{
			this.ctrl.getJoueur1().getPartie().tourSuivant();
			this.repaint();
		}
		if (e.getSource() == this.btnQuitter)
			System.exit(1);
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e)
	{

	}

	public void mouseExited(MouseEvent e)
	{}

	

	public void mouseDragged(MouseEvent e) {}

}