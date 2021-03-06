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

public class ExchangeResourcesCommandData implements org.apache.thrift.TBase<ExchangeResourcesCommandData, ExchangeResourcesCommandData._Fields>, java.io.Serializable, Cloneable, Comparable<ExchangeResourcesCommandData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ExchangeResourcesCommandData");

  private static final org.apache.thrift.protocol.TField PC_FIELD_DESC = new org.apache.thrift.protocol.TField("pc", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField MILITAR_QUANTITY_FIELD_DESC = new org.apache.thrift.protocol.TField("militar_quantity", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField FUEL_QUANTITY_FIELD_DESC = new org.apache.thrift.protocol.TField("fuel_quantity", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField DELETE_RESOURCES_FIELD_DESC = new org.apache.thrift.protocol.TField("deleteResources", org.apache.thrift.protocol.TType.BOOL, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ExchangeResourcesCommandDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ExchangeResourcesCommandDataTupleSchemeFactory());
  }

  public PlaneCommandData pc; // required
  public double militar_quantity; // required
  public double fuel_quantity; // required
  public boolean deleteResources; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PC((short)1, "pc"),
    MILITAR_QUANTITY((short)2, "militar_quantity"),
    FUEL_QUANTITY((short)3, "fuel_quantity"),
    DELETE_RESOURCES((short)4, "deleteResources");

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
        case 2: // MILITAR_QUANTITY
          return MILITAR_QUANTITY;
        case 3: // FUEL_QUANTITY
          return FUEL_QUANTITY;
        case 4: // DELETE_RESOURCES
          return DELETE_RESOURCES;
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
  private static final int __MILITAR_QUANTITY_ISSET_ID = 0;
  private static final int __FUEL_QUANTITY_ISSET_ID = 1;
  private static final int __DELETERESOURCES_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PC, new org.apache.thrift.meta_data.FieldMetaData("pc", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PlaneCommandData.class)));
    tmpMap.put(_Fields.MILITAR_QUANTITY, new org.apache.thrift.meta_data.FieldMetaData("militar_quantity", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.FUEL_QUANTITY, new org.apache.thrift.meta_data.FieldMetaData("fuel_quantity", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.DELETE_RESOURCES, new org.apache.thrift.meta_data.FieldMetaData("deleteResources", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ExchangeResourcesCommandData.class, metaDataMap);
  }

  public ExchangeResourcesCommandData() {
  }

  public ExchangeResourcesCommandData(
    PlaneCommandData pc,
    double militar_quantity,
    double fuel_quantity,
    boolean deleteResources)
  {
    this();
    this.pc = pc;
    this.militar_quantity = militar_quantity;
    setMilitar_quantityIsSet(true);
    this.fuel_quantity = fuel_quantity;
    setFuel_quantityIsSet(true);
    this.deleteResources = deleteResources;
    setDeleteResourcesIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ExchangeResourcesCommandData(ExchangeResourcesCommandData other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPc()) {
      this.pc = new PlaneCommandData(other.pc);
    }
    this.militar_quantity = other.militar_quantity;
    this.fuel_quantity = other.fuel_quantity;
    this.deleteResources = other.deleteResources;
  }

  public ExchangeResourcesCommandData deepCopy() {
    return new ExchangeResourcesCommandData(this);
  }

  @Override
  public void clear() {
    this.pc = null;
    setMilitar_quantityIsSet(false);
    this.militar_quantity = 0.0;
    setFuel_quantityIsSet(false);
    this.fuel_quantity = 0.0;
    setDeleteResourcesIsSet(false);
    this.deleteResources = false;
  }

  public PlaneCommandData getPc() {
    return this.pc;
  }

  public ExchangeResourcesCommandData setPc(PlaneCommandData pc) {
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

  public double getMilitar_quantity() {
    return this.militar_quantity;
  }

  public ExchangeResourcesCommandData setMilitar_quantity(double militar_quantity) {
    this.militar_quantity = militar_quantity;
    setMilitar_quantityIsSet(true);
    return this;
  }

  public void unsetMilitar_quantity() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MILITAR_QUANTITY_ISSET_ID);
  }

  /** Returns true if field militar_quantity is set (has been assigned a value) and false otherwise */
  public boolean isSetMilitar_quantity() {
    return EncodingUtils.testBit(__isset_bitfield, __MILITAR_QUANTITY_ISSET_ID);
  }

  public void setMilitar_quantityIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MILITAR_QUANTITY_ISSET_ID, value);
  }

  public double getFuel_quantity() {
    return this.fuel_quantity;
  }

  public ExchangeResourcesCommandData setFuel_quantity(double fuel_quantity) {
    this.fuel_quantity = fuel_quantity;
    setFuel_quantityIsSet(true);
    return this;
  }

  public void unsetFuel_quantity() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FUEL_QUANTITY_ISSET_ID);
  }

  /** Returns true if field fuel_quantity is set (has been assigned a value) and false otherwise */
  public boolean isSetFuel_quantity() {
    return EncodingUtils.testBit(__isset_bitfield, __FUEL_QUANTITY_ISSET_ID);
  }

  public void setFuel_quantityIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FUEL_QUANTITY_ISSET_ID, value);
  }

  public boolean isDeleteResources() {
    return this.deleteResources;
  }

  public ExchangeResourcesCommandData setDeleteResources(boolean deleteResources) {
    this.deleteResources = deleteResources;
    setDeleteResourcesIsSet(true);
    return this;
  }

  public void unsetDeleteResources() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DELETERESOURCES_ISSET_ID);
  }

  /** Returns true if field deleteResources is set (has been assigned a value) and false otherwise */
  public boolean isSetDeleteResources() {
    return EncodingUtils.testBit(__isset_bitfield, __DELETERESOURCES_ISSET_ID);
  }

  public void setDeleteResourcesIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DELETERESOURCES_ISSET_ID, value);
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

    case MILITAR_QUANTITY:
      if (value == null) {
        unsetMilitar_quantity();
      } else {
        setMilitar_quantity((Double)value);
      }
      break;

    case FUEL_QUANTITY:
      if (value == null) {
        unsetFuel_quantity();
      } else {
        setFuel_quantity((Double)value);
      }
      break;

    case DELETE_RESOURCES:
      if (value == null) {
        unsetDeleteResources();
      } else {
        setDeleteResources((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PC:
      return getPc();

    case MILITAR_QUANTITY:
      return Double.valueOf(getMilitar_quantity());

    case FUEL_QUANTITY:
      return Double.valueOf(getFuel_quantity());

    case DELETE_RESOURCES:
      return Boolean.valueOf(isDeleteResources());

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
    case MILITAR_QUANTITY:
      return isSetMilitar_quantity();
    case FUEL_QUANTITY:
      return isSetFuel_quantity();
    case DELETE_RESOURCES:
      return isSetDeleteResources();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ExchangeResourcesCommandData)
      return this.equals((ExchangeResourcesCommandData)that);
    return false;
  }

  public boolean equals(ExchangeResourcesCommandData that) {
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

    boolean this_present_militar_quantity = true;
    boolean that_present_militar_quantity = true;
    if (this_present_militar_quantity || that_present_militar_quantity) {
      if (!(this_present_militar_quantity && that_present_militar_quantity))
        return false;
      if (this.militar_quantity != that.militar_quantity)
        return false;
    }

    boolean this_present_fuel_quantity = true;
    boolean that_present_fuel_quantity = true;
    if (this_present_fuel_quantity || that_present_fuel_quantity) {
      if (!(this_present_fuel_quantity && that_present_fuel_quantity))
        return false;
      if (this.fuel_quantity != that.fuel_quantity)
        return false;
    }

    boolean this_present_deleteResources = true;
    boolean that_present_deleteResources = true;
    if (this_present_deleteResources || that_present_deleteResources) {
      if (!(this_present_deleteResources && that_present_deleteResources))
        return false;
      if (this.deleteResources != that.deleteResources)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ExchangeResourcesCommandData other) {
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
    lastComparison = Boolean.valueOf(isSetMilitar_quantity()).compareTo(other.isSetMilitar_quantity());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMilitar_quantity()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.militar_quantity, other.militar_quantity);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFuel_quantity()).compareTo(other.isSetFuel_quantity());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFuel_quantity()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fuel_quantity, other.fuel_quantity);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDeleteResources()).compareTo(other.isSetDeleteResources());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDeleteResources()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.deleteResources, other.deleteResources);
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
    StringBuilder sb = new StringBuilder("ExchangeResourcesCommandData(");
    boolean first = true;

    sb.append("pc:");
    if (this.pc == null) {
      sb.append("null");
    } else {
      sb.append(this.pc);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("militar_quantity:");
    sb.append(this.militar_quantity);
    first = false;
    if (!first) sb.append(", ");
    sb.append("fuel_quantity:");
    sb.append(this.fuel_quantity);
    first = false;
    if (!first) sb.append(", ");
    sb.append("deleteResources:");
    sb.append(this.deleteResources);
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

  private static class ExchangeResourcesCommandDataStandardSchemeFactory implements SchemeFactory {
    public ExchangeResourcesCommandDataStandardScheme getScheme() {
      return new ExchangeResourcesCommandDataStandardScheme();
    }
  }

  private static class ExchangeResourcesCommandDataStandardScheme extends StandardScheme<ExchangeResourcesCommandData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ExchangeResourcesCommandData struct) throws org.apache.thrift.TException {
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
          case 2: // MILITAR_QUANTITY
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.militar_quantity = iprot.readDouble();
              struct.setMilitar_quantityIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FUEL_QUANTITY
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.fuel_quantity = iprot.readDouble();
              struct.setFuel_quantityIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DELETE_RESOURCES
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.deleteResources = iprot.readBool();
              struct.setDeleteResourcesIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ExchangeResourcesCommandData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pc != null) {
        oprot.writeFieldBegin(PC_FIELD_DESC);
        struct.pc.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(MILITAR_QUANTITY_FIELD_DESC);
      oprot.writeDouble(struct.militar_quantity);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FUEL_QUANTITY_FIELD_DESC);
      oprot.writeDouble(struct.fuel_quantity);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DELETE_RESOURCES_FIELD_DESC);
      oprot.writeBool(struct.deleteResources);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ExchangeResourcesCommandDataTupleSchemeFactory implements SchemeFactory {
    public ExchangeResourcesCommandDataTupleScheme getScheme() {
      return new ExchangeResourcesCommandDataTupleScheme();
    }
  }

  private static class ExchangeResourcesCommandDataTupleScheme extends TupleScheme<ExchangeResourcesCommandData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ExchangeResourcesCommandData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPc()) {
        optionals.set(0);
      }
      if (struct.isSetMilitar_quantity()) {
        optionals.set(1);
      }
      if (struct.isSetFuel_quantity()) {
        optionals.set(2);
      }
      if (struct.isSetDeleteResources()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetPc()) {
        struct.pc.write(oprot);
      }
      if (struct.isSetMilitar_quantity()) {
        oprot.writeDouble(struct.militar_quantity);
      }
      if (struct.isSetFuel_quantity()) {
        oprot.writeDouble(struct.fuel_quantity);
      }
      if (struct.isSetDeleteResources()) {
        oprot.writeBool(struct.deleteResources);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ExchangeResourcesCommandData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.pc = new PlaneCommandData();
        struct.pc.read(iprot);
        struct.setPcIsSet(true);
      }
      if (incoming.get(1)) {
        struct.militar_quantity = iprot.readDouble();
        struct.setMilitar_quantityIsSet(true);
      }
      if (incoming.get(2)) {
        struct.fuel_quantity = iprot.readDouble();
        struct.setFuel_quantityIsSet(true);
      }
      if (incoming.get(3)) {
        struct.deleteResources = iprot.readBool();
        struct.setDeleteResourcesIsSet(true);
      }
    }
  }

}

