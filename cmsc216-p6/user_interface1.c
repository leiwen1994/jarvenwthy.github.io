#include "document.h"
#include <ctype.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sysexits.h>
#define EOF(-1)
#define MAX_CHAR_SIZE 1024

int main(int argc, char *argv[]) {
  FILE *fp;
  char *ptr;
  const char *name = "main_document";
  Document doc;
  char line[MAX_CHAR_SIZE + 1] = {'\0'};
  char commands[MAX_CHAR_SIZE + 1] = {'\0'};
  char text_input[MAX_CHAR_SIZE + 1] = {'\0'};
  char line_add[MAX_CHAR_SIZE + 1] = {'\0'};
  char remaining_input[MAX_CHAR_SIZE + 1] = {'\0'};
  int paragraph_number = 0;
  int line_number = 0;
  int vaild_input = 0;

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
    vaild_input = sscanf(line, "%s %s", command, text_input);
    if (line[0] == '#' || line[0] == '\n' || vaild_input == EOF) {
      if (argc == 1) {
        fp = stdin;
      }
    }
    /*add_paragraph_after*/
    if (strcmp(command_input, "add_paragraph_after") == 0) {
      is_add_paragraph_after(&name, line, command, remaining_input, value_read);
    }
    if (strcmp(command_input, "add_line_after") == 0) {
      is_add_line_after(&main_document, line, command_input, remaining_input,
                        value_read);
    }
    if (strcmp(command_input, "print_document") == 0) {
      if (remaining_input[0] != '\0') {
        printf("Invalid Command\n");
      } else {
        print_document(&main_document);
      }
    }
    if (strcmp(command_input, "append_line") == 0) {
      is_append_line(&main_document, line, command_input, remaining_input,
                     value_read);
    }
    if (strcmp(command_input, "remove_line") == 0) {
      is_remove_line(&main_document, line, command_input, remaining_input,
                     value_read);
    }
    if (strcmp(command_input, "replace_text") == 0) {
      is_replace_text(&main_document, line, command_input, remaining_input);
    }
    if (strcmp(command_input, "load_file") == 0) {
      is_load_file(&main_document, line, command_input, remaining_input);
    }
    if (strcmp(command_input, "highlight_text") == 0) {
      is_highlight_text(&main_document, line, command_input, remaining_input);
    }
    if (strcmp(command_input, "remove_text") == 0) {
      is_remove_text(&main_document, line, command_input, remaining_input);
    }
    if (strcmp(command_input, "save_document") == 0) {
      is_save_document(&main_document, line, command_input, remaining_input);
    }
    if (strcmp(command_input, "reset_document") == 0) {
      reset_document(&main_document);
      if (command_input[0] != '\0') {
        printf("Invalid Command\n");
      }
    }
    if (strcmp(command_input, "quit") == 0) {
      if (remaining_input[0] != '\0') {
        printf("Invalid Command\n");
      } else {

        exit(EXIT_SUCCESS);
      }
    }
    if (strcmp(command_input, "exit") == 0) {
      if (remaining_input[0] != '\0') {
        printf("Invalid Command\n");
      } else {
        exit(EXIT_SUCCESS);
      }
    }

    command_input[0] = '\0';
    remaining_input[0] = '\0';
  }

  fclose(input);
}

static void is_add_paragraph_after(Document *doc, char *line, char *command,
                                   char *remaining) {
  int digit = 0, paragraph_num = 0, result = 0;
  int i;
  char extra[MAX_CHAR_SIZE + 1] = {'\0'};
  remaining[0] = '\0';
  sscanf(line, "%s %s %s", command, remaining, extra);
  if (remaining[0] != '\0' && extra[0] == '\0') {
    while (remaining[i] != '\0') {
      digit = isdigit(remaining[i]);
      i++;
    }
    paragraph_num = atoi(remaining);
    if (digit == 0 || paragraph_num < 0) {
      printf("Invalid Command\n");
    } else {
    }
    if (digit != 0) {

      if (paragraph_num >= 0) {
        result = add_paragraph_after(doc, paragraph_num);
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
}

/* NEED FIXINGGG */
static void is_add_line_after(Document *doc, char *line, char *command_input,
                              char *remaining_input, int value_read) {
  int int_line = 0, int_paragraph = 0, i = 0, paragraph_num = 0, line_num = 0,
      pos;
  char line_content[MAX_CHAR_LENGTH + 1],
      string_paragraph[MAX_CHAR_LENGTH + 1] = {'\0'},
                                         string_line[MAX_CHAR_LENGTH + 1] =
                                             {'\0'},
                                         asterisk[] = "*";
  char *line_ptr, *ptr;
  value_read = sscanf(line, "%s %s %s%n", command_input, string_paragraph,
                      string_line, &pos);

  if (string_paragraph[0] != '\0' && string_line[0] != '\0') {

    string_paragraph[strlen(string_paragraph)] = '\0';
    string_line[strlen(string_line)] = '\0';
    line_ptr = line + pos;
    /* checks if string is a number */
    while (string_line[i] != '\0') {
      int_line = isdigit(string_line[i]);
      i++;
    }
    i = 0;
    /* checks if string is a number */
    while (string_paragraph[i] != '\0') {
      int_paragraph = isdigit(string_paragraph[i]);
      i++;
    }
    if (int_line != 0 && int_paragraph != 0) {
      paragraph_num = atoi(string_paragraph);
      line_num = atoi(string_line);
      if (paragraph_num >= 0 &&
          line_num >= 0) { /* makes sure they're not negative*/
        ptr = strstr(line_ptr,
                     asterisk); /* checks that the string contains '*' */
        if (ptr) {
          int result = add_line_after(doc, paragraph_num, line_num, ptr + 1);
          if (result == FAILURE) {
            printf("%s failed\n", command_input);
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
/* NEED FIXINGGG */
static void is_append_line(Document *doc, char *line, char *command_input,
                           char *remaining_input, int value_read) {
  int int_paragraph = 0, i = 0, paragraph_num = 0, pos = 0;
  char string_paragraph[MAX_CHAR_LENGTH + 1] = {'\0'}, asterisk[] = "*";
  char *line_ptr, *ptr;

  value_read = sscanf(line, "%s %s%n", command_input, string_paragraph, &pos);

  if (string_paragraph[0] != '\0') {

    line_ptr = line + pos;
    /* checks if string is a number */
    while (string_paragraph[i] != '\0') {
      int_paragraph = isdigit(string_paragraph[i]);
      i++;
    }
    if (int_paragraph != 0) {
      paragraph_num = atoi(string_paragraph);

      if (paragraph_num > 0) { /* makes sure they're not negative*/
        ptr = strstr(line_ptr,
                     asterisk); /* checks that the string contains '*' */
        if (ptr) {
          int result = append_line(doc, paragraph_num, ptr + 1);
          if (result == FAILURE) {
            printf("%s failed\n", command_input);
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

static void is_remove_line(Document *doc, char *line, char *command_input,
                           char *remaining_input, int value_read) {
  int i = 0, int_line = 0, int_paragraph = 0, paragraph_num = 0, line_num = 0;
  char string_line[MAX_CHAR_LENGTH + 1] = {'\0'},
                                     string_paragraph[MAX_CHAR_LENGTH + 1] =
                                         {'\0'},
                                     extra_input[MAX_CHAR_LENGTH + 1] = {'\0'};
  value_read = sscanf(line, "%s %s %s %s", command_input, string_paragraph,
                      string_line, extra_input);
  if (string_line[0] != '\0' && string_paragraph[0] != '\0' &&
      extra_input[0] == '\0') {

    while (string_line[i] != '\0') {
      int_line = isdigit(string_line[i]);
      i++;
    }
    i = 0;
    /* checks if string is a number */
    while (string_paragraph[i] != '\0') {
      int_paragraph = isdigit(string_paragraph[i]);
      i++;
    }
    if (int_line != 0 && int_paragraph != 0) {
      paragraph_num = atoi(string_paragraph);
      line_num = atoi(string_line);
      if (paragraph_num >= 0 &&
          line_num >= 0) { /* makes sure they're not negative*/
        int result = remove_line(doc, paragraph_num, line_num);
        if (result == FAILURE) {
          printf("%s failed\n", command_input);
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

static void is_replace_text(Document *doc, char *line, char *command_input,
                            char *remaining_input) {
  int result = 0, i = 0, j = 0;
  char target[MAX_CHAR_LENGTH + 1] = {'\0'},
                                replacement[MAX_CHAR_LENGTH + 1] = {'\0'},
                                quote = '\"';
  char *quote_found, *next_quote_found;
  /*  value_read = sscanf(line, "%s", command_input); */
  if (remaining_input != NULL) {
    quote_found = strchr(line, quote);
    if (quote_found) {
      quote_found += 1;
      while (*quote_found != quote && *quote_found != '\0') {
        target[i] = *quote_found;
        quote_found++;
        i++;
      }
      if (*quote_found != '\0' || target[0] != '\0') {
        target[i] = '\0';
        quote_found += 1;
        next_quote_found = strchr(quote_found, quote);
        if (next_quote_found) {
          next_quote_found += 1;
          while (*next_quote_found != quote && *next_quote_found != '\0') {
            replacement[j] = *next_quote_found;
            next_quote_found++;
            j++;
          }
          if (*next_quote_found != '\0') {
            replacement[j] = '\0';
            result = replace_text(doc, target, replacement);
            if (result == FAILURE) {
              printf("%s failed\n", command_input);
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

static void is_highlight_text(Document *doc, char *line, char *command_input,
                              char *remaining_input) {

  int i = 0;
  char target[MAX_CHAR_LENGTH + 1] = {'\0'}, quote = '\"';
  char *quote_found;
  /*  value_read = sscanf(line, "%s", command_input); */
  if (remaining_input != NULL) {
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
static void is_remove_text(Document *doc, char *line, char *command_input,
                           char *remaining_input) {
  int i = 0;
  char target[MAX_CHAR_LENGTH + 1] = {'\0'}, quote = '\"', *quote_found;
  /*  value_read = sscanf(line, "%s", command_input); */
  if (remaining_input != NULL) {
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

static void is_load_file(Document *doc, char *line, char *command_input,
                         char *remaining_input) {
  int result = 0;
  char filename[MAX_CHAR_LENGTH + 1] = {'\0'};
  remaining_input[0] = '\0';
  sscanf(line, "%s %s %s", command_input, filename, remaining_input);
  if (filename[0] != '\0' && remaining_input[0] == '\0') {
    result = load_file(doc, filename);
    if (result == FAILURE) {
      printf("%s failed\n", command_input);
    }
  } else {
    printf("Invalid Command\n");
  }
}

static void is_save_document(Document *doc, char *line, char *command_input,
                             char *remaining_input) {
  int result = 0;
  char filename[MAX_CHAR_LENGTH + 1] = {'\0'};

  remaining_input[0] = '\0';
  sscanf(line, "%s %s %s", command_input, filename, remaining_input);
  if (filename[0] != '\0' && remaining_input[0] == '\0') {
    result = save_document(doc, filename);
    if (result == FAILURE) {
      printf("%s failed\n", command_input);
    }
  } else {
    printf("Invalid Command\n");
  }
}
