import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WeatherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = getCityWeather(request.getParameter("city"));
        response.setCharacterEncoding("Windows-1251");
        request.setAttribute("result", result);
        PrintWriter printWriter =response.getWriter();
        printWriter.println("<html>\n" +
                "<head>\n" +
                "    <title>Weather in your city</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method=\"get\" action=\"/weather\">\n" +
                "    <label>Город:</label>\n" +
                "    <input type=\"text\" name=\"city\">\n" +
                "    <input type=\"submit\" value=\"Поиск\"><br>\n" +
                "</form>\n" +
                result +
                "\n" +
                "</body>\n" +
                "</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private String getURLContent(String urlAddress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Город не найден");
        }

        return content.toString();
    }
    public String getCityWeather(String city) {
        String weatherText;
        String output = getURLContent("https://api.openweathermap.org/data/2.5/weather?q="
                + city
                + "&appid=" + getServletContext().getInitParameter("API") + "&units=metric");
        if (!output.isEmpty()) {
            JSONObject object = new JSONObject(output);
            weatherText = "Погода в городе " + city + ":"
                    + "\n\nТемпература: " + object.getJSONObject("main").getDouble("temp")
                    + "\nОщущается: " + object.getJSONObject("main").getDouble("feels_like")
                    + "\nВлажность: " + object.getJSONObject("main").getDouble("humidity")
                    + "\nДавление: " + object.getJSONObject("main").getDouble("pressure");
        }
        else {
            weatherText="Не знаю я такой город";
        }
        return weatherText;
    }
}
