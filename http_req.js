var request = require('request');
var cheerio = require('cheerio');
var iconv   = require('iconv').Iconv;

var kr_2_utf8 = new iconv('euc-kr', 'utf-8//translit//ignore');
var utf8_2_kr = new iconv('utf-8', 'euc-kr');

var url_usedstore = "http://off.aladin.co.kr/usedstore/wsearchresult.aspx";

function buf2url(buf)
{
  var str = "";

  for(var i = 0; i < buf.length; i++) {
    if(buf[i] == 0x20) {
      str = str + "+";
    }
    else if(buf[i] < 0x80) {
      str = str + String.fromCharCode(buf[i]);
    }
    else {
      var hexa = '%' + buf[i].toString(16);
      str = str + hexa;
    }
  }

  return str;
}

exports.search_usedbooks = function(shop, query)
{
  var buf_utf8  = new Buffer(query, 'utf-8');
  var query_euckr = utf8_2_kr.convert(buf_utf8);
  var query_encd = buf2url(query_euckr);

  var url = url_usedstore + "?offcode=" + shop + "&SearchWord=" + query_encd;

  console.log("URL: " + url);

  request(url, function(err, res, body) {

    if(!err && res.statusCode == 200) {
      var buf = new Buffer(body, 'binary');
      body = kr_2_utf8.convert(buf).toString('utf-8');

      $ = cheerio.load(body);
      var title = $('title').text();

      console.log(title); // euc-kr crashed (by request?)
    }
  });
}
