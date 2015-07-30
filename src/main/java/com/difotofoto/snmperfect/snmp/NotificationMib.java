package com.difotofoto.snmperfect.snmp;

import org.apache.log4j.Logger;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.NotificationOriginator;
import org.snmp4j.agent.mo.MOFactory;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;

/**
 * This class serve as the generic notification/trap context object used by SNMP4J.
 * It will be used to register/unregister and create Managed Object for notification purpose.
 *
 * @author Fneman
 */
public class NotificationMib implements MOGroup {

    public static Logger logger = Logger.getLogger(NotificationMib.class);

    public NotificationMib() {
        super();
    }


    public NotificationMib(MOFactory moFactory) {
        super();
        createMO(moFactory);
    }

    public void sendNotification(NotificationOriginator notificationOriginator,
                                 OctetString context, VariableBinding[] vbs, OID oid) {
        notificationOriginator.notify(context, oid, vbs);
    }

    protected void createMO(MOFactory moFactory) {
        addTCsToFactory(moFactory);
    }


    public void registerMOs(MOServer arg0, OctetString arg1)
            throws DuplicateRegistrationException {

    }


    public void unregisterMOs(MOServer arg0, OctetString arg1) {

    }

    protected void addTCsToFactory(MOFactory moFactory) {
    }

    public void addImportedTCsToFactory(MOFactory moFactory) {
    }
}
