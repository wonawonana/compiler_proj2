package lexical_modified;

public class lbracerbrace extends DFA{
	/*state table, T0=0, T1=1, T2=2.... АјС§Че=-1*/
	int[][] brace_state_table= {
			{1,2},
			{-1,-1},
			{-1,-1}
	};
	String[] brace_final_array= {null, "lbrace", "rbrace"};
	
	char[] brace_input_symbol= {'{', '}'};

	public lbracerbrace() {
		setState_table(brace_state_table);
		setFinal_array(brace_final_array);
		setInput_symbol(brace_input_symbol);
	};
}
