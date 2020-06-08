package lexical_modified;

public class addsubmultdiv extends DFA{
	/*state table, T0=0, T1=1, T2=2.... АјС§Че=-1*/
	int[][] arithmetic_state_table= {
			{1,2,3,4,},
			{-1,-1,-1,-1},
			{-1,-1,-1,-1},
			{-1,-1,-1,-1},
			{-1,-1,-1,-1}
	};	
	
	String[] arithmetic_final_array= {null, "addsub","addsub","multdiv","multdiv"};
	
	char[] arithmetic_input_symbol= {'+', '-', '*', '/'};
	
	public addsubmultdiv() {
		setState_table(arithmetic_state_table);
        setFinal_array(arithmetic_final_array);
        setInput_symbol(arithmetic_input_symbol);
	}
}
