package com.example.neptune;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.driver.SigV4WebSocketChannelizer;

public class NeptuneConnect {

  /*  public void init(){

        System.out.println("*****");

        Cluster cluster = Cluster.build("demo.cluster-custom-chuoniryb3ms.ap-southeast-2.neptune.amazonaws.com")
                .port(8182)
                .enableSsl(true)
                .handshakeInterceptor( r ->
                        {
                            NeptuneNettyHttpSigV4Signer sigV4Signer = null;
                            try {
                                sigV4Signer = new NeptuneNettyHttpSigV4Signer("ap-southeast-2",
                                        new DefaultAWSCredentialsProviderChain());
                                sigV4Signer.signRequest(r);
                            } catch (NeptuneSigV4SignerException e) {
                                e.printStackTrace();
                            }
                            return r;
                        }
                ).create();
        Client client = cluster.connect();
        System.out.println(client.submit("g.V().has('code','IAD')").all());
    }
    */



   public void init(){

        System.out.println("*****");

        Cluster cluster = Cluster.build("demo.cluster-custom-chuoniryb3ms.ap-southeast-2.neptune.amazonaws.com")
                .port(8182)
                .enableSsl(true)
                .maxConnectionPoolSize(4)
                .minConnectionPoolSize(4)
                .maxSimultaneousUsagePerConnection(8)
                //.maxInProcessPerConnection(maxInProcessPerConnection)
                .maxWaitForConnection(10000)
                .channelizer(SigV4WebSocketChannelizer.class)
                .create();
        Client client = cluster.connect();
        System.out.println("Output -----> "+client.submit("g.V().count()"));


           //final Cluster cluster = builder.create();
           /**try {
               final Client client = cluster.connect();
               final ResultSet rs = client.submit("g.V().count()");

               for (Result r : rs) {
                   System.out.println("output -->"+r);
               }
           } finally {
               cluster.close();
           }**/
    }
}
