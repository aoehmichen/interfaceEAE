<!DOCTYPE html>
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="eAE"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		%{--<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">--}%
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<g:layoutHead/>
		<g:javascript library="application"/>		
		<r:layoutResources />
	</head>
	<body>
		<div id="eaeLogo" role="banner"><a href="http://www.etriks.org"><img src="${resource(dir: 'images', file: 'eAE.svg')}" width="90" height="90"/>
        </a>eTRIKS Analytical Environement - Management Page</div>
		<g:layoutBody/>
		<div class="footer" role="contentinfo">Copyright 2016 eTRIKS</div>
		%{--<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>--}%
		<r:layoutResources />
	</body>
</html>
