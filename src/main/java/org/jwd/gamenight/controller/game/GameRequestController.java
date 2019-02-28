package org.jwd.gamenight.controller.game;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.entity.game.Game;
import org.jwd.gamenight.services.WorkOperations;
import org.jwd.gamenight.services.account.AccountService;
import org.jwd.gamenight.services.game.GameService;
import org.jwd.gamenight.services.game.GameVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameRequestController
{

	@Autowired
	private AccountService accountService;

	@Autowired
	private GameService gameService;

	@Autowired
	private GameVoteService gameVoteService;

	@RequestMapping(value = "addOrUpdateUserVote", method = RequestMethod.POST)
	public @ResponseBody String addOrUpdateUserVote(HttpServletRequest request,
			HttpServletResponse response)
	{
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		
		int gameId = Integer.parseInt(request.getParameter("gameId"));
		int value = Integer.parseInt(request.getParameter("value"));

		Account thisAccount = accountService.getUserAccountInfo(username);
		Game thisGame = gameService.getGameById(gameId);
		int accountId = thisAccount.getAccount_id();

		int res = gameVoteService.setOrUpdateGameVote(gameId, accountId, value);
		
		try
		{
			WorkOperations.sendSpecialEmailToUser(thisAccount, thisGame.getTitle(), "vote");
		} catch (AddressException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Integer.toString(res);
	}

	@RequestMapping(value = "deleteGame", method = RequestMethod.POST)
	public @ResponseBody String deleteGame(HttpServletRequest request,
			HttpServletResponse response)
	{
		int gameId = Integer.parseInt(request.getParameter("gameId"));

		String res = gameService.deleteGame(gameId);
		
		return res;
	}
}
