/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.finance.swap;

import java.time.LocalDate;

import org.joda.beans.ImmutableBean;

import com.opengamma.basics.currency.Currency;

/**
 * A period over which interest is accrued with a single payment.
 * <p>
 * A single payment period within a swap leg.
 * Interest is accrued and paid within the period.
 * <p>
 * Implementations must be immutable and thread-safe beans.
 */
public interface PaymentPeriod
    extends ImmutableBean {

  /**
   * Gets the date that the payment is made.
   * <p>
   * Each payment period has a single payment date.
   * This date has been adjusted to be a valid business day.
   * 
   * @return the start date of the period
   */
  public LocalDate getPaymentDate();

  /**
   * Gets the start date of the period.
   * <p>
   * This is the first accrual date in the period.
   * This date has been adjusted to be a valid business day.
   * 
   * @return the start date of the period
   */
  public LocalDate getStartDate();

  /**
   * Gets the end date of the period.
   * <p>
   * This is the last accrual date in the period.
   * This date has been adjusted to be a valid business day.
   * 
   * @return the end date of the period
   */
  public LocalDate getEndDate();

  /**
   * Gets the currency of the period.
   * <p>
   * A period has a single currency.
   * 
   * @return the currency of the period
   */
  public Currency getCurrency();

}