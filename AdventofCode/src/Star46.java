import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Star46 {
	
	//Instructions the instructions 
	static ArrayList<String> instructions;
			
	// Function that reads the file input, get the instructions, and stores them
	public static void getInstructions(String file_name) {
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
					
			String line = null;
			instructions = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				instructions.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Function to calculate the factorial
	public static int getFactorial(int num) {
		if (num == 1)
			return 1;
		return num * getFactorial(num - 1);
	}

	// Value found to be the number of eggs factorial + (first input in instruction 19 * first input in instruction 20) after looking at patterns
	public static int getAValue(String file_name) {
		getInstructions(file_name);
		return getFactorial(12) + (Integer.parseInt(instructions.get(19).split(" ")[1]) * Integer.parseInt(instructions.get(20).split(" ")[1]));
	}

	public static void main(String[] argvs) {
		System.out.println("Value at register a = " + getAValue("Star45_input.txt"));
	}
}
