#ifndef _BOARD_H_

#define _BOARD_H_

#include <stdio.h>
#include <stdlib.h>

typedef struct{

	int rows, columns;
	char **values;

} *board_t;

board_t read_board_file(char *file_name);

board_t make_board(int height, int width);

board_t free_board(board_t b);

void print_board(FILE *out, board_t b);

#endif