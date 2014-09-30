/**
 * 
 */
package au.edu.unimelb.comp90018.brickbreaker.framework.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickTypeI;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickTypeII;
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
	//private static String URIPREFIX = "http://192.168.1.1/";
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
	    HttpClient httpclient = new DefaultHttpClient();
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
	 * Downloads the game and generate the proper model classes that represents the XML
	 * @param levelName Name of the desired level
	 * @return A model with all the parameters of the level
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public String downloadGame(String levelName) throws ClientProtocolException, IOException, XmlPullParserException{
		String response = makeHttpRequest(levelName);
		return response;
	}
	public String downloadHighScores() throws ClientProtocolException, IOException{
		String response = makeHttpRequest("highScore.php");
		return response;
	}
	private String loadFile (String fileName){
		FileHandle filehandle = Gdx.files.external(fileName);
		return filehandle.readString();
	}
	private void persistFile (String fileName, String value){
		FileHandle filehandle = Gdx.files.external(fileName);
		filehandle.writeString(value, false);		
	}
	public void persistGame (String levelName, String xml){
		persistFile(levelName, xml);
	}

	public void persistScores(String highScores) {
		persistFile("highScores.xml", highScores);
	}

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
            	   
            	   if (type.equals("typeI")){
            		  //Gdx.app.log("Adding Brick Type I", "Adding Brick Type I");
            	      gameLevel.addBrick(new BrickTypeI(x, y));
            	   }
            	   else{
            		   //Gdx.app.log("Adding Brick Type II", "Adding Brick Type I");
            		   gameLevel.addBrick(new BrickTypeII(x , y));
            	   }
               }
               else{
            	   if (tagName.equals("Paddle")){
                	   int x = Integer.valueOf(xpp.getAttributeValue("", "x"));
                	   int y = Integer.valueOf(xpp.getAttributeValue("", "y"));
                	   int w = Integer.valueOf(xpp.getAttributeValue("", "w"));
                	   //int speed = Integer.valueOf(xpp.getAttributeValue("", "speed"));
                	   paddle = new Paddle(worldWidth / w, worldHeight * 0.15f);
                	   gameLevel.setPaddle(paddle);            	   }
            	   else if (tagName.equals("Ball")){
                	//   int radius = Integer.valueOf(xpp.getAttributeValue("", "radius"));
                	//   int speed = Integer.valueOf(xpp.getAttributeValue("", "speed"));
                	   Ball ball = new Ball(worldWidth / 2, paddle.position.y + Paddle.PADDLE_HEIGHT / 2 + Ball.BALL_HEIGHT / 2,
           					new Vector2(worldWidth * 0.4f, worldHeight * 0.4f));
            		   gameLevel.setBall(ball);
            	   }
               }
            }
         eventType = xpp.next();
        }

		return gameLevel;
	}
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
               }
            }
         eventType = xpp.next();
        }
		return sb.toString();
	}
}
