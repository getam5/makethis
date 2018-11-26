package com.spring.myapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.spring.myapp.dao.BoardDao;
import com.spring.myapp.domain.Board;
import com.spring.myapp.domain.BoardReply;
import com.spring.myapp.service.BoardService;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Resource(name="boardDao")
	private BoardDao boardDao;

	@Override
	public int regContent(Map<String, Object> paramMap) {
		if(paramMap.get("id")==null) {
			return boardDao.regContent(paramMap);
		}else {
			return boardDao.modifyContent(paramMap);
		}
	}
	
	@Override
	public int regVacation(Map<String, Object> paramMap) {
		//휴가 신청서 등록이고.. 꼭 전달이 되야하나?? 담당자의 확인만 존재하면되지않나?
		if(paramMap.get("id") == null) {
			return boardDao.regVacation(paramMap);
		} else {
			return boardDao.modifyVacation(paramMap);
		}
	}
	
	@Override
	public int getContentCnt(Map<String, Object> paramMap) {
		return boardDao.getContentCnt(paramMap);
	}

	@Override
	public List<Board> getContentList(Map<String, Object> paramMap) {
		return boardDao.getContentList(paramMap);
	}

	@Override
	public Board getContentView(Map<String, Object> paramMap) {
		return boardDao.getContentView(paramMap);
	}

	@Override
	public int regReply(Map<String, Object> paramMap) {
		return boardDao.regReply(paramMap);
	}

	@Override
	public List<BoardReply> getReplyList(Map<String, Object> paramMap) {

		List<BoardReply> boardReplyList = boardDao.getReplyList(paramMap);

		List<BoardReply> boardReplyListParent = new ArrayList<BoardReply>();
		List<BoardReply> boardReplyListChild = new ArrayList<BoardReply>();
		List<BoardReply> newBoardReplyList = new ArrayList<BoardReply>();

		for(BoardReply boardReply: boardReplyList){
			if(boardReply.getDepth().equals("0")){
				boardReplyListParent.add(boardReply);
			}else{
				boardReplyListChild.add(boardReply);
			}
		}

		for(BoardReply boardReplyParent: boardReplyListParent){
			newBoardReplyList.add(boardReplyParent);
			for(BoardReply boardReplyChild: boardReplyListChild){
				if(boardReplyParent.getReply_id().equals(boardReplyChild.getParent_id())){
					newBoardReplyList.add(boardReplyChild);
				}

			}

		}

		return newBoardReplyList;
	}

	@Override
	public int delReply(Map<String, Object> paramMap) {
		return boardDao.delReply(paramMap);
	}

	@Override
	public int getBoardCheck(Map<String, Object> paramMap) {
		return boardDao.getBoardCheck(paramMap);
	}

	@Override
	public int delBoard(Map<String, Object> paramMap) {
		return boardDao.delBoard(paramMap);
	}

	@Override
	public boolean checkReply(Map<String, Object> paramMap) {
		return boardDao.checkReply(paramMap);
	}

	@Override
	public boolean updateReply(Map<String, Object> paramMap) {
		return boardDao.updateReply(paramMap);
	}

}
