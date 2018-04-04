#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "board.h"
#include "simulator.h"


int main(int argc, char** argv)
{
	FILE *plik_konfig;
	char *nazwa_pliku_konfig=NULL;
	int ile_generacji=-1; //nadano wartosc -1, zeby wiedziec czy uzytkownik sam podal parametr
	FILE *plik_wyjsciowy;
	char nazwa_pliku_wyjsciowego[100];//tworze w inny sposob niz nazwe pliku konfiguracyjnego, bo do tej bd doklejal prefiks ../bin/
	int i=2;
	FILE *plik_ustawien;
	int neighbourhood;
	int rules[9];
	
	int resultCheck; //do sprawdzania czy funkcje zadzialaly prawidlowo	

	board_t current_generation;

	while(argc>i)
	{
		if(strcmp(argv[i-1],"-i")==0)
		{	
			nazwa_pliku_konfig=argv[i];
		}
		else if(strcmp(argv[i-1],"-g")==0)
		{
			ile_generacji=atoi(argv[i]);
		}
		else if(strcmp(argv[i-1],"-o")==0)
		{
			strcpy(nazwa_pliku_wyjsciowego,"../bin/");
			strcat(nazwa_pliku_wyjsciowego,argv[i]);
		}
		i+=2;
	}	
	printf("|%s| |%d| |%s|\n ",nazwa_pliku_konfig,ile_generacji,nazwa_pliku_wyjsciowego);		
	if(nazwa_pliku_konfig == NULL)
		nazwa_pliku_konfig = "../data/generation_config"; //domyslna nazwa pliku konfiguracyjnego
	if(strstr(nazwa_pliku_wyjsciowego,"../bin/") == NULL)
		strcpy(nazwa_pliku_wyjsciowego,"../bin/last_generation_config"); //domyslna nazwa pliku wyjsciowego
	if(ile_generacji==-1)
		ile_generacji = 20; //domyslna ilosc generacji do zasymulowania


	plik_konfig = fopen(nazwa_pliku_konfig,"r");
	if(plik_konfig==NULL)
	{
		printf("Nie mozna otworzyc pliku %s.\n",nazwa_pliku_konfig);
		return EXIT_FAILURE;
	}
	plik_wyjsciowy = fopen(nazwa_pliku_wyjsciowego,"w");
	if(plik_wyjsciowy==NULL)
	{
		printf("Nie mozna otworzyc pliku %s.\n",nazwa_pliku_wyjsciowego);
		return EXIT_FAILURE;
	}
	plik_ustawien = fopen("../data/settings","r");
	if(plik_ustawien==NULL)
	{
		printf("Nie mozna otworzyc pliku ustawien: settings.\n");
		return EXIT_FAILURE;
	}


	if(ile_generacji==0) //jesli atoi zwroci 0, to znaczy, ze uzytkownik podal bledny parametr
	{
		printf("Podano bledna ilosc generacji do zasymulowania.\n");
		return EXIT_FAILURE;
	}

	resultCheck=read_rules(plik_ustawien,rules,&neighbourhood);
	if(resultCheck==1)
	{
		printf("Bledny format pliku ustawien.\n");
		return EXIT_FAILURE;
	}
	


	
	




	
	

	return 0;
}
