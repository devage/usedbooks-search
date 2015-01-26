var request = require('request');
var cheerio = require('cheerio');
var iconv   = require('iconv').Iconv;

var kr_2_utf8 = new iconv('euc-kr', 'utf-8');
var utf8_2_kr = new iconv('utf-8', 'euc-kr');

exports.test_jsfunc_call = function(url)
{
  request(url, function(err, res, body) {
    if(!err && res.statusCode == 200) {
      $ = cheerio.load(body);
      console.log($('title').text());

      var str_utf8 = '서적';
      var buf_utf8 = new Buffer(str_utf8, 'utf-8');
      var buf_kr   = utf8_2_kr.convert(buf_utf8);
      var str_kr   = buf_kr.toString(); // XXX

      console.log('utf-8:  ' + buf_utf8.toString());
      console.dir(buf_utf8);
      console.log('-> euc-kr: ' + str_kr);
      console.dir(buf_kr);
      console.log('-> utf-8: ' + kr_2_utf8.convert(buf_kr).toString());
      console.dir(kr_2_utf8.convert(buf_kr));
    }
  });
}
