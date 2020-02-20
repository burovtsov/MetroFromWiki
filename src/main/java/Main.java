import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        final int HEX_LENGTH = 7;

        try {

            ArrayList<Line> lines = new ArrayList<>();
            //TreeSet<Station> stations = new TreeSet<>();
            //HashMap<Line, Station> stationsOnLines = new HashMap<>();
            List<Station> connections = new ArrayList();

            Document page = Jsoup.connect("https://ru.wikipedia.org/wiki/Список_станций_Московского_метрополитена")
                            .maxBodySize(0).get();

            Elements rowsTable1 = page.select("table[class = standard sortable]").eq(0).select("tr");
            rowsTable1.remove(0); //delete Table1 title
            Elements rowsTable2 = page.select("table[class = standard sortable]").eq(1).select("tr");
            rowsTable2.remove(0); //delete Table2 title
            Elements rowsTable3 = page.select("table[class = standard sortable]").eq(2).select("tr");
            rowsTable3.remove(0); //delete Table3 title

            rowsTable1.stream()
                    .forEach(r-> {

                        String lineNumber = r.select("td").eq(0).select("span").first().text().replaceAll("^0","");
                        Double lineIndex = Double.parseDouble(r.select("td").attr("data-sort-value"));
                        String lineName = r.select("td").eq(0).select("span").attr("title");
                        String lineColor = r.select("td").attr("style").replace("background:", "");
                        String stationName = r.select("td").eq(1).select("a").first().text();

//  System.out.print(r.select("td").eq(3).attr("data-sort-value") + " : "); // connection line number
//  System.out.print(r.select("td").eq(3).select("span").attr("title")); // connection station
                        String connectionLineNumber = r.select("td").eq(3).attr("data-sort-value");
                        String connectionStation = r.select("td").eq(3).select("span").attr("title");

                        if (!connectionLineNumber.equals("Infinity")) {
                           String[] connectionLines = connectionLineNumber.split(".|0");
                            System.out.println(connectionLines. );
                        };

                        if (lineColor.length() < HEX_LENGTH)
                            lineColor = "LINE CLOSED";
                        else if (lineColor.length() > HEX_LENGTH)
                                lineColor = lineColor.substring(70,87);

                        if ((lines.isEmpty()) || (!lines.get(lines.size() - 1).getNumber().equals(lineNumber))) {

                            Line line = new Line(
                                    lineNumber,
                                    lineIndex,
                                    lineName,
                                    lineColor
                            );

                            lines.add(line);
                        }

                        Station station = new Station(
                                lines.get(lines.size() - 1),
                                stationName
                        );

                        lines.get(lines.size() - 1).addStation(station);



                    });

            for (Line l : lines) {
                System.out.println(l.getNumber() + " >> " + l.getName());
                    for (Station s : l.getStations())
                    System.out.println(s.getName());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
