var request = require('request');
var cheerio = require('cheerio');
var iconv   = require('iconv').Iconv;

var to_utf_8 = new iconv('euc-kr', 'utf-8');
var to_euckr = new iconv('utf-8', 'euc-kr');

var url_usedstore = "http://off.aladin.co.kr/usedstore/wsearchresult.aspx";

function aladin_urlenc(buf)
{
  var str = "", added = "";

  for(var i = 0; i < buf.length; i++) {
    if(buf[i] == 0x20) added = "+";            // ' '
    else if(buf[i] == 0x5c) added = "";        // '\'
    else if((buf[i] > 0x2f && buf[i] < 0x3a)   // 0..9
        ||  (buf[i] > 0x40 && buf[i] < 0x5b)   // A..Z
        ||  (buf[i] > 0x60 && buf[i] < 0x7b)   // a..z
        || buf[i] == 0x2a || buf[i] == 0x2d    // '*', '-'
        || buf[i] == 0x2e || buf[i] == 0x5f) { // '.', '_'
      added = String.fromCharCode(buf[i]);
    }
    else
      added = '%' + buf[i].toString(16);
 
    str += added;
  }

  return str;
}

exports.search_usedbooks = function(shop, query, callback)
{
  var buf  = new Buffer(query, 'utf-8');
  var qry_percent = aladin_urlenc(to_euckr.convert(buf));
  var url = url_usedstore + "?offcode=" + shop + "&SearchWord=" + qry_percent;

  var options = {
    url: url,
    encoding: null // required for changing to 'utf-8'
  };

  //console.log("URL: " + url);

  request(options, function(err, res, body) {
    if(!err && res.statusCode == 200) {
      var body_str = to_utf_8.convert(body).toString('utf-8');

      $ = cheerio.load(body_str);
      var results = $('a.bo_l');
      var res = [];

      if(results.length > 0) {
        results.each(function(i, el) {
          res.push($(el).text());
        });
      }
      callback(shop, res); // to clojurescript
    }
  });
}
