public class HW5 {
    static final int SIZE = 1000;
    static final int COUNT = 2;
    static final int PART_SIZE = SIZE / COUNT;

    public static void main(String[] args) {
        float[] arr1 = method1();
        float[] arr2 = method2();

        for (int i = 0; i < SIZE; i++) {
            System.out.println((i + 1) + ": " + arr1[i] + " " + arr2[i]);
		}
    }

    static float[] method1() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения метода 1: " + (end - start) + "мс");
        return arr;
    }

    static float[] method2() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long start = System.currentTimeMillis();

        final float[][] ARR_PARTS = new float[COUNT][PART_SIZE];
        Thread[] t = new Thread[COUNT];
        for (int i = 0; i < COUNT; i++) {
            System.arraycopy(arr, PART_SIZE * i, ARR_PARTS[i], 0, PART_SIZE);
            final int U = i;
            t[i] = new Thread(() -> {
                for (int j = 0; j < PART_SIZE; j++) {
                    ARR_PARTS[U][j] = (float) (ARR_PARTS[U][j] * Math.sin(0.2f + (j + PART_SIZE * U) / 5) * Math.cos(0.2f + (j + PART_SIZE * U) / 5) * Math.cos(0.4f + (j + PART_SIZE * U) / 2));
                }
            });
            t[i].start();
        }
        try {
            for (int i = 0; i < COUNT; i++) {
                t[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < COUNT; i++) {
            System.arraycopy(ARR_PARTS[i], 0, arr, i * PART_SIZE, PART_SIZE);
        }
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения метода 2: " + (end - start) + "мс");
        return arr;
    }

}


