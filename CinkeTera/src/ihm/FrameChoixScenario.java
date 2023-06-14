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

	private JButton  btnScenario;
	private JButton  btnQuitter;
	private JButton  btn2J;
	private JButton  btn1J;

	private Controleur ctrl;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * 
	 * 
	 */
	public FrameChoixScenario(Controleur ctrl)
	{
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		this.ctrl = ctrl;
		
		int l = ( tailleEcran.width  - 1000 ) / 2;
		int h = ( tailleEcran.height -  700 ) / 2;

		this.setSize     ( 1000, 700   );
		this.setLocation (    l,   h   );
		this.setTitle    ( "CinkeTera" );

		JPanel panelTest = new JPanel ( );
		panelTest.setOpaque ( false );
		panelTest.setLayout ( new GridLayout ( 1, 3 ) );

		JPanel panel = new JPanel ( new BorderLayout ( ) );
		panel.setOpaque ( false );

		JPanel panelButton = new JPanel ( );
		panelButton.setOpaque ( false );

		this.btn1J       = new JButton ( "Scenario 1" );
		this.btn2J       = new JButton ( "Scenario 2" );
		this.btnScenario = new JButton ( "Scenario 3" );
		this.btnQuitter  = new JButton ( "Quitter"  );

		panelButton.add ( this.btn1J       );
		panelButton.add ( this.btn2J       );
		panelButton.add ( this.btnScenario );
		panelButton.add ( this.btnQuitter  );

		panelButton.setLayout ( new GridLayout ( 2,1, 15, 15 ) );

		JPanel panelInutile = new JPanel ( );
		panelInutile.setOpaque ( false );
		JPanel panelInutile2 = new JPanel ( );
		panelInutile2.setOpaque ( false );

		panelTest.add ( panelInutile  );
		panelTest.add ( panelButton   );
		panelTest.add ( panelInutile2 );

		panel.add ( panelTest, BorderLayout.SOUTH );

		Image img       = new ImageIcon ( this.getToolkit ( ).getImage ( "donnees/images/image_scenario.png" ) ).getImage ( );
		Image scaledImg = img.getScaledInstance ( 1000, 700, Image.SCALE_SMOOTH );

		panelButton.setLayout ( new GridLayout ( 6, 1, 15, 15 ) );

		JLabel j = new JLabel ( new ImageIcon ( scaledImg ) );
		j.setLayout ( new BorderLayout ( ) );
		j.add ( panel, BorderLayout.CENTER );


		this.getContentPane ( ).add ( j );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btn1J      .addActionListener ( this );
		this.btn2J      .addActionListener ( this );
		this.btnScenario.addActionListener ( this );
		this.btnQuitter .addActionListener ( this );

		//Mettre un icone peu utile en image de logiciel
		Image icon = Toolkit.getDefaultToolkit ( ).getImage ( "donnees/images/boat.png" );

		this.setIconImage             ( icon          );
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( true          );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btn1J )
			this.ctrl.lancerScenario (1);
		if ( e.getSource ( ) == this.btn2J )
			this.ctrl.lancerScenario (0);
		if ( e.getSource ( ) == this.btnScenario )
			this.ctrl.lancerScenario (3);
			

		this.cacher();
	}

	public void cacher ( ) { this.dispose ( ); }

}
