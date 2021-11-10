// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  ApiController_0: controllers.ApiController,
  // @LINE:14
  HomeController_2: controllers.HomeController,
  // @LINE:21
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    ApiController_0: controllers.ApiController,
    // @LINE:14
    HomeController_2: controllers.HomeController,
    // @LINE:21
    Assets_1: controllers.Assets
  ) = this(errorHandler, ApiController_0, HomeController_2, Assets_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, ApiController_0, HomeController_2, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """index""", """controllers.ApiController.index1"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """searchQuery/""" + "$" + """key<[^/]+>""", """controllers.ApiController.searchQuery(key:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """home""", """controllers.ApiController.home(request:Request)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """home""", """controllers.ApiController.save(request:Request)"""),
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """explore""", """controllers.HomeController.explore"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """tutorial""", """controllers.HomeController.tutorial"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """time""", """controllers.HomeController.time"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_ApiController_index10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("index")))
  )
  private[this] lazy val controllers_ApiController_index10_invoker = createInvoker(
    ApiController_0.index1,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "index1",
      Nil,
      "GET",
      this.prefix + """index""",
      """ An example controller showing a sample home page#""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_ApiController_searchQuery1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("searchQuery/"), DynamicPart("key", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApiController_searchQuery1_invoker = createInvoker(
    ApiController_0.searchQuery(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "searchQuery",
      Seq(classOf[String]),
      "GET",
      this.prefix + """searchQuery/""" + "$" + """key<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_ApiController_home2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("home")))
  )
  private[this] lazy val controllers_ApiController_home2_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      ApiController_0.home(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "home",
      Seq(classOf[play.mvc.Http.Request]),
      "GET",
      this.prefix + """home""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_ApiController_save3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("home")))
  )
  private[this] lazy val controllers_ApiController_save3_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      ApiController_0.save(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "save",
      Seq(classOf[play.mvc.Http.Request]),
      "POST",
      this.prefix + """home""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:14
  private[this] lazy val controllers_HomeController_index4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index4_invoker = createInvoker(
    HomeController_2.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_HomeController_explore5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("explore")))
  )
  private[this] lazy val controllers_HomeController_explore5_invoker = createInvoker(
    HomeController_2.explore,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "explore",
      Nil,
      "GET",
      this.prefix + """explore""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val controllers_HomeController_tutorial6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("tutorial")))
  )
  private[this] lazy val controllers_HomeController_tutorial6_invoker = createInvoker(
    HomeController_2.tutorial,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "tutorial",
      Nil,
      "GET",
      this.prefix + """tutorial""",
      """""",
      Seq()
    )
  )

  // @LINE:17
  private[this] lazy val controllers_HomeController_time7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("time")))
  )
  private[this] lazy val controllers_HomeController_time7_invoker = createInvoker(
    HomeController_2.time,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "time",
      Nil,
      "GET",
      this.prefix + """time""",
      """""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val controllers_Assets_versioned8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned8_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_ApiController_index10_route(params@_) =>
      call { 
        controllers_ApiController_index10_invoker.call(ApiController_0.index1)
      }
  
    // @LINE:8
    case controllers_ApiController_searchQuery1_route(params@_) =>
      call(params.fromPath[String]("key", None)) { (key) =>
        controllers_ApiController_searchQuery1_invoker.call(ApiController_0.searchQuery(key))
      }
  
    // @LINE:10
    case controllers_ApiController_home2_route(params@_) =>
      call { 
        controllers_ApiController_home2_invoker.call(
          req => ApiController_0.home(req))
      }
  
    // @LINE:12
    case controllers_ApiController_save3_route(params@_) =>
      call { 
        controllers_ApiController_save3_invoker.call(
          req => ApiController_0.save(req))
      }
  
    // @LINE:14
    case controllers_HomeController_index4_route(params@_) =>
      call { 
        controllers_HomeController_index4_invoker.call(HomeController_2.index)
      }
  
    // @LINE:15
    case controllers_HomeController_explore5_route(params@_) =>
      call { 
        controllers_HomeController_explore5_invoker.call(HomeController_2.explore)
      }
  
    // @LINE:16
    case controllers_HomeController_tutorial6_route(params@_) =>
      call { 
        controllers_HomeController_tutorial6_invoker.call(HomeController_2.tutorial)
      }
  
    // @LINE:17
    case controllers_HomeController_time7_route(params@_) =>
      call { 
        controllers_HomeController_time7_invoker.call(HomeController_2.time)
      }
  
    // @LINE:21
    case controllers_Assets_versioned8_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned8_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
