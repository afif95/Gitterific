
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
/*1.2*/import helper._
/*2.2*/import play.mvc.Http.Request
/*3.2*/import dto.formData
/*4.2*/import dto.Repository

object home extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[play.data.Form[formData],List[Repository],Request,play.i18n.Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*7.2*/(newUserForm: play.data.Form[formData], repoList: List[Repository])(implicit request: Request, messages: play.i18n.Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*7.126*/("""
"""),format.raw/*8.1*/("""<!-- HTML Head -->



<body>
   		<h1> Welcome to Gitterific! </h1>
   		"""),_display_(/*14.7*/helper/*14.13*/.form(action = routes.ApiController.fetchRepos())/*14.62*/ {_display_(Seq[Any](format.raw/*14.64*/("""
        """),_display_(/*15.10*/helper/*15.16*/.inputText(newUserForm("searchInput"))),format.raw/*15.54*/("""
        """),format.raw/*16.9*/("""<button type="submit" class="btn btn-primary btn-block">Search</button>

       
        
    """)))}),format.raw/*20.6*/("""



    """),format.raw/*24.5*/("""<style>
        table, th, td """),format.raw/*25.23*/("""{"""),format.raw/*25.24*/("""
         """),format.raw/*26.10*/("""border: 1px solid black;
        """),format.raw/*27.9*/("""}"""),format.raw/*27.10*/("""
        """),format.raw/*28.9*/("""</style>
    """),_display_(/*29.6*/if(!repoList.isEmpty | repoList != null)/*29.46*/    {_display_(Seq[Any](format.raw/*29.51*/("""
    """),format.raw/*30.5*/("""<table>
        <thead>
          
        </thead>
        <tbody>

        
        
        """),_display_(/*38.10*/for(repo <- repoList) yield /*38.31*/ {_display_(Seq[Any](format.raw/*38.33*/("""
            
           """),format.raw/*40.12*/("""<tr>
            <td><a class="btn btn-link" href=""""),_display_(/*41.48*/routes/*41.54*/.ApiController.getRepository(repo.getOwner().getLogin(), repo.getName())),format.raw/*41.126*/("""">"""),_display_(/*41.129*/repo/*41.133*/.getFull_name()),format.raw/*41.148*/("""</td>
            <td>"""),_display_(/*42.18*/repo/*42.22*/.getName()),format.raw/*42.32*/("""</td>
            <td><a class="btn btn-link" href=""""),_display_(/*43.48*/routes/*43.54*/.ApiController.getOwner(repo.getOwner().getLogin())),format.raw/*43.105*/("""">"""),_display_(/*43.108*/repo/*43.112*/.getOwner().getLogin()),format.raw/*43.134*/("""</td>
            
            
           
            
           </tr>
       
        """)))}),format.raw/*50.10*/("""
        
        """),format.raw/*52.9*/("""</tbody>
       
    </table>
""")))}),format.raw/*55.2*/("""

"""),format.raw/*57.1*/("""</body>

"""))
      }
    }
  }

  def render(newUserForm:play.data.Form[formData],repoList:List[Repository],request:Request,messages:play.i18n.Messages): play.twirl.api.HtmlFormat.Appendable = apply(newUserForm,repoList)(request,messages)

  def f:((play.data.Form[formData],List[Repository]) => (Request,play.i18n.Messages) => play.twirl.api.HtmlFormat.Appendable) = (newUserForm,repoList) => (request,messages) => apply(newUserForm,repoList)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/home.scala.html
                  HASH: 7739823d8df545589d9f9102021622a53f5f54b3
                  MATRIX: 610->1|633->19|669->50|696->72|1083->100|1303->224|1331->226|1437->306|1452->312|1510->361|1550->363|1588->374|1603->380|1662->418|1699->428|1828->527|1867->539|1926->570|1955->571|1994->582|2055->616|2084->617|2121->627|2162->642|2211->682|2254->687|2287->693|2418->797|2455->818|2495->820|2550->847|2630->900|2645->906|2739->978|2770->981|2784->985|2821->1000|2872->1024|2885->1028|2916->1038|2997->1092|3012->1098|3085->1149|3116->1152|3130->1156|3174->1178|3303->1276|3350->1296|3414->1330|3445->1334
                  LINES: 23->1|24->2|25->3|26->4|31->7|36->7|37->8|43->14|43->14|43->14|43->14|44->15|44->15|44->15|45->16|49->20|53->24|54->25|54->25|55->26|56->27|56->27|57->28|58->29|58->29|58->29|59->30|67->38|67->38|67->38|69->40|70->41|70->41|70->41|70->41|70->41|70->41|71->42|71->42|71->42|72->43|72->43|72->43|72->43|72->43|72->43|79->50|81->52|84->55|86->57
                  -- GENERATED --
              */
          