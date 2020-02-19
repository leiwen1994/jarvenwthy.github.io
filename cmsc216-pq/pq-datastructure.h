#if !defined(PQ_DATASTRUCTURE_H)
#define PQ_DATASTRUCTURE_H
typedef struct node {
  char *data;
  struct node *next;
  int priority;
} Node;

typedef struct Priority_queue {
  Node *head;
  int size;
} Priority_queue;

#endif
