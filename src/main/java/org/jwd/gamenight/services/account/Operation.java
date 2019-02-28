package org.jwd.gamenight.services.account;

import org.springframework.context.annotation.Scope;

@Scope("request") 
public interface Operation {
	String convertToMD5(String str) throws Exception;
}
