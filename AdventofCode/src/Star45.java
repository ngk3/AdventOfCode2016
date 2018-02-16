import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Star45 {
	
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
		registers.put("a", 7);
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
		initializeRegisters();
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
			else
				tgl(instr_arr[1], counter);
			counter++;
		}
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

	// Function used to toggle the instructions
	public static void tgl(String reg, int counter) {
		// Get the instruction to toggle
		int increment;
		if (registers.containsKey(reg))
			increment = registers.get(reg);
		else
			increment = Integer.parseInt(reg);
		
		// If an attempt is made to toggle an instruction outside the program, nothing happens.
		if (counter + increment >= instructions.size())
			return;
		String [] instruction_splitted = instructions.get(counter + increment).split(" ");
		
		// For two-argument instructions, jnz becomes cpy, and all other two-instructions become jnz
		if (instruction_splitted.length == 2) {
			if (instruction_splitted[0].equals("inc"))
				instructions.set(counter + increment, "dec " + instruction_splitted[1]);
			else
				instructions.set(counter + increment, "inc " + instruction_splitted[1]);
		}
		// For two-argument instructions, jnz becomes cpy, and all other two-instructions become jnz.
		else {
			if (instruction_splitted[0].equals("jnz"))
				instructions.set(counter + increment, "cpy " + instruction_splitted[1] + " " + instruction_splitted[2]);
			else
				instructions.set(counter + increment, "jnz " + instruction_splitted[1] + " " + instruction_splitted[2]);
		}	
	}

	public static void main(String[] argvs) {
		getInstructions("Star45_input.txt");
		runInstructions();
		System.out.println("Value at register a = " + registers.get("a"));
	}
}
