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

        Parser ob = new Parser("C:\\Users\\walkpale\\Downloads\\history_csv_20190130_115116.csv");

        Double value = 0.0;
        Double value1 = 0.0;
        for (AbstractInOutCome ab: ob.readCsvFile())
        {
            System.out.println(ab.getPaymentValue());
                //System.out.println( ab.getPaymentReceiver().getBankAccountNmb() + ", " + (ab.getPaymentReceiver().getCity()) + ", " + ab.getPaymentValue() + ", " + ab.getDescription());

            if(ab instanceof Outcome)
                if(!ab.getPaymentReceiver().getBankAccountNmb().equalsIgnoreCase("39 1020 4795 0000 9002 0400 6672"))
                    value += ab.getPaymentValue();

            if(ab instanceof Income)
                value1 += ab.getPaymentValue();
        }
        System.out.println("Total money spent: " + value);
        System.out.println("Total money received: " + value1);
        System.out.println("Final value " + (value + value1));


    }
}
