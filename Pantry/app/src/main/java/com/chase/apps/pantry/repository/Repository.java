package com.chase.apps.pantry.repository;

import java.util.Set;

/**
 * Created by Chase on 2016-09-21.
 */
public interface Repository <Entity, Identity> {

    Entity findById(Identity identity1, Identity identity2);
    Entity save(Entity entity);
    Entity update(Entity entity);
    Entity delete (Entity entity);
    int deleteAll();
    Set<Entity> findAll();
}
