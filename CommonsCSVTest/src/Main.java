package CommonsCSVTest.src;

import java.util.Scanner;

/**
 * Created by Nissan on 11/21/2017.
 */
public class Main {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        System.out.println("enter 0,1");
        int inputChoice = Integer.parseInt(stdin.nextLine());
        Filter filter = new Filter(inputChoice);
        if (filter.setFilter(stdin.nextLine()) )
            System.out.println("succes");
        else
            System.out.println("failure");

    }
}
