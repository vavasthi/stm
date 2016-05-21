package com.sanjnan.server.sanjnan.caching;

import com.sanjnan.server.exception.EntityNotFoundException;

import java.util.List;

/**
 * Created by vinay on 3/7/16.
 */
public class AbstractCacheService {

  protected <T> T getFirstElementFromList(List<T> entities) {

    if (entities != null && entities.size() != 0) {
      return entities.get(0);
    }
    else {
      throw new EntityNotFoundException("Entity not found.");
    }
  }


}
