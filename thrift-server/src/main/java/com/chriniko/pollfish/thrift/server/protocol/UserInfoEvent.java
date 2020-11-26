/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.chriniko.pollfish.thrift.server.protocol;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)", date = "2020-11-22")
public class UserInfoEvent implements org.apache.thrift.TBase<UserInfoEvent, UserInfoEvent._Fields>, java.io.Serializable, Cloneable, Comparable<UserInfoEvent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserInfoEvent");

  private static final org.apache.thrift.protocol.TField V_FIELD_DESC = new org.apache.thrift.protocol.TField("v", org.apache.thrift.protocol.TType.I16, (short)1);
  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new UserInfoEventStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new UserInfoEventTupleSchemeFactory();

  public short v; // optional
  public @org.apache.thrift.annotation.Nullable java.lang.String userId; // required
  public @org.apache.thrift.annotation.Nullable UserInfoEventType type; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    V((short)1, "v"),
    USER_ID((short)2, "userId"),
    TYPE((short)3, "type");

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
        case 1: // V
          return V;
        case 2: // USER_ID
          return USER_ID;
        case 3: // TYPE
          return TYPE;
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
  private static final int __V_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.V};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.V, new org.apache.thrift.meta_data.FieldMetaData("v", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "UserInfoEventType")));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserInfoEvent.class, metaDataMap);
  }

  public UserInfoEvent() {
    this.v = (short)1;

  }

  public UserInfoEvent(
    java.lang.String userId,
    UserInfoEventType type)
  {
    this();
    this.userId = userId;
    this.type = type;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserInfoEvent(UserInfoEvent other) {
    __isset_bitfield = other.__isset_bitfield;
    this.v = other.v;
    if (other.isSetUserId()) {
      this.userId = other.userId;
    }
    if (other.isSetType()) {
      this.type = new UserInfoEventType(other.type);
    }
  }

  public UserInfoEvent deepCopy() {
    return new UserInfoEvent(this);
  }

  @Override
  public void clear() {
    this.v = (short)1;

    this.userId = null;
    this.type = null;
  }

  public short getV() {
    return this.v;
  }

  public UserInfoEvent setV(short v) {
    this.v = v;
    setVIsSet(true);
    return this;
  }

  public void unsetV() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __V_ISSET_ID);
  }

  /** Returns true if field v is set (has been assigned a value) and false otherwise */
  public boolean isSetV() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __V_ISSET_ID);
  }

  public void setVIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __V_ISSET_ID, value);
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getUserId() {
    return this.userId;
  }

  public UserInfoEvent setUserId(@org.apache.thrift.annotation.Nullable java.lang.String userId) {
    this.userId = userId;
    return this;
  }

  public void unsetUserId() {
    this.userId = null;
  }

  /** Returns true if field userId is set (has been assigned a value) and false otherwise */
  public boolean isSetUserId() {
    return this.userId != null;
  }

  public void setUserIdIsSet(boolean value) {
    if (!value) {
      this.userId = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public UserInfoEventType getType() {
    return this.type;
  }

  public UserInfoEvent setType(@org.apache.thrift.annotation.Nullable UserInfoEventType type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case V:
      if (value == null) {
        unsetV();
      } else {
        setV((java.lang.Short)value);
      }
      break;

    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((java.lang.String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((UserInfoEventType)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case V:
      return getV();

    case USER_ID:
      return getUserId();

    case TYPE:
      return getType();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case V:
      return isSetV();
    case USER_ID:
      return isSetUserId();
    case TYPE:
      return isSetType();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof UserInfoEvent)
      return this.equals((UserInfoEvent)that);
    return false;
  }

  public boolean equals(UserInfoEvent that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_v = true && this.isSetV();
    boolean that_present_v = true && that.isSetV();
    if (this_present_v || that_present_v) {
      if (!(this_present_v && that_present_v))
        return false;
      if (this.v != that.v)
        return false;
    }

    boolean this_present_userId = true && this.isSetUserId();
    boolean that_present_userId = true && that.isSetUserId();
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (!this.userId.equals(that.userId))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetV()) ? 131071 : 524287);
    if (isSetV())
      hashCode = hashCode * 8191 + v;

    hashCode = hashCode * 8191 + ((isSetUserId()) ? 131071 : 524287);
    if (isSetUserId())
      hashCode = hashCode * 8191 + userId.hashCode();

    hashCode = hashCode * 8191 + ((isSetType()) ? 131071 : 524287);
    if (isSetType())
      hashCode = hashCode * 8191 + type.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(UserInfoEvent other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetV()).compareTo(other.isSetV());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetV()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.v, other.v);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("UserInfoEvent(");
    boolean first = true;

    if (isSetV()) {
      sb.append("v:");
      sb.append(this.v);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("userId:");
    if (this.userId == null) {
      sb.append("null");
    } else {
      sb.append(this.userId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (userId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'userId' was not present! Struct: " + toString());
    }
    if (type == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'type' was not present! Struct: " + toString());
    }
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

  private static class UserInfoEventStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserInfoEventStandardScheme getScheme() {
      return new UserInfoEventStandardScheme();
    }
  }

  private static class UserInfoEventStandardScheme extends org.apache.thrift.scheme.StandardScheme<UserInfoEvent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserInfoEvent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // V
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.v = iprot.readI16();
              struct.setVIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userId = iprot.readString();
              struct.setUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.type = new UserInfoEventType();
              struct.type.read(iprot);
              struct.setTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserInfoEvent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetV()) {
        oprot.writeFieldBegin(V_FIELD_DESC);
        oprot.writeI16(struct.v);
        oprot.writeFieldEnd();
      }
      if (struct.userId != null) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeString(struct.userId);
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        struct.type.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserInfoEventTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserInfoEventTupleScheme getScheme() {
      return new UserInfoEventTupleScheme();
    }
  }

  private static class UserInfoEventTupleScheme extends org.apache.thrift.scheme.TupleScheme<UserInfoEvent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserInfoEvent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.userId);
      struct.type.write(oprot);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetV()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetV()) {
        oprot.writeI16(struct.v);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserInfoEvent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.userId = iprot.readString();
      struct.setUserIdIsSet(true);
      struct.type = new UserInfoEventType();
      struct.type.read(iprot);
      struct.setTypeIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.v = iprot.readI16();
        struct.setVIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

