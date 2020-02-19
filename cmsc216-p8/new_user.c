/*name:jiawen lei
UID:115694569
directorID:jlei4
*/
#include "document.h"
#include <ctype.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sysexits.h>

#define MAX_CHAR_LENGTH 1024
#define EOF (-1)

static void add_paragraph_helper(Document *doc, char *line, char *command,
                                 char *text);
static void add_line_helper(Document *doc, char *line, char *command);
static void append_helper(Document *doc, char *line, char *command);
static void remove_line_helper(Document *doc, char *line, char *command);
static void load_helper(Document *doc, char *line, char *command, char *text);
static void replace_helper(Document *doc, char *line, char *command,
                           char *text);
static void highlight_helper(Document *doc, char *line, char *command,
                             char *text);
static void remove_text_helper(Document *doc, char *line, char *command,
                               char *text);

static void save_helper(Document *doc, char *line, char *command, char *text);

int main(int argc, char *argv[]) {
  FILE *fp;
  char line[MAX_CHAR_LENGTH + 1];
  char command[MAX_CHAR_LENGTH + 1] = {'\0'};
  char text[MAX_CHAR_LENGTH + 1] = {'\0'};
  int vaild_input = 0;
  Document main_document;
  /*if there hsa no file, the program will read from the input*/
  if (argc == 1) {
    fp = stdin;
  } else if (argc == 2) {
    /*if there is a file, the program will read from the filename*/
    fp = fopen(argv[1], "r");
    /*In case of an error opening the file your program should print (to
     * standard error) the message ”FILENAME cannot be opened.” where FILENAME
     * represents the file name. The program will then exit with the exit code
     * EX_OSERR.*/
    if (fp == NULL) {
      fprintf(stderr, "%s cannot be opened.\n", argv[1]);
      exit(EX_OSERR);
    }
  } else {
    /*print output from usage*/
    fprintf(stderr, "Usage: user_interface\n");
    fprintf(stderr, "Usage: user_interface <filename>\n");
    exit(EX_USAGE);
  }
  /*accord with the output*/
  if (argc == 1) {
    printf(">");
  }
  /*initialize the document by provied*/
  init_document(&main_document, "main_document");
  /*read till the end of the file*/
  while (fgets(line, MAX_CHAR_LENGTH + 1, fp) != NULL) {
    vaild_input = sscanf(line, "%s %s", command, text);
    /*check all invilid cases*/
    if (line[0] == '#' || line[0] == '\n' || vaild_input == EOF) {
      if (argc == 1) {
        fp = stdin;
      }
    }
    /*helper method will help print the output*/
    if (strcmp(command, "add_paragraph_after") == 0) {
      add_paragraph_helper(&main_document, line, command, text);
    }
    if (strcmp(command, "add_line_after") == 0) {
      add_line_helper(&main_document, line, command);
    }
    if (strcmp(command, "print_document") == 0) {
      if (text[0] != '\0') {
        fprintf(stdout, "Invalid Command\n");
      } else {
        print_document(&main_document);
      }
    }
    if (strcmp(command, "quit") == 0) {
      if (text[0] != '\0') {
        fprintf(stdout, "Invalid Command\n");
      } else {

        exit(EXIT_SUCCESS);
      }
    }
    if (strcmp(command, "exit") == 0) {
      if (text[0] != '\0') {
        fprintf(stdout, "Invalid Command\n");
      } else {
        exit(EXIT_SUCCESS);
      }
    }
    if (strcmp(command, "append_line") == 0) {
      append_helper(&main_document, line, command);
    }

    if (strcmp(command, "remove_line") == 0) {
      remove_line_helper(&main_document, line, command);
    }
    if (strcmp(command, "replace_text") == 0) {
      replace_helper(&main_document, line, command, text);
    }
    if (strcmp(command, "load_file") == 0) {
      load_helper(&main_document, line, command, text);
    }
    if (strcmp(command, "highlight_text") == 0) {
      highlight_helper(&main_document, line, command, text);
    }
    if (strcmp(command, "remove_text") == 0) {
      remove_text_helper(&main_document, line, command, text);
    }
    if (strcmp(command, "save_document") == 0) {
      save_helper(&main_document, line, command, text);
    }
    if (strcmp(command, "reset_document") == 0) {
      reset_document(&main_document);
      if (command[0] != '\0') {
        fprintf(stdout, "Invalid Command\n");
      }
    }
    /*initialize command and input again*/
    command[0] = '\0';
    text[0] = '\0';
    /*match the output*/
    if (argc == 1) {
      printf(">");
    }
  }
  /*close file and return 1*/
  fclose(fp);
  return 1;
}

static void add_paragraph_helper(Document *doc, char *line, char *command,
                                 char *text) {
  int digit = 0, i = 0, paragraph_num = 0;
  int result = 0;
  sscanf(line, "%s %s", command, text);
  /*check if no addition input after paragraph_num*/
  if (text[0] != '\0') {
    /*check paragraph_num is number*/
    while (text[i] != '\0') {
      digit = isdigit(text[i]);
      i++;
    }
    /*check paragraph_num is missing and < 0*/
    if (digit != 0) {
      paragraph_num = atoi(text);
      if (paragraph_num >= 0) {
        /*check command*/
        result = add_paragraph_after(doc, paragraph_num);
        if (result == FAILURE) {
          printf("%s failed\n", command);
        }
      } else {
        fprintf(stdout, "Invalid Command\n");
      }
    } else {
      fprintf(stdout, "Invalid Command\n");
    }
  } else {
    fprintf(stdout, "Invalid Command\n");
  }
}

static void add_line_helper(Document *doc, char *line, char *command) {
  int digit_line = 0, digit_paragraph = 0, i = 0, paragraph_num = 0,
      line_num = 0, pos;
  int result = 0;
  char paragraph_input[MAX_CHAR_LENGTH + 1] = {'\0'};
  char line_input[MAX_CHAR_LENGTH + 1] = {'\0'};
  char star_check[] = "*";
  char *line_ptr, *ptr;
  sscanf(line, "%s %s %s %n", command, paragraph_input, line_input, &pos);
  if (paragraph_input[0] != '\0' && line_input[0] != '\0') {

    paragraph_input[strlen(paragraph_input)] = '\0';
    line_input[strlen(line_input)] = '\0';
    line_ptr = line + pos;
    /*check line_num is digit*/
    while (line_input[i] != '\0') {
      digit_line = isdigit(line_input[i]);
      i++;
    }
    i = 0;
    /*check paragraph_num is digit*/
    while (paragraph_input[i] != '\0') {
      digit_paragraph = isdigit(paragraph_input[i]);
      i++;
    }
    /*check line_num and paragraph_num is missing and <0*/
    if (digit_line != 0 && digit_paragraph != 0) {
      paragraph_num = atoi(paragraph_input);
      line_num = atoi(line_input);
      if (paragraph_num >= 0 && line_num >= 0) {
        /*check * is missing*/
        ptr = strstr(line_ptr, star_check);
        if (ptr) {
          /*check command*/
          result = add_line_after(doc, paragraph_num, line_num, ptr + 1);
          if (result == FAILURE) {
            printf("%s failed\n", command);
          }
        } else {
          fprintf(stdout, "Invalid Command\n");
        }
      } else {
        fprintf(stdout, "Invalid Command\n");
      }
    } else {
      fprintf(stdout, "Invalid Command\n");
    }
  } else {
    fprintf(stdout, "Invalid Command\n");
  }
}

static void append_helper(Document *doc, char *line, char *command) {
  int digit_paragraph = 0, i = 0, paragraph_num = 0, pos = 0;
  int result = 0;
  char paragraph_input[MAX_CHAR_LENGTH + 1] = {'\0'};
  char star_check[] = "*";
  char *line_ptr, *ptr;

  sscanf(line, "%s %s%n", command, paragraph_input, &pos);
  /*chekc paragraph_num is missing*/
  if (paragraph_input[0] != '\0') {
    line_ptr = line + pos;
    /*check paragraph_num is digit*/
    while (paragraph_input[i] != '\0') {
      digit_paragraph = isdigit(paragraph_input[i]);
      i++;
    }
    if (digit_paragraph != 0) {
      paragraph_num = atoi(paragraph_input);
      /*check paragraph_num <0*/
      if (paragraph_num > 0) {
        /*check * is missing*/
        ptr = strstr(line_ptr, star_check);
        if (ptr) {
          /*check command*/
          result = append_line(doc, paragraph_num, ptr + 1);
          if (result == FAILURE) {
            printf("%s failed\n", command);
          }
        } else {
          fprintf(stdout, "Invalid Command\n");
        }
      } else {
        fprintf(stdout, "Invalid Command\n");
      }
    } else {
      fprintf(stdout, "Invalid Command\n");
    }
  } else {
    fprintf(stdout, "Invalid Command\n");
  }
}

static void remove_line_helper(Document *doc, char *line, char *command) {
  int i = 0, digit_line = 0, digit_paragraph = 0, paragraph_num = 0,
      line_num = 0;
  int result = 0;
  char line_input[MAX_CHAR_LENGTH + 1] = {'\0'};
  char paragraph_input[MAX_CHAR_LENGTH + 1] = {'\0'};
  char extra_input[MAX_CHAR_LENGTH + 1] = {'\0'};
  sscanf(line, "%s %s %s %s", command, paragraph_input, line_input,
         extra_input);
  /*check paragraph_num and line_num is missing*/
  if (line_input[0] != '\0' && paragraph_input[0] != '\0' &&
      extra_input[0] == '\0') {
    /*check line_num is missing*/
    while (line_input[i] != '\0') {
      digit_line = isdigit(line_input[i]);
      i++;
    }
    i = 0;
    /*check paragraph_num is missing*/
    while (paragraph_input[i] != '\0') {
      digit_paragraph = isdigit(paragraph_input[i]);
      i++;
    }
    if (digit_line != 0 && digit_paragraph != 0) {
      paragraph_num = atoi(paragraph_input);
      line_num = atoi(line_input);
      /*check paragraph_num and line_num <0*/
      if (paragraph_num > 0 && line_num > 0) {
        /*check command*/
        result = remove_line(doc, paragraph_num, line_num);
        if (result == FAILURE) {
          printf("%s failed\n", command);
        }
      } else {
        fprintf(stdout, "Invalid Command\n");
      }
    } else {
      fprintf(stdout, "Invalid Command\n");
    }
  } else {
    fprintf(stdout, "Invalid Command\n");
  }
}

static void replace_helper(Document *doc, char *line, char *command,
                           char *text) {
  int result = 0, i = 0, j = 0;
  char target[MAX_CHAR_LENGTH + 1] = {'\0'};
  char replacement[MAX_CHAR_LENGTH + 1] = {'\0'};
  char quote = '\"';
  char *quote_found, *end_quote_found;
  if (text != NULL) {
    /*check target exsit*/
    quote_found = strchr(line, quote);
    if (quote_found) {
      quote_found += 1;
      /*locate target starting index*/
      while (*quote_found != quote && *quote_found != '\0') {
        target[i] = *quote_found;
        quote_found++;
        i++;
      }
      /*locate target ending index*/
      if (*quote_found != '\0' || target[0] != '\0') {
        target[i] = '\0';
        quote_found += 1;
        end_quote_found = strchr(quote_found, quote);
        if (end_quote_found) {
          end_quote_found += 1;
          /*check replacement exist*/
          while (*end_quote_found != quote && *end_quote_found != '\0') {
            replacement[j] = *end_quote_found;
            end_quote_found++;
            j++;
          }
          if (*end_quote_found != '\0') {
            replacement[j] = '\0';
            /*check command*/
            result = replace_text(doc, target, replacement);
            if (result == FAILURE) {
              printf("%s failed\n", command);
            }
          } else {
            fprintf(stdout, "Invalid Command\n");
          }
        } else {
          fprintf(stdout, "Invalid Command\n");
        }
      } else {
        fprintf(stdout, "Invalid Command\n");
      }
    } else {
      fprintf(stdout, "Invalid Command\n");
    }
  } else {
    fprintf(stdout, "Invalid Command\n");
  }
}

static void highlight_helper(Document *doc, char *line, char *command,
                             char *text) {
  int i = 0;
  char target[MAX_CHAR_LENGTH + 1] = {'\0'};
  char quote = '\"';
  char *quote_found;
  /*check target exist*/
  if (text != NULL) {
    quote_found = strchr(line, quote);
    if (quote_found) {
      quote_found += 1;
      while (*quote_found != quote && *quote_found != '\0') {
        target[i] = *quote_found;
        quote_found++;
        i++;
      }
      /*check command*/
      if (*quote_found != '\0' && target[0] != '\0') {
        target[i] = '\0';
        highlight_text(doc, target);
      } else {
        fprintf(stdout, "Invalid Command\n");
      }
    } else {
      fprintf(stdout, "Invalid Command\n");
    }
  } else {
    fprintf(stdout, "Invalid Command\n");
  }
}

static void remove_text_helper(Document *doc, char *line, char *command,
                               char *text) {
  int i = 0;
  char target[MAX_CHAR_LENGTH + 1] = {'\0'};
  char quote = '\"';
  char *quote_found;
  /*check target exist*/
  if (text != NULL) {
    quote_found = strchr(line, quote);
    if (quote_found) {
      quote_found += 1;
      /*locate target*/
      while (*quote_found != quote && *quote_found != '\0') {
        target[i] = *quote_found;
        quote_found++;
        i++;
      }
      /*check command*/
      if (*quote_found != '\0' && target[0] != '\0') {
        target[i] = '\0';
        remove_text(doc, target);
      } else {
        fprintf(stdout, "Invalid Command\n");
      }
    } else {
      fprintf(stdout, "Invalid Command\n");
    }
  } else {
    fprintf(stdout, "Invalid Command\n");
  }
}

static void load_helper(Document *doc, char *line, char *command, char *text) {
  int result = 0;
  char filename[MAX_CHAR_LENGTH + 1] = {'\0'};
  text[0] = '\0';
  sscanf(line, "%s %s %s", command, filename, text);
  /*check filename is missing and following date after filename*/
  if (filename[0] != '\0' && text[0] == '\0') {
    result = load_file(doc, filename);
    /*check command*/
    if (result == FAILURE) {
      printf("%s failed\n", command);
    }
  } else {
    fprintf(stdout, "Invalid Command\n");
  }
}

static void save_helper(Document *doc, char *line, char *command, char *text) {
  int result = 0;
  char filename[MAX_CHAR_LENGTH + 1] = {'\0'};
  text[0] = '\0';
  sscanf(line, "%s %s %s", command, filename, text);
  /*check filename is missing and following date after filename*/
  if (filename[0] != '\0' && text[0] == '\0') {
    result = save_document(doc, filename);
    /*check command*/
    if (result == FAILURE) {
      printf("%s failed\n", command);
    }
  } else {
    fprintf(stdout, "Invalid Command\n");
  }
}
