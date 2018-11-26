<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false" %>
<!-- 현재 페이지에 선택창을 만들거임... 버튼 여러개로 만들어서 간단 메인페이지 왜 선택화면있잔슴 그거... 공기관같은데보면 들어갈 때 누르는거 ! -->
<html>
    <head>
        <title>게시판</title>
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/vue"></script>
        <script type="text/javascript">
        
            $(document).ready(function(){
                var app = new Vue({
                	  el: '#app',
                	  data: {
                	    message: '안녕하세요 Vue!'
                	  }
                	})
                
               	var app2 = new Vue({
               		el: "#app-2",
               		data: {
               			message : '이 페이지는' + new Date() + '에 로드 되었습니다.'
               		}
               	})
                
                var app3 = new Vue({
                	el : '#app-3',
                	data : {
                		seen : true
                	}
                })
                
                var app4 = new Vue({
                	el : 'app-4',
                	data : [
                		{ text : 'learning perfect javascript'},
                		{ text : 'learning Vue'},
                		{ text : 'will be make something to fantastic'}
                	]
                })
            	//--페이지 셋팅
            	var totalPage = ${totalPage}; //전체 페이지
            	var startPage = ${startPage}; //현재 페이지
            	
            	var pagination = "";
            	
            	//--페이지네이션에 항상 10개가 보이도록 조절
            	var forStart = 0;
            	var forEnd = 0;
            	
            	if((startPage-5) < 1){
            		forStart = 1;
            	}else{
            		forStart = startPage-5;
            	}
            	
            	if(forStart == 1){
            		
            		if(totalPage>9){
            			forEnd = 10;
            		}else{
            			forEnd = totalPage;
            		}
            		
            	}else{
            		
            		if((startPage+4) > totalPage){
                		
            			forEnd = totalPage;
                		
                		if(forEnd>9){
                			forStart = forEnd-9
                		}
                		
                	}else{
                		forEnd = startPage+4;
                	}
            	}
            	//--페이지네이션에 항상 10개가 보이도록 조절
            	
            	//전체 페이지 수를 받아 돌린다.
            	for(var i = forStart ; i<= forEnd ; i++){
            		if(startPage == i){
            			pagination += ' <button name="page_move" start_page="'+i+'" disabled>'+i+'</button>';
            		}else{
            			pagination += ' <button name="page_move" start_page="'+i+'" style="cursor:pointer;" >'+i+'</button>';
            		}
            	}
            	
            	//하단 페이지 부분에 붙인다.
            	$("#pagination").append(pagination);
            	//--페이지 셋팅
            	
            	
            	$("a[name='subject']").click(function(){
            		
            		location.href = "/myapp/board/view?id="+$(this).attr("content_id");
            		
            	});
            	
            	$("#write").click(function(){
            		location.href = "/myapp/board/edit";
            	});
            	            	
            	$(document).on("click","button[name='page_move']",function(){
            		
                    var visiblePages = 10;//리스트 보여줄 페이지
                    
                    $('#startPage').val($(this).attr("start_page"));//보고 싶은 페이지
                    $('#visiblePages').val(visiblePages);
                    
                    $("#frmSearch").submit();
                    
            	});
            	
            });
        </script>
        <style>
        	.mouseOverHighlight {
				   border-bottom: 1px solid blue;
				   cursor: pointer !important;
				   color: blue;
				   pointer-events: auto;
				}
        </style>
    </head>
    <body>
    <div id="app">
	  {{ message }}
	</div>
	<div id="app-2">
		<span v-bind:title="message">
			동적 바인딩 title
		</span>
	</div>
	<div id="app-3">
		<p v-if="seen">now, you can see me!</p>
	</div>
	<div id="app-4">
		<ol>
			<li v-for="todo in todos">
				{{ todo.text }}
			</li>
		</ol>
	</div>
	<!-- 여기다 테스트 용으로 일단 휴가신청서(button)을 만들고 이 외 추가....로그인 이 후 바로 연결될 부분인 곳 (기본적으로 권한 인증도 이루어져야함.) 
		그리고 위에는 누구누구님 (직급) 날짜/시간(sysdate) 접속하셨습니다.
		그리고 아무래도 전자 결제 관련이니까.. ( 공지사항? 같은 admin급이 올린 글을 모두가 확인할 수 있는 간단 게시판 목록정도는 보여야할 듯) ->그냥 들어가고나서 보이는걸?
		어렵다...
		
	-->
	<button id="vacbutton" onclick="location.href='/myapp/main/vacWrite'">휴가신청서</button>
	<button id="vacbutton" onclick="vac.jsp">2</button>
	<button id="vacbutton" onclick="vac.jsp">3</button>
	<button id="vacbutton" onclick="vac.jsp">4</button>
	<button id="vacbutton" onclick="vac.jsp">5</button>
	<button id="vacbutton" onclick="vac.jsp">6</button>
	<!-- 
		버튼보다 상위에 존재해야하는게 공지사항 및 접속자 정보...
	 -->
    	<form class="form-inline" id="frmSearch" action="/myapp/board/list">
	    	<input type="hidden" id="startPage" name="startPage" value=""><!-- 페이징을 위한 hidden타입 추가 -->
	        <input type="hidden" id="visiblePages" name="visiblePages" value=""><!-- 페이징을 위한 hidden타입 추가 -->
	    	<div align="center">
	    		<table width="1200px">
	    			<tr>
	    				<td align="right">
	    					<button type="button" id="write" name="write">글 작성</button>
	    				</td>
	    			</tr>
	    		</table>
	    		<table border="1" width="1200px">
	    			<tr>
	    				<th width="50px">
	    					No
	    				</th>
	    				<th width="850px">
	    					제목
	    				</th>
	    				<th width="100px">
	    					작성자
	    				</th>
	    				<th width="200px">
	    					작성일
	    				</th>
	    			</tr>
	    			<c:choose>
			        	<c:when test="${fn:length(boardList) == 0}">
			            	<tr>
			            		<td colspan="4" align="center">
			            			조회결과가 없습니다.
			            		</td>
			            	</tr>
			           	</c:when>
			           	<c:otherwise>
			            	<c:forEach var="boardList" items="${boardList}" varStatus="status">
								<tr>
						    		<td align="center">${boardList.id}</td>
						    		<td>
						    			<a name="subject" class="mouseOverHighlight" content_id="${boardList.id}">${boardList.subject}</a>
						    		</td>
						    		<td align="center">${boardList.writer}</td>
						    		<td align="center">${boardList.register_datetime}</td>
						    	</tr>
						    </c:forEach>
			           	</c:otherwise> 
			    	</c:choose>
	    		</table>
	    		<br>
	    		<div id="pagination"></div>
	    	</div>
    	</form>
    </body>
</html>