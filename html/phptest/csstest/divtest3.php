<!DOCTYPE html>
<html lang="ja">
    <head>
        <title></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
    </head>
    <body>
    <style>
        #div1{
            width:400px;
            height:400px;
            overflow:scroll;
        }
        #div2{
            width:800px;
            height:700px;
        }
    </style>
    <div id="div1">
        <div id="div2">

        </div>
    </div>
    <button onclick="click01();">移動</button>
    <button onclick="click02();">移動2</button>

    <script>
        function click01(){
            var var1 = div1.clientHeight;
            var var2 = div1.scrollHeight;
            console.log(var1);
            console.log(var2);
            console.log('click01');

            $('#div1').scrollTop(var2 - var1);
            console.log(var2 - var1);
        }

        function click02(){
            $('#div1').scrollTop($('#div1').height());
            console.log($('#div1').scrollTop());
        }
    </script>
    </body>
</html>
