package game;

import org.apache.log4j.Logger;

/**
 * Scores manager, by player
 * @author nicolas v
 *
 */
public class Scores {

	private Logger log = Logger.getLogger(this.getClass());
	private int[] scores;

	public Scores(int nbPlayers) {
		scores = new int[nbPlayers];
	}

	protected void addPoints(int idPlayer, int scoreToAdd)
	{
		if(scoreToAdd > 0 && idPlayer >= 0 && idPlayer < scores.length)
			scores[idPlayer] += scoreToAdd;
		else
			log.error("Please specify a known idPlayer and a positive scoreToAdd");
	}

	protected void removePoints(int idPlayer, int scoreToRem)
	{
		if(scoreToRem > 0 && idPlayer >= 0 && idPlayer < scores.length)
		{
			scores[idPlayer] -= scoreToRem;
			if(scores[idPlayer] < 0)
				scores[idPlayer] = 0;
		}
		else
			log.error("Please specify a known idPlayer and a positive scoreToAdd");
	}


	public int getScore(int idPlayer)
	{
		if(idPlayer >= 0 && idPlayer < scores.length)
			return scores[idPlayer];
		else {
			log.error("Please specify a known idPlayer and a positive scoreToAdd");
			return -1;
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		for(int i =0 ; i<scores.length ; i++) {
			sb.append(i);
			sb.append('=');
			sb.append(scores[i]);
			sb.append('\n');
		}

		return sb.toString();
	}

	//Tests
	/*public static void main(String[] args) {
		Scores s = new Scores(4);
		System.out.println(s);
		s.addPoints(2,200);
		s.addPoints(4, 5);
		System.out.println(s);
		s.removePoints(-1, 100);
		s.removePoints(2, 50);
		s.removePoints(3, 10);
		System.out.println(s);
	}*/
}
