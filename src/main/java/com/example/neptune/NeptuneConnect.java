package com.example.neptune;

import org.apache.tinkerpop.gremlin.driver.HandshakeInterceptor;
import com.amazonaws.neptune.auth.NeptuneSigV4SignerException;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.neptune.auth.NeptuneNettyHttpSigV4Signer;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.structure.T;

public class NeptuneConnect {

   public void init(){
      try{
         Cluster.Builder builder = Cluster.build();
                builder.addContactPoint("mcs-neptune-perf-latest-engine-cluster.cluster-ro-cswfbacmucoc.us-west-2.neptune.amazonaws.com");
                //builder.handshakeInterceptor(HandshakeInterceptor.NO_OP);
                builder.maxConnectionPoolSize(4);
                builder.minConnectionPoolSize(4);
                builder.maxSimultaneousUsagePerConnection(8);
                builder.maxInProcessPerConnection(8);
                builder.maxWaitForConnection(10000);
                builder.maxContentLength(81928192);
                builder.handshakeInterceptor( r ->
                        {
                            NeptuneNettyHttpSigV4Signer sigV4Signer = null;
                            try {
                                sigV4Signer = new NeptuneNettyHttpSigV4Signer("us-west-2",
                                        new DefaultAWSCredentialsProviderChain());
                                sigV4Signer.signRequest(r);
                               //throw new NeptuneSigV4SignerException("test");
                            } catch (NeptuneSigV4SignerException e) {
                                System.out.println("NeptuneSigV4SignerException");
                              //  throw new RuntimeException(e);
                               //System.out.println("1111111111111");
                            }
                            return r;
                        }
                );
                builder.port(8182);
                builder.enableSsl(true);
                builder.trustStore("SFSRootCAG2.pem");
         
         Cluster cluster = builder.create();
         
         Client client = cluster.connect();
         System.out.println("Output -----> "+client.submit("g.V().count()"));
         GraphTraversalSource g = traversal().withRemote(DriverRemoteConnection.using(cluster));

        // Add a vertex.
        // Note that a Gremlin terminal step, e.g. iterate(), is required to make a request to the remote server.
        // The full list of Gremlin terminal steps is at https://tinkerpop.apache.org/docs/current/reference/#terminal-steps
        g.addV("Person").property("Name", "Justin").iterate();

        // Add a vertex with a user-supplied ID.
        g.addV("Custom Label").property(T.id, "CustomId1").property("name", "Custom id vertex 1").iterate();
        g.addV("Custom Label").property(T.id, "CustomId2").property("name", "Custom id vertex 2").iterate();

        //g.addE("Edge Label").from(__.V("CustomId1")).to(__.V("CustomId2")).iterate();

        // This gets the vertices, only.
        GraphTraversal t = g.V().limit(3).elementMap();

        t.forEachRemaining(
            e ->  System.out.println(t.toList())
        );
         //cluster.close();
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
