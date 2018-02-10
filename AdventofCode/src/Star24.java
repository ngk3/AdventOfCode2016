import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Star24 {
		
	// Registers and Instructions tracks the registers and stores the instructions respectively. Counter tracks the current instruction
	static HashMap<String, Integer> registers;
	static ArrayList<String> instructions;
	static int counter;
		
	// Function that initializes the Registers (a,b,c,d)
	public static void initializeRegisters() {
		registers = new HashMap<String, Integer>();
		String[] names = {"a", "b", "c", "d"};
		// Initializes the registers to 0
		for(int i = 0; i < 4; i++)
			registers.put(names[i], 0);
		// This line added for Part 2 (Initialize register c to 1). Remove this line to get answer to Part 1
		registers.put("c", 1);
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
			else {
				jnz(instr_arr[1], instr_arr[2]);
				continue;
			}
			counter++;
		}
	}
		
	// Function used to get the contents of an register (Assumes only a,b,c,d will be checked for)
	public static int getRegister(String reg) {
		return registers.get(reg);
	}
		
	// Function used to perform the copy instruction
	public static void copy(String from, String reg) {
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
		// Otherwise, jump to instruction num away
		else
			counter += Integer.parseInt(num);
	}
		
	public static void main(String[] args) 
	{	
		initializeRegisters();
		getInstructions("Star23_input.txt");
		runInstructions();
		System.out.println("Register a has value: " + getRegister("a"));
	}

}
