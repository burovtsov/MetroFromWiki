public class Station {
    Line line;
    String name;

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
}