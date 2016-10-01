import java.util.Scanner;
import java.util.Stack;
import java.lang.*;
import java.io.*;

/* 
	David Monteiro - 10364119
	Chee Kang Kong - 12369711

	This program reclassifies the hand types by checking if the player' hand interacts with the flop.
	It also recalculates the values for mean, variance and standard deviation.
*/

public class CleanDataset{

	public static boolean havePair(int [] hand, int [] table){
		for(int i = 0; i < table.length; i++){
			if(i%2!=0){
				if (hand[1] == hand[3]) return true;
				else if(hand[1] == table[i]) return true;
				else if(hand[3] == table[i]) return true;
			}
		}
		return false;
	}

	public static boolean haveDouble(int [] hand, int [] table){
		for(int i = 0; i < table.length; i++){
			if(i%2!=0){
				if(hand[1] == table[i] && hand[3] == table[i] || hand[1] != hand[3]) return true;
			}
		}
		return false;
	}
	
	public static boolean haveThree(int [] hand, int [] table){
		for(int i = 0; i < table.length; i++){
			if(i%2!=0){
				if(hand[1] == table[i] || hand[3] == table[i]) return true;
			}
			if(table[3] == table[1] && table[3] == hand[1] || table[3] == table[5] && table[3] == hand[3]){
				return true;
			}
		}
		return false;
	}

	public static void main (String [] args){
		try{
			Scanner in = new Scanner(new File(args[0]));
			PrintWriter out = new PrintWriter("cleanedPokerHand.txt");

			String num; 
			int dig = 0;

			int [] myHand = new int [4];
			int [] flop = new int [6];

			int inputLength = 0;
			double mean = 0;
			double variance = 0;
			double standardDeviation = 0;
			
			int [] count = new int [10];
			for(int i = 0; i<count.length; i++){
				count[i]=0;
			}

			Stack <Integer> stack = new Stack<>();

			while(in.hasNext()){

				num = "";
				num = num + in.next();
				char last = num.charAt(num.length()-1);
				num = num.substring(0,num.length()-1);
				String temp = "";
				int j = 0;

				while(j<10){
					for(int i = 0; i<num.length(); i++){
						if(num.charAt(i) == ','){
							temp = num.substring(0,i);
							dig = Integer.parseInt(temp);
							num = num.substring(i+1);
							i = i - temp.length();
							if(j<4){ 
								myHand[j] = dig;
							}
							else{
								flop[j-4] = dig;
							}
							j++;
						}
					}
				}

				dig = (int)(Character.getNumericValue(last));
				if(dig == 1 && !havePair(myHand, flop)) dig=0;
				if(dig == 2 && !haveDouble(myHand, flop)) dig=0;
				if(dig == 3 && !haveThree(myHand, flop)) dig=0;

				mean += dig;
				inputLength++;

				stack.push(dig);

				for(int i = 0; i<count.length; i++){
					if(dig == i) count[i]++;
				}

				for(int i = 0; i<10; i++){
					if(i<4){ out.print(myHand[i]);}
					else{ out.print(flop[i-4]);}
					out.print(",");
				}
				out.print(dig + "\n");
			}

			in.close();
			out.close();
  
			mean = mean / inputLength;
			
			int n;
			while(!stack.isEmpty()){
				n = stack.pop();
				variance += (n-mean)*(n-mean);
			}

			variance = variance / inputLength;
			standardDeviation = Math.sqrt(variance);

			for(int i = 0; i<count.length; i++){
				System.out.print(i+"\t"+count[i]+"\n");
			}

			System.out.println("Mean is: " + mean);
			System.out.println("Variance is: " + variance);
			System.out.println("Standard Deviation is: " + standardDeviation);

		}
		catch(IOException e){
			System.out.println("File unreadable");
			e.printStackTrace();
		}

	}

}