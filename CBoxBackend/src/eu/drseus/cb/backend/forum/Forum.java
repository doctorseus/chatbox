package eu.drseus.cb.backend.forum;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.security.auth.login.LoginException;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import eu.drseus.cb.backend.forum.exception.ForumIOException;
import eu.drseus.cb.shared.forum.chat.Message;
import eu.drseus.cb.shared.forum.user.User;

public class Forum {
	
	private static final String URL_FORUM = "http://www.informatik-forum.at/";
	
	private BasicCookieStore cookieStore;
	private CloseableHttpClient httpclient;
	

	public Forum(){
		 cookieStore = new BasicCookieStore();
	     httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
	}
	
	public void login(String username, String password) throws ForumIOException, LoginException {
		
		HttpPost httpPostLogin = new HttpPost(URL_FORUM + "login.php?do=login");
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("vb_login_username", username));
		nvps.add(new BasicNameValuePair("vb_login_password", password));
		nvps.add(new BasicNameValuePair("vb_login_password_hint", "Password"));
		nvps.add(new BasicNameValuePair("cookieuser", "1"));
		nvps.add(new BasicNameValuePair("s", ""));
		nvps.add(new BasicNameValuePair("securitytoken", "guest"));
		nvps.add(new BasicNameValuePair("do", "login"));
		nvps.add(new BasicNameValuePair("vb_login_md5password", ""));
		nvps.add(new BasicNameValuePair("vb_login_md5password_utf", ""));

		httpPostLogin.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		
        
		try {
			CloseableHttpResponse response = httpclient.execute(httpPostLogin);
			
			try {
				HttpEntity entity = response.getEntity();

				//TODO: Check if login is successful.
				boolean b = false;
				if(b)
					throw new LoginException("You have entered an invalid username or password.");
				
				EntityUtils.consume(entity);

			} finally {
				response.close();
			}
			
		} catch (IOException e) {
			throw new ForumIOException(e);
		}
        
       
        
	}

	/**
	 * Returns the newest chatbox messages. (order: new to old, like on the page)
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Message> fetchChatbox() throws Exception {
		// To prevent Jsoup parsing errors. (Chatbox sends a corrupt html page)
		String htmlBefore = "<html><head></head><body><table>";
		String htmlAfter = "</table></body></html>";

		// GMT(+0) Timezone
		SimpleDateFormat timeDateFormat = new SimpleDateFormat("dd-MM-yy, HH:mm");
		timeDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		ArrayList<Message> chatMessages = new ArrayList<>();

		HttpGet httpGetChatboxMessages = new HttpGet(URL_FORUM + "misc.php?show=ccbmessages");

        CloseableHttpResponse response = httpclient.execute(httpGetChatboxMessages);
        try {
        	
            HttpEntity entity = response.getEntity();
            
            String content = EntityUtils.toString(entity);
            
            Document doc = Jsoup.parse(htmlBefore+content+htmlAfter);
			Element bodyE = doc.body();
            
            Elements chatRows = bodyE.select("tr");
            
            for(int i = 0; i < chatRows.size(); i++){
            	Element chatRow = chatRows.get(i);

            	String userId = chatRow.select("td").get(0).select("a").get(1).attr("href").split("u=")[1];
            	String userName = chatRow.select("td").get(0).select("a").get(1).text();
            	
            	User user = User.create(Long.parseLong(userId), userName);
            	
            	String messageId = chatRow.select("td").get(0).select("a").get(0).attr("href").split("ccbloc=")[1];
            	String timeString = chatRow.select("td").get(0).select("span").text().split("\\]")[0].substring(1);
            	Date messageTime = timeDateFormat.parse(timeString);
            	String messageContent =  chatRow.select("td").get(1).html();
            	
            	Message message = Message.create(Long.parseLong(messageId), user, messageTime, messageContent);
            	chatMessages.add(message);
            	
            }
            
            EntityUtils.consume(entity);
            
        } finally {
            response.close();
        }
		
        //Collections.reverse(chatMessages); //uncomment this for reverse order new->old  TO old->new
        return chatMessages;
        
	}
	
}
