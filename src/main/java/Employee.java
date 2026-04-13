public class Employee {
    // PROPERTIES

    private int id;
    private String name;
    private double salary;
    private String role;

    // CONSTRUCTOR

    public Employee(int id, String nome, double salary, String role) {

        this.id = id;
        this.name = nome;
        this.salary = salary;
        this.role = role;
    }

    // GETTERS AND SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return name; }
    public void setNome(String nome) { this.name = nome; }

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

}
