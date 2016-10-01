import java.util.Scanner;
import java.util.Stack;
import java.lang.*;
import java.io.*;

/* 
	David Monteiro - 10364119
	Chee Kang Kong - 12369711

	This program calculates the number of occurences of each type of Poker Hand (Nothing in Hand -> Royal Flush).
	It also calculates the mean (average) type of hand, the variance and the standard deviation.
*/
public class AnalyseDataset{
	public static void main (String [] args){
		try{
			Scanner in = new Scanner(new File(args[0]));

			String num;
			int dig; 
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
				char temp = num.charAt(num.length()-1);

				dig = (int)(Character.getNumericValue(temp));
				mean += dig;
				inputLength++;

				stack.push(dig);

				for(int i = 0; i<count.length; i++){
					if(dig == i) count[i]++;
					
				}
			}
			in.close();

			for(int i = 0; i<count.length; i++){
				System.out.print(i+"\t"+count[i]+"\n");
			}
			mean = mean / inputLength;

			int n;
			while(!stack.isEmpty()){
				n = stack.pop();
				variance += (n-mean)*(n-mean);
			}

			variance = variance / inputLength;
			standardDeviation = Math.sqrt(variance);

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