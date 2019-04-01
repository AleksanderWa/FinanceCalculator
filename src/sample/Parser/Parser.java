package sample.Parser;

import io.reactivex.Flowable;
import org.jetbrains.annotations.NotNull;
import sample.AppFinance.transaction.AbstractInOutCome;
import sample.AppFinance.BankOperationConsts;
import sample.AppFinance.transaction.InOutComeFactory;
import sample.AppFinance.transaction.Receiver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class Parser {

    private String csvPath;
    private List<AbstractInOutCome> list;

    public Parser(String csvPath)
    {
        this.csvPath = csvPath;
        list = new ArrayList<>();
    }

    public List<AbstractInOutCome> getList() {
        return list;
    }

   public List<AbstractInOutCome> readCsvFile()
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

                .map(s -> Arrays.stream(s.split(",\""))
                        .map(s1 -> s1.replace("\"",""))
                        .filter(s1 -> !s1.startsWith(","))

                        .filter(s1 -> !s1.isEmpty())
                        .collect(Collectors.toList()))

                .map(field -> {
                    switch (field.get(2)) {
                        case BankOperationConsts.CREDIT_CARD:
                        case BankOperationConsts.ATM_CASH_BACK:
                            return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)),
                                    Double.parseDouble(field.get(5)), field.get(4), this.parseReceiver(field.get(7),field.get(6)),
                                    this.parseDate(field.get(8)), field.get(2), this.parseDescription(field.get(6)));

                        case BankOperationConsts.GET_FROM:
                        case BankOperationConsts.GET_FROM_NOW:
                            return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)),
                                    Double.parseDouble(field.get(5)), field.get(4), this.parseSender(field.get(7),
                                            field.get(8), field.get(6)), this.parseDate(field.get(0)), field.get(2), field.get(8));

                        case BankOperationConsts.CONSTANT_PAYMENT:
                        case BankOperationConsts.SEND_TO:
                            boolean isLongRow = sendToHelper(field.size()) == BankOperationConsts.LONG_ROW_SEND_TO;
                            if(isLongRow)
                                return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)),
                                        Double.parseDouble(field.get(5)), field.get(4), this.parseReceiver(field.get(7),
                                                field.get(8), field.get(6)), this.parseDate(field.get(0)), field.get(2),
                                        this.parseDescription(field.get(8),field.get(9)));

                            else if(!field.get(7).startsWith(BankOperationConsts.DESCRIPTION))
                                return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)),
                                        Double.parseDouble(field.get(5)), field.get(4), this.parseReceiver(field.get(7),
                                                field.get(8), field.get(6)), this.parseDate(field.get(0)), field.get(2),
                                        this.parseDescription(field.get(8)));

                        default: //phone cash
                            return InOutComeFactory.getInOutCome(Double.parseDouble(field.get(3)),
                                    Double.parseDouble(field.get(5)), field.get(4), this.parseReceiver(field.get(6),
                                            field.get(7)), this.parseDate(field.get(0)), field.get(2), field.get(7));
                    }
                })
                .subscribe(abstractInOutCome -> list.add(abstractInOutCome));
                return list;
    }



    @NotNull
    private Receiver parseReceiver(final String rawData, final String rawDataAccNmb)
    {
        List<String> tempList = new ArrayList<>();
        StringBuilder tempLocation = new StringBuilder();
        if(rawData != null && !rawData.isEmpty())
        {
            io.reactivex.Observable<String> stream =
                    io.reactivex.Observable.fromIterable(Arrays.asList(rawData.replace(":","").split(" ")))
                    .filter(s -> !s.isEmpty())
                    .skip(2)
                    .filter(s -> !s.equals(BankOperationConsts.CITY) && !s.equals(BankOperationConsts.ADDRESS) &&
                            !s.equals(BankOperationConsts.RECEIVER))
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
            return new Receiver(tempList.get(0),tempList.get(1),tempLocation.toString(), rawDataAccNmb.split(BankOperationConsts.DESCRIPTION)[1]);
        }

        else
        {
            return new Receiver("","","", "");
        }
    }

    @NotNull
    private Receiver parseReceiver(final String rawData, final String rawDataAddr,final String rawDataAccNmb)
    {
        if(rawData != null && !rawData.isEmpty())
        {
            boolean startsWithAddress = rawData.startsWith(BankOperationConsts.RECEIVER_ADDRESS) || rawData.startsWith(BankOperationConsts.SENDER_ADDRESS);
            boolean validBankAcc = rawDataAccNmb.startsWith(BankOperationConsts.BANK_ACC_NUMBER_RECEIVER) || rawDataAccNmb.startsWith(BankOperationConsts.BANK_ACC_NUMBER_SENDER);

            if(rawDataAddr.startsWith(BankOperationConsts.RECEIVER_ADDRESS) && validBankAcc)
            {
                return new Receiver("", rawDataAddr.split(BankOperationConsts.RECEIVER_ADDRESS)[1],
                        rawData.split(BankOperationConsts.RECEIVER)[1], rawDataAccNmb.split(BankOperationConsts.BANK_ACC_NUMBER_RECEIVER)[1]);
            }
            else if(startsWithAddress && validBankAcc)
                return new Receiver("", "", rawData.split(BankOperationConsts.RECEIVER_ADDRESS)[1],rawDataAccNmb.split(BankOperationConsts.BANK_ACC_NUMBER_RECEIVER)[1]);
            else if (validBankAcc)
                return new Receiver("","",rawData.split(BankOperationConsts.RECEIVER)[1], rawDataAccNmb.split(BankOperationConsts.BANK_ACC_NUMBER_RECEIVER)[1]);
            else
                return new Receiver("","",rawData.split(BankOperationConsts.RECEIVER_ADDRESS)[1], "");
        }

        else
        {
            return new Receiver("", "", "", "");
        }
    }


    //TODO add regex for receiver: REGEX FOR CITY: ^.*?\d{3}-\d{2}
    @NotNull
    private Receiver parseSender(final String rawData, final String rawDataAddr, final String rawDataAccNmb)
    {
        String regex = "^.*\\d{2}?-\\d{3}?";
        if(rawData != null && !rawData.isEmpty())
        {
            if(rawData.startsWith(BankOperationConsts.SENDER) && rawDataAddr.startsWith(BankOperationConsts.SENDER_ADDRESS))
                return new Receiver("", rawDataAddr.replace(" ", "").split(regex)[1],
                        rawDataAddr.split(BankOperationConsts.SENDER_ADDRESS)[1], rawDataAccNmb.split(BankOperationConsts.BANK_ACC_NUMBER_SENDER)[1]);
            else
                return new Receiver("",rawData.replace(" ", "").split(regex)[1]
                        ,rawData.split(BankOperationConsts.SENDER)[1], rawDataAccNmb.split(BankOperationConsts.BANK_ACC_NUMBER_SENDER)[1]);
        }

        else
        {
            return new Receiver("", "", "", "");
        }
    }

    @NotNull
    private Date parseDate(final String rawData) throws ParseException {
        String dateString;
        if(rawData != null && !rawData.isEmpty())
        {
            if (rawData.startsWith(BankOperationConsts.DATE)) {
                dateString = rawData.split(BankOperationConsts.DATE)[1];
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
                    if (data.startsWith(BankOperationConsts.DESCRIPTION))
                        returnData = data.split(BankOperationConsts.DESCRIPTION)[1];
                }
                return returnData;
            } else
                return "";
    }

    private int sendToHelper(final int fields)
    {
        if (fields > 9)
        {
            return BankOperationConsts.LONG_ROW_SEND_TO;
        }
        else
        {
            return BankOperationConsts.SHORT_ROW_SEND_TO;
        }
    }

}