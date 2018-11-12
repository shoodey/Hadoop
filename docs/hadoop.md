# Hadoop

This section will delineate the setup required to install hadoop on our virtual machines.

## Installation

On each VM we will download the hadoop project **using version 3.1.1, the most recent at the time** doing the following:

?> _hduser@master_ cd ~

?> _hduser@master_ wget http://mirror.cc.columbia.edu/pub/software/apache/hadoop/common/hadoop-3.1.1/hadoop-3.1.1.tar.gz

?> _hduser@master_ tar xvzf hadoop-3.0.1.tar.gz -C hadoop


## Configuration

?> _hduser@master_ cd hadoop/etc/hadoop

We then update the configuration files to the following (vi, vim, nano can be used):

?> _hduser@master_ vim hadoop-env.sh

and add the following lines:
```
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64

export HDFS_NAMENODE_USER="hduser"
export HDFS_DATANODE_USER="hduser"
export HDFS_SECONDARYNAMENODE_USER="hduser"
export YARN_RESOURCEMANAGER_USER="hduser"
export YARN_NODEMANAGER_USER="hduser"
```

We then source the file:

?> _hduser@master_ source hadoop-env.sh

We need to update another configuration file:

?> _hduser@master_ vim core-site.xml

and add the following lines:

```
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://master:9000</value>
    </property>
</configuration>
```

## Data Directory

We need to create a data directory for the Hadoop Distributed File System (HDFS) to store all relevant HDFS files

?> _hduser@master_ sudo mkdir -p /usr/local/hadoop/hdfs/data

?> _hduser@master_ sudo chown -R hduser:hduser /usr/local/hadoop/hdfs/data

## Master Configuration

We configure the following file to contain:

?> _hduser@master_ vim hdfs-site.xml

```
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>3</value>
    </property>
    <property>
        <name>dfs.namenode.name.dir</name>
        <value>file:///usr/local/hadoop/hdfs/data</value>
    </property>
</configuration>
```

?> _hduser@master_ vim mapred-site.xml

```
<configuration>
    <property>
        <name>mapreduce.jobtracker.address</name>
        <value>master:54311</value>
    </property>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>

    <property>
        <name>yarn.app.mapreduce.am.env</name>
        <value>HADOOP_MAPRED_HOME=/home/hduser/hadoop</value>
    </property>
    <property>
        <name>mapreduce.map.env</name>
        <value>HADOOP_MAPRED_HOME=/home/hduser/hadoop</value>
    </property>
    <property>
        <name>mapreduce.reduce.env</name>
        <value>HADOOP_MAPRED_HOME=/home/hduser/hadoop</value>
    </property>
</configuration>
```

?> _hduser@master_ vim yarn-site.xml

```
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
        <value>org.apache.hadoop.mapred.ShuffleHandler</value>
    </property>
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>master</value>
    </property>
</configuration>
```
?> _hduser@master_ vim masters

```
master
```

?> _hduser@master_ vim worker

```
worker1
worker2
worker3
worker4
worker5
```

## Workers Configuration

On our only worker Base-Worker we do the following:


?> _hduser@master_ vim hdfs-site.xml

```
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>3</value>
    </property>
    <property>
        <name>dfs.datanode.data.dir</name>
        <value>file:///usr/local/hadoop/hdfs/data</value>
    </property>
</configuration>
```