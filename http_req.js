var request = require('request');
var parse5  = require('parse5');

request('http://www.google.co.kr', function(err, res, body) {
  if(!err && res.statusCode == 200) {
    var parser = new parse5.Parser();
    var serializer = new parse5.Serializer();

    var document = parser.parse(body);
    console.dir(document.childNodes[1].childNodes[1]);

    var html = serializer.serialize(document.childNodes[1].childNodes[1]);
    console.log(html);
  }
});
