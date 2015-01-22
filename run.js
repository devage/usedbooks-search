try {
    require("source-map-support").install();
} catch(err) {
}
require("./out/goog/bootstrap/nodejs.js");
require("./out/usedbooks_search.js");
goog.require("usedbooks_search.core");
goog.require("cljs.nodejscli");
