
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
  def apply/*8.2*/(newUserForm: play.data.Form[formData], repoList: List[Repository])(implicit request: Request, messages: play.i18n.Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*8.126*/("""
"""),format.raw/*9.1*/("""<!-- HTML Head -->



<body>
   		<h1> Welcome to Gitterific! </h1>
   		"""),_display_(/*15.7*/helper/*15.13*/.form(action = routes.ApiController.fetchRepos())/*15.62*/ {_display_(Seq[Any](format.raw/*15.64*/("""
        """),_display_(/*16.10*/helper/*16.16*/.inputText(newUserForm("searchInput"))),format.raw/*16.54*/("""
        """),format.raw/*17.9*/("""<button type="submit" class="btn btn-primary btn-block">Search</button>

       
        
    """)))}),format.raw/*21.6*/("""



    """),format.raw/*25.5*/("""<style>
        table, th, td """),format.raw/*26.23*/("""{"""),format.raw/*26.24*/("""
         """),format.raw/*27.10*/("""border: 1px solid black;
        """),format.raw/*28.9*/("""}"""),format.raw/*28.10*/("""
        """),format.raw/*29.9*/("""</style>
    """),_display_(/*30.6*/if(!repoList.isEmpty & repoList != null)/*30.46*/    {_display_(Seq[Any](format.raw/*30.51*/("""
    """),format.raw/*31.5*/("""<table>
        <thead>
          
        </thead>
        <tbody>

        
        
        """),_display_(/*39.10*/for(repo <- repoList) yield /*39.31*/ {_display_(Seq[Any](format.raw/*39.33*/("""
            
           """),format.raw/*41.12*/("""<tr>
            <td>"""),_display_(/*42.18*/repo/*42.22*/.getFull_name()),format.raw/*42.37*/("""</td>
            <td>"""),_display_(/*43.18*/repo/*43.22*/.getName()),format.raw/*43.32*/("""</td>
            <td><a class="btn btn-link" href=""""),_display_(/*44.48*/routes/*44.54*/.ApiController.getOwner(repo.getOwner().getLogin())),format.raw/*44.105*/("""">"""),_display_(/*44.108*/repo/*44.112*/.getOwner().getLogin()),format.raw/*44.134*/("""</td>
            
            
           
            
           </tr>
       
        """)))}),format.raw/*51.10*/("""
        
        """),format.raw/*53.9*/("""</tbody>
       
    </table>
""")))}),format.raw/*56.2*/("""

"""),format.raw/*58.1*/("""</body>

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
                  HASH: 7a790660f9881ba18f9e44a47807158b411d63b7
                  MATRIX: 610->3|633->21|669->52|696->74|1083->102|1303->226|1331->228|1437->308|1452->314|1510->363|1550->365|1588->376|1603->382|1662->420|1699->430|1828->529|1867->541|1926->572|1955->573|1994->584|2055->618|2084->619|2121->629|2162->644|2211->684|2254->689|2287->695|2418->799|2455->820|2495->822|2550->849|2600->872|2613->876|2649->891|2700->915|2713->919|2744->929|2825->983|2840->989|2913->1040|2944->1043|2958->1047|3002->1069|3131->1167|3178->1187|3242->1221|3273->1225
                  LINES: 23->2|24->3|25->4|26->5|31->8|36->8|37->9|43->15|43->15|43->15|43->15|44->16|44->16|44->16|45->17|49->21|53->25|54->26|54->26|55->27|56->28|56->28|57->29|58->30|58->30|58->30|59->31|67->39|67->39|67->39|69->41|70->42|70->42|70->42|71->43|71->43|71->43|72->44|72->44|72->44|72->44|72->44|72->44|79->51|81->53|84->56|86->58
                  -- GENERATED --
              */
          