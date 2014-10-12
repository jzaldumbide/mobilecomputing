/**
 * 
 */
package au.edu.unimelb.comp90018.brickbreaker.framework.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickTypeI;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickTypeII;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickTypeIII;
import au.edu.unimelb.comp90018.brickbreaker.actors.GameLevel;
import au.edu.unimelb.comp90018.brickbreaker.actors.Paddle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

/**
 * This class handles the Network interaction in order to download the levels from a web server
 * @author achaves
 *
 */
public class LevelDownloader {
	/**
	 * String with the URI prefix where the levels are allocated
	 */
//	private static String URIPREFIX = "http://192.168.2.100/";
	private static String URIPREFIX = "http://anchavesb.netne.net/";
	/**
	 * Default class constructor
	 */
	public LevelDownloader(){
		
	}
	
	/**
	 * Make an http request to the web server
	 * @param levelName Name of the desired level
	 * @return A string with the web server's result (a XML)
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String makeHttpRequest (String levelName) throws ClientProtocolException, IOException{
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
	    HttpClient httpclient = new DefaultHttpClient(httpParams);
	    HttpResponse response = httpclient.execute(new HttpGet(URIPREFIX+levelName));
	    StatusLine statusLine = response.getStatusLine();
	    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        response.getEntity().writeTo(out);
	        out.close();
	        String responseString = out.toString();
	        //Log.v("Http Response",responseString);
	        return responseString;
	        //..more logic
	    } else{
	        //Closes the connection.
	        response.getEntity().getContent().close();
	        throw new IOException(statusLine.getReasonPhrase());
	    }
	}
	/**
	 * Downloads an xml level provided the name
	 * @param levelName Name of the desired level
	 * @return A string with the xml
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public String downloadGame(String levelName) throws ClientProtocolException, IOException, XmlPullParserException{
		String response = makeHttpRequest(levelName);
		return response;
	}
	/**
	 * Download High Scores and return the resultant XML
	 * @return XML result with High Scores
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String downloadHighScores() throws ClientProtocolException, IOException{
		String response = makeHttpRequest("highScore.php");
		return response;
	}
	/**
	 * Load a text file from the file system
	 * @param fileName
	 * @return A string with the file content
	 */
	private String loadFile (String fileName){
		FileHandle filehandle = Gdx.files.external(fileName);
		return filehandle.readString();
	}
	/**
	 * Persists a text file in the file system
	 * @param fileName File name
	 * @param value Text to write
	 */
	private void persistFile (String fileName, String value){
		FileHandle filehandle = Gdx.files.external(fileName);
		filehandle.writeString(value, false);		
	}
	/**
	 * Persist a level xml in the file system
	 * @param levelName Name of the level
	 * @param xml Xml with the file system
	 */
	public void persistGame (String levelName, String xml){
		persistFile(levelName, xml);
	}

	/**
	 * Persist high scores in the file system
	 * @param highScores XML with the high scores
	 */
	public void persistScores(String highScores) {
		persistFile("highScores.xml", highScores);
	}

	/**
	 * Load a level from the file system and generate a properly constructed GameLevel
	 * @param levelName Level Name
	 * @param worldWidth World Width
	 * @param worldHeight World Height
	 * @return A GameLevel constructed
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public GameLevel loadLevel(String levelName, float worldWidth, float worldHeight) throws XmlPullParserException, IOException {
		String levelXml = loadFile(levelName);
		GameLevel gameLevel = new GameLevel();
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput( new StringReader ( levelXml) );
        int eventType = xpp.getEventType();
        Paddle paddle = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
        	if(eventType == XmlPullParser.START_TAG) {
               String tagName = xpp.getName();
               if (tagName.equals("Brick")){
            	   int x = Integer.valueOf(xpp.getAttributeValue("", "x"));
            	   int y = Integer.valueOf(xpp.getAttributeValue("", "y"));
            	   //int w = Integer.valueOf(xpp.getAttributeValue("", "w"));
            	   String type = xpp.getAttributeValue("", "t");
            	   
					if (type.equals("typeI")) {
						gameLevel.addBrick(new BrickTypeI(x, y));
					} else if (type.equals("typeII")) {
						gameLevel.addBrick(new BrickTypeII(x, y));
					} else {
						gameLevel.addBrick(new BrickTypeIII(x, y));
					}
               }
               else{
            	   if (tagName.equals("Paddle")){
                	   int w = Integer.valueOf(xpp.getAttributeValue("", "w"));
                	   paddle = new Paddle(worldWidth / 2, worldHeight * 0.15f, w);
                	   gameLevel.setPaddle(paddle);            	   }
               }
            }
         eventType = xpp.next();
        }
    	   Ball ball = new Ball(worldWidth / 2, paddle.position.y + Paddle.PADDLE_HEIGHT / 2 + Ball.BALL_HEIGHT / 2,
				new Vector2(worldWidth * 0.4f, worldHeight * 0.4f));
		   gameLevel.setBall(ball);

		return gameLevel;
	}
	/**
	 * Load High Scores from the file system
	 * @return A string with high scores formatted like this:
	 *              1. Player: Score
	 *              2. Player: Score
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public String loadHighScores() throws XmlPullParserException, IOException {
		String highScores = loadFile("highScores.xml");
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput( new StringReader ( highScores ) );
        StringBuffer sb = new StringBuffer();
        int eventType = xpp.getEventType();
        sb.append("Scores\n\n");
        int position = 1;
        while (eventType != XmlPullParser.END_DOCUMENT) {
        	if(eventType == XmlPullParser.START_TAG) {
               String tagName = xpp.getName();
               if (tagName.equals("highScore")){
            	   String name = xpp.getAttributeValue("", "name");
            	   int score = Integer.valueOf(xpp.getAttributeValue("", "score"));
            	   sb.append(position);
            	   sb.append(". ");
            	   sb.append(name);
            	   sb.append(": ");
            	   sb.append(score);
            	   sb.append("\n");
            	   position++;
               }
            }
         eventType = xpp.next();
        }
		return sb.toString();
	}
	/**
	 * Load High Scores from the file system and return them as a List of Integers
	 * @return A list of integers with the top 10 scores
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public List<Integer> loadHighScoresAsList() throws XmlPullParserException, IOException {
		String highScores = loadFile("highScores.xml");
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput( new StringReader ( highScores ) );
        List<Integer> scores = new ArrayList<Integer>();
        int eventType = xpp.getEventType();
        int position = 1;
        while (eventType != XmlPullParser.END_DOCUMENT) {
        	if(eventType == XmlPullParser.START_TAG) {
               String tagName = xpp.getName();
               if (tagName.equals("highScore")){
            	   String name = xpp.getAttributeValue("", "name");
            	   int score = Integer.valueOf(xpp.getAttributeValue("", "score"));
            	   scores.add(score);
               }
            }
         eventType = xpp.next();
        }
		return scores;
	}

	/**
	 * Submits a new high score to the server
	 * @param name Player's name
	 * @param score Player's score
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void submitHighScore(String name, int score) throws ClientProtocolException, IOException {
		String url = "highScore.php?action=add&name="+URLEncoder.encode(name, "utf-8")+"&score="+score;
		makeHttpRequest(url);		
	}
}
