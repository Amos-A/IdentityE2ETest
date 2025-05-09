package testcases.utilities;

import testcases.schemas.Car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UtilityClass {


    //Method that returns a list of lines from the file provided in the path

    public ArrayList<String> readFromTextFile(String path) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }


//Pattern matching method that accepts the text to search and the regular exp as string and returns a list if matches found

    public ArrayList<String> findPattern(String regExp, String txt) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(txt);
        //boolean matchFound = matcher.find();
        ArrayList<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

//Method that returns a list of registrations found in the input file that match the regular expression

    public ArrayList<String> getRegsFromFile() throws IOException {
        String filepath = "src/test/resources/Data/car_input.txt";
        String pattern = "[A-Z]{2}\\d{2}\\s*[A-Z]{3}";
        ArrayList<String> Lines = readFromTextFile(filepath);
        ArrayList<String> regs = new ArrayList<>();

        for(String ln : Lines) {
            regs.addAll(findPattern(pattern, ln));
        }
        System.out.println(regs);
        return regs;
    }


// Method that returns a list of cars found in the output file as array for the object car

    public ArrayList<Car> getCarsFromFile() throws IOException {
        String filepath = "src/test/resources/Data/car_output.txt";
        ArrayList<String> Lines = readFromTextFile(filepath);
        ArrayList<Car> cars = new ArrayList<>();

        for(String ln : Lines) {
            String[] details = ln.split(","); // Split using a comma
            Car c = new Car(details[0], details[1],details[2],details[3]);
            cars.add(c);
        }
        return cars;
    }
}

