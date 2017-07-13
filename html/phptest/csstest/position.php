<!DOCTYPE html>
<html lang="en">
    <head>
        <title></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <body>
    <style>
        #div1{
            overflow:auto;
            width:200px;
            height:200px;
            position:absolute;
            background-color:yellow;
        }
        #div2{
            width:200px;
            height:200px;
            position:absolute;
            background-color:gray;
        }
    </style>
    <div id="div1">
        <div id="div2">
            div2
        </div>
    </div>

    <script>
        $("#div2").css("top",100);
    </script>
    </body>
</html>
