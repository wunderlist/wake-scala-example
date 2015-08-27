import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

class UsersController extends Resource {

  val name = "users"

  override def index = complete("{}")
  override def show = complete { HttpResponse(entity = "Hello world!") }
}
object HTTPService {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load

  def run = {
    val interface = Option(config.getString("http.interface")).getOrElse("0.0.0.0")
    val port = Option(config.getInt("http.port")).getOrElse(9000)
    println(s"Running http server on $interface:$port")
    Http().bindAndHandle(
      Router.resources(new UsersController),
      interface,
      port
    )
  }
}

object Main {
  def main(argv: Array[String]):Unit = {
    HTTPService.run
  }
}
