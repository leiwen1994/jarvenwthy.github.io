/*name: Jiawen Lei
  UID:115694569
  DirectoryID: jlei4
  */
#include <math.h>
#include <stdio.h>

#define MAX_ASSIGNMENTS 50

void print(int arr[], int number_of_assignments,
           int points_penalty_per_day_late, int drop_n_lower_value_assignments,
           double mean, double deviation, double numeric, char stats);

double numeric_cal(int score[], int number_of_assignments,
                   int points_penalty_per_day_late, int days[], int weight[]);
int main() {
  int points_penalty_per_day_late = 0;
  int drop_n_lower_value_assignments = 0;
  int number_of_assignments = 0;
  char stats;
  double mean = 0.0;
  double deviation = 0.0;
  double numeric = 0.0;
  int i, j, k;
  int sum = 0;
  int compare_num = 1;
  int counter = 0;
  int lower_value = 10001;
  int temp = 0;
  int temp2 = 0;

  int number[MAX_ASSIGNMENTS];
  int score[MAX_ASSIGNMENTS];
  int weight[MAX_ASSIGNMENTS];
  int late[MAX_ASSIGNMENTS];
  int new_score[MAX_ASSIGNMENTS];
  int new_arr[MAX_ASSIGNMENTS];
  int low_value[MAX_ASSIGNMENTS];
  int low_value_array[MAX_ASSIGNMENTS];
  int cal_numeric_score[MAX_ASSIGNMENTS];
  int weight_array_copy[MAX_ASSIGNMENTS];
  int late_array_copy[MAX_ASSIGNMENTS];
  /*scan the input and store into variable*/
  scanf("%d %d %c", &points_penalty_per_day_late,
        &drop_n_lower_value_assignments, &stats);
  scanf(" %d", &number_of_assignments);
  for (i = 0; i < number_of_assignments; i++) {
    scanf(" %d, %d, %d, %d,", &number[i], &score[i], &weight[i], &late[i]);
    sum += weight[i];
    new_score[i] = score[i] - late[i] * points_penalty_per_day_late;
    mean += new_score[i];
  }
  /*calcu mean*/
  mean = mean / number_of_assignments;

  /*sort the array*/
  for (i = 0; i < number_of_assignments; i++) {
    for (j = 0; j < number_of_assignments; j++) {
      if (compare_num == number[j]) {
        new_arr[counter++] = number[j];
        new_arr[counter++] = score[j];
        new_arr[counter++] = weight[j];
        new_arr[counter++] = late[j];
      }
    }
    compare_num++;
  }
  /*if the weight is not 100 error*/
  if (sum != 100) {
    printf("ERROR: Invalid values provided\n");
    return 0;
  }

  /*make a weight array to compare*/
  for (k = 0; k < number_of_assignments; k++) {
    low_value[k] = weight[k] * score[k];
  }

  /*find the n lowest weight assignments and put in a new array*/
  for (i = 0; i < drop_n_lower_value_assignments; i++) {
    for (j = 0; j < number_of_assignments; j++) {
      if (low_value[j] != temp && low_value[j] < lower_value) {
        lower_value = low_value[j];
        temp2 = lower_value;
      }
    }

    temp = temp2;
    low_value_array[i] = temp;
    lower_value = 10001;
  }

  /*make a new array after drop the n low weight array*/
  for (j = 0; j < number_of_assignments; j++) {
    if (low_value[j] > low_value_array[drop_n_lower_value_assignments - 1]) {
      cal_numeric_score[j] = score[j];
    }
  }

  /*make two copy array to connect the high weight score with weight and day
   * late*/
  for (j = 0; j < number_of_assignments - drop_n_lower_value_assignments; j++) {
    for (i = 0; i < number_of_assignments; i++) {
      if (score[i] == cal_numeric_score[j]) {
        weight_array_copy[j] = weight[i];
        late_array_copy[j] = late[i];
      }
    }
  }

  /*cal deviation*/
  for (j = 0; j < number_of_assignments; j++) {
    deviation += pow(new_score[j] - mean, 2);
  }
  deviation = sqrt(deviation / number_of_assignments);

  /*call helper*/
  numeric = numeric_cal(cal_numeric_score, number_of_assignments,
                        points_penalty_per_day_late, late_array_copy,
                        weight_array_copy);
  print(new_arr, number_of_assignments, points_penalty_per_day_late,
        drop_n_lower_value_assignments, mean, deviation, numeric, stats);

  return 1;
}
void print(int arr[], int number_of_assignments,
           int points_penalty_per_day_late, int drop_n_lower_value_assignments,
           double mean, double deviation, double numeric, char stats) {
  int i;
  int count = 1;

  printf("Numeric Score: %5.4f\n", numeric);
  printf("Points Penalty Per Day Late: %d\n", points_penalty_per_day_late);
  printf("Number of Assignments Dropped: %d\n", drop_n_lower_value_assignments);
  printf("Values Provided: \n");
  printf("Assignment, Score, Weight, Days Late\n");
  for (i = 0; i < number_of_assignments * 4; i++) {
    printf("%d", arr[i]);
    if (count % 4 != 0) {
      printf(", ");
    } else {
      printf("\n");
    }
    count++;
  }
  if (stats == 'y' || stats == 'Y') {
    printf("Mean: %5.4f, Standard Deviation: %5.4f \n", mean, deviation);
  }
}
/*a helper that helps cal numeric*/
double numeric_cal(int score[], int number_of_assignments,
                   int points_penalty_per_day_late, int days[], int weight[]) {
  int i;
  double sum = 0.0;
  double numeric = 0.0;
  double weight_sum = 0.0;

  for (i = 0; i < number_of_assignments; i++) {
    sum = sum +
          ((score[i] - (points_penalty_per_day_late * days[i])) * weight[i]);
    weight_sum += weight[i];
  }
  numeric = sum / weight_sum;
  return numeric;
}
