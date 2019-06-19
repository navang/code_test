<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<script>
		var xmlHttp;
		window.onload=function(){
			// 1. 브라우저에 따른 XMLHttpRequest생성하기.
			xmlHttp = new XMLHttpRequest();
			
			// 2. 요청에 대한 응답처리 이벤트 리스너 등록.
			xmlHttp.onreadystatechange = on_ReadyStateChange;
			
			// 3.서버로 보낼 데이터 생성.
			// 3.데이터 생성.
			var data = "cate=book&name=hong";		
			
			//###########################################################
			// 4. GET방식으로 데이터 보내기, 응답은 비동기로 클라이언트<->서버간의 연결 요청준비.
			xmlHttp.open("GET", "01_server.jsp?"+data, true);
			// 5. 실제 데이터 전송.
			xmlHttp.send(null);
			
			//####			
			// T. 동기/비동기 실행 테스트를 위한 부분.
			alert("전송 시작!");
		}
		
		
		// 6.응답처리.
		function on_ReadyStateChange(){
			if(xmlHttp.readyState == 4){
				if(xmlHttp.status == 200) { // 정상적으로 응답받은 상태 
				//	alert("서버에서 보낸 데이터:"+xmlHttp.responseText);
				parseData(xmlHttp.responseText);
				}else{
					alert("error");
			}
		}
	}
		
		//##################################################
		//7. CSV포맷  데이터 처리.
		function parseData(strText){
			var ary =strText.split("|"); 
			// ary[0] : cate=서버에서book변경
			// ary[1] : name=변경된hong 
			for(var i=0; i<ary.length; i++){ 
				var param = ary[i].split("=");
				if(param[0].trim() == "cate"){
					document.getElementById("cate").value=param[1];
				}else(param[0].trim() == "name")
				document.getElementById("name").value=param[1]
			}
			
		
		}
	</script>
</head>

<body>
서버로부터 넘겨받은 데이터<br/>
첫번째 데이터 : <input type="text" name="" id="cate"/><br/>
두번째 데이터 : <input type="text" name="" id="name"/><br/>
</body>
</html>


