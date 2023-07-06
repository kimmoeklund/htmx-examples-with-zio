package fi.ke.htmx.examples

import zio.*
import zio.http.{html as _, *}
import zio.http.html.*
import zio.http.html.Attributes.PartialAttribute
import zio.http.html.Html.fromDomElement

/*
https://htmx.org/examples/update-other-content/
 */
object UpdatingOtherContentSol2:
  val path = "updating-other-content-2"

  private def page = html(
    htmxHead ++ Html.fromString(
      """
      |   <h2>Contacts</h2>
      |   <table class="table">
      |   <thead>
      |      <tr>
      |        <th>Name</th>
      |        <th>Email</th>
      |        <th></th>
      |      </tr>
      |    </thead>
      |    <tbody id="contacts-table">
      |    </tbody>
      |  </table>
      |  <h2>Add A Contact</h2>""".stripMargin
    ) ++ form
  )

  private def form = Html.fromString(s"""
       |    <form hx-post="/${this.path}"
       |      <label>
       |        Name
       |            <input name="name" type="text">
       |      </label>
       |      <label>
       |        Email
       |            <input name="email" type="email">
       |      </label>
       |        <button type="submit" class="btn btn-primary">Add</button>
       |    </form>
       |
  """.stripMargin)

  private def otherContentResponse(name: String, email: String): Html =
    Html.fromString(
      s"""
      <tbody hx-swap-oob="afterend:#contacts-table">
        <tr>
            <td>${name}</td>
            <td>${email}</td>
        </tr>
      </tbody>
    """.stripMargin
    ) ++ form

  def apply(): UHttpApp = Http.collectZIO[Request]:
    case Method.GET -> Root / this.path => ZIO.succeed(Response.html(page))

    case req @ Method.POST -> Root / this.path =>
      val effect = for {
        form <- req.body.asURLEncodedForm
        name <- ZIO.fromOption(form.get("name").get.stringValue)
        email <- ZIO.fromOption(form.get("email").get.stringValue)
      } yield (name, email)
      effect.foldZIO(
        _ => ZIO.succeed(Response.status(Status.BadRequest)),
        (name, email) => ZIO.succeed(htmlSnippet(otherContentResponse(name, email)))
      )

end UpdatingOtherContentSol2
