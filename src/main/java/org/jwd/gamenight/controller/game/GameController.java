package org.jwd.gamenight.controller.game;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.jwd.gamenight.constants.UrlConstants;
import org.jwd.gamenight.services.account.AccountService;
import org.jwd.gamenight.services.game.GameService;
//import org.jwd.gamenight.services.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController
{
	 @Autowired
	 private GameService gameService;
	
	 @Autowired
		private AccountService accountService;

	@RequestMapping(value = UrlConstants.GAME_REGISTER_URL, method = RequestMethod.GET)
	public String home(Locale locale, Model model)
	{
		model.addAttribute("gameService", gameService);
		model.addAttribute("games", gameService.getAllGames());

		model.addAttribute("createGameUrl", UrlConstants.CREATE_GAME_URL);
		model.addAttribute("title", "Игри");
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		model.addAttribute("user", accountService.getUserAccountInfo(username));

		return "game/gameRegister";
	}

	@RequestMapping(value = UrlConstants.CREATE_GAME_URL, method = RequestMethod.GET)
	public String addStudent(Model model)
	{
		model.addAttribute("saveGame", UrlConstants.CREATE_GAME_SAVE_URL);
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		model.addAttribute("user", accountService.getUserAccountInfo(username));

		return "game/createGame";
	}

	@RequestMapping(value = UrlConstants.CREATE_GAME_SAVE_URL, method = RequestMethod.POST)
	public String addStudentSave(Model model, HttpServletRequest request)
			throws Exception
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Part filePart = request.getPart("image");

		gameService.createGame(request.getParameter("title"),
		 request.getParameter("description"), filePart, username);

		return "redirect:/gameRegister";
	}

	@RequestMapping(value = UrlConstants.EDIT_OWN_GAME_URL, method = RequestMethod.GET)
	public String editOwnGame(Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		model.addAttribute("user", accountService.getUserAccountInfo(username));
		model.addAttribute("editGame", "/editGame");

		model.addAttribute("games", gameService.getGamesByAuthor(username));

		return "game/editDeleteOwnGame";
	}
	
	@RequestMapping(value = "/editGame", method = RequestMethod.POST)
	public String editGame(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		int gameId = Integer.parseInt(request.getParameter("gameId"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Part filePart = request.getPart("image");

		String res = gameService.updateGameFields(gameId, title, description, filePart);

		return "redirect:" + UrlConstants.EDIT_OWN_GAME_URL;
	}
}
