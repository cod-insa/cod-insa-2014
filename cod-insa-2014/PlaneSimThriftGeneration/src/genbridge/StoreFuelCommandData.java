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

public class StoreFuelCommandData implements org.apache.thrift.TBase<StoreFuelCommandData, StoreFuelCommandData._Fields>, java.io.Serializable, Cloneable, Comparable<StoreFuelCommandData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StoreFuelCommandData");

  private static final org.apache.thrift.protocol.TField PC_FIELD_DESC = new org.apache.thrift.protocol.TField("pc", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField QUANTITY_FIELD_DESC = new org.apache.thrift.protocol.TField("quantity", org.apache.thrift.protocol.TType.DOUBLE, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StoreFuelCommandDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StoreFuelCommandDataTupleSchemeFactory());
  }

  public PlaneCommandData pc; // required
  public double quantity; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PC((short)1, "pc"),
    QUANTITY((short)2, "quantity");

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
        case 1: // PC
          return PC;
        case 2: // QUANTITY
          return QUANTITY;
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
  private static final int __QUANTITY_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PC, new org.apache.thrift.meta_data.FieldMetaData("pc", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PlaneCommandData.class)));
    tmpMap.put(_Fields.QUANTITY, new org.apache.thrift.meta_data.FieldMetaData("quantity", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StoreFuelCommandData.class, metaDataMap);
  }

  public StoreFuelCommandData() {
  }

  public StoreFuelCommandData(
    PlaneCommandData pc,
    double quantity)
  {
    this();
    this.pc = pc;
    this.quantity = quantity;
    setQuantityIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StoreFuelCommandData(StoreFuelCommandData other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPc()) {
      this.pc = new PlaneCommandData(other.pc);
    }
    this.quantity = other.quantity;
  }

  public StoreFuelCommandData deepCopy() {
    return new StoreFuelCommandData(this);
  }

  @Override
  public void clear() {
    this.pc = null;
    setQuantityIsSet(false);
    this.quantity = 0.0;
  }

  public PlaneCommandData getPc() {
    return this.pc;
  }

  public StoreFuelCommandData setPc(PlaneCommandData pc) {
    this.pc = pc;
    return this;
  }

  public void unsetPc() {
    this.pc = null;
  }

  /** Returns true if field pc is set (has been assigned a value) and false otherwise */
  public boolean isSetPc() {
    return this.pc != null;
  }

  public void setPcIsSet(boolean value) {
    if (!value) {
      this.pc = null;
    }
  }

  public double getQuantity() {
    return this.quantity;
  }

  public StoreFuelCommandData setQuantity(double quantity) {
    this.quantity = quantity;
    setQuantityIsSet(true);
    return this;
  }

  public void unsetQuantity() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __QUANTITY_ISSET_ID);
  }

  /** Returns true if field quantity is set (has been assigned a value) and false otherwise */
  public boolean isSetQuantity() {
    return EncodingUtils.testBit(__isset_bitfield, __QUANTITY_ISSET_ID);
  }

  public void setQuantityIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __QUANTITY_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PC:
      if (value == null) {
        unsetPc();
      } else {
        setPc((PlaneCommandData)value);
      }
      break;

    case QUANTITY:
      if (value == null) {
        unsetQuantity();
      } else {
        setQuantity((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PC:
      return getPc();

    case QUANTITY:
      return Double.valueOf(getQuantity());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PC:
      return isSetPc();
    case QUANTITY:
      return isSetQuantity();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StoreFuelCommandData)
      return this.equals((StoreFuelCommandData)that);
    return false;
  }

  public boolean equals(StoreFuelCommandData that) {
    if (that == null)
      return false;

    boolean this_present_pc = true && this.isSetPc();
    boolean that_present_pc = true && that.isSetPc();
    if (this_present_pc || that_present_pc) {
      if (!(this_present_pc && that_present_pc))
        return false;
      if (!this.pc.equals(that.pc))
        return false;
    }

    boolean this_present_quantity = true;
    boolean that_present_quantity = true;
    if (this_present_quantity || that_present_quantity) {
      if (!(this_present_quantity && that_present_quantity))
        return false;
      if (this.quantity != that.quantity)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(StoreFuelCommandData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPc()).compareTo(other.isSetPc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pc, other.pc);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetQuantity()).compareTo(other.isSetQuantity());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQuantity()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.quantity, other.quantity);
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
    StringBuilder sb = new StringBuilder("StoreFuelCommandData(");
    boolean first = true;

    sb.append("pc:");
    if (this.pc == null) {
      sb.append("null");
    } else {
      sb.append(this.pc);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("quantity:");
    sb.append(this.quantity);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (pc != null) {
      pc.validate();
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

  private static class StoreFuelCommandDataStandardSchemeFactory implements SchemeFactory {
    public StoreFuelCommandDataStandardScheme getScheme() {
      return new StoreFuelCommandDataStandardScheme();
    }
  }

  private static class StoreFuelCommandDataStandardScheme extends StandardScheme<StoreFuelCommandData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StoreFuelCommandData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.pc = new PlaneCommandData();
              struct.pc.read(iprot);
              struct.setPcIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // QUANTITY
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.quantity = iprot.readDouble();
              struct.setQuantityIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StoreFuelCommandData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pc != null) {
        oprot.writeFieldBegin(PC_FIELD_DESC);
        struct.pc.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(QUANTITY_FIELD_DESC);
      oprot.writeDouble(struct.quantity);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StoreFuelCommandDataTupleSchemeFactory implements SchemeFactory {
    public StoreFuelCommandDataTupleScheme getScheme() {
      return new StoreFuelCommandDataTupleScheme();
    }
  }

  private static class StoreFuelCommandDataTupleScheme extends TupleScheme<StoreFuelCommandData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StoreFuelCommandData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPc()) {
        optionals.set(0);
      }
      if (struct.isSetQuantity()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPc()) {
        struct.pc.write(oprot);
      }
      if (struct.isSetQuantity()) {
        oprot.writeDouble(struct.quantity);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StoreFuelCommandData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.pc = new PlaneCommandData();
        struct.pc.read(iprot);
        struct.setPcIsSet(true);
      }
      if (incoming.get(1)) {
        struct.quantity = iprot.readDouble();
        struct.setQuantityIsSet(true);
      }
    }
  }

}

