package org.jwd.gamenight.controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jwd.gamenight.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountRequestController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "addActivePeriod", method = RequestMethod.POST)
	public @ResponseBody String addPeriod(HttpServletRequest request, HttpServletResponse response) {
		int accountId = Integer.parseInt(request.getParameter("accountId"));
		
		int newActivePeriod = accountService.addActivePeriod(accountId);
		
		return Integer.toString(newActivePeriod);
	}
	
	@RequestMapping(value = "changeAuthority", method = RequestMethod.POST)
	public @ResponseBody String toAdmin(HttpServletRequest request, HttpServletResponse response) {
		int accountId = Integer.parseInt(request.getParameter("accountId"));
		int newValue = Integer.parseInt(request.getParameter("newValue"));
		
		accountService.changeAuthority(accountId, newValue);
		
		return "success";
	}
}
