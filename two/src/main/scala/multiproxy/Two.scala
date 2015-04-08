package multiproxy

import akka.actor.{ActorSystem, Props}
import akka.cluster.Cluster
import akka.contrib.pattern.{ClusterSharding, ShardRegion}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

object Two extends App {

   val system = ActorSystem("application")

   import system.dispatcher

   val cluster = Cluster(system)


  val idExtractor: ShardRegion.IdExtractor = {
    case i: Int => (i.toString, i)
  }

  val shardResolver: ShardRegion.ShardResolver = {
    case i: Int => (i % 3).toString
  }

   ClusterSharding(system).start(
     typeName = "TWO",
     entryProps = Some(Props[ActorTwo]),
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
