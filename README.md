team35
======

## SimCity201 Project Repository for CS 201 students

### How to Run our SimCity
Run cityGui.SimCityGui. This is our main application. To run tests, run the tests in UnitTests.BankUnitTests, UnitTests.BusUnitTest, UnitTests.MarketUnitTests, and UnitTests.restaurantLindaUnitTests. There are also gui unit tests in UnitTests.GuiUnitTests.

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

#### 5. House
Start SimCity(run the SimCityGui.java), and click “Add House” button. Place the image on the green and left click. 
Each house contains one inhabitant and the inventory.
Click on the house to view inside: bed (black), table (cyan), refrigerator (white), grill (gray).
Entering: the building can be entered by call the personIn method in House; and set the active role in person to inhabitant.
	-I have already modified the method in person, and waiting for the city animation people to make the person walk to the house and call this method.

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

##### Simon (Fangli) Hou

+ Co-designed the Person with yocca
+ Fully coded the House and House animation(CityHouseCard.java)
+ Fully coded the InhabitantRole and InhabitantGui
+ Co-designed the CityAnimation with yocca
+ Designed the method to integrate the Animation of building into the the main window
+ Helped all teammates to integrate their building animations into the main window
+ Designed the method to keep individual animation panels running in the background
+ Created / added to multiple interfaces
+ Kept designs simple and efficient, and cleared other’s redundant codes

##### Parker Seagren
+ Co-designed the Market roles (with Linda Xu)
+ Co-implemented the Market roles (with Linda Xu)
+ Fully designed and coded the market animation
+ Fully designed and coded the market panel
+ Updated all market roles to cohere with animation and city
+ Fully unit tested all market roles, including those that I did not design/implement
+ Co-designed transportation (with Gabriel)
+ Integrated the market animation, panel, and guis into the main city gui
+ Helped the rest of the team to suppress bugs and compiler errors

##### Andrew Yocca
+ Designed the City Gui with the exception of the bus system
+ Coded the person movement in the City Gui
+ Implemented sprites into the city gui, market, bank, restaurant, and house
+ Coded the person agent and designed the person scheduler
	- ‘meldefon’ made a lot of additions to make it work properly and to test it
+ Created and wrote unit test for the Person Agent


### Known Bugs/Missing Functionality

+ Market agent code does not fully allow shift changes, works fine with one shift of 
employees
+ Some bugs in the zoomed animation of the bank, agent code seems to be working fine
+ 
