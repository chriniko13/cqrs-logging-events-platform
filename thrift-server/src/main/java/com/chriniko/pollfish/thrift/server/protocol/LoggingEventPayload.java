/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.chriniko.pollfish.thrift.server.protocol;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)", date = "2020-11-22")
public class LoggingEventPayload extends org.apache.thrift.TUnion<LoggingEventPayload, LoggingEventPayload._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LoggingEventPayload");
  private static final org.apache.thrift.protocol.TField MONITOR_SERVICE_EVENT_FIELD_DESC = new org.apache.thrift.protocol.TField("monitorServiceEvent", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField TRANSACTION_EVENT_FIELD_DESC = new org.apache.thrift.protocol.TField("transactionEvent", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField USER_INFO_EVENT_FIELD_DESC = new org.apache.thrift.protocol.TField("userInfoEvent", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MONITOR_SERVICE_EVENT((short)1, "monitorServiceEvent"),
    TRANSACTION_EVENT((short)2, "transactionEvent"),
    USER_INFO_EVENT((short)3, "userInfoEvent");

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
        case 1: // MONITOR_SERVICE_EVENT
          return MONITOR_SERVICE_EVENT;
        case 2: // TRANSACTION_EVENT
          return TRANSACTION_EVENT;
        case 3: // USER_INFO_EVENT
          return USER_INFO_EVENT;
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

  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MONITOR_SERVICE_EVENT, new org.apache.thrift.meta_data.FieldMetaData("monitorServiceEvent", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, MonitorServiceEvent.class)));
    tmpMap.put(_Fields.TRANSACTION_EVENT, new org.apache.thrift.meta_data.FieldMetaData("transactionEvent", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TransactionEvent.class)));
    tmpMap.put(_Fields.USER_INFO_EVENT, new org.apache.thrift.meta_data.FieldMetaData("userInfoEvent", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, UserInfoEvent.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LoggingEventPayload.class, metaDataMap);
  }

  public LoggingEventPayload() {
    super();
  }

  public LoggingEventPayload(_Fields setField, java.lang.Object value) {
    super(setField, value);
  }

  public LoggingEventPayload(LoggingEventPayload other) {
    super(other);
  }
  public LoggingEventPayload deepCopy() {
    return new LoggingEventPayload(this);
  }

  public static LoggingEventPayload monitorServiceEvent(MonitorServiceEvent value) {
    LoggingEventPayload x = new LoggingEventPayload();
    x.setMonitorServiceEvent(value);
    return x;
  }

  public static LoggingEventPayload transactionEvent(TransactionEvent value) {
    LoggingEventPayload x = new LoggingEventPayload();
    x.setTransactionEvent(value);
    return x;
  }

  public static LoggingEventPayload userInfoEvent(UserInfoEvent value) {
    LoggingEventPayload x = new LoggingEventPayload();
    x.setUserInfoEvent(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, java.lang.Object value) throws java.lang.ClassCastException {
    switch (setField) {
      case MONITOR_SERVICE_EVENT:
        if (value instanceof MonitorServiceEvent) {
          break;
        }
        throw new java.lang.ClassCastException("Was expecting value of type MonitorServiceEvent for field 'monitorServiceEvent', but got " + value.getClass().getSimpleName());
      case TRANSACTION_EVENT:
        if (value instanceof TransactionEvent) {
          break;
        }
        throw new java.lang.ClassCastException("Was expecting value of type TransactionEvent for field 'transactionEvent', but got " + value.getClass().getSimpleName());
      case USER_INFO_EVENT:
        if (value instanceof UserInfoEvent) {
          break;
        }
        throw new java.lang.ClassCastException("Was expecting value of type UserInfoEvent for field 'userInfoEvent', but got " + value.getClass().getSimpleName());
      default:
        throw new java.lang.IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected java.lang.Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case MONITOR_SERVICE_EVENT:
          if (field.type == MONITOR_SERVICE_EVENT_FIELD_DESC.type) {
            MonitorServiceEvent monitorServiceEvent;
            monitorServiceEvent = new MonitorServiceEvent();
            monitorServiceEvent.read(iprot);
            return monitorServiceEvent;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case TRANSACTION_EVENT:
          if (field.type == TRANSACTION_EVENT_FIELD_DESC.type) {
            TransactionEvent transactionEvent;
            transactionEvent = new TransactionEvent();
            transactionEvent.read(iprot);
            return transactionEvent;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case USER_INFO_EVENT:
          if (field.type == USER_INFO_EVENT_FIELD_DESC.type) {
            UserInfoEvent userInfoEvent;
            userInfoEvent = new UserInfoEvent();
            userInfoEvent.read(iprot);
            return userInfoEvent;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        default:
          throw new java.lang.IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      return null;
    }
  }

  @Override
  protected void standardSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case MONITOR_SERVICE_EVENT:
        MonitorServiceEvent monitorServiceEvent = (MonitorServiceEvent)value_;
        monitorServiceEvent.write(oprot);
        return;
      case TRANSACTION_EVENT:
        TransactionEvent transactionEvent = (TransactionEvent)value_;
        transactionEvent.write(oprot);
        return;
      case USER_INFO_EVENT:
        UserInfoEvent userInfoEvent = (UserInfoEvent)value_;
        userInfoEvent.write(oprot);
        return;
      default:
        throw new java.lang.IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected java.lang.Object tupleSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, short fieldID) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(fieldID);
    if (setField != null) {
      switch (setField) {
        case MONITOR_SERVICE_EVENT:
          MonitorServiceEvent monitorServiceEvent;
          monitorServiceEvent = new MonitorServiceEvent();
          monitorServiceEvent.read(iprot);
          return monitorServiceEvent;
        case TRANSACTION_EVENT:
          TransactionEvent transactionEvent;
          transactionEvent = new TransactionEvent();
          transactionEvent.read(iprot);
          return transactionEvent;
        case USER_INFO_EVENT:
          UserInfoEvent userInfoEvent;
          userInfoEvent = new UserInfoEvent();
          userInfoEvent.read(iprot);
          return userInfoEvent;
        default:
          throw new java.lang.IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      throw new org.apache.thrift.protocol.TProtocolException("Couldn't find a field with field id " + fieldID);
    }
  }

  @Override
  protected void tupleSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case MONITOR_SERVICE_EVENT:
        MonitorServiceEvent monitorServiceEvent = (MonitorServiceEvent)value_;
        monitorServiceEvent.write(oprot);
        return;
      case TRANSACTION_EVENT:
        TransactionEvent transactionEvent = (TransactionEvent)value_;
        transactionEvent.write(oprot);
        return;
      case USER_INFO_EVENT:
        UserInfoEvent userInfoEvent = (UserInfoEvent)value_;
        userInfoEvent.write(oprot);
        return;
      default:
        throw new java.lang.IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case MONITOR_SERVICE_EVENT:
        return MONITOR_SERVICE_EVENT_FIELD_DESC;
      case TRANSACTION_EVENT:
        return TRANSACTION_EVENT_FIELD_DESC;
      case USER_INFO_EVENT:
        return USER_INFO_EVENT_FIELD_DESC;
      default:
        throw new java.lang.IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TStruct getStructDesc() {
    return STRUCT_DESC;
  }

  @Override
  protected _Fields enumForId(short id) {
    return _Fields.findByThriftIdOrThrow(id);
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public MonitorServiceEvent getMonitorServiceEvent() {
    if (getSetField() == _Fields.MONITOR_SERVICE_EVENT) {
      return (MonitorServiceEvent)getFieldValue();
    } else {
      throw new java.lang.RuntimeException("Cannot get field 'monitorServiceEvent' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setMonitorServiceEvent(MonitorServiceEvent value) {
    if (value == null) throw new java.lang.NullPointerException();
    setField_ = _Fields.MONITOR_SERVICE_EVENT;
    value_ = value;
  }

  public TransactionEvent getTransactionEvent() {
    if (getSetField() == _Fields.TRANSACTION_EVENT) {
      return (TransactionEvent)getFieldValue();
    } else {
      throw new java.lang.RuntimeException("Cannot get field 'transactionEvent' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setTransactionEvent(TransactionEvent value) {
    if (value == null) throw new java.lang.NullPointerException();
    setField_ = _Fields.TRANSACTION_EVENT;
    value_ = value;
  }

  public UserInfoEvent getUserInfoEvent() {
    if (getSetField() == _Fields.USER_INFO_EVENT) {
      return (UserInfoEvent)getFieldValue();
    } else {
      throw new java.lang.RuntimeException("Cannot get field 'userInfoEvent' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setUserInfoEvent(UserInfoEvent value) {
    if (value == null) throw new java.lang.NullPointerException();
    setField_ = _Fields.USER_INFO_EVENT;
    value_ = value;
  }

  public boolean isSetMonitorServiceEvent() {
    return setField_ == _Fields.MONITOR_SERVICE_EVENT;
  }


  public boolean isSetTransactionEvent() {
    return setField_ == _Fields.TRANSACTION_EVENT;
  }


  public boolean isSetUserInfoEvent() {
    return setField_ == _Fields.USER_INFO_EVENT;
  }


  public boolean equals(java.lang.Object other) {
    if (other instanceof LoggingEventPayload) {
      return equals((LoggingEventPayload)other);
    } else {
      return false;
    }
  }

  public boolean equals(LoggingEventPayload other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(LoggingEventPayload other) {
    int lastComparison = org.apache.thrift.TBaseHelper.compareTo(getSetField(), other.getSetField());
    if (lastComparison == 0) {
      return org.apache.thrift.TBaseHelper.compareTo(getFieldValue(), other.getFieldValue());
    }
    return lastComparison;
  }


  @Override
  public int hashCode() {
    java.util.List<java.lang.Object> list = new java.util.ArrayList<java.lang.Object>();
    list.add(this.getClass().getName());
    org.apache.thrift.TFieldIdEnum setField = getSetField();
    if (setField != null) {
      list.add(setField.getThriftFieldId());
      java.lang.Object value = getFieldValue();
      if (value instanceof org.apache.thrift.TEnum) {
        list.add(((org.apache.thrift.TEnum)getFieldValue()).getValue());
      } else {
        list.add(value);
      }
    }
    return list.hashCode();
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }


}