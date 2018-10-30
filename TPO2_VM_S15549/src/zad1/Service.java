/**
 *
 *  @author Volkov Maksym S15549
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

	private String shortCountryName;
	private String city;
	private String forCountry;

	public Service(String country) {
		String wrt = null;
		try {

			URL jSonCountryInfo = new URL("https://restcountries.eu/rest/v2/name/" + country);
			URLConnection citeConnection = jSonCountryInfo.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(citeConnection.getInputStream()));
			wrt = br.readLine();
			br.close();

		} catch (IOException e) {

		}
		// {"code"
		Pattern p = Pattern.compile("code\":\"[A-Z]+");
		Matcher m = p.matcher(wrt);
		m.find();

		this.shortCountryName = m.group();

		this.shortCountryName = shortCountryName.replaceAll("code\":\"", "");
		//System.out.println(shortCountryName);

	}

	public String getWeather(String city) {
		String weather = null;
		this.city = city;
		

		try {
			URL jSonWeather = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + ","
					+ shortCountryName + "&appid=d9e6766b607553792bf6911523e2077e");
			URLConnection citeConnection = jSonWeather.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(citeConnection.getInputStream()));
			weather = br.readLine();
			br.close();

		} catch (IOException e) {

		}

		return weather;
	}

	public Double getRateFor(String forCountry) {
		this.forCountry = forCountry;
		String data = null;
		String wrt;
		double nativeCurrency;
		double foreighnCurrency;

		try {
			URL currencyVal = new URL(
					"http://data.fixer.io/api/latest?access_key=e88b9d3d09e730c8c7adb50aaddd1ee3&base=EUR");
			URLConnection citeConnection = currencyVal.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(citeConnection.getInputStream()));
			data = br.readLine();
			br.close();

		} catch (IOException e) {

		}
		Pattern pat = Pattern.compile(forCountry + "\":\\d+.\\d+");
		Matcher mat = pat.matcher(data);
		mat.find();
		wrt = mat.group();
		wrt = wrt.replaceAll(forCountry + "\":", "");
		foreighnCurrency = Double.parseDouble(wrt);

		if (shortCountryName.contains("EUR"))
			return (1 / foreighnCurrency);

		Pattern p = Pattern.compile(shortCountryName + "\":\\d+.\\d+");
		Matcher m = p.matcher(data);
		m.find();
		wrt = m.group();
		wrt = wrt.replaceAll(shortCountryName + "\":", "");
		nativeCurrency = Double.parseDouble(wrt);
		return (nativeCurrency / foreighnCurrency);
	}

	public Double getNBPRate() {
		String dataFromCite = "";

		if (shortCountryName.equals("PLN"))
			return 1.0;

		try {
			URL cite = new URL("http://www.nbp.pl/kursy/kursya.html");
			URLConnection connection = cite.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String wrt;

			while ((wrt = br.readLine()) != null)
				dataFromCite += wrt;

			if (!(dataFromCite.contains(shortCountryName))) {
				//System.out.println("Here");
				cite = new URL("http://www.nbp.pl/kursy/kursyb.html");
				connection = cite.openConnection();
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				wrt = null;
				while ((wrt = br.readLine()) != null)
					dataFromCite +=wrt;

			}
		} catch (IOException e) {

			
		}

		String wrt = null;
		//System.out.println(shortCountryName);
		Pattern pat = Pattern.compile(shortCountryName + "</td>\\s+<td class=\"bgt\\d right\">\\d+,\\d+");
		Matcher mat = pat.matcher(dataFromCite);
		mat.find();
		try {
		wrt = mat.group();
		}catch(Exception e) {
			return 0.0;
		}
		wrt = wrt.replaceAll(shortCountryName + "</td>\\s+<td class=\"bgt\\d right\">", "");

		wrt = wrt.replace(",", ".");
		return Double.parseDouble(wrt);

	}
	public String getCity() {
		return this.city;
	}
	public String getForCountry() {
		return this.forCountry;
	}
	public String getFromCountry() {
		return this.shortCountryName;
	}
}
