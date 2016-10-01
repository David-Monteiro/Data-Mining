import java.util.Scanner;
import java.util.Stack;
import java.util.HashSet;
import java.lang.*;
import java.io.*;

/* 
	David Monteiro - 10364119
	Chee Kang Kong - 12369711

	This program determines the risk involved in betting with a hand supplied as a parameter (from our cleaned dataset).
*/

public class RiskCalculator{

	public static boolean equalCards(int [] cards, int size){
		//Here we are making sure that there are no two equal cards 
		for(int i = 0; i < size-1; i++){
			for(int j = i+1; j < size; j++){
				if (cards[i] == cards[j]) return true;
			}
		}
		for(int i = (cards.length-size); i < cards.length-1; i++){
			for(int j = i+1; j < cards.length; j++){
				if (cards[i] == cards[j]) return true;
			}
		}
		return false;
	}

	public static boolean orderedCard(int [] hand, int [] table, int numApart) { 
		//Reads cards from hand and table saves only the card number into a created array
		//After storing data, we sort the array.
		//After Compare and return		
		int [] cards = new int[5];
		for(int i = 0; i<5; i++){
			if(i<2)		cards[i] = hand[(i*2)+1];
			else		cards[i] = table[((i-1)*2)-1];
		}
		int i = 0; 
		while (i<cards.length-1){
			int min = i;
			int j = i+1;
	
			while (j<cards.length){
				if( cards[j] < cards[min] ){
					min = j;
				}
				j++;
			}  
			int temp = cards[i];
			cards[i] = cards[min];
			cards[min] = temp;
			i++;
		}
		if(numApart == 4){
			if((cards[3] <= cards[0]+numApart || cards[4] <= cards[1]+numApart) && !equalCards(cards, numApart) ) return true;
		}
		if(numApart == 3){
			if((cards[2] <= cards[0]+numApart || cards[3] <= cards[1]+numApart || cards[4] <= cards[2]+numApart) && !equalCards(cards, numApart)) return true;
		}
		return false;
	}

	public static boolean sameSuit (int [] hand, int [] table, int num) {
		HashSet<Integer> suits = new HashSet<>();
		for (int i= 0; i < hand.length; i++) {
			if (i%2==0) {
				suits.add(hand[i]);
			}
		}
		for(int i= 0; i < table.length; i++) {
			if (i%2==0) {
				suits.add(table[i]);
			}
		}
		return (suits.size()>=(num-1));
	}

	public static boolean highCard(int [] hand){
		//See if player has a Card of 10 or above
		//If yes, return true else false
		for(int i = 0; i<hand.length; i++){
			if(hand[i] >= 10) return true;
			if(hand[i] == 1 && i%2 != 0) return true;
		}
		return false;
	}
	
	public static void main (String [] args){

		int [] myHand =  new int [4];
		int [] flop = new int [6];
		char last;
		int handClass;

		System.out.println("Please copy an entry from the cleaned dataset and paste it here:");
		String input = System.console().readLine();
		if (input.length() > 25) {
			System.out.println("Input is too long.");
		}
		else if (input.length() < 20) {
			System.out.println("Input is too short.");
		}
		else {
			int j = 0;
			int dig = 0;
			String temp = "";
			last = (input.charAt(input.length() - 1));
			while (j < 10) {
				for (int i = 0; i < input.length(); i++) {
					if (input.charAt(i) == ',') {
						temp = input.substring(0,i);
						dig = Integer.parseInt(temp);
						input = input.substring(i+1);
						i = i - temp.length();
						if (j < 4) {
							myHand[j] = dig;
							if(j % 2 == 0) System.out.print("Hand " + j + " :\tSuit- " + myHand[j]);
							else System.out.print("\tCard- " + myHand[j] + "\n");
						}
						else {
							flop[j-4] = dig;
							if(j % 2 == 0) System.out.print("Flop " + (j-4) + " :\tSuit- " + flop[j-4]);
							else System.out.print("\tCard- " +flop[j-4] + "\n");
						}
						j++;
					}
				}
			}
			
			handClass = (int)(Character.getNumericValue(last));
			if (handClass > 1) {
				System.out.println("Worth a bet - better than One Pair, good position to be in at this stage.");
			}
			else if(orderedCard(myHand, flop, 4) || sameSuit(myHand, flop, 4)) {
				System.out.println("Worth a bet - good chance of getting Straight/Flush.");
			}
			else if(orderedCard(myHand, flop, 3) || sameSuit(myHand, flop, 3)) {
				System.out.println("Risky bet - Slim chance of getting Straight/Flush.");
			}
			else if(handClass == 1) {
				System.out.println("Risky bet - One Pair in hand, decent enough to place a bet.");
			}
			else if (highCard(myHand)) {
				System.out.println("Very risky bet - Very slim chance to win, you have Nothing in Hand but a High Card.");
			}
			else {
				System.out.println("Extremely risky bluff - Nothing in Hand.");
			}
		}
	}
}