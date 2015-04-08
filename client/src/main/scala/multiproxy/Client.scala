package multiproxy

import akka.actor.ActorSystem
import akka.cluster.Cluster
import akka.contrib.pattern.{ShardRegion, ClusterSharding}

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

object Client extends App {

  val system = ActorSystem("application")

  import system.dispatcher

  val cluster = Cluster(system)

  val idExtractor1: ShardRegion.IdExtractor = {
    case i: Int => (i.toString, i)
  }

  val shardResolver1: ShardRegion.ShardResolver = {
    case i: Int => (i % 3).toString
  }


  val idExtractor2: ShardRegion.IdExtractor = {
    case i: Int => (i.toString, i)
  }

  val shardResolver2: ShardRegion.ShardResolver = {
    case i: Int => (i % 3).toString
  }


  val one = ClusterSharding(system).start(
    typeName = "ONE",
    entryProps = None,
    idExtractor = idExtractor1 ,
    shardResolver = shardResolver1
  )

  val two = ClusterSharding(system).start(
    typeName = "TWO",
    entryProps = None,
    idExtractor = idExtractor2 ,
    shardResolver = shardResolver2
  )

  system.scheduler.schedule(5 seconds, 2 seconds)( one ! Random.nextInt())
  system.scheduler.schedule(6 seconds, 2 seconds)( two ! Random.nextInt())


  sys.addShutdownHook {
    system.shutdown()
    system.awaitTermination(10 seconds)
  }

}
