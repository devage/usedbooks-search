var request = require('request');
var cheerio = require('cheerio');

request('http://www.google.co.kr', function(err, res, body) {
  if(!err && res.statusCode == 200) {
    $ = cheerio.load(body);
    console.log($('title').text());
  }
});
