package algorithm.util;

import java.util.List;

public class RandomPopper<T> {

    private List<T> list;
    private int[] indexes;
    private int remaining;

    /*
    * Using ArrayList is recommended since get() costs O(1)
    *
    * array to ArrayList:
    * new ArrayList<Element>(Arrays.asList(array))
    * */
    public RandomPopper(List<T> list) {
        if (list == null || list.size() <= 0) throw new IllegalArgumentException("illegal list");
        this.list = list;
        indexes = new int[list.size()];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        remaining = list.size();
    }

    public T randomPop() {
        if (isEmpty()) return null;
        int selected = (int) (RandomUtils.random() * remaining);
        T ans = list.get(indexes[selected]);
        swap(indexes, selected, remaining - 1);
        remaining--;
        return ans;
    }
    
    public T randomPeek() {
        if (isEmpty()) return null;
        int selected = (int) (RandomUtils.random() * remaining);
        T ans = list.get(indexes[selected]);
        return ans;
    }

    public boolean isEmpty() {
        return remaining <= 0;
    }

    private static void swap(int[] vec, int a, int b) {
        int aux = vec[a];
        vec[a] = vec[b];
        vec[b] = aux;
    }
}