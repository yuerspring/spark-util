# spark-kudu

scala version 2.10
spark version 1.3.0 1.6.0
kudu version 1.3.0

* 读取kafka的数据流，实时将数据写入kudu中
* 提供 对外的 读取 kudu 数据的 Api(可按照scan过滤数据)。
* 提供 对外的 rdd 写入Kudu的方法
* 提供 impala查询kudu的方法

我的 kudu 博客地址 

http://blog.csdn.net/a1043498776/article/details/72681890


# Example SparkStreamKuduTest
> SparkStreamKuduTest 流式 
```
val sc = new SparkContext(new SparkConf().setMaster("local[5]").setAppName("Test"))
val kuducontext = new KuduContext(kudumaster, sc)
val sparksql = new SQLContext(sc)
import sparksql.implicits._
val ssc = new StreamingContext(sc, Seconds(15))
val ds = ssc.createDirectStream[(String,String)](kp, topics, msgHandle)
ds.foreachRDD { rdd =>
      //val df=rdd.toDF 
      //将数据插入表中default.test
      //kuducontext.insertRows(df, "impala::default.test")
      //KuduImpalaUtil.execute(s"""sql """)
      rdd.updateOffsets(kp, "test")
}
ssc.start()
ssc.awaitTermination()

```




