package fi.ke.htmx.examples

import zio.*
import zio.logging.{LogFormat, console}
import zio.http.*
import zio.logging.backend.SLF4J

import java.io.File

object MainApp extends ZIOAppDefault:
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    Runtime.removeDefaultLoggers >>> SLF4J.slf4j

  private val indexPage = Http.collectHttp[Request]:
    case Method.GET -> Root =>
    Http.fromFile(new File("src/main/resources/index.html"))

  def run: ZIO[Any, Throwable, Unit] =
    Server
      .serve(indexPage.withDefaultErrorResponse ++ UpdatingOtherContentSol2())
      .provide(Server.default)
      .zipPar(ZIO.logInfo("Example application started, please open http://localhost:8080 in your browser"))
