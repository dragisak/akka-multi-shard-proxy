package multiproxy

import akka.actor.{ActorSystem, Props}
import akka.cluster.Cluster
import akka.contrib.pattern.{ClusterSharding, ShardRegion}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

object W1 extends App {

   val system = ActorSystem("application")

   import system.dispatcher

   val cluster = Cluster(system)


   val idExtractor: ShardRegion.IdExtractor = {
     case msg: Int => (msg.toString, msg)
   }

   val shardResolver: ShardRegion.ShardResolver = {
     case msg: Int => (msg % 3).toString
   }


   ClusterSharding(system).start(
     typeName = "W1",
     entryProps = Some(Props[W1Actor]),
     idExtractor = idExtractor ,
     shardResolver = shardResolver
   )

   sys.addShutdownHook {

     cluster.leave(cluster.selfAddress)

     Await.ready(Future{Thread.sleep(2000)}, 3 seconds) // wait few secs for cluster leave message to propagate

     system.shutdown()
     system.awaitTermination(10 seconds)
   }

 }
