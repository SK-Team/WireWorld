#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "board.h"
#include "simulator.h"
#include "pngx.h"

int main(int argc, char** argv)
{
	FILE *config_file;
	char *config_file_name=NULL;
	int how_many_generations=-1; //nadano wartosc -1, zeby wiedziec czy uzytkownik sam podal parametr
	FILE *out_file;
	char out_file_name[100];//tworze w inny sposob niz nazwe pliku konfiguracyjnego, bo do tej bd doklejal prefiks ../bin/
	int i=2; //do sprawdzania argumentow wywolania oraz pozniejszych iteracji
	int j; //do przyszlych iteracji
	FILE *settings_file;
	int neighbourhood;
	int rules[9];
	
	int resultCheck; //do sprawdzania czy funkcje zadzialaly prawidlowo	

	board_t current_generation;
	char command[15];	 //do przechodzenia generacji, pomijania lub zapisywania
	int if_correct_command;	
	int command_length;
	int tmp; //do tymczasowych podstawien
	int generations_to_skip;

	char* out_png_file = "../bin/out.png";

	while(argc>i)
	{
		if(strcmp(argv[i-1],"-i")==0)
		{	
			config_file_name=argv[i];
		}
		else if(strcmp(argv[i-1],"-g")==0)
		{
			how_many_generations=atoi(argv[i]);
		}
		else if(strcmp(argv[i-1],"-o")==0)
		{
			strcpy(out_file_name,"../bin/");
			strcat(out_file_name,argv[i]);
		}
		i+=2;
	}	
	
	if(config_file_name == NULL)
		config_file_name = "../data/generation_config"; //domyslna nazwa pliku konfiguracyjnego
	if(strstr(out_file_name,"../bin/") == NULL)
		strcpy(out_file_name,"../bin/last_generation_config"); //domyslna nazwa pliku wyjsciowego
	if(how_many_generations==-1)
		how_many_generations = 20; //domyslna ilosc generacji do zasymulowania

//	printf("|%s| |%d| |%s|\n ",nazwa_pliku_konfig,ile_generacji,nazwa_pliku_wyjsciowego);		

	config_file = fopen(config_file_name,"r");
	if(config_file==NULL)
	{
		printf("Nie mozna otworzyc pliku %s.\n",config_file_name);
		return EXIT_FAILURE;
	}
	out_file = fopen(out_file_name,"w");
	if(out_file==NULL)
	{
		printf("Nie mozna otworzyc pliku %s.\n",out_file_name);
		return EXIT_FAILURE;
	}
	settings_file = fopen("../data/settings","r");
	if(settings_file==NULL)
	{
		printf("Nie mozna otworzyc pliku ustawien: settings.\n");
		return EXIT_FAILURE;
	}


	if(how_many_generations==0) //jesli atoi zwroci 0, to znaczy, ze uzytkownik podal bledny parametr
	{
		printf("Podano bledna ilosc generacji do zasymulowania.\n");
		return EXIT_FAILURE;
	}

	resultCheck=read_rules(settings_file,rules,&neighbourhood);
	if(resultCheck==1)
	{
		printf("Bledny format pliku ustawien.\n");
		return EXIT_FAILURE;
	}
	
	current_generation = read_board_file(config_file);
	
	i=0;
	while(i<how_many_generations)
	{
		printf("\n----- Generacja nr %d -----\n",i);
		print_board(stdout,current_generation);
		if_correct_command=0;
		generations_to_skip=0;
		while(!if_correct_command)
		{
			//scanf("%s",command);
			fgets(command,14,stdin);
			command_length=strlen(command)-1;//ignoruje znak konca linii;
			if(strstr(command,"n")!=NULL)
			{
				
				if(command_length>2)
				{
					if(command[1]!=' ')
						continue;
					tmp=atoi(&command[2]);
					tmp=tmp>0?tmp:1;
					generations_to_skip=tmp;
					if_correct_command=1;	
					continue;
				}
				else if(command_length>1)
				{
					if(command[1]!=' ')
						continue;
					else //ta opcja dopuszcza wpisanie "n " - potraktowane bedzie jako "n"
					{
						generations_to_skip=1;
						if_correct_command=1;
						continue;	
					}
				}
				else
				{
					generations_to_skip=1;
					if_correct_command=1;
					continue;
				}
			}
			else if(strcmp(command,"s\n")==0)
			{
				if_correct_command=1;

				int res = create_png(current_generation, out_png_file);

				if(res == 0)
					printf("Udalo sie zapisac generacje do pliku.\n");
				else
					printf("Nie udalo sie zapisac generacje do pliku.\n");

				//tu zrealizowac zapisywanie png i wypisac czy poprawnie
				generations_to_skip=1;
			}		
		}
		for(j=0; j<generations_to_skip && j+i<how_many_generations; j++)
		{
			current_generation=simulate_generation(current_generation,rules,neighbourhood);
		}
		if(i+generations_to_skip<how_many_generations)
			i+=generations_to_skip;
		else
		{
			printf("\n----- Generacja nr %d -----\n",how_many_generations);
			print_board(stdout,current_generation);
			break;
			
		}
		
	
	}
	print_board(out_file,current_generation); //wypisanie ostatniej generacji do pliku wyjsciowego

	



	
	

	return 0;
}
