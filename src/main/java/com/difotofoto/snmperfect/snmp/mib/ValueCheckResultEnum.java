package com.difotofoto.snmperfect.snmp.mib;

import org.snmp4j.mp.SnmpConstants;

/**
 * Created by ferdinand on 30/07/2015.
 */
public enum ValueCheckResultEnum {

    Success(SnmpConstants.SNMP_ERROR_SUCCESS),
    ErrorAuthorizationError(SnmpConstants.SNMP_ERROR_AUTHORIZATION_ERROR),
    ErrorBadValue(SnmpConstants.SNMP_ERROR_BAD_VALUE),
    ErrorCommitFailed(SnmpConstants.SNMP_ERROR_COMMIT_FAILED),
    ErrorGeneralError(SnmpConstants.SNMP_ERROR_GENERAL_ERROR),
    ErrorInconsistentName(SnmpConstants.SNMP_ERROR_INCONSISTENT_NAME),
    ErrorInconsistentValue(SnmpConstants.SNMP_ERROR_INCONSISTENT_VALUE),
    ErrorIo(SnmpConstants.SNMP_ERROR_IO),
    ErrorLexicographicOrder(SnmpConstants.SNMP_ERROR_LEXICOGRAPHIC_ORDER),
    ErrorNoAccess(SnmpConstants.SNMP_ERROR_NO_ACCESS),
    ErrorNoCreation(SnmpConstants.SNMP_ERROR_NO_CREATION),
    ErrorNoSuchName(SnmpConstants.SNMP_ERROR_NO_SUCH_NAME),
    ErrorTimeOut(SnmpConstants.SNMP_ERROR_TIMEOUT),
    ErrorTooBig(SnmpConstants.SNMP_ERROR_TOO_BIG),
    ErrorNotWriteable(SnmpConstants.SNMP_ERROR_NOT_WRITEABLE),
    ErrorWrongValue(SnmpConstants.SNMP_ERROR_WRONG_VALUE),
    ErrorWrongType(SnmpConstants.SNMP_ERROR_WRONG_TYPE),
    ErrorWrongEncoding(SnmpConstants.SNMP_ERROR_WRONG_ENCODING),
    ErrorWrongLength(SnmpConstants.SNMP_ERROR_WRONG_LENGTH),
    ErrorReadOnly(SnmpConstants.SNMP_ERROR_READ_ONLY),
    ErrorResourceUnavailable(SnmpConstants.SNMP_ERROR_RESOURCE_UNAVAILABLE),
    ErrorReport(SnmpConstants.SNMP_ERROR_REPORT);

    private ValueCheckResultEnum(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

}
