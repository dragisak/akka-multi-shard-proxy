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


  val idExtractor: ShardRegion.IdExtractor = {
    case msg: Int => (msg.toString, msg)
  }

  val shardResolver: ShardRegion.ShardResolver = {
    case msg: Int => (msg % 3).toString
  }


  val w1 = ClusterSharding(system).start(
    typeName = "W1",
    entryProps = None,
    idExtractor = idExtractor ,
    shardResolver = shardResolver
  )

  val w2 = ClusterSharding(system).start(
    typeName = "W2",
    entryProps = None,
    idExtractor = idExtractor ,
    shardResolver = shardResolver
  )

  system.scheduler.schedule(5 seconds, 2 seconds)( w1 ! Random.nextInt())
  system.scheduler.schedule(6 seconds, 2 seconds)( w2 ! Random.nextInt())


  sys.addShutdownHook {

    cluster.leave(cluster.selfAddress)

    Await.ready(Future{Thread.sleep(2000)}, 3 seconds) // wait few secs for cluster leave message to propagate

    system.shutdown()
    system.awaitTermination(10 seconds)
  }

}
