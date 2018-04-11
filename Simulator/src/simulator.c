#include "simulator.h"
int read_rules(FILE *in, int rules[], int*neighbourhood)
{
	char tmp; //do wczytywania znakow z pliku ustawien
	int pom; //do konwersji zasad gry na liczby calkowite
	int i;
	if(fscanf(in,"%c",&tmp)!=1)
		return 1;
	if(tmp=='N')
		*neighbourhood=4;
	else if(tmp=='M')
		*neighbourhood=8;
	else return 1; //bledny format pliku

	for(i=0; i<=*neighbourhood; i++)
	{
		rules[i]=0;
	}
	

	while((tmp=fgetc(in))!=EOF && tmp!='/')
	{
		if(tmp=='\n')
			continue;
		pom = (int)tmp-48; //konwersja na int
		if(pom<0 || pom>*neighbourhood)
			return 1;
		rules[pom]=1;
	}
	while((tmp=fgetc(in))!=EOF)
	{
		if(tmp=='\n')
			continue;
		pom = (int)tmp-48;
		if(pom<0 || pom>*neighbourhood)
			return 1;
		if(rules[pom]==1) //rodzi sie lub przezywa w zalezosci od wlasnego stanu
		{
			rules[pom]=3;
		}
		else
		{
			rules[pom]=2;
		}
	}
	return 0;	
}
int count_neighbours_alive(int x, int y, int neighbourhood, board_t b)
{
	int alive=0;
	alive+=(y-1>-1)?((b->values[y-1][x]=='1')?1:0):0;		
	alive+=(y+1<b->rows)?((b->values[y+1][x]=='1')?1:0):0;		
	alive+=(x-1>-1)?((b->values[y][x-1]=='1')?1:0):0;	
	alive+=(x+1<b->columns)?((b->values[y][x+1]=='1')?1:0):0;		
	
	if(neighbourhood==8)
	{
		alive+=(y-1>-1 && x+1<b->columns)?((b->values[y-1][x+1]=='1')?1:0):0;		
		alive+=(y+1<b->rows && x+1<b->columns)?((b->values[y+1][x+1]=='1')?1:0):0;		
		alive+=(y+1<b->rows && x-1>-1)?((b->values[y+1][x-1]=='1')?1:0):0;		
		alive+=(y-1>-1 && x-1>-1)?((b->values[y-1][x-1]=='1')?1:0):0;		
	}
	return alive;
}
char what_happens_with_cell(char current_state,int neighbours_alive, int rules[])
{
	
	switch(rules[neighbours_alive])
	{
		case 0:
			return '0';
		case 1:
			if(current_state=='1')return '1';  
			return '0';
		case 2:
			if(current_state=='0')return '1';
			return '0';
		case 3:
			return '1';
	}
}
board_t simulate_generation(board_t previous_generation, int rules[], int neighbourhood)
{
	int i,j;
	board_t nextGen = make_board(previous_generation->rows,previous_generation->columns);
	for(i=0; i<previous_generation->rows; i++)
	{	for(j=0; j<previous_generation->columns; j++)
		{	
			nextGen->values[i][j]=what_happens_with_cell(previous_generation->values[i][j],count_neighbours_alive(j,i,neighbourhood,previous_generation),rules);		
		}
		
	}
	previous_generation=free_board(previous_generation);
	return nextGen;	
}
