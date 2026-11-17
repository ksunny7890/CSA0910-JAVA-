public class Q4_ThreadLifeCycleScannerStations {

    static class ScannerStation implements Runnable {
        private final int stationId;
        private final int scripts;

        ScannerStation(int stationId, int scripts) {
            this.stationId = stationId;
            this.scripts = scripts;
        }

        @Override
        public void run() {
            System.out.println("Station " + stationId + " started.");
            try {
                Thread.sleep(500 + stationId * 200L);
                System.out.println("Station " + stationId +
                                   " scanned " + scripts + " scripts.");
            } catch (InterruptedException e) {
                System.out.println("Station " + stationId + " was interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] stations = new Thread[4];

        for (int i = 0; i < stations.length; i++) {
            Runnable task = new ScannerStation(i + 1, 25);
            stations[i] = new Thread(task, "Scanner-" + (i + 1));
            stations[i].start(); // Creates and starts a new thread.
        }

        for (Thread station : stations) {
            System.out.println(station.getName() + " alive: " + station.isAlive());
            station.join(); // Wait for this station to finish.
        }

        System.out.println("All stations completed. Total scripts scanned: 100");
    }
}
