package StringSecondAssignments;


public class Part2 {

    public int howMany(String a, String b) {
        int cnt = 0;
        int x = 0;
        while (b.contains(a)) {

            if (b.toLowerCase().startsWith(a.toLowerCase())) {
                cnt++;
            }
            b = b.substring(3);
        }
        return cnt;
    }
}
