import java.util.*;

public class RoundRobinLab {

    static class Process {
        int id;
        int arrivalTime;
        int burstTime;
        int remainingTime;
        int completionTime;
        int turnaroundTime;
        int waitingTime;

        public Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    public static void scheduleRoundRobin(List<Process> processes, int timeQuantum) {
        int currentTime = 0;

        ArrayList<Process> readyQueue = new ArrayList<Process>(processes); // Ready queue with all the processes

        while (!readyQueue.isEmpty()) // If the queue is not empty
        {
            Process currentProcess = readyQueue.removeFirst();
            int execTime = Math.min(timeQuantum, currentProcess.remainingTime); // Calculates execution time from the min of the quantum and remaining time

            currentTime += execTime;
            currentProcess.remainingTime -= execTime;

            if (currentProcess.remainingTime > 0) // If the remaining time is greater than 0, it adds the process back to the readyQueue
            {
                readyQueue.add(currentProcess);
            }

            else // If it is not greater than 0, then the remaining time has to be zero, meaning it has completed.
            {
                currentProcess.completionTime = currentTime;
            }
        }
        
        for (Process process : processes) // Calculates turnaround and waiting times
        {
            process.turnaroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;
        }

    }

    /**
     * Calculate and display metrics (FULLY PROVIDED)
     */
    public static void calculateMetrics(List<Process> processes, int timeQuantum) {
        System.out.println("========================================");
        System.out.println("Round Robin Scheduling Simulator");
        System.out.println("========================================\n");
        System.out.println("Time Quantum: " + timeQuantum + "ms");
        System.out.println("----------------------------------------");
        System.out.println("Process | Arrival | Burst | Completion | Turnaround | Waiting");

        double totalTurnaround = 0;
        double totalWaiting = 0;

        for (Process p : processes) {
            System.out.printf("   %d    |    %d    |   %d   |     %d     |     %d     |    %d\n",
                    p.id, p.arrivalTime, p.burstTime, p.completionTime,
                    p.turnaroundTime, p.waitingTime);
            totalTurnaround += p.turnaroundTime;
            totalWaiting += p.waitingTime;
        }

        System.out.println();
        System.out.printf("Average Turnaround Time: %.2fms\n", totalTurnaround / processes.size());
        System.out.printf("Average Waiting Time: %.2fms\n", totalWaiting / processes.size());
        System.out.println("========================================\n\n");
    }

    /**
     * Main method (FULLY PROVIDED)
     */
    public static void main(String[] args) {
        List<Process> processes1 = new ArrayList<>();
        processes1.add(new Process(1, 0, 7));
        processes1.add(new Process(2, 0, 4));
        processes1.add(new Process(3, 0, 2));

        scheduleRoundRobin(processes1, 3);
        calculateMetrics(processes1, 3);

        List<Process> processes2 = new ArrayList<>();
        processes2.add(new Process(1, 0, 7));
        processes2.add(new Process(2, 0, 4));
        processes2.add(new Process(3, 0, 2));

        scheduleRoundRobin(processes2, 5);
        calculateMetrics(processes2, 5);
    }
}