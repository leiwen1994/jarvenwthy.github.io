/*name: jiawen lei
  UID: 115694569
  directID: jlei4
  */

#include "text_manipulation.h"
#include <stdio.h>
#include <string.h>

void test_assert(int test_result, const char *test_name, int test_number) {
  if (test_result) {
    printf("pass %s %d\n", test_name, test_number);
  } else {
    printf("fail %s %d\n", test_name, test_number);
  }
}

int main() {
  char result[MAX_STR_LEN + 1];

  center("terps", 7, result);
  test_assert(strcmp(result, " terps ") == 0, "center", 1);

  return 0;
}

int remove_spaces(const char *source, char *result, int *num_spaces_removed) {
  int count = 0;
  int i, j = 0;
  int len;
  /*if the null pointer or the len == 0  then return false;*/
  if (source == NULL || strlen(source) == 0) {
    return FAILURE;
  } else {
    len = strlen(source);
    /*if there is a space   counter ++*/
    for (i = 0; i < len + 1; i++) {
      if (source[i] == ' ') {
        count++;
      } else {
        /*if not copy the character from source*/
        result[j] = source[i];
        j++;
      }
    }
    /*if the num_spaces_removed is not empty  then set it to count*/
    if (num_spaces_removed != NULL) {
      *num_spaces_removed = count;
    }
    /*the last character is \0*/
    result[strlen(result) + 1] = '\0';
    return SUCCESS;
  }
}

int center(const char *source, int width, char *result) {
  int space = 0;
  int i, j = 0;
  int len = 0;
  /*check if failure*/
  if (source == NULL || strlen(source) == 0 || width < strlen(source)) {
    return FAILURE;
  } else {
    /*assign varaible after make sure source is not null*/
    len = strlen(source);
    space = (width - len) / 2;
    /*add space before*/
    for (i = 0; i < space; i++) {
      result[i] = ' ';
      j++;
    }
    /*copy the string*/
    for (i = 0; i < len; i++) {
      result[j] = source[i];
      j++;
    }
    /*add after*/
    for (i = 0; i < space; i++) {
      result[j] = ' ';
      j++;
    }
    /*the last character is \0*/
    result[strlen(result) + 1] = '\0';
    return SUCCESS;
  }
}
