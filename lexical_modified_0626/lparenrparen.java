package lexical_modified;

public class lparenrparen extends DFA{
	/*state table, T0=0, T1=1, T2=2.... °øÁıÇÕ=-1*/
	int[][] paren_state_table= {
			{1,2},
			{-1,-1},
			{-1,-1}
	};
	String[] paren_final_array= {null, "lparen", "rparen"};
	
	char[] paren_input_symbol= {'(', ')'};

	public lparenrparen() {
		setState_table(paren_state_table);
		setFinal_array(paren_final_array);
		setInput_symbol(paren_input_symbol);
	};
}

