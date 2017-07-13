<!DOCTYPE html>
<html lang="ja">
    <head>
        <title></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="css/style.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <body>
    <style>
        #div1{
            overflow:scroll;
            position:absolute;
            width:400px;
            height:200px;
            margin:0 auto;
        }
        #div2{
            width:700px;
            height:400px;
            background-color:gray;
            position:absolute;
            background-image:url("test.bmp");
        }
    </style>
    <div id="div1">
        <div id="div2">
            1<br />1<br />1<br />1<br />1<br />1<br />1<br />1<br />1<br />1<br />1<br />1<br />1<br />1<br />1<br />1<br />end
        </div>
    </div>
    <script>
        var Zoom = 1.0;
        function zoomout(){
            Zoom -= 0.1;
            $('#div2').css("transform","scale(" + Zoom + ")");
            $('#div2').css("top",0);
            $('#div2').css("left",0);
            //alert("a");
        }
        
        
        var val1 = $('#div2').height();
        function zoomin(){
            Zoom += 0.1;

            console.log($('#div2').height());
            $('#div2').css("transform","scale(" + Zoom + ")");
            console.log($('#div2').height());

            val1 *= Zoom;
            var top = (val1 - $('#div1').height());
            
            //$('#div2').css('top','30px');
            //$('#div2').css("top",val1 - top);
            //$('#div2').css("left","100px");
            
            // console.log(top);
            console.log($('#div1').height());
            console.log($('#div2').height());
            //alert("a");
        }
    </script>
    <div style="position:absolute;top:300px">
    <button onclick="zoomout();">縮小</button>
    <button onclick="zoomin();">拡大</button>
    </div>
    </body>
</html>