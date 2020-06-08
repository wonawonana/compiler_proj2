package lexical_modified;

public class semi extends DFA{
	/*state table, T0=0, T1=1, T2=2.... °øÁıÇÕ=-1*/
	int[][] terminating_state_table= {
			{1},
			{-1}
	};
	
	String[] terminating_final_array= {null, "semi"};
	
	char[] terminating_input_symbol= {';'};
	
	public semi() {
		setState_table(terminating_state_table);
        setFinal_array(terminating_final_array);
        setInput_symbol(terminating_input_symbol);
	};
}
