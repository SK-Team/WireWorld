#include <stdio.h>
#include <stdlib.h>

#include "Board.h"

int main(int argc, char **argv){

	FILE *out = fopen("test","r");

	if(out == NULL){
		printf("Slabiutko\n");
		return 1;
	}

	board_t b = read_board_file(out);

	print_board(stdout, b);

	b = free_board(b);	

}
