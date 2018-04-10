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
#include "pngx.h"

#define DEBUG

int x, y;

int width, height;
png_byte color_type;
png_byte bit_depth;

png_structp png_ptr;
png_infop info_ptr;
int number_of_passes;
png_bytep * row_pointers;

int write_png_file(char* file_name) {
	FILE *fp = fopen(file_name, "wb");
	if (!fp){
    		printf("[write_png_file] File %s could not be opened for writing", file_name);
		return 1;
	}

 	 png_ptr = png_create_write_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);

  	if (!png_ptr){
    		printf("[write_png_file] png_create_write_struct failed");
		return 1;
	}

  	info_ptr = png_create_info_struct(png_ptr);
  	if (!info_ptr){
    		printf("[write_png_file] png_create_info_struct failed");
		return 1;
	}

  	if (setjmp(png_jmpbuf(png_ptr))){
    		printf("[write_png_file] Error during init_io");
		return 1;
	}

  	png_init_io(png_ptr, fp);

  	if (setjmp(png_jmpbuf(png_ptr))){
    		printf("[write_png_file] Error during writing header");
		return 1;
	}

  	png_set_IHDR(png_ptr, info_ptr, width, height,
   		bit_depth, color_type, PNG_INTERLACE_NONE,
   		PNG_COMPRESSION_TYPE_BASE, PNG_FILTER_TYPE_BASE);

  	png_write_info(png_ptr, info_ptr);

  	if (setjmp(png_jmpbuf(png_ptr))){
    		printf("[write_png_file] Error during writing bytes");
		return 1;
	}

  	png_write_image(png_ptr, row_pointers);

  	if (setjmp(png_jmpbuf(png_ptr))){
    		printf("[write_png_file] Error during end of write");
		return 1;
	}

  	png_write_end(png_ptr, NULL);

  	for (y = 0; y < height; y++)
    		free(row_pointers[y]);
  	free(row_pointers);

  	fclose(fp);

	return 0;
}

/*
Wlasna czesc kodu
*/

void process_file(board_t b) {
  	width = b->columns;
 	 height = b->rows;
	  bit_depth = 8;
  	color_type = PNG_COLOR_TYPE_GRAY;

  	number_of_passes = 7;
  	row_pointers = (png_bytep*) malloc(sizeof(png_bytep) * height);
  	for (y = 0; y < height; y++)
    		row_pointers[y] = (png_byte*) malloc(sizeof(png_byte) * width);


  	for (y=0; y<height; y++) {
    		png_byte* row = row_pointers[y];
    		for (x=0; x<width; x++) {
      			row[x] = b->values[y][x] == '0'? 255 : 0;
#ifdef DEBUG
			printf("Pixel at position [ %d - %d ] has RGBA values: %d\n",
       				x, y, row[x]);
#endif
    		}
  	}


}



int create_png(board_t b, char *filename){


	int res = 0;

	process_file(b);

	res = write_png_file(filename);

	return res;

}



char * out_png_gen(int generation_no){

	char * nazwa;

	int how_many_chars = 1;

	int i;

	int gen_no = generation_no;

	while(generation_no >= 10){

		how_many_chars++;

		generation_no = generation_no / 10;
	}



	nazwa = malloc( (14 + how_many_chars) * sizeof(char));

	strcpy(nazwa, "../bin/out");

	for(i = 0;i < how_many_chars; i++){

		nazwa[i + 10] = gen_no % 10 + '0';

		gen_no = gen_no / 10;

	}

	nazwa[10 + i++] ='.';

	nazwa[10 + i++] ='p';
	
	nazwa[10 + i++] ='n';

	nazwa[10 + i++] ='g';

	nazwa[10 + i] = '\0';


	return nazwa;
	

}












