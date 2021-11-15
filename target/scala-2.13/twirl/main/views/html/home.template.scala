
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


Seq[Any](format.raw/*8.1*/("""<!-- HTML Head -->



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
    """),_display_(/*29.6*/if(!repoList.isEmpty & repoList != null)/*29.46*/    {_display_(Seq[Any](format.raw/*29.51*/("""
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
                  HASH: 112a89ae8e25b82c283e7f3d7448cf409cbb9d63
                  MATRIX: 610->1|633->18|669->48|696->69|1083->94|1301->219|1401->293|1416->299|1474->348|1514->350|1551->360|1566->366|1625->404|1661->413|1786->508|1821->516|1879->546|1908->547|1946->557|2006->590|2035->591|2071->600|2111->614|2160->654|2203->659|2235->664|2358->760|2395->781|2435->783|2488->808|2567->860|2582->866|2676->938|2707->941|2721->945|2758->960|2808->983|2821->987|2852->997|2932->1050|2947->1056|3020->1107|3051->1110|3065->1114|3109->1136|3231->1227|3276->1245|3337->1276|3366->1278
                  LINES: 23->1|24->2|25->3|26->4|31->7|36->8|42->14|42->14|42->14|42->14|43->15|43->15|43->15|44->16|48->20|52->24|53->25|53->25|54->26|55->27|55->27|56->28|57->29|57->29|57->29|58->30|66->38|66->38|66->38|68->40|69->41|69->41|69->41|69->41|69->41|69->41|70->42|70->42|70->42|71->43|71->43|71->43|71->43|71->43|71->43|78->50|80->52|83->55|85->57
                  -- GENERATED --
              */
          