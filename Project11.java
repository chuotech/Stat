import java.util.*;
public class Project11 { //create class to store virus status
	public boolean infected; //true if currently infected
	public boolean infectedOnce; //true if was infected at least once
	public Project11(boolean virus, boolean virusOnce) { //constructor
		infected = virus;
		infectedOnce = virusOnce;
	}
	public boolean getVirus() { //method to get current status of infected or not
		return infected;
	}
	public void setVirus(boolean x) { //method to change status of infected to true or false
		infected = x;
	}
	public void wasInfected() { //method to change status of if the computer was infected at least once
		infectedOnce = true;
	}
	public boolean wasInfectedStatus() { //method to get status of if the computer was infected at least once
		return infectedOnce;
	}
	public static void main(String[] args) {
	    Scanner scan = new Scanner(System.in);
	    //scan user inputs to get number of computers, probability of infection, and number of computers repaired daily
	    System.out.println("Enter number of Computers: ");
	    int computerAmount = scan.nextInt();
	    System.out.println("Enter probability of Spreading Virus: ");
	    double probVirus = scan.nextDouble();
	    System.out.println("Enter number of Computers Repaired Daily: ");
	    int computerRepaired = scan.nextInt();
	    System.out.println("Enter number of trials: ");
	    int numTrials = scan.nextInt();
	    scan.close();
	    int[] numDays = new int[numTrials];
	    //declare arrays for number of days, if all computers were infected, and amount of computers infected
	    double[] probAllInfected = new double[numTrials];
	    double[] numInfectedTotal = new double[numTrials];
	    Random randomGenerator = new Random(); //declare random for random number generation
	    ArrayList<Project11> computers = new ArrayList<Project11>(); //arraylist that all computer objects will be stored in.
	    for(int i = 0; i < numTrials; i++) { //start of trials
	    	int days = 1;
	    	int numInfected = 1;
	    	for(int count = 0; count < computerAmount; count++) { //for loop to add computers into computers arraylist
	 	    	if(count == 0)
	 	    		computers.add(new Project11(true,true)); //if statement to set one computer to be infected
	 	    	else 
	 	    		computers.add(new Project11(false,false)); //rest of iteration just add healthy computers
	 	    }
	    	while(numInfected > 0) { //start of main simulation
	    		int addition = 0; //declare variable of amount of computers infected
	    		for(int j = 0; j < computerAmount; j++) { //for loop to infected computers
	    			if(computers.get(j).getVirus() == false) { //skips iteration if the computer is already infected
	    				if(randomGenerator.nextDouble() < probVirus) { //if infected, sets computer status to infected, set status to was infected at least once, and adds 1 to addition
	    					computers.get(j).setVirus(true);
	    					computers.get(j).wasInfected();
	    					addition++;
	    				}
	    			}
	    		}
	    		numInfected += addition; //adds addition variable to current amount of computers currently infected
	    		double numToRemove = Math.min(numInfected, computerRepaired); //declare variable to the smaller of number currently infected and amount of computers repaired daily
	    		ArrayList<Integer> infected = new ArrayList<Integer>(); //declare arraylist containing indexes of computers currently infected
	    		for (int j = 0; j < computerAmount; j++) { //for loop adds to infected arraylist if computer status is currently infected
	    			if(computers.get(j).getVirus())
	    				infected.add(j);
	    		}
	    		for(int j = 0; j < numToRemove; j++) { //for loop to randomly pick indexes in infected arraylist to cure infection
	    			Collections.shuffle(infected);
	    			computers.get(infected.get(0)).setVirus(false);
	    			infected.remove(0);
	    			numInfected--;
	    		}
	    		days++; //add 1 to days
	    		numInfectedTotal[i] += addition; //current addition variable to numInfectedTotal array
	    		infected.clear(); //empty arraylist infected
	    	}
	    	numDays[i] = days; //for trial i, set current days to current trial for array numDays
	    	int wasInfectedCount = 0; //declare integer to count number of computers that was ever infected
	    	for(int k = 0; k < computerAmount; k++) { //for loop to count number of computers that were ever infected
	    		if(computers.get(k).wasInfectedStatus() == true)
	    			wasInfectedCount++;
	    	}
	    	if(wasInfectedCount % computerAmount == 0) // if else statements to see if all computers were infected at least once or not
	    		probAllInfected[i] = 1; //for trial i, set current trial for probability of all computers being infected at least once to 1
	    	else
	    		probAllInfected[i] = 0; //for trial i, set current trial for probability of all computers being infected at least once to 0
	        computers.clear(); //clear arraylist computers to refresh for next trial
	    }
	    //gets average for each array
	    double averageRemoveTime = Arrays.stream(numDays).average().orElse(0);
	    double averageProbInfected = Arrays.stream(probAllInfected).average().orElse(0);
	    double averageNumberInfected = Arrays.stream(numInfectedTotal).average().orElse(0);
	    //prints results
	    System.out.println("Number of trials: " + numTrials);
	    System.out.println("Average time to remove all viruses from population: " + averageRemoveTime + " days");
	    System.out.println("Average probability of each computer getting infected at least once: " + averageProbInfected);
	    System.out.println("Average expected number of computers infected: " + averageNumberInfected);
	}
}
