package com.example.neptune;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.SigV4WebSocketChannelizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class NeptuneConnect {

    public void init(){

        System.out.println("*****");

        Cluster cluster = Cluster.build("demo.cluster-custom-chuoniryb3ms.ap-southeast-2.neptune.amazonaws.com")
                .port(8182)
                .enableSsl(true)
                .channelizer(SigV4WebSocketChannelizer.class)
                .create();
        Client client = cluster.connect();
        System.out.println(client.submit("g.V().has('code','IAD')").all());
    }
}
