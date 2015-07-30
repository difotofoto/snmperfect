package com.difotofoto.snmperfect.snmp;

import com.difotofoto.snmperfect.snmp.mib.MibDocument;
import com.difotofoto.snmperfect.snmp.mib.NotificationType;
import org.apache.log4j.Logger;
import org.friendlysnmp.AgentWorker;
import org.friendlysnmp.FException;
import org.friendlysnmp.FNotification;
import org.friendlysnmp.mib.BaseMib;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.mo.DefaultMOFactory;
import org.snmp4j.smi.OctetString;

import java.util.HashMap;
import java.util.Map;

public class NotificationMibFriendly extends BaseMib {

    public static Logger logger = Logger.getLogger(NotificationMibFriendly.class);
    private NotificationMib mibORIG;


    private Map<String, FNotification> notificationMap = new HashMap<String, FNotification>();
    private MibDocument mibDocument;

    public NotificationMibFriendly(MibDocument mibDocument) {
        super();
        this.mibDocument = mibDocument;
    }

    public void init(AgentWorker agent) throws FException {
        super.init(agent);
        mibORIG = new NotificationMib(DefaultMOFactory.getInstance());
        for (NotificationType ntype : mibDocument.getNotificationTypes()) {
            logger.info("Registering Notification (" + ntype.getName() + ") " + ntype.getOIDString());
            FNotification fn = new FNotification(ntype.getName(), ntype.getOID(), agent);
            notificationMap.put(ntype.getName(), fn);
            addNode(fn);
        }
    }

    @Override
    public void registerMOs(MOServer server, OctetString octet)
            throws DuplicateRegistrationException {
        mibORIG.registerMOs(server, octet);
    }

    @Override
    public void unregisterMOs(MOServer server, OctetString octet) {
        mibORIG.unregisterMOs(server, octet);
    }

    public FNotification getFNotification(String id) {
        return notificationMap.get(id);
    }

}
