#include "document.h"
#include <ctype.h>
#include <stdio.h>
#include <string.h>

int init_document(Document *doc, const char *name) {
  /*check invilid cases*/
  if (doc == NULL || name == NULL || strlen(name) > MAX_STR_SIZE) {
    return FAILURE;
  }
  /*initial paragraph to 0*/
  doc->number_of_paragraphs = 0;
  /*copy the name file*/
  strcpy(doc->name, name);
  return SUCCESS;
}

int reset_document(Document *doc) {
  /*check invilid cases*/
  if (doc == NULL) {
    return FAILURE;
  }
  /*initial paragraph to 0*/
  doc->number_of_paragraphs = 0;
  return SUCCESS;
}

int print_document(Document *doc) {

  int i = 0, j = 0;
  /*check invilid cases*/
  if (doc == NULL) {
    return FAILURE;
  }
  /*print name and number of paragraph*/
  printf("Document name: \"%s\"\n", doc->name);
  printf("Number of Paragraphs: %d\n", doc->number_of_paragraphs);
  /*print line*/
  if (doc->number_of_paragraphs != 0) {
    for (i = 0; i < doc->number_of_paragraphs; i++) {
      if (doc->paragraphs[i].number_of_lines != 0) {
        for (j = 0; j < doc->paragraphs[i].number_of_lines; j++) {
          /*check if the line is empty*/
          printf("%s\n", doc->paragraphs[i].lines[j]);
        }
        /*put empty line if the number paragraph is greater than it should be*/
        if (i < doc->number_of_paragraphs - 1) {
          printf("\n");
        }
      }
    }
  }
  return SUCCESS;
}
int add_paragraph_after(Document *doc, int paragraph_number) {
  int i = 0;
  if (doc == NULL || doc->number_of_paragraphs >= MAX_PARAGRAPHS ||
      paragraph_number > doc->number_of_paragraphs) {
    return FAILURE;
  } else {
    /* shifs paragraphs down when needed */
    if (paragraph_number < doc->number_of_paragraphs) {
      for (i = doc->number_of_paragraphs; i > paragraph_number; i--) {
        doc->paragraphs[i] = doc->paragraphs[i - 1];
      }
    }
    doc->paragraphs[paragraph_number].number_of_lines = 0;
    doc->number_of_paragraphs++;
    return SUCCESS;
  }
}
int add_line_after(Document *doc, int paragraph_number, int line_number,
                   const char *new_line) {
  int i = 0;
  int line = 0;
  /*check invilid cases*/
  if (doc == NULL || paragraph_number > doc->number_of_paragraphs ||
      line_number > doc->paragraphs[paragraph_number - 1].number_of_lines ||
      new_line == NULL || line_number >= MAX_PARAGRAPH_LINES) {
    return FAILURE;
  }
  /*get totally line in that paragraph*/
  get_number_lines_paragraph(doc, paragraph_number, &line);
  /*copy new_line to a specific paragraph with line_number*/
  strcpy(doc->paragraphs[paragraph_number - 1].lines[line_number], new_line);
  /*loop through and put an empty line after*/
  for (i = line_number; i < line; i++) {
    strcpy(doc->paragraphs[paragraph_number - 1].lines[i - 1],
           doc->paragraphs[paragraph_number - 1].lines[i]);
  }
  /*line +1*/
  doc->paragraphs[paragraph_number - 1].number_of_lines++;
  return SUCCESS;
}
int get_number_lines_paragraph(Document *doc, int paragraph_number,
                               int *number_of_lines) {
  /*check invilid cases*/
  if (doc == NULL || number_of_lines == NULL ||
      paragraph_number > doc->number_of_paragraphs) {
    return FAILURE;
  }
  /*get line number with paragraph_number*/
  *number_of_lines = doc->paragraphs[paragraph_number - 1].number_of_lines;
  return SUCCESS;
}
int append_line(Document *doc, int paragraph_number, const char *new_line) {
  int num_line = 0;
  /*get number of line in paragraph_number*/
  get_number_lines_paragraph(doc, paragraph_number, &num_line);
  /*check invilid cases*/
  if (doc == NULL || paragraph_number > doc->number_of_paragraphs ||
      doc->paragraphs[paragraph_number].number_of_lines ==
          MAX_PARAGRAPH_LINES ||
      new_line == NULL) {
    return FAILURE;
  }
  /*copy new line after num_line*/
  strcpy(doc->paragraphs[paragraph_number - 1].lines[num_line], new_line);
  /*line +1*/
  doc->paragraphs[paragraph_number - 1].number_of_lines++;
  return SUCCESS;
}
int remove_line(Document *doc, int paragraph_number, int line_number) {
  int i = 0;
  int line = 0;
  /*get number of line in paragraph_number*/
  get_number_lines_paragraph(doc, paragraph_number, &line);
  /*check invilid cases*/
  if (doc == NULL || paragraph_number > doc->number_of_paragraphs ||
      line_number > line) {
    return FAILURE;
  }
  /*copy the line after that in original position*/
  for (i = line_number - 1; i < line; i++) {
    strcpy(doc->paragraphs[paragraph_number - 1].lines[i],
           doc->paragraphs[paragraph_number - 1].lines[i + 1]);
  }
  /*line -1*/
  doc->paragraphs[paragraph_number - 1].number_of_lines--;
  return SUCCESS;
}
int load_document(Document *doc, char data[][MAX_STR_SIZE + 1],
                  int data_lines) {
  int i = 0;
  int num_paragrahs = 0;
  /*check invilid cases*/
  if (doc == NULL || data == NULL || data_lines == 0) {
    return FAILURE;
  }
  /*set an empty paragraph after num_paragrahs*/
  add_paragraph_after(doc, num_paragrahs);
  for (i = 0; i < data_lines; i++) {
    /*check if original data[] is empty*/
    if (strcmp(data[i], "") != 0) {
      /*if string is not empty then append a line*/
      append_line(doc, num_paragrahs + 1, data[i]);
    } else {
      /*or, make another paragraph*/
      num_paragrahs++;
      add_paragraph_after(doc, num_paragrahs);
    }
  }
  return SUCCESS;
}
int replace_text(Document *doc, const char *target, const char *replacement) {
  int i = 0, j = 0;
  char *ret = NULL;
  char new_string[MAX_STR_SIZE + 1];
  /*check invilid cases*/
  if (doc == NULL || target == NULL || replacement == NULL) {
    return FAILURE;
  }

  for (i = 0; i < doc->number_of_paragraphs; i++) {
    for (j = 0; j < doc->paragraphs[i].number_of_lines; j++) {
      ret = strstr(doc->paragraphs[i].lines[j], target);
      while (ret != NULL) {
        if (ret != NULL) {
          strcpy(new_string, ret + strlen(target));
          *ret = '\0';
          strcat(doc->paragraphs[i].lines[j], replacement);
          strcat(doc->paragraphs[i].lines[j], new_string);
        }
        ret = strstr(ret + strlen(replacement), target);
      }
    }
  }
  return SUCCESS;
}

int highlight_text(Document *doc, const char *target) {
  char temp_string[MAX_STR_SIZE + 1];

  if (doc == NULL || target == NULL) {
    return FAILURE;
  }
  strcpy(temp_string, HIGHLIGHT_START_STR);
  strcat(temp_string, target);
  strcat(temp_string, HIGHLIGHT_END_STR);
  temp_string[strlen(target) + 2] = '\0';
  replace_text(doc, target, temp_string);

  return SUCCESS;
}
int remove_text(Document *doc, const char *target) {

  int i = 0, j = 0;
  char *ret;
  char new_string[MAX_STR_SIZE + 1];
  /*check invilid cases*/

  if (doc == NULL || target == NULL) {
    return FAILURE;
  }
  /*find target and replace with empty string character*/
  /*
    for (i = 0; i < doc->number_of_paragraphs; i++) {
      for (j = 0; j < doc->paragraphs[i].number_of_lines; j++) {
        ret = strstr(doc->paragraphs[i].lines[j], target);
        if (ret != NULL) {
          strcpy(new_string, ret + strlen(target));
          *ret = '\0';
          strcat(doc->paragraphs[i].lines[j], new_string);
        }
      }
    }
  */
  replace(doc, target, "");
  return SUCCESS;
}
int load_file(Document *doc, const char *filename) {
  FILE *fp;
  int i;
  int number_of_paragraphs = 0;
  char new_string[MAX_STR_SIZE + 1];
  int paragraph_check = 1;
  if (doc == NULL || filename == NULL) {
    return FAILURE;
  }
  fp = fopen(filename, "r");
  if (fp == NULL) {
    return FAILURE;
  }
  add_paragraph_after(doc, number_of_paragraphs);
  while (fgets(new_string, MAX_STR_SIZE + 1, fp) != NULL) {
    new_string[strlen(new_string) - 1] = '\0';
    for (i = 0; new_string[i] != '\0'; i++) {
      if (isspace(new_string[i]) == 0) {
        paragraph_check = 0;
        append_line(doc, number_of_paragraphs + 1, new_string);
        break;
      }
    }
    if (paragraph_check == 1) {
      number_of_paragraphs++;
      add_paragraph_after(doc, number_of_paragraphs);
    }
    paragraph_check = 1;
  }
  fclose(fp);
  return SUCCESS;
}
int save_document(Document *doc, const char *filename) {
  int i;
  int j;
  FILE *fp;
  fp = fopen(filename, "w");
  if (doc == NULL || filename == NULL || fp == NULL) {
    return FAILURE;
  }
  for (i = 0; i < doc->number_of_paragraphs; i++) {
    for (j = 0; j < doc->paragraphs[i].number_of_lines; j++) {

      fputs(doc->paragraphs[i].lines[j], fp);

      fputs("\n", fp);
    }
    fputs("\n", fp);
  }
  fclose(fp);
  return SUCCESS;
}
