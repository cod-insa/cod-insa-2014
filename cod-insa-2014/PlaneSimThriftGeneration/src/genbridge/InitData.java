/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package genbridge;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitData implements org.apache.thrift.TBase<InitData, InitData._Fields>, java.io.Serializable, Cloneable, Comparable<InitData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("InitData");

  private static final org.apache.thrift.protocol.TField BASES_FIELD_DESC = new org.apache.thrift.protocol.TField("bases", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField MAP_WIDTH_FIELD_DESC = new org.apache.thrift.protocol.TField("mapWidth", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField MAP_HEIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("mapHeight", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField PROGRESS_AXIS_FIELD_DESC = new org.apache.thrift.protocol.TField("progressAxis", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField MY_COUNTRY_FIELD_DESC = new org.apache.thrift.protocol.TField("myCountry", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField OTHERS_COUNTRY_FIELD_DESC = new org.apache.thrift.protocol.TField("othersCountry", org.apache.thrift.protocol.TType.LIST, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new InitDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new InitDataTupleSchemeFactory());
  }

  public List<BaseInitData> bases; // required
  public double mapWidth; // required
  public double mapHeight; // required
  public List<ProgressAxisInitData> progressAxis; // required
  public CountryInitData myCountry; // required
  public List<CountryInitData> othersCountry; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BASES((short)1, "bases"),
    MAP_WIDTH((short)2, "mapWidth"),
    MAP_HEIGHT((short)3, "mapHeight"),
    PROGRESS_AXIS((short)4, "progressAxis"),
    MY_COUNTRY((short)5, "myCountry"),
    OTHERS_COUNTRY((short)6, "othersCountry");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // BASES
          return BASES;
        case 2: // MAP_WIDTH
          return MAP_WIDTH;
        case 3: // MAP_HEIGHT
          return MAP_HEIGHT;
        case 4: // PROGRESS_AXIS
          return PROGRESS_AXIS;
        case 5: // MY_COUNTRY
          return MY_COUNTRY;
        case 6: // OTHERS_COUNTRY
          return OTHERS_COUNTRY;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __MAPWIDTH_ISSET_ID = 0;
  private static final int __MAPHEIGHT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BASES, new org.apache.thrift.meta_data.FieldMetaData("bases", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, BaseInitData.class))));
    tmpMap.put(_Fields.MAP_WIDTH, new org.apache.thrift.meta_data.FieldMetaData("mapWidth", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.MAP_HEIGHT, new org.apache.thrift.meta_data.FieldMetaData("mapHeight", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.PROGRESS_AXIS, new org.apache.thrift.meta_data.FieldMetaData("progressAxis", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ProgressAxisInitData.class))));
    tmpMap.put(_Fields.MY_COUNTRY, new org.apache.thrift.meta_data.FieldMetaData("myCountry", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CountryInitData.class)));
    tmpMap.put(_Fields.OTHERS_COUNTRY, new org.apache.thrift.meta_data.FieldMetaData("othersCountry", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CountryInitData.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(InitData.class, metaDataMap);
  }

  public InitData() {
  }

  public InitData(
    List<BaseInitData> bases,
    double mapWidth,
    double mapHeight,
    List<ProgressAxisInitData> progressAxis,
    CountryInitData myCountry,
    List<CountryInitData> othersCountry)
  {
    this();
    this.bases = bases;
    this.mapWidth = mapWidth;
    setMapWidthIsSet(true);
    this.mapHeight = mapHeight;
    setMapHeightIsSet(true);
    this.progressAxis = progressAxis;
    this.myCountry = myCountry;
    this.othersCountry = othersCountry;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public InitData(InitData other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetBases()) {
      List<BaseInitData> __this__bases = new ArrayList<BaseInitData>(other.bases.size());
      for (BaseInitData other_element : other.bases) {
        __this__bases.add(new BaseInitData(other_element));
      }
      this.bases = __this__bases;
    }
    this.mapWidth = other.mapWidth;
    this.mapHeight = other.mapHeight;
    if (other.isSetProgressAxis()) {
      List<ProgressAxisInitData> __this__progressAxis = new ArrayList<ProgressAxisInitData>(other.progressAxis.size());
      for (ProgressAxisInitData other_element : other.progressAxis) {
        __this__progressAxis.add(new ProgressAxisInitData(other_element));
      }
      this.progressAxis = __this__progressAxis;
    }
    if (other.isSetMyCountry()) {
      this.myCountry = new CountryInitData(other.myCountry);
    }
    if (other.isSetOthersCountry()) {
      List<CountryInitData> __this__othersCountry = new ArrayList<CountryInitData>(other.othersCountry.size());
      for (CountryInitData other_element : other.othersCountry) {
        __this__othersCountry.add(new CountryInitData(other_element));
      }
      this.othersCountry = __this__othersCountry;
    }
  }

  public InitData deepCopy() {
    return new InitData(this);
  }

  @Override
  public void clear() {
    this.bases = null;
    setMapWidthIsSet(false);
    this.mapWidth = 0.0;
    setMapHeightIsSet(false);
    this.mapHeight = 0.0;
    this.progressAxis = null;
    this.myCountry = null;
    this.othersCountry = null;
  }

  public int getBasesSize() {
    return (this.bases == null) ? 0 : this.bases.size();
  }

  public java.util.Iterator<BaseInitData> getBasesIterator() {
    return (this.bases == null) ? null : this.bases.iterator();
  }

  public void addToBases(BaseInitData elem) {
    if (this.bases == null) {
      this.bases = new ArrayList<BaseInitData>();
    }
    this.bases.add(elem);
  }

  public List<BaseInitData> getBases() {
    return this.bases;
  }

  public InitData setBases(List<BaseInitData> bases) {
    this.bases = bases;
    return this;
  }

  public void unsetBases() {
    this.bases = null;
  }

  /** Returns true if field bases is set (has been assigned a value) and false otherwise */
  public boolean isSetBases() {
    return this.bases != null;
  }

  public void setBasesIsSet(boolean value) {
    if (!value) {
      this.bases = null;
    }
  }

  public double getMapWidth() {
    return this.mapWidth;
  }

  public InitData setMapWidth(double mapWidth) {
    this.mapWidth = mapWidth;
    setMapWidthIsSet(true);
    return this;
  }

  public void unsetMapWidth() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MAPWIDTH_ISSET_ID);
  }

  /** Returns true if field mapWidth is set (has been assigned a value) and false otherwise */
  public boolean isSetMapWidth() {
    return EncodingUtils.testBit(__isset_bitfield, __MAPWIDTH_ISSET_ID);
  }

  public void setMapWidthIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MAPWIDTH_ISSET_ID, value);
  }

  public double getMapHeight() {
    return this.mapHeight;
  }

  public InitData setMapHeight(double mapHeight) {
    this.mapHeight = mapHeight;
    setMapHeightIsSet(true);
    return this;
  }

  public void unsetMapHeight() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MAPHEIGHT_ISSET_ID);
  }

  /** Returns true if field mapHeight is set (has been assigned a value) and false otherwise */
  public boolean isSetMapHeight() {
    return EncodingUtils.testBit(__isset_bitfield, __MAPHEIGHT_ISSET_ID);
  }

  public void setMapHeightIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MAPHEIGHT_ISSET_ID, value);
  }

  public int getProgressAxisSize() {
    return (this.progressAxis == null) ? 0 : this.progressAxis.size();
  }

  public java.util.Iterator<ProgressAxisInitData> getProgressAxisIterator() {
    return (this.progressAxis == null) ? null : this.progressAxis.iterator();
  }

  public void addToProgressAxis(ProgressAxisInitData elem) {
    if (this.progressAxis == null) {
      this.progressAxis = new ArrayList<ProgressAxisInitData>();
    }
    this.progressAxis.add(elem);
  }

  public List<ProgressAxisInitData> getProgressAxis() {
    return this.progressAxis;
  }

  public InitData setProgressAxis(List<ProgressAxisInitData> progressAxis) {
    this.progressAxis = progressAxis;
    return this;
  }

  public void unsetProgressAxis() {
    this.progressAxis = null;
  }

  /** Returns true if field progressAxis is set (has been assigned a value) and false otherwise */
  public boolean isSetProgressAxis() {
    return this.progressAxis != null;
  }

  public void setProgressAxisIsSet(boolean value) {
    if (!value) {
      this.progressAxis = null;
    }
  }

  public CountryInitData getMyCountry() {
    return this.myCountry;
  }

  public InitData setMyCountry(CountryInitData myCountry) {
    this.myCountry = myCountry;
    return this;
  }

  public void unsetMyCountry() {
    this.myCountry = null;
  }

  /** Returns true if field myCountry is set (has been assigned a value) and false otherwise */
  public boolean isSetMyCountry() {
    return this.myCountry != null;
  }

  public void setMyCountryIsSet(boolean value) {
    if (!value) {
      this.myCountry = null;
    }
  }

  public int getOthersCountrySize() {
    return (this.othersCountry == null) ? 0 : this.othersCountry.size();
  }

  public java.util.Iterator<CountryInitData> getOthersCountryIterator() {
    return (this.othersCountry == null) ? null : this.othersCountry.iterator();
  }

  public void addToOthersCountry(CountryInitData elem) {
    if (this.othersCountry == null) {
      this.othersCountry = new ArrayList<CountryInitData>();
    }
    this.othersCountry.add(elem);
  }

  public List<CountryInitData> getOthersCountry() {
    return this.othersCountry;
  }

  public InitData setOthersCountry(List<CountryInitData> othersCountry) {
    this.othersCountry = othersCountry;
    return this;
  }

  public void unsetOthersCountry() {
    this.othersCountry = null;
  }

  /** Returns true if field othersCountry is set (has been assigned a value) and false otherwise */
  public boolean isSetOthersCountry() {
    return this.othersCountry != null;
  }

  public void setOthersCountryIsSet(boolean value) {
    if (!value) {
      this.othersCountry = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BASES:
      if (value == null) {
        unsetBases();
      } else {
        setBases((List<BaseInitData>)value);
      }
      break;

    case MAP_WIDTH:
      if (value == null) {
        unsetMapWidth();
      } else {
        setMapWidth((Double)value);
      }
      break;

    case MAP_HEIGHT:
      if (value == null) {
        unsetMapHeight();
      } else {
        setMapHeight((Double)value);
      }
      break;

    case PROGRESS_AXIS:
      if (value == null) {
        unsetProgressAxis();
      } else {
        setProgressAxis((List<ProgressAxisInitData>)value);
      }
      break;

    case MY_COUNTRY:
      if (value == null) {
        unsetMyCountry();
      } else {
        setMyCountry((CountryInitData)value);
      }
      break;

    case OTHERS_COUNTRY:
      if (value == null) {
        unsetOthersCountry();
      } else {
        setOthersCountry((List<CountryInitData>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BASES:
      return getBases();

    case MAP_WIDTH:
      return Double.valueOf(getMapWidth());

    case MAP_HEIGHT:
      return Double.valueOf(getMapHeight());

    case PROGRESS_AXIS:
      return getProgressAxis();

    case MY_COUNTRY:
      return getMyCountry();

    case OTHERS_COUNTRY:
      return getOthersCountry();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BASES:
      return isSetBases();
    case MAP_WIDTH:
      return isSetMapWidth();
    case MAP_HEIGHT:
      return isSetMapHeight();
    case PROGRESS_AXIS:
      return isSetProgressAxis();
    case MY_COUNTRY:
      return isSetMyCountry();
    case OTHERS_COUNTRY:
      return isSetOthersCountry();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof InitData)
      return this.equals((InitData)that);
    return false;
  }

  public boolean equals(InitData that) {
    if (that == null)
      return false;

    boolean this_present_bases = true && this.isSetBases();
    boolean that_present_bases = true && that.isSetBases();
    if (this_present_bases || that_present_bases) {
      if (!(this_present_bases && that_present_bases))
        return false;
      if (!this.bases.equals(that.bases))
        return false;
    }

    boolean this_present_mapWidth = true;
    boolean that_present_mapWidth = true;
    if (this_present_mapWidth || that_present_mapWidth) {
      if (!(this_present_mapWidth && that_present_mapWidth))
        return false;
      if (this.mapWidth != that.mapWidth)
        return false;
    }

    boolean this_present_mapHeight = true;
    boolean that_present_mapHeight = true;
    if (this_present_mapHeight || that_present_mapHeight) {
      if (!(this_present_mapHeight && that_present_mapHeight))
        return false;
      if (this.mapHeight != that.mapHeight)
        return false;
    }

    boolean this_present_progressAxis = true && this.isSetProgressAxis();
    boolean that_present_progressAxis = true && that.isSetProgressAxis();
    if (this_present_progressAxis || that_present_progressAxis) {
      if (!(this_present_progressAxis && that_present_progressAxis))
        return false;
      if (!this.progressAxis.equals(that.progressAxis))
        return false;
    }

    boolean this_present_myCountry = true && this.isSetMyCountry();
    boolean that_present_myCountry = true && that.isSetMyCountry();
    if (this_present_myCountry || that_present_myCountry) {
      if (!(this_present_myCountry && that_present_myCountry))
        return false;
      if (!this.myCountry.equals(that.myCountry))
        return false;
    }

    boolean this_present_othersCountry = true && this.isSetOthersCountry();
    boolean that_present_othersCountry = true && that.isSetOthersCountry();
    if (this_present_othersCountry || that_present_othersCountry) {
      if (!(this_present_othersCountry && that_present_othersCountry))
        return false;
      if (!this.othersCountry.equals(that.othersCountry))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(InitData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetBases()).compareTo(other.isSetBases());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBases()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bases, other.bases);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMapWidth()).compareTo(other.isSetMapWidth());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMapWidth()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mapWidth, other.mapWidth);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMapHeight()).compareTo(other.isSetMapHeight());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMapHeight()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mapHeight, other.mapHeight);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProgressAxis()).compareTo(other.isSetProgressAxis());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProgressAxis()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.progressAxis, other.progressAxis);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMyCountry()).compareTo(other.isSetMyCountry());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMyCountry()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.myCountry, other.myCountry);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOthersCountry()).compareTo(other.isSetOthersCountry());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOthersCountry()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.othersCountry, other.othersCountry);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("InitData(");
    boolean first = true;

    sb.append("bases:");
    if (this.bases == null) {
      sb.append("null");
    } else {
      sb.append(this.bases);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("mapWidth:");
    sb.append(this.mapWidth);
    first = false;
    if (!first) sb.append(", ");
    sb.append("mapHeight:");
    sb.append(this.mapHeight);
    first = false;
    if (!first) sb.append(", ");
    sb.append("progressAxis:");
    if (this.progressAxis == null) {
      sb.append("null");
    } else {
      sb.append(this.progressAxis);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("myCountry:");
    if (this.myCountry == null) {
      sb.append("null");
    } else {
      sb.append(this.myCountry);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("othersCountry:");
    if (this.othersCountry == null) {
      sb.append("null");
    } else {
      sb.append(this.othersCountry);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (myCountry != null) {
      myCountry.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class InitDataStandardSchemeFactory implements SchemeFactory {
    public InitDataStandardScheme getScheme() {
      return new InitDataStandardScheme();
    }
  }

  private static class InitDataStandardScheme extends StandardScheme<InitData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, InitData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BASES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.bases = new ArrayList<BaseInitData>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  BaseInitData _elem10;
                  _elem10 = new BaseInitData();
                  _elem10.read(iprot);
                  struct.bases.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setBasesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MAP_WIDTH
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.mapWidth = iprot.readDouble();
              struct.setMapWidthIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MAP_HEIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.mapHeight = iprot.readDouble();
              struct.setMapHeightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PROGRESS_AXIS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list11 = iprot.readListBegin();
                struct.progressAxis = new ArrayList<ProgressAxisInitData>(_list11.size);
                for (int _i12 = 0; _i12 < _list11.size; ++_i12)
                {
                  ProgressAxisInitData _elem13;
                  _elem13 = new ProgressAxisInitData();
                  _elem13.read(iprot);
                  struct.progressAxis.add(_elem13);
                }
                iprot.readListEnd();
              }
              struct.setProgressAxisIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MY_COUNTRY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.myCountry = new CountryInitData();
              struct.myCountry.read(iprot);
              struct.setMyCountryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // OTHERS_COUNTRY
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list14 = iprot.readListBegin();
                struct.othersCountry = new ArrayList<CountryInitData>(_list14.size);
                for (int _i15 = 0; _i15 < _list14.size; ++_i15)
                {
                  CountryInitData _elem16;
                  _elem16 = new CountryInitData();
                  _elem16.read(iprot);
                  struct.othersCountry.add(_elem16);
                }
                iprot.readListEnd();
              }
              struct.setOthersCountryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, InitData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.bases != null) {
        oprot.writeFieldBegin(BASES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.bases.size()));
          for (BaseInitData _iter17 : struct.bases)
          {
            _iter17.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(MAP_WIDTH_FIELD_DESC);
      oprot.writeDouble(struct.mapWidth);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MAP_HEIGHT_FIELD_DESC);
      oprot.writeDouble(struct.mapHeight);
      oprot.writeFieldEnd();
      if (struct.progressAxis != null) {
        oprot.writeFieldBegin(PROGRESS_AXIS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.progressAxis.size()));
          for (ProgressAxisInitData _iter18 : struct.progressAxis)
          {
            _iter18.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.myCountry != null) {
        oprot.writeFieldBegin(MY_COUNTRY_FIELD_DESC);
        struct.myCountry.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.othersCountry != null) {
        oprot.writeFieldBegin(OTHERS_COUNTRY_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.othersCountry.size()));
          for (CountryInitData _iter19 : struct.othersCountry)
          {
            _iter19.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class InitDataTupleSchemeFactory implements SchemeFactory {
    public InitDataTupleScheme getScheme() {
      return new InitDataTupleScheme();
    }
  }

  private static class InitDataTupleScheme extends TupleScheme<InitData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, InitData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBases()) {
        optionals.set(0);
      }
      if (struct.isSetMapWidth()) {
        optionals.set(1);
      }
      if (struct.isSetMapHeight()) {
        optionals.set(2);
      }
      if (struct.isSetProgressAxis()) {
        optionals.set(3);
      }
      if (struct.isSetMyCountry()) {
        optionals.set(4);
      }
      if (struct.isSetOthersCountry()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetBases()) {
        {
          oprot.writeI32(struct.bases.size());
          for (BaseInitData _iter20 : struct.bases)
          {
            _iter20.write(oprot);
          }
        }
      }
      if (struct.isSetMapWidth()) {
        oprot.writeDouble(struct.mapWidth);
      }
      if (struct.isSetMapHeight()) {
        oprot.writeDouble(struct.mapHeight);
      }
      if (struct.isSetProgressAxis()) {
        {
          oprot.writeI32(struct.progressAxis.size());
          for (ProgressAxisInitData _iter21 : struct.progressAxis)
          {
            _iter21.write(oprot);
          }
        }
      }
      if (struct.isSetMyCountry()) {
        struct.myCountry.write(oprot);
      }
      if (struct.isSetOthersCountry()) {
        {
          oprot.writeI32(struct.othersCountry.size());
          for (CountryInitData _iter22 : struct.othersCountry)
          {
            _iter22.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, InitData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list23 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.bases = new ArrayList<BaseInitData>(_list23.size);
          for (int _i24 = 0; _i24 < _list23.size; ++_i24)
          {
            BaseInitData _elem25;
            _elem25 = new BaseInitData();
            _elem25.read(iprot);
            struct.bases.add(_elem25);
          }
        }
        struct.setBasesIsSet(true);
      }
      if (incoming.get(1)) {
        struct.mapWidth = iprot.readDouble();
        struct.setMapWidthIsSet(true);
      }
      if (incoming.get(2)) {
        struct.mapHeight = iprot.readDouble();
        struct.setMapHeightIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list26 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.progressAxis = new ArrayList<ProgressAxisInitData>(_list26.size);
          for (int _i27 = 0; _i27 < _list26.size; ++_i27)
          {
            ProgressAxisInitData _elem28;
            _elem28 = new ProgressAxisInitData();
            _elem28.read(iprot);
            struct.progressAxis.add(_elem28);
          }
        }
        struct.setProgressAxisIsSet(true);
      }
      if (incoming.get(4)) {
        struct.myCountry = new CountryInitData();
        struct.myCountry.read(iprot);
        struct.setMyCountryIsSet(true);
      }
      if (incoming.get(5)) {
        {
          org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.othersCountry = new ArrayList<CountryInitData>(_list29.size);
          for (int _i30 = 0; _i30 < _list29.size; ++_i30)
          {
            CountryInitData _elem31;
            _elem31 = new CountryInitData();
            _elem31.read(iprot);
            struct.othersCountry.add(_elem31);
          }
        }
        struct.setOthersCountryIsSet(true);
      }
    }
  }

}

