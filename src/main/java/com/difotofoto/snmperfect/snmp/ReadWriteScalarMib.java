package com.difotofoto.snmperfect.snmp;

import com.difotofoto.snmperfect.snmp.mib.MaxAccessEnum;
import com.difotofoto.snmperfect.snmp.mib.MibDocument;
import com.difotofoto.snmperfect.snmp.mib.ObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.mo.MOFactory;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.smi.OctetString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ferdinand on 31/07/2015.
 */
public class ReadWriteScalarMib implements MOGroup {

    public static Logger logger = LoggerFactory.getLogger(ReadWriteScalarMib.class);
    @SuppressWarnings("rawtypes")
    private Map<String, MOScalar> moScalarMap = new HashMap<String, MOScalar>();
    private MibDocument mibDocument;


    public ReadWriteScalarMib(MOFactory moFactory, MibDocument mibDocument) {
        this.mibDocument = mibDocument;
        createMO(moFactory);
    }

    protected void createMO(MOFactory moFactory) {
        addTCsToFactory(moFactory);
        for (ObjectType ot : mibDocument.getObjectTypes()) {
            if (ot.getMaxAccess().equals(MaxAccessEnum.READ_WRITE)) {
                moScalarMap.put(ot.getName(), moFactory.createScalar(ot.getOID(), moFactory.createAccess(ot.getMaxAccess().getAccessType()), ot.getSyntax().getAbstractVariable()));
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public MOScalar getMOScalarByID(String id) {
        return moScalarMap.get(id);
    }


    @SuppressWarnings("rawtypes")
    public void registerMOs(MOServer server, OctetString octet)
            throws DuplicateRegistrationException {
        for (MOScalar ne : moScalarMap.values()) {
            server.register(ne, octet);
        }
    }


    @SuppressWarnings("rawtypes")
    public void unregisterMOs(MOServer server, OctetString octet) {
        for (MOScalar ne : moScalarMap.values()) {
            server.unregister(ne, octet);
        }
    }

    protected void addTCsToFactory(MOFactory moFactory) {
        // NO Textual Conversion
    }

    public void addImportedTCsToFactory(MOFactory moFactory) {
    }
}
