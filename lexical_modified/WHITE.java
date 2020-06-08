package lexical_modified;

import java.io.FileWriter;
import java.io.IOException;

public class WHITE extends DFA{
   /*state table, T0=0, T1=1, T2=2.... 공집합=-1*/
   int[][] white_state_table= {
         {1,2,3},
         {4,5,6},
         {4,5,6},
         {4,5,6},
         {4,5,6},
         {4,5,6},
         {4,5,6}
   };
   
   
   String[] white_fianl_array= {null, "WHITE", "WHITE", "WHITE", "WHITE", "WHITE", "WHITE"};
   
   /* \n(enter)가 인식이 안 된다->일단은  아스키 13으로 설정
    * \n(enter)는 13 10, isAccept의 idx 오버라이딩(idx++ -> idx+=2)*/
   char[] white_input_symbol= {'\t', 13, ' '};
   
   public WHITE() {
      setState_table(white_state_table);
        setFinal_array(white_fianl_array);
        setInput_symbol(white_input_symbol);
   };
   
   /*오버라이딩- \n(enter)
    * console에 (int)출력
    * */
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
          /*matchSymbol찾기   */
          for(int i = 0;i<input_symbol.length; i++) { 
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
             }//if-else문 끝
          }//for문 끝
     
          /*final 검사: 
           * 1.symbolAccept가 false라면, arrayChar[idx]와 매치하는 input_symbol이 없다
           * 2.state_table[T][matchSymbol]이 -1이라면, 새로운 state는 공집합이다
           */       
          //1.symbolAccept가 false라면, arrayChar[idx]와 매치하는 input_symbol이 없다
          if(symbolAccept==false){ //symbolAccept가 false인 경우
               //final검사
               if(isFinal(T)) {  //T가 final state인 경우
            	   System.out.print("Token_name: "+final_array[T]);  
            	   //System.out.print(", Token_value: "); 
            	   System.out.println(); 
            	   
            	   /*fileWriter: Token_name
            	   fileWriter.write(final_array[T]);
            	   fileWriter.write("  ");*/     
            	   /*fileWriter: Token_value
            	   for(int i=start;i<idx;i++) {
                	 // System.out.print(final_array[T]);
                	 if(arrayChar[i]==9){
                		 fileWriter.write("\\t \n");
                	 }
                	 else if(arrayChar[i]==10){
                		 fileWriter.write("\\n \n");
                	 }
                	 else if (arrayChar[i]==32){
                		 fileWriter.write("(space)\n");
                	 }
            	   }*/
            	                  
            	   return idx;
               } //if(isFinal(T)) 끝            


               /*WHITE는 모든 state가 final state*/
               
               
          }//if-else  끝
         
          // 2.state_table[T][matchSymbol]이 -1이라면, 새로운 state는 공집합이다
          if(state_table[T][matchSymbol]==-1) {  //state_table[T][matchSymbol]가 -1인 경우
              //final 검사
              if(isFinal(T)) { //T가 final state인 경우
                 System.out.print("Token_name: "+final_array[T]);
                 //System.out.print(", Token_value: ");
                 System.out.println();
                 
                 /*fileWriter: Token_name
                 fileWriter.write(final_array[T]);
                 fileWriter.write("  ");*/
                 /*fileWriter: Token_value
                 for(int i=start;i<idx;i++) {
                    //System.out.print(arrayChar[i]);
                	 if(arrayChar[i]==9){
                		 fileWriter.write("\\t \n");
                	 }
                	 else if(arrayChar[i]==10){
                		 fileWriter.write("\\n \n");
                	 }
                	 else if (arrayChar[i]==32){
                		 fileWriter.write("(space)\n");
                	 }//if-else문 끝
                 }//for(int i=start;i<idx;i++)끝        */
                 
                 return idx;
              }//if(isFinal(T))끝

              
             /*WHITE는 모든 state가 final state*/
              
              
          }//if(state_table[T][matchSymbol]==-1) 끝
          else { //state_table[T][matchSymbol]가 -1이 아닌 경우  
              //새로운 state
              T=state_table[T][matchSymbol];
              /*enter 오버라이딩*/
              if(matchSymbol==1) {
                  idx+=2;
              }
              else {
                  idx++;
              }  
              //symbolAccept초기화
              symbolAccept=false;
           }//if(state_table[T][matchSymbol]==-1)-else 끝
       }while(T!=-1);//T가 공집합이 아닐 때까지. do-while문 끝
       /*다음 start인덱스 반환*/
       return 0;
   }//isAccept끝
}