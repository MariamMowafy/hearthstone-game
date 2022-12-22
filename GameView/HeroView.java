package GameView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HeroView {
	private JFrame selectHeroFrame;
	private JFrame selectOpponentFrame;

	private JPanel heroPanel;
	private JPanel opponentPanel;
	private ActionListener listener;
	ArrayList<String> names = new ArrayList<String>( Arrays. asList( "Hunter" , "Mage" , "Paladin" , "Priest" , "Warlock") );
	//ArrayList<ImageIcon> images= new ArrayList<ImageIcon>(Arrays.asList(new ImageIcon("Hunter.png"), new ImageIcon("Mage.png"), new ImageIcon("Paladin.png"),new ImageIcon("Priest.png"),new ImageIcon("Warlock.png")));
	
	
	
	public HeroView(ActionListener listener) {
			this.listener = listener;
		
			setupHeroSelect();

		
	}
	
	public void setupHeroSelect() {
		selectHeroFrame= new JFrame();
		
		selectHeroFrame.setSize(1000,400);
		selectHeroFrame.setLocation(100,100);
		selectHeroFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectHeroFrame.setVisible(true);
		Container main = selectHeroFrame.getContentPane();
		main.setLayout(new BorderLayout(8,6));
		//main.setBackground(Color.YELLOW);
		selectHeroFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.BLACK));
		
		
		
		this.heroPanel = new JPanel(new GridLayout(5, 0));
		heroPanel.setPreferredSize(new Dimension(1000,400));
		
		JButton[] heroes= new JButton[5];
		for (int i = 0; i < heroes.length; i++) {
			SelectHeroButton b = new SelectHeroButton(names.get(i));
			//b.setIcon(images.get(i));
			b.addActionListener(listener);
				//b.setIcon(defaultIcon);
				
				heroes[i] = b;
				heroPanel.add(b);
			
		}
		
		main.add(heroPanel, BorderLayout.CENTER);

	}
	
	public void setupOpponentSelect() {
		
		selectHeroFrame.setVisible(false);

		
		selectOpponentFrame= new JFrame();
		
		selectOpponentFrame.setSize(1000,400);
		selectOpponentFrame.setLocation(100,100);
		selectOpponentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectOpponentFrame.setVisible(true);
		Container main = selectOpponentFrame.getContentPane();
		main.setLayout(new BorderLayout(8,6));
		//main.setBackground(Color.YELLOW);
		selectOpponentFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.BLACK));
		
		
		
		this.opponentPanel = new JPanel(new GridLayout(5, 0));
		opponentPanel.setPreferredSize(new Dimension(1000,400));
		
		JButton[] opponents= new JButton[5];
		for (int i = 0; i < opponents.length; i++) {
			SelectOpponentButton b = new SelectOpponentButton(names.get(i));
				//b.setIcon(defaultIcon);
				
				opponents[i] = b;
				opponentPanel.add(b);
				b.addActionListener(listener);

			
		}
		
		main.add(opponentPanel, BorderLayout.CENTER);

	}
	
	public void hideOpponentFrame() {
		this.selectOpponentFrame.setVisible(false);
	}
	
}
