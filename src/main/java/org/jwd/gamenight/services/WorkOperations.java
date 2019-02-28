package org.jwd.gamenight.services;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.jwd.gamenight.entity.account.Account;

public class WorkOperations
{
	private static int imgTransformWidth = 50;

	private static int imgTransformHeight = 50;

	/**
	 * <p>
	 * This function mave image file format and existing validation
	 *
	 * @param filePart
	 * @return
	 */
	public static String validateAvatarFile(Part filePart)
	{
		if (filePart.getSize() > 5)
		{
			String fileFormat = filePart.getContentType();
			
			if ("image/jpeg".equals(fileFormat)	|| "image/png".equals(fileFormat))
			{
				return "valid";
			}
			
			return "invalid";
		}
		
		return "no file is selected";
	}

	/**
	 * <p>
	 * This function validate given email address by special regex pattern
	 *
	 * @param email
	 * @return
	 */
	public static String validateEmail(String email)
	{
		Pattern r = Pattern.compile(Constants.EMAIL_REGEX_PATTERN);
		Matcher emailScan = r.matcher(email);
		r = Pattern.compile("[(  )(\\)]");
		Matcher spaceScan = r.matcher(email);

		if (emailScan.find() && !spaceScan.find())
		{
			return "valid";
		}
		
		return "invalid";
	}

	
	public static byte[] fromPartToFile(Part avatar, String filePath, int width, int height) throws IOException
    {
        InputStream fileContent = null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        File file = new File(filePath);
        FileOutputStream fos = new FileOutputStream(file);
        

        int nRead;
        byte[] bytes = new byte[(int)avatar.getSize()];

        try
        {
            fileContent = avatar.getInputStream();

            while ((nRead = fileContent.read(bytes, 0, bytes.length)) != -1)
            {
                buffer.write(bytes, 0, nRead);
            }

            buffer.flush();

            bytes = buffer.toByteArray();
            bytes = scale(bytes, width, height);
            
            fos.write(bytes);            

            return bytes;
        }
        finally
        {
        	fos.close();
            IOUtils.closeQuietly(buffer);
            IOUtils.closeQuietly(fileContent);
        }
    }
	
	
	/**
	 * <p>
	 * Resize given image file by also given like parameters new width and
	 * height
	 *
	 * @param fileData
	 * @param width
	 * @param height
	 * @return resized file byte array
	 * @throws IOException
	 */
	public static byte[] scale(byte[] fileData, int width, int height)
			throws IOException
	{
		ByteArrayInputStream in = new ByteArrayInputStream(fileData);

		try
		{
			BufferedImage img = ImageIO.read(in);

			if (height == 0)
			{
				height = (width * img.getHeight())
						/ img.getWidth();
			}

			if (width == 0)
			{
				width = (height * img.getWidth())
						/ img.getHeight();
			}

			Image scaledImage = img.getScaledInstance(width,
					height, Image.SCALE_SMOOTH);
			BufferedImage imageBuff = new BufferedImage(width,
					height, BufferedImage.TYPE_INT_RGB);
			imageBuff.getGraphics().drawImage(scaledImage, 0, 0,
					new Color(0, 0, 0), null);

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			ImageIO.write(imageBuff, "jpg", buffer);

			return buffer.toByteArray();
		} 
		catch (IOException e)
		{
			throw new IOException(
					"IOException on reading or writing on image scale process!");
		}
	}

	/**
	 * <p>
	 * Generate special notification massage for givan like parameter eventType
	 * and next this call function for sending generated message like email
	 *
	 * @param user
	 * @param gameName
	 * @param eventType
	 * @throws AddressException
	 * @throws MessagingException
	 * @see org.jwd.gamenight.services.seeburger.taskmanager.service.WorkOperations#sendEmail(String,
	 *      String)
	 */
	public static void sendSpecialEmailToUser(Account user, String gameName,
			String eventType) throws AddressException, MessagingException
	{
		String message = "Здравей " + user.getFirstName() + " " + user.getLastName() + ",\n\n";
		String theme = "";

		switch (eventType)
		{
			case "newGame":
				message += "Твоето предложение за игра на име " + gameName + " беше създадено.";
				theme = "Предложение за игра";
				break;
			case "vote":
				message += "Твоят вот за играта " + gameName + "беше отчетен.";
				theme = "Вот за игра";
				break;
	
			default:
				break;
		}

		message += "\n\nПоздави,\nGame Night";

		sendEmail(message, user.getEmail(), "minkov.plamen93@gmail.com", theme);
	}

	/**
	 * Make connection to Gmail service and send email to given like parameret
	 * email address
	 *
	 * @param messageText
	 * @param address
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private static void sendEmail(String messageText, String address, String authorAddress, String theme)
			throws AddressException, MessagingException
	{
		final String username = "minkov.plamen93@gmail.com";
		final String password = "pa4inito";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(username, password);
					}
				});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(authorAddress));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(address));
		message.setSubject(theme);
		message.setText(messageText);

		Transport.send(message);
	}

	public static void setImgTransformWidth(int imgTransformWidth)
	{
		WorkOperations.imgTransformWidth = imgTransformWidth;
	}

	public static void setImgTransformHeight(int imgTransformHeight)
	{
		WorkOperations.imgTransformHeight = imgTransformHeight;
	}
}
