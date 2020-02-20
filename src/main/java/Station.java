import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Station {
    Line line;
    String name;
    List<String> connections = new ArrayList();

    public Station (Line line, String name) {
        this.line = line;
        this.name = name;
    }

  //======

    public Line getLine()
    {
        return line;
    }

    public String getName()
    {
        return name;
    }

    public List<String> getConnections() {
        return connections;
    }

    public void setConnections(String[] array) {
        connections = Arrays.asList(array);
    }
}