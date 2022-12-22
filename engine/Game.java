	package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import GameView.GameView;
import GameView.SelectHeroButton;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.CurseOfWeakness;
import model.cards.spells.DivineSpirit;
import model.cards.spells.FieldSpell;
import model.cards.spells.Flamestrike;
import model.cards.spells.HolyNova;
import model.cards.spells.KillCommand;
import model.cards.spells.LevelUp;
import model.cards.spells.MultiShot;
import model.cards.spells.Polymorph;
import model.cards.spells.Pyroblast;
import model.cards.spells.SealOfChampions;
import model.cards.spells.ShadowWordDeath;
import model.cards.spells.SiphonSoul;
import model.cards.spells.Spell;
import model.cards.spells.TwistingNether;
import model.heroes.Hero;
import model.heroes.HeroListener;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;

public class Game implements ActionValidator, HeroListener, ActionListener {
	private Hero firstHero;
	private Hero secondHero;
	private Hero currentHero;
	private Hero opponent;
	private Card clicked;
	private Minion minionTarget;
	private int clickedOnOpponent;
	private int clickedOnMe;

	private GameListener listener;
	private GameView gameView;
	private JButton target;

	public Game(Hero p1, Hero p2) throws FullHandException, CloneNotSupportedException {
		
		firstHero = p1;
		secondHero = p2;
		clickedOnOpponent=0;
		clickedOnMe=1;
		firstHero.setListener(this);
		secondHero.setListener(this);
		firstHero.setValidator(this);
		secondHero.setValidator(this);
		int coin = (int) (Math.random() * 2);
		currentHero = coin == 0 ? firstHero : secondHero;
		opponent = currentHero == firstHero ? secondHero : firstHero;
		currentHero = p1;
		opponent= p2;
//		currentHero.setCurrentManaCrystals(50);
//		opponent.setCurrentManaCrystals(50);
//		currentHero.setTotalManaCrystals(50);
//		opponent.setTotalManaCrystals(50);
		
			
		currentHero.setCurrentManaCrystals(1);
		currentHero.setTotalManaCrystals(1);
		for (int i = 0; i < 3; i++) {
			currentHero.drawCard();
		}
		for (int i = 0; i < 4; i++) {
			opponent.drawCard();
		}
		
		gameView = new GameView(this,  p1,  p2);
		gameView.setButtonsListener(this);
		
		
	
		

		
		
	}

	@Override
	public void validateTurn(Hero user) throws NotYourTurnException {
		if (user == opponent)
			throw new NotYourTurnException("You can not do any action in your opponent's turn");
	}

	public void validateAttack(Minion a, Minion t)
			throws TauntBypassException, InvalidTargetException, NotSummonedException, CannotAttackException {
		if (a.getAttack() <= 0)
			throw new CannotAttackException("This minion Cannot Attack");
		if (a.isSleeping())
			throw new CannotAttackException("Give this minion a turn to get ready");
		if (a.isAttacked())
			throw new CannotAttackException("This minion has already attacked");
		if (!currentHero.getField().contains(a))
			throw new NotSummonedException("You can not attack with a minion that has not been summoned yet");
		if (currentHero.getField().contains(t))
			throw new InvalidTargetException("You can not attack a friendly minion");
		if (!opponent.getField().contains(t))
			throw new NotSummonedException("You can not attack a minion that your opponent has not summoned yet");
		if (!t.isTaunt()) {
			for (int i = 0; i < opponent.getField().size(); i++) {
				if (opponent.getField().get(i).isTaunt())
					throw new TauntBypassException("A minion with taunt is in the way");
			}

		}

	}

	public void validateAttack(Minion m, Hero t)
			throws TauntBypassException, NotSummonedException, InvalidTargetException, CannotAttackException {
		if (m.getAttack() <= 0)
			throw new CannotAttackException("This minion Cannot Attack");
		if (m.isSleeping())
			throw new CannotAttackException("Give this minion a turn to get ready");
		if (m.isAttacked())
			throw new CannotAttackException("This minion has already attacked");
		if (!currentHero.getField().contains(m))
			throw new NotSummonedException("You can not attack with a minion that has not been summoned yet");
		if (t.getField().contains(m))
			throw new InvalidTargetException("You can not attack yourself with your minions");
		for (int i = 0; i < opponent.getField().size(); i++) {
			if (opponent.getField().get(i).isTaunt())
				throw new TauntBypassException("A minion with taunt is in the way");
		}
	}

	public void validateManaCost(Card c) throws NotEnoughManaException {
		if (currentHero.getCurrentManaCrystals() < c.getManaCost())
			throw new NotEnoughManaException("I don't have enough mana !!");
	}

	public void validatePlayingMinion(Minion m) throws FullFieldException {
		if (currentHero.getField().size() == 7)
			throw new FullFieldException("No space for this minion");
	}

	public void validateUsingHeroPower(Hero h) throws NotEnoughManaException, HeroPowerAlreadyUsedException {
		if (h.getCurrentManaCrystals() < 2)
			throw new NotEnoughManaException("I don't have enough mana !!");
		if (h.isHeroPowerUsed())
			throw new HeroPowerAlreadyUsedException(" I already used my hero power");
	}

	@Override
	public void onHeroDeath() {

		   //listener.onGameOver();
	    gameView.hideFrame();
	      JFrame Window = new JFrame();
	    Window.setSize(800,800);
	    Window.setVisible(true);
	    JLabel Label = new JLabel("Game Over");
	    if(currentHero.getCurrentHP()==0){
	      Label.setText(opponent.getName()+ " WINS!!!");
	    }
	    
	    else{Label.setText(currentHero.getName()+ " WINS!!!");}
	      
	    Window.getContentPane().add(Label);
	    Window.setVisible(true); 

	}

	@Override
	public void damageOpponent(int amount) {

		opponent.setCurrentHP(opponent.getCurrentHP() - amount);
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	@Override
	public void endTurn() throws FullHandException, CloneNotSupportedException {
		
		Hero temp = currentHero;
		currentHero = opponent;
		opponent = temp;
		currentHero.setTotalManaCrystals(currentHero.getTotalManaCrystals() + 1);
		currentHero.setCurrentManaCrystals(currentHero.getTotalManaCrystals());
		currentHero.setHeroPowerUsed(false);
		for (Minion m : currentHero.getField()) {
			m.setAttacked(false);
			m.setSleeping(false);
		}
		currentHero.drawCard();
		gameView.hideFrame();
		gameView= new GameView(this, currentHero, opponent);
		gameView.setButtonsListener(this);
		System.out.println("3ayz ehh men end turn");
		
	}

	public Hero getOpponent() {
		return opponent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton currbtn = (JButton) e.getSource();
		switch (currbtn.getName().substring(0, 4)) {
		case "End ":
			try {
				
				endTurn();
				
			} catch (FullHandException e1) {
				fullHandException();
				
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				System.out.println("Error CloneNotblabla");
			}
			
			
			
			
			break;
		case "Oppo":
			System.out.println("3ayz ehh men Opponent");
			clickedOnOpponent=1;
			clickedOnMe=1;
			break;
		case "My H":
			clickedOnMe=1;
			clickedOnOpponent=0;
			
			break;
		case "????":
			System.out.println("Eeedak w wara2y yasta");
			gameView.test();
			break;
		case "EMPT":
			System.out.println("zorar fady");
			//gameView.test();
			break;
		case "hand":
			int handIndex = gameView.gethandArrayIndex(currbtn);
			System.out.println(handIndex);
			Card c = currentHero.getHand().get(handIndex);
			System.out.println(currbtn.getName());//Debug
			
			if(c instanceof Minion){
				
			Minion m =	(Minion) currentHero.getHand().get(handIndex);
			System.out.println("MINION Played: " + m.getName());
			try {
				currentHero.playMinion(m);
				System.out.println("MINION Played");
				//gameView.setField(handIndex);
				
			} catch (NotYourTurnException e1) {
				
				this.notyourturn();
			} catch (NotEnoughManaException e1) {
				this.notenoughmana();
				
			} catch (FullFieldException e1) {
				
				this.fieldisfull();
			}
			gameView.setHand();
			gameView.setInfoP1();
			gameView.setInfoP2();
			break;
			} 
			
			if(c instanceof Flamestrike){
				System.out.println("Flamestrike");
				try {
					currentHero.castSpell((Flamestrike)c, opponent.getField());
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				}
				
				//gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
				break;
			}
			if(c instanceof Pyroblast){
				System.out.println("Pyroblast");
			
				if(clickedOnOpponent==1){// Button Opponent not made yet
					try {
						currentHero.castSpell((Pyroblast) c, opponent);
					} catch (NotYourTurnException e1) {
						notyourturn();
					} catch (NotEnoughManaException e1) {
						notenoughmana();
					}
					
					System.out.println("Darabt el opponent");
				}
				 	
				else {
					System.out.println("darabt fel field");
				
					
					
				try {
					
					Minion m = (Minion) clicked;
					currentHero.castSpell((Pyroblast) c, m);
					
				} catch (NotYourTurnException e1) {
					notyourturn();
					
				} 
				catch (NotEnoughManaException e1) {
					notenoughmana();
					
					
				}
				catch (java.lang.NullPointerException e1) {
					nullclicked();
					
				}
				catch (InvalidTargetException e1) {
					invalidTarget();
					
				}}
				
				
				
				
				//gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
			break;
			}
			if( c instanceof CurseOfWeakness){
				System.out.println("CurseOfWeakness");
				try {
					currentHero.castSpell((CurseOfWeakness)c, opponent.getField());
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				}
				
			//	gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
				break;
				
			}
			
			if(c instanceof DivineSpirit){
				System.out.println("DivineSpirit");
				
				
					try {
						
							currentHero.castSpell((DivineSpirit)c, (Minion) clicked);
						
					} catch (NotYourTurnException e1) {
						notyourturn();
					} 
					catch (java.lang.NullPointerException e1) {
						nullclicked();
						
					}catch (NotEnoughManaException e1) {
						notenoughmana();
					} catch (InvalidTargetException e1) {
						invalidTarget();
					}
					//gameView.setHand();
					gameView.setInfoP1();
					gameView.setInfoP2();
				
			break;
			}
			
			if(c instanceof HolyNova){
				
				System.out.println("HolyNova");
				try {
					currentHero.castSpell((HolyNova)c, opponent.getField());
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				}
				
				//gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
				break;
				
			}
			if(c instanceof KillCommand){
				System.out.println("KillCommand");
				if(clickedOnOpponent==1){
					try {
						currentHero.castSpell((KillCommand)c, opponent);
					} catch (NotYourTurnException e1) {
						notyourturn();
					} catch (NotEnoughManaException e1) {
						notenoughmana();
					}
				}
				else{
					
					try {
						
								currentHero.castSpell((KillCommand)c,(Minion) clicked);
						
					} catch (NotYourTurnException e1) {
						notyourturn();
					}
					catch (java.lang.NullPointerException e1) {
						nullclicked();
						
					}catch (NotEnoughManaException e1) {
						notenoughmana();
					} catch (InvalidTargetException e1) {
						invalidTarget();
					}
					
					
				}
				//gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
				break;
			}
			
			if(c instanceof LevelUp){
				System.out.println("LevelUp");
				try {
					currentHero.castSpell((FieldSpell) c);
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				}
				gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
				break;
			}
			if(c instanceof MultiShot){
				System.out.println("MultiShot");
				try {
					currentHero.castSpell((MultiShot)c, opponent.getField());
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				}
				
				//gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
				break;
				
			}
			
			if(c instanceof Polymorph){
				System.out.println("Polymorph");
				
				try {
					
					currentHero.castSpell((Polymorph)c, (Minion) clicked);
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				} catch (InvalidTargetException e1) {
					invalidTarget();
				}
				//gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
		break;
				
				
			}
			
			if(c instanceof SealOfChampions){
				System.out.println("SealOfChampions");
				
				try {
					currentHero.castSpell((SealOfChampions)c, (Minion) clicked);
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				} catch (InvalidTargetException e1) {
					invalidTarget();
				}
				catch (java.lang.NullPointerException e1) {
					nullclicked();
					
				}
				//gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
		break;
				
			}
			
			if(c instanceof ShadowWordDeath){
				System.out.println("ShadowWordDeath");
				
				try {
					currentHero.castSpell((ShadowWordDeath)c, (Minion) clicked);
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				} catch (InvalidTargetException e1) {
					invalidTarget();
				}
				catch (java.lang.NullPointerException e1) {
					nullclicked();
					
				}
				//gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
		break;
				
			}
			if(c instanceof SiphonSoul){
			
				try {
					currentHero.castSpell((SiphonSoul)c, (Minion)clicked);
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				}
				catch (java.lang.NullPointerException e1) {
					nullclicked();
					
				}
				//gameView.setHand();
				gameView.setInfoP1();
				gameView.setInfoP2();
				break;
			}
			
		if(c instanceof TwistingNether){
			System.out.println("TwistingNether");
			
			try {
				currentHero.castSpell((TwistingNether)c, opponent.getField());
			} catch (NotYourTurnException e1) {
				notyourturn();
			} catch (NotEnoughManaException e1) {
				notenoughmana();
			}
			//gameView.setHand();
			gameView.setInfoP1();
			gameView.setInfoP2();
			
			break;
		}
		
		
			
			
			
			
			
			
			
			
			
			
			
			
			
			break;
		case "fiel":
			System.out.println(currbtn.getName());
			int i = gameView.getfieldArrayIndex(currbtn);
			Minion attacker = (Minion) currentHero.getField().get(i);
			minionTarget = attacker;
			if(clickedOnOpponent==1){
				try {
					currentHero.attackWithMinion(attacker, opponent);
					iAttacked();
				} catch (exceptions.CannotAttackException e1) {
					CannotAttackException();
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (exceptions.TauntBypassException e1) {
					TauntBypassException();
				} catch (NotSummonedException e1) {
					notsummonedexception();
				} catch (InvalidTargetException e1) {
					invalidTarget();
				}
			}
			else{
			//attacker.attack((Minion) clicked);
			try {
				currentHero.attackWithMinion(attacker, (Minion) clicked);
				iAttacked();
			} catch (CannotAttackException e1) {
				CannotAttackException();
			} catch (NotYourTurnException e1) {
				notyourturn();
			} catch (TauntBypassException e1) {
				TauntBypassException();
			} catch (InvalidTargetException e1) {
				invalidTarget();
			} catch (NotSummonedException e1) {
				notsummonedexception();
			}}
			
			clicked = attacker;
			gameView.setHand();
			gameView.setInfoP1();
			gameView.setInfoP2();
			
			break;
			
		case "ofie":
			System.out.println(currbtn.getName());
			int j = gameView.getfieldArrayOppIndex(currbtn);
			clicked= opponent.getField().get(j);
			clickedOnOpponent=0;
			
			
			
			break;
			
		case "Use ":
			System.out.println("Button works");
			if(currentHero instanceof Hunter){
				try {
					((Hunter) currentHero).useHeroPower();
					heroPowerDone();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				} catch (HeroPowerAlreadyUsedException e1) {
					heropoweralreadyused();
				} catch (NotYourTurnException e1) {
					 notyourturn();
				} catch (FullHandException e1) {
					fullHandException();
				} catch (FullFieldException e1) {
					fullfieldexception();
				} catch (CloneNotSupportedException e1) {
					clonenotsupported();
				}
			}
			if(currentHero instanceof Mage){
				if(clickedOnOpponent==1){
					try {
						((Mage) currentHero).useHeroPower(opponent);
						heroPowerDone();
					} catch (NotEnoughManaException e1) {
						notenoughmana();
					} catch (HeroPowerAlreadyUsedException e1) {
						heropoweralreadyused();
					} catch (NotYourTurnException e1) {
						notyourturn();
					} catch (FullHandException e1) {
						fullHandException();
					} catch (FullFieldException e1) {
						fullfieldexception();
					} catch (CloneNotSupportedException e1) {
						clonenotsupported();
					}
				}
				else if(clickedOnMe==1){
					try {
						((Mage) currentHero).useHeroPower(currentHero);
						heroPowerDone();
					} catch (NotEnoughManaException e1) {
						notenoughmana();
					} catch (HeroPowerAlreadyUsedException e1) {
						heropoweralreadyused();
					} catch (NotYourTurnException e1) {
						notyourturn();
					} catch (FullHandException e1) {
						fullHandException();
					} catch (FullFieldException e1) {
						fullfieldexception();
					} catch (CloneNotSupportedException e1) {
						clonenotsupported();
					}
				}
				else
				{
					try {
						((Mage) currentHero).useHeroPower((Minion)clicked);
						heroPowerDone();
					} catch (NotEnoughManaException e1) {
						notenoughmana();
					} catch (HeroPowerAlreadyUsedException e1) {
						heropoweralreadyused();
					} catch (NotYourTurnException e1) {
						notyourturn();
					} catch (FullHandException e1) {
						fullHandException();
					} catch (FullFieldException e1) {
						fullfieldexception();
					} catch (CloneNotSupportedException e1) {
						clonenotsupported();
					}
				}
				
			}
			if(currentHero instanceof Paladin){
				try {
					((Paladin) currentHero).useHeroPower();
					heroPowerDone();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				} catch (HeroPowerAlreadyUsedException e1) {
					heropoweralreadyused();
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (FullHandException e1) {
					fullHandException();
				} catch (FullFieldException e1) {
					fullfieldexception();
				} catch (CloneNotSupportedException e1) {
					clonenotsupported();
				}
				
			}
			if(currentHero instanceof Priest){
				if(clickedOnOpponent==1){
					try {
						((Priest) currentHero).useHeroPower(opponent);
						heroPowerDone();
					} catch (NotEnoughManaException e1) {
						notenoughmana();
					} catch (HeroPowerAlreadyUsedException e1) {
						heropoweralreadyused();
					} catch (NotYourTurnException e1) {
						notyourturn();
					} catch (FullHandException e1) {
						fullHandException();
					} catch (FullFieldException e1) {
						fullfieldexception();
					} catch (CloneNotSupportedException e1) {
						clonenotsupported();
					}
				}
				else if(clickedOnMe==1){
					try {
						((Priest) currentHero).useHeroPower(currentHero);
						heroPowerDone();
					} catch (NotEnoughManaException e1) {
						notenoughmana();
					} catch (HeroPowerAlreadyUsedException e1) {
						heropoweralreadyused();
					} catch (NotYourTurnException e1) {
						notyourturn();
					} catch (FullHandException e1) {
						fullHandException();
					} catch (FullFieldException e1) {
						fullfieldexception();
					} catch (CloneNotSupportedException e1) {
						clonenotsupported();
					}
				}
				else{
				try {
					((Priest) currentHero).useHeroPower((Minion) clicked);
					heroPowerDone();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				} catch (HeroPowerAlreadyUsedException e1) {
					heropoweralreadyused();
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (FullHandException e1) {
					fullHandException();
				} catch (FullFieldException e1) {
					fullfieldexception();
				} catch (CloneNotSupportedException e1) {
					clonenotsupported();
				}
				}
			}
			if(currentHero instanceof Warlock){
				try {
					((Warlock) currentHero).useHeroPower();
					heroPowerDone();
				} catch (NotEnoughManaException e1) {
					notenoughmana();
				} catch (HeroPowerAlreadyUsedException e1) {
					heropoweralreadyused();
				} catch (NotYourTurnException e1) {
					notyourturn();
				} catch (FullHandException e1) {
					fullHandException();
				} catch (FullFieldException e1) {
					fullfieldexception();
				} catch (CloneNotSupportedException e1) {
					clonenotsupported();
				}
	
			}
			gameView.setHand();
			gameView.setInfoP1();
			gameView.setInfoP2();
			
			break;

		default:
			
			
			
			
			
			break;
		}
		
	
//		gameView.setInfoP1();
//		gameView.setInfoP2();
//		//gameView.setHand();
		
		
		
		
	}
	private void clonenotsupported() {
		JFrame Window = new JFrame();
		Window.setSize(500,500);
		Window.setVisible(true);
		JLabel Label = new JLabel("Clone Not Supported");
		Window.getContentPane().add(Label);
		Window.setVisible(true);}
	
	private void heropoweralreadyused() {
		JFrame Window = new JFrame();
		Window.setSize(500,500);
		Window.setVisible(true);
		JLabel Label = new JLabel("hero power already used");
		Window.getContentPane().add(Label);
		Window.setVisible(true);}
	
	private void fullHandException() {
		JFrame Window = new JFrame();
		Window.setSize(500,500);
		Window.setVisible(true);
		JLabel Label = new JLabel("Your Hand is Full");
		Window.getContentPane().add(Label);
		Window.setVisible(true);
		
	}
	private void fullfieldexception() {
		JFrame Window = new JFrame();
		Window.setSize(500,500);
		Window.setVisible(true);
		JLabel Label = new JLabel("Your Field is Full");
		Window.getContentPane().add(Label);
		Window.setVisible(true);
		
	}
	

	public void notyourturn(){
		JFrame Window = new JFrame();
	Window.setSize(500,500);
	Window.setVisible(true);
	JLabel Label = new JLabel("Not Your Turn");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }
	
	public void nullclicked(){
		JFrame Window = new JFrame();
	Window.setSize(500,500);
	Window.setVisible(true);
	JLabel Label = new JLabel("Click on target before casting spell");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }
	
	public void fieldisfull(){
		JFrame Window = new JFrame();
	Window.setSize(500,500);
	Window.setVisible(true);
	JLabel Label = new JLabel("Field is Full");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }
	
	public void notenoughmana(){
		JFrame Window = new JFrame();
	Window.setSize(500,500);
	Window.setVisible(true);
	JLabel Label = new JLabel("Not enough mana");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }
	
	public void invalidTarget(){
		JFrame Window = new JFrame();
	Window.setSize(500,500);
	Window.setVisible(true);
	JLabel Label = new JLabel("Invalid Target");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }
	
	

	public void notsummonedexception(){
		JFrame Window = new JFrame();
	Window.setSize(500,500);
	Window.setVisible(true);
	JLabel Label = new JLabel("You can not attack a minion that your opponent has not summoned yet");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }
	
	
	public void CannotAttackException(){
		JFrame Window = new JFrame();
	Window.setSize(500,500);
	Window.setVisible(true);
	JLabel Label = new JLabel("Cannot Attack");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }
	
	

	public void TauntBypassException(){
		JFrame Window = new JFrame();
	Window.setSize(500,500);
	Window.setVisible(true);
	JLabel Label = new JLabel("TauntBypass Exception");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }
	
	public void iAttacked(){
		JFrame Window = new JFrame();
	Window.setSize(400,200);
	Window.setVisible(true);
	JLabel Label = new JLabel("Attack Done");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }

	public void heroPowerDone(){
		JFrame Window = new JFrame();
	Window.setSize(400,200);
	Window.setVisible(true);
	JLabel Label = new JLabel("Hero Power Done");
	Window.getContentPane().add(Label);
	Window.setVisible(true); }
	
	public static void main(String[] args) throws FullHandException, CloneNotSupportedException {
	
		HeroSelect hv = new HeroSelect();
		
		
		
	}
	
}
