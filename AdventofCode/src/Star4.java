import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
	
public class Star4 {

	/*
	 * For visualization
	 *  	----------------		---------------------
	 * 		|	    1 	    |		|		  1         |
	 * 		|  	 2  3  4	|		|	  2   3   4     |
	 * 		| 5  6  7  8  9 | ===> 	| 5   6	  7   8   9 |
	 * 		|	 A  B  C	|		|     10  11  12    |
	 * 		|	    D	    |		|		  13        |
	 * 		----------------		---------------------
	 */
		
	// Function used to track the code for the Bathroom
	private static ArrayList<Integer> code = new ArrayList<Integer>();
		
	// Function used to get the bathroom code from the instructions
	public static void readFile(String input_file) {
		try {
			// Open the input file
			FileReader fr = new FileReader(input_file);
			BufferedReader br = new BufferedReader(fr);
				
			// To be used to store the different instructions  
			ArrayList<String[]> instructions = new ArrayList<String[]>(); 
				
			// Read through the file and add into the instructions array
			String line = null;
			while ((line = br.readLine()) != null) {
				instructions.add(line.split(""));
			}
				
			// Go through the instructions
			for (int i = 0; i < instructions.size(); i++) {
				// The start digit is initially 5, otherwise it is the previous digit that was entered in code
				int start;
				if (code.size() == 0)
					start = 5;
				else
					start = code.get(code.size() - 1);
					
				// Go through each individual movements
				for(int j = 0; j < instructions.get(i).length; j++) {
					// Move the number accordingly to the keypad image above
					switch (instructions.get(i)[j]) {
						case "U":	if (start == 1 || start == 2 || start == 4 || start == 5 || start == 9)
										break;
									else {
										if (start == 13 || start == 3)
											start -= 2;
										else
											start -= 4;
									}
									break;
						case "D":	
									if (start ==  5 || start == 9 || start == 10 || start == 12 || start == 13)
										break;
									else {
										if (start == 1 || start == 11)
											start += 2;
										else
											start += 4;
									}
									break;
						case "R":	
									if (start == 1 || start == 4 ||start == 9 || start == 12 || start == 13)
										break;
									else
										start += 1;
									break;
						case "L":	
									if (start == 1 || start == 2 || start == 5 || start == 10 || start == 13)
										break;
									else
										start -= 1;
									break;
					}
				}
					
				// Add the number into code 
				code.add(start);
			}	
				
			br.close();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	// Function used to print the code found according to the keypad image above
	public static void getCode() {
		String printing = "";
		for (Integer i : code) {
			if (i == 10)
				printing += "A";
			else if (i == 11)
				printing += "B";
			else if (i == 12)
				printing += "C";
			else if (i == 13)
				printing += "D";
			else
				printing += i;
		}
		System.out.println(printing);
	}
		
	public static void main (String[] argvs) {
		readFile("Star3_input.txt");
		getCode();
	}
}
