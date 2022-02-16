package Package2;

import Package1.ClassB;

public class ClassC extends ClassB{
    public static void main(String[] args) {
        ClassC classC = new ClassC();
        classC.Printers();
    }

    public void Printers(){
        /*EXTENDS PART*/
        //System.out.println(numberOne); --> default modifier only allows print in same package
        //System.out.println(numberThree);
        System.out.println(numberFour);
        //MethodOne(); --> default modifier only allows print in same package
        //MethodThree();
        MethodFour();

        /*INSTANTIATE PART*/
        ClassB classB = new ClassB();
        //System.out.println(classB.numberOne); --> default modifier only allows print in same package
        System.out.println(classB.numberThree);
        //System.out.println(classB.numberFour); --> protected modifier only allows to print if class is extended
        //classB.MethodOne(); --> default modifier only allows print in same package
        classB.MethodThree();
        //classB.MethodFour(); --> protected modifier only allows to print if class is extended
    }
}
