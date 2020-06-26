package lexical_modified;

import java.io.FileWriter;
import java.io.IOException;

/*NZD- '0'은 if(==)에 걸리므로 그 다음에 있는 if문에서 D로 처리 가능*/
public class num_float extends DFA{
	/*state table, T0=0, T1=1, T2=2.... 공집합=-1*/
	int[][] IF_state_table= {
			{1,2,3,-1},
			{-1,-1,-1,4},
			{5,-1,3,-1},
			{6,-1,7,8},		
			{9,-1,10,-1},
			{-1,-1,-1,8},
			{6,-1,7,8},
			{6,-1,7,8},
			{9,-1,10,-1},
			{11,-1,10,-1},
			{11,-1,10,-1},
			{11,-1,10,-1}
	};
	
	String[] IF_final_array= {null, "num", null, "num", null, null, "num", "num", null, "float", "float", null};
	
	/*NZD: '0'은 if(==)에 걸리므로 그 다음에 있는 if(==D)에서  처리 가능
	 * if (arrayChar[idx] == input_symbol[i]): input_symbol[0] 0, input_symbol[1] -, input_symbol[3] .
	 * else if(input_symbol[i]=='D'): input_symbol[3] N
	 * */
	char[] IF_input_symbol= {'0', '-', 'D', '.'};
	
	public num_float() {
		setState_table(IF_state_table);
		setFinal_array(IF_final_array);
		setInput_symbol(IF_input_symbol);
	};
	
	/*오버라이딩
	 */
	public int isAccept(char[] arrayChar, int start, FileWriter fileWriter) throws IOException{	
	 	/*charSequence[start]부터 input_symbol과 비교 시작*/
       int idx=start;   
	   /*arrayChar[idx]와 매치하는 input_symbol이 있으면 sybolAccept는 true, 없으면 false*/
       boolean symbolAccept=false; 
       /*state_table의 열(T0=0, T1=1....)*/
       int T=0;
       /*input_symbol에서 arrayChar[idx]와 일치하는 배열요소의 index*/
       int matchSymbol=-1;	    
       
       do{
    	   /*matchSymbol찾기	*/
    	   for (int i = 0;i<input_symbol.length; i++) { 
    		   //arrayChar[idx]가 input_symbol[i]와 일치하는 경우
    		   if (arrayChar[idx] == input_symbol[i]) {
    			   matchSymbol = i;
    			   symbolAccept=true;
    			   break;
               }
               //arrayChar[idx]가 letter인 경우
               else if(input_symbol[i]=='L') {
               		if((90>=arrayChar[idx] && arrayChar[idx]>=65) || (122>=arrayChar[idx] && arrayChar[idx]>=97)) {
               			matchSymbol = i;
               			symbolAccept=true;
	                    break;
               		}
               }
               //arrayChar[idx]가 digit인 경우
               else if(input_symbol[i]=='D') {
               		if(57>=arrayChar[idx] && arrayChar[idx]>=48) {
               			matchSymbol = i;
               			symbolAccept=true;
	                    break;
               		}
               }
    	   }//for문 끝
     
    	   /*final 검사: 
    	    * 1.symbolAccept가 false라면, arrayChar[idx]와 매치하는 input_symbol이 없다
    	    * 2.state_table[T][matchSymbol]이 -1이라면, 새로운 state는 공집합이다
    	    */    
    	   //1.symbolAccept가 false라면, arrayChar[idx]와 매치하는 input_symbol이 없다
    	   if(symbolAccept==true) { //symbolAccept가 true인 경우
    		   /*기존state가 T9,T10 일 때 inputSymbol이 0인경우
    		    * .00, .10
    		    */
    		   if((T==9 ||T==10)&&arrayChar[idx]=='0') {
    			   /*.00, .10뒤에 .이 오는 경우*/
    			   if(arrayChar[idx+1]=='.') {
    				   /*0.00.까지 idx를 이동*/
    				   idx+=2;
    				   System.out.println("<ERROR>");
    				   System.out.println("error at .");
    				   System.out.print("입력된 문자열: ");
    				   for(int i=start;i<idx;i++) {
            				System.out.print(arrayChar[i]);
            			}
            			System.out.println();
            			
            			//output출력
   						fileWriter.write("\n<ERROR>\n");
   						fileWriter.write("error at .\n");
   						fileWriter.write("입력된 문자열: ");
   						/*fileWriter: Token_value*/
   	         			for(int i=start;i<idx;i++) {
   	         				fileWriter.write(arrayChar[i]);
   	         			}
   	              		fileWriter.write("\n");
   	              		
        			    return idx;
    			   }//if(arrayChar[idx+1]=='.')끝
    			   //0.0이나 0.~NZD까지 float으로 저장한 뒤 메인으로 돌아간다
    			   else {
    				   /*0.0까지 FLOAT으로 저장*/
        				System.out.print("Token_name: FLOAT");
        				System.out.print(", Token_value: ");
                  		//0.0까지 출력
                  		for(int i=start;i<start+3;i++) {
             				System.out.print(arrayChar[i]);
             			}
                  		System.out.println();
                  		
                 		/*fileWriter: Token_name*/
                  		fileWriter.write("FLOAT");
                  		/*ouput을 배열로 변환할 때 구분점- ,*/
                  		fileWriter.write(",");
                  		
                  		//0.0뒤의 0부터 main으로 돌아간다
                  		return start+3;
    			   }//if(arrayChar[idx+1]=='.') -else 끝
    		   }//if((T==9 ||T==10)&&arrayChar[idx]=='0')  끝 
    	   }// if(symbolAccept==true)끝
    	   else { //symbolAccept가 false인 경우
         		//final검사
         		if(isFinal(T)) { //T가 final state인 경우
         			System.out.print("Token_name: "+final_array[T]);
         			System.out.print(", Token_value: ");
         			for(int i=start;i<idx;i++) {
         				System.out.print(arrayChar[i]);
         			}
         			System.out.println();
         			
         			/*fileWriter: Token_name*/
         			fileWriter.write(final_array[T]);
         			/*ouput을 배열로 변환할 때 구분점- ,*/
         			fileWriter.write(","); 

           			break;
         		} //if(isFinal(T))끝     
         		/*T가 final state가 아닌 경우:
    	    	 * T4, T8
    	    	 */
         		else { //T가 final state가 아닌 경우, <ERROR>
         			/*.이후로 매치하는 input symbol이 없는 경우*/
         			if(T==4||T==8) {
         				System.out.println("<ERROR>");
         				System.out.println("error at .");
     				    System.out.print("입력된 문자열: ");
     				    for(int i=start;i<idx;i++) {
            				System.out.print(arrayChar[i]);
            			}
            			System.out.println();
            			
             			//output출력
    					fileWriter.write("\n<ERROR>\n");
    					fileWriter.write("error at .\n");
    					fileWriter.write("입력된 문자열: ");
    					/*fileWriter: Token_value*/
             			for(int i=start;i<idx;i++) {
             				fileWriter.write(arrayChar[i]);
             			}
                  		fileWriter.write("\n");
                  		
         			    return idx;
         			}//if(T==4||T==8) 끝
         			else {
         				//콘솔 출력
         				System.out.println("<ERROR>");
         				System.out.println("입력된 문자열에 해당하는 토큰이 없습니다."); 
         				System.out.print("입력된 문자열: ");
         				for(int i=start;i<idx;i++) {
             				System.out.print(arrayChar[i]);
             			}
             			System.out.println();
             			
         				//output출력
         				fileWriter.write("\n<ERROR>\n");
         				fileWriter.write("입력된 문자열에 해당하는 토큰이 없습니다.\n");
         				fileWriter.write("입력된 문자열: ");
         				/*fileWriter: Token_value*/
             			for(int i=start;i<idx;i++) {
             				fileWriter.write(arrayChar[i]);
             			}
                  		fileWriter.write("\n");
                  		
         				break;
         			}//if(T==4||T==8) -else 끝
         		
         		}//if(isFinal(T)) -else 끝    
         
    	   }//if(symbolAccept==true) -else 끝		
         		
    	   
    	   // 2.state_table[T][matchSymbol]이 -1이라면, 새로운 state는 공집합이다
    	    if(state_table[T][matchSymbol]==-1) { //state_table[T][matchSymbol]가 -1인 경우
    	    	//final 검사
    	    	if(isFinal(T)) { //T가 final state인 경우
    	    		System.out.print("Token_name: "+final_array[T]);
    	    		System.out.print(", Token_value: ");
         			for(int i=start;i<idx;i++) {
         				System.out.print(arrayChar[i]);
         			}
         			System.out.println();
         			
    	    		/*fileWriter: Token_name*/
               		fileWriter.write(final_array[T]);
               		/*ouput을 배열로 변환할 때 구분점- ,*/
         			fileWriter.write(",");  
         			
    	    		break;
    	    	}//if(isFinal(T)) 끝
       	 		else {//T가 final state가 아닌 경우,  <ERROR>
       	 				//콘솔 출력
       	 				System.out.println("<ERROR>");
       	 				System.out.println("입력된 문자열에 해당하는 토큰이 없습니다."); 
         				System.out.print("입력된 문자열: ");
         				for(int i=start;i<idx;i++) {
             				System.out.print(arrayChar[i]);
             			}
             			System.out.println();
             			
         				//output출력
         				fileWriter.write("\n<ERROR>\n");
         				fileWriter.write("입력된 문자열에 해당하는 토큰이 없습니다.\n");
         				fileWriter.write("입력된 문자열: ");
         				/*fileWriter: Token_value*/
             			for(int i=start;i<idx;i++) {
             				fileWriter.write(arrayChar[i]);
             			}
                  		fileWriter.write("\n");
                  		
                  		break;
       	 		}//if(isFinal(T)) -else 끝
    	    }//if(state_table[T][matchSymbol]==-1) 끝	
    	    else { //state_table[T][matchSymbol]가 -1이 아닌 경우 
    	    	//새로운 state
    	    	T=state_table[T][matchSymbol];
    	    	//idx 1증가
    	    	idx++;  
    	    	//symbolAccept초기화
    	    	symbolAccept=false;
    	   }//if(state_table[T][matchSymbol]==-1) -else 끝
       }while(T!=-1);//T가 공집합이 아닐 때까지. do-while문 끝
       
       /*다음 start인덱스 반환*/
       return idx;
   }//isAccept끝
       
}