/**
 *
 */
package com.test.consistent;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

/**
 * @author xuechongyang
 * 
 */
public final class ZkClient {

    private  ZooKeeper zk = null;

    private final  Lock lock = new ReentrantLock();

    private  boolean connected = false;

    private  String root = "";

    private  int timeout = 1000;

    private  String nodes = "";

    private  List<Map<String, String>> users = null;

    private  final Logger log = Logger.getLogger(ZkClient.class);

    public  final String CREATOR_ALL_ACL = "CREATOR_ALL_ACL";

    public  final String OPEN_ACL_UNSAFE = "OPEN_ACL_UNSAFE";

    public  final String READ_ACL_UNSAFE = "READ_ACL_UNSAFE";

    public ZkClient(String server) {
        this.nodes = server;
        timeout = 5000;
        root = "";
        try {
            reconnect();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public  void reconnect() throws IOException {
        lock.lock();
        try {
            if (connected) {
                log.info("zk already connected. init() do nothing and return.");
                return;
            }
            final AtomicInteger count = new AtomicInteger(1);
            zk = new ZooKeeper(nodes, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (KeeperState.SyncConnected.equals(event.getState())) {
                        count.set(0);
                    } else if (KeeperState.Expired.equals(event.getState())) {
                        connected = false;
                        try {
                            zk.close();
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.warn("Session of Zookeeper is Expired,is restarting");
                        try {
                            reconnect();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                        log.warn("Zookeeper is restarted.");
                    }
                }
            });

            while (true) {
                if (count.intValue() == 0) {
                    connected = true;
                    break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            // log.error(e.getMessage());
            connected = false;
            throw e;
        } finally {
            lock.unlock();
        }
    }

    public  ZooKeeper getZookeeper() {
        return zk;
    }

    public  boolean exists(String path) {
        try {
            return zk.exists(path, false) != null;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public  boolean exists(String path, Watcher watcher) {
        try {
            return zk.exists(path, watcher) != null;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public  String create(String path, String data, String auth, CreateMode mode)
        throws ServiceException {
        if (!path.startsWith(root)) {
            path = root + path;
        }
        List<ACL> acl;
        if (auth!=null && !auth.equals("")) {
            acl = Ids.OPEN_ACL_UNSAFE;
        } else if (auth.equals(CREATOR_ALL_ACL)) {
            acl = Ids.CREATOR_ALL_ACL;
        } else if (auth.equals(OPEN_ACL_UNSAFE)) {
            acl = Ids.OPEN_ACL_UNSAFE;
        } else {
            acl = Ids.READ_ACL_UNSAFE;
        }

        try {
            byte[] bytes = null;
            if (data != null) {
                bytes = data.getBytes("UTF-8");
            }
            String resultPath = zk.create(path, bytes, acl, mode);
            return resultPath;
        } catch(Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public  void delete(String path) throws ServiceException {
        if (!path.startsWith(root)) {
            path = root + path;
        }
        List<String> children = getChildren(path);
        if (children != null && children.size() > 0) {
            for (String child : children) {
                delete(path + "/" + child);
            }
        }
        try {
            zk.delete(path, -1);
        } catch(Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public  void update(String path, String data) throws ServiceException {
        try {
            if (!path.startsWith(root)) {
                path = root + path;
            }
            zk.setData(path, data.getBytes("UTF-8"), -1);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public  void sync(String path) {
        try {
            zk.sync(path, null, null);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public  String getData(String path) {
        try {
            if (!path.startsWith(root)) {
                path = root + path;
            }
            byte[] data = zk.getData(path, false, null);
            return new String(data);
        } catch(KeeperException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  String getData(String path, Watcher watcher) {
        try {
            if (!path.startsWith(root)) {
                path = root + path;
            }
            byte[] data = zk.getData(path, watcher, null);
            return new String(data);
        } catch(KeeperException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  List<String> getChildren(String path) {
        List<String> nodes = null;
        try {
            if (!path.startsWith(root)) {
                path = root + path;
            }
            nodes = zk.getChildren(path, null);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        return nodes;
    }

    public  List<String> getChildren(String path, Watcher watcher) {
        List<String> nodes = null;
        try {
            if (!path.startsWith(root)) {
                path = root + path;
            }
            nodes = zk.getChildren(path, watcher);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        return nodes;
    }

    public  Properties getProps(String path) {

        Properties prop = new Properties();
        try {
            if (!path.startsWith(root)) {
                path = root + path;
            }
            byte[] datas = zk.getData(path, false, null);
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(datas));
            prop.load(dis);
            dis.close();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return prop;
    }

    public  void setAcls(String path, List<ACL> acl, int version) {
        try {
            if (!path.startsWith(root)) {
                path = root + path;
            }
            zk.setACL(path, acl, version);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
