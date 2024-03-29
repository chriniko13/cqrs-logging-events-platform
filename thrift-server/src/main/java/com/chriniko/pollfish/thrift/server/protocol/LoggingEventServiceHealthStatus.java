/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.chriniko.pollfish.thrift.server.protocol;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)", date = "2020-11-22")
public class LoggingEventServiceHealthStatus implements org.apache.thrift.TBase<LoggingEventServiceHealthStatus, LoggingEventServiceHealthStatus._Fields>, java.io.Serializable, Cloneable, Comparable<LoggingEventServiceHealthStatus> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LoggingEventServiceHealthStatus");

  private static final org.apache.thrift.protocol.TField HEALTHY_FIELD_DESC = new org.apache.thrift.protocol.TField("healthy", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField MSG_FIELD_DESC = new org.apache.thrift.protocol.TField("msg", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new LoggingEventServiceHealthStatusStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new LoggingEventServiceHealthStatusTupleSchemeFactory();

  public boolean healthy; // required
  public @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer msg; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    HEALTHY((short)1, "healthy"),
    MSG((short)2, "msg");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // HEALTHY
          return HEALTHY;
        case 2: // MSG
          return MSG;
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
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __HEALTHY_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.HEALTHY, new org.apache.thrift.meta_data.FieldMetaData("healthy", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.MSG, new org.apache.thrift.meta_data.FieldMetaData("msg", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LoggingEventServiceHealthStatus.class, metaDataMap);
  }

  public LoggingEventServiceHealthStatus() {
  }

  public LoggingEventServiceHealthStatus(
    boolean healthy,
    java.nio.ByteBuffer msg)
  {
    this();
    this.healthy = healthy;
    setHealthyIsSet(true);
    this.msg = org.apache.thrift.TBaseHelper.copyBinary(msg);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LoggingEventServiceHealthStatus(LoggingEventServiceHealthStatus other) {
    __isset_bitfield = other.__isset_bitfield;
    this.healthy = other.healthy;
    if (other.isSetMsg()) {
      this.msg = org.apache.thrift.TBaseHelper.copyBinary(other.msg);
    }
  }

  public LoggingEventServiceHealthStatus deepCopy() {
    return new LoggingEventServiceHealthStatus(this);
  }

  @Override
  public void clear() {
    setHealthyIsSet(false);
    this.healthy = false;
    this.msg = null;
  }

  public boolean isHealthy() {
    return this.healthy;
  }

  public LoggingEventServiceHealthStatus setHealthy(boolean healthy) {
    this.healthy = healthy;
    setHealthyIsSet(true);
    return this;
  }

  public void unsetHealthy() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __HEALTHY_ISSET_ID);
  }

  /** Returns true if field healthy is set (has been assigned a value) and false otherwise */
  public boolean isSetHealthy() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __HEALTHY_ISSET_ID);
  }

  public void setHealthyIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __HEALTHY_ISSET_ID, value);
  }

  public byte[] getMsg() {
    setMsg(org.apache.thrift.TBaseHelper.rightSize(msg));
    return msg == null ? null : msg.array();
  }

  public java.nio.ByteBuffer bufferForMsg() {
    return org.apache.thrift.TBaseHelper.copyBinary(msg);
  }

  public LoggingEventServiceHealthStatus setMsg(byte[] msg) {
    this.msg = msg == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(msg.clone());
    return this;
  }

  public LoggingEventServiceHealthStatus setMsg(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer msg) {
    this.msg = org.apache.thrift.TBaseHelper.copyBinary(msg);
    return this;
  }

  public void unsetMsg() {
    this.msg = null;
  }

  /** Returns true if field msg is set (has been assigned a value) and false otherwise */
  public boolean isSetMsg() {
    return this.msg != null;
  }

  public void setMsgIsSet(boolean value) {
    if (!value) {
      this.msg = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case HEALTHY:
      if (value == null) {
        unsetHealthy();
      } else {
        setHealthy((java.lang.Boolean)value);
      }
      break;

    case MSG:
      if (value == null) {
        unsetMsg();
      } else {
        if (value instanceof byte[]) {
          setMsg((byte[])value);
        } else {
          setMsg((java.nio.ByteBuffer)value);
        }
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case HEALTHY:
      return isHealthy();

    case MSG:
      return getMsg();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case HEALTHY:
      return isSetHealthy();
    case MSG:
      return isSetMsg();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof LoggingEventServiceHealthStatus)
      return this.equals((LoggingEventServiceHealthStatus)that);
    return false;
  }

  public boolean equals(LoggingEventServiceHealthStatus that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_healthy = true;
    boolean that_present_healthy = true;
    if (this_present_healthy || that_present_healthy) {
      if (!(this_present_healthy && that_present_healthy))
        return false;
      if (this.healthy != that.healthy)
        return false;
    }

    boolean this_present_msg = true && this.isSetMsg();
    boolean that_present_msg = true && that.isSetMsg();
    if (this_present_msg || that_present_msg) {
      if (!(this_present_msg && that_present_msg))
        return false;
      if (!this.msg.equals(that.msg))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((healthy) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetMsg()) ? 131071 : 524287);
    if (isSetMsg())
      hashCode = hashCode * 8191 + msg.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(LoggingEventServiceHealthStatus other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetHealthy()).compareTo(other.isSetHealthy());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHealthy()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.healthy, other.healthy);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMsg()).compareTo(other.isSetMsg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMsg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.msg, other.msg);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("LoggingEventServiceHealthStatus(");
    boolean first = true;

    sb.append("healthy:");
    sb.append(this.healthy);
    first = false;
    if (!first) sb.append(", ");
    sb.append("msg:");
    if (this.msg == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.msg, sb);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class LoggingEventServiceHealthStatusStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LoggingEventServiceHealthStatusStandardScheme getScheme() {
      return new LoggingEventServiceHealthStatusStandardScheme();
    }
  }

  private static class LoggingEventServiceHealthStatusStandardScheme extends org.apache.thrift.scheme.StandardScheme<LoggingEventServiceHealthStatus> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LoggingEventServiceHealthStatus struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // HEALTHY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.healthy = iprot.readBool();
              struct.setHealthyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MSG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.msg = iprot.readBinary();
              struct.setMsgIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, LoggingEventServiceHealthStatus struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(HEALTHY_FIELD_DESC);
      oprot.writeBool(struct.healthy);
      oprot.writeFieldEnd();
      if (struct.msg != null) {
        oprot.writeFieldBegin(MSG_FIELD_DESC);
        oprot.writeBinary(struct.msg);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LoggingEventServiceHealthStatusTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LoggingEventServiceHealthStatusTupleScheme getScheme() {
      return new LoggingEventServiceHealthStatusTupleScheme();
    }
  }

  private static class LoggingEventServiceHealthStatusTupleScheme extends org.apache.thrift.scheme.TupleScheme<LoggingEventServiceHealthStatus> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LoggingEventServiceHealthStatus struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetHealthy()) {
        optionals.set(0);
      }
      if (struct.isSetMsg()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetHealthy()) {
        oprot.writeBool(struct.healthy);
      }
      if (struct.isSetMsg()) {
        oprot.writeBinary(struct.msg);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LoggingEventServiceHealthStatus struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.healthy = iprot.readBool();
        struct.setHealthyIsSet(true);
      }
      if (incoming.get(1)) {
        struct.msg = iprot.readBinary();
        struct.setMsgIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

