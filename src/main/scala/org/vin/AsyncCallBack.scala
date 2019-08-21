package org.vin

import org.apache.kafka.clients.producer.{Callback, RecordMetadata}

class AsyncCallBack extends Callback{

   def onCompletion(recordMetadata:RecordMetadata,e:Exception) = {
    if(recordMetadata != null)
      {
        println(s"Details of the metadata are: ${recordMetadata.topic()} : ${recordMetadata.partition()} : ${recordMetadata.offset()}")
      }
    else {
      println(s"Exception Message: ${e.getMessage}")
    }
  }

}
