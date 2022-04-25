//<![CDATA[ 

function activeTabCenter(laClasse) {
	$('li.tab').each(function(index) { 
	   if($(this).hasClass(laClasse)) {
		   PF('widgetVarTabView').select(index);
		   //console.log( "test ***** "+$(this).attr('tabindex')+"   "+index);
	   }
	});
}

function scrollToTop() {
	var elmnt = document.getElementById("top-bar");
	elmnt.scrollTop = 0;
}

function test(xhr, status, args) {
    var returnedValue = args.returnedValue;
    console.log(returnedValue);
    return returnedValue;
 }

function rr() {
	var a = rc();
	return a;
}

function precedentTabView(tabView) {
	if(tabView.getActiveIndex()-1 >= 0){tabView.select(tabView.getActiveIndex()-1);}
}

function suivantTabView(tabView) {
	if(tabView.getActiveIndex()+1 <= tabView.getLength()-1){tabView.select(tabView.getActiveIndex()+1);}
}

function precedentPossible(tabView) {
	if(tabView.getActiveIndex()-1 >= 0){return true;} else return false;
}

function suivantPossible(tabView) {
	if(tabView.getActiveIndex()+1 <= tabView.getLength()-1){return true;} else return false;
}

/*$('.fade').click(function() {
	  var element = $(this);
	  var tmpClass = element.attr('class');
	  element.removeClass();
	  setTimeout(function() {
	    element.offsetWidth = element.offsetWidth;
	    element.addClass(tmpClass).addClass('start-now');
	  }, 10);
	});*/

    
//]]>