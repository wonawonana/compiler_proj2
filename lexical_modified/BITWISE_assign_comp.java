package lexical_modified;

public class BITWISE_assign_comp extends DFA{
	/*state table, T0=0, T1=1, T2=2.... °øÁýÇÕ=-1*/
	int[][] BAC_state_table= {
			{1,2,3,4,5,6},
			{-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,7},
			{-1,-1,-1,8,-1,9},
			{-1,-1,-1,-1,10,11},
			{-1,-1,-1,-1,-1,12},
			{-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1},
	};
	
	String[] BAC_final_array= {null, "BITWISE", "BITWISE", null, "comp", "comp","assign",
			"comp","BITWISE","comp","BITWISE","comp", "comp"};
	
	char[] BAC_input_symbol= {'&', '|', '!', '<', '>', '='};
	
	public BITWISE_assign_comp() {
		setState_table(BAC_state_table);
        setFinal_array(BAC_final_array);
        setInput_symbol(BAC_input_symbol);
	}
}
