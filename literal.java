package lexical_modified;

public class literal extends DFA{
	/*state table, T0=0, T1=1, T2=2.... 공집합=-1*/
	int[][] string_state_table= {
			{1,-1,-1,-1},
			{2,3,4,5},
			{-1,-1,-1,-1},
			{2,3,4,5},
			{2,3,4,5},
			{2,3,4,5}
	};
	
	String[] string_final_array= {null, null, "literal", null, null, null};
	
	/*D나 L의 경우 isAccept에서 if문 사용한다*/
	char[] string_input_symbol= {'"', 'D', 'L', ' '}; 
	
	public literal() {
		setState_table(string_state_table);
        setFinal_array(string_final_array);
        setInput_symbol(string_input_symbol);
	}		
}
