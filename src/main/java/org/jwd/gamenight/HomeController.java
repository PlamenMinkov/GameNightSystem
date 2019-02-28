package org.jwd.gamenight;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.jwd.gamenight.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.jwd.gamenight.constants.UrlConstants;
//import org.jwd.gamenight.entity.account.Account;
//import org.jwd.gamenight.utils.UserUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	final static Logger log = Logger.getLogger(HomeController.class);
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws NoSuchAlgorithmException {
		log.debug("Hello this is a debug message");
		log.info("Hello this is an info message");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		model.addAttribute("user", accountService.getUserAccountInfo(username));
		
		return "home";
	}
}
