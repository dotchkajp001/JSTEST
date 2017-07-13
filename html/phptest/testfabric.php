<!DOCTYPE html>
<html lang="en">
    <head>
        <title></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/fabric.js/1.7.15/fabric.min.js"></script>
        <script>
            var zoom = 1.0;
            function zoomin(){
                zoom += 0.1;
                fabCanvas.setZoom(zoom);

                $(inner).width($(inner).width() * 1.1);

                //$('#div1').css("top","100px");
                // $(canvas1).css("transform","scale(" + zoom + ")");
            }

            function zoomout(){
                zoom -= 0.1;
                fabCanvas.setZoom(zoom);
                // $(canvas1).css("transform","scale(" + zoom + ")");
            }

            // $(function(){
            //     var rect = new fabric.Rect({
            //         fill:'red',
            //         width:40,
            //         height:40,
            //         top:40,
            //         left:40
            //     });

            //     var fabCanvas = new fabric.Canvas('canvas1');
            //     fabCanvas.add(rect);
            // })
        </script>
    </head>
    <body>
    testfabric<br />
    <style>
        canvas{
            /*background-color:gray;*/
        }
        .position{
            margin:0px 0 0 100px;
        }
        #div1{
            margin-top:100px;
            height:200px;
            width:200px;
            overflow:scroll;
        }
        #canvas1{
            height:200px;
            width:200px;
            transform:scale(1);
        }
    </style>
    <div id="div1" class="position">
        <div id="inner">
            <canvas id="canvas1" width="200" height="200">このWebブラウザはcanvasの描写に対応していません。</canvas>
        </div>
    </div>

    <!--<canvas id="canvas2"></canvas>-->
    <div class="position">
        <button onclick="zoomin();">ズームイン</button>
        <button onclick="zoomout();">ズームアウト</button>
    </div>
    end
    
    <script>
        // 四角形
        var rect = new fabric.Rect({
            fill:'red',
            width:40,
            height:40,
            top:40,
            left:40
        });

        var rect2 = new fabric.Rect({
            widt:100,
            height:100
        })

        var fabCanvas = new fabric.Canvas('canvas1');
        // fabCanvas.add(rect);

        // テキスト
        var text = new fabric.Text("テスト文字列"
        ,{fontSize:15
        ,top:40
        ,left:40});

        // fabCanvas.add(text);
        fabCanvas.add(rect);
        fabCanvas.add(rect2);
        console.log(fabCanvas);
    </script>
    </body>
</html>