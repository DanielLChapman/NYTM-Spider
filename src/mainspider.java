import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;

public class mainspider {

	public static void main(String[] args) throws IOException {

		// To reduce the repetitive hits to nytm, only updates every day once.
		ArrayList<String> urlList = new ArrayList();
		// opens the text file of urls
		BufferedReader br = new BufferedReader(new FileReader("urlList.txt"));
		// checks the date with teh date in the file
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		// if the dates arent the same, update
		if (br.readLine() == dateFormat.format(date)) {
			urlList = getUrls();
		}
		// if they are the same, just pull the urls from the text file
		else {
			String line;
			while ((line = br.readLine()) != null) {
				urlList.add(line);
			}
		}
		// print them out just to check that they are being read correctly.
		for (int x = 0; x < urlList.size(); x++) {
			System.out.println(urlList.get(x));
		}
	}

	public static ArrayList<String> getUrls() throws IOException {
		// escaped quote for parsing
		char test = '"';
		// holding the list of urls
		ArrayList<String> urlList = new ArrayList();
		// For each page of the nytm, iterate through each line that contains
		// cta inline hiring
		// parse that line to just the url, then save it in the arraylist
		for (int i = 1; i <= 28; i++) {
			//string url
			String urlStr = "https://nytm.org/made?list=true&page=" + i;
			//opsn the connection with properties to get around 403 errors
			URLConnection connection = new URL(urlStr).openConnection();
			connection
					.setRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();

			//open buffer reader with charset UTF-8
			BufferedReader r = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), Charset.forName("UTF-8")));

			String line;
			while ((line = r.readLine()) != null) {
				//for each line, see if it contains cta inline hiring (nytm line for hiring links)
				if (line.contains("cta inline hiring")) {
					//if they do, parse it.
					StringBuilder sbTemp = new StringBuilder();
					sbTemp.append(line);
					// chars from < to the first "
					sbTemp.delete(0, 41);
					StringBuilder sbTemp2 = new StringBuilder();
					char temp;
					int counter = 0;
					// until we get to the char ", make a new string adding each
					// char
					while ((temp = sbTemp.charAt(counter)) != test) {
						sbTemp2.append(temp);
						counter++;
					}
					urlList.add(sbTemp2.toString());
				}
			}
		}

		// adds the date to the file then adds all the urls.
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		PrintWriter out = new PrintWriter("urlList.txt");
		out.println(dateFormat.format(date));
		for (int x = 0; x < urlList.size(); x++) {
			out.println(urlList.get(x));
		}
		out.close();
		return urlList;
	}
}
