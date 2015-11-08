import java.util.List;

public class Tuple implements Comparable<Tuple> {

    private int[] items;

    public Tuple(int... elements) {
        items = elements;
    }

    public Tuple(List<Integer> elements) {
        items = new int[elements.size()];

        for (int i = 0; i < elements.size(); i++) {
            items[i] = elements.get(i);
        }
    }

    public int getLength() {
        return items.length;
    }

    public int get(int index) {
        return items[index];
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tuple)) {
            return false;
        }
        Tuple other = (Tuple) obj;
        return compareTo(other) == 0;
    }

    @Override
    public int compareTo(Tuple other) {
        int otherIdx = 0;
        int thisIdx = 0;
        while (true) {
            if (otherIdx == other.getLength() && thisIdx == this.getLength()) {
                return 0;
            } else if (otherIdx == other.getLength()) {
                // no more element in other
                return 1;
            } else if (thisIdx == this.getLength()) {
                // no more element in this
                return -1;
            } else {
                int diff = items[thisIdx] - other.items[thisIdx];
                if (diff != 0) {
                    return diff;
                }
            }
            thisIdx++;
            otherIdx++;
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            sb.append(items[i]).append(", ");
        }
        return sb.toString();
    }

}
