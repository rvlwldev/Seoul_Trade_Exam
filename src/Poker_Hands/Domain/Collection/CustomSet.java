package Poker_Hands.Domain.Collection;

import java.util.ArrayList;

public class CustomSet<E> extends ArrayList<E> {

    public CustomSet() {
        super();
    }

    @Override
    public boolean add(E e) {
        if (!contains(e)) return super.add(e);

        return false;
    }

    public void addAll(E[] arr) {
        for (E e : arr) {
            if (!contains(e)) {
                super.add(e);
            }
        }
    }

}
