package com.example.neptune;

import com.amazonaws.neptune.auth.NeptuneSigV4SignerException;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.neptune.auth.NeptuneNettyHttpSigV4Signer;

public class NeptuneConnect {

   public void init(){

        System.out.println("*****");
      try{
         Cluster.Builder builder = Cluster.build();
                builder.addContactPoint("demo.cluster-custom-chuoniryb3ms.ap-southeast-2.neptune.amazonaws.com");
                builder.handshakeInterceptor( r ->
                        {
                            NeptuneNettyHttpSigV4Signer sigV4Signer = null;
                            try {
                               System.out.println("555555555555");
                                sigV4Signer = new NeptuneNettyHttpSigV4Signer(null,
                                        new DefaultAWSCredentialsProviderChain());
                                sigV4Signer.signRequest(r);
                               //throw new NeptuneSigV4SignerException("test");
                            } catch (NeptuneSigV4SignerException e) {
                                System.out.println("1111111111111");
                                throw new RuntimeException(e);
                               //System.out.println("1111111111111");
                            }
                            return r;
                        }
                );
                System.out.println("22222222222222");
                builder.port(8182);
                System.out.println("33333333333333");
                builder.enableSsl(true);
         
        Client client = builder.create().connect();
          System.out.println("444444444444444");
        System.out.println(client.submit("g.V().has('code','IAD')").all());
      }
      catch(Exception e){
         System.out.println("Errorrrrrrrr"+e);
      }
    }



 /**  public void init(){

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
    //}
}
