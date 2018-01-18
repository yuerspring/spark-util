package com.spark.kudu

import org.apache.spark.kudu.olap.kuduImplicitTrait
import kafka.message.MessageAndMetadata
import org.slf4j.LoggerFactory
import org.apache.spark.kudu.olap.caseclassTrait
import java.util.Random
package object entry extends kuduImplicitTrait
    with caseclassTrait {
  val LOG = LoggerFactory.getLogger("kudureport")
  val zookeeper = "zk1,zk2,zk3"
  val brokers = "kafka1:9092,kafka2:9092,kafka3:9092"
  val kudumaster = "kudu-master2"
  val intopics = "test1,test2"
  val radom = new Random();
  def msgHandle = (mmd: MessageAndMetadata[String, String]) => (mmd.topic, mmd.message)

}