package com.spark.kudu.entry
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.kafka.KafkaSparkStreamManager
import kafka.serializer.StringDecoder
import org.slf4j.LoggerFactory
import org.apache.spark.common.util.Configuration
import org.apache.log4j.PropertyConfigurator
import org.apache.spark.func.tool._
import org.apache.log4j.PropertyConfigurator
import org.apache.kudu.spark.kudu.KuduContext
import org.apache.spark.sql.SQLContext
import java.util.Date
import java.text.SimpleDateFormat
/**
 * @author LMQ
 * @description 将sparkstreaming的数据写进kudu。同时使用impala生成OLAP报表存成kudu。
 *
 */
object SparkStreamKuduRunMain {
  val sim = new SimpleDateFormat("yyyy-MM-dd");
  def main(args: Array[String]): Unit = {
    val batchTime = args(0).toLong
    runJob(batchTime)
  }
  def runJob(time: Long) {
    val sc = new SparkContext(new SparkConf().setAppName("SparkStreamKuduRunMain"))
    val kuducontext = new KuduContext(kudumaster, sc)
    val sparksql = new SQLContext(sc)
    import sparksql.implicits._
    val ssc = new StreamingContext(sc, Seconds(time))
    var kp = Map[String, String](
      "metadata.broker.list" -> brokers,
      "serializer.class" -> "kafka.serializer.StringEncoder",
      "group.id" -> "test",
      "newgroup.last.earliest" -> "earliest", //如果是新的group id 就从最新还是最旧开始
      "kafka.last.consum" -> "consum")
    val topics = intopics.split(",").toSet
    val ds = ssc.createDirectStream[(String, String)](kp, topics, msgHandle)
    var count = 0L
    ds.foreachRDD { rdd =>
      //val df=rdd.toDF 
      //将数据插入表中default.test
      //kuducontext.insertRows(df, "impala::default.test")
      //KuduImpalaUtil.execute(s"""sql """)
      rdd.updateOffsets(kp, "test")
    }
    ssc.start()
    ssc.awaitTermination()
  }
}