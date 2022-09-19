# Neptune

sudo yum update -y
sudo yum install git
sudo yum install maven (sudo yum revolve maven)
sudo yum install java-1.8.0-devel/sudo yum install java-1.8.0-openjdk

wget https://archive.apache.org/dist/tinkerpop/3.5.2/apache-tinkerpop-gremlin-console-3.5.2-bin.zip

git clone https://github.com/savithavinay/Neptune.git

Mvn clean install
Mvn spring-boot:run


**Delete .m2 and .groovy/grapes

rm ../apache-tinkerpop-gremlin-console-3.4.9/lib/netty-all-*.jar


cluster = Cluster.build("demo1.cluster-chuoniryb3ms.ap-southeast-2.neptune.amazonaws.com").enableSsl(true).handshakeInterceptor{ r ->def sigV4Signer = new NeptuneNettyHttpSigV4Signer("ap-southeast-2", new DefaultAWSCredentialsProviderChain()); sigV4Signer.signRequest(r); return r  }.create()


Ref https://docs.aws.amazon.com/neptune/latest/userguide/access-graph-gremlin-console.html
https://docs.aws.amazon.com/neptune/latest/userguide/iam-auth-connecting-gremlin-console.html
