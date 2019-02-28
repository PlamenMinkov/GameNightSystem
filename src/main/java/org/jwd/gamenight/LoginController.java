package org.jwd.gamenight;

import org.jwd.gamenight.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.jwd.gamenight.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		model.addAttribute("user", accountService.getUserAccountInfo(username));
		System.out.println(accountService.getUserAccountInfo(username));
		
		return "login";
	}
}