package org.jwd.gamenight.services.account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jwd.gamenight.entity.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class OperationImpl implements Operation{
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws NoSuchAlgorithmException 
	 */
	@Override
	public String convertToMD5(String str) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes());
		
		byte[] digest = md.digest();
		
		StringBuffer sb = new StringBuffer();
		
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		
		return sb.toString();
	}

}
