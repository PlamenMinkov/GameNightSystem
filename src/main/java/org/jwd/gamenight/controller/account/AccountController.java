package org.jwd.gamenight.controller.account;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.jwd.gamenight.constants.UrlConstants;
import org.jwd.gamenight.dto.account.UserSearch;
import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.services.WorkOperations;
import org.jwd.gamenight.services.account.AccountService;
import org.jwd.gamenight.services.account.Operation;
import org.jwd.gamenight.services.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController
{
	@Autowired
	private Operation operationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private GameService gameService;

	
	@RequestMapping(value = UrlConstants.CUSTOMERS_REGISTER_URL, method = RequestMethod.GET)
	public String home(Model model,
			@ModelAttribute("UserSearch") UserSearch userSearch)
	{
		model.addAttribute("customers",
				accountService.getAccounts(userSearch.getUsername()));

		model.addAttribute("accountRegister",
				UrlConstants.CUSTOMERS_REGISTER_URL);
		model.addAttribute("registrationUrl", UrlConstants.REGISTRATION_URL);
		model.addAttribute("accountProfile", UrlConstants.ACCOUNT_PROFILE_URL);

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		model.addAttribute("user", accountService.getUserAccountInfo(username));

		model.addAttribute("authority", auth.getAuthorities().toArray()[0]);

		return "customersRegister";
	}

	
	@RequestMapping(value = UrlConstants.REGISTRATION_URL, method = RequestMethod.GET)
	public String addStudent(Model model)
	{
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		model.addAttribute("user", accountService.getUserAccountInfo(username));
		model.addAttribute("saveRegistration",
				UrlConstants.REGISTRATION_SAVE_URL);
		
		return "registration";
	}

	
	@RequestMapping(value = UrlConstants.REGISTRATION_SAVE_URL, method = RequestMethod.POST)
	public String addStudentSave(Model model, HttpServletRequest request)
			throws Exception
	{
		model.addAttribute("customers", accountService.getAccounts(null));

		String password = request.getParameter("password");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String description = request.getParameter("description");
		Part filePart = request.getPart("avatar");		

		accountService.createUserAccountInfo(username, email, description, password, firstName,	lastName, filePart);
		
		model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication());
		
		addStudent(model);

		return "redirect:/";
	}

	
	@RequestMapping(value = UrlConstants.ACCOUNT_PROFILE_URL, method = RequestMethod.GET)
	public String accountProfile(Model model,
			@ModelAttribute("UserSearch") UserSearch userSearch)
	{
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		model.addAttribute("user", accountService.getUserAccountInfo(username));
		model.addAttribute("saveRegistration",
				UrlConstants.REGISTRATION_SAVE_URL);
		model.addAttribute("account",
				accountService.getUserAccountInfo(userSearch.getUsername()));
		model.addAttribute("games",
				gameService.getGamesByAuthor(userSearch.getUsername()));
		model.addAttribute("editable", false);

		return "account/profilePage";
	}

	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Model model, HttpServletRequest request)
			throws Exception
	{
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		model.addAttribute("user", accountService.getUserAccountInfo(username));
		
		model.addAttribute("gameService", gameService);
		model.addAttribute("account", accountService.getUserAccountInfo(username));
		model.addAttribute("games", gameService.getGamesByAuthor(username));
		model.addAttribute("editable", false);

		return "account/profilePage";
	}
	
	@RequestMapping(value = UrlConstants.EDIT_ACCOUNT, method = RequestMethod.GET)
	public String editProfile(Model model, HttpServletRequest request)
			throws Exception
	{
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		model.addAttribute("user", accountService.getUserAccountInfo(username));
		
		model.addAttribute("gameService", gameService);
		model.addAttribute("account", accountService.getUserAccountInfo(username));
		model.addAttribute("games", gameService.getGamesByAuthor(username));
		model.addAttribute("editable", true);
		
		model.addAttribute("editAccount",
				UrlConstants.EDIT_ACCOUNT);

		return "account/profilePage";
	}
	
	@RequestMapping(value = UrlConstants.EDIT_ACCOUNT, method = RequestMethod.POST)
	public String editProfileSave(Model model, HttpServletRequest request)
			throws Exception
	{
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int accountId = Integer.parseInt(request.getParameter("accountId"));
		Part filePart = request.getPart("avatar");	
		
		Map<String, String> resultMap = accountService.editUserAccountInfo(accountId, email, firstName, lastName, filePart);

		return "redirect:/profile";
	}
	
	@RequestMapping(value = "/sender", method = RequestMethod.GET)
	public String mailSend(Model model, HttpServletRequest request)
			throws Exception
	{
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		String receiverEmail = request.getParameter("mailAdress");
		
		model.addAttribute("receiver", accountService.findAccountByEmail(receiverEmail));
		model.addAttribute("user", accountService.getUserAccountInfo(username));

		return "account/mailSender";
	}
}
