#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "board.h"

void should_show_confirmation_for_successful_memory_allocation(int height, int width){
	
	board_t b = make_board(height, width);

	if(b != NULL){
		printf("Udalo sie zaalokowac pamiec na plansze!\n");
	}

	free_board(b);

}

void should_show_error_message_for_unsuccessful_memory_allocation(int height, int width){
	
	board_t b = make_board(height, width);

	if(b == NULL){
		printf("Nie udalo sie zaalokowac pamiec na plansze!\n");	
	} else
		free_board(b);

}

void should_print_board(FILE *out, board_t b){
	
	print_board(out, b);
}

void should_show_board_for_correct_board_file(FILE *out){//zmiana w stosunku do specyfikacji,
							 //FILE * zamiast char * file_name

	board_t b = read_board_file(out);

	if(b != NULL){
		print_board(stdout, b);
		free_board(b);
	}
}

void should_show_error_message_for_incorrect_board_file(FILE *out){//zmiana w stosunku do specyfikacji,
                                                          	   //FILE * zamiast char * file_name

	board_t b = read_board_file(out);

	if(b == NULL)
		printf("Bledny plik wejsciowy! Nie udalo sie wczytac macierzy!\n");
	else
		free_board(b);
}



int main(int argc, char **argv){

	if(argc > 1){
		
		if(strcmp(argv[1], "-sa") == 0){
			
			printf("Test poprawnego alokowania pamieci na plansze.\n");
			should_show_confirmation_for_successful_memory_allocation(25, 25);
		
		} else if (strcmp(argv[1], "-ua") == 0){

			printf("Test niepoprawnego alokowania pamieci na plansze.\n");
			should_show_error_message_for_unsuccessful_memory_allocation(25, -2);
		
		} else if (strcmp(argv[1], "-p") == 0){
			
			printf("Test wypisywania planszy.\n");
			FILE *in = fopen("../data/test/test0","r"); 
			FILE *out = argc > 2 ? fopen(argv[2], "w") : stdout;	

			if(out == NULL || in == NULL){
				printf("Nie mozna otworzyc pliku do testu.\n");
				return 1;
			}
				           
			board_t b = read_board_file(in);
			   
			if(b == NULL){
				printf("Brak pamieci na plansze.\n");
				fclose(in);
				return 2;
			}

			print_board(out ,b);
			free_board(b);
			fclose(out);
			fclose(in);

		} else if (strcmp(argv[1], "-cr") == 0){
			
			printf("Test poprawnego czytania planszy z pliku.\n");

			FILE *out = argc > 2 ? fopen(argv[2], "r") : fopen("../data/test/test0", "r");

			if(out == NULL){
				printf("Nie mozna otworzyc pliku do testu.\n");
				return 1;
			}
				           
			should_show_board_for_correct_board_file(out);

			fclose(out);

		} else if (strcmp(argv[1], "-ir") == 0){
			
			printf("Test niepoprawnego czytania planszy z pliku.\n");

			FILE *out = argc > 2 ? fopen(argv[2], "r") : fopen("../data/test/test3", "r");

			if(out == NULL){
				printf("Nie mozna otworzyc pliku do testu.\n");
				return 1;
			}
				           
			should_show_error_message_for_incorrect_board_file(out);

			fclose(out);

		} else if (strcmp(argv[1], "-help") == 0 || strcmp(argv[1], "-h") == 0 || strcmp(argv[1], "--help") == 0) {
		
			printf("\n");
			printf("Uzycie programu testujacego modul board\n");
			printf("\n");
			printf("./board_test [argument] [path]\n");
			printf("\n");
			printf("Lista argumentow:\n");
			printf("--help, -help, -h - wywolanie pomocy,\n");
			printf("-sa - wywolanie testu poprawnego alokowania pamieci na plansze,\n");
			printf("-ua - wywolanie testu niepoprawnego alokowania pamieci na plansze,\n");
			printf("-p - wywolanie testu drukowania,\n");
			printf("-cr - wywolanie testu poprawnego czytania z pliku,\n");
			printf("      po podaniu sciezki pliku na nim wykonac test,\n");
			printf("-ir - wywolanie testu niepoprawnego czytania z pliku,\n");
			printf("      po podaniu sciezki pliku na nim wykonac test.\n");
			printf("\n");

		} 
		
	} else {
			
			printf("\n");
			printf("Uzycie programu testujacego modul board\n");
			printf("\n");
			printf("./board_test [argument] [path]\n");
			printf("\n");
			printf("Lista argumentow:\n");
			printf("--help, -help, -h - wywolanie pomocy,\n");
			printf("-sa - wywolanie testu poprawnego alokowania pamieci na plansze,\n");
			printf("-ua - wywolanie testu niepoprawnego alokowania pamieci na plansze,\n");
			printf("-p - wywolanie testu drukowania,\n");
			printf("-cr - wywolanie testu poprawnego czytania z pliku,\n");
			printf("      po podaniu sciezki pliku na nim wykonac test,\n");
			printf("-ir - wywolanie testu niepoprawnego czytania z pliku,\n");
			printf("      po podaniu sciezki pliku na nim wykonac test.\n");
			printf("\n");

	}

	return 0;

}
