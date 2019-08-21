package org.vin

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, _}
import org.apache.kafka.common.serialization.{IntegerSerializer, StringSerializer}

object KafkaProducerFactory {

  private var  producer: KafkaProducer[String, String] = _

  def getKafkaProducer(): KafkaProducer[String, String] = {

    if (producer == null) {
      //TODO implement reading of conf file and populate the kafka producer properties
      val producerProps = new Properties
      producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
      producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[IntegerSerializer])
      producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])

      producer = new KafkaProducer[String, String](producerProps)
    }
    producer
  }

}
