package org.jwd.gamenight.services.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.Part;

import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.entity.game.Game;
import org.jwd.gamenight.repository.AccountRepository;
import org.jwd.gamenight.repository.GameRepository;
import org.jwd.gamenight.repository.GameVoteRepository;
import org.jwd.gamenight.services.WorkOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService
{
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private GameVoteRepository gameVoteRepo;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<Game> getAllGames()
	{
		Iterable<Game> source = gameRepository.findAll();
		List<Game> games = new ArrayList<>();
		source.forEach(games::add);

		return games;
	}

	@Override
	public Game getGameData(String title)
	{
		return gameRepository.findByTitle(title);
	}

	public String updateGameFields(int gameId, String title, String description, Part filePart)
	{
		String fileValidation = WorkOperations.validateAvatarFile(filePart);
		int haveImage = fileValidation.equals("no file is selected") ? 0 : 1;
		Game thisGame = gameRepository.findByGameId(gameId);
		int previousImageVal = thisGame.getImage();

		Map<String, String> errorMap = new HashMap<>();

		if (title.length() < 2 || title.length() > 30)
		{
			errorMap.put("InavlidTitle",
					"The count of title symbols must be in interval 2-30!");
		}
		if (description.length() < 10)
		{
			errorMap.put("InavlidDescription",
					"The count of last name symbols must be more than 9!");
		}
		if (haveImage == 1)
		{
			if (!"valid".equals(fileValidation))
			{
				if ("invalid".equals(fileValidation))
				{
					errorMap.put("InavlidAvatar",
							"You can select only image/jpeg or image/png for avatar image!");
				}
			}
			else
			{
				previousImageVal = 1;
			}
		}

		if(errorMap.size()==0)
		{
			gameRepository.updateGameFields(gameId, title, description, previousImageVal);
	
			
			if(haveImage == 1)
    		{
    			try
				{
					WorkOperations.fromPartToFile(filePart, "src/main/resources/static/resources/game_images/" + thisGame.getGameId() + ".jpg", 100, 100);
				} 
    			catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
			
			errorMap.put("success", "success");
		}

		return "Успешно обновление!";
	}

	@Override
	public Map<String, String> createGame(String title, String description, Part image,
			String username)
	{
		String fileValidation = WorkOperations.validateAvatarFile(image);
		int haveImage = fileValidation.equals("no file is selected") ? 0 : 1;
		Account author = accountRepository.findByUsername(username);

		int authorId = author.getAccount_id();

		Map<String, String> errorMap = new HashMap<>();

		if (title.length() < 2 || title.length() > 30)
		{
			errorMap.put("InavlidTitle",
					"The count of title symbols must be in interval 2-30!");
		}
		if (description.length() < 10)
		{
			errorMap.put("InavlidDescription",
					"The count of last name symbols must be more than 9!");
		}
		if (haveImage == 1)
		{
			if (!"valid".equals(fileValidation))
			{
				if ("invalid".equals(fileValidation))
				{
					errorMap.put("InavlidAvatar",
							"You can select only image/jpeg or image/png for avatar image!");
				}
			}
		}

		if(errorMap.size()==0)
		{
			gameRepository.save(new Game(title,description,haveImage,authorId));
	
			try
			{
				WorkOperations.sendSpecialEmailToUser(author, title, "newGame");
			} 
			catch (AddressException e1)
			{
				System.out.println("-----------------greshkaaaa");
			} 
			catch (MessagingException e1)
			{
				System.out.println("-----------------greshkaaaa");
			}
			
			if(haveImage == 1)
    		{
				Game newGame = gameRepository.findByTitle(title);
				
    			try
				{
					WorkOperations.fromPartToFile(image, "src/main/resources/static/resources/game_images/" + newGame.getGameId() + ".jpg", 100, 100);
				} 
    			catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
			
			errorMap.put("success", "success");
		}	
		
		return errorMap;
	}

	@Override
	public List<Game> getGamesByAuthor(String author)
	{
		Account account = accountRepository.findByUsername(author);
		int authorId = account.getAccount_id();

		return gameRepository.findByAuthorId(authorId);
	}

	@Override
	public String deleteGame(int gameId)
	{
		gameVoteRepo.deleteGameVotes(gameId);
		
		try
		{

			File file = new File("src/main/resources/static/resources/game_images/" + gameId + ".jpg");

			file.delete();

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return Integer.toString(gameRepository.deleteGame(gameId));
	}

	@Override
	public int getVoteOfGame(int gameId)
	{
		Integer vote = gameVoteRepo.getGameVote(gameId);
		
		if(vote == null)
		{
			return 0;
		}
		
		return vote;
	}

	@Override
	public Game getGameById(int gameId)
	{
		return gameRepository.findByGameId(gameId);
	}

}
