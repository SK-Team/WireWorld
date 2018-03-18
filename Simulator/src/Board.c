#include <stdio.h>
#include <stdlib.h>

#include "Board.h"

board_t make_board(int height, int width){
	
	board_t nb = malloc(sizeof(*nb));
	
	int i,j;

	if (nb == NULL)
		return NULL;

	nb->values = malloc(height * sizeof(char *));

	if (nb->values == NULL){
		free(nb);
		return NULL;
	}

	for (i = 0; i < height; i++) {
		nb->values[i] =  malloc(width * sizeof(char));
		if (nb->values[i] == NULL){
			for(j = 0; j < i; j++)
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

board_t free_board(board_t b){

	int i;
	
	for (i = 0; i < b->rows; i++)
		free(b->values[i]);

	free(b->values);
	free(b);

	return NULL;
}

board_t read_board_file(FILE* in){
	
	int i,j;

	int height, width;

	board_t b;

	if (fscanf(in, "%d %d", &height, &width) != 0)
		return NULL;

	if ((b = make_board(height, width)) == NULL)
		return NULL;

	for (i = 0; i < b->rows; i++)
		for (j = 0; j < b->columns; j++){
			if ((fscanf(in, "%c", &(b->values[i][j]))) != 0){
				free(b);
				return NULL;
			}
		}

	return b;
}

void print_board(FILE *out, board_t b){
	
	int i,j;

	for (i = 0; i < b->rows; i++){
		for (j = 0; j < b->columns; j++)
			fprintf(out, "%c ", b->values[i][j]);
		fprintf(out, "\n");
	}
}
