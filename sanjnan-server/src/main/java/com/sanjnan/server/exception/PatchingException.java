/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

import com.sanjnan.server.entity.PatchableEntity;

/**
 * Created by vinay on 1/11/16.
 */
public class PatchingException extends HubbleBaseException {
    /**
     * Instantiates a new Patching exception.
     *
     * @param destination the destination
     * @param source      the source
     */
    public PatchingException(PatchableEntity destination, PatchableEntity source) {
    super(destination.getClass().getName() + " can't be patched from " + source.getClass().getName());
  }

    public PatchingException(Class<?> cls, Object obj) {
        super(String.format("Object %s is not an instance of class %s.", obj.toString(), cls.getName()));
    }

    public PatchingException(Class<?> cls, String name) {
        super(String.format("Class %s doesn't contain a setter method %s.", cls.getName(), name));
    }
}
