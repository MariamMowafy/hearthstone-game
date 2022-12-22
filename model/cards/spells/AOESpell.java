package model.cards.spells;

import java.util.ArrayList;

import model.cards.Card;
import model.cards.minions.Minion;

public interface AOESpell {
	public void performAction(ArrayList<Card> oppField, ArrayList<Card> curField);
}
