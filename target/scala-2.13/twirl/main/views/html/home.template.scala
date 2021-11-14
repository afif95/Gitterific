
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
/*5.2*/import dto.Repository

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
    """),_display_(/*29.6*/if(!repoList.isEmpty)/*29.27*/    {_display_(Seq[Any](format.raw/*29.32*/("""
    """),format.raw/*30.5*/("""<table>
        <thead>
          
        </thead>
        <tbody>

        
        
        """),_display_(/*38.10*/for(repo <- repoList) yield /*38.31*/ {_display_(Seq[Any](format.raw/*38.33*/("""
            
           """),format.raw/*40.12*/("""<tr>
            <td>"""),_display_(/*41.18*/repo/*41.22*/.getName()),format.raw/*41.32*/("""</td>
            
           </tr>
       
        """)))}),format.raw/*45.10*/("""
        
        """),format.raw/*47.9*/("""</tbody>
       
    </table>
""")))}),format.raw/*50.2*/("""

"""),format.raw/*52.1*/("""</body>

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
                  HASH: 8e7aa163e3fc0a7ef2ce27586f34e7d784afc901
                  MATRIX: 610->3|633->21|669->52|696->74|1083->100|1303->224|1331->226|1437->306|1452->312|1510->361|1550->363|1588->374|1603->380|1662->418|1699->428|1828->527|1867->539|1926->570|1955->571|1994->582|2055->616|2084->617|2121->627|2162->642|2192->663|2235->668|2268->674|2399->778|2436->799|2476->801|2531->828|2581->851|2594->855|2625->865|2713->922|2760->942|2824->976|2855->980
                  LINES: 23->2|24->3|25->4|26->5|31->7|36->7|37->8|43->14|43->14|43->14|43->14|44->15|44->15|44->15|45->16|49->20|53->24|54->25|54->25|55->26|56->27|56->27|57->28|58->29|58->29|58->29|59->30|67->38|67->38|67->38|69->40|70->41|70->41|70->41|74->45|76->47|79->50|81->52
                  -- GENERATED --
              */
          