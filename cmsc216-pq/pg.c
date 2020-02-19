/*
Name: Jiawen Lei
Directer ID: jlei4
Uid: 115694569
section: 0107
description: creat own data-structure in order to make a link list that has
priority. and faction provided can support different purpose and make one extra
faction in this project
Hornor Pledge: I pledge on my honor that I have not
given or received any unauthorized assistance on this assignment/examination.
*/

#include "pq.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void init(Priority_queue *const pq) {
  if (pq == NULL)
    return;

  pq->head = NULL;
  pq->size = 0;
}

int enqueue(Priority_queue *const pq, const char new_element[], int priority) {
  Node *temp;
  Node *n;
  /*check if the varaible is null*/
  if (pq == NULL || new_element == NULL || priority < 0)
    return 0;
  /*check if has the node with same priority*/
  if (pq->size > 1) {
    Node *same_priority = pq->head;
    while (same_priority != NULL) {
      if (same_priority->priority == priority) {
        return 0;
      }
      same_priority = same_priority->next;
    }
  }
  /*make a deep copy of a node*/
  temp = pq->head;
  n = malloc(sizeof(Node));
  if (n == NULL) {
    return 0;
  }
  n->data = malloc(strlen(new_element) + 1);
  strcpy(n->data, new_element);
  n->priority = priority;
  /*when nothing in the Priority_queue or priority is greater than the head
   * priority*/
  if (pq->size == 0 || temp->priority < priority) {
    n->next = pq->head;
    pq->head = n;
    pq->size = pq->size + 1;
  } else {
    /*using while loop to go through the entir link list and find right
     * posistion*/
    while (temp->next != NULL && temp->next->priority > priority) {
      temp = temp->next;
    }
    n->next = temp->next;
    temp->next = n;
    pq->size = pq->size + 1;
  }
  return 1;
}
/*check if the Priority_queue is empty or nor*/
int is_empty(const Priority_queue pq) {
  if (pq.size == 0)
    return 1;

  return 0;
}

int size(const Priority_queue pq) {
  /*return the size of the link list*/
  return pq.size;
}

/*deep copy a node without modify the original list*/
char *peek(Priority_queue pq) {
  char *temp;
  if (pq.size == 0 || pq.head == NULL)
    return NULL;

  temp = malloc(sizeof(pq) * sizeof(char *));
  if (temp == NULL) {
    return NULL;
  }
  strcpy(temp, pq.head->data);
  return temp;
}
/*find the node has highest priority then remove it*/
char *dequeue(Priority_queue *const pq) {
  Node *temp;
  if (pq == NULL) {
    return NULL;
  }
  if (pq->size == 0) {
    return NULL;
  } else {
    temp = pq->head;
    pq->head = pq->head->next;
    pq->size = pq->size - 1;
    return temp->data;
  }
}
/*clear everything*/
void clear(Priority_queue *const pq) {
  while (pq->head != NULL) {
    pq->head = pq->head->next;
  }
  pq->size = 0;
}
/*copy the element to a char arrays*/
char **get_all_elements(Priority_queue pq) {
  int i;
  Node *temp = pq.head;
  char **arr = malloc((pq.size + 1) * sizeof(char *));
  if (arr == NULL) {
    return NULL;
  }
  if (pq.size < 0)
    return 0;

  if (pq.size == 0)
    arr[0] = NULL;

  for (i = 0; i < pq.size; i++) {
    arr[i] = malloc(strlen(temp->data) + 1);
    strcpy(arr[i], temp->data);
    temp = temp->next;
  }
  arr[pq.size] = NULL;

  return arr;
}
/*check the priority and remove the node between the low and hight*/
int remove_elements_between(Priority_queue *const pq, int low, int high) {
  Node *temp = pq->head;
  Node *high_priority = pq->head;
  int count;
  if (pq->head == NULL)
    return 0;
  /*check any invaild cases*/
  if (low > high || high < low)
    return 0;

  /*loop through till high is greater than the temp priority*/
  while (temp->priority > high) {
    high_priority = temp;
    temp = temp->next;
  }
  while (temp->priority >= low) {
    temp = temp->next;
    count++;
  }

  high_priority->next = temp;
  pq->size = pq->size - count;

  return 1;
}
