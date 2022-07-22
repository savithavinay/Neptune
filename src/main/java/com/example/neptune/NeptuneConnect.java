package com.example.neptune;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.neptune.auth.NeptuneNettyHttpSigV4Signer;
import com.amazonaws.neptune.auth.NeptuneSigV4SignerException;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class NeptuneConnect {

    public void init(){

        System.out.println("*****");

        Cluster cluster = Cluster.build("demo.cluster-custom-chuoniryb3ms.ap-southeast-2.neptune.amazonaws.com")
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
}
