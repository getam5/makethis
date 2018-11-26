package com.spring.myapp.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.myapp.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	BoardService boardService;

	@RequestMapping(value = "/board/list")
	public String boardList(@RequestParam Map<String, Object> paramMap, Model model) {

		int startPage = (paramMap.get("startPage")!=null?Integer.parseInt(paramMap.get("startPage").toString()):1);
		int visiblePages = (paramMap.get("visiblePages")!=null?Integer.parseInt(paramMap.get("visiblePages").toString()):10);
		int totalCnt = boardService.getContentCnt(paramMap);


		BigDecimal decimal1 = new BigDecimal(totalCnt);
		BigDecimal decimal2 = new BigDecimal(visiblePages);
		BigDecimal totalPage = decimal1.divide(decimal2, 0, BigDecimal.ROUND_UP);

		int startLimitPage = 0;
		if(startPage==1){
			startLimitPage = 0;
		}else{
			startLimitPage = (startPage-1)*visiblePages;
		}

		paramMap.put("start", startLimitPage);

		//ORACLE
		paramMap.put("end", startLimitPage+visiblePages);

		model.addAttribute("startPage", startPage+"");
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("boardList", boardService.getContentList(paramMap));

		return "boardList";

	}

	@RequestMapping(value = "/board/view")
	public String boardView(@RequestParam Map<String, Object> paramMap, Model model) {

		model.addAttribute("replyList", boardService.getReplyList(paramMap));
		model.addAttribute("boardView", boardService.getContentView(paramMap));

		return "boardView";

	}

	@RequestMapping(value = "/board/edit")
	public String boardEdit(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, Model model) {

		String Referer = request.getHeader("referer");

		if(Referer!=null){
			if(paramMap.get("id") != null){
				if(Referer.indexOf("/board/view")>-1){

					model.addAttribute("boardView", boardService.getContentView(paramMap));
					return "boardEdit";
				}else{
					return "redirect:/board/list";
				}
			}else{ 
				if(Referer.indexOf("/board/list")>-1){
					return "boardEdit";
				}else{
					return "redirect:/board/list";
				}
			}
		}else{
			return "redirect:/board/list";
		}

	}
	
	@RequestMapping(value="/board/save", method=RequestMethod.POST)
	@ResponseBody
	public Object boardSave(@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> retVal = new HashMap<String, Object>();

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("password").toString(), null);
		paramMap.put("password", password);

		int result = boardService.regContent(paramMap);

		if(result>0){
			retVal.put("code", "OK");
			retVal.put("message", "등록에 성공 하였습니다.");
		}else{
			retVal.put("code", "FAIL");
			retVal.put("message", "등록에 실패 하였습니다.");
		}

		return retVal;

	}
	
	@RequestMapping(value = "/main/vacWrite")
	public String vacationWrite(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, Model model) {

		String Referer = request.getHeader("referer");
		if(Referer!=null){
			if(paramMap.get("id") != null){
				if(Referer.indexOf("/board/view")>-1){
//					Thumbnails.of(new File("/src/main/webapp/WEB-INF/views/test.jpg")).size(160, 160).toFile(new File("thumbnail.jpg"));
					
					model.addAttribute("boardView", boardService.getContentView(paramMap));
					return "vacationWrite";
				}else{
					return "redirect:/board/list";
				}
			}else{ 
				if(Referer.indexOf("/board/list")>-1){
					return "vacationWrite";
				}else{
					return "redirect:/board/list";
				}
			}
		}else{
			return "redirect:/board/list";
		}

	}
	
	@RequestMapping(value="/main/vacSave", method=RequestMethod.POST)
	@ResponseBody
	public Object vacationSave(@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> retVal = new HashMap<String, Object>();

		int result = boardService.regVacation(paramMap);

		if(result>0){
			retVal.put("code", "OK");
			retVal.put("message", "등록에 성공 하였습니다.");
		}else{
			retVal.put("code", "FAIL");
			retVal.put("message", "등록에 실패 하였습니다.");
		}

		return retVal;

	}
	
	@RequestMapping(value="/board/del", method=RequestMethod.POST)
	@ResponseBody
	public Object boardDel(@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> retVal = new HashMap<String, Object>();

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("password").toString(), null);
		paramMap.put("password", password);

		int result = boardService.delBoard(paramMap);

		if(result>0){
			retVal.put("code", "OK");
		}else{
			retVal.put("code", "FAIL");
			retVal.put("message", "패스워드를 확인해주세요.");
		}

		return retVal;

	}

	@RequestMapping(value="/board/check", method=RequestMethod.POST)
	@ResponseBody
	public Object boardCheck(@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> retVal = new HashMap<String, Object>();

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("password").toString(), null);
		paramMap.put("password", password);

		int result = boardService.getBoardCheck(paramMap);

		if(result>0){
			retVal.put("code", "OK");
		}else{
			retVal.put("code", "FAIL");
			retVal.put("message", "패스워드를 확인해주세요.");
		}

		return retVal;

	}

	@RequestMapping(value="/board/reply/save", method=RequestMethod.POST)
	@ResponseBody
	public Object boardReplySave(@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> retVal = new HashMap<String, Object>();

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		paramMap.put("reply_password", password);

		int result = boardService.regReply(paramMap);

		if(result>0){
			retVal.put("code", "OK");
			retVal.put("reply_id", paramMap.get("reply_id"));
			retVal.put("parent_id", paramMap.get("parent_id"));
			retVal.put("message", "등록에 성공 하였습니다.");
		}else{
			retVal.put("code", "FAIL");
			retVal.put("message", "등록에 실패 하였습니다.");
		}

		return retVal;

	}

	@RequestMapping(value="/board/reply/del", method=RequestMethod.POST)
	@ResponseBody
	public Object boardReplyDel(@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> retVal = new HashMap<String, Object>();

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		paramMap.put("reply_password", password);

		int result = boardService.delReply(paramMap);

		if(result>0){
			retVal.put("code", "OK");
		}else{
			retVal.put("code", "FAIL");
			retVal.put("message", "삭제에 실패했습니다. 패스워드를 확인해주세요.");
		}

		return retVal;

	}

	@RequestMapping(value="/board/reply/check", method=RequestMethod.POST)
	@ResponseBody
	public Object boardReplyCheck(@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> retVal = new HashMap<String, Object>();

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		paramMap.put("reply_password", password);

		boolean check = boardService.checkReply(paramMap);

		if(check){
			retVal.put("code", "OK");
			retVal.put("reply_id", paramMap.get("reply_id"));
		}else{
			retVal.put("code", "FAIL");
			retVal.put("message", "패스워드를 확인해 주세요.");
		}

		return retVal;

	}

	@RequestMapping(value="/board/reply/update", method=RequestMethod.POST)
	@ResponseBody
	public Object boardReplyUpdate(@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> retVal = new HashMap<String, Object>();

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
		paramMap.put("reply_password", password);

		System.out.println(paramMap);

		boolean check = boardService.updateReply(paramMap);

		if(check){
			retVal.put("code", "OK");
			retVal.put("reply_id", paramMap.get("reply_id"));
			retVal.put("message", "수정에 성공 하였습니다.");
		}else{
			retVal.put("code", "FAIL");
			retVal.put("message", "수정에 실패 하였습니다.");
		}

		return retVal;

	}


}
