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

public class CountryData implements org.apache.thrift.TBase<CountryData, CountryData._Fields>, java.io.Serializable, Cloneable, Comparable<CountryData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CountryData");

  private static final org.apache.thrift.protocol.TField PLANES_ID_IN_PRODUCTION_CHAIN_FIELD_DESC = new org.apache.thrift.protocol.TField("PlanesIdInProductionChain", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CountryDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CountryDataTupleSchemeFactory());
  }

  public List<Integer> PlanesIdInProductionChain; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PLANES_ID_IN_PRODUCTION_CHAIN((short)1, "PlanesIdInProductionChain");

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
        case 1: // PLANES_ID_IN_PRODUCTION_CHAIN
          return PLANES_ID_IN_PRODUCTION_CHAIN;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PLANES_ID_IN_PRODUCTION_CHAIN, new org.apache.thrift.meta_data.FieldMetaData("PlanesIdInProductionChain", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32            , "int"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CountryData.class, metaDataMap);
  }

  public CountryData() {
  }

  public CountryData(
    List<Integer> PlanesIdInProductionChain)
  {
    this();
    this.PlanesIdInProductionChain = PlanesIdInProductionChain;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CountryData(CountryData other) {
    if (other.isSetPlanesIdInProductionChain()) {
      List<Integer> __this__PlanesIdInProductionChain = new ArrayList<Integer>(other.PlanesIdInProductionChain.size());
      for (Integer other_element : other.PlanesIdInProductionChain) {
        __this__PlanesIdInProductionChain.add(other_element);
      }
      this.PlanesIdInProductionChain = __this__PlanesIdInProductionChain;
    }
  }

  public CountryData deepCopy() {
    return new CountryData(this);
  }

  @Override
  public void clear() {
    this.PlanesIdInProductionChain = null;
  }

  public int getPlanesIdInProductionChainSize() {
    return (this.PlanesIdInProductionChain == null) ? 0 : this.PlanesIdInProductionChain.size();
  }

  public java.util.Iterator<Integer> getPlanesIdInProductionChainIterator() {
    return (this.PlanesIdInProductionChain == null) ? null : this.PlanesIdInProductionChain.iterator();
  }

  public void addToPlanesIdInProductionChain(int elem) {
    if (this.PlanesIdInProductionChain == null) {
      this.PlanesIdInProductionChain = new ArrayList<Integer>();
    }
    this.PlanesIdInProductionChain.add(elem);
  }

  public List<Integer> getPlanesIdInProductionChain() {
    return this.PlanesIdInProductionChain;
  }

  public CountryData setPlanesIdInProductionChain(List<Integer> PlanesIdInProductionChain) {
    this.PlanesIdInProductionChain = PlanesIdInProductionChain;
    return this;
  }

  public void unsetPlanesIdInProductionChain() {
    this.PlanesIdInProductionChain = null;
  }

  /** Returns true if field PlanesIdInProductionChain is set (has been assigned a value) and false otherwise */
  public boolean isSetPlanesIdInProductionChain() {
    return this.PlanesIdInProductionChain != null;
  }

  public void setPlanesIdInProductionChainIsSet(boolean value) {
    if (!value) {
      this.PlanesIdInProductionChain = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PLANES_ID_IN_PRODUCTION_CHAIN:
      if (value == null) {
        unsetPlanesIdInProductionChain();
      } else {
        setPlanesIdInProductionChain((List<Integer>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PLANES_ID_IN_PRODUCTION_CHAIN:
      return getPlanesIdInProductionChain();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PLANES_ID_IN_PRODUCTION_CHAIN:
      return isSetPlanesIdInProductionChain();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CountryData)
      return this.equals((CountryData)that);
    return false;
  }

  public boolean equals(CountryData that) {
    if (that == null)
      return false;

    boolean this_present_PlanesIdInProductionChain = true && this.isSetPlanesIdInProductionChain();
    boolean that_present_PlanesIdInProductionChain = true && that.isSetPlanesIdInProductionChain();
    if (this_present_PlanesIdInProductionChain || that_present_PlanesIdInProductionChain) {
      if (!(this_present_PlanesIdInProductionChain && that_present_PlanesIdInProductionChain))
        return false;
      if (!this.PlanesIdInProductionChain.equals(that.PlanesIdInProductionChain))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(CountryData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPlanesIdInProductionChain()).compareTo(other.isSetPlanesIdInProductionChain());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlanesIdInProductionChain()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.PlanesIdInProductionChain, other.PlanesIdInProductionChain);
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
    StringBuilder sb = new StringBuilder("CountryData(");
    boolean first = true;

    sb.append("PlanesIdInProductionChain:");
    if (this.PlanesIdInProductionChain == null) {
      sb.append("null");
    } else {
      sb.append(this.PlanesIdInProductionChain);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CountryDataStandardSchemeFactory implements SchemeFactory {
    public CountryDataStandardScheme getScheme() {
      return new CountryDataStandardScheme();
    }
  }

  private static class CountryDataStandardScheme extends StandardScheme<CountryData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CountryData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PLANES_ID_IN_PRODUCTION_CHAIN
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.PlanesIdInProductionChain = new ArrayList<Integer>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  int _elem10;
                  _elem10 = iprot.readI32();
                  struct.PlanesIdInProductionChain.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setPlanesIdInProductionChainIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CountryData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.PlanesIdInProductionChain != null) {
        oprot.writeFieldBegin(PLANES_ID_IN_PRODUCTION_CHAIN_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.PlanesIdInProductionChain.size()));
          for (int _iter11 : struct.PlanesIdInProductionChain)
          {
            oprot.writeI32(_iter11);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CountryDataTupleSchemeFactory implements SchemeFactory {
    public CountryDataTupleScheme getScheme() {
      return new CountryDataTupleScheme();
    }
  }

  private static class CountryDataTupleScheme extends TupleScheme<CountryData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CountryData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPlanesIdInProductionChain()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetPlanesIdInProductionChain()) {
        {
          oprot.writeI32(struct.PlanesIdInProductionChain.size());
          for (int _iter12 : struct.PlanesIdInProductionChain)
          {
            oprot.writeI32(_iter12);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CountryData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.PlanesIdInProductionChain = new ArrayList<Integer>(_list13.size);
          for (int _i14 = 0; _i14 < _list13.size; ++_i14)
          {
            int _elem15;
            _elem15 = iprot.readI32();
            struct.PlanesIdInProductionChain.add(_elem15);
          }
        }
        struct.setPlanesIdInProductionChainIsSet(true);
      }
    }
  }

}

