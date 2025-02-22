public class Human {
    public String name;
    public Integer ID;
    public Double height;

    public Human(String name, Integer ID) {
        this.name = name;
        this.ID = ID;

    }

    class Foot {
        Double height;

        public Foot() {
            this.height = Human.this.height / 2;
        }
    }
    static class Medicine {

    }
}
