package cs;

import java.util.stream.IntStream;

public class CountingSortService {
    void sort(int[] arr) {
        int n = arr.length;
        int min = IntStream.of(arr).min().getAsInt();
        int max = IntStream.of(arr).max().getAsInt();
        int size = max + 1;
        if (min < 0) {
            size = max - min + 1;
        }
        if (size < n) {
            size = n;
        }

        int[] output = new int[n];

        int[] count = new int[size];

        for (int j : arr) {
            ++count[j];
        }

        for (int i = 1; i < size; ++i) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            --count[arr[i]];
        }

        System.arraycopy(output, 0, arr, 0, n);
    }
}
