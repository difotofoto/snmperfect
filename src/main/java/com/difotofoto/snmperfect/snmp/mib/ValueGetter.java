package com.difotofoto.snmperfect.snmp.mib;

import org.snmp4j.smi.AbstractVariable;

public interface ValueGetter<V extends AbstractVariable> {
    public V getValue();
}
