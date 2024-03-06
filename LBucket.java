import java.util.Scanner;

public class LBucket {
    static int random(int a) {
        return (int) (Math.random() * a);
    }

    public static void main(String[] args) throws InterruptedException {
        int[] packetSize = new int[5];

        // Generating random packet sizes
        for (int i = 0; i < 5; i++) {
            packetSize[i] = random(10);
            System.out.println("PacketSize[" + i + "] : " + packetSize[i]);
        }

        // Taking input for output rate and bucket size
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter output rate : ");
        int outputRate = scanner.nextInt();
        System.out.print("Enter bucket size : ");
        int bucketSize = scanner.nextInt();
        scanner.close();

        int p_zsz_rm = 0; // Variable to track bytes remaining in the bucket

        // Processing each packet
        for (int i = 0; i < 5; i++) {
            if (packetSize[i] + p_zsz_rm > bucketSize) { // If adding new packet exceeds bucket size
                if (packetSize[i] > bucketSize) {
                    System.out.println("Incoming packet " + i + " of size " + packetSize[i] +
                            " is greater than bucket capacity : PACKET REJECTION");
                } else {
                    System.out.println("Packet " + i + ": Bucket capacity exceeded : REJECTING new packet");
                }
            } else {
                // Adding packet size to bytes remaining
                p_zsz_rm += packetSize[i];
                System.out.println("Incoming packet " + i + " of size : " + packetSize[i]);
                System.out.println("Bytes remaining for transmission : " + p_zsz_rm);

                int p_time = random(6) * 10; // Random transmission time
                System.out.println("Time left for transmission is " + p_time);

                // Simulating transmission time
                for (int clk = 10; clk <= p_time; clk += 10) {
                    Thread.sleep(1); // Simulating time
                    if (p_zsz_rm > 0) {
                        if (p_zsz_rm <= outputRate) {
                            System.out.println("Packet of size " + p_zsz_rm + " transmitted");
                            p_zsz_rm = 0; // Packet transmitted, reset bytes remaining
                        } else {
                            System.out.println("Packet of size " + outputRate + " transmitted");
                            p_zsz_rm -= outputRate; // Reduce bytes remaining by output rate
                            System.out.println("Bytes remaining after transmission " + p_zsz_rm);
                            System.out.println("Time left : " + (p_time - clk));
                        }
                    } else {
                        System.out.println("No packets to transmit");
                    }
                }
            }
        }
    }
}
