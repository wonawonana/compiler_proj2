#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#define TERMINAL_MAX_LENGTH 100

int main() {
	/*파일 입출력*/
	int file_size = 0;
	FILE *fp;  //fp 초기화 꼭 해주기
	char ch;

	//,의 개수를 읽어서 terminal 개수 파악
	int num_terminal= 0;
	//input_string배열- 동적할당
	int **input_string;
	//lexical_output의 <ERROR>탐색
	char ch_error = ' ';
	//lexical_output을 배열에 저장하기
	char ch_array = ' ';

	/*ouput.txt 읽어오기*/
	if ((fp = fopen("C:\\Users\\방윤하\\Desktop\\2020-1\\과제\\컴파일러\\proj1\\input_output\\lexical_output.txt", "r")) == NULL) //파일을 읽을 수 없는 경우
		printf("ERROR- cannot lexical_output.txt \n");
	else {
		/*file_size*/
		// 파일 포인터 fp를 파일의 끝으로 이동시킴
		fseek(fp, 0, SEEK_END);    
		file_size = ftell(fp);
		//fp 파일 맨 앞으로 초기화
		fseek(fp, 0, SEEK_SET);

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
			for (char ch_count=fp;ch_count!=EOF; ) {
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
	input_string = malloc(sizeof(char*)*(num_terminal+10));
	for (int i = 0; i < num_terminal; i++) {
		input_string[i] = malloc(sizeof(char)*TERMINAL_MAX_LENGTH);
	}

	/*,를 구분점으로 하여 배열에 terminal 저장*/
	for (int row=0;row<num_terminal;row++) {	
		for (int col = 0;; col++) {
			ch_array = getc(fp);
			input_string[row][col] = ch_array;
			if(ch_array==','||ch_array==EOF){
				break;
			}
		}
	}
	//fp 파일 맨 앞으로 초기화
	fseek(fp, 0, SEEK_SET);

	/*배열 출력: 배열요소= termianl + ,(,는 terminal의 끝을 표시하는 역할)*/
	for (int row = 0; row < num_terminal; row++) {
		printf("\ninput_string[%d]: ", row);
		for (int col = 0; ; col++) {
			printf("%c", input_string[row][col]);
			if (input_string[row][col] == ',') {
				break;
			}
		}
	}

	return 0;
}