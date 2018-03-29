/*
Kod zaczerpniety z iSODa ze strony materialow dla przedmiotu JIMP 2,
zamieszczony tam przez pana dr B. Chabra 
*/

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdarg.h>

#include <png.h>

#include "board.h"

int x, y;

int width, height;
png_byte color_type;
png_byte bit_depth;

png_structp png_ptr;
png_infop info_ptr;
int number_of_passes;
png_bytep * row_pointers;

void write_png_file(char* file_name) {
	FILE *fp = fopen(file_name, "wb");
	if (!fp)
    		printf("[write_png_file] File %s could not be opened for writing", file_name);

 	 png_ptr = png_create_write_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);

  	if (!png_ptr)
    		printf("[write_png_file] png_create_write_struct failed");

  	info_ptr = png_create_info_struct(png_ptr);
  	if (!info_ptr)
    		printf("[write_png_file] png_create_info_struct failed");

  	if (setjmp(png_jmpbuf(png_ptr)))
    		printf("[write_png_file] Error during init_io");

  	png_init_io(png_ptr, fp);

  	if (setjmp(png_jmpbuf(png_ptr)))
    		printf("[write_png_file] Error during writing header");

  	png_set_IHDR(png_ptr, info_ptr, width, height,
   		bit_depth, color_type, PNG_INTERLACE_NONE,
   		PNG_COMPRESSION_TYPE_BASE, PNG_FILTER_TYPE_BASE);

  	png_write_info(png_ptr, info_ptr);

  	if (setjmp(png_jmpbuf(png_ptr)))
    		printf("[write_png_file] Error during writing bytes");

  	png_write_image(png_ptr, row_pointers);

  	if (setjmp(png_jmpbuf(png_ptr)))
    		printf("[write_png_file] Error during end of write");

  	png_write_end(png_ptr, NULL);

  	for (y = 0; y < height; y++)
    		free(row_pointers[y]);
  	free(row_pointers);

  	fclose(fp);
}

/*
Wlasna czesc kodu
*/

void process_file(board_t b) {
  	width = b->rows;
 	 height = b->columns;
	  bit_depth = 8;
  	color_type = PNG_COLOR_TYPE_GRAY;

  	number_of_passes = 7;
  	row_pointers = (png_bytep*) malloc(sizeof(png_bytep) * height);
  	for (y = 0; y < height; y++)
    		row_pointers[y] = (png_byte*) malloc(sizeof(png_byte) * width);

  	for (y=0; y<height; y++) {
    		png_byte* row = row_pointers[y];
    		for (x=0; x<width; x++) {
      			row[x] = b->values[x][y] == '1'? 255 : 0;
      			printf("Pixel at position [ %d - %d ] has RGBA values: %d\n",
       				x, y, row[x]);
    		}
  	}
}


int main(int argc, char **argv) {
  	
	FILE * in = fopen("../data/test/test0", "r");

	if(in == NULL){
		printf("Plik\n");
		return -1;
	}

	board_t b = read_board_file(in);


	if(b == NULL){
		printf("Plansza\n");
		return -2;
	}

	process_file(b);
  	write_png_file("out.png");

  	return 0;
}
