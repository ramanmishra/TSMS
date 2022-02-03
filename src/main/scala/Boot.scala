import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout
import com.tsms.actor.AppSupervisorActor
import com.tsms.constants.MiscConstants.SYSTEM
import com.tsms.database.DBConnector
import com.tsms.service.{RatingService, SeriesService, SpoilerAlertService, UserService}
import com.tsms.utils.ConfigProvider

import java.sql.Connection
import java.util.concurrent.TimeUnit

/**
 * Entry point of the application
 */
object Boot extends App with ConfigProvider {
  implicit val system: ActorSystem = ActorSystem(SYSTEM)
  implicit val timeout: Timeout = Timeout(10, TimeUnit.SECONDS)
  val dbConn: Connection = DBConnector().getConnection
  DBConnector().runMigration()
  val supervisorActor = system.actorOf(AppSupervisorActor.props(dbConn))
  val route = UserService(supervisorActor) ~ SeriesService(supervisorActor) ~ SpoilerAlertService(supervisorActor) ~ RatingService(supervisorActor)

  val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)


  println(s"Server now online. Please navigate to http://localhost:8080/\n")
}
