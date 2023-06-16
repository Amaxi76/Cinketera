package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import controleur.*;

public class FrameChoixScenario extends JFrame implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

	/**
	 * Bouton 
	 */
	private JButton  btnScenario1;
	
	private JButton  btnScenario2;
	private JButton  btnScenario3;
	private JButton  btnScenario4;
	private JButton  btnQuitter;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FrameChoixScenario ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		Image img       = new ImageIcon ( this.getToolkit ( ).getImage ( "donnees/images/image_scenario.png" ) ).getImage ( );
		Image scaledImg = img.getScaledInstance ( 1000, 700, Image.SCALE_SMOOTH );
		
		Image     icon        = Toolkit.getDefaultToolkit ( ).getImage ( "donnees/images/boat.png" );
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		int l = ( tailleEcran.width  - 1000 ) / 2;
		int h = ( tailleEcran.height -  700 ) / 2;

		this.setSize     ( 1000, 700   );
		this.setLocation (    l,   h   );
		this.setTitle    ( "CinkeTera" );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		JPanel panel         = new JPanel ( new BorderLayout (      )         );
		JPanel panelTest     = new JPanel ( new GridLayout   ( 1, 3 )         );
		JPanel panelButton   = new JPanel ( new GridLayout   ( 7, 1, 15, 15 ) );
		JPanel panelInutile  = new JPanel (                                   );
		JPanel panelInutile2 = new JPanel (                                   );
		
		JLabel j = new JLabel ( new ImageIcon ( scaledImg ) );
		j.setLayout ( new BorderLayout ( ) );

		panel        .setOpaque ( false );
		panelTest    .setOpaque ( false );
		panelButton  .setOpaque ( false );
		panelInutile .setOpaque ( false );
		panelInutile2.setOpaque ( false );

		this.btnScenario1 = new JButton ( "Scenario 1" );
		this.btnScenario2 = new JButton ( "Scenario 2" );
		this.btnScenario3 = new JButton ( "Scenario 3" );
		this.btnScenario4 = new JButton ( "Scenario 4" );
		this.btnQuitter   = new JButton ( "Quitter"    );

		panelButton.add ( this.btnScenario1 );
		panelButton.add ( this.btnScenario2 );
		panelButton.add ( this.btnScenario3 );
		panelButton.add ( this.btnScenario4 );
		panelButton.add ( this.btnQuitter   );

		panelTest.add ( panelInutile  );
		panelTest.add ( panelButton   );
		panelTest.add ( panelInutile2 );

		panel.add ( panelTest, BorderLayout.SOUTH );
		
		j.add ( panel, BorderLayout.CENTER );

		this.getContentPane ( ).add ( j );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnScenario1.addActionListener ( this );
		this.btnScenario2.addActionListener ( this );
		this.btnScenario3.addActionListener ( this );
		this.btnScenario4.addActionListener ( this );
		this.btnQuitter  .addActionListener ( this );

		this.setIconImage             ( icon          );
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( true          );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnScenario1 )
			this.ctrl.lancerScenario (1);
		if ( e.getSource ( ) == this.btnScenario2 )
			this.ctrl.lancerScenario (2);
		if ( e.getSource ( ) == this.btnScenario3 )
			this.ctrl.lancerScenario (3);
		if ( e.getSource ( ) == this.btnScenario4 )
			this.ctrl.lancerScenario (4);
			
		this.cacher();
	}

	public void cacher ( ) { this.dispose ( ); }
}
