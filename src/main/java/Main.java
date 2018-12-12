import io.reactivex.Observable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static List<String> SUPER_HEROES = Arrays.asList(
            "Superman",
            "Batman",
            "Aquaman",
            "Asterix",
            "Captain America"
    );

    public static void main(String... args) {
        Observable<String> stream = Observable.fromIterable(SUPER_HEROES)
                .filter(s -> {
                    return s.startsWith("A");
                });
        stream.subscribe(
                //name -> System.out.println(name)
        );

        Parser ob = new Parser("C:\\Users\\Aleksander\\Desktop\\csv_october.csv");

        Double value = 0.0;
        Double value1 = 0.0;
        for (AbstractInOutCome ab: ob.readCsvFile())
        {

                System.out.println((ab.getPaymentReceiver().getCity()) + ", " + ab.getPaymentValue() + ", " + ab.getDescription());

            if(ab instanceof Outcome)
                value += ab.getPaymentValue();

            if(ab instanceof Income)
                value1 += ab.getPaymentValue();
        }
        System.out.println("Total money spent: " + value);
        System.out.println("Total money received: " + value1);
        System.out.println("List " + ob.readCsvFile().get(0).getDescription());


    }
}