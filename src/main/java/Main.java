import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        final int HEX_LENGTH = 7;

        try {

            ArrayList<Line> lines = new ArrayList<>();
            //TreeSet<Station> stations = new TreeSet<>();
            //HashMap<Line, Station> stationsOnLines = new HashMap<>();
            //List<Station> connections = new ArrayList();

            Document page = Jsoup.connect("https://ru.wikipedia.org/wiki/Список_станций_Московского_метрополитена")
                            .maxBodySize(0).get();

            Elements rowsTable1 = page.select("table[class = standard sortable]").eq(0).select("tr"); //Cells of Table 1 "Станции Московского метрополитена"
            rowsTable1.remove(0); //delete Table1 title
            Elements rowsTable2 = page.select("table[class = standard sortable]").eq(1).select("tr"); //Cells of Table 2 "Станции Московского монорельса"
            rowsTable2.remove(0); //delete Table2 title
            Elements rowsTable3 = page.select("table[class = standard sortable]").eq(2).select("tr"); //Cells of Table 3 "Платформы Московского центрального кольца"
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
                        //String connectionLineNumber = r.select("td").eq(3).attr("data-sort-value");
                        //String connectionStation = r.select("td").eq(3).select("span").attr("title");

                        String connectionLineNumber = r.select("td").eq(3).select("span").text();
                        //System.out.println("!!!!!!!! " + connectionLineNumber);





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



                        if (!connectionLineNumber.equals("Infinity")) {
                            //String[] connectionLines = connectionLineNumber.split("\\.|0");
                            String[] connectionLines = connectionLineNumber.split("\\s+");
                            String connectionStation = r.select("td").eq(3).select("span").attr("title").replace("Переход на станцию ","").replaceAll("[а-яА-Я-]+ линии", "");

                          // Arrays.stream(connectionLines).forEach(c -> c.replaceAll("^0", ""));

                            for (String s : connectionLines)
                                System.out.print(s + " " + connectionStation + " ");
                            System.out.println();

                            station.setConnections(connectionLines);
                        }




                        lines.get(lines.size() - 1).addStation(station);



                    });

            for (Line l : lines) {
                System.out.println(l.getNumber() + " >> " + l.getName());
                    for (Station s : l.getStations()) {
                        System.out.println(s.getName());
                            for (String st : s.getConnections())
                            System.out.print(st + " ");
                        System.out.println();
                    }
            }









        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
