package fi.ke.htmx.examples

import zio.http.{Response, Status, Headers, Header, MediaType, Body}
import zio.http.html.*
import zio.http.html.Attributes.PartialAttribute

def htmxHead = head(
  Html.fromString("""<meta name="htmx-config" content='{"useTemplateFragments":true}'>"""),
  title("Permissions"),
  link(
    relAttr := "stylesheet",
    hrefAttr := "https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
  ),
  script(
    srcAttr := "https://unpkg.com/htmx.org@1.9.2",
    PartialAttribute(
      "integrityAttr"
    ) := "sha384-L6OqL9pRWyyFU3+/bjdSri+iIphTN/bvYyM37tICVyOJkWZLpP2vGn6VUEXgzg6h",
    PartialAttribute("crossoriginAttr") := "anonymous"
  )
)

def htmlSnippet(data: Html, status: Status = Status.Ok): Response = Response(
  status,
  Headers(Header.ContentType(MediaType.text.html).untyped),
  Body.fromCharSequence(data.encode)
)
