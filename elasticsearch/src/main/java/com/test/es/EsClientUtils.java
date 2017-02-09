package com.test.es;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class EsClientUtils {
    private static final Logger logger = Logger.getLogger(EsClientUtils.class);

    public final static AtomicReference<TransportClient> clientReference = new AtomicReference<TransportClient>();

    private static volatile boolean alive = true;

    private static volatile boolean started = false;

    public static Client getClient() {

        while (clientReference.get() == null || clientReference.get().connectedNodes().size() == 0) {

            logger.info("ClientUtil.getClient() " + clientReference.get());

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch(InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        return clientReference.get();
    }

    public static synchronized void initClient(final String esname, final String eshostStr) {

        logger.debug("esname-------------- " + esname);
        logger.debug("eshostStr----------- " + eshostStr);

        if (started) {
            logger.info("Client has started " + EsClientUtils.getClient());
            return;
        }

        init(esname, eshostStr);

        Thread t = new Thread(() -> {
            while (alive) {
                if (clientReference.get() == null || clientReference.get().connectedNodes().size() == 0) {
                    init(esname, eshostStr);
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch(InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            logger.info("clientReference is " + clientReference.get());

        });

        t.start();
    }

    private static void init(String esname, String eshostStr) {
        try {
            Settings settings = ImmutableSettings.settingsBuilder()
                    .put("client.transport.ping_timeout", 30000).put("client.transport.sniff", true)
                    .put("cluster.name", esname).build();

            TransportClient client = new TransportClient(settings);

            String[] eshost = eshostStr.split(",");

            for (int i = 0; i < eshost.length; i++) {
                client.addTransportAddress(new InetSocketTransportAddress(eshost[i], 9300));
            }

            ImmutableList<DiscoveryNode> list = client.connectedNodes();

            for (DiscoveryNode discoveryNode : list) {
                logger.info(discoveryNode.getHostAddress() + " " + discoveryNode.getHostName() + " "
                        + discoveryNode.getAddress());
            }

            logger.info("client.connectedNodes().size()==" + client.connectedNodes().size());
            clientReference.set(client);
            started = true;
        } catch(Throwable e) {
            System.err.println("ClientUtil.initClient eror ---" + e.getMessage());
        }
    }

    public static synchronized void close() {
        alive = false;
        Client client = clientReference.get();
        if (client != null) {
            client.close();
            client = null;

        } else {
            logger.info("ClientUtil.close() Client is null ");

        }
        started = false;
        logger.info("ClientUtil.close() " + alive);
    }
}
