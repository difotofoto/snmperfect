package com.difotofoto.snmperfect.snmp.mib;

import com.difotofoto.snmperfect.snmp.NotificationMibFriendly;
import org.apache.log4j.Logger;

public abstract class NotificationScanProcess implements Runnable {

    private static Logger log = Logger.getLogger(NotificationScanProcess.class);

    private boolean alive = false;
    private Thread notificationThread;
    private NotificationMibFriendly nfFriendly;

    public  void onInitialize() {
        // do nothing
    }

    public  void onStart() {
        // do nothing
    }

    public  void onStop() {
        // do nothing
    }

    public void onShutDown() {
        // do nothing
    }

    public abstract void scan();

    public abstract int sleepTime();

    protected void sendNotification(String id) {
        nfFriendly.getFNotification(id).sendNotification();
    }

    protected void sendNotification(NotificationType type) {
        if (nfFriendly == null) {
            log.error("Notification MIB is null.");
        } else {
            if (type == null) {
                log.error("Notification TYPE is null.");
            } else {
                if (nfFriendly.getFNotification(type.getName()) == null) {
                    log.error("Notification with name " + type.getName() + " is not exist.");
                } else {
                    nfFriendly.getFNotification(type.getName()).sendNotification();
                }
            }
        }
    }

    public void initialize() {
        onInitialize();
    }

    public void start() {
        if (!alive) {
            log.warn("Starting ... ");
            alive = true;
            onStart();
            notificationThread = new Thread(this);
            notificationThread.start();
        }
    }

    public void stop() {
        if (alive) {
            log.warn("Stop ... ");
            alive = false;
            notificationThread.interrupt();
            onStop();
        }
    }

    public void shutdown() {
        log.warn("Shutting Down ... ");
        onShutDown();
    }

    public void run() {
        while (alive) {
            try {
                Thread.sleep(sleepTime());
                scan();
            } catch (InterruptedException e) {
                // DO NOTHING
            }
        }
        alive = false;
        onStop();
    }

    /**
     * @return the nfFriendly
     */
    public NotificationMibFriendly getNotificationMibFriendly() {
        return nfFriendly;
    }

    /**
     * @param nfFriendly the nfFriendly to set
     */
    public void setNotificationMibFriendly(NotificationMibFriendly nfFriendly) {
        this.nfFriendly = nfFriendly;
    }

    /**
     * @return the alive
     */
    public boolean isAlive() {
        return alive;
    }

}
