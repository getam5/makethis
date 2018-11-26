<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
    <head>
        <title>휴가신청서</title>
		
		<style type="text/css">
			.img_wrap {
				width : 300px;
				margin-top : 50px;
			}
			.img_wrap img {
				max-width : 100%;
			}
		</style>
		
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="//cdn.ckeditor.com/4.7.1/standard/ckeditor.js"></script>
        <script type="text/javascript">
     	
        var sel_file;
    	    
        $(document).ready(function(){
            	
            	$("#input_img").on("change", handleImgFileSelect);
            	
            	$(function(){
               		$.datepicker.setDefaults({
               			dateFormat: 'yy-mm-dd' //Input Display Format 변경
                            ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
                            ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
                            ,changeYear: true //콤보박스에서 년 선택 가능
                            ,changeMonth: true //콤보박스에서 월 선택 가능                
                            ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
                            ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
                            ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
                            ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
                            ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
                            ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
                            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
                            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
                            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
                            ,minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
                            ,maxDate: "+1M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)  
               		});
               		
               		$("#sdate").datepicker();
               		$("#edate").datepicker();
               		
               		$('#sdate').datepicker('setDate', 'today');
               		$('#edate').datepicker('setDate', '+1D');
               	});
            	   
            	$("#list").click(function(){
            		location.href = "/myapp/board/list";
            	});
            	
            	$("#save").click(function(){
            		
            		//널 검사
                    if($("#subject").val().trim() == ""){
                    	alert("제목을 입력하세요.");
                    	$("#subject").focus();
                    	return false;
                    }
            		
                    if($("#writer").val().trim() == ""){
                    	alert("작성자를 입력하세요.");
                    	$("#writer").focus();
                    	return false;
                    }
                    
                    if($("#password").val().trim() == ""){
                    	alert("비밀번호를 입력하세요.");
                    	$("#password").focus();
                    	return false;
                    }
            		
            		//값 셋팅
            		var objParams = {
            				<c:if test="${boardView.id != null}"> //있으면 수정 없으면 등록
            				id			: $("#board_id").val(),
            				</c:if>
            				subject		: $("#subject").val(),
            				writer		: $("#writer").val(),
            				password	: $("#password").val(),
            				content		: content
    				};
            		
            		//ajax 호출
            		$.ajax({
            			url			:	"/board/save",
            			dataType	:	"json",
            			contentType :	"application/x-www-form-urlencoded; charset=UTF-8",
            			type 		:	"post",
            			data		:	objParams,
            			success 	:	function(retVal){

            				if(retVal.code == "OK") {
            					alert(retVal.message);
            					location.href = "/board/list";	
            				} else {
            					alert(retVal.message);
            				}
            				
            			},
            			error		:	function(request, status, error){
            				console.log("AJAX_ERROR");
            			}
            		});
            		x
            		
            	});
            	
            });
        
        function handleImgFileSelect(e) {
        	var files = e.target.files;
        	var filesArr = Array.prototype.slice.call(files);
        	
        	filesArr.forEach(function(f) {
        		if(!f.type.match("image.*")) {
        			alert("확장자는 이미지 확장자만 가능");
        			return;
        		}
        		
        		sel_file = f;
        		
        		var reader = new FileReader();
        		reader.onload = function(e) {
        			$("#img").attr("src", e.target.result);
        		}
        		reader.readAsDataURL(f);
        	});
        }
        
        </script>
    </head>
    <body>
    	<div align="center">
   			<table width="1200px">
   			<div>
   				<div class="img_wrap">
   					<img id="img" />
   				</div>
   			</div>
   				<tr>
   					<td>
   						부서명
   						<div>
   							<label><input type="text" id="groupname" readonly disabled></label>
   						</div>
   						휴가 내역
   						<div class="select">
   							<label><input type="radio" name="search_type" value="normal" checked/>연차 휴가</label>
   							<label><input type="radio" name="search_type" value="goodbad" />경조사 휴가</label>
   							<label><input type="radio" name="search_type" value="special" />특별 휴가</label>
   							<label><input type="radio" name="search_type" value="etc" />기타</label>
   						</div>
   						휴가 기간
   						<div>
   							<label><input type="text" id="sdate" readonly disabled>시작일</label>
   							~
   							<label><input type="text" id="edate" readonly disabled>종료일</label>
   						</div>
   					</td>
   				</tr>
   				<tr>
   					<td>
   						<textarea name="content" id="content" rows="10" cols="80" placeholder="휴가 사유를 적어주세요.">${boardView.content}</textarea>
   					</td>
   				</tr>
   				<tr>	
   					<td>
   						<label><input type="text" id="part">행선지</label>
   					</td>
   				</tr>
   				<tr>	
   					<td>
   						<label><input type="text" id="subphone">비상연락망</label>
   					</td>
   				</tr>
   				<tr>
   					<td align="right">
   						<button id="save" name="save">저장</button>
   						<button id="list" name="list">메인화면</button>
   					</td>
   				</tr>
   				<tr>
	   				<td>
	   					<p class="title">담당자 사인 업로드</p>
	   					<input type="file" id="input_img" />
	   				</td>
   				</tr>
   			</table>
    	</div>
    </body>
</html>