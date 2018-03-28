#include <stdio.h>
#include "board.h"
#include "simulator.h"

void should_show1_generation_after_simulation(board_t previous_generation, int rules[], int neighbourhood)
{

	board_t new_generation = simulate_generation(previous_generation,rules, neighbourhood);
	print_board(stdout,new_generation);	
	
	

}


int main(int argc, char** argv)
{
	FILE *in = argc>1?fopen(argv[1],"r",):NULL;
	if(in==NULL)
		fprintf(stderr,"Nie moge odczytac pliku z plansza. Test nie zadziala\n");
	//Trzeba wstawic czytanie ustawien
	board_t generation = read_board_file(in);
	should_show1_generation_after_simulation(generation,rules,neighbourhood);


}
