team35
======

## SimCity201 Project Repository for CS 201 students

### Some Basic Scenarios to Demonstrate Functionality for Grading
NOTES: 
- These scenarios will all be very simple, since they are meant to demonstrate particular
functionalities of SimCity - one at a time. It'd be quite difficult to see through the print
statements what was going on if many scenarios were running at once, so these grading aids
make it easier by doing one scenario at a time. To see lots of things happening at once (at 
the cost of clarity) call the last scenario (#5).
- Most scenarios will end in the person sleeping.

#### 1. Buses
Start SimCity, and create a new person. Enter the name as "Bus Ride". This will add the
bus system to the city, and will create a single person name "Rider". Rider will choose to
Rider will choose to go to the bus stop, ride the bus, and go home. In this particular case,
riding the bus is out of the way for Rider, but this is just because we are simply trying to
demonstrate the bus system's functionality.

#### 2. Bank
Start SimCity and open the Person Creation panel. Enter the name as "Bank". This will create several
people and will give them all the job of Bank Teller, but with different shifts. It will
add a bank to the city. When everything is created, all new people will go to the bank,
one to start his shift, and the others to do business. To see inside the bank, click on the
bank in the main city view. As you'll see, soon after the scenario starts, the first teller
will get off work and help the others with their remaining business, and the same will happen
for up to the last teller as well. The banking can be seen in the animation or more specifically in
the print statements. Afterwards, they all go home to sleep.

#### 3. Market
Start SimCity and open the Person Creation panel. Enter the name as "Market". This will create
a market and people, who will staff and buy from the market. 3 people will 
work during the shift, and the others will do business with the market staffers. To see
the animation, click on the market in the city view. Also, see the print statements, which 
detail the market purchases. The people will go hom after they have finished all business. 

#### 4. Busy 
Start SimCity, and open the Person Creation panel. Enter the name as "Busy". This will create
several buildings and enough people to staff them. The buses will also be added. Some people will
go to work at the buildings, some will go to the bank to do business, others will board the 
bus. Click on any building to see what's inside. People will go home to sleep as the default
behavior.

### Individual Contributions

##### Bobby Groom

+ Worked on bank animation.
+ Helped design bank.
+ The GUIs.(bank, btr, bcr).
+ Integrating the building GUIs with the SimCity zoom panel.
+ Connecting the bank animations to the personAgent.

##### Gabriel Mel de Fontenay

+ Co-designed the bank (with rgroom)
+ Fully coded and unit tested the non-animation code of the bank
+ Co-designed the bus system (pseags)
+ Coded and unit tested the Bus system
+ Integrated most of the agent code in the city (except Restaurant)
	- Market staffers shift changes is not fully coded yet.
+ Helped write/added to much of the person code (with yocca)
+ Created and wrote unit tests for the Person Creation Panel
	- Panel tests not completely exhaustive, could be longer
+ Wrote grading scenarios


### Known Bugs/Missing Functionality

+ Market agent code does not fully allow shift changes, works fine with one shift of 
employees
+ Some bugs in the zoomed animation of the bank, agent code seems to be working fine
+ 
