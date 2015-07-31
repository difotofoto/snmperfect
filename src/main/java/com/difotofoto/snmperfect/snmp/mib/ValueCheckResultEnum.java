package com.difotofoto.snmperfect.snmp.mib;

import org.friendlysnmp.ValueValidation;
import org.snmp4j.mp.SnmpConstants;

/**
 * Created by ferdinand on 30/07/2015.
 */
public enum ValueCheckResultEnum {

    Success(SnmpConstants.SNMP_ERROR_SUCCESS, ValueValidation.SUCCESS),
    ErrorAuthorizationError(SnmpConstants.SNMP_ERROR_AUTHORIZATION_ERROR, ValueValidation.AUTHORIZATION_ERROR),
    ErrorBadValue(SnmpConstants.SNMP_ERROR_BAD_VALUE, ValueValidation.BAD_VALUE),
    ErrorCommitFailed(SnmpConstants.SNMP_ERROR_COMMIT_FAILED, ValueValidation.COMMIT_FAILED),
    ErrorGeneralError(SnmpConstants.SNMP_ERROR_GENERAL_ERROR, ValueValidation.GENERAL_ERROR),
    ErrorInconsistentName(SnmpConstants.SNMP_ERROR_INCONSISTENT_NAME, ValueValidation.INCONSISTENT_NAME),
    ErrorInconsistentValue(SnmpConstants.SNMP_ERROR_INCONSISTENT_VALUE, ValueValidation.INCONSISTENT_VALUE),
    ErrorIo(SnmpConstants.SNMP_ERROR_IO, ValueValidation.GENERAL_ERROR),
    ErrorLexicographicOrder(SnmpConstants.SNMP_ERROR_LEXICOGRAPHIC_ORDER, ValueValidation.GENERAL_ERROR),
    ErrorNoAccess(SnmpConstants.SNMP_ERROR_NO_ACCESS, ValueValidation.NO_ACCESS),
    ErrorNoCreation(SnmpConstants.SNMP_ERROR_NO_CREATION, ValueValidation.NO_CREATION),
    ErrorNoSuchName(SnmpConstants.SNMP_ERROR_NO_SUCH_NAME, ValueValidation.NO_SUCH_NAME),
    ErrorTimeOut(SnmpConstants.SNMP_ERROR_TIMEOUT, ValueValidation.GENERAL_ERROR),
    ErrorTooBig(SnmpConstants.SNMP_ERROR_TOO_BIG, ValueValidation.TOO_BIG),
    ErrorNotWriteable(SnmpConstants.SNMP_ERROR_NOT_WRITEABLE, ValueValidation.NOT_WRITEABLE),
    ErrorWrongValue(SnmpConstants.SNMP_ERROR_WRONG_VALUE, ValueValidation.WRONG_VALUE),
    ErrorWrongType(SnmpConstants.SNMP_ERROR_WRONG_TYPE, ValueValidation.WRONG_ENCODING),
    ErrorWrongEncoding(SnmpConstants.SNMP_ERROR_WRONG_ENCODING, ValueValidation.WRONG_ENCODING),
    ErrorWrongLength(SnmpConstants.SNMP_ERROR_WRONG_LENGTH, ValueValidation.WRONG_LENGTH),
    ErrorReadOnly(SnmpConstants.SNMP_ERROR_READ_ONLY, ValueValidation.READ_ONLY),
    ErrorResourceUnavailable(SnmpConstants.SNMP_ERROR_RESOURCE_UNAVAILABLE, ValueValidation.RESOURCE_UNAVAILABLE),
    ErrorReport(SnmpConstants.SNMP_ERROR_REPORT, ValueValidation.GENERAL_ERROR);

    private int code;
    private ValueValidation valueValidation;

    ValueCheckResultEnum(int code, ValueValidation valueValidation) {
        this.code = code;
        this.valueValidation = valueValidation;
    }

    public int getCode() {
        return code;
    }

    public ValueValidation getValueValidation() {
        return valueValidation;
    }
}
