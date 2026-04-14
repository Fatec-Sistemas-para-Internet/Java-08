import java.util.UUID;

public class Employee {
    // PROPERTIES

    private UUID id;
    private String name;
    private double salary;
    private String role;

    // CONSTRUCTOR

    public Employee(UUID id, String nome, double salary, String role) {

        this.id = id;
        this.name = nome;
        this.salary = salary;
        this.role = role;
    }

    // GETTERS AND SETTERS
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String nome) { this.name = nome; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // METHODS

    public void increaseSalary(double percentage){
        this.salary *= percentage;
    }

    public void decreaseSalary(double percentage) throws Exception {
        if(percentage <= 1.00){
            throw new IllegalArgumentException("You can't decrease a salary to less than its value!");
        }

        double decrease = this.salary * percentage;
        this.salary -= decrease;
    }

    public void printEmployee(){
        System.out.println("ID:" + this.getId());
        System.out.println("---");
        System.out.println("NAME: " + this.getName());
        System.out.println("---");
        System.out.println("SALARY: " + this.getSalary());
        System.out.println("---");
        System.out.println("ROLE: " + this.getRole());
        System.out.println("---");
    }

}
