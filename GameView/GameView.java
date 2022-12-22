package GameView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.heroes.Hero;

public class GameView {
	private JFrame mainFrame;
	private JPanel currentPnl;
	private JPanel middlePnl;
	private JPanel opponentPnl;
	private JPanel game;
	private JPanel hand;
	private JPanel field;
	private JPanel oppHand;
	private JPanel oppField;

	private ActionListener listener;
	private JTextArea firstInfo;
	private JTextArea secondInfo;
	private Hero p1;
	private Hero p2;
	
	
	private ArrayList<JButton> handArray;
	private JButton[] handOppArray;
	private ArrayList<JButton> fieldArray;// not added to GUI yet
	private ArrayList<JButton> fieldOppArray;//we dont fill it yet.
	
	private JButton opponent;
	private JButton endTurn;
	private JButton useHeroPower;
	private JButton me;
	

	public GameView(ActionListener listener, Hero p1, Hero p2) {
		this.setListener(listener);
		this.p1 = p1;
		this.p2 = p2;
		
		setupMainFrame();

	}

	public void setupMainFrame() {

		mainFrame = new JFrame();
		mainFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
	    
   	 	mainFrame.setTitle("Hearth Stone");
   

		mainFrame.setSize(2250, 700);
		mainFrame.setLocation(100, 100);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		Container main = mainFrame.getContentPane();
		// main.setLayout(new BorderLayout(8,6));
		main.setLayout(new GridLayout(3, 1));
		// main.setBackground(Color.YELLOW);
		//mainFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.GREEN));

		opponentPnl = new JPanel();
		opponentPnl.setLayout(new BorderLayout());
		opponentPnl.setBackground(Color.lightGray);
		secondInfo = new JTextArea();
		secondInfo.setPreferredSize(new Dimension(100,100));
		secondInfo.setEditable(false);
		secondInfo.setLineWrap(true);
		this.setInfoP2();

		middlePnl = new JPanel();
		middlePnl.setBackground(Color.lightGray);
		
		

		currentPnl = new JPanel();
		currentPnl.setBackground(Color.cyan);
		
		opponent = new JButton();
		opponent.setText("Opponent");
		opponent.setName("Opponent");
		
		endTurn = new JButton();
		endTurn.setText("End Turn");
		endTurn.setName("End Turn");
		
		useHeroPower = new JButton();
		useHeroPower.setText("Use Hero Power");
		useHeroPower.setName("Use Hero Power");
		
		me = new JButton();
		me.setText("My Hero");
		me.setName("My Hero");
		
		
//		opponentPnl.setLayout(new BorderLayout());
		middlePnl.setLayout(new BorderLayout());
		currentPnl.setLayout(new BorderLayout());
		
		
		
		
		opponentPnl.add(opponent, BorderLayout.EAST);
		
		middlePnl.add(endTurn, BorderLayout.EAST);
		
		
		middlePnl.add(useHeroPower, BorderLayout.WEST);
		currentPnl.add(me, BorderLayout.WEST);
		
		
		firstInfo = new JTextArea();
		firstInfo.setEditable(false);
		firstInfo.setLineWrap(true);
		
		
		hand = new JPanel();
		oppHand = new JPanel();
		field = new JPanel();
		oppField = new JPanel();
	//	hand.setLayout(new GridLayout(1, 11));
		setInfoP1();
		handArray = new ArrayList<JButton>();
		handOppArray = new JButton[10];
		fieldArray= new ArrayList<JButton>();
		fieldOppArray= new ArrayList<JButton>();
		
		
		
//		for (int i = 0; i < fieldArray.size(); i++) {
//			fieldArray.set(i, new JButton());
//			fieldArray.get(i).setPreferredSize(new Dimension(200, 100));
//			
//			
//		}
//		for (int i = 0; i < fieldArray.size(); i++) {
//			field.add(fieldArray.get(i));
//
//		}
//		
//		for (int i = 0; i < fieldOppArray.size(); i++) {
//			fieldOppArray.set(i, new JButton()) ;
//			fieldOppArray.get(i).setPreferredSize(new Dimension(200, 100));
//			fieldOppArray.get(i).setText("EMPTY Opponent");
//			fieldOppArray.get(i).setName("EMPTY");
//			
//		}
//		for (int i = 0; i < fieldOppArray.size(); i++) {
//			oppField.add(fieldOppArray.get(i));
//
//		}
//		
//		for (int i = 0; i < handArray.size(); i++) {
//			handArray.set(i, new JButton())  ;
//			handArray.get(i).setPreferredSize(new Dimension(300, 100));
//		}
//		
//		for (int i = 0; i < handArray.size(); i++) {
//			hand.add(handArray.get(i));
//
//		}
		for (int i = 0; i < handOppArray.length; i++) {
			handOppArray[i]= new JButton() ;
			handOppArray[i].setPreferredSize(new Dimension(100, 100));
			handOppArray[i].setText("???");
			handOppArray[i].setName("????");
		}
		
		for (int i = 0; i < handOppArray.length; i++) {
			oppHand.add(handOppArray[i]);

		}


		this.setHand();
		


		main.add(opponentPnl);

		main.add(middlePnl);

		main.add(currentPnl);

	}

	public ActionListener getListener() {
		return listener;
	}

	public void setListener(ActionListener listener) {
		this.listener = listener;

		
	}
	public void setButtonsListener(ActionListener listener) {
		for(int i =0 ; i< this.handArray.size();i++){
		this.handArray.get(i).addActionListener(listener);}
		
		for(int i =0 ; i< this.fieldArray.size();i++){
			this.fieldArray.get(i).addActionListener(listener);}
		
		for(int i =0 ; i< this.handOppArray.length;i++){
			this.handOppArray[i].addActionListener(listener);}
		
		for(int i =0 ; i< this.fieldOppArray.size();i++){
			this.fieldOppArray.get(i).addActionListener(listener);}
		
		
		opponent.addActionListener(listener);
		endTurn.addActionListener(listener);
		useHeroPower.addActionListener(listener);
		me.addActionListener(listener);
		
		
		
		

		
	}
	
	

	public void switchPlayers() {

	}
	public void test(){
		hand.remove(handArray.get(0));
		
		
	}
	public void setField(int handIndex) {
		handArray.get(handIndex).setVisible(false);
		handArray.remove(handIndex);
		if(p1.getField().size()!=0){
			for (int i = 0; i < p1.getField().size() && i < 7; i++) {
				Card currentCard = (Card) p1.getField().get(i);
				JButton b = new JButton();
				b.setText("\n" + currentCard.getInfo());
				b.setName("field " + currentCard.getName());
				b.setPreferredSize(new Dimension(300,100));
				fieldArray.add(b);
				field.add(b);
				
			}}
		
	}

	public void setHand() {
		for(JButton b: handArray){
			b.setVisible(false);
		}
		for(JButton b: fieldArray){
			b.setVisible(false);
		}
		for(JButton b: fieldOppArray){
			b.setVisible(false);
		}
		handArray.clear();;
		fieldArray.clear();;
		fieldOppArray.clear();;
	
//		System.out.println(handArray.size() + " Size of Hand");
//		System.out.println(fieldArray.size() + " Size of Field");
//		System.out.println(fieldOppArray.size() + " Size of Opponent Field");

		for (int i = 0; i < p1.getHand().size() && i < 10; i++) {
			Card currentCard = (Card) p1.getHand().get(i);
			JButton b = new JButton();
			b.setText("\n" + currentCard.getInfo());
			b.setName("hand " + currentCard.getName());
			b.setPreferredSize(new Dimension(300,100));
			handArray.add(b);
			hand.add(b);
		}

		if(p1.getField().size()!=0){
			for (int i = 0; i < p1.getField().size() && i < 7; i++) {
				Card currentCard = (Card) p1.getField().get(i);
				JButton b = new JButton();
				b.setText("\n" + currentCard.getInfo());
				b.setName("field " + currentCard.getName());
				b.setPreferredSize(new Dimension(300,100));
				fieldArray.add(b);
				field.add(b);
				
			}}

		
		
		
		
		
		if(p2.getField().size()!=0){
		for (int i = 0; i < p2.getField().size() && i < 7; i++) {
			Card currentCard = (Card) p2.getField().get(i);
			JButton b = new JButton();
			b.setText("\n" + currentCard.getInfo());
			b.setName("ofield " + currentCard.getName());
			b.setPreferredSize(new Dimension(300,100));
			fieldOppArray.add(b);
			oppField.add(b);
			
		}
		}
		
		
		


		
	
		
		
		

		currentPnl.add(hand);
		opponentPnl.add(oppHand);
		middlePnl.add(field,BorderLayout.SOUTH);
		middlePnl.add(oppField,BorderLayout.NORTH);
		
		
		
	}

	public void setInfoP2() {

		secondInfo.setText("Name: " + p2.getName() + "\nHP: " + p2.getCurrentHP() + "\nCurrent Mana Crystals: "
				+ p2.getCurrentManaCrystals() + "\nTotal Mana Crystals: " + p2.getTotalManaCrystals()
				+ "\n Cards left in deck: " + p2.getDeck().size() + "\nCards in hand: " + p2.getHand().size());

		opponentPnl.add(secondInfo, BorderLayout.WEST);
		


	}

	
	public void setInfoP1() {

		firstInfo.setText("Name: " + p1.getName() + "\nHP: " + p1.getCurrentHP() + "\nCurrent Mana Crystals: "
				+ p1.getCurrentManaCrystals() + "\nTotal Mana Crystals: " + p1.getTotalManaCrystals()
				+ "\n Cards left in deck: " + p1.getDeck().size() + "\nCards in hand: " + p1.getHand().size());

           hand.add(firstInfo)	;	
		
		

	}
	
	public int gethandArrayIndex(JButton b){
		for(int i =0;i<handArray.size();i++){
			if(b.getText().equals(handArray.get(i).getText()))
				return i;		
		}
		return -1;
	}
	
	public int getfieldArrayOppIndex(JButton b){
		for(int i =0;i<fieldOppArray.size();i++){
			if(b.equals(fieldOppArray.get(i)))
				return i;		
		}
		return -1;
	}
	public int getfieldArrayIndex(JButton b){
		for(int i =0;i<fieldArray.size();i++){
			if(b.equals(fieldArray.get(i)))
				return i;		
		}
		return -1;
	}
	

	
	public void hideFrame() {
		this.mainFrame.setVisible(false);
	}
	
	
//	public void scenario(){
//		try {
//			p1.drawCard();
//		} catch (FullHandException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}}
//		public void actionPerformed(ActionEvent e) {
//	        JButton b = (JButton) e.getSource();
//	        
//		
//		
//		
//		
//	}
	public static void main(String[] args) {
		
	}

	

	
}
