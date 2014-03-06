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

public class PlaneData implements org.apache.thrift.TBase<PlaneData, PlaneData._Fields>, java.io.Serializable, Cloneable, Comparable<PlaneData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PlaneData");

  private static final org.apache.thrift.protocol.TField PLANE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("plane_id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField POSIT_FIELD_DESC = new org.apache.thrift.protocol.TField("posit", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField AI_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("ai_id", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField ENERGY_FIELD_DESC = new org.apache.thrift.protocol.TField("energy", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField GAZ_FIELD_DESC = new org.apache.thrift.protocol.TField("gaz", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("state", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PlaneDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PlaneDataTupleSchemeFactory());
  }

  public int plane_id; // required
  public CoordData posit; // required
  public int ai_id; // required
  public int energy; // required
  public int gaz; // required
  /**
   * 
   * @see PlaneStateData
   */
  public PlaneStateData state; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PLANE_ID((short)1, "plane_id"),
    POSIT((short)2, "posit"),
    AI_ID((short)3, "ai_id"),
    ENERGY((short)4, "energy"),
    GAZ((short)5, "gaz"),
    /**
     * 
     * @see PlaneStateData
     */
    STATE((short)6, "state");

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
        case 1: // PLANE_ID
          return PLANE_ID;
        case 2: // POSIT
          return POSIT;
        case 3: // AI_ID
          return AI_ID;
        case 4: // ENERGY
          return ENERGY;
        case 5: // GAZ
          return GAZ;
        case 6: // STATE
          return STATE;
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
  private static final int __PLANE_ID_ISSET_ID = 0;
  private static final int __AI_ID_ISSET_ID = 1;
  private static final int __ENERGY_ISSET_ID = 2;
  private static final int __GAZ_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PLANE_ID, new org.apache.thrift.meta_data.FieldMetaData("plane_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.POSIT, new org.apache.thrift.meta_data.FieldMetaData("posit", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CoordData.class)));
    tmpMap.put(_Fields.AI_ID, new org.apache.thrift.meta_data.FieldMetaData("ai_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.ENERGY, new org.apache.thrift.meta_data.FieldMetaData("energy", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.GAZ, new org.apache.thrift.meta_data.FieldMetaData("gaz", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.STATE, new org.apache.thrift.meta_data.FieldMetaData("state", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, PlaneStateData.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PlaneData.class, metaDataMap);
  }

  public PlaneData() {
  }

  public PlaneData(
    int plane_id,
    CoordData posit,
    int ai_id,
    int energy,
    int gaz,
    PlaneStateData state)
  {
    this();
    this.plane_id = plane_id;
    setPlane_idIsSet(true);
    this.posit = posit;
    this.ai_id = ai_id;
    setAi_idIsSet(true);
    this.energy = energy;
    setEnergyIsSet(true);
    this.gaz = gaz;
    setGazIsSet(true);
    this.state = state;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PlaneData(PlaneData other) {
    __isset_bitfield = other.__isset_bitfield;
    this.plane_id = other.plane_id;
    if (other.isSetPosit()) {
      this.posit = new CoordData(other.posit);
    }
    this.ai_id = other.ai_id;
    this.energy = other.energy;
    this.gaz = other.gaz;
    if (other.isSetState()) {
      this.state = other.state;
    }
  }

  public PlaneData deepCopy() {
    return new PlaneData(this);
  }

  @Override
  public void clear() {
    setPlane_idIsSet(false);
    this.plane_id = 0;
    this.posit = null;
    setAi_idIsSet(false);
    this.ai_id = 0;
    setEnergyIsSet(false);
    this.energy = 0;
    setGazIsSet(false);
    this.gaz = 0;
    this.state = null;
  }

  public int getPlane_id() {
    return this.plane_id;
  }

  public PlaneData setPlane_id(int plane_id) {
    this.plane_id = plane_id;
    setPlane_idIsSet(true);
    return this;
  }

  public void unsetPlane_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PLANE_ID_ISSET_ID);
  }

  /** Returns true if field plane_id is set (has been assigned a value) and false otherwise */
  public boolean isSetPlane_id() {
    return EncodingUtils.testBit(__isset_bitfield, __PLANE_ID_ISSET_ID);
  }

  public void setPlane_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PLANE_ID_ISSET_ID, value);
  }

  public CoordData getPosit() {
    return this.posit;
  }

  public PlaneData setPosit(CoordData posit) {
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

  public int getAi_id() {
    return this.ai_id;
  }

  public PlaneData setAi_id(int ai_id) {
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

  public int getEnergy() {
    return this.energy;
  }

  public PlaneData setEnergy(int energy) {
    this.energy = energy;
    setEnergyIsSet(true);
    return this;
  }

  public void unsetEnergy() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENERGY_ISSET_ID);
  }

  /** Returns true if field energy is set (has been assigned a value) and false otherwise */
  public boolean isSetEnergy() {
    return EncodingUtils.testBit(__isset_bitfield, __ENERGY_ISSET_ID);
  }

  public void setEnergyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENERGY_ISSET_ID, value);
  }

  public int getGaz() {
    return this.gaz;
  }

  public PlaneData setGaz(int gaz) {
    this.gaz = gaz;
    setGazIsSet(true);
    return this;
  }

  public void unsetGaz() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __GAZ_ISSET_ID);
  }

  /** Returns true if field gaz is set (has been assigned a value) and false otherwise */
  public boolean isSetGaz() {
    return EncodingUtils.testBit(__isset_bitfield, __GAZ_ISSET_ID);
  }

  public void setGazIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __GAZ_ISSET_ID, value);
  }

  /**
   * 
   * @see PlaneStateData
   */
  public PlaneStateData getState() {
    return this.state;
  }

  /**
   * 
   * @see PlaneStateData
   */
  public PlaneData setState(PlaneStateData state) {
    this.state = state;
    return this;
  }

  public void unsetState() {
    this.state = null;
  }

  /** Returns true if field state is set (has been assigned a value) and false otherwise */
  public boolean isSetState() {
    return this.state != null;
  }

  public void setStateIsSet(boolean value) {
    if (!value) {
      this.state = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PLANE_ID:
      if (value == null) {
        unsetPlane_id();
      } else {
        setPlane_id((Integer)value);
      }
      break;

    case POSIT:
      if (value == null) {
        unsetPosit();
      } else {
        setPosit((CoordData)value);
      }
      break;

    case AI_ID:
      if (value == null) {
        unsetAi_id();
      } else {
        setAi_id((Integer)value);
      }
      break;

    case ENERGY:
      if (value == null) {
        unsetEnergy();
      } else {
        setEnergy((Integer)value);
      }
      break;

    case GAZ:
      if (value == null) {
        unsetGaz();
      } else {
        setGaz((Integer)value);
      }
      break;

    case STATE:
      if (value == null) {
        unsetState();
      } else {
        setState((PlaneStateData)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PLANE_ID:
      return Integer.valueOf(getPlane_id());

    case POSIT:
      return getPosit();

    case AI_ID:
      return Integer.valueOf(getAi_id());

    case ENERGY:
      return Integer.valueOf(getEnergy());

    case GAZ:
      return Integer.valueOf(getGaz());

    case STATE:
      return getState();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PLANE_ID:
      return isSetPlane_id();
    case POSIT:
      return isSetPosit();
    case AI_ID:
      return isSetAi_id();
    case ENERGY:
      return isSetEnergy();
    case GAZ:
      return isSetGaz();
    case STATE:
      return isSetState();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PlaneData)
      return this.equals((PlaneData)that);
    return false;
  }

  public boolean equals(PlaneData that) {
    if (that == null)
      return false;

    boolean this_present_plane_id = true;
    boolean that_present_plane_id = true;
    if (this_present_plane_id || that_present_plane_id) {
      if (!(this_present_plane_id && that_present_plane_id))
        return false;
      if (this.plane_id != that.plane_id)
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

    boolean this_present_ai_id = true;
    boolean that_present_ai_id = true;
    if (this_present_ai_id || that_present_ai_id) {
      if (!(this_present_ai_id && that_present_ai_id))
        return false;
      if (this.ai_id != that.ai_id)
        return false;
    }

    boolean this_present_energy = true;
    boolean that_present_energy = true;
    if (this_present_energy || that_present_energy) {
      if (!(this_present_energy && that_present_energy))
        return false;
      if (this.energy != that.energy)
        return false;
    }

    boolean this_present_gaz = true;
    boolean that_present_gaz = true;
    if (this_present_gaz || that_present_gaz) {
      if (!(this_present_gaz && that_present_gaz))
        return false;
      if (this.gaz != that.gaz)
        return false;
    }

    boolean this_present_state = true && this.isSetState();
    boolean that_present_state = true && that.isSetState();
    if (this_present_state || that_present_state) {
      if (!(this_present_state && that_present_state))
        return false;
      if (!this.state.equals(that.state))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(PlaneData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPlane_id()).compareTo(other.isSetPlane_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlane_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.plane_id, other.plane_id);
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
    lastComparison = Boolean.valueOf(isSetEnergy()).compareTo(other.isSetEnergy());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEnergy()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.energy, other.energy);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGaz()).compareTo(other.isSetGaz());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGaz()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.gaz, other.gaz);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetState()).compareTo(other.isSetState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.state, other.state);
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
    StringBuilder sb = new StringBuilder("PlaneData(");
    boolean first = true;

    sb.append("plane_id:");
    sb.append(this.plane_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("posit:");
    if (this.posit == null) {
      sb.append("null");
    } else {
      sb.append(this.posit);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("ai_id:");
    sb.append(this.ai_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("energy:");
    sb.append(this.energy);
    first = false;
    if (!first) sb.append(", ");
    sb.append("gaz:");
    sb.append(this.gaz);
    first = false;
    if (!first) sb.append(", ");
    sb.append("state:");
    if (this.state == null) {
      sb.append("null");
    } else {
      sb.append(this.state);
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

  private static class PlaneDataStandardSchemeFactory implements SchemeFactory {
    public PlaneDataStandardScheme getScheme() {
      return new PlaneDataStandardScheme();
    }
  }

  private static class PlaneDataStandardScheme extends StandardScheme<PlaneData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PlaneData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PLANE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.plane_id = iprot.readI32();
              struct.setPlane_idIsSet(true);
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
          case 3: // AI_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.ai_id = iprot.readI32();
              struct.setAi_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ENERGY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.energy = iprot.readI32();
              struct.setEnergyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // GAZ
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.gaz = iprot.readI32();
              struct.setGazIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.state = PlaneStateData.findByValue(iprot.readI32());
              struct.setStateIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PlaneData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PLANE_ID_FIELD_DESC);
      oprot.writeI32(struct.plane_id);
      oprot.writeFieldEnd();
      if (struct.posit != null) {
        oprot.writeFieldBegin(POSIT_FIELD_DESC);
        struct.posit.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(AI_ID_FIELD_DESC);
      oprot.writeI32(struct.ai_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ENERGY_FIELD_DESC);
      oprot.writeI32(struct.energy);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(GAZ_FIELD_DESC);
      oprot.writeI32(struct.gaz);
      oprot.writeFieldEnd();
      if (struct.state != null) {
        oprot.writeFieldBegin(STATE_FIELD_DESC);
        oprot.writeI32(struct.state.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PlaneDataTupleSchemeFactory implements SchemeFactory {
    public PlaneDataTupleScheme getScheme() {
      return new PlaneDataTupleScheme();
    }
  }

  private static class PlaneDataTupleScheme extends TupleScheme<PlaneData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PlaneData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPlane_id()) {
        optionals.set(0);
      }
      if (struct.isSetPosit()) {
        optionals.set(1);
      }
      if (struct.isSetAi_id()) {
        optionals.set(2);
      }
      if (struct.isSetEnergy()) {
        optionals.set(3);
      }
      if (struct.isSetGaz()) {
        optionals.set(4);
      }
      if (struct.isSetState()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetPlane_id()) {
        oprot.writeI32(struct.plane_id);
      }
      if (struct.isSetPosit()) {
        struct.posit.write(oprot);
      }
      if (struct.isSetAi_id()) {
        oprot.writeI32(struct.ai_id);
      }
      if (struct.isSetEnergy()) {
        oprot.writeI32(struct.energy);
      }
      if (struct.isSetGaz()) {
        oprot.writeI32(struct.gaz);
      }
      if (struct.isSetState()) {
        oprot.writeI32(struct.state.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PlaneData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.plane_id = iprot.readI32();
        struct.setPlane_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.posit = new CoordData();
        struct.posit.read(iprot);
        struct.setPositIsSet(true);
      }
      if (incoming.get(2)) {
        struct.ai_id = iprot.readI32();
        struct.setAi_idIsSet(true);
      }
      if (incoming.get(3)) {
        struct.energy = iprot.readI32();
        struct.setEnergyIsSet(true);
      }
      if (incoming.get(4)) {
        struct.gaz = iprot.readI32();
        struct.setGazIsSet(true);
      }
      if (incoming.get(5)) {
        struct.state = PlaneStateData.findByValue(iprot.readI32());
        struct.setStateIsSet(true);
      }
    }
  }

}

