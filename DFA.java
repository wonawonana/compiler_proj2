package lexical_modified;

import java.io.FileWriter;
import java.io.IOException;

public class DFA {
	/*state tabe: dfa table의 값(T0, T1, ...공집합)을 저장한다*/
    int state_table[][];
    /*state T가 final state면 Token_name을 저장, 아니면 null을 저장한다*/
    String final_array[];
    /*각 dfa의 symbol을 저장한다*/
	char input_symbol[];        	
	
   public DFA() {}
   
   /*set- 각 클래스의 멤버변수를 DFA 클래스의 멤버변수에 저장한다*/
   //state_table
   public void setState_table(int[][] token_state_table) {
	   state_table = token_state_table;
   }
   //final_array
   public void setFinal_array(String[] token_final_array) {
       final_array = token_final_array;
   }
   //input_symbol
   public void setInput_symbol(char[] token_input_symbol) {
       input_symbol = token_input_symbol;
   } 
   /*isAccept 오버라이딩한 클래스: num_float, WHITE*/ 
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
   	   
    	   if(symbolAccept==false) { //symbolAccept가 false인 경우
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
         			/*fileWriter: Token_value
         			for(int i=start;i<idx;i++) {
         				fileWriter.write(arrayChar[i]);
         			}*/

         			break;
         		}  //if(isFinal(T))끝       		
         		else { //T가 final state가 아닌 경우, <ERROR>
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
         		}//if(isFinal(T)) -else끝   
         		
         	}//if(symbolAccept==false) 끝
         
    	   
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
       	 		else { //T가 final state가 아닌 경우,  <ERROR>
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
    

   /*T가 final state면 true를 반환한다. 아니면 false를 반환한다*/
   public boolean isFinal(int T){
       if(final_array[T]==null)
           return false;
       return true;
   }

}