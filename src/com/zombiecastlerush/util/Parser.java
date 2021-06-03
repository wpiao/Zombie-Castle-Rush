
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static void main(String[] args) throws Exception {
        new Parser().parse("go North");
    }

    public static ArrayList<String> parse(String input){
        ArrayList<String> actions = new ArrayList<>(Arrays.asList("go", "look", "pick-up", "drop"));
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(input.split(" ")));
        return !actions.contains(commands.get(0)) || commands.size() == 0 ? null : reduceArray(commands);
    }

    public static ArrayList<String> reduceArray(ArrayList<String>  arr){
        List<String> list = new ArrayList<String>(arr);
        list.removeAll(Arrays.asList("", null, " "));  //removes empty and null elements
        list.replaceAll(x -> x.trim());
        return (ArrayList<String>) list;
    }
}