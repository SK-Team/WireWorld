#include "simulator.h"

int count_neighbours_alive(int x, int y, int neighbouthood, board_t b)
{
	int alive=0;
	alive+=(x-1>-1)?((b->values[x-1][y]==1)?1:0):0;		
	alive+=(x+1<b->columns)?((b->values[x+1][y]==1)?1:0):0;		
	alive+=(y-1>-1)?((b->values[x][y-1]==1)?1:0):0;	
	alive+=(y+1<b->rows)?((b->values[x][y+1]==1)?1:0):0;		
	
	if(neighbouthood==8)
	{
		alive+=(x-1>-1 && y+1<b->rows)?((b->values[x-1][y+1]==1)?1:0):0;		
		alive+=(x+1<b->columns && y+1<b->rows)?((b->values[x+1][y+1]==1)?1:0):0;		
		alive+=(x+1<b->columns && y-1>-1)?((b->values[x+1][y-1]==1)?1:0):0;		
		alive+=(x-1>-1 && y-1>-1)?((b->values[x-1][y-1]==1)?1:0):0;		
	}
	return alive;
}
int what_happens_with_cell(int current_state,int neighbours_alive, int rules[])
{
	
	switch(rules[neighbours_alive])
	{
		case 0:
			return 0;
		case 1:
			if(current_state==1)return 1;
			return 0;
		case 2:
			if(current_state==0)return 1;
			return 0;
		case 3:
			return 1;
		

	}
}
board_t simulate_generation(board_t previous_generation, int rules[], int neighbourhood)
{
	int i,j;
	board_t nextGen = make_board(previous_generation->rows,previous_generation->columns);
	for(i=0; i<previous_generation->rows; i++)
	{
		for(j=0; j<previous_generation->columns; j++)
		{	
			nextGen->values[i][j]=what_happens_with_cell(previous_generation->values[i][j],count_neighbours_alive(i,j,neighbourhood,previous_generation),rules);		
		}
		
	}
	
	
}


