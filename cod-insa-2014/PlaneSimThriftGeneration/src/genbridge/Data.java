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

public class Data implements org.apache.thrift.TBase<Data, Data._Fields>, java.io.Serializable, Cloneable, Comparable<Data> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Data");

  private static final org.apache.thrift.protocol.TField NUM_FRAME_FIELD_DESC = new org.apache.thrift.protocol.TField("numFrame", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PLANES_FIELD_DESC = new org.apache.thrift.protocol.TField("planes", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField BASES_FIELD_DESC = new org.apache.thrift.protocol.TField("bases", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField PROGRESS_AXIS_FIELD_DESC = new org.apache.thrift.protocol.TField("progressAxis", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField MY_COUNTRY_FIELD_DESC = new org.apache.thrift.protocol.TField("myCountry", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField CURRENT_MONEY_FIELD_DESC = new org.apache.thrift.protocol.TField("currentMoney", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DataTupleSchemeFactory());
  }

  public int numFrame; // required
  public List<PlaneData> planes; // required
  public List<BaseData> bases; // required
  public List<ProgressAxisData> progressAxis; // required
  public CountryData myCountry; // required
  public int currentMoney; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NUM_FRAME((short)1, "numFrame"),
    PLANES((short)2, "planes"),
    BASES((short)3, "bases"),
    PROGRESS_AXIS((short)4, "progressAxis"),
    MY_COUNTRY((short)5, "myCountry"),
    CURRENT_MONEY((short)6, "currentMoney");

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
        case 1: // NUM_FRAME
          return NUM_FRAME;
        case 2: // PLANES
          return PLANES;
        case 3: // BASES
          return BASES;
        case 4: // PROGRESS_AXIS
          return PROGRESS_AXIS;
        case 5: // MY_COUNTRY
          return MY_COUNTRY;
        case 6: // CURRENT_MONEY
          return CURRENT_MONEY;
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
  private static final int __NUMFRAME_ISSET_ID = 0;
  private static final int __CURRENTMONEY_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NUM_FRAME, new org.apache.thrift.meta_data.FieldMetaData("numFrame", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.PLANES, new org.apache.thrift.meta_data.FieldMetaData("planes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PlaneData.class))));
    tmpMap.put(_Fields.BASES, new org.apache.thrift.meta_data.FieldMetaData("bases", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, BaseData.class))));
    tmpMap.put(_Fields.PROGRESS_AXIS, new org.apache.thrift.meta_data.FieldMetaData("progressAxis", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ProgressAxisData.class))));
    tmpMap.put(_Fields.MY_COUNTRY, new org.apache.thrift.meta_data.FieldMetaData("myCountry", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CountryData.class)));
    tmpMap.put(_Fields.CURRENT_MONEY, new org.apache.thrift.meta_data.FieldMetaData("currentMoney", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Data.class, metaDataMap);
  }

  public Data() {
  }

  public Data(
    int numFrame,
    List<PlaneData> planes,
    List<BaseData> bases,
    List<ProgressAxisData> progressAxis,
    CountryData myCountry,
    int currentMoney)
  {
    this();
    this.numFrame = numFrame;
    setNumFrameIsSet(true);
    this.planes = planes;
    this.bases = bases;
    this.progressAxis = progressAxis;
    this.myCountry = myCountry;
    this.currentMoney = currentMoney;
    setCurrentMoneyIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Data(Data other) {
    __isset_bitfield = other.__isset_bitfield;
    this.numFrame = other.numFrame;
    if (other.isSetPlanes()) {
      List<PlaneData> __this__planes = new ArrayList<PlaneData>(other.planes.size());
      for (PlaneData other_element : other.planes) {
        __this__planes.add(new PlaneData(other_element));
      }
      this.planes = __this__planes;
    }
    if (other.isSetBases()) {
      List<BaseData> __this__bases = new ArrayList<BaseData>(other.bases.size());
      for (BaseData other_element : other.bases) {
        __this__bases.add(new BaseData(other_element));
      }
      this.bases = __this__bases;
    }
    if (other.isSetProgressAxis()) {
      List<ProgressAxisData> __this__progressAxis = new ArrayList<ProgressAxisData>(other.progressAxis.size());
      for (ProgressAxisData other_element : other.progressAxis) {
        __this__progressAxis.add(new ProgressAxisData(other_element));
      }
      this.progressAxis = __this__progressAxis;
    }
    if (other.isSetMyCountry()) {
      this.myCountry = new CountryData(other.myCountry);
    }
    this.currentMoney = other.currentMoney;
  }

  public Data deepCopy() {
    return new Data(this);
  }

  @Override
  public void clear() {
    setNumFrameIsSet(false);
    this.numFrame = 0;
    this.planes = null;
    this.bases = null;
    this.progressAxis = null;
    this.myCountry = null;
    setCurrentMoneyIsSet(false);
    this.currentMoney = 0;
  }

  public int getNumFrame() {
    return this.numFrame;
  }

  public Data setNumFrame(int numFrame) {
    this.numFrame = numFrame;
    setNumFrameIsSet(true);
    return this;
  }

  public void unsetNumFrame() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NUMFRAME_ISSET_ID);
  }

  /** Returns true if field numFrame is set (has been assigned a value) and false otherwise */
  public boolean isSetNumFrame() {
    return EncodingUtils.testBit(__isset_bitfield, __NUMFRAME_ISSET_ID);
  }

  public void setNumFrameIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NUMFRAME_ISSET_ID, value);
  }

  public int getPlanesSize() {
    return (this.planes == null) ? 0 : this.planes.size();
  }

  public java.util.Iterator<PlaneData> getPlanesIterator() {
    return (this.planes == null) ? null : this.planes.iterator();
  }

  public void addToPlanes(PlaneData elem) {
    if (this.planes == null) {
      this.planes = new ArrayList<PlaneData>();
    }
    this.planes.add(elem);
  }

  public List<PlaneData> getPlanes() {
    return this.planes;
  }

  public Data setPlanes(List<PlaneData> planes) {
    this.planes = planes;
    return this;
  }

  public void unsetPlanes() {
    this.planes = null;
  }

  /** Returns true if field planes is set (has been assigned a value) and false otherwise */
  public boolean isSetPlanes() {
    return this.planes != null;
  }

  public void setPlanesIsSet(boolean value) {
    if (!value) {
      this.planes = null;
    }
  }

  public int getBasesSize() {
    return (this.bases == null) ? 0 : this.bases.size();
  }

  public java.util.Iterator<BaseData> getBasesIterator() {
    return (this.bases == null) ? null : this.bases.iterator();
  }

  public void addToBases(BaseData elem) {
    if (this.bases == null) {
      this.bases = new ArrayList<BaseData>();
    }
    this.bases.add(elem);
  }

  public List<BaseData> getBases() {
    return this.bases;
  }

  public Data setBases(List<BaseData> bases) {
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

  public int getProgressAxisSize() {
    return (this.progressAxis == null) ? 0 : this.progressAxis.size();
  }

  public java.util.Iterator<ProgressAxisData> getProgressAxisIterator() {
    return (this.progressAxis == null) ? null : this.progressAxis.iterator();
  }

  public void addToProgressAxis(ProgressAxisData elem) {
    if (this.progressAxis == null) {
      this.progressAxis = new ArrayList<ProgressAxisData>();
    }
    this.progressAxis.add(elem);
  }

  public List<ProgressAxisData> getProgressAxis() {
    return this.progressAxis;
  }

  public Data setProgressAxis(List<ProgressAxisData> progressAxis) {
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

  public CountryData getMyCountry() {
    return this.myCountry;
  }

  public Data setMyCountry(CountryData myCountry) {
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

  public int getCurrentMoney() {
    return this.currentMoney;
  }

  public Data setCurrentMoney(int currentMoney) {
    this.currentMoney = currentMoney;
    setCurrentMoneyIsSet(true);
    return this;
  }

  public void unsetCurrentMoney() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CURRENTMONEY_ISSET_ID);
  }

  /** Returns true if field currentMoney is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrentMoney() {
    return EncodingUtils.testBit(__isset_bitfield, __CURRENTMONEY_ISSET_ID);
  }

  public void setCurrentMoneyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CURRENTMONEY_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NUM_FRAME:
      if (value == null) {
        unsetNumFrame();
      } else {
        setNumFrame((Integer)value);
      }
      break;

    case PLANES:
      if (value == null) {
        unsetPlanes();
      } else {
        setPlanes((List<PlaneData>)value);
      }
      break;

    case BASES:
      if (value == null) {
        unsetBases();
      } else {
        setBases((List<BaseData>)value);
      }
      break;

    case PROGRESS_AXIS:
      if (value == null) {
        unsetProgressAxis();
      } else {
        setProgressAxis((List<ProgressAxisData>)value);
      }
      break;

    case MY_COUNTRY:
      if (value == null) {
        unsetMyCountry();
      } else {
        setMyCountry((CountryData)value);
      }
      break;

    case CURRENT_MONEY:
      if (value == null) {
        unsetCurrentMoney();
      } else {
        setCurrentMoney((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NUM_FRAME:
      return Integer.valueOf(getNumFrame());

    case PLANES:
      return getPlanes();

    case BASES:
      return getBases();

    case PROGRESS_AXIS:
      return getProgressAxis();

    case MY_COUNTRY:
      return getMyCountry();

    case CURRENT_MONEY:
      return Integer.valueOf(getCurrentMoney());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NUM_FRAME:
      return isSetNumFrame();
    case PLANES:
      return isSetPlanes();
    case BASES:
      return isSetBases();
    case PROGRESS_AXIS:
      return isSetProgressAxis();
    case MY_COUNTRY:
      return isSetMyCountry();
    case CURRENT_MONEY:
      return isSetCurrentMoney();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Data)
      return this.equals((Data)that);
    return false;
  }

  public boolean equals(Data that) {
    if (that == null)
      return false;

    boolean this_present_numFrame = true;
    boolean that_present_numFrame = true;
    if (this_present_numFrame || that_present_numFrame) {
      if (!(this_present_numFrame && that_present_numFrame))
        return false;
      if (this.numFrame != that.numFrame)
        return false;
    }

    boolean this_present_planes = true && this.isSetPlanes();
    boolean that_present_planes = true && that.isSetPlanes();
    if (this_present_planes || that_present_planes) {
      if (!(this_present_planes && that_present_planes))
        return false;
      if (!this.planes.equals(that.planes))
        return false;
    }

    boolean this_present_bases = true && this.isSetBases();
    boolean that_present_bases = true && that.isSetBases();
    if (this_present_bases || that_present_bases) {
      if (!(this_present_bases && that_present_bases))
        return false;
      if (!this.bases.equals(that.bases))
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

    boolean this_present_currentMoney = true;
    boolean that_present_currentMoney = true;
    if (this_present_currentMoney || that_present_currentMoney) {
      if (!(this_present_currentMoney && that_present_currentMoney))
        return false;
      if (this.currentMoney != that.currentMoney)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(Data other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetNumFrame()).compareTo(other.isSetNumFrame());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNumFrame()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.numFrame, other.numFrame);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlanes()).compareTo(other.isSetPlanes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlanes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.planes, other.planes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetCurrentMoney()).compareTo(other.isSetCurrentMoney());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrentMoney()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currentMoney, other.currentMoney);
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
    StringBuilder sb = new StringBuilder("Data(");
    boolean first = true;

    sb.append("numFrame:");
    sb.append(this.numFrame);
    first = false;
    if (!first) sb.append(", ");
    sb.append("planes:");
    if (this.planes == null) {
      sb.append("null");
    } else {
      sb.append(this.planes);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bases:");
    if (this.bases == null) {
      sb.append("null");
    } else {
      sb.append(this.bases);
    }
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
    sb.append("currentMoney:");
    sb.append(this.currentMoney);
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

  private static class DataStandardSchemeFactory implements SchemeFactory {
    public DataStandardScheme getScheme() {
      return new DataStandardScheme();
    }
  }

  private static class DataStandardScheme extends StandardScheme<Data> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Data struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NUM_FRAME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.numFrame = iprot.readI32();
              struct.setNumFrameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PLANES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list32 = iprot.readListBegin();
                struct.planes = new ArrayList<PlaneData>(_list32.size);
                for (int _i33 = 0; _i33 < _list32.size; ++_i33)
                {
                  PlaneData _elem34;
                  _elem34 = new PlaneData();
                  _elem34.read(iprot);
                  struct.planes.add(_elem34);
                }
                iprot.readListEnd();
              }
              struct.setPlanesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BASES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list35 = iprot.readListBegin();
                struct.bases = new ArrayList<BaseData>(_list35.size);
                for (int _i36 = 0; _i36 < _list35.size; ++_i36)
                {
                  BaseData _elem37;
                  _elem37 = new BaseData();
                  _elem37.read(iprot);
                  struct.bases.add(_elem37);
                }
                iprot.readListEnd();
              }
              struct.setBasesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PROGRESS_AXIS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list38 = iprot.readListBegin();
                struct.progressAxis = new ArrayList<ProgressAxisData>(_list38.size);
                for (int _i39 = 0; _i39 < _list38.size; ++_i39)
                {
                  ProgressAxisData _elem40;
                  _elem40 = new ProgressAxisData();
                  _elem40.read(iprot);
                  struct.progressAxis.add(_elem40);
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
              struct.myCountry = new CountryData();
              struct.myCountry.read(iprot);
              struct.setMyCountryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CURRENT_MONEY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.currentMoney = iprot.readI32();
              struct.setCurrentMoneyIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Data struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(NUM_FRAME_FIELD_DESC);
      oprot.writeI32(struct.numFrame);
      oprot.writeFieldEnd();
      if (struct.planes != null) {
        oprot.writeFieldBegin(PLANES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.planes.size()));
          for (PlaneData _iter41 : struct.planes)
          {
            _iter41.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.bases != null) {
        oprot.writeFieldBegin(BASES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.bases.size()));
          for (BaseData _iter42 : struct.bases)
          {
            _iter42.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.progressAxis != null) {
        oprot.writeFieldBegin(PROGRESS_AXIS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.progressAxis.size()));
          for (ProgressAxisData _iter43 : struct.progressAxis)
          {
            _iter43.write(oprot);
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
      oprot.writeFieldBegin(CURRENT_MONEY_FIELD_DESC);
      oprot.writeI32(struct.currentMoney);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DataTupleSchemeFactory implements SchemeFactory {
    public DataTupleScheme getScheme() {
      return new DataTupleScheme();
    }
  }

  private static class DataTupleScheme extends TupleScheme<Data> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Data struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetNumFrame()) {
        optionals.set(0);
      }
      if (struct.isSetPlanes()) {
        optionals.set(1);
      }
      if (struct.isSetBases()) {
        optionals.set(2);
      }
      if (struct.isSetProgressAxis()) {
        optionals.set(3);
      }
      if (struct.isSetMyCountry()) {
        optionals.set(4);
      }
      if (struct.isSetCurrentMoney()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetNumFrame()) {
        oprot.writeI32(struct.numFrame);
      }
      if (struct.isSetPlanes()) {
        {
          oprot.writeI32(struct.planes.size());
          for (PlaneData _iter44 : struct.planes)
          {
            _iter44.write(oprot);
          }
        }
      }
      if (struct.isSetBases()) {
        {
          oprot.writeI32(struct.bases.size());
          for (BaseData _iter45 : struct.bases)
          {
            _iter45.write(oprot);
          }
        }
      }
      if (struct.isSetProgressAxis()) {
        {
          oprot.writeI32(struct.progressAxis.size());
          for (ProgressAxisData _iter46 : struct.progressAxis)
          {
            _iter46.write(oprot);
          }
        }
      }
      if (struct.isSetMyCountry()) {
        struct.myCountry.write(oprot);
      }
      if (struct.isSetCurrentMoney()) {
        oprot.writeI32(struct.currentMoney);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Data struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.numFrame = iprot.readI32();
        struct.setNumFrameIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list47 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.planes = new ArrayList<PlaneData>(_list47.size);
          for (int _i48 = 0; _i48 < _list47.size; ++_i48)
          {
            PlaneData _elem49;
            _elem49 = new PlaneData();
            _elem49.read(iprot);
            struct.planes.add(_elem49);
          }
        }
        struct.setPlanesIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list50 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.bases = new ArrayList<BaseData>(_list50.size);
          for (int _i51 = 0; _i51 < _list50.size; ++_i51)
          {
            BaseData _elem52;
            _elem52 = new BaseData();
            _elem52.read(iprot);
            struct.bases.add(_elem52);
          }
        }
        struct.setBasesIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list53 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.progressAxis = new ArrayList<ProgressAxisData>(_list53.size);
          for (int _i54 = 0; _i54 < _list53.size; ++_i54)
          {
            ProgressAxisData _elem55;
            _elem55 = new ProgressAxisData();
            _elem55.read(iprot);
            struct.progressAxis.add(_elem55);
          }
        }
        struct.setProgressAxisIsSet(true);
      }
      if (incoming.get(4)) {
        struct.myCountry = new CountryData();
        struct.myCountry.read(iprot);
        struct.setMyCountryIsSet(true);
      }
      if (incoming.get(5)) {
        struct.currentMoney = iprot.readI32();
        struct.setCurrentMoneyIsSet(true);
      }
    }
  }

}

