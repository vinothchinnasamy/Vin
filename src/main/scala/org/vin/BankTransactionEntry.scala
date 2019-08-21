package org.vin

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord}

import scala.io.StdIn._

object BankTransactionEntry {

  def readTransaction() ={

    print(s"Please enter the account number : ")
    val accountNumber = readLine()
    println("-------------------------------------")
    print("Please enter the transaction type : ")
    val transactionType = readLine()
    println("-------------------------------------")
    print("Please enter the transaction Amount : ")
    val transactionAmount = readFloat()

    val messageToPublish = accountNumber + ":" + transactionType + ":" + transactionAmount

    publishMessageToKafka("topic1",messageToPublish,true)

  }

  def publishMessageToKafka(topic:String,messageToPublish: String,inSync:Boolean = true) = {
    val kafkaProducer = KafkaProducerFactory.getKafkaProducer()
    val producerRecord = produceKafkaMessage(topic,messageToPublish)
      if (inSync)
        {
          produceSync(kafkaProducer,producerRecord)
        }
      else {
        produceAsync(kafkaProducer,producerRecord, new AsyncCallBack)
      }

  }

  def produceSync(kafkaProducer:KafkaProducer[String,String],
                  producerRecord: ProducerRecord[String,String]) = {
    val metadata = kafkaProducer.send(producerRecord).get()

    if (metadata != null) {
      println("-----------------Succesfully published the messages----------------------")
      println(s"Summary of published messages ")
      println(s"Details of  metadata: topic ${metadata.topic()}")
      println(s"Details of  metadata: partition ${metadata.partition()}" )
      println(s"Details of  metadata: offset ${metadata.offset()}")
    }
  }

  def produceAsync(kafkaProducer:KafkaProducer[String,String],
                   producerRecord: ProducerRecord[String,String],
                   callBackObject: Callback) ={

    val k = kafkaProducer.send(producerRecord,callBackObject)
    println(k.isDone)
  }



  def produceKafkaMessage(topic: String,messageToPublish: String) = {
    new ProducerRecord[String,String](topic,messageToPublish)
  }

  def main(args: Array[String]) = {
    readTransaction();
  }

}
