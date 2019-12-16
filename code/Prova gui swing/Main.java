import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Main {

	public static void main ( String [] args ){
		MyFrame m = new MyFrame();
	}

}

class MyFrame extends JFrame {

	JPanel boardPanel = new JPanel();
	JPanel menuPanel = new JPanel();
	JToolBar toolbar = new JToolBar( JToolBar.HORIZONTAL );
	JButton b = new JButton( "0" );
	int counter = 0;

	/*public MyFrame (){
		Container c = this.getContentPane();
		c.setLayout ( new BorderLayout() );
		//this.boardPanel.setLayout( null );
		//c.add( boardPanel );
		b.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed ( ActionEvent e ){
				counter++;
				b.setText( ""+counter );
			}
		} );
		//Image img = ImageIO.read(getClass().getResource("tank.png"));
    	//b.setIcon(new ImageIcon("tank.png"));
		//b.setBackground( Color.RED );  
		//b.setForeground( Color.BLACK );  
		//b.setBounds( 10,10,80,40 ); 
		toolbar.add( new JButton("UNO") );
		toolbar.add( new JButton("DUE") );
		toolbar.add( new JButton("TRE") );
		//boardPanel.add( b );
		menuPanel.add( toolbar );
		menuPanel.setPreferredSize( new Dimension(500,500) );
		c.add( menuPanel, BorderLayout.NORTH );
		this.operazioniAMemoria();
	}*/

	public MyFrame (){
		//setContentPane(new JLabel(new ImageIcon("background.jpg")));
		ImagePanel background = new ImagePanel( new ImageIcon("background.jpg") );
		Container c = this.getContentPane();
		c.add( background );
		c.setLayout ( new BorderLayout() );
		toolbar.add( new JButton("UNO") );
		toolbar.add( new JButton("DUE") );
		toolbar.add( new JButton("TRE") );
		boardPanel.setLayout( null );
		b.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed ( ActionEvent e ){
				counter++;
				b.setText( ""+counter );
			}
		} );
		b.setIcon(new ImageIcon("tank.png"));
		b.setBackground( Color.RED );  
		b.setForeground( Color.BLACK );  
		b.setBounds( 10,10,80,40 ); 
		boardPanel.add( b );
		c.add( boardPanel );
		c.add( toolbar, BorderLayout.SOUTH );
		this.operazioniAMemoria();
	}

	/*public MyFrame (){
		Container c = this.getContentPane();
		c.setLayout( new BorderLayout() );
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		p1.add( new JButton("UNO") );
		p2.add( new JButton("UNO") );
		p3.add( new JButton("UNO") );
		p4.add( new JButton("UNO") );
		p5.add( new JButton("UNO") );

		c.add( p1, BorderLayout.NORTH ); 
		c.add( p2, BorderLayout.SOUTH ); 
		c.add( p3, BorderLayout.EAST ); 
		c.add( p4, BorderLayout.WEST ); 
		c.add( p5, BorderLayout.CENTER ); 
		
		this.operazioniAMemoria();
	}*/

	private void operazioniAMemoria (){
		this.setSize( 500,500 );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setVisible(true); 
	}

}	