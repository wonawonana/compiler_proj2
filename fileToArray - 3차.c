#define _CRT_SECURE_NO_WARNINGS

/*헤더파일*/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "SLR_table.h"
#include "CFG G.h"

/*매크로 상수*/
#define MAX_STACK_SIZE 100
#define true 1
#define false 0

/*전역 변수*/
//input_string배열- 동적할당
char **input_string;
//,의 개수를 읽어서 terminal 개수 파악
int num_terminal = 0;
//terminal 크기 배열
int *input_length;
//stack 구현
int stack[MAX_STACK_SIZE];   //global var
int top = -1;

/*함수 선언*/
//스택
int isEmpty();
int isFull();
void push(int value);
int pop();
//lexical_output을 배열로 변환
void fileToArray();
//input_string[]과 일치하는 ACTION 반환
int compare_input_ACTION(int partition);


int main() {
	//partition뒤의 배열요소: input_string[partition], partition앞의 배열요소: input_string[partition-1]
	int partition = 0; //SLR_ACTION_col 탐색
	//compare_input_ACTION함수 호출하면 SLR_ACTION_col 반환
	int SLR_ACTION_col = -1;
	//strcmp()로 SLR_GOTO_col 탐색
	int SLR_GOTO_col = -1;
	//스택에 저장할 state
	int nextState ;
	//SLR_table에 저장된 value
	int value_SLR_ACTION;
	int p_value_SLR_ACTION;
	int value_SLR_GOTO;
	//parsing 중간 결과 저장하기 위한 배열
	char **parsing_temp;
	//parsing_temp의 partition, partition_parsing_temp: parsnig -|alpha|
	int partition_parsing_temp=0;
	//A->alpha에서 |alpha|
	int len_alpha;
	//parsing temp에 저장된 terminal, nonterminal의 개수
	int num_parsing_temp;

	/*lexical_output을 배열로 변환*/
	fileToArray();
	
	//input_length를 터미널의 개수(+10)만큼 할당
	input_length = malloc(sizeof(int)*(num_terminal + 10));

	/*input_string 출력, terminal크기 측정*/
	printf("|");
	for (int row = 0; row < num_terminal; row++) {
		for (int col = 0; ; col++) {
			if (input_string[row][col] == ',') {
				printf(" ");
				//input_string[row]에 저장된 terminal의 크기를 input_length[row]에 저장
				input_length[row] = col;
				break;
			}
			else {
				//input_string 출력
				printf("%c", input_string[row][col]);
			}
		}
	}
	printf("\n\n");
	//parsing_temp동적할당
	parsing_temp = malloc(sizeof(char*)*(num_terminal + 10));
	for (int i = 0; i < num_terminal; i++) {
		parsing_temp[i] = malloc(sizeof(char)*TERMINAL_MAX_LENGTH);
	}
	//parsing_temp 초기화- input_string 저장
	for (int row = 0;row<num_terminal; row++) {
		for (int col = 0; col <= input_length[row]; col++) {
			// parsing_temp에 ','도 저장- 구분점 역할
			parsing_temp[row][col] = input_string[row][col];
		}
	}
	//num_parsing_temp 초기화
	num_parsing_temp = num_terminal;

	/*stack initializatione- push(0)*/
	nextState = 0;//state T가 0부터 시작하므로 0을 push
	push(nextState);
	
	/*SLR parsing*/
	for (int i = 0; i < 10; i++) {
		printf("stack[top]: %d  ", stack[top]);
		/*partition뒤의 terminal(input_string[partition])과 일치하는 ACTION의 terminal 찾기*/
		SLR_ACTION_col = compare_input_ACTION(partition);
		if (SLR_ACTION_col == -1) {
			printf("일치하는 ACTION이 없습니다.\n");
		}

		printf("SLR_table_ACTION[%d][%d]: %d  \n", stack[top], SLR_ACTION_col, SLR_table_ACTION[stack[top]][SLR_ACTION_col]);
		value_SLR_ACTION = SLR_table_ACTION[stack[top]][SLR_ACTION_col];
		/*value_SLR이 음수일 경우 Reduce*/
		if (value_SLR_ACTION <= 0) {
			//value_SLR_ACTION이 음수면 p_value_SLR_ACTION에 절댓값 저장
			if (value_SLR_ACTION < 0) {
				p_value_SLR_ACTION = -value_SLR_ACTION;
			}
			//parsing_temp의 partition_parsing_temp
			len_alpha = G_L_size[p_value_SLR_ACTION];
			printf("len_alpha: %d ", len_alpha);

			

			//A->alpha, partition_parsing_temp와 num_parsing_temp 변화: -|alpha| +|A|
			partition_parsing_temp = partition - len_alpha + 1;
			num_parsing_temp = num_parsing_temp - len_alpha + 1;

			/*ex. T*id|num, T->id
			1.alpha 전까지 parsing_temp에 저장 ex.T*
			2.A->alpha, A를 parsing_temp에 저장 ex.T
			3.partition 뒷부분을 parsing_temp에 저장 ex.num
			parsing_temp= T*T|num
			*/
			//1.alpha 전까지 parsing_temp에 저장 ex.T*
			printf("partition- len_alpha: %d \n", partition - len_alpha);
			for (int row = 0; row < partition - len_alpha; row++) {
				for (int col = 0; col <= input_length[row]; col++) {
					parsing_temp[row][col] = input_string[row][col];
				}
			}

			//pop 
			for (int p = 0; p < G_L_size[p_value_SLR_ACTION]; p++) {
				pop();
			}
			printf("stack[top]: %d  ", stack[top]);
			printf("%s \n", G_R[p_value_SLR_ACTION]);

			//GOTO
			for (int g = 0; g < num_syntax_nonterminal; g++) {
				if (!strcmp(G_R[p_value_SLR_ACTION], GOTO[g])) {
					SLR_GOTO_col = g;
				}
			}
			printf("SLR_table_GOTO[%d][%d]: %d \n", stack[top], SLR_GOTO_col, SLR_table_GOTO[stack[top]][SLR_GOTO_col]);
			value_SLR_GOTO = SLR_table_GOTO[stack[top]][SLR_GOTO_col];
			push(value_SLR_GOTO);

			//2.A->alpha, A를 parsing_temp[partition_parsing_temp-1]에 저장 ex.T
			printf("A, partiton_parsing_temp -1: %d \n", partition_parsing_temp - 1);
			for (int i = 0;; i++) {
				parsing_temp[partition_parsing_temp-1][i] = GOTO_Reduce[SLR_GOTO_col][i];
				if (parsing_temp[partition_parsing_temp-1][i] == ',') {
					break;
				}
			}
			
			//3.partition 뒷부분을 parsing_temp에 저장 ex.num
			int temp = partition_parsing_temp;
			for (int row = partition; row < num_terminal; row++) {
				for (int col = 0; col <= input_length[row]; col++) {
					parsing_temp[temp][col] = input_string[row][col];
				}
				temp++;
			}
			
			//parsing_temp출력
			printf("<Reduce> ");
			for (int row = 0; row <num_parsing_temp; row++) {
				//A 뒤에서 "|"출력
				if (row == partition_parsing_temp) {
					printf("|");
				}
				for (int col = 0; parsing_temp[row][col] != ','; col++) {
					printf("%c", parsing_temp[row][col]);
				}
				printf(" ");
				
			}
			printf("\n\n");
		}
		else { /*ACTION - S 인 경우*/
			if (19 >= SLR_ACTION_col && SLR_ACTION_col >= 0) {
				//shift
				partition++;
				partition_parsing_temp++;
				//push the next state
				push(value_SLR_ACTION);
			}

			//parsing_temp 출력
			printf("<Shift> ");
			for (int row = 0; row < num_parsing_temp; row++) {
				//parsing_temp[partition_parsing_temp] 앞에서 "|"출력
				if (row == partition_parsing_temp)
					printf("|");
				for (int col = 0; parsing_temp[row][col] != ','; col++) {
					printf("%c", parsing_temp[row][col]);
				}
				printf(" ");
			}
			//"|"가 배열의 끝에 도달했을 경우
			if (partition_parsing_temp == num_parsing_temp) {
				printf("|");
			}
			printf("\n\n");
		}
		
	}


	

	return 0;
}

/*스택*/
int IsEmpty() {
	if (top < 0)
		return true;
	else
		return false;
}
int IsFull() {
	if (top >= MAX_STACK_SIZE - 1)
		return true;
	else
		return false;
}

void push(int value) {
	if (IsFull() == true)
		printf("stack is full.\n");
	else
		stack[++top] = value;
}
int pop() {
	if (IsEmpty() == true)
		printf("stack is empty.\n");
	else
		return stack[top--];
}




/*lexical_output을 배열로 변환*/
void fileToArray() {
	//파일 포인터fp
	FILE *fp;  //fp 초기화 
	//lexical_output의 <ERROR>탐색
	char ch_error = ' ';
	//lexical_output을 배열에 저장하기
	char ch_array = ' ';

	/*ouput.txt 읽어오기*/
	if ((fp = fopen("C:\\Users\\방윤하\\Desktop\\2020-1\\과제\\컴파일러\\proj1\\input_output\\lexical_output.txt", "r")) == NULL) //파일을 읽을 수 없는 경우
		printf("ERROR- cannot lexical_output.txt \n");
	else {
		/*ouput.txt에 <ERROR>가 있는지 검사*/
		for (; ch_error != EOF && ch_error != '<'; ) {
			ch_error = fgetc(fp);
		}
		//fp 파일 맨 앞으로 초기화
		fseek(fp, 0, SEEK_SET);
		if (ch_error == '<') {
			printf("lexica_output에 <ERROR>가 존재합니다. \n");
		}
		//<ERROR> 없으면 터미널 개수(num_terminal)확인
		else {
			//,개수 == num_terminal
			for (char ch_count = fp; ch_count != EOF; ) {
				ch_count = fgetc(fp);
				if (ch_count == ',') {
					num_terminal++;
				}
			}
			//fp 파일 맨 앞으로 초기화
			fseek(fp, 0, SEEK_SET);
		}
	}

	/*input_string 크기 할당- [num_terminal+10][TERMINAL_MAX_LENGTH] */
	input_string = malloc(sizeof(char*)*(num_terminal + 10));
	for (int i = 0; i < num_terminal; i++) {
		input_string[i] = malloc(sizeof(char)*TERMINAL_MAX_LENGTH);
	}

	/*,를 구분점으로 하여 배열에 terminal 저장*/
	for (int row = 0; row < num_terminal; row++) {
		for (int col = 0;; col++) {
			ch_array = getc(fp);
			input_string[row][col] = ch_array;
			if (ch_array == ',' || ch_array == EOF) {
				break;
			}
		}
	}
	//fp 파일 맨 앞으로 초기화
	fseek(fp, 0, SEEK_SET);
}

/*partition뒤의 terminal(input_string[partition])과 일치하는 ACTION의 terminal 찾기*/
int compare_input_ACTION(int partition) {
	//input_string배열에 저장된 terminal과 input_ACTION배열에 저장된 terminal의 크기 비교
	for (int i = 0; i < num_syntax_terminal; i++) {
		if (input_length[partition] == ACTION_length[i]) {
			//문자 일치하는지 확인
			for (int j = 0; j < input_length[partition]; j++) {
				//일치하지 않으면 break;
				if (input_string[partition][j] != ACTION[i][j]) {
					break;
				}
				else {
					//문자가 모두 일치하는 경우
					if (j == input_length[partition] - 1) {
						for (int k = 0; k <= j; k++) {
							printf("%c", ACTION[i][k]);
						}
						printf("\n");

						//일치하는 input_ACTION의 terminal의 인덱스 반환
						return i;
					}
				}

			}
		}
	}

	//일치하는 input_ACTION의 terminal이 없는 경우
	return -1;
}