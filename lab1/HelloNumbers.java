public class HelloNumbers {
    public static void main(String[] args) {
        int summation = 0;
        for (String num : args) {
            summation += Integer.parseInt(num);
        }
        System.out.println(summation);
    }
}