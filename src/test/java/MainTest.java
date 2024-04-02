import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.hamcrest.MatcherAssert;

import java.util.ArrayList;
import java.util.List;

class MainTest {

    @org.junit.jupiter.api.Test
    public void testListToJson_success() {
        // given:
        final List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee(1, "John", "Smith", "USA", 25));
        employees.add(new Employee(2, "Inav", "Petrov", "RU", 23));
        final String expected = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";

        // when:
        final String json = Main.listToJson(employees);

        // then:
        Assertions.assertEquals(json, expected);
    }

    @org.junit.jupiter.api.Test
    public void hamCrestTestListToJson_success() {
        final List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee(1, "John", "Smith", "USA", 25));
        employees.add(new Employee(2, "Inav", "Petrov", "RU", 23));
       final String json = Main.listToJson(employees);

        MatcherAssert.assertThat(json, Matchers.equalTo("[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]"));
    }

    @org.junit.jupiter.api.Test
    public void testParseCSV_success() {
        // given:
        final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        final String fileName = "data.csv";
        final List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee(1, "John", "Smith", "USA", 25));
        employees.add(new Employee(2, "Inav", "Petrov", "RU", 23));
        // when:
        List<Employee> parseCSVList = Main.parseCSV(columnMapping, fileName);

        // then:
        Assertions.assertEquals(parseCSVList, employees);
    }

    @org.junit.jupiter.api.Test
    public void hamcrestTestParseCSV_success() {
        final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        final String fileName = "data.csv";

        MatcherAssert.assertThat(Main.parseCSV(columnMapping, fileName), Matchers.contains(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Inav", "Petrov", "RU", 23)
        ));
    }

    @org.junit.jupiter.api.Test
    public void testParseCSV_emptyMapping() {
        // given:
        final String[] columnMapping = {};
        final String fileName = "data.csv";

        // when:
        List<Employee> parseCSVList = Main.parseCSV(columnMapping, fileName);

        // then:
        Assertions.assertEquals(parseCSVList, null);
    }

    @org.junit.jupiter.api.Test
    public void HamcrestTestParseCSV_emptyMapping() {
        final String[] columnMapping = {};
        final String fileName = "data.csv";

        MatcherAssert.assertThat(Main.parseCSV(columnMapping, fileName), Matchers.equalTo(null));
    }
}