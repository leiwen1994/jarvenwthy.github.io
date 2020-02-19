#include "photoalbum.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

Photo *create_photo(int id, const char *description) {
  Photo *new_photo;
  new_photo = malloc(sizeof(Photo));
  new_photo->id = id;
  if (description != NULL) {
    if (new_photo == NULL) {
      return NULL;
    } else {
      new_photo->description = malloc(strlen(description) + 1);
      if (new_photo->description == NULL) {
        return NULL;
      }
      strcpy(new_photo->description, description);
    }
  } else {
    new_photo->description = NULL;
  }
  return new_photo;
}

void print_photo(Photo *photo) {
  if (photo != NULL) {
    if (photo->description == NULL) {
      printf("Photo Id: %d, Description: None", photo->id);
    }
    printf("Photo Id: %d, Description: %s\n", photo->id, photo->description);
  }
}

void destroy_photo(Photo *photo) {
  if (photo != NULL) {
    if (photo->description != NULL) {
      free(photo->description);
    }
    free(photo);
  }
}

void initialize_album(Album *album) {
  if (album != NULL) {
    album->size = 0;
  }
}

void print_album(const Album *album) {
  int i;
  if (album != NULL) {
    if (album->size == 0) {
      printf("Album has no photos.\n");
    }
    for (i = 0; i < album->size; i++) {
      print_photo(album->all_photos[i]);
    }
  }
}

void destroy_album(Album *album) {
  int i;
  if (album != NULL) {
    for (i = 0; i < album->size; i++) {
      destroy_photo(album->all_photos[i]);
    }
    album->size = 0;
  }
}

void add_photo_to_album(Album *album, int id, const char *description) {
  Photo *new_photo;
  if (album != NULL) {
    if (album->size < MAX_ALBUM_SIZE) {
      new_photo = create_photo(id, description);
      if (new_photo != NULL) {

        album->all_photos[album->size] = new_photo;
      }
    }
    album->size++;
  }
}
