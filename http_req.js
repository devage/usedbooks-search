var request = require('request');
var cheerio = require('cheerio');
var iconv   = require('iconv').Iconv;

var to_utf_8 = new iconv('euc-kr', 'utf-8');
var to_euckr = new iconv('utf-8', 'euc-kr');

var url_usedstore = "http://off.aladin.co.kr/usedstore/wsearchresult.aspx";

function enc_bin2percent(buf)
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

exports.search_usedbooks = function(shop, query, callback)
{
  var buf  = new Buffer(query, 'utf-8');
  var qry_percent = enc_bin2percent(to_euckr.convert(buf));
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
