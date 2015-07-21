import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class mainspider {

	
	public static void main(String[] args) throws IOException {
		
		
		char test = '"';
		ArrayList<String> urlList = new ArrayList();
		for (int i = 1; i <= 28; i++ ) {
		String urlStr = "https://nytm.org/made?list=true&page=" + i;
		URLConnection connection = new URL(urlStr).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();

		BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

		String line;
		while ((line = r.readLine()) != null) {
			if (line.contains("cta inline hiring")) {
				StringBuilder sbTemp = new StringBuilder();
				sbTemp.append(line);
				sbTemp.delete(0, 41);
				StringBuilder sbTemp2 = new StringBuilder();
				char temp;
				int counter = 0;
				while ((temp = sbTemp.charAt(counter)) != test ){
					sbTemp2.append(temp);
					counter++;
				}
				urlList.add(sbTemp2.toString());
			}
			}
		}
		for (int x = 0; x < urlList.size(); x++) {
			System.out.println(urlList.get(x));
		}
	}
}
