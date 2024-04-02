import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json, "data.json");

        List<Employee> listXML = parseXML("data.xml");
        String xmlToJson = listToJson(listXML);
        writeString(xmlToJson, "data2.json");

        String json2 = readString("new_data.json");
        List<Employee> jsonList = jsonToList(json2);
        System.out.println(jsonList);
    }

    public static List<Employee> jsonToList(String json) {
        List<Employee> list = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(json);
            JSONArray employees = (JSONArray) obj;
            for (Object iter : employees) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Employee employee = gson.fromJson(iter.toString(), Employee.class);
                list.add(employee);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String readString(String fileName) {
        StringBuilder builder = new StringBuilder();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fileName));
            JSONArray employees = (JSONArray) obj;

            builder.append("[" + employees.get(0) + ",");
            for (int i = 1; i < employees.size() - 1; i++) {
                builder.append(employees.get(i) + ",");
            }
            builder.append(employees.get(employees.size() - 1) + "]");
        } catch (IOException |  ParseException e) {
            e.printStackTrace();
        }
        return new String(builder);
    }

    public static List<Employee> parseXML(String fileName) {
        List<Employee> data = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));

            NodeList list = document.getElementsByTagName("employee");
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                Employee employee = new Employee(
                        Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()),
                        element.getElementsByTagName("firstName").item(0).getTextContent(),
                        element.getElementsByTagName("lastName").item(0).getTextContent(),
                        element.getElementsByTagName("country").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent())
                );
                data.add(employee);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return data;
    }

    public static void writeString(String json, String fileName) {
        try(FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(json);
            writer.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(list, listType);
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> data = null;
        try(CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = 
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping[0],
                    columnMapping[1],
                    columnMapping[2],
                    columnMapping[3],
                    columnMapping[4]
            );
            
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            
            data = csv.parse();
            
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            return data;
        }
    }
}
