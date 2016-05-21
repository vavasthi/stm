/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.config.validator;

import com.sanjnan.server.config.mapper.annotations.H2ONonNullString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by vinay on 3/15/16.
 */
public class SanjnanNonNullStringValidator implements ConstraintValidator<H2ONonNullString, String> {

  private H2ONonNullString h2ONonNullString;
  @Override
  public void initialize(final H2ONonNullString h2ONonNullString) {
    this.h2ONonNullString = h2ONonNullString;
  }

  @Override
  public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {

    if (s == null) {
      return h2ONonNullString.nullAllowed();
    }
    if (s.length() >= h2ONonNullString.min() && s.length() <= h2ONonNullString.max()) {
      return true;
    }
    return false;
  }
}
