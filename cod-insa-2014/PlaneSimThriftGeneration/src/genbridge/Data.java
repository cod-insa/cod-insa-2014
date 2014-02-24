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

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DataTupleSchemeFactory());
  }

  public int numFrame; // required
  public List<PlaneData> planes; // required
  public List<BaseData> bases; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NUM_FRAME((short)1, "numFrame"),
    PLANES((short)2, "planes"),
    BASES((short)3, "bases");

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
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Data.class, metaDataMap);
  }

  public Data() {
  }

  public Data(
    int numFrame,
    List<PlaneData> planes,
    List<BaseData> bases)
  {
    this();
    this.numFrame = numFrame;
    setNumFrameIsSet(true);
    this.planes = planes;
    this.bases = bases;
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
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.planes = new ArrayList<PlaneData>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  PlaneData _elem2;
                  _elem2 = new PlaneData();
                  _elem2.read(iprot);
                  struct.planes.add(_elem2);
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
                org.apache.thrift.protocol.TList _list3 = iprot.readListBegin();
                struct.bases = new ArrayList<BaseData>(_list3.size);
                for (int _i4 = 0; _i4 < _list3.size; ++_i4)
                {
                  BaseData _elem5;
                  _elem5 = new BaseData();
                  _elem5.read(iprot);
                  struct.bases.add(_elem5);
                }
                iprot.readListEnd();
              }
              struct.setBasesIsSet(true);
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
          for (PlaneData _iter6 : struct.planes)
          {
            _iter6.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.bases != null) {
        oprot.writeFieldBegin(BASES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.bases.size()));
          for (BaseData _iter7 : struct.bases)
          {
            _iter7.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
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
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetNumFrame()) {
        oprot.writeI32(struct.numFrame);
      }
      if (struct.isSetPlanes()) {
        {
          oprot.writeI32(struct.planes.size());
          for (PlaneData _iter8 : struct.planes)
          {
            _iter8.write(oprot);
          }
        }
      }
      if (struct.isSetBases()) {
        {
          oprot.writeI32(struct.bases.size());
          for (BaseData _iter9 : struct.bases)
          {
            _iter9.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Data struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.numFrame = iprot.readI32();
        struct.setNumFrameIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list10 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.planes = new ArrayList<PlaneData>(_list10.size);
          for (int _i11 = 0; _i11 < _list10.size; ++_i11)
          {
            PlaneData _elem12;
            _elem12 = new PlaneData();
            _elem12.read(iprot);
            struct.planes.add(_elem12);
          }
        }
        struct.setPlanesIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.bases = new ArrayList<BaseData>(_list13.size);
          for (int _i14 = 0; _i14 < _list13.size; ++_i14)
          {
            BaseData _elem15;
            _elem15 = new BaseData();
            _elem15.read(iprot);
            struct.bases.add(_elem15);
          }
        }
        struct.setBasesIsSet(true);
      }
    }
  }

}

