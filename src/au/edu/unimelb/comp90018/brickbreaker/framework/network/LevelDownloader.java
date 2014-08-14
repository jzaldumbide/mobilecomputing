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

import android.util.Log;
import au.edu.unimelb.comp90018.brickbreaker.framework.model.Ball;
import au.edu.unimelb.comp90018.brickbreaker.framework.model.Brick;
import au.edu.unimelb.comp90018.brickbreaker.framework.model.GameLevel;
import au.edu.unimelb.comp90018.brickbreaker.framework.model.Paddle;

/**
 * @author achaves
 *
 */
public class LevelDownloader {
	private static String URIPREFIX = "http://192.168.1.1/";
	public LevelDownloader(){
		
	}
	
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
	public GameLevel downloadGame(String levelName) throws ClientProtocolException, IOException, XmlPullParserException{
		String response = makeHttpRequest(levelName);
		GameLevel gameLevel = new GameLevel();
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput( new StringReader ( response ) );
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
        	if(eventType == XmlPullParser.START_TAG) {
               String tagName = xpp.getName();
               if (tagName.equals("Brick")){
            	   int x = Integer.valueOf(xpp.getAttributeValue("", "x"));
            	   int y = Integer.valueOf(xpp.getAttributeValue("", "y"));
            	   int w = Integer.valueOf(xpp.getAttributeValue("", "w"));
            	   String type = xpp.getAttributeValue("", "t");
            	   Brick brick = new Brick(w,x,y,type);
            	   gameLevel.addBrick(x,y,brick);
               }
               else{
            	   if (tagName.equals("Paddle")){
                	   int x = Integer.valueOf(xpp.getAttributeValue("", "x"));
                	   int y = Integer.valueOf(xpp.getAttributeValue("", "y"));
                	   int w = Integer.valueOf(xpp.getAttributeValue("", "w"));
                	   int speed = Integer.valueOf(xpp.getAttributeValue("", "speed"));
                	   Paddle paddle = new Paddle(w,x,y,speed);
                	   gameLevel.setPaddle(paddle);
            	   }
            	   else if (tagName.equals("Ball")){
                	   int radius = Integer.valueOf(xpp.getAttributeValue("", "radius"));
                	   int speed = Integer.valueOf(xpp.getAttributeValue("", "speed"));
            		   Ball ball = new Ball(speed, radius);
            		   gameLevel.setBall(ball);
            	   }
               }
            }
         eventType = xpp.next();
        }

		return gameLevel;
		
	}
}
