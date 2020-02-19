#if !defined(SSS_DATASTRUCTURE_H)
#define SSS_DATASTRUCTURE_H

typedef struct node {
  struct node *next;
  char *data;
} Node;

typedef struct commands {
  Node *comp;
  Node *test;
} Commands;

#endif
