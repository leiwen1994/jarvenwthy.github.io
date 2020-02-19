#include "sss.h"
#include "safe-fork.h"
#include "split.h"
#include <err.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sysexits.h>
#include <unistd.h>

Commands read_commands(const char compile_cmds[], const char test_cmds[]) {
  FILE *comp_file, *test_file;
  char comp_ele[256], test_ele[256];
  Commands comm_list;
  Node *curr, *temp;
  /*check if the inputs are vaild*/
  if (compile_cmds == NULL || test_cmds == NULL) {
    exit(0);
  }
  /*initial the node*/
  comm_list.comp = NULL;
  comm_list.test = NULL;
  /*open the file*/
  comp_file = fopen(compile_cmds, "r");
  test_file = fopen(test_cmds, "r");
  /*check if the file is vaild*/
  if (comp_file == NULL || test_file == NULL) {
    exit(0);
  }
  /*fgets to go through line by line until end*/
  while (fgets(comp_ele, 256, comp_file) != NULL) {
    /*allocate node and check if its null*/
    temp = malloc(sizeof(Node));
    if (temp == NULL) {
      exit(0);
    }
    /*allocate string and check if its null*/
    temp->data = malloc(strlen(comp_ele) + 1);
    if (temp->data == NULL) {
      exit(0);
    }
    /*copy the string into node's data*/
    strcpy(temp->data, comp_ele);
    temp->next = NULL;
    /*put into list*/
    if (comm_list.comp == NULL) {
      comm_list.comp = temp;
      curr = comm_list.comp;
    } else {
      curr->next = temp;
      curr = curr->next;
    }
  }
  /*same thing did above*/
  while (fgets(test_ele, 256, test_file) != NULL) {
    temp = malloc(sizeof(Node));
    if (temp == NULL) {
      exit(0);
    }
    temp->data = malloc(strlen(test_ele) + 1);
    if (temp->data == NULL) {
      exit(0);
    }
    strcpy(temp->data, test_ele);
    temp->next = NULL;
    if (comm_list.test == NULL) {
      comm_list.test = temp;
      curr = comm_list.test;
    } else {
      curr->next = temp;
      curr = curr->next;
    }
  }
  fclose(comp_file);
  fclose(test_file);
  return comm_list;
}

void clear_commands(Commands *commands) {
  Node *comp_node;
  Node *comp_temp;
  Node *test_node;
  Node *test_temp;
  comp_node = commands->comp;
  test_node = commands->test;
  if (commands == NULL) {
    return;
  }
  /*make a temp node to store the memory and then free the original one */
  while (comp_node != NULL) {
    comp_temp = comp_node;
    comp_node = comp_node->next;
    free(comp_temp->data);
    free(comp_temp);
  }
  /*same thing*/
  while (test_node != NULL) {
    test_temp = test_node;
    test_node = test_node->next;
    free(test_temp->data);
    free(test_temp);
  }
}

int compile_program(Commands commands) {
  int pid;
  char **words;
  int status;
  Node *curr;
  int index;
  int input_fd = -1;
  int input_red = 0;
  int output_fd = -1;
  int output_red = 0;
  curr = commands.comp;
  while (curr != NULL) {
    index = 0;
    /*split the string*/
    words = split(curr->data);
    /*set a counter to track through the string*/
    while (words[index] != NULL) {
      index++;
    }
    /*if the string word greater than 2, check the second string is ">"*/
    if ((index > 2) && (strcmp(words[index - 2], ">") == 0)) {
      output_red = 1;
      /*flags*/
      output_fd = open(words[index - 1], O_WRONLY | O_CREAT | O_TRUNC);
    }
    /*greter and "<"*/
    if ((index > 2) && (strcmp(words[index - 2], "<") == 0)) {
      input_red = 1;
      /*flags*/
      input_fd = open(words[index - 1], O_RDONLY);
    }
    /*greater than 5 and if move back 4 string equal to "<"*/
    if ((index >= 5) && (strcmp(words[index - 4], "<") == 0)) {
      input_red = 1;
      /*flags*/
      input_fd = open(words[index - 3], O_RDONLY);
    }
    /*fork*/
    pid = safe_fork();
    if (pid > 0) {
      /*wait for children*/
      wait(&status);
      /*the exit status is not zero*/
      if (!WIFEXITED(status) || WEXITSTATUS(status) != 0) {
        return (FAILED_COMPILATION);
      }
    }
    /*children take over the words*/
    else if (pid == 0) {
      /*check input and output descriptor*/
      if (input_fd != -1 && input_red == 1) {
        dup2(input_fd, STDIN_FILENO);
      }
      if (output_fd != -1 && output_red == 1) {
        dup2(output_fd, STDOUT_FILENO);
      }
      execvp(words[0], words);
    }
    index = 0;
    /*free momory*/
    while (words[index] != NULL) {
      free(words[index]);
      index++;
    }
    free(words);
    curr = curr->next;
  }
  return (SUCCESSFUL_COMPILATION);
}
/*same thing*/
int test_program(Commands commands) {
  int pid;
  char **words;
  int status;
  Node *curr;
  int index;
  int count = 0;
  int input_fd = -1;
  int input_red = 0;
  int output_fd = -1;
  int output_red = 0;
  curr = commands.test;
  while (curr != NULL) {
    words = split(curr->data);
    index = 0;
    while (words[index] != NULL) {
      index++;
    }
    if ((index > 2) && (strcmp(words[index - 2], ">") == 0)) {
      output_red = 1;
      output_fd = open(words[index - 1], O_WRONLY | O_CREAT | O_TRUNC);
    }
    if ((index > 2) && (strcmp(words[index - 2], "<") == 0)) {
      input_red = 1;
      input_fd = open(words[index - 1], O_RDONLY);
    }
    if ((index >= 5) && (strcmp(words[index - 4], "<") == 0)) {
      input_red = 1;
      input_fd = open(words[index - 3], O_RDONLY);
    }
    pid = safe_fork();
    if (pid > 0) {
      wait(&status);
      if (WIFEXITED(status) && WEXITSTATUS(status) == 0) {
        count++;
      }
    } else if (pid == 0) {
      if (input_fd != -1 && input_red == 1) {
        dup2(input_fd, STDIN_FILENO);
      }
      if (output_fd != -1 && output_red == 1) {
        dup2(output_fd, STDOUT_FILENO);
      }
      execvp(words[0], words);
    }
    index = 0;
    while (words[index] != NULL) {
      free(words[index]);
      index++;
    }
    free(words);
    curr = curr->next;
  }
  return count;
}
