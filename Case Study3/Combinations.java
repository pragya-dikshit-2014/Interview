
package case1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Combinations {
	public static void main(String[] args) {

		List<List<Integer>> powerSet = new LinkedList<List<Integer>>();
		int arr[] = {1,2,3, 4, 5};
		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int t : arr)
			al.add(t);
		for (int i = 1; i <= arr.length; i++)
			powerSet.addAll(combination(al, i));

		for(List<Integer> l : powerSet) {
			for(Integer ir : l) {
				System.out.print(ir+" ");
			}
			System.out.println();
		}
	}
  
  

	public static List<List<Integer>> combination(List<Integer> values, int size) {

		if (0 == size) {
			return Collections.singletonList(Collections.<Integer> emptyList());
		}

		if (values.isEmpty()) {
			return Collections.emptyList();
		}

		List<List<Integer>> combination = new LinkedList<List<Integer>>();

		Integer actual = values.iterator().next();

		List<Integer> subSet = new LinkedList<Integer>(values);
		subSet.remove(actual);

		List<List<Integer>> subSetCombination = combination(subSet, size - 1);

		for (List<Integer> set : subSetCombination) {
			List<Integer> newSet = new LinkedList<Integer>(set);
			newSet.add(0, actual);
			combination.add(newSet);
		}

		combination.addAll(combination(subSet, size));

		return combination;
	}
}
