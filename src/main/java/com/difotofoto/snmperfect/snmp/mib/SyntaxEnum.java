package com.difotofoto.snmperfect.snmp.mib;

import org.snmp4j.smi.AbstractVariable;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OctetString;

public enum SyntaxEnum {

    OctetString("OCTET STRING (SIZE (0..255))", new ValueGetter<OctetString>() {
        public OctetString getValue() {
            return new OctetString();
        }
    }),
    Integer32("Integer32", new ValueGetter<Integer32>() {
        public Integer32 getValue() {
            return new Integer32(0);
        }
    });

    private String text;
    private ValueGetter valueGetter;
    private SyntaxEnum(String text, ValueGetter valueGetter) {
        this.text = text;
        this.valueGetter = valueGetter;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    public AbstractVariable getAbstractVariable() {
        return (AbstractVariable) valueGetter.getValue();
    }
}
