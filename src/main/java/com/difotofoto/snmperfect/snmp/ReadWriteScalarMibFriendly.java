package com.difotofoto.snmperfect.snmp;

import com.difotofoto.snmperfect.snmp.mib.MaxAccessEnum;
import com.difotofoto.snmperfect.snmp.mib.MibDocument;
import com.difotofoto.snmperfect.snmp.mib.ObjectType;
import com.difotofoto.snmperfect.snmp.mib.ValueCheckResultEnum;
import org.friendlysnmp.AgentWorker;
import org.friendlysnmp.FException;
import org.friendlysnmp.FScalar;
import org.friendlysnmp.ValueValidation;
import org.friendlysnmp.event.FScalarGetListener;
import org.friendlysnmp.event.FScalarSetListener;
import org.friendlysnmp.event.FScalarValidationListener;
import org.friendlysnmp.mib.BaseMib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.mo.DefaultMOFactory;
import org.snmp4j.smi.AbstractVariable;
import org.snmp4j.smi.OctetString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ferdinand on 31/07/2015.
 */
public class ReadWriteScalarMibFriendly extends BaseMib {

    public static Logger logger = LoggerFactory.getLogger(ReadWriteScalarMibFriendly.class);
    private ReadOnlyScalarMib mibORIG;
    private MibDocument mibDocument;

    private Map<String, FScalar> fScalarMap = new HashMap<String, FScalar>();

    public ReadWriteScalarMibFriendly(MibDocument mibDocument) {
        super();
        this.mibDocument = mibDocument;
    }

    @Override
    public void init(AgentWorker agent) throws FException {
        super.init(agent);

        mibORIG = new ReadOnlyScalarMib(DefaultMOFactory.getInstance(), mibDocument);

        for (final ObjectType ot : mibDocument.getObjectTypes()) {
            if (ot.getMaxAccess().equals(MaxAccessEnum.READ_WRITE)) {
                logger.info("Registering Read Write (" + ot.getName() + ") " + ot.getOIDString());
                FScalar fscalar = new FScalar(ot.getName(), mibORIG.getMOScalarByID(ot.getName()), agent);
                fScalarMap.put(ot.getName(), fscalar);
                addNode(fscalar);
                fscalar.addGetListener(new FScalarGetListener() {
                    @Override
                    public void get(FScalar scalar) {
                        Object o = ot.getValueGetter().getValue();
                        logger.info("RW Get - " + ot.getName() + " - " + ot.getOIDString() + " : " + o);
                        scalar.setValueEx(o);
                    }
                });
                fscalar.addSetListener(new FScalarSetListener() {
                    @Override
                    public void set(FScalar fScalar) {
                        AbstractVariable var = (AbstractVariable) fScalar.getValue();
                        logger.info("RW Set - " + ot.getName() + " - " + ot.getOIDString() + " : " + var);
                        ot.getValueSetter().setValue(var);
                    }
                });
                fscalar.addValidationListener(new FScalarValidationListener() {
                    @Override
                    public ValueValidation validate(FScalar fScalar, Object o) {
                        ValueCheckResultEnum result = ot.getValueChecker().checkValue((AbstractVariable) o);
                        logger.info("RW Validation - " + ot.getName() + " - " + ot.getOIDString() + " : " + o + " : " + result);
                        return result.getValueValidation();
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
