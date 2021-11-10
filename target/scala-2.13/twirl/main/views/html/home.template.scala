
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.data._
import play.core.j.PlayFormsMagicForJava._
import scala.jdk.CollectionConverters._
/*2.2*/import helper._
/*3.2*/import play.mvc.Http.Request
/*4.2*/import dto.formData

object home extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[play.data.Form[formData],play.i18n.Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*6.2*/(newUserForm: play.data.Form[formData])(implicit messages: play.i18n.Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*6.80*/("""
"""),format.raw/*7.1*/("""<!-- HTML Head -->



<body>
   		<h1> Welcome to Gitterific! </h1>
   		"""),_display_(/*13.7*/helper/*13.13*/.form(action = routes.ApiController.save())/*13.56*/ {_display_(Seq[Any](format.raw/*13.58*/("""
        """),_display_(/*14.10*/helper/*14.16*/.inputText(newUserForm("searchInput"))),format.raw/*14.54*/("""
        """),format.raw/*15.9*/("""<button type="submit" class="btn btn-primary btn-block">Search</button>
    """)))}),format.raw/*16.6*/("""
"""),format.raw/*17.1*/("""</body>

"""))
      }
    }
  }

  def render(newUserForm:play.data.Form[formData],messages:play.i18n.Messages): play.twirl.api.HtmlFormat.Appendable = apply(newUserForm)(messages)

  def f:((play.data.Form[formData]) => (play.i18n.Messages) => play.twirl.api.HtmlFormat.Appendable) = (newUserForm) => (messages) => apply(newUserForm)(messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/home.scala.html
                  HASH: 1f79e1eed987c0affac43f258a02146b52302c99
                  MATRIX: 610->3|633->21|669->52|1029->76|1202->154|1230->156|1336->236|1351->242|1403->285|1443->287|1481->298|1496->304|1555->342|1592->352|1700->430|1729->432
                  LINES: 23->2|24->3|25->4|30->6|35->6|36->7|42->13|42->13|42->13|42->13|43->14|43->14|43->14|44->15|45->16|46->17
                  -- GENERATED --
              */
          