#include <stdio.h>
#include <stdlib.h>

#include "Board.h"

board_t make_board(int height, int width){
	
	board_t nb =  malloc(sizeof(*nb));

	int i,j;

	if (nb == NULL)
		return NULL;

	nb->values =   malloc(height * sizeof(char *));

	if(nb->values == NULL){
		free(nb);
		return NULL;
	}

	for (i = 0; i <height; i++) {
		nb->values[i] =  malloc(width * sizeof(char));
		if(nb->values[i] == NULL){
			for(j=0; j<i; j++)
				free(nb->values[j]);
			free(nb->values);
			free(nb);
			return NULL;
		}
	}
	
	nb->rows = height;
	nb->columns = width;

	return nb;
	
}
