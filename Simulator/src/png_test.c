#include <stdio.h>
#include <stdlib.h>

#include "board.h"
#include "pngx.h"

int main(int argc, char **argv) {
  	
	FILE * in = fopen("../data/generation_config", "r");

	char *filename = argc > 1? argv[1]: "../bin/out.png";

	if(in == NULL){
		printf("Plik\n");
		return -1;
	}

	board_t b = read_board_file(in);


	if(b == NULL){
		printf("Plansza\n");
		return -2;
	}

  	int res = create_png(b, filename);

	printf("\n%d\n", res);
/*

	int i;

	char *naz;

	for(i = 0; i < 15; i++){
	
		naz = out_png_gen(i);

		free(naz);

	}
*/
}
