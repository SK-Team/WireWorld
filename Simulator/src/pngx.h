#ifndef _PNG_H_
#define _PNG_H_

int write_png_file(char *);

void process_file(board_t);

int create_png(board_t, char *);

int main(int, char **);

char *out_png_gen(int);

#endif

