<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="${statusInfo}">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Alibaba, Inc.</title>

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link href="src/vendor/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link href="src/css/style.css" rel="stylesheet">

    <!--[if lt IE 7]>
    <script src="src/vendor/es5-shim/es5-shim.js"></script>
    <![endif]-->
</head>
<body>
    <!--[if lt IE 7]>
        <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
    <![endif]-->

    <!-- Add your site or application content here -->
    试试
    <h2>${statusInfo}</h2>
    <h2>${data}</h2>
    <%=request.getCookies()%>
</body>
</html>