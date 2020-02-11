import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line> {
    private String number;
    private String name;
    private String color;
    private Double index;
    private List<Station> stations;

    public Line(String number, Double index, String name, String color)
    {
        this.number = number;
        this.index = index;
        this.name = name;
        this.color =color;
        //color = Color.decode(clr);
        stations = new ArrayList<>();
    }

    //=====

    public String getNumber()
    {
        return number;
    }

    public Double getIndex() {
        return index;
    }

    public String getName()
    {
        return name;
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public List<Station> getStations()
    {
        return stations;
    }

    @Override
    public int compareTo(Line l) {
        return index.compareTo(l.getIndex());
        //return Double.compare(Double.parseDouble(number), Double.parseDouble(l.getNumber()));
    }


}
