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
  HomeController_2: controllers.HomeController,
  // @LINE:8
  ApiController_0: controllers.ApiController,
  // @LINE:28
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_2: controllers.HomeController,
    // @LINE:8
    ApiController_0: controllers.ApiController,
    // @LINE:28
    Assets_1: controllers.Assets
  ) = this(errorHandler, HomeController_2, ApiController_0, Assets_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_2, ApiController_0, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """index""", """controllers.ApiController.index1"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """owner/""" + "$" + """key<[^/]+>""", """controllers.ApiController.getOwner(key:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """rep/""" + "$" + """key<[^/]+>""", """controllers.ApiController.getOwnerRepos(key:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """repository/""" + "$" + """key<[^/]+>/""" + "$" + """repo<[^/]+>""", """controllers.ApiController.getRepository(key:String, repo:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """home""", """controllers.ApiController.showRepos(request:Request)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """home""", """controllers.ApiController.fetchRepos(request:Request)"""),
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
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_2.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page#""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_ApiController_index11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("index")))
  )
  private[this] lazy val controllers_ApiController_index11_invoker = createInvoker(
    ApiController_0.index1,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "index1",
      Nil,
      "GET",
      this.prefix + """index""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_ApiController_getOwner2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("owner/"), DynamicPart("key", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApiController_getOwner2_invoker = createInvoker(
    ApiController_0.getOwner(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "getOwner",
      Seq(classOf[String]),
      "GET",
      this.prefix + """owner/""" + "$" + """key<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_ApiController_getOwnerRepos3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("rep/"), DynamicPart("key", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApiController_getOwnerRepos3_invoker = createInvoker(
    ApiController_0.getOwnerRepos(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "getOwnerRepos",
      Seq(classOf[String]),
      "GET",
      this.prefix + """rep/""" + "$" + """key<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_ApiController_getRepository4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("repository/"), DynamicPart("key", """[^/]+""",true), StaticPart("/"), DynamicPart("repo", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApiController_getRepository4_invoker = createInvoker(
    ApiController_0.getRepository(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "getRepository",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """repository/""" + "$" + """key<[^/]+>/""" + "$" + """repo<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val controllers_ApiController_showRepos5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("home")))
  )
  private[this] lazy val controllers_ApiController_showRepos5_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      ApiController_0.showRepos(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "showRepos",
      Seq(classOf[play.mvc.Http.Request]),
      "GET",
      this.prefix + """home""",
      """""",
      Seq()
    )
  )

  // @LINE:20
  private[this] lazy val controllers_ApiController_fetchRepos6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("home")))
  )
  private[this] lazy val controllers_ApiController_fetchRepos6_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      ApiController_0.fetchRepos(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "fetchRepos",
      Seq(classOf[play.mvc.Http.Request]),
      "POST",
      this.prefix + """home""",
      """""",
      Seq("""nocsrf""")
    )
  )

  // @LINE:22
  private[this] lazy val controllers_HomeController_explore7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("explore")))
  )
  private[this] lazy val controllers_HomeController_explore7_invoker = createInvoker(
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

  // @LINE:23
  private[this] lazy val controllers_HomeController_tutorial8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("tutorial")))
  )
  private[this] lazy val controllers_HomeController_tutorial8_invoker = createInvoker(
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

  // @LINE:24
  private[this] lazy val controllers_HomeController_time9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("time")))
  )
  private[this] lazy val controllers_HomeController_time9_invoker = createInvoker(
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

  // @LINE:28
  private[this] lazy val controllers_Assets_versioned10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned10_invoker = createInvoker(
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
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_2.index)
      }
  
    // @LINE:8
    case controllers_ApiController_index11_route(params@_) =>
      call { 
        controllers_ApiController_index11_invoker.call(ApiController_0.index1)
      }
  
    // @LINE:10
    case controllers_ApiController_getOwner2_route(params@_) =>
      call(params.fromPath[String]("key", None)) { (key) =>
        controllers_ApiController_getOwner2_invoker.call(ApiController_0.getOwner(key))
      }
  
    // @LINE:11
    case controllers_ApiController_getOwnerRepos3_route(params@_) =>
      call(params.fromPath[String]("key", None)) { (key) =>
        controllers_ApiController_getOwnerRepos3_invoker.call(ApiController_0.getOwnerRepos(key))
      }
  
    // @LINE:14
    case controllers_ApiController_getRepository4_route(params@_) =>
      call(params.fromPath[String]("key", None), params.fromPath[String]("repo", None)) { (key, repo) =>
        controllers_ApiController_getRepository4_invoker.call(ApiController_0.getRepository(key, repo))
      }
  
    // @LINE:18
    case controllers_ApiController_showRepos5_route(params@_) =>
      call { 
        controllers_ApiController_showRepos5_invoker.call(
          req => ApiController_0.showRepos(req))
      }
  
    // @LINE:20
    case controllers_ApiController_fetchRepos6_route(params@_) =>
      call { 
        controllers_ApiController_fetchRepos6_invoker.call(
          req => ApiController_0.fetchRepos(req))
      }
  
    // @LINE:22
    case controllers_HomeController_explore7_route(params@_) =>
      call { 
        controllers_HomeController_explore7_invoker.call(HomeController_2.explore)
      }
  
    // @LINE:23
    case controllers_HomeController_tutorial8_route(params@_) =>
      call { 
        controllers_HomeController_tutorial8_invoker.call(HomeController_2.tutorial)
      }
  
    // @LINE:24
    case controllers_HomeController_time9_route(params@_) =>
      call { 
        controllers_HomeController_time9_invoker.call(HomeController_2.time)
      }
  
    // @LINE:28
    case controllers_Assets_versioned10_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned10_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
