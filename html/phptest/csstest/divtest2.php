<!DOCTYPE html>
<html lang="ja">
    <head>
        <title></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <body>
    <style>
        #div1{
            overflow:scroll;
            position:absolute;
            width:600px;
            height:400px;
            margin:0 auto;
            margin-top:200px;
            margin-left:200px;
        }
        #div2{
            width:800px;
            height:600px;
            background-color:gray;
            position:absolute;
            background-image:url("test.bmp");
            /*margin-top:200px;
            margin-left:200px;*/
        }
        #div3{
            width:700px;
            height:400px;
            left:700px;
            background-color:gray;
            position:absolute;
            background-image:url("test.bmp");
            /*margin-top:200px;
            margin-left:200px;*/
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
        
        
        console.log($(div1).height());
        console.log(div1.scrollHeight);
        $(div1).scrollTop($(div1).height());
        console.log(div1.scrollTop);


        var div3h = $('#div3').height();

        
        function zoomin(){
            Zoom += 0.1;
            // Zoom = 1.1;

            var height = $('#div2').height() ;
            var afHeight = height * Zoom;
            // (ｽｹｰﾙ後高さ - 高さ) / 2
            var sabun = (afHeight - height) / 2;

            var width = $('#div2').width() ;
            var afWidth = width * Zoom;
            // (ｽｹｰﾙ後幅 - 幅) / 2
            var sabun2 = (afWidth - width) / 2;

            // $('#div2').css("transform","scale(" + Zoom + ")");
            // $('#div2').css("top",sabun);
            // $('#div2').css("left",sabun2);

            var obj = document.getElementById("div1");
            var top = obj.scrollTop;
            var left = obj.scrollLeft;

            // Zoom前の情報取得
            console.log("Zoom前の情報");
            var preTop = obj.scrollTop;
            var preLeft = obj.scrollLeft;
            $(obj).scrollTop(obj.scrollHeight);
            $(obj).scrollLeft(obj.scrollWidth);
            // barを除いた高さ
            var preHeight = obj.scrollTop;
            var preWidth = obj.scrollLeft;
            
            // 中央からのbar頭の距離
            var preLenHeightCen = preHeight/2 - preTop;
            var preLenWidthCen = preWidth/2 - preLeft;
            console.log("preHeight:" + preHeight);
            console.log("preWidth:" + preWidth);
            
            // Zoom処理
            $('#div2').css("transform","scale(" + Zoom + "," + Zoom + ")");
            $('#div2').css("top",sabun);
            $('#div2').css("left",sabun2);

            // Zoom後の情報取得
            console.log("Zoom後の情報")

            $(obj).scrollTop(obj.scrollHeight);
            $(obj).scrollLeft(obj.scrollWidth);
            var afHeight = obj.scrollTop;
            var afWidth = obj.scrollLeft;

            var afLenHeightCen = afHeight / 2;
            var afLenWidthCen = afWidth / 2;

            console.log("afHeight:" + afHeight);
            console.log("afWidth:" + afWidth);

            var afTop = afLenHeightCen - preLenHeightCen;
            var afLeft = afWidth / 2 - preLenWidthCen;
            
            var afTopSa = afLenHeightCen - afTop;
            //$(obj).scrollTop(afTop);
            //$(obj).scrollLeft(afLeft);
            $(obj).scrollTop(afTop - (afTopSa / afLenHeightCen) * ((afHeight - preHeight) / 5));
            $(obj).scrollLeft(afLeft - (afLeft / afWidth) * ((afWidth - preWidth) / 5));
            
            console.log("ずれの修正");
            console.log((afHeight - preHeight) / 2);
            console.log((afTopSa / afLenHeightCen) * (sabun / 2));
        }
    </script>
    <div style="position:absolute;top:300px">
    <button onclick="zoomout();">縮小</button>
    <button onclick="zoomin();">拡大</button>
    </div>
    </body>
</html>