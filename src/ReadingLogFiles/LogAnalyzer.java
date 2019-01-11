package ReadingLogFiles;
/**
 * Write a description of class LogAnalyzer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import edu.duke.*;

public class LogAnalyzer {
    private ArrayList<LogEntry> records;

    public LogAnalyzer() {
        records = new ArrayList<>();

    }

    public void readFile(String filename) {
        FileResource fr = new FileResource(filename);

        for (String line : fr.lines()) {
            LogEntry le = WebLogParser.parseEntry(line);
            records.add(le);
        }
    }

    public void printAllHigherThanNum(int num) {
        ArrayList<Integer> allHigherThanNumList = new ArrayList<>();
        int code;

        for (LogEntry le : records) {

            code = le.getStatusCode();
            if ((allHigherThanNumList.isEmpty() ||
                    !allHigherThanNumList.contains(code)) &&
                    code >= num)
                allHigherThanNumList.add(code);
        }
        System.out.println("Unique status code(es):");
        for (int x : allHigherThanNumList) {
            System.out.println(x);
        }
    }


    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }


    public int countUniqueIPs() {

        ArrayList<String> uniqueIpAdresses = new ArrayList<>();
        String Ip;
        for (LogEntry le : records) {

            Ip = le.getIpAddress();
            if (uniqueIpAdresses.isEmpty() || !uniqueIpAdresses.contains(Ip))
                uniqueIpAdresses.add(Ip);
        }
        return uniqueIpAdresses.size();
    }

    public ArrayList<String> uniqueIPVisitsOnDay(String someday) {

        ArrayList<String> uniqueIPVisitorsOnDay = new ArrayList<>();
        String Ip;
        Date date;

        for (LogEntry le : records) {

            Ip = le.getIpAddress();
            date = le.getAccessTime();
            if (uniqueIPVisitorsOnDay.isEmpty() ||
                    date.toString().contains(someday) && !uniqueIPVisitorsOnDay.contains(Ip)) {
                uniqueIPVisitorsOnDay.add(Ip);
            }
        }

        return uniqueIPVisitorsOnDay;
    }

    public ArrayList<String> ipVisitsOnDay(String someday) {

        ArrayList<String> ipVisitorsOnDay = new ArrayList<>();
        String Ip;
        Date date;

        for (LogEntry le : records) {

            Ip = le.getIpAddress();
            date = le.getAccessTime();
            if (date.toString().contains(someday)) {
                ipVisitorsOnDay.add(Ip);
            }
        }

        return ipVisitorsOnDay;
    }

    public int countUniqueIPslnRange(int low, int high) {
        int cnt = 0;
        int statusCode;
        String ip;
        ArrayList<String> uniqueIPVisitors = new ArrayList<>();


        for (LogEntry le : records) {
            statusCode = le.getStatusCode();
            ip = le.getIpAddress();
            if (!uniqueIPVisitors.contains(ip) &&
                    (statusCode >= low && statusCode <= high)) {
                uniqueIPVisitors.add(ip);
                cnt++;
            }
        }

        return cnt;
    }

    public HashMap<String, Integer> countsVisitsPerIP() {

        HashMap<String, Integer> countsVisitPerIpMap =
                new HashMap<>();
        String ip;

        for (LogEntry le : records) {
            ip = le.getIpAddress();
            if (!countsVisitPerIpMap.containsKey(ip))
                countsVisitPerIpMap.put(ip, 1);
            else
                countsVisitPerIpMap.put(ip, countsVisitPerIpMap.get(ip) + 1);
        }
        return countsVisitPerIpMap;
    }

    public int mostNumberVisitsByIp(HashMap<String, Integer> map) {
        int num = 0;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (num < entry.getValue())
                num = entry.getValue();

        }
        return num;
    }

    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> map) {
        ArrayList<String> arrayList = new ArrayList<>();
        int num = mostNumberVisitsByIp(map);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (num == entry.getValue())
                arrayList.add(entry.getKey());
        }
        return arrayList;
    }

    public HashMap<String, ArrayList<String>> iPsForDays() {

        String date;
        HashMap<String, ArrayList<String>> daysIpVisitsMap =
                new HashMap<>();

        for (LogEntry le : records) {
            date = getMonthName(le.getAccessTime().getMonth()) + " ";
            date = date.concat(
                    Integer.toString(le.getAccessTime().getDate()));

            if (!daysIpVisitsMap.containsKey(date))
                daysIpVisitsMap.put(date, ipVisitsOnDay(date));
        }

        return daysIpVisitsMap;
    }

    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {

        HashMap<String, ArrayList<String>> tempMap = iPsForDays();
        int num = 0;
        String day = "";

        for (Map.Entry<String, ArrayList<String>> entry :
                tempMap.entrySet()) {

            if (num < entry.getValue().size()) {
                num = entry.getValue().size();
                day = entry.getKey();
            }
        }
        return day;
    }

    public ArrayList<String> iPsWithMostVisitsOnDay(String someDay) {
        ArrayList<String> ipPerDate = ipVisitsOnDay(someDay);
        HashMap<String, Integer> tempMap = new HashMap<>();
        int cnt = 0;

        for (String s : ipPerDate) {
            if (!tempMap.containsKey(s))
                tempMap.put(s, 1);
            else {
                tempMap.put(s, tempMap.get(s) + 1);
                if (cnt < tempMap.get(s) + 1)
                    cnt = tempMap.get(s);
            }
        }
        ipPerDate.clear();
        for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
            if (entry.getValue() == cnt)
                ipPerDate.add(entry.getKey());
        }
        return ipPerDate;
    }

    private String getMonthName(int i) {

        String[] arr = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Nov", "Dec"};
        return arr[i];
    }

}
