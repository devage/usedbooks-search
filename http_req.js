var http = require('http');
var htmlparser = require('htmlparser2');

var parser = new htmlparser.Parser({
	onopentag: function(name, attribs) {
		console.log('tag: ' + name);
	},
	ontext: function(text) {
		console.log('---> ', text);
	},
	onclosetag: function(text) {
		// nothing
	}
}, {decodeEntities: true});

http.get('http://www.google.co.kr/index.html', function(res) {
	console.log('Got response: ' + res.statusCode);

	res.on('data', function(chunk) {
		parser.write(chunk);
		parser.end();
	});

}).on('error', function(e) {
	console.log('Got error: ' + e.message);
});
