#pragma once
#define NUM_G 32
#define NONTERMINAL_MAX_LENGTH	100

/*G: R->L*/
//R nonterminal
char *G_R[NUM_G + 10]=
//0, 1		 2		3		4		5		6		 7		8		9		10		 
{"S","CODE","CODE","CODE","VDECL","VDECL", "ASSIGN","FDECL","ARG","ARG" ,"MOREARGS",
//11		12		13		14		15		16		17		18
"MOREARGS","BLOCK","BLOCK","STMT","STMT" ,"STMT" ,"STMT" ,"STMT", 
//19	20		21	 22		23	 24		 25		26		27		28		29		 30		 31		 32
"ELSE","ELSE","RHS","RHS","EXPR","EXPR","TERM","TERM","FACTOR","FACTOR","FACTOR","FACTOR","COND","RETURN",""
};



//L Å©±â
int G_L_size[NUM_G + 10] = 
//0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32
{ 1, 2,2,0,3,3,3,9,3,0, 4, 0, 2, 0, 1, 2, 8, 7,11, 4, 0, 1, 1, 3, 1, 3, 1, 3, 1, 1, 1, 3, 3}; 