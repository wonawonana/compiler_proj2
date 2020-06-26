package lexical_modified;

import java.io.*;

public class Lexical_Analyzer_Modified {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub		
		/*input 파일*/
		String fileNameReader= args[0];
		//String fileNameReader="C:\\Users\\방윤하\\Desktop\\2020-1\\과제\\컴파일러\\proj1\\input_output\\lexical_input.txt";
		/*파일 사이즈*/
		File file= new File(fileNameReader);
		final int LENGTH=(int)file.length();
				
		/*파일을 저장할 char배열 생성*/
		char[] charSequence = new char[LENGTH+ 30];
		/*FileReader 파일 객체 생성*/
		FileReader fileReader = new FileReader(fileNameReader);
		/*파일 내용을 charSequence에 저장*/
		fileReader.read(charSequence);
		fileReader.close();
		
		/*charSequence출력*/
		System.out.println("<FILE>");
		System.out.println("charSequence: ");
		System.out.println(charSequence);
		System.out.println("file_length: "+LENGTH);
		System.out.println();	
		
		/*output 파일*/
		String fileNameWriter = "C:\\Users\\lexical_output.txt";
		
		/*FileWriter 파일 객체 생성*/
        FileWriter fileWriter = new FileWriter(fileNameWriter);	
		
		/*인덱스 start*/
		int start=0;		
		/*inputChar에 charSequence[start] 저장*/
		char inputChar= charSequence[start];
		
		/*class 인스턴스 생성*/
		literal string_instance= new literal();
		BITWISE_assign_comp BAC_instance= new BITWISE_assign_comp();
		addsubmultdiv 	arithmetic_instance= new addsubmultdiv();
		semi terminating_instance=new semi();
		comma separating_instance=new comma();
		WHITE white_instance=new WHITE();
		lbracerbrace brace_instance=new lbracerbrace();
		lparenrparen paren_instance=new lparenrparen();
		num_float IF_instance= new num_float();
		id_vtype_BOOLEAN_ifforelsewhilereturn ITBK_instance=new id_vtype_BOOLEAN_ifforelsewhilereturn();
		
		/* charSequence[start]부터 dfa를 돌린다
		 * 각 dfa에서 새로운 start 반환
		 * start==LENGTH면 break
		 */
		while(start<LENGTH) {
			
			/*inputChar에 알맞은 dfa를 호출한다*/
			switch(inputChar) {
			
			case '"':		 
				/*새로운 start*/
				start=string_instance.isAccept(charSequence, start, fileWriter);
				break;
				
			case '-':
				char nextChar=charSequence[start+1];
				/*nextChar가 digits(0~9)인 경우*/
				if(57>=(int)nextChar && (int)nextChar>=48){
					//nextChar가 0인 경우 ->.이 존재하면 I|F.dfa, 존재하지 않으면 arithmetic
					if((int)nextChar==48) {
						//-0.
						if(charSequence[start+2]=='.') {
							start=IF_instance.isAccept(charSequence, start, fileWriter);
						}
						//-0뒤에 .이 오지 않는다
						else {
							start=arithmetic_instance.isAccept(charSequence, start, fileWriter);
						}
					}
					//nextChar가 0이 아닌 경우
					else {
						start=IF_instance.isAccept(charSequence, start, fileWriter);
					}
				}//if(57>=(int)nextChar && (int)nextChar>=48)끝
				else {
					start=arithmetic_instance.isAccept(charSequence, start, fileWriter);
				}
				break;	
				
			case '+': case '*': case '/':		 
				start=arithmetic_instance.isAccept(charSequence, start, fileWriter);
				break;
				
			case '<': case '>': case '&': case '|': case '=': case '!':
				start=BAC_instance.isAccept(charSequence, start, fileWriter);
				break;	
				
			case ';':	 
				start=terminating_instance.isAccept(charSequence, start, fileWriter);
				break;
				
			case '{': case '}':	 
				start=brace_instance.isAccept(charSequence, start, fileWriter);
				break;
				
			case '(': case ')':		 
				start=paren_instance.isAccept(charSequence, start, fileWriter);
				break;
				
			case ',':			 
				start=separating_instance.isAccept(charSequence, start, fileWriter);
				break;	
				
			case '\t': case '\n': case ' ':			
				start=white_instance.isAccept(charSequence, start, fileWriter);
				break;
				
			/*case letter는 default에서 다룬다*/	
			case '_':		 
				start=ITBK_instance.isAccept(charSequence, start, fileWriter);
				break;
				
			/*inputChar가 letter||digit인 경우 
			 * \n인 경우
			 * 또는 에러인 경우	
			 */
			default:
				/*digits*/
				if(57>=inputChar && inputChar>=48) {
					start=IF_instance.isAccept(charSequence, start, fileWriter);
				}
				/*letter*/
				else if((90>=inputChar && inputChar>=65) || (122>=inputChar && inputChar>=97)) {
					start=ITBK_instance.isAccept(charSequence, start, fileWriter);
				}				
				else {
					/*\n: 13 10*/
					if((int)inputChar==13) {
						//System.out.println("  9: TAB,  13 10: ENTER,  32: SPACE");					
						start=white_instance.isAccept(charSequence, start, fileWriter);
					}
					/*에러- symbol아닌 문자가 들어온 경우*/
					else {
						//콘솔 출력
						System.out.println("<ERROR>");
						System.out.println("잘못된 문자를 입력했습니다.");
						System.out.println("입력된 문자: "+inputChar);
						System.out.println();
						//output출력
						fileWriter.write("\n<ERROR>\n");
						fileWriter.write("잘못된 문자를 입력했습니다.\n");
						fileWriter.write("입력된 문자: ");
						fileWriter.write(inputChar);
						fileWriter.write("\n\n");
						//inputChar: charSequence[start] -> charSequence[start+1]
						start++;
					}
				}
				
			}//switch 끝					
			/*새로운 inputChar*/
			inputChar=charSequence[start];			
		}//while문 끝			
		//if(start==LENGTH) {System.out.println("\n\n종료되었습니다.");}	
		/*output 파일 닫기*/
		fileWriter.close();
	}//main 끝	
}//class 끝
