#include <stdio.h>
#include "board.h"
#include "simulator.h"

void should_show1_generation_after_simulation(board_t previous_generation, int rules[], int neighbourhood) {
    board_t new_generation = simulate_generation(previous_generation, rules, neighbourhood);
    print_board(stdout, new_generation);
}


int main(int argc, char **argv) {
    int rules[9];
    int neighbourhood;
    FILE *wejsciowy, *ustawienia;
    wejsciowy = argc > 1 ? fopen(argv[1], "r") : NULL;
    ustawienia = argc > 2 ? fopen(argv[2], "r") : NULL;
    if (wejsciowy == NULL) {
        fprintf(stderr, "Nie moge odczytac pliku z plansza. Test nie zadziala\n");
        return EXIT_FAILURE;
    }
    if (ustawienia == NULL) {
        fprintf(stderr, "Nie moge odczytac pliku ustawien. Test nie zadziala\n");
        return EXIT_FAILURE;
    }

    read_rules(ustawienia, rules, &neighbourhood);

    board_t generation = read_board_file(wejsciowy);
    if (generation == NULL) {
        fprintf(stderr, "Korczakowski to pajac\n");
        return EXIT_FAILURE;
    }
    should_show1_generation_after_simulation(generation, rules, neighbourhood);
}
