package bst;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class BinarySearchTreeTest {

    void testBalancedTree(int amount) {
        BinarySearchTree tree = new BinarySearchTree();
        for (int i = 0; i < amount; i++) {
            tree.insert(i);
        }
        tree.balanceTree();

        Instant startInsert = Instant.now();
        tree.insert(amount + 1);
        Instant endInsert = Instant.now();
        Duration insertDuration = Duration.between(startInsert, endInsert);

        Instant startSearch = Instant.now();
        tree.search(amount / 2);
        Instant endSearch = Instant.now();
        Duration searchDuration = Duration.between(startSearch, endSearch);

        Instant startDelete = Instant.now();
        tree.delete(amount / 2);
        Instant endDelete = Instant.now();
        Duration deleteDuration = Duration.between(startDelete, endDelete);

        System.out.printf("Create balanced tree with %d elements", amount);
        System.out.printf("\t Insert %s", insertDuration);
        System.out.printf("\t Search %s", searchDuration);
        System.out.printf("\t Delete %s%n", deleteDuration);
    }

    void testLeftTree(int amount) {
        BinarySearchTree tree = new BinarySearchTree();
        for (int i = 0; i < amount; i++) {
            tree.insert(i);
        }

        Instant startInsert = Instant.now();
        tree.insert(amount + 1);
        Instant endInsert = Instant.now();
        Duration insertDuration = Duration.between(startInsert, endInsert);

        Instant startSearch = Instant.now();
        tree.search(amount / 2);
        Instant endSearch = Instant.now();
        Duration searchDuration = Duration.between(startSearch, endSearch);

        Instant startDelete = Instant.now();
        tree.delete(amount / 2);
        Instant endDelete = Instant.now();
        Duration deleteDuration = Duration.between(startDelete, endDelete);

        System.out.printf("Create left tree with %d elements", amount);
        System.out.printf("\t Insert %s", insertDuration);
        System.out.printf("\t Search %s", searchDuration);
        System.out.printf("\t Delete %s%n", deleteDuration);
    }

    void testRightTree(int amount) {
        BinarySearchTree tree = new BinarySearchTree();
        for (int i = amount; i > 0; i--) {
            tree.insert(i);
        }

        Instant startInsert = Instant.now();
        tree.insert(amount + 1);
        Instant endInsert = Instant.now();
        Duration insertDuration = Duration.between(startInsert, endInsert);

        Instant startSearch = Instant.now();
        tree.search(amount / 2);
        Instant endSearch = Instant.now();
        Duration searchDuration = Duration.between(startSearch, endSearch);

        Instant startDelete = Instant.now();
        tree.delete(amount / 2);
        Instant endDelete = Instant.now();
        Duration deleteDuration = Duration.between(startDelete, endDelete);

        System.out.printf("Create right tree with %d elements", amount);
        System.out.printf("\t Insert %s", insertDuration);
        System.out.printf("\t Search %s", searchDuration);
        System.out.printf("\t Delete %s%n", deleteDuration);
    }

    void testRandomTree(int amount) {
        BinarySearchTree tree = new BinarySearchTree();
        for (int i = 0; i < amount; i++) {
            tree.insert(new Random().nextInt(amount));
        }

        Instant startInsert = Instant.now();
        tree.insert(amount + 1);
        Instant endInsert = Instant.now();
        Duration insertDuration = Duration.between(startInsert, endInsert);

        Instant startSearch = Instant.now();
        tree.search(amount / 2);
        Instant endSearch = Instant.now();
        Duration searchDuration = Duration.between(startSearch, endSearch);

        Instant startDelete = Instant.now();
        tree.delete(amount / 2);
        Instant endDelete = Instant.now();
        Duration deleteDuration = Duration.between(startDelete, endDelete);

        System.out.printf("Create Random tree with %d elements", amount);
        System.out.printf("\t Insert %s", insertDuration);
        System.out.printf("\t Search %s", searchDuration);
        System.out.printf("\t Delete %s%n", deleteDuration);
    }

    @Test
    void test() {
        testBalancedTree(50);
        testBalancedTree(2500);
        testBalancedTree(5000);

        testLeftTree(50);
        testLeftTree(2500);
        testLeftTree(5000);
        testLeftTree(10000);

        testRightTree(50);
        testRightTree(2500);
        testRightTree(5000);
        testRightTree(10000);

        testRandomTree(50);
        testRandomTree(2500);
        testRandomTree(5000);
        testRandomTree(10000);
    }
}
