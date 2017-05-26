<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
	$(function(){
		console.log('start');
	})
	
	// a3rtのrestApiを使用した処理
	function send01(){
		$.ajax({
			type: "POST",
			url:"https://api.a3rt.recruit-tech.co.jp/talk/v1/smalltalk",
			data:{
				"apikey":"bzuXlmRoBDncSrVkVedCBe4vT0S4TJHt",
				"query":$("input[name=query]").val()
			},
			success:function(data){
				//alert(data);
				alert(data.results[0].reply);
			}
		});
	}
</script>
</head>
<body>
	テスト０１
	<form method="POST" action="https://api.a3rt.recruit-tech.co.jp/talk/v1/smalltalk">
		<input type="text" name="query" />
		<input type="submit" />
		<input type="hidden" name="apikey" value="bzuXlmRoBDncSrVkVedCBe4vT0S4TJHt" />
	</form>
	
	<button onclick="send01();">送信</button>
</body>
</html>