/*name : jiawen lei
  UID: 115694569
  DID: jlei4
  */
#include "calendar.h"
#include "event.h"
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sysexits.h>
int init_calendar(const char *name, int days,
                  int (*comp_func)(const void *ptr1, const void *ptr2),
                  void (*free_info_func)(void *ptr), Calendar **calendar) {
  /*check invalid pararmeter*/
  if (!(*calendar) || !name || days < 1) {
    return FAILURE;
  }
  /*allocate calendar and check if NULL*/
  (*calendar) = malloc(sizeof(Calendar));
  if (!(*calendar)) {
    return FAILURE;
  }
  /*samething here allocate name and events and check if they are NULL*/
  (*calendar)->name = malloc(strlen(name) + 1);
  (*calendar)->events = calloc(days, sizeof(Event));
  if (!(*calendar)->name || !(*calendar)->events) {
    return FAILURE;
  }
  /*copy value of pararmeter and return*/
  strcpy((*calendar)->name, name);
  (*calendar)->days = days;
  (*calendar)->total_events = 0;
  (*calendar)->comp_func = comp_func;
  (*calendar)->free_info_func = free_info_func;
  return SUCCESS;
}

int print_calendar(Calendar *calendar, FILE *output_stream, int print_all) {
  int i;
  Event *curr;
  /*check invalid case*/
  if (!calendar || !output_stream) {
    return FAILURE;
  }
  /*if print_all is not NULL, then print name days and events*/
  if (print_all) {
    fprintf(output_stream, "Calendar's Name: \"%s\"\n", calendar->name);
    fprintf(output_stream, "Days: %d\n", calendar->days);
    fprintf(output_stream, "Total Events: %d\n\n", calendar->total_events);
  }
  printf("**** Events ****\n");
  /*if total_events is not 0, then print event*/
  for (i = 0; i < calendar->days; i++) {
    curr = calendar->events[i];
    if (calendar->total_events > 0) {
      fprintf(output_stream, "Day %d\n", i + 1);
      while (curr) {
        fprintf(output_stream, "Event's Name: \"%s\", ", curr->name);
        fprintf(output_stream, "Start_time: %d, ", curr->start_time);
        fprintf(output_stream, "Duration: %d\n", curr->duration_minutes);
        curr = curr->next;
      }
    }
  }
  return SUCCESS;
}

int add_event(Calendar *calendar, const char *name, int start_time,
              int duration_minutes, void *info, int day) {
  Event *new_event;
  Event *curr;
  Event *temp = NULL;
  /*check invalid case*/
  if (!calendar || !name || start_time < 0 || start_time > 2400 ||
      duration_minutes <= 0 || day < 1 || day > calendar->days) {
    return FAILURE;
  }
  /*allocate event if new_event is null then free it*/
  new_event = malloc(sizeof(Event));
  if (!new_event) {
    free(new_event);
    return FAILURE;
  }
  /*allocate new_event's name, if null, free*/
  new_event->name = malloc(strlen(name) + 1);
  if (!(new_event->name)) {
    free(new_event->name);
    free(new_event);
    return FAILURE;
  }
  /*copy the value of pararmeter*/
  strcpy(new_event->name, name);
  new_event->start_time = start_time;
  new_event->duration_minutes = duration_minutes;
  new_event->info = info;
  curr = calendar->events[day - 1];
  /*use comp_func to find the curr location*/
  while (curr && (calendar->comp_func(new_event, curr) >= 0)) {
    temp = curr;
    curr = curr->next;
  }
  /*if there already had the event, FAILURE*/
  if (find_event(calendar, name, &curr) == SUCCESS) {
    return FAILURE;
  }
  /*put front*/
  if (temp) {
    temp->next = new_event;
    new_event->next = curr;
  }
  /*put after*/
  else {
    calendar->events[day - 1] = new_event;
    new_event->next = curr;
  }
  /*return and event++*/
  calendar->total_events++;
  return SUCCESS;
}

int find_event(Calendar *calendar, const char *name, Event **event) {
  Event *curr;
  int i;
  /*check invalid case*/
  if (!calendar || !name) {
    return FAILURE;
  }
  /*check if the given pararmeter is not NULL*/
  if (*event) {
    /*loop through days and compare each event's name with the pararmeter name*/
    for (i = 0; i < calendar->days; i++) {
      curr = calendar->events[i];
      while (curr) {
        if (strcmp(curr->name, name) == 0) {
          /*if found, return the event points and return SUCCESS*/
          (*event) = curr;
          return SUCCESS;
        }
        curr = curr->next;
      }
    }
  }
  return FAILURE;
}
int find_event_in_day(Calendar *calendar, const char *name, int day,
                      Event **event) {
  Event *curr;
  /*check invalid case*/
  if (!calendar || !name || day < 1 || day > calendar->days) {
    return FAILURE;
  }
  /*if event != NULL, set curr = that specific day*/
  if (*event) {
    curr = calendar->events[day - 1];
    /*loop through to find the specific day with specific name*/
    while (curr) {
      if (strcmp(curr->name, name) == 0) {
        (*event) = curr;
        return SUCCESS;
      }
      curr = curr->next;
    }
  }
  return FAILURE;
}
int remove_event(Calendar *calendar, const char *name) {
  Event *curr;
  Event *temp = NULL;
  int i;
  /*check invalid case*/
  if (!calendar || !name) {
    return FAILURE;
  }
  /*loop through*/
  for (i = 0; i < calendar->days; i++) {
    curr = calendar->events[i];
    while (curr) {
      /*find specific name*/
      if (strcmp(curr->name, name) == 0) {
        temp = curr;
        /*check free_info_func and check curr info*/
        if (calendar->free_info_func && curr->info) {
          calendar->free_info_func(temp->info);
        }
        /*free*/
        free(temp->name);
        curr = curr->next;
        free(temp);
      }
      curr = curr->next;
    }
  }
  calendar->total_events--;
  return SUCCESS;
}
void *get_event_info(Calendar *calendar, const char *name) {
  Event *curr;
  int i;
  /*loop through and call find_event function to get info*/
  for (i = 0; i < calendar->days; i++) {
    curr = calendar->events[i];
    while (curr) {
      if (find_event(calendar, name, &curr) == SUCCESS) {
        return curr->info;
      }
      curr = curr->next;
    }
  }
  return NULL;
}
int clear_calendar(Calendar *calendar) {
  Event *curr;
  Event *temp = NULL;
  int i;
  /*invalid case*/
  if (!calendar) {
    return FAILURE;
  }
  /*same as pervious function*/
  for (i = 0; i < calendar->days; i++) {
    curr = calendar->events[i];
    while (curr) {
      temp = curr;
      /*check if the given free_info_func and info is NULL,if not then free
       * info*/
      if (calendar->free_info_func && curr->info) {
        calendar->free_info_func(temp->info);
      }
      /*free name and loop through to free temp*/
      free(temp->name);
      curr = curr->next;
      free(temp);
    }
    calendar->events[i] = NULL;
  }
  calendar->total_events = 0;
  return SUCCESS;
}
int clear_day(Calendar *calendar, int day) {
  Event *curr;
  Event *temp = NULL;
  int count = 0;
  /*check invalid case*/
  if (!calendar || day < 1 || day > calendar->days) {
    return FAILURE;
  }

  curr = calendar->events[day - 1];
  /*loop through and free*/
  while (curr) {
    temp = curr;
    if (calendar->free_info_func && curr->info) {
      calendar->free_info_func(temp->info);
    }
    free(temp->name);
    curr = curr->next;
    free(temp);
    calendar->total_events--;
  }
  /*set the node with specific day to null*/
  calendar->events[day - 1] = NULL;
  return SUCCESS;
}

int destroy_calendar(Calendar *calendar) {
  Event *curr;
  Event *temp = NULL;
  int i;
  /*invalid case*/
  if (!calendar) {
    return FAILURE;
  }
  /*same as pervious function*/
  for (i = 0; i < calendar->days; i++) {
    curr = calendar->events[i];
    while (curr) {
      temp = curr;
      /*check if the given free_info_func and info is NULL,if not then free
       * info*/
      if (calendar->free_info_func && curr->info) {
        calendar->free_info_func(temp->info);
      }
      /*free name and loop through to free temp*/
      free(temp->name);
      curr = curr->next;
      free(temp);
    }
  }
  /*free the rest*/
  free(calendar->events);
  free(calendar->name);
  free(calendar);
  return SUCCESS;
}
