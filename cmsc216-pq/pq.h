/* (c) Larry Herman, 2018.  You are allowed to use this code yourself, but
   not to provide it to anyone else. */

#include "pq-datastructure.h"

void init(Priority_queue *const pq);
int enqueue(Priority_queue *const pq, const char new_element[], int priority);
int is_empty(const Priority_queue pq);
int size(const Priority_queue pq);
char *dequeue(Priority_queue *const pq);
void clear(Priority_queue *const pq);
char **get_all_elements(Priority_queue pq);
int remove_elements_between(Priority_queue *const pq, int low, int high);
