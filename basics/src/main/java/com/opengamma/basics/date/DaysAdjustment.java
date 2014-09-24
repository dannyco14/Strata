/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.basics.date;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.collect.ArgChecker;

/**
 * An adjustment that alters a date by adding a period of days.
 * <p>
 * When processing dates in finance, the rules for adjusting a date by a number of days can be complex.
 * This class represents those rules, which operate in two steps - addition followed by adjustment.
 * There are two main ways to perform the addition:
 * 
 * <h4>Approach 1 - calendar days addition</h4>
 * This approach is triggered by using the {@code ofCalendarDays()} factory methods.
 * When adding a number of days to a date the addition is simple, no holidays or weekends apply.
 * For example, two days after Friday 15th August would be Sunday 17th, even though this is typically a weekend.
 * There are two steps in the calculation:
 * <p>
 * In step one, the number of days is added without skipping any dates.
 * <p>
 * In step two, the result of step one is optionally adjusted to be a business day
 * using a {@code BusinessDayAdjustment}.
 * 
 * <h4>Approach 2 - business days addition</h4>
 * With this approach the days to be added are treated as business days.
 * For example, two days after Friday 15th August would be Tuesday 19th, assuming a Saturday/Sunday
 * weekend and no other applicable holidays.
 * <p>
 * This approach is triggered by using the {@code ofBusinessDays()} factory methods.
 * The distinction between business days, holidays and weekends is made using the specified holiday calendar.
 * There are two steps in the calculation:
 * <p>
 * In step one, the number of days is added using {@link HolidayCalendar#shift(LocalDate, int)}.
 * <p>
 * In step two, the result of step one is optionally adjusted to be a business day
 * using a {@code BusinessDayAdjustment}.
 * <p>
 * At first glance, step two may seem pointless, as the result of step one will always be a valid business day.
 * However, the step two adjustment allows the possibility of applying a different holiday calendar.
 * <p>
 * For example, a rule might have two parts: "first add 2 London business days, and then adjust the
 * result to be a valid New York business day using the 'ModifiedFollowing' convention".
 * Note that the holiday calendar differs in the two parts of the rule.
 * 
 * <h4>Usage</h4>
 * {@code DaysAdjustment} implements {@code TemporalAdjuster} allowing it to directly adjust a date:
 * <pre>
 *  LocalDate adjusted = baseDate.with(daysAdjustment);
 * </pre>
 */
@BeanDefinition(builderScope = "private")
public final class DaysAdjustment
    implements ImmutableBean, DateAdjuster, Serializable {

  /**
   * An instance that performs no adjustment.
   */
  public static final DaysAdjustment NONE =
      new DaysAdjustment(0, HolidayCalendars.NO_HOLIDAYS, BusinessDayAdjustment.NONE);

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The number of days to be added.
   * <p>
   * When the adjustment is performed, this amount will be added to the input date
   * using the calendar to determine the addition type.
   */
  @PropertyDefinition(validate = "notNull")
  private final int days;
  /**
   * The holiday calendar that defines the meaning of a day when performing the addition.
   * <p>
   * When the adjustment is performed, this calendar is used to determine which days are business days.
   * <p>
   * If the holiday calendar is 'None' then addition uses simple date addition arithmetic without
   * considering any days as holidays or weekends.
   * If the holiday calendar is anything other than 'None' then addition uses that calendar,
   * effectively repeatedly finding the next business day.
   * <p>
   * See the class-level documentation for more information.
   */
  @PropertyDefinition(validate = "notNull")
  private final HolidayCalendar calendar;
  /**
   * The business day adjustment that is performed to the result of the addition.
   * <p>
   * This adjustment is applied to the result of the period addition calculation.
   * If the addition is performed using business days then any adjustment here is expected to
   * have a different holiday calendar to that used during addition.
   * <p>
   * If no adjustment is required, use the 'None' business day adjustment.
   * <p>
   * See the class-level documentation for more information.
   */
  @PropertyDefinition(validate = "notNull")
  private final BusinessDayAdjustment adjustment;

  //-------------------------------------------------------------------------
  /**
   * Obtains a days adjustment that can adjust a date by a specific number of calendar days.
   * <p>
   * When adjusting a date, the specified number of calendar days is added.
   * Holidays and weekends are not taken into account in the calculation.
   * <p>
   * No business day adjustment is applied to the result of the addition.
   * 
   * @param numberOfDays  the number of days
   * @return the days adjustment
   */
  public static DaysAdjustment ofCalendarDays(int numberOfDays) {
    return new DaysAdjustment(numberOfDays, HolidayCalendars.NO_HOLIDAYS, BusinessDayAdjustment.NONE);
  }

  /**
   * Obtains a days adjustment that can adjust a date by a specific number of calendar days.
   * <p>
   * When adjusting a date, the specified number of calendar days is added.
   * Holidays and weekends are not taken into account in the calculation.
   * <p>
   * The business day adjustment is applied to the result of the addition.
   * 
   * @param numberOfDays  the number of days
   * @param adjustment  the business day adjustment to apply to the result of the addition
   * @return the days adjustment
   */
  public static DaysAdjustment ofCalendarDays(int numberOfDays, BusinessDayAdjustment adjustment) {
    return new DaysAdjustment(numberOfDays, HolidayCalendars.NO_HOLIDAYS, adjustment);
  }

  /**
   * Obtains a days adjustment that can adjust a date by a specific number of business days.
   * <p>
   * When adjusting a date, the specified number of business days is added.
   * This is equivalent to repeatedly finding the next business day.
   * <p>
   * No business day adjustment is applied to the result of the addition.
   * 
   * @param numberOfDays  the number of days
   * @param holidayCalendar  the calendar that defines holidays and business days
   * @return the days adjustment
   */
  public static DaysAdjustment ofBusinessDays(int numberOfDays, HolidayCalendar holidayCalendar) {
    return new DaysAdjustment(numberOfDays, holidayCalendar, BusinessDayAdjustment.NONE);
  }

  /**
   * Obtains a days adjustment that can adjust a date by a specific number of business days.
   * <p>
   * When adjusting a date, the specified number of business days is added.
   * This is equivalent to repeatedly finding the next business day.
   * <p>
   * The business day adjustment is applied to the result of the addition.
   * 
   * @param numberOfDays  the number of days
   * @param holidayCalendar  the calendar that defines holidays and business days
   * @param adjustment  the business day adjustment to apply to the result of the addition
   * @return the days adjustment
   */
  public static DaysAdjustment ofBusinessDays(
      int numberOfDays,
      HolidayCalendar holidayCalendar,
      BusinessDayAdjustment adjustment) {
    return new DaysAdjustment(numberOfDays, holidayCalendar, adjustment);
  }

  //-------------------------------------------------------------------------
  /**
   * Adjusts the date, adding the period in days using the holiday calendar
   * and then applying a business day adjustment.
   * <p>
   * The calculation is performed in two steps.
   * <p>
   * Step one, use {@link HolidayCalendar#shift(LocalDate, int)} to add the number of days.
   * If the holiday calendar is 'None' this will effectively add using simple arithmetic.
   * <p>
   * Step two, use {@link BusinessDayAdjustment#adjust(LocalDate)} to adjust the result of step one.
   * 
   * @param date  the date to adjust
   * @return the adjusted temporal
   */
  @Override
  public LocalDate adjust(LocalDate date) {
    ArgChecker.notNull(date, "date");
    LocalDate added = calendar.shift(date, days);
    return adjustment.adjust(added);
  }

  //-------------------------------------------------------------------------
  /**
   * Returns a string describing the adjustment.
   * 
   * @return the descriptive string
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append(days);
    if (calendar == HolidayCalendars.NO_HOLIDAYS) {
      buf.append(" calendar day");
      if (days != 1) {
        buf.append("s");
      }
    } else {
      buf.append(" business day");
      if (days != 1) {
        buf.append("s");
      }
      buf.append(" using calendar ").append(calendar);
    }
    if (adjustment.equals(BusinessDayAdjustment.NONE) == false) {
      buf.append(" then apply ").append(adjustment);
    }
    return buf.toString();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DaysAdjustment}.
   * @return the meta-bean, not null
   */
  public static DaysAdjustment.Meta meta() {
    return DaysAdjustment.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(DaysAdjustment.Meta.INSTANCE);
  }

  private DaysAdjustment(
      int days,
      HolidayCalendar calendar,
      BusinessDayAdjustment adjustment) {
    JodaBeanUtils.notNull(days, "days");
    JodaBeanUtils.notNull(calendar, "calendar");
    JodaBeanUtils.notNull(adjustment, "adjustment");
    this.days = days;
    this.calendar = calendar;
    this.adjustment = adjustment;
  }

  @Override
  public DaysAdjustment.Meta metaBean() {
    return DaysAdjustment.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the number of days to be added.
   * <p>
   * When the adjustment is performed, this amount will be added to the input date
   * using the calendar to determine the addition type.
   * @return the value of the property, not null
   */
  public int getDays() {
    return days;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the holiday calendar that defines the meaning of a day when performing the addition.
   * <p>
   * When the adjustment is performed, this calendar is used to determine which days are business days.
   * <p>
   * If the holiday calendar is 'None' then addition uses simple date addition arithmetic without
   * considering any days as holidays or weekends.
   * If the holiday calendar is anything other than 'None' then addition uses that calendar,
   * effectively repeatedly finding the next business day.
   * <p>
   * See the class-level documentation for more information.
   * @return the value of the property, not null
   */
  public HolidayCalendar getCalendar() {
    return calendar;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the business day adjustment that is performed to the result of the addition.
   * <p>
   * This adjustment is applied to the result of the period addition calculation.
   * If the addition is performed using business days then any adjustment here is expected to
   * have a different holiday calendar to that used during addition.
   * <p>
   * If no adjustment is required, use the 'None' business day adjustment.
   * <p>
   * See the class-level documentation for more information.
   * @return the value of the property, not null
   */
  public BusinessDayAdjustment getAdjustment() {
    return adjustment;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DaysAdjustment other = (DaysAdjustment) obj;
      return (getDays() == other.getDays()) &&
          JodaBeanUtils.equal(getCalendar(), other.getCalendar()) &&
          JodaBeanUtils.equal(getAdjustment(), other.getAdjustment());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getDays());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCalendar());
    hash += hash * 31 + JodaBeanUtils.hashCode(getAdjustment());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DaysAdjustment}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code days} property.
     */
    private final MetaProperty<Integer> days = DirectMetaProperty.ofImmutable(
        this, "days", DaysAdjustment.class, Integer.TYPE);
    /**
     * The meta-property for the {@code calendar} property.
     */
    private final MetaProperty<HolidayCalendar> calendar = DirectMetaProperty.ofImmutable(
        this, "calendar", DaysAdjustment.class, HolidayCalendar.class);
    /**
     * The meta-property for the {@code adjustment} property.
     */
    private final MetaProperty<BusinessDayAdjustment> adjustment = DirectMetaProperty.ofImmutable(
        this, "adjustment", DaysAdjustment.class, BusinessDayAdjustment.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "days",
        "calendar",
        "adjustment");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3076183:  // days
          return days;
        case -178324674:  // calendar
          return calendar;
        case 1977085293:  // adjustment
          return adjustment;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends DaysAdjustment> builder() {
      return new DaysAdjustment.Builder();
    }

    @Override
    public Class<? extends DaysAdjustment> beanType() {
      return DaysAdjustment.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code days} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Integer> days() {
      return days;
    }

    /**
     * The meta-property for the {@code calendar} property.
     * @return the meta-property, not null
     */
    public MetaProperty<HolidayCalendar> calendar() {
      return calendar;
    }

    /**
     * The meta-property for the {@code adjustment} property.
     * @return the meta-property, not null
     */
    public MetaProperty<BusinessDayAdjustment> adjustment() {
      return adjustment;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3076183:  // days
          return ((DaysAdjustment) bean).getDays();
        case -178324674:  // calendar
          return ((DaysAdjustment) bean).getCalendar();
        case 1977085293:  // adjustment
          return ((DaysAdjustment) bean).getAdjustment();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code DaysAdjustment}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<DaysAdjustment> {

    private int days;
    private HolidayCalendar calendar;
    private BusinessDayAdjustment adjustment;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3076183:  // days
          return days;
        case -178324674:  // calendar
          return calendar;
        case 1977085293:  // adjustment
          return adjustment;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 3076183:  // days
          this.days = (Integer) newValue;
          break;
        case -178324674:  // calendar
          this.calendar = (HolidayCalendar) newValue;
          break;
        case 1977085293:  // adjustment
          this.adjustment = (BusinessDayAdjustment) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public DaysAdjustment build() {
      return new DaysAdjustment(
          days,
          calendar,
          adjustment);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("DaysAdjustment.Builder{");
      buf.append("days").append('=').append(JodaBeanUtils.toString(days)).append(',').append(' ');
      buf.append("calendar").append('=').append(JodaBeanUtils.toString(calendar)).append(',').append(' ');
      buf.append("adjustment").append('=').append(JodaBeanUtils.toString(adjustment));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
