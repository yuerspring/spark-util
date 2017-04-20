package org.apache.spark.func.tool

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka.KafkaSparkStreamManager
import kafka.serializer.StringDecoder
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import scala.reflect.ClassTag
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.common.util.Configuration

trait SparkImplicitTool {
  implicit class sscFunc(ssc: StreamingContext) {
    def createDirectStream[R: ClassTag](
      kp: Map[String, String],
      topics: Set[String],
      fromOffset: Map[TopicAndPartition, Long],
      msgHandle: (MessageAndMetadata[String, String]) => R) :InputDStream[R]={
      KafkaSparkStreamManager.createDirectStream
      [String, String, StringDecoder, StringDecoder, R](ssc,kp,topics, fromOffset, msgHandle)
    }
    def createDirectStream[R: ClassTag](
      conf: Configuration,
      topics: Set[String],
      fromOffset: Map[TopicAndPartition, Long]=null,
      msgHandle: (MessageAndMetadata[String, String]) => R) :InputDStream[R]={
      KafkaSparkStreamManager.createDirectStream
      [String, String, StringDecoder, StringDecoder, R](ssc,conf,topics, fromOffset, msgHandle)
    }
  }
}