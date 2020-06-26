package lexical_modified;

public class comma extends DFA{
	/*state table, T0=0, T1=1, T2=2.... °øÁıÇÕ=-1*/
	int[][] separating_state_table= {
			{1},
			{-1}
	};
	
	String[] separating_final_array= {null, "comma"};
	
	char[] separating_input_symbol= {','};
	
	public comma() {
		setState_table(separating_state_table);
        setFinal_array(separating_final_array);
        setInput_symbol(separating_input_symbol);
	};
}
