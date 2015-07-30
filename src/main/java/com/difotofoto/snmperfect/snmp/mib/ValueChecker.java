package com.difotofoto.snmperfect.snmp.mib;

import org.snmp4j.smi.AbstractVariable;

/**
 * Created by ferdinand on 30/07/2015.
 */
public interface ValueChecker<V extends AbstractVariable> {
    public ValueCheckResultEnum checkValue(V value);
}
