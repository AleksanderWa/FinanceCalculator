import io.reactivex.Flowable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class Parser implements IBankOperations {

    private String csvPath;
    private List<AbstractInOutCome> list;

    Parser(String csvPath)
    {
        this.csvPath = csvPath;
        list = new ArrayList<>();
    }

    public List<AbstractInOutCome> getList() {
        return list;
    }

   List<AbstractInOutCome> readCsvFile()
   {

        Flowable<String> readerFile = Flowable.using(
                () -> new BufferedReader(new FileReader(csvPath)),
                reader -> Flowable.fromIterable(() -> reader.lines().iterator()),
                BufferedReader::close
        );
        readerFile
                .skip(1)
                .filter(s -> !s.isEmpty())
                .doOnComplete(() -> System.out.println("done"))

                .map(s -> Arrays.stream(s.split("\""))
                        .filter(s1 -> !s1.startsWith(","))
                        .filter(s1 -> !s1.isEmpty())
                        .collect(Collectors.toList()))

                .map(field -> {
                    switch (field.get(2)) {
                        case CREDIT_CARD:
                        case ATM_CASH_BACK:
                            return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)), Double.parseDouble(field.get(5)), field.get(4), this.parseReceiver(field.get(7)), this.parseDate(field.get(8)), field.get(2), this.parseDescription(field.get(6)));
                        case GET_FROM:
                            return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)), Double.parseDouble(field.get(5)), field.get(4), this.parseSender(field.get(7),field.get(8)), this.parseDate(field.get(0)), field.get(2), field.get(8));
                        case SEND_TO:
                            boolean isLongRow = sendToHelper(field.size()) == LONG_ROW_SEND_TO;
                            if(isLongRow)
                                return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)), Double.parseDouble(field.get(5)), field.get(4), this.parseReceiver(field.get(7), field.get(8)), this.parseDate(field.get(0)), field.get(2), this.parseDescription(field.get(8),field.get(9)));
                            else if(!field.get(7).startsWith(DESCRIPTION))
                                return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)), Double.parseDouble(field.get(5)), field.get(4), this.parseReceiver(field.get(7), field.get(8)), this.parseDate(field.get(0)), field.get(2), this.parseDescription(field.get(8)));
                        default: //phone cash
                            return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)), Double.parseDouble(field.get(5)), field.get(4), this.parseReceiver(field.get(6),field.get(7)), this.parseDate(field.get(0)), field.get(2), field.get(7));
                    }
                })
                .subscribe(abstractInOutCome -> list.add(abstractInOutCome));
                return list;
    }


    @NotNull
    private Receiver parseReceiver(final String rawData)
    {
        List<String> tempList = new ArrayList<>();
        StringBuilder tempLocation = new StringBuilder();
        if(rawData != null && !rawData.isEmpty())
        {
            io.reactivex.Observable<String> stream = io.reactivex.Observable.fromIterable(Arrays.asList(rawData.replace(":","").split(" ")))
                    .filter(s -> !s.isEmpty())
                    .skip(2)
                    .filter(s -> !s.equals(CITY) && !s.equals(ADDRESS) && !s.equals(RECEIVER))
                    .map(s-> {
                        if (tempList.size() <= 1)
                        {
                            tempList.add(s);
                        }
                        else
                        {
                            tempLocation.append(s);
                            tempLocation.append(" ");
                        }
                        return s;
                    });


            stream.subscribe();
            //System.out.println(tempLocation);
            return new Receiver(tempList.get(0),tempList.get(1),tempLocation.toString());
        }

        else
        {
            return new Receiver("","","");
        }
    }

    @NotNull
    private Receiver parseReceiver(final String rawData, final String rawDataAddr)
    {
        if(rawData != null && !rawData.isEmpty())
        {
            if(rawDataAddr.startsWith(RECEIVER_ADDRESS))
            {

                return new Receiver("", rawDataAddr.split(RECEIVER_ADDRESS)[1], rawData.split(RECEIVER)[1]);
            }
            else if(rawData.startsWith(RECEIVER_ADDRESS) || rawData.startsWith(SENDER_ADDRESS))
                return new Receiver("", "", rawData.split(RECEIVER_ADDRESS)[1]);
            else
                return new Receiver("","",rawData.split(RECEIVER)[1]);
        }

        else
        {
            return new Receiver("", "", "");
        }
    }


    @NotNull
    private Receiver parseSender(final String rawData, final String rawDataAddr)
    {
        if(rawData != null && !rawData.isEmpty())
        {
            if(rawData.startsWith(SENDER_ADDRESS) && rawDataAddr.startsWith(SENDER_ADDRESS))
                return new Receiver("", rawData.split(SENDER)[1], rawData.split(SENDER_ADDRESS)[1]);
            else
                return new Receiver("","",rawData.split(SENDER)[1]);
        }

        else
        {
            return new Receiver("", "", "");
        }
    }

    @NotNull
    private Date parseDate(final String rawData) throws ParseException {
        String dateString;
        if(rawData != null && !rawData.isEmpty())
        {
            if (rawData.startsWith(DATE)) {
                dateString = rawData.split(DATE)[1];
            } else
                dateString = rawData.concat(" 00:00:00");
            DateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return ft.parse(dateString);
        }
        else
            {
                dateString = ("0000-00-00 00:00:00");
                DateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                return ft.parse(dateString);
            }
    }

    @NotNull
    private String parseDescription(final String ... rawData)
    {
        String returnData = "";
            if (rawData != null)
            {
                for (String data: rawData)
                {
                    if (data.startsWith(DESCRIPTION))
                        returnData = data.split(DESCRIPTION)[1];
                }
                return returnData;
            } else
                return "";
    }

    private int sendToHelper(final int fields)
    {
        if (fields > 9)
        {
            return LONG_ROW_SEND_TO;
        }
        else
        {
            return SHORT_ROW_SEND_TO;
        }
    }


}