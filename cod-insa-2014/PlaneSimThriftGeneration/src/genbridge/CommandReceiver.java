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

public class CommandReceiver {

  public interface Iface {

    public Response addActionToPerform(Action act, int idConnection) throws org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void addActionToPerform(Action act, int idConnection, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public Response addActionToPerform(Action act, int idConnection) throws org.apache.thrift.TException
    {
      send_addActionToPerform(act, idConnection);
      return recv_addActionToPerform();
    }

    public void send_addActionToPerform(Action act, int idConnection) throws org.apache.thrift.TException
    {
      addActionToPerform_args args = new addActionToPerform_args();
      args.setAct(act);
      args.setIdConnection(idConnection);
      sendBase("addActionToPerform", args);
    }

    public Response recv_addActionToPerform() throws org.apache.thrift.TException
    {
      addActionToPerform_result result = new addActionToPerform_result();
      receiveBase(result, "addActionToPerform");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "addActionToPerform failed: unknown result");
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void addActionToPerform(Action act, int idConnection, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      checkReady();
      addActionToPerform_call method_call = new addActionToPerform_call(act, idConnection, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class addActionToPerform_call extends org.apache.thrift.async.TAsyncMethodCall {
      private Action act;
      private int idConnection;
      public addActionToPerform_call(Action act, int idConnection, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.act = act;
        this.idConnection = idConnection;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("addActionToPerform", org.apache.thrift.protocol.TMessageType.CALL, 0));
        addActionToPerform_args args = new addActionToPerform_args();
        args.setAct(act);
        args.setIdConnection(idConnection);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public Response getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_addActionToPerform();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("addActionToPerform", new addActionToPerform());
      return processMap;
    }

    public static class addActionToPerform<I extends Iface> extends org.apache.thrift.ProcessFunction<I, addActionToPerform_args> {
      public addActionToPerform() {
        super("addActionToPerform");
      }

      public addActionToPerform_args getEmptyArgsInstance() {
        return new addActionToPerform_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public addActionToPerform_result getResult(I iface, addActionToPerform_args args) throws org.apache.thrift.TException {
        addActionToPerform_result result = new addActionToPerform_result();
        result.success = iface.addActionToPerform(args.act, args.idConnection);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("addActionToPerform", new addActionToPerform());
      return processMap;
    }

    public static class addActionToPerform<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, addActionToPerform_args, Response> {
      public addActionToPerform() {
        super("addActionToPerform");
      }

      public addActionToPerform_args getEmptyArgsInstance() {
        return new addActionToPerform_args();
      }

      public AsyncMethodCallback<Response> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<Response>() { 
          public void onComplete(Response o) {
            addActionToPerform_result result = new addActionToPerform_result();
            result.success = o;
            try {
              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
              return;
            } catch (Exception e) {
              LOGGER.error("Exception writing to internal frame buffer", e);
            }
            fb.close();
          }
          public void onError(Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TBase msg;
            addActionToPerform_result result = new addActionToPerform_result();
            {
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
              return;
            } catch (Exception ex) {
              LOGGER.error("Exception writing to internal frame buffer", ex);
            }
            fb.close();
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, addActionToPerform_args args, org.apache.thrift.async.AsyncMethodCallback<Response> resultHandler) throws TException {
        iface.addActionToPerform(args.act, args.idConnection,resultHandler);
      }
    }

  }

  public static class addActionToPerform_args implements org.apache.thrift.TBase<addActionToPerform_args, addActionToPerform_args._Fields>, java.io.Serializable, Cloneable, Comparable<addActionToPerform_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("addActionToPerform_args");

    private static final org.apache.thrift.protocol.TField ACT_FIELD_DESC = new org.apache.thrift.protocol.TField("act", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField ID_CONNECTION_FIELD_DESC = new org.apache.thrift.protocol.TField("idConnection", org.apache.thrift.protocol.TType.I32, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new addActionToPerform_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new addActionToPerform_argsTupleSchemeFactory());
    }

    public Action act; // required
    public int idConnection; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      ACT((short)1, "act"),
      ID_CONNECTION((short)2, "idConnection");

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
          case 1: // ACT
            return ACT;
          case 2: // ID_CONNECTION
            return ID_CONNECTION;
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
    private static final int __IDCONNECTION_ISSET_ID = 0;
    private byte __isset_bitfield = 0;
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.ACT, new org.apache.thrift.meta_data.FieldMetaData("act", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Action.class)));
      tmpMap.put(_Fields.ID_CONNECTION, new org.apache.thrift.meta_data.FieldMetaData("idConnection", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32          , "int")));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(addActionToPerform_args.class, metaDataMap);
    }

    public addActionToPerform_args() {
    }

    public addActionToPerform_args(
      Action act,
      int idConnection)
    {
      this();
      this.act = act;
      this.idConnection = idConnection;
      setIdConnectionIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public addActionToPerform_args(addActionToPerform_args other) {
      __isset_bitfield = other.__isset_bitfield;
      if (other.isSetAct()) {
        this.act = new Action(other.act);
      }
      this.idConnection = other.idConnection;
    }

    public addActionToPerform_args deepCopy() {
      return new addActionToPerform_args(this);
    }

    @Override
    public void clear() {
      this.act = null;
      setIdConnectionIsSet(false);
      this.idConnection = 0;
    }

    public Action getAct() {
      return this.act;
    }

    public addActionToPerform_args setAct(Action act) {
      this.act = act;
      return this;
    }

    public void unsetAct() {
      this.act = null;
    }

    /** Returns true if field act is set (has been assigned a value) and false otherwise */
    public boolean isSetAct() {
      return this.act != null;
    }

    public void setActIsSet(boolean value) {
      if (!value) {
        this.act = null;
      }
    }

    public int getIdConnection() {
      return this.idConnection;
    }

    public addActionToPerform_args setIdConnection(int idConnection) {
      this.idConnection = idConnection;
      setIdConnectionIsSet(true);
      return this;
    }

    public void unsetIdConnection() {
      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IDCONNECTION_ISSET_ID);
    }

    /** Returns true if field idConnection is set (has been assigned a value) and false otherwise */
    public boolean isSetIdConnection() {
      return EncodingUtils.testBit(__isset_bitfield, __IDCONNECTION_ISSET_ID);
    }

    public void setIdConnectionIsSet(boolean value) {
      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IDCONNECTION_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case ACT:
        if (value == null) {
          unsetAct();
        } else {
          setAct((Action)value);
        }
        break;

      case ID_CONNECTION:
        if (value == null) {
          unsetIdConnection();
        } else {
          setIdConnection((Integer)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case ACT:
        return getAct();

      case ID_CONNECTION:
        return Integer.valueOf(getIdConnection());

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case ACT:
        return isSetAct();
      case ID_CONNECTION:
        return isSetIdConnection();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof addActionToPerform_args)
        return this.equals((addActionToPerform_args)that);
      return false;
    }

    public boolean equals(addActionToPerform_args that) {
      if (that == null)
        return false;

      boolean this_present_act = true && this.isSetAct();
      boolean that_present_act = true && that.isSetAct();
      if (this_present_act || that_present_act) {
        if (!(this_present_act && that_present_act))
          return false;
        if (!this.act.equals(that.act))
          return false;
      }

      boolean this_present_idConnection = true;
      boolean that_present_idConnection = true;
      if (this_present_idConnection || that_present_idConnection) {
        if (!(this_present_idConnection && that_present_idConnection))
          return false;
        if (this.idConnection != that.idConnection)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public int compareTo(addActionToPerform_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetAct()).compareTo(other.isSetAct());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetAct()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.act, other.act);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetIdConnection()).compareTo(other.isSetIdConnection());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIdConnection()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.idConnection, other.idConnection);
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
      StringBuilder sb = new StringBuilder("addActionToPerform_args(");
      boolean first = true;

      sb.append("act:");
      if (this.act == null) {
        sb.append("null");
      } else {
        sb.append(this.act);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("idConnection:");
      sb.append(this.idConnection);
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (act != null) {
        act.validate();
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

    private static class addActionToPerform_argsStandardSchemeFactory implements SchemeFactory {
      public addActionToPerform_argsStandardScheme getScheme() {
        return new addActionToPerform_argsStandardScheme();
      }
    }

    private static class addActionToPerform_argsStandardScheme extends StandardScheme<addActionToPerform_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, addActionToPerform_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // ACT
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.act = new Action();
                struct.act.read(iprot);
                struct.setActIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // ID_CONNECTION
              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                struct.idConnection = iprot.readI32();
                struct.setIdConnectionIsSet(true);
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

      public void write(org.apache.thrift.protocol.TProtocol oprot, addActionToPerform_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.act != null) {
          oprot.writeFieldBegin(ACT_FIELD_DESC);
          struct.act.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldBegin(ID_CONNECTION_FIELD_DESC);
        oprot.writeI32(struct.idConnection);
        oprot.writeFieldEnd();
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class addActionToPerform_argsTupleSchemeFactory implements SchemeFactory {
      public addActionToPerform_argsTupleScheme getScheme() {
        return new addActionToPerform_argsTupleScheme();
      }
    }

    private static class addActionToPerform_argsTupleScheme extends TupleScheme<addActionToPerform_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, addActionToPerform_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetAct()) {
          optionals.set(0);
        }
        if (struct.isSetIdConnection()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetAct()) {
          struct.act.write(oprot);
        }
        if (struct.isSetIdConnection()) {
          oprot.writeI32(struct.idConnection);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, addActionToPerform_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.act = new Action();
          struct.act.read(iprot);
          struct.setActIsSet(true);
        }
        if (incoming.get(1)) {
          struct.idConnection = iprot.readI32();
          struct.setIdConnectionIsSet(true);
        }
      }
    }

  }

  public static class addActionToPerform_result implements org.apache.thrift.TBase<addActionToPerform_result, addActionToPerform_result._Fields>, java.io.Serializable, Cloneable, Comparable<addActionToPerform_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("addActionToPerform_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new addActionToPerform_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new addActionToPerform_resultTupleSchemeFactory());
    }

    public Response success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

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
          case 0: // SUCCESS
            return SUCCESS;
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
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Response.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(addActionToPerform_result.class, metaDataMap);
    }

    public addActionToPerform_result() {
    }

    public addActionToPerform_result(
      Response success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public addActionToPerform_result(addActionToPerform_result other) {
      if (other.isSetSuccess()) {
        this.success = new Response(other.success);
      }
    }

    public addActionToPerform_result deepCopy() {
      return new addActionToPerform_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
    }

    public Response getSuccess() {
      return this.success;
    }

    public addActionToPerform_result setSuccess(Response success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((Response)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof addActionToPerform_result)
        return this.equals((addActionToPerform_result)that);
      return false;
    }

    public boolean equals(addActionToPerform_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public int compareTo(addActionToPerform_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
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
      StringBuilder sb = new StringBuilder("addActionToPerform_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (success != null) {
        success.validate();
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
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class addActionToPerform_resultStandardSchemeFactory implements SchemeFactory {
      public addActionToPerform_resultStandardScheme getScheme() {
        return new addActionToPerform_resultStandardScheme();
      }
    }

    private static class addActionToPerform_resultStandardScheme extends StandardScheme<addActionToPerform_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, addActionToPerform_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.success = new Response();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
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

      public void write(org.apache.thrift.protocol.TProtocol oprot, addActionToPerform_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class addActionToPerform_resultTupleSchemeFactory implements SchemeFactory {
      public addActionToPerform_resultTupleScheme getScheme() {
        return new addActionToPerform_resultTupleScheme();
      }
    }

    private static class addActionToPerform_resultTupleScheme extends TupleScheme<addActionToPerform_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, addActionToPerform_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, addActionToPerform_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = new Response();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
      }
    }

  }

}