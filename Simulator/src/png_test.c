#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "board.h"
#include "pngx.h"

void should_show_generated_file_names(){ //zmiana w stosunku do specyfikacji
					 //dodanie tej funkcji testujacej 
	
	int i;

	char *filename;

	printf("\nWygenerowane przykladowe nazwy plikow:\n");

	for(i = 1; i < 100000; i*=10){
	
		filename = out_png_gen(i);

		printf("%s\n", filename);

		free(filename);

	}

}

void should_show_confirmation_after_png_file_generation(){
	

	FILE * in = fopen("../data/generation_config", "r");

	char *filename = "../bin/out.png";

	if(in == NULL){
		printf("Blad w odczycie pliku wejsciowego\n");
		return;
	}

	board_t b = read_board_file(in);


	if(b == NULL){
		printf("Blad w odczycie planszy\n");
		return;
	}

  	int res = create_png(b, filename);

	free_board(b);

	fclose(in);

	if(res == 0)
		printf("\nUdalo sie wygenerowac plik png o nazwie: %s\n", filename);
	else
		printf("\nNie udalo sie wygenerowac pliku png\n");


}

int main(int argc, char **argv) {
  	
	if(argc > 1){
	
		if(strcmp(argv[1], "-f") == 0){
		
			printf("Test generacji nazw plikow png.\n");
			should_show_generated_file_names();

		} else if (strcmp(argv[1], "-p") == 0){
		
			printf("Test generacji plikow png.\n");
			should_show_confirmation_after_png_file_generation();

		} else if (strcmp(argv[1], "-help") == 0 || strcmp(argv[1], "-h") == 0 || strcmp(argv[1], "--help") == 0) {
		
			printf("\n");
			printf("Uzycie programu testujacego modul png\n");
			printf("\n");
			printf("./png_test [argument]\n");
			printf("\n");
			printf("Lista argumentow:\n");
			printf("--help, -help, -h - wywolanie pomocy,\n");
			printf("-f - wywolanie testu generacji nazw plikow png,\n");
			printf("-p - wywolanie testu generacji plikow png,\n");
			printf("\n");

		} 
		
	} else {
			
			printf("\n");
			printf("Uzycie programu testujacego modul png\n");
			printf("\n");
			printf("./png_test [argument]\n");
			printf("\n");
			printf("Lista argumentow:\n");
			printf("--help, -help, -h - wywolanie pomocy,\n");
			printf("-f - wywolanie testu generacji nazw plikow png,\n");
			printf("-p - wywolanie testu generacji plikow png,\n");
			printf("\n");

	}

	return 0;
}
