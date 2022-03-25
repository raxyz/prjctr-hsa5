package cs;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

class CountingSortServiceTest {
    private final CountingSortService service;

    CountingSortServiceTest() {
        this.service = new CountingSortService();
    }

    private final int amount = 1000;

    @Test
    public void testWithDuplication() {
        int[] arr = new int[amount];

        for (int i = 0; i < amount; i++) {
            arr[i] = new Random().nextInt(0, 10);
        }

        Instant start = Instant.now();
        this.service.sort(arr);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.printf("Array size %d with duplication: %s%n", amount, duration);
    }

    @Test
    public void testRandom() {
        int[] arr = new int[amount];

        for (int i = 0; i < amount; i++) {
            arr[i] = new Random().nextInt(0, amount);
        }

        Instant start = Instant.now();
        this.service.sort(arr);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.printf("Array size %d random: %s%n", amount, duration);
    }

    @Test
    public void testSortedArray() {
        int[] arr = new int[amount];

        for (int i = 0; i < amount; i++) {
            arr[i] = i;
        }

        Instant start = Instant.now();
        this.service.sort(arr);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.printf("Array size %d sorted: %s%n", amount, duration);
    }

    @Test
    public void testReverseArray() {
        int[] arr = new int[amount];

        for (int i = 0; i < amount; i++) {
            arr[i] = amount - i;
        }

        Instant start = Instant.now();
        this.service.sort(arr);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.printf("Array size %d reversed: %s%n", amount, duration);
    }


    @Test
    public void testSmallWithBigNumbersArray() {
        int[] arr = {6, 5, 4, 2, 7, 0, 1, 8, 3, amount};

        Instant start = Instant.now();
        this.service.sort(arr);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.printf("Array size %d small with big numbers: %s%n", arr.length, duration);
    }

    @Test
    public void testSmallWithoutBigNumbersArray() {
        int[] arr = {6, 5, 4, 2, 7, 0, 1, 8, 3, 2};

        Instant start = Instant.now();
        this.service.sort(arr);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.printf("Array size %d small without big numbers: %s%n", arr.length, duration);
    }
}