package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import GameView.HeroView;
import GameView.SelectHeroButton;
import GameView.SelectOpponentButton;
import exceptions.FullHandException;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;


public class HeroSelect implements ActionListener{
	private HeroView heroView;
	private Hero firstHero;
	private Hero secondHero;

	
	
	public HeroSelect() {
		
		heroView = new HeroView(this);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton currbtn = (JButton) e.getSource();

		if (currbtn instanceof SelectHeroButton) {
			String textBtn = currbtn.getText();

			System.out.println("BUTTON CLICKED WAS: " + textBtn);

			switch (textBtn) {
			case "Hunter":
				
				try {
					firstHero = new Hunter();
					
				} catch (IOException | CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				break;

			case ("Mage"):
				try {
					firstHero = new Mage();
				} catch (IOException | CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			case "Paladin":
				try {
					firstHero = new Paladin();
				} catch (IOException | CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			case ("Priest"):
				try {
					firstHero = new Priest();
				} catch (IOException | CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			case "Warlock":
				try {
					firstHero = new Warlock();
				} catch (IOException | CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			}
			
			System.out.println(firstHero);
			heroView.setupOpponentSelect();
			
		}

			if (currbtn instanceof SelectOpponentButton) {
				String opponentTxt = currbtn.getText();

				System.out.println("BUTTON CLICKED WAS: " + opponentTxt);

				switch (opponentTxt) {
				case "Hunter":
					
					try {
						secondHero = new Hunter();
						
					} catch (IOException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					break;

				case ("Mage"):
					try {
						secondHero = new Mage();
					} catch (IOException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;

				case "Paladin":
					try {
						secondHero = new Paladin();
					} catch (IOException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;

				case ("Priest"):
					try {
						secondHero = new Priest();
					} catch (IOException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;

				case "Warlock":
					try {
						secondHero = new Warlock();
					} catch (IOException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;

				}
				
				
				System.out.println(secondHero);
				this.heroView.hideOpponentFrame();
				
				
				try {
					Game game = new Game(this.getfirstHero(), this.getsecondHero());
				} catch (FullHandException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		
	}



	public Hero getfirstHero() {
		return firstHero;
	}





	public Hero getsecondHero() {
		return secondHero;
	}





	

}
