package com.spark.kudu

import kafka.message.MessageAndMetadata
import org.apache.spark.kudu.olap.kuduImplicitTrait
import org.apache.spark.kudu.olap.caseclassTrait
import com.spark.kudu.entry.MD5Util
import java.util.Random
package object test
    extends kuduImplicitTrait
    with caseclassTrait {
  val zookeeper = "zk1,zk2,zk3"
  val brokers = "kafka1:9092,kafka2:9092,kafka3:9092"
  val kudumaster = "kudu-master2"
  val intopics = "test1,test2"
  val radom = new Random();
  def msgHandle = (mmd: MessageAndMetadata[String, String]) => (mmd.topic, mmd.message)

}