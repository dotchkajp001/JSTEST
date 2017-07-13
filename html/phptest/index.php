<!DOCTYPE html>
<html lang="ja">
    <head>
        <title></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
    testPage
    <?php echo("samplePhp");?><br />
    <ul>
        <li><a href="testjquery.php">testjquery</a></li>
        <li><a href="testfabric.php">testfabric</a></li>
        <li><a href="testjs.php">testjs</a></li>
    </ul>
    <ul>
        <li><a href="csstest/transform.php">transform</a></li>
        <li><a href="csstest/position.php">position</a></li>
        <li><a href="csstest/divtest.php">divtest</a></li>
        <li><a href="csstest/divtest2.php">divtest2</a></li>
        <li><a href="csstest/divtest3.php">divtest3</a></li>
    </ul>
    </body>
</html>

<!--
	Alias /test01 "H:/opengion_user/workVSCode/jstest/html/phptest/"
    <Directory "H:/opengion_user/workVSCode/jstest/html/phptest">
        AllowOverride AuthConfig
        Require local
        ErrorDocument 403 /error/XAMPP_FORBIDDEN.html.var
    </Directory>

    apacheを起動していから
    url:http://localhost:85/test01
-->