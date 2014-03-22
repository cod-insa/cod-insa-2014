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

public class BaseData implements org.apache.thrift.TBase<BaseData, BaseData._Fields>, java.io.Serializable, Cloneable, Comparable<BaseData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BaseData");

  private static final org.apache.thrift.protocol.TField BASE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("base_id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField AI_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("ai_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField MILITAR_RESSOURCE_FIELD_DESC = new org.apache.thrift.protocol.TField("militarRessource", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField FUEL_RESSOURCE_FIELD_DESC = new org.apache.thrift.protocol.TField("fuelRessource", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BaseDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BaseDataTupleSchemeFactory());
  }

  public int base_id; // required
  public int ai_id; // required
  public int militarRessource; // required
  public int fuelRessource; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BASE_ID((short)1, "base_id"),
    AI_ID((short)2, "ai_id"),
    MILITAR_RESSOURCE((short)3, "militarRessource"),
    FUEL_RESSOURCE((short)4, "fuelRessource");

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
        case 2: // AI_ID
          return AI_ID;
        case 3: // MILITAR_RESSOURCE
          return MILITAR_RESSOURCE;
        case 4: // FUEL_RESSOURCE
          return FUEL_RESSOURCE;
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
  private static final int __AI_ID_ISSET_ID = 1;
  private static final int __MILITARRESSOURCE_ISSET_ID = 2;
  private static final int __FUELRESSOURCE_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BASE_ID, new org.apache.thrift.meta_data.FieldMetaData("base_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.AI_ID, new org.apache.thrift.meta_data.FieldMetaData("ai_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.MILITAR_RESSOURCE, new org.apache.thrift.meta_data.FieldMetaData("militarRessource", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.FUEL_RESSOURCE, new org.apache.thrift.meta_data.FieldMetaData("fuelRessource", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BaseData.class, metaDataMap);
  }

  public BaseData() {
  }

  public BaseData(
    int base_id,
    int ai_id,
    int militarRessource,
    int fuelRessource)
  {
    this();
    this.base_id = base_id;
    setBase_idIsSet(true);
    this.ai_id = ai_id;
    setAi_idIsSet(true);
    this.militarRessource = militarRessource;
    setMilitarRessourceIsSet(true);
    this.fuelRessource = fuelRessource;
    setFuelRessourceIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BaseData(BaseData other) {
    __isset_bitfield = other.__isset_bitfield;
    this.base_id = other.base_id;
    this.ai_id = other.ai_id;
    this.militarRessource = other.militarRessource;
    this.fuelRessource = other.fuelRessource;
  }

  public BaseData deepCopy() {
    return new BaseData(this);
  }

  @Override
  public void clear() {
    setBase_idIsSet(false);
    this.base_id = 0;
    setAi_idIsSet(false);
    this.ai_id = 0;
    setMilitarRessourceIsSet(false);
    this.militarRessource = 0;
    setFuelRessourceIsSet(false);
    this.fuelRessource = 0;
  }

  public int getBase_id() {
    return this.base_id;
  }

  public BaseData setBase_id(int base_id) {
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

  public int getAi_id() {
    return this.ai_id;
  }

  public BaseData setAi_id(int ai_id) {
    this.ai_id = ai_id;
    setAi_idIsSet(true);
    return this;
  }

  public void unsetAi_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __AI_ID_ISSET_ID);
  }

  /** Returns true if field ai_id is set (has been assigned a value) and false otherwise */
  public boolean isSetAi_id() {
    return EncodingUtils.testBit(__isset_bitfield, __AI_ID_ISSET_ID);
  }

  public void setAi_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __AI_ID_ISSET_ID, value);
  }

  public int getMilitarRessource() {
    return this.militarRessource;
  }

  public BaseData setMilitarRessource(int militarRessource) {
    this.militarRessource = militarRessource;
    setMilitarRessourceIsSet(true);
    return this;
  }

  public void unsetMilitarRessource() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MILITARRESSOURCE_ISSET_ID);
  }

  /** Returns true if field militarRessource is set (has been assigned a value) and false otherwise */
  public boolean isSetMilitarRessource() {
    return EncodingUtils.testBit(__isset_bitfield, __MILITARRESSOURCE_ISSET_ID);
  }

  public void setMilitarRessourceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MILITARRESSOURCE_ISSET_ID, value);
  }

  public int getFuelRessource() {
    return this.fuelRessource;
  }

  public BaseData setFuelRessource(int fuelRessource) {
    this.fuelRessource = fuelRessource;
    setFuelRessourceIsSet(true);
    return this;
  }

  public void unsetFuelRessource() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FUELRESSOURCE_ISSET_ID);
  }

  /** Returns true if field fuelRessource is set (has been assigned a value) and false otherwise */
  public boolean isSetFuelRessource() {
    return EncodingUtils.testBit(__isset_bitfield, __FUELRESSOURCE_ISSET_ID);
  }

  public void setFuelRessourceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FUELRESSOURCE_ISSET_ID, value);
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

    case AI_ID:
      if (value == null) {
        unsetAi_id();
      } else {
        setAi_id((Integer)value);
      }
      break;

    case MILITAR_RESSOURCE:
      if (value == null) {
        unsetMilitarRessource();
      } else {
        setMilitarRessource((Integer)value);
      }
      break;

    case FUEL_RESSOURCE:
      if (value == null) {
        unsetFuelRessource();
      } else {
        setFuelRessource((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BASE_ID:
      return Integer.valueOf(getBase_id());

    case AI_ID:
      return Integer.valueOf(getAi_id());

    case MILITAR_RESSOURCE:
      return Integer.valueOf(getMilitarRessource());

    case FUEL_RESSOURCE:
      return Integer.valueOf(getFuelRessource());

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
    case AI_ID:
      return isSetAi_id();
    case MILITAR_RESSOURCE:
      return isSetMilitarRessource();
    case FUEL_RESSOURCE:
      return isSetFuelRessource();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BaseData)
      return this.equals((BaseData)that);
    return false;
  }

  public boolean equals(BaseData that) {
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

    boolean this_present_ai_id = true;
    boolean that_present_ai_id = true;
    if (this_present_ai_id || that_present_ai_id) {
      if (!(this_present_ai_id && that_present_ai_id))
        return false;
      if (this.ai_id != that.ai_id)
        return false;
    }

    boolean this_present_militarRessource = true;
    boolean that_present_militarRessource = true;
    if (this_present_militarRessource || that_present_militarRessource) {
      if (!(this_present_militarRessource && that_present_militarRessource))
        return false;
      if (this.militarRessource != that.militarRessource)
        return false;
    }

    boolean this_present_fuelRessource = true;
    boolean that_present_fuelRessource = true;
    if (this_present_fuelRessource || that_present_fuelRessource) {
      if (!(this_present_fuelRessource && that_present_fuelRessource))
        return false;
      if (this.fuelRessource != that.fuelRessource)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(BaseData other) {
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
    lastComparison = Boolean.valueOf(isSetAi_id()).compareTo(other.isSetAi_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAi_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ai_id, other.ai_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMilitarRessource()).compareTo(other.isSetMilitarRessource());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMilitarRessource()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.militarRessource, other.militarRessource);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFuelRessource()).compareTo(other.isSetFuelRessource());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFuelRessource()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fuelRessource, other.fuelRessource);
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
    StringBuilder sb = new StringBuilder("BaseData(");
    boolean first = true;

    sb.append("base_id:");
    sb.append(this.base_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("ai_id:");
    sb.append(this.ai_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("militarRessource:");
    sb.append(this.militarRessource);
    first = false;
    if (!first) sb.append(", ");
    sb.append("fuelRessource:");
    sb.append(this.fuelRessource);
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

  private static class BaseDataStandardSchemeFactory implements SchemeFactory {
    public BaseDataStandardScheme getScheme() {
      return new BaseDataStandardScheme();
    }
  }

  private static class BaseDataStandardScheme extends StandardScheme<BaseData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BaseData struct) throws org.apache.thrift.TException {
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
          case 2: // AI_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.ai_id = iprot.readI32();
              struct.setAi_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MILITAR_RESSOURCE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.militarRessource = iprot.readI32();
              struct.setMilitarRessourceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FUEL_RESSOURCE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.fuelRessource = iprot.readI32();
              struct.setFuelRessourceIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BaseData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(BASE_ID_FIELD_DESC);
      oprot.writeI32(struct.base_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(AI_ID_FIELD_DESC);
      oprot.writeI32(struct.ai_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MILITAR_RESSOURCE_FIELD_DESC);
      oprot.writeI32(struct.militarRessource);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FUEL_RESSOURCE_FIELD_DESC);
      oprot.writeI32(struct.fuelRessource);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BaseDataTupleSchemeFactory implements SchemeFactory {
    public BaseDataTupleScheme getScheme() {
      return new BaseDataTupleScheme();
    }
  }

  private static class BaseDataTupleScheme extends TupleScheme<BaseData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BaseData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBase_id()) {
        optionals.set(0);
      }
      if (struct.isSetAi_id()) {
        optionals.set(1);
      }
      if (struct.isSetMilitarRessource()) {
        optionals.set(2);
      }
      if (struct.isSetFuelRessource()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetBase_id()) {
        oprot.writeI32(struct.base_id);
      }
      if (struct.isSetAi_id()) {
        oprot.writeI32(struct.ai_id);
      }
      if (struct.isSetMilitarRessource()) {
        oprot.writeI32(struct.militarRessource);
      }
      if (struct.isSetFuelRessource()) {
        oprot.writeI32(struct.fuelRessource);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BaseData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.base_id = iprot.readI32();
        struct.setBase_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.ai_id = iprot.readI32();
        struct.setAi_idIsSet(true);
      }
      if (incoming.get(2)) {
        struct.militarRessource = iprot.readI32();
        struct.setMilitarRessourceIsSet(true);
      }
      if (incoming.get(3)) {
        struct.fuelRessource = iprot.readI32();
        struct.setFuelRessourceIsSet(true);
      }
    }
  }

}

