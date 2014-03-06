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

public class BaseInitData implements org.apache.thrift.TBase<BaseInitData, BaseInitData._Fields>, java.io.Serializable, Cloneable, Comparable<BaseInitData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BaseInitData");

  private static final org.apache.thrift.protocol.TField BASE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("base_id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField POSIT_FIELD_DESC = new org.apache.thrift.protocol.TField("posit", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BaseInitDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BaseInitDataTupleSchemeFactory());
  }

  public int base_id; // required
  public CoordData posit; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BASE_ID((short)1, "base_id"),
    POSIT((short)2, "posit");

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
        case 1: // BASE_ID
          return BASE_ID;
        case 2: // POSIT
          return POSIT;
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
  private static final int __BASE_ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BASE_ID, new org.apache.thrift.meta_data.FieldMetaData("base_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.POSIT, new org.apache.thrift.meta_data.FieldMetaData("posit", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CoordData.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BaseInitData.class, metaDataMap);
  }

  public BaseInitData() {
  }

  public BaseInitData(
    int base_id,
    CoordData posit)
  {
    this();
    this.base_id = base_id;
    setBase_idIsSet(true);
    this.posit = posit;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BaseInitData(BaseInitData other) {
    __isset_bitfield = other.__isset_bitfield;
    this.base_id = other.base_id;
    if (other.isSetPosit()) {
      this.posit = new CoordData(other.posit);
    }
  }

  public BaseInitData deepCopy() {
    return new BaseInitData(this);
  }

  @Override
  public void clear() {
    setBase_idIsSet(false);
    this.base_id = 0;
    this.posit = null;
  }

  public int getBase_id() {
    return this.base_id;
  }

  public BaseInitData setBase_id(int base_id) {
    this.base_id = base_id;
    setBase_idIsSet(true);
    return this;
  }

  public void unsetBase_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BASE_ID_ISSET_ID);
  }

  /** Returns true if field base_id is set (has been assigned a value) and false otherwise */
  public boolean isSetBase_id() {
    return EncodingUtils.testBit(__isset_bitfield, __BASE_ID_ISSET_ID);
  }

  public void setBase_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BASE_ID_ISSET_ID, value);
  }

  public CoordData getPosit() {
    return this.posit;
  }

  public BaseInitData setPosit(CoordData posit) {
    this.posit = posit;
    return this;
  }

  public void unsetPosit() {
    this.posit = null;
  }

  /** Returns true if field posit is set (has been assigned a value) and false otherwise */
  public boolean isSetPosit() {
    return this.posit != null;
  }

  public void setPositIsSet(boolean value) {
    if (!value) {
      this.posit = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BASE_ID:
      if (value == null) {
        unsetBase_id();
      } else {
        setBase_id((Integer)value);
      }
      break;

    case POSIT:
      if (value == null) {
        unsetPosit();
      } else {
        setPosit((CoordData)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BASE_ID:
      return Integer.valueOf(getBase_id());

    case POSIT:
      return getPosit();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BASE_ID:
      return isSetBase_id();
    case POSIT:
      return isSetPosit();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BaseInitData)
      return this.equals((BaseInitData)that);
    return false;
  }

  public boolean equals(BaseInitData that) {
    if (that == null)
      return false;

    boolean this_present_base_id = true;
    boolean that_present_base_id = true;
    if (this_present_base_id || that_present_base_id) {
      if (!(this_present_base_id && that_present_base_id))
        return false;
      if (this.base_id != that.base_id)
        return false;
    }

    boolean this_present_posit = true && this.isSetPosit();
    boolean that_present_posit = true && that.isSetPosit();
    if (this_present_posit || that_present_posit) {
      if (!(this_present_posit && that_present_posit))
        return false;
      if (!this.posit.equals(that.posit))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(BaseInitData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetBase_id()).compareTo(other.isSetBase_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBase_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.base_id, other.base_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPosit()).compareTo(other.isSetPosit());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPosit()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.posit, other.posit);
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
    StringBuilder sb = new StringBuilder("BaseInitData(");
    boolean first = true;

    sb.append("base_id:");
    sb.append(this.base_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("posit:");
    if (this.posit == null) {
      sb.append("null");
    } else {
      sb.append(this.posit);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (posit != null) {
      posit.validate();
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

  private static class BaseInitDataStandardSchemeFactory implements SchemeFactory {
    public BaseInitDataStandardScheme getScheme() {
      return new BaseInitDataStandardScheme();
    }
  }

  private static class BaseInitDataStandardScheme extends StandardScheme<BaseInitData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BaseInitData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BASE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.base_id = iprot.readI32();
              struct.setBase_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // POSIT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.posit = new CoordData();
              struct.posit.read(iprot);
              struct.setPositIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BaseInitData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(BASE_ID_FIELD_DESC);
      oprot.writeI32(struct.base_id);
      oprot.writeFieldEnd();
      if (struct.posit != null) {
        oprot.writeFieldBegin(POSIT_FIELD_DESC);
        struct.posit.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BaseInitDataTupleSchemeFactory implements SchemeFactory {
    public BaseInitDataTupleScheme getScheme() {
      return new BaseInitDataTupleScheme();
    }
  }

  private static class BaseInitDataTupleScheme extends TupleScheme<BaseInitData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BaseInitData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBase_id()) {
        optionals.set(0);
      }
      if (struct.isSetPosit()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetBase_id()) {
        oprot.writeI32(struct.base_id);
      }
      if (struct.isSetPosit()) {
        struct.posit.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BaseInitData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.base_id = iprot.readI32();
        struct.setBase_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.posit = new CoordData();
        struct.posit.read(iprot);
        struct.setPositIsSet(true);
      }
    }
  }

}

