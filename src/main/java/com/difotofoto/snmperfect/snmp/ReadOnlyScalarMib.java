package com.difotofoto.snmperfect.snmp;

import com.difotofoto.snmperfect.snmp.mib.MaxAccessEnum;
import com.difotofoto.snmperfect.snmp.mib.MibDocument;
import com.difotofoto.snmperfect.snmp.mib.ObjectType;
import org.apache.log4j.Logger;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.mo.MOFactory;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.smi.OctetString;

import java.util.HashMap;
import java.util.Map;

/**
 * This class serve as the generic read-only context object used by SNMP4J.
 * It will be used to register/unregister and create Managed Object for read only scalar purpose.
 *
 * @author Fneman
 */
public class ReadOnlyScalarMib implements MOGroup {

    public static Logger logger = Logger.getLogger(ReadOnlyScalarMib.class);
    @SuppressWarnings("rawtypes")
    private Map<String, MOScalar> moScalarMap = new HashMap<String, MOScalar>();
    private MibDocument mibDocument;


    public ReadOnlyScalarMib(MOFactory moFactory, MibDocument mibDocument) {
        this.mibDocument = mibDocument;
        createMO(moFactory);
    }

    protected void createMO(MOFactory moFactory) {
        addTCsToFactory(moFactory);
        for (ObjectType ot : mibDocument.getObjectTypes()) {
            if (ot.getMaxAccess().equals(MaxAccessEnum.READ_ONLY)) {
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
