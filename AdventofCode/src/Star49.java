import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Star49 {
	
	// Registers and Instructions tracks the registers and stores the instructions respectively.
	// Counter tracks the current instruction. transmit tracks the transmitting clock signal.
	static HashMap<String, Integer> registers;
	static ArrayList<String> instructions;
	static int counter;
	static String transmit;
				
	// Function that initializes the Registers (a,b,c,d) and set register a to a_value
	public static void initializeRegisters(int a_value) {
		registers = new HashMap<String, Integer>();
		String[] names = {"a", "b", "c", "d"};
		// Initializes the registers to 0
		for(int i = 0; i < 4; i++)
			registers.put(names[i], 0);
		registers.put("a", a_value);
		transmit = "";
	}
				
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
				
	// Function that runs the instructions until the last instruction is exited
	public static void runInstructions() {
		counter = 0;
		while (counter < instructions.size()) {
			String[] instr_arr = instructions.get(counter).split(" ");
			if (instr_arr[0].equals("cpy"))
				copy(instr_arr[1], instr_arr[2]);
			else if (instr_arr[0].equals("inc"))
				inc(instr_arr[1]);
			else if (instr_arr[0].equals("dec"))
				dec(instr_arr[1]);
			else if (instr_arr[0].equals("jnz")){
				jnz(instr_arr[1], instr_arr[2]);
				continue;
			}
			else {
				// Add to the transmitting signal
				out(instr_arr[1]);
				// If not matching the continous 01, return
				if (transmit.length() % 2 == 0 && !transmit.matches("(01)+"))
					return;
				// If matched to (01)+ to up to 50 digits, return
				if (transmit.length() > 50)
					return;
			}
			counter++;
		}
	}

	// Function used to find the register a value to get a continous (01)+ clock signal
	public int findAGetRepeating(String file_name) {
		getInstructions(file_name);
		int a = 0;
		while (true) {
			initializeRegisters(a);
			runInstructions();
			// If all 50 digits are found to be continous, return a
			if (transmit.length() > 50)
				break;
			// It is found that the transmit would only start with 01 for each multiple of 4 for a
			a+=4;
		}
		return a;
	}

	// Function used to perform the copy instruction
	public static void copy(String from, String reg) {
		// Check if valid instruction, skip instruction if invalid
		if (!reg.matches("[abcd]"))
			return;
				
		// Copy the value from a register to reg if from is a register
		if (from.matches("[abcd]"))
			registers.put(reg, registers.get(from));
		// Otherwise, copy the integer value of from into reg
		else
			registers.put(reg, Integer.parseInt(from));
	}
				
	// Function used to increment the value in reg
	public static void inc(String reg) {
		registers.put(reg, registers.get(reg) + 1);
	}
				
	// Function used to decrement the value in reg
	public static void dec(String reg) {
		registers.put(reg, registers.get(reg) - 1);
	}
				
	// Function used to perform the jump function
	public static void jnz(String reg, String num) {
		// If looking into a register and it is 0 or if looking at a num and it is 0, do not jump
		if ((registers.containsKey(reg) && registers.get(reg) == 0) || reg.equals("0"))
			counter++;
		// Otherwise, jump to instruction num away if end counter is not less than 0
		else if (registers.containsKey(num) && (counter + registers.get(num) >= 0))
			counter += registers.get(num);
		else if (counter + Integer.parseInt(num) < 0)
			counter++;
		else
			counter += Integer.parseInt(num);
	}

	// Function used to add to the transmit signal
	public static void out(String reg) {
		if (registers.containsKey(reg))
			transmit += registers.get(reg);
		else
			transmit += reg;
	}	

	public static void main(String[] argvs) {
		Star49 pgm = new Star49();
		System.out.println("Register a has to be initialized this value to be repeating => " + pgm.findAGetRepeating("Star49_input.txt"));
	}
}
