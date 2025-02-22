import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Human sadra = new Human("sadra", 403104345);
        Human.Foot bigi = sadra.new Foot();
        class myShape extends Shape {
            @Override
            public Double getArea() {
                return 10.0;
            }
        }

        Shape sh = new myShape();
        Runnable program = new Runnable() {
            @Override
            public void run() {
                System.out.println("Test");
            }
        };
        Cat myCat = new Cat();

    }
}