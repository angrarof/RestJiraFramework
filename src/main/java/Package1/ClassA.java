package Package1;

public class ClassA{
    public static void main(String[] args) {
        ClassA classA = new ClassA();
        classA.Printers();
    }

    public void Printers(){
        /*EXTENDS PART*/
        /*System.out.println(numberOne);
        System.out.println(numberThree);
        System.out.println(numberFour);

        MethodOne();
        MethodThree();
        MethodFour();*/

        /*INSTANTIATE PART*/
        ClassB classB = new ClassB();
        System.out.println(classB.numberOne);
        System.out.println(classB.numberThree);
        System.out.println(classB.numberFour);

        classB.MethodOne();
        classB.MethodThree();
        classB.MethodFour();
    }
}
