package com.opengamma.platform.finance.future;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
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

@BeanDefinition
public class ExpandedIborFutureOption implements IborFutureOptionProduct, ImmutableBean, Serializable {

  @PropertyDefinition(validate = "notNull")
  private final ExpandedIborFuture expandedIborFuture;

  @PropertyDefinition(validate = "notNull")
  private final LocalDate expirationDate;

  @PropertyDefinition
  private final double strike;

  @PropertyDefinition
  private final boolean isCall;

  @PropertyDefinition(validate = "notNull")
  private final LocalDate lastTradeDate;

  //-------------------------------------------------------------------------
  @Override
  public ExpandedIborFutureOption expand() {
    return this;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ExpandedIborFutureOption}.
   * @return the meta-bean, not null
   */
  public static ExpandedIborFutureOption.Meta meta() {
    return ExpandedIborFutureOption.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ExpandedIborFutureOption.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static ExpandedIborFutureOption.Builder builder() {
    return new ExpandedIborFutureOption.Builder();
  }

  /**
   * Restricted constructor.
   * @param builder  the builder to copy from, not null
   */
  protected ExpandedIborFutureOption(ExpandedIborFutureOption.Builder builder) {
    JodaBeanUtils.notNull(builder.expandedIborFuture, "expandedIborFuture");
    JodaBeanUtils.notNull(builder.expirationDate, "expirationDate");
    JodaBeanUtils.notNull(builder.lastTradeDate, "lastTradeDate");
    this.expandedIborFuture = builder.expandedIborFuture;
    this.expirationDate = builder.expirationDate;
    this.strike = builder.strike;
    this.isCall = builder.isCall;
    this.lastTradeDate = builder.lastTradeDate;
  }

  @Override
  public ExpandedIborFutureOption.Meta metaBean() {
    return ExpandedIborFutureOption.Meta.INSTANCE;
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
   * Gets the expandedIborFuture.
   * @return the value of the property, not null
   */
  public ExpandedIborFuture getExpandedIborFuture() {
    return expandedIborFuture;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the expirationDate.
   * @return the value of the property, not null
   */
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the strike.
   * @return the value of the property
   */
  public double getStrike() {
    return strike;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the isCall.
   * @return the value of the property
   */
  public boolean isIsCall() {
    return isCall;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the lastTradeDate.
   * @return the value of the property, not null
   */
  public LocalDate getLastTradeDate() {
    return lastTradeDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ExpandedIborFutureOption other = (ExpandedIborFutureOption) obj;
      return JodaBeanUtils.equal(getExpandedIborFuture(), other.getExpandedIborFuture()) &&
          JodaBeanUtils.equal(getExpirationDate(), other.getExpirationDate()) &&
          JodaBeanUtils.equal(getStrike(), other.getStrike()) &&
          (isIsCall() == other.isIsCall()) &&
          JodaBeanUtils.equal(getLastTradeDate(), other.getLastTradeDate());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getExpandedIborFuture());
    hash = hash * 31 + JodaBeanUtils.hashCode(getExpirationDate());
    hash = hash * 31 + JodaBeanUtils.hashCode(getStrike());
    hash = hash * 31 + JodaBeanUtils.hashCode(isIsCall());
    hash = hash * 31 + JodaBeanUtils.hashCode(getLastTradeDate());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(192);
    buf.append("ExpandedIborFutureOption{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("expandedIborFuture").append('=').append(JodaBeanUtils.toString(getExpandedIborFuture())).append(',').append(' ');
    buf.append("expirationDate").append('=').append(JodaBeanUtils.toString(getExpirationDate())).append(',').append(' ');
    buf.append("strike").append('=').append(JodaBeanUtils.toString(getStrike())).append(',').append(' ');
    buf.append("isCall").append('=').append(JodaBeanUtils.toString(isIsCall())).append(',').append(' ');
    buf.append("lastTradeDate").append('=').append(JodaBeanUtils.toString(getLastTradeDate())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ExpandedIborFutureOption}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code expandedIborFuture} property.
     */
    private final MetaProperty<ExpandedIborFuture> expandedIborFuture = DirectMetaProperty.ofImmutable(
        this, "expandedIborFuture", ExpandedIborFutureOption.class, ExpandedIborFuture.class);
    /**
     * The meta-property for the {@code expirationDate} property.
     */
    private final MetaProperty<LocalDate> expirationDate = DirectMetaProperty.ofImmutable(
        this, "expirationDate", ExpandedIborFutureOption.class, LocalDate.class);
    /**
     * The meta-property for the {@code strike} property.
     */
    private final MetaProperty<Double> strike = DirectMetaProperty.ofImmutable(
        this, "strike", ExpandedIborFutureOption.class, Double.TYPE);
    /**
     * The meta-property for the {@code isCall} property.
     */
    private final MetaProperty<Boolean> isCall = DirectMetaProperty.ofImmutable(
        this, "isCall", ExpandedIborFutureOption.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code lastTradeDate} property.
     */
    private final MetaProperty<LocalDate> lastTradeDate = DirectMetaProperty.ofImmutable(
        this, "lastTradeDate", ExpandedIborFutureOption.class, LocalDate.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "expandedIborFuture",
        "expirationDate",
        "strike",
        "isCall",
        "lastTradeDate");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 108063544:  // expandedIborFuture
          return expandedIborFuture;
        case -668811523:  // expirationDate
          return expirationDate;
        case -891985998:  // strike
          return strike;
        case -1180608856:  // isCall
          return isCall;
        case -1041950404:  // lastTradeDate
          return lastTradeDate;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public ExpandedIborFutureOption.Builder builder() {
      return new ExpandedIborFutureOption.Builder();
    }

    @Override
    public Class<? extends ExpandedIborFutureOption> beanType() {
      return ExpandedIborFutureOption.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code expandedIborFuture} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExpandedIborFuture> expandedIborFuture() {
      return expandedIborFuture;
    }

    /**
     * The meta-property for the {@code expirationDate} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalDate> expirationDate() {
      return expirationDate;
    }

    /**
     * The meta-property for the {@code strike} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> strike() {
      return strike;
    }

    /**
     * The meta-property for the {@code isCall} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> isCall() {
      return isCall;
    }

    /**
     * The meta-property for the {@code lastTradeDate} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalDate> lastTradeDate() {
      return lastTradeDate;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 108063544:  // expandedIborFuture
          return ((ExpandedIborFutureOption) bean).getExpandedIborFuture();
        case -668811523:  // expirationDate
          return ((ExpandedIborFutureOption) bean).getExpirationDate();
        case -891985998:  // strike
          return ((ExpandedIborFutureOption) bean).getStrike();
        case -1180608856:  // isCall
          return ((ExpandedIborFutureOption) bean).isIsCall();
        case -1041950404:  // lastTradeDate
          return ((ExpandedIborFutureOption) bean).getLastTradeDate();
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
   * The bean-builder for {@code ExpandedIborFutureOption}.
   */
  public static class Builder extends DirectFieldsBeanBuilder<ExpandedIborFutureOption> {

    private ExpandedIborFuture expandedIborFuture;
    private LocalDate expirationDate;
    private double strike;
    private boolean isCall;
    private LocalDate lastTradeDate;

    /**
     * Restricted constructor.
     */
    protected Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    protected Builder(ExpandedIborFutureOption beanToCopy) {
      this.expandedIborFuture = beanToCopy.getExpandedIborFuture();
      this.expirationDate = beanToCopy.getExpirationDate();
      this.strike = beanToCopy.getStrike();
      this.isCall = beanToCopy.isIsCall();
      this.lastTradeDate = beanToCopy.getLastTradeDate();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 108063544:  // expandedIborFuture
          return expandedIborFuture;
        case -668811523:  // expirationDate
          return expirationDate;
        case -891985998:  // strike
          return strike;
        case -1180608856:  // isCall
          return isCall;
        case -1041950404:  // lastTradeDate
          return lastTradeDate;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 108063544:  // expandedIborFuture
          this.expandedIborFuture = (ExpandedIborFuture) newValue;
          break;
        case -668811523:  // expirationDate
          this.expirationDate = (LocalDate) newValue;
          break;
        case -891985998:  // strike
          this.strike = (Double) newValue;
          break;
        case -1180608856:  // isCall
          this.isCall = (Boolean) newValue;
          break;
        case -1041950404:  // lastTradeDate
          this.lastTradeDate = (LocalDate) newValue;
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
    public ExpandedIborFutureOption build() {
      return new ExpandedIborFutureOption(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code expandedIborFuture} property in the builder.
     * @param expandedIborFuture  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder expandedIborFuture(ExpandedIborFuture expandedIborFuture) {
      JodaBeanUtils.notNull(expandedIborFuture, "expandedIborFuture");
      this.expandedIborFuture = expandedIborFuture;
      return this;
    }

    /**
     * Sets the {@code expirationDate} property in the builder.
     * @param expirationDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder expirationDate(LocalDate expirationDate) {
      JodaBeanUtils.notNull(expirationDate, "expirationDate");
      this.expirationDate = expirationDate;
      return this;
    }

    /**
     * Sets the {@code strike} property in the builder.
     * @param strike  the new value
     * @return this, for chaining, not null
     */
    public Builder strike(double strike) {
      this.strike = strike;
      return this;
    }

    /**
     * Sets the {@code isCall} property in the builder.
     * @param isCall  the new value
     * @return this, for chaining, not null
     */
    public Builder isCall(boolean isCall) {
      this.isCall = isCall;
      return this;
    }

    /**
     * Sets the {@code lastTradeDate} property in the builder.
     * @param lastTradeDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder lastTradeDate(LocalDate lastTradeDate) {
      JodaBeanUtils.notNull(lastTradeDate, "lastTradeDate");
      this.lastTradeDate = lastTradeDate;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(192);
      buf.append("ExpandedIborFutureOption.Builder{");
      int len = buf.length();
      toString(buf);
      if (buf.length() > len) {
        buf.setLength(buf.length() - 2);
      }
      buf.append('}');
      return buf.toString();
    }

    protected void toString(StringBuilder buf) {
      buf.append("expandedIborFuture").append('=').append(JodaBeanUtils.toString(expandedIborFuture)).append(',').append(' ');
      buf.append("expirationDate").append('=').append(JodaBeanUtils.toString(expirationDate)).append(',').append(' ');
      buf.append("strike").append('=').append(JodaBeanUtils.toString(strike)).append(',').append(' ');
      buf.append("isCall").append('=').append(JodaBeanUtils.toString(isCall)).append(',').append(' ');
      buf.append("lastTradeDate").append('=').append(JodaBeanUtils.toString(lastTradeDate)).append(',').append(' ');
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}