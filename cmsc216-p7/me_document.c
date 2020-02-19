#include "document.h"
#include <ctype.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sysexits.h>

#define MAX_CHAR_LENGTH 1024

static void replace_helper(Document *doc, char *line, char *command,
                           char *text_input);
static void highlight_helper(Document *doc, char *line, char *command,
                             char *text_input);
static void remove_helper(Document *doc, char *line, char *command,
                          char *text_input);
static void save_helper(Document *doc, char *line, char *command,
                        char *text_input);
int main(int argc, char *argv[]) {
  FILE *fp;
  char *ptr;
  const char *name = "main_document";
  Document doc;
  char temp[MAX_CHAR_SIZE + 1] = {'\0'};
  char line[MAX_CHAR_SIZE + 1] = {'\0'};
  char file_name[MAX_CHAR_SIZE + 1] = {'\0'};
  char commands[MAX_CHAR_SIZE + 1] = {'\0'};
  char text_input[MAX_CHAR_SIZE + 1] = {'\0'};
  int paragraph_num = 0;
  int line_num = 0;
  int digit_paragraph_check = 0;
  int digit_line_check = 0;
  int i = 0;
  char *star_pos;

  if (argc == 1) {
    fp = stdin;
  } else if (argc == 2) {
    if ((fp = fopen(argv[1], "r")) == NULL) {
      fprintf(stderr, "%s cannot be opened\n", argv[1]);
      exit(EX_OSERR);
    }
  } else {
    fprintf(stderr, "Usage: user_interface\n");
    fprintf(stderr, "Usage: user_interface <filename>\n");
    exit(EX_USAGE);
  }

  init_document(&doc, name);
  fgets(line, MAX_CHAR_SIZE + 1, fp);
  while (!feof(fp)) {
    if (argc == 1) {
      printf(">");
    }
    sscanf(line, "%s %s", command, text_input);
    if (line[0] == '#' || line[0] == '\n') {
      if (argc == 1) {
        fp = stdin;
      }
    }
    /*add_paragraph_after check*/
    if (strcmp(command, "add_paragraph_after") == 0) {
      sscanf(line, "%s %d %s", command, &paragraph_num, text_input);
      digit_paragraph_check = isdigit(paragraph_num);
      if (paragraph_num < 0 || strcmp(text_input, "") == 0 ||
          digit_paragraph_check == 0) {
        printf("Invalid Command\n");
      } else {
        if (add_paragraph_after(&doc, paragraph_num) == FAILURE) {
          printf("%s failed\n", command);
        }
      }
    }
    /*add_line_after*/
    if (strcmp(command, "add_line_after") == 0) {
      star_pos = strstr(line, "*");
      strcpy(temp, star_pos + 1);
      sscanf(line, "%s %d %d", command, &paragraph_num, &line_num);
      digit_paragraph_check = isdigit(paragraph_num);
      digit_line_check = isdigit(line_num);
      if (paragraph_num < 0 || line_num < 0 || digit_line_check == 0 ||
          digit_paragraph_check == 0 || strcmp(star_pos, "*") == 0) {
        printf("Invalid Command\n");
      } else {
        if (add_line_after(&doc, paragraph_number, line_number, temp) ==
            FAILURE) {
          printf("%s failed\n", command);
        }
      }
    }
    /*print_document*/
    if (strcmp(command, "print_document") == 0) {
      if (strcmp(text_input, "") == 0) {
        printf("Invalid Command\n");
      } else {
        print_document(&main_document);
      }
    }
    /*quit*/
    if (strcmp(command, "quit") == 0) {
      if (strcmp(text_input, "") == 0) {
        printf("Invalid Command\n");
      } else {
        exit(EXIT_SUCCESS);
      }
    }
    /*exit*/
    if (strcmp(command, "exit") == 0) {
      if (strcmp(text_input, "") == 0) {
        printf("Invalid Command\n");
      } else {
        exit(EXIT_SUCCESS);
      }
    }

    /*append_line*/
    if (strcmp(command, "append_line") == 0) {
      star_pos = strstr(line, "*");
      strcpy(temp, star_pos + 1);
      sscanf(line, "%s %d", command, &paragraph_num);
      digit_paragraph_check = isdigit(paragraph_num);
      if (paragraph_num < 0 || digit_paragraph_check == 0 ||
          strncmp(tracker, "*", 0) == 0) {
        printf("Invalid Command\n");
      } else {
        if (append_line(&doc, paragraph_num, temp) == FAILURE) {
          printf("%s failed\n", command);
        }
      }
      strcpy(temp, "");
    }
    /*remove_line*/
    if (strcmp(command, "remove_line") == 0) {
      sscanf(line, "%s %d %d %s", command, &paragraph_num, &line_num,
             text_input);
      digit_paragraph_check = isdigit(paragraph_num);
      digit_line_check = isdigit(line_num);
      if (paragraph_num <= 0 || line_number <= 0 ||
          digit_paragraph_check == 0 || digit_line_check == 0 ||
          strcmp(text_input, "") == 0) {
        printf("Invalid Command\n");
      } else {
        if (remove_line(&doc, paragraph_num, line_num) == FAILURE) {
          printf("%s failed\n", command);
        }
      }
    }
    /*load_file*/
    if (strcmp(command, "load_file") == 0) {
      sscanf(line, "%s %s %s", command, file_name, text_input);
      if (strcmp(file_name, "\0") == 0 || strcmp(text_input, "\0") != 0) {
        printf("Invalid Command\n");
      } else {
        if (load_file(&doc, file_name) == FAILURE) {
          printf("%s failed\n", command);
        }
      }
    }
    /*replace_text*/
    if (strcmp(command, "replace_text") == 0) {
      replace_helper(&name, line, command, text_input);
    }
    /*highlight_text*/
    if (strcmp(command, "highlight_text") == 0) {
      highlight_helper(&main_document, line, command, text_input);
    }

    if (strcmp(command, "remove_text") == 0) {
      remove_helper(&main_document, line, command, text_input);
    }
    if (strcmp(command, "save_document") == 0) {
      save_helper(&main_document, line, command, text_input);
    }
    if (strcmp(command, "reset_document") == 0) {
      reset_document(&main_document);
      if (command[0] != '\0') {
        printf("Invalid command\n");
      }
    }
    command[0] = '\0';
    text_input[0] = '\0';
  }
  fclose(input);
}

static void replace_helper(Document *doc, char *line, char *command,
                           char *text_input) {
  int result = 0;
  int i = 0, j = 0;
  char target[MAX_CHAR_LENGTH + 1] = {'\0'};
  char replacement[MAX_CHAR_LENGTH + 1] = {'\0'};
  char quote = '\"';
  char *quote, *next_quote;
  if (text_input != NULL) {
    quote = strchr(line, quote);
    if (quote) {
      quote += 1;
      while (*quote != quote && *quote != '\0') {
        target[i] = *quote;
        quote++;
        i++;
      }
      if (*quote != '\0' || target[0] != '\0') {
        target[i] = '\0';
        quote += 1;
        next_quote = strchr(quote, quote);
        if (next_quote) {
          next_quote += 1;
          while (*next_quote != quote && *next_quote != '\0') {
            replacement[j] = *next_quote;
            next_quote++;
            j++;
          }
          if (*next_quote != '\0') {
            replacement[j] = '\0';
            result = replace_text(doc, target, replacement);
            if (result == FAILURE) {
              printf("%s failed\n", command);
            }
          } else {
            printf("Invalid Command\n");
          }
        } else {
          printf("Invalid Command\n");
        }
      } else {
        printf("Invalid Command\n");
      }
    } else {
      printf("Invalid Command\n");
    }
  } else {
    printf("Invalid Command\n");
  }
}

static void highlight_helper(Document *doc, char *line, char *command,
                             char *text_input) {
  int i = 0;
  char target[MAX_CHAR_LENGTH + 1] = {'\0'}, quote = '\"';
  char *quote;
  if (text_input != NULL) {
    quote = strchr(line, quote);
    if (quote) {
      quote += 1;
      while (*quote != quote && *quote != '\0') {
        target[i] = *quote;
        quote++;
        i++;
      }
      if (*quote != '\0' && target[0] != '\0') {
        target[i] = '\0';
        highlight_text(doc, target);
      } else {
        printf("Invalid Command\n");
      }
    } else {
      printf("Invalid Command\n");
    }
  } else {
    printf("Invalid Command\n");
  }
}

static void remove_helper(Document *doc, char *line, char *command,
                          char *text_input) {
  int i = 0;
  char target[MAX_CHAR_LENGTH + 1] = {'\0'}, quote = '\"', *quote_found;
  if (text_input != NULL) {
    quote_found = strchr(line, quote);
    if (quote_found) {
      quote_found += 1;
      while (*quote_found != quote && *quote_found != '\0') {
        target[i] = *quote_found;
        quote_found++;
        i++;
      }
      if (*quote_found != '\0' && target[0] != '\0') {
        target[i] = '\0';
        remove_text(doc, target);
      } else {
        printf("Invalid Command\n");
      }
    } else {
      printf("Invalid Command\n");
    }
  } else {
    printf("Invalid Command\n");
  }
}

static void save_helper(Document *doc, char *line, char *command,
                        char *text_input) {
  int result = 0;
  char filename[MAX_CHAR_LENGTH + 1] = {'\0'};
  text_input[0] = '\0';
  sscanf(line, "%s %s %s", command, filename, text_input);
  if (filename[0] != '\0' && text_input[0] == '\0') {
    result = save_document(doc, filename);
    if (result == FAILURE) {
      printf("%s failed\n", command);
    }
  } else {
    printf("Invalid Command\n");
  }
}
