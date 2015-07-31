package com.difotofoto.snmperfect.snmp;

import com.difotofoto.snmperfect.snmp.mib.MaxAccessEnum;
import com.difotofoto.snmperfect.snmp.mib.MibDocument;
import com.difotofoto.snmperfect.snmp.mib.ObjectType;
import org.friendlysnmp.AgentWorker;
import org.friendlysnmp.FException;
import org.friendlysnmp.FScalar;
import org.friendlysnmp.event.FScalarGetListener;
import org.friendlysnmp.mib.BaseMib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.mo.DefaultMOFactory;
import org.snmp4j.smi.OctetString;

import java.util.HashMap;
import java.util.Map;

public class ReadOnlyScalarMibFriendly extends BaseMib {

    public static Logger logger = LoggerFactory.getLogger(ReadOnlyScalarMibFriendly.class);
    private ReadOnlyScalarMib mibORIG;
    private MibDocument mibDocument;

    private Map<String, FScalar> fScalarMap = new HashMap<String, FScalar>();

    public ReadOnlyScalarMibFriendly(MibDocument mibDocument) {
        super();
        this.mibDocument = mibDocument;
    }

    @Override
    public void init(AgentWorker agent) throws FException {
        super.init(agent);

        mibORIG = new ReadOnlyScalarMib(DefaultMOFactory.getInstance(), mibDocument);

        for (final ObjectType ot : mibDocument.getObjectTypes()) {
            if (ot.getMaxAccess().equals(MaxAccessEnum.READ_ONLY)) {
                logger.info("Registering Read Only (" + ot.getName() + ") " + ot.getOIDString());
                FScalar fscalar = new FScalar(ot.getName(), mibORIG.getMOScalarByID(ot.getName()), agent);
                fScalarMap.put(ot.getName(), fscalar);
                addNode(fscalar);
                fscalar.addGetListener(new FScalarGetListener() {
                    @Override
                    public void get(FScalar scalar) {
                        Object o = ot.getValueGetter().getValue();
                        logger.info("RO - " + ot.getName() + " - " + ot.getOIDString() + " : " + o);
                        scalar.setValueEx(o);
                    }
                });
            }
        }

    }

//	private boolean isAlreadyContainsOid(ObjectType ot) {
//		String oidString = ot.getOIDString();
//	}

    @Override
    public void registerMOs(MOServer server, OctetString octet)
            throws DuplicateRegistrationException {
        logger.info("RegisterMO " + octet);
        mibORIG.registerMOs(server, octet);
    }

    @Override
    public void unregisterMOs(MOServer server, OctetString octet) {
        logger.info("UnRegisterMO " + octet);
        mibORIG.unregisterMOs(server, octet);
    }

    public FScalar getFScalarById(String id) {
        return fScalarMap.get(id);
    }


}