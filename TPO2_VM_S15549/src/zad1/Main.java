/**
 *
 *  @author Volkov Maksym S15549
 *
 */

package zad1;

import javax.swing.JFrame;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		Service s = new Service("Ukraine");
		String weatherJson = s.getWeather("Kyiv");
		Double rate1 = s.getRateFor("USD");
		Double rate2 = s.getNBPRate();

		// ...
		// część uruchamiająca GUI
		// System.out.println(rate22);

		String weather = weatherJson;
		DecimalFormat df = new DecimalFormat("###.###");

		Pattern pat = Pattern.compile("\"temp\":\\d+.\\d+");
		Matcher mat = pat.matcher(weather);
		mat.find();
		weather = mat.group();
		weather = weather.replaceAll("\"temp\":", "");

		double temp = Double.parseDouble(weather) - 273.15;

		weather = weatherJson;
		pat = Pattern.compile("\"humidity\":\\d+");
		mat = pat.matcher(weather);
		mat.find();
		weather = mat.group();
		weather = weather.replaceAll("\"humidity\":", "");

		JFrame frame = new JFrame("TPO2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// final JFXPanel brows = new JFXPanel();
		frame.setSize(736, 604);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		JLabel lblNewLabel = new JLabel("Temperature:");
		lblNewLabel.setFont(new Font("Sylfaen", Font.PLAIN, 32));
		lblNewLabel.setBounds(10, 10, 181, 60);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblWeather = new JLabel("Humidity:");
		lblWeather.setFont(new Font("Sylfaen", Font.PLAIN, 32));
		lblWeather.setBounds(53, 80, 138, 60);
		frame.getContentPane().add(lblWeather);

		JLabel label = new JLabel(df.format(temp) + " C");
		
		if (temp <= 0) {
			label.setForeground(new java.awt.Color(0,50,200));
		}else {
			label.setForeground(new java.awt.Color(255, 102, 51));
		}
		label.setFont(new Font("Sylfaen", Font.PLAIN, 32));
		label.setBounds(201, 10, 113, 60);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel(weather);
		label_1.setForeground(new java.awt.Color(0, 51, 204));
		label_1.setFont(new Font("Sylfaen", Font.PLAIN, 32));
		label_1.setBounds(201, 80, 113, 60);
		frame.getContentPane().add(label_1);

		JLabel lblFrom = new JLabel("1 " + s.getForCountry() + " = ");
		lblFrom.setFont(new Font("Sylfaen", Font.PLAIN, 32));
		lblFrom.setBounds(436, 80, 121, 60);// 334, 80, 171, 60
		frame.getContentPane().add(lblFrom);

		JLabel lblInPln = new JLabel("Rate in PLN:");
		lblInPln.setFont(new Font("Sylfaen", Font.PLAIN, 32));
		lblInPln.setBounds(436, 10, 171, 60);
		frame.getContentPane().add(lblInPln);

		JLabel label_2 = new JLabel(df.format(rate1) + " " + s.getFromCountry());
		label_2.setFont(new Font("Sylfaen", Font.PLAIN, 32));
		label_2.setBounds(556, 80, 151, 60);
		frame.getContentPane().add(label_2);

		JLabel label_3 = new JLabel(df.format(rate2) + "");
		label_3.setFont(new Font("Sylfaen", Font.PLAIN, 32));
		label_3.setBounds(617, 10, 113, 60);
		frame.getContentPane().add(label_3);

		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBounds(10, 189, 710, 376);
		frame.getContentPane().add(jfxPanel);

		Platform.runLater(() -> {
			try {
				WebView webView = new WebView();
				jfxPanel.setScene(new Scene(webView));
				webView.getEngine().load("https://en.wikipedia.org/wiki/" + s.getCity());
			} catch (Exception e) {
			}
		});

		frame.setVisible(true);

	}
}
