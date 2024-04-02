public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {
        // Пустой конструктор
    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{id=" + this.id +
                ", firstName='" + this.firstName +
                "', lastName='" + this.lastName +
                "', country='" + this.country +
                "', age=" + this.age + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return this.id == employee.id &&
                this.firstName.equals(employee.firstName) &&
                this.lastName.equals(employee.lastName) &&
                this.country.equals(employee.country) &&
                this.age == employee.age;
    }
} //Employee{id=1, firstName='John', lastName='Smith', country='USA', age=25}