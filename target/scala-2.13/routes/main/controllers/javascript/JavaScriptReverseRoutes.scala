// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:24
    def time: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.time",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "time"})
        }
      """
    )
  
    // @LINE:6
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:23
    def tutorial: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.tutorial",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "tutorial"})
        }
      """
    )
  
    // @LINE:22
    def explore: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.explore",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "explore"})
        }
      """
    )
  
  }

  // @LINE:28
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:28
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:8
  class ReverseApiController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def getRepository: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiController.getRepository",
      """
        function(key0,repo1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "repository/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("key", key0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("repo", repo1))})
        }
      """
    )
  
    // @LINE:11
    def getOwnerRepos: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiController.getOwnerRepos",
      """
        function(key0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "rep/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("key", key0))})
        }
      """
    )
  
    // @LINE:20
    def fetchRepos: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiController.fetchRepos",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "home"})
        }
      """
    )
  
    // @LINE:8
    def index1: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiController.index1",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "index"})
        }
      """
    )
  
    // @LINE:18
    def showRepos: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiController.showRepos",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "home"})
        }
      """
    )
  
    // @LINE:10
    def getOwner: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiController.getOwner",
      """
        function(key0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "owner/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("key", key0))})
        }
      """
    )
  
  }


}
