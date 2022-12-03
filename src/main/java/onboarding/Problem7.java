package onboarding;

import java.util.*;

public class Problem7 {
    private static final int DEFAULT_SCORE = 0, VISITOR_SCORE = 1, FRIENDS_SCORE = 10;

    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {
        Map<String, Integer> users = new HashMap<>();
        calculate(visitors, users, getVisitorsCalculator());
        Calculator friendCalculator = getFriendsCalculator();
        for (List<String> pairedFriends : friends) {
            calculate(pairedFriends, users, friendCalculator);
        }
        List<String> alreadyFriends = findByAlreadyFriends(user, friends);
        removeByAlreadyFriends(users, alreadyFriends);
        return toArrayListWithSort(users);
    }

    public static void calculate(List<String> src, Map<String, Integer> dest, Calculator calculator) {
        for (String user : src) {
            Integer score = dest.get(user);
            if (score == null) {
                score = DEFAULT_SCORE;
            }
            score += calculator.calculate();
            dest.put(user, score);
        }
    }

    public interface Calculator {
        int calculate();
    }

    public static Calculator getVisitorsCalculator() {
        return () -> VISITOR_SCORE;
    }

    public static Calculator getFriendsCalculator() {
        return () -> FRIENDS_SCORE;
    }

    public static void removeByAlreadyFriends(Map<String, Integer> users, List<String> friends) {
        for (String friend : friends) {
            users.remove(friend);
        }
    }

    public static List<String> findByAlreadyFriends(String target, List<List<String>> friends) {
        Set<String> alreadyFriends = new HashSet<>();
        alreadyFriends.add(target);
        for (List<String> users : friends) {
            if (users.contains(target)) {
                String user = users.get(0);
                alreadyFriends.add(user);
            }
        }
        return new ArrayList<>(alreadyFriends);
    }

    public static ArrayList<String> toArrayListWithSort(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(getSortComparator());
        ArrayList<String> sorted = new ArrayList<>();
        entries.forEach(user -> sorted.add(user.getKey()));
        return sorted;
    }

    public static Comparator<Map.Entry<String, Integer>> getSortComparator() {
        return (left, right) -> {
            if (left.getValue().intValue() == right.getValue().intValue()) {
                return left.getKey().compareTo(right.getKey());
            }
            return left.getValue().compareTo((right.getValue()));
        };
    }
}