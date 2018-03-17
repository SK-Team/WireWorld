#include <stdio.h>
#include <stdlib.h>

#include "Board.h"

int main(int argc, char **argv){

	board_t b = make_board(5,5);

	if(b == NULL){
		printf("Niefajnie\n");
		return 1;		
	}
	
	int i,j;

	for (i = 0; i < 5; i++)
		for (j = 0; j < 5; j++)
			b->values[i][j] = 'a';

	for (i = 0; i < 5; i++){
	        for (j = 0; j < 5; j++)
			printf("%c ", b->values[i][j]);
		printf("\n");
	}
}
