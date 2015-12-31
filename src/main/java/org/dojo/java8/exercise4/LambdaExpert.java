package org.dojo.java8.exercise4;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.dojo.java8.model.Child;
import org.dojo.java8.model.Person;
import org.dojo.java8.model.PersonLibrary;

public class LambdaExpert {

    private List<Person> persons = PersonLibrary
            .getPersons("/person_data_with_children.json");

    // TODO use map, flatMap, sorted and limit
    public List<Child> firstTwoYoungerChildsByName() {
        // List<Child> allChildren = new ArrayList<>();
        //
        // for (Person person : persons) {
        // allChildren.addAll(person.getChildren());
        // }
        //
        // Collections.sort(allChildren, new ChildComparator());
        //
        // return allChildren.subList(0, 2);

        return persons
                .stream()
                .map(Person::getChildren)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Child::getName).thenComparing(
                        Child::getAge)).limit(2).collect(Collectors.toList());

    }

    private class ChildComparator implements Comparator<Child> {
        @Override
        public int compare(Child c1, Child c2) {
            int nameComparaison = c1.getName().compareTo(c2.getName());
            if (nameComparaison == 0) {
                return Integer.compare(c1.getAge(), c2.getAge());
            } else {
                return nameComparaison;
            }
        }
    }

    // TODO NOTE! DO NOT USE STREAMS
    // TODO refactor Map computation with Map merge method and you can
    // TODO eventually change loop by Iterator forEach method
    // TODO use static reference to the Integer sum method
    public Map<String, Long> countWord(List<String> words) {

        // Map<String, Long> count = new HashMap<>();
        //
        // for (String word : words) {
        //
        // Long countOfCurrentWord = count.get(word);
        // if (countOfCurrentWord == null) {
        // countOfCurrentWord = 0L;
        // }
        //
        // count.put(word, countOfCurrentWord + 1L);
        // }
        //
        // return count;

        Map<String, Long> count = new HashMap<>();
        words.forEach(word -> count.merge(word, 1L, Long::sum));
        return count;
    }

    // TODO NOTE! USE STREAMS
    // TODO use Collectors.groupingBy with Collectors.counting in second
    // argument
    public Map<String, Long> countWordStream(List<String> words) {

        // Map<String, Long> count = new HashMap<>();
        //
        // for (String word : words) {
        //
        // Long countOfCurrentWord = count.get(word);
        // if (countOfCurrentWord == null) {
        // countOfCurrentWord = 0L;
        // }
        //
        // count.put(word, countOfCurrentWord + 1L);
        // }
        //
        // return count;

        return words.stream().collect(
                Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));
    }

    // TODO use Collectors.collectingAndThen, Collectors.groupingBy with
    // Collectors.counting in second argument
    // TODO to complete the refactor use max map and orElse
    public int retrieveMostCommonAge() {
//        Map<Integer, Integer> numberPeopleByAge = new HashMap<>();
//        Integer personsByAge;
//        for (Person person : persons) {
//
//            personsByAge = numberPeopleByAge.get(person.getAge());
//            if (personsByAge == null) {
//                personsByAge = 0;
//                numberPeopleByAge.put(person.getAge(), personsByAge);
//            }
//            personsByAge++;
//
//            numberPeopleByAge.put(person.getAge(), personsByAge);
//
//        }
//
//        Map.Entry<Integer, Integer> maxEntry = null;
//
//        for (Map.Entry<Integer, Integer> entry : numberPeopleByAge.entrySet()) {
//            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
//                maxEntry = entry;
//            }
//
//        }
//
//        return maxEntry.getKey();
        
        return persons.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Person::getAge, Collectors.counting()),
                        Map::entrySet))
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(0);
    }

}
