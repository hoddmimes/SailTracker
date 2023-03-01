






function getUrlParam() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}




function validateEmail(email)
{
    var re = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
    return re.test(email);
}

function validateDate(pDate)
{
    var re = /^\d{4}-\d{1,2}-\d{1,2}$/;
    return re.test( pDate );
}

function loadUrl(newLocation)
{
  window.location = newLocation;
  return false;
}


function utf8Encode(s) {
  return unescape(encodeURIComponent(s));
}

function utf8Decode(s) {
  return decodeURIComponent(escape(s));
}

function utf8EncodeArray( pArray ) {
    for( var i = 0; i < pArray.length; i++) {
        pArray[i] = utf8Encode( pArray[i] );
    }
    return pArray;
}

function utf8DecodeArray( pArray ) {
    for( var i = 0; i < pArray.length; i++) {
        pArray[i] = utf8Decode( pArray[i] );
    }
    return pArray;
}

function isEmpty(x)
{
   return (
        (typeof x == 'undefined')
                    ||
        (x == null)
                    ||
        (x == false)  //same as: !x
                    ||
        (x.length == 0)
                    ||
        (x == "")
                    ||
        (x.replace(/\s/g,"") == "")
                    ||
        (!/[^\s]/.test(x))
                    ||
        (/^\s*$/.test(x))
  );
}

function isAuthorized() {
   var authorized = (function() {
        var isAuth= null;
        $.ajax({
            'async': false,
            'global': false,
            'url': '/sailtracker/app/isAuthorized',
            'success': function(resp) {
                isAuth = (resp === "1");
            }
        });
        return isAuth;
    })();
    return authorized;
}


function compareLength( pStr, pLength ) {
      //console.log('str length' + pStr.length + ' compare-to: ' + pLength );
      return (pStr.length == pLength);
}

function isNumeric( pStr ) {
	var isNumericTxt = /^[-+]?(\d+|\d+\.\d*|\d*\.\d+)$/;
	return isNumericTxt.test( pStr );
}

function isDateTime( pStr ) {
	var isDateTimeTxt = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$/;
	return isDateTimeTxt.test( pStr );
}


function formatErrorMessage(jqXHR, exception) {

    if (jqXHR.status === 0) {
        return ('Not connected.\nPlease verify your network connection.');
    } else if (jqXHR.status == 404) {
        return ('The requested page not found. [404]');
    } else if (jqXHR.status == 500) {
        return ('Internal Server Error [500].');
    } else if (exception === 'parsererror') {
        return ('Requested JSON parse failed.');
    } else if (exception === 'timeout') {
        return ('Time out error.');
    } else if (exception === 'abort') {
        return ('Ajax request aborted.');
    } else {
        return ('Uncaught Error.\n' + jqXHR.responseText);
    }
}

function stError(xhr, err) {
    var responseTitle= $(xhr.responseText).filter('title').get(0);
    alert($(responseTitle).text() + "\n" + formatErrorMessage(xhr, err) );
}

function includeHTML() {
  var z, i, elmnt, file, xhttp;
  /* Loop through a collection of all HTML elements: */
  z = document.getElementsByTagName("*");
  for (i = 0; i < z.length; i++) {
    elmnt = z[i];
    /*search for elements with a certain atrribute:*/
    file = elmnt.getAttribute("w3-include-html");
    if (file) {
      /* Make an HTTP request using the attribute value as the file name: */
      xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
          if (this.status == 200) {elmnt.innerHTML = this.responseText;}
          if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
          /* Remove the attribute, and call this function once more: */
          elmnt.removeAttribute("w3-include-html");
          includeHTML();
        }
      }
      xhttp.open("GET", file, true);
      xhttp.send();
      /* Exit the function: */
      return;
    }
  }
}

  function  gotoAddPosition() {
      window.location.href = '/sailtracker/addpos.html';
      return false;
  }

  function  gotoPositions() {
      window.location.href = '/sailtracker/positions.html';
      return false;
  }

  function checkAuth( msg ) {
    if (msg.hasOwnProperty('Response')) {
      if (!msg.Response.success) {
        if (msg.Response.reason.startsWith("Authorization failure")) {
            alert( msg.Response.reason );
            loadUrl( msg.Response.optionPage );
            return false;
        }
      }
    }
    return true;
  }

  function  gotoSettings() {
       window.location.href = '/sailtracker/settings.html';
      return false;
  }

    function  gotoMap() {
        window.location.href = '/sailtracker/mappositions.html';
        return false;
    }

    function  trimstr( str, maxLength ) {
        tStr = str + ''
        if (tStr.length > maxLength) {
            return tStr.substring(0, maxLength);
        }
        return tStr;
    }

    function fmtdbl( value, maxLength ) {
         tStr = value + ''
         if (tStr.length > maxLength) {
            return tStr.substring(0, maxLength);
         }
         return tStr;
    }

    function dbg() {
        console.log("dbg");
    }

    function createChkBox() {
       var x = document.createElement("INPUT");
       x.setAttribute("type", "checkbox");
       x.checked = false;
       return x;
    }

    function getParameterByName(name, url = window.location.href) {
        name = name.replace(/[\[\]]/g, '\\$&');
        var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }