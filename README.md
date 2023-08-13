
# Sustainable Outfit Tracker for CPSC 210

## Outfit Carbon Tracker 

### **Task 2**

#### Who can use this app?

> **_Outfit Carbon Tracker_** is for people interested in the impact of fast fashion on
> climate change and global sustainability. It facilitates adding
> information about brand name/designer name, supplying country, color, size, material. 


#### Personal interest 

Using the Outfit Carbon Tracker would greatly benefit my fashion choices and help me make more informed decisions. 
As a fashion enthusiast, it's important for me to ensure I am making the right choices when it comes to my outfit 
purchases. With the emergence of fast fashion, I am increasingly aware of the detrimental environmental impact caused 
by the industry, including the production of massive amounts of plastic. For instance, polyester, a widely used form of 
plastic derived from oil, has surpassed cotton as the mainstay of textile production, contributing to the global plastic 
waste issue. Additionally, the production of different materials often requires excessive amounts of water. 
To address these concerns, I am committed to becoming more environmentally conscious by using the Outfit Carbon Tracker,
which will allow me to keep a diary of my environmental impact through my daily wardrobe choices.

#### Why use this app?
There are several compelling reasons to use the Outfit Carbon Tracker app. 
Firstly, by utilizing this app, users can gain a deeper understanding of the environmental impact of their fashion choices.
The app goes beyond simply logging outfits by providing crucial information about the water usage and plastic production 
associated with each item.Additionally, the app promotes accountability and encourages individuals to take an active 
role in mitigating the negative effects of the fashion industry. 
By using the Outfit Carbon Tracker, users can contribute to a more sustainable future while still 
enjoying the world of fashion.

#### Possible features
- Add Clothing to Daily Closet Diary: Users can easily add clothing items to their daily closet diary,
allowing them to keep track of their outfits and their environmental impact.

- View Today's Worn Clothes: Users can access a list that displays the clothes they have worn for the current day, 
providing a convenient overview of their daily outfit choices.

- Remove Clothing from Closet: Users have the option to remove clothing items from their closet, enabling them to update 
their wardrobe and reflect accurate information in their diary.

- Edit Outfits: Users can edit the outfits available in their closet, allowing them to make modifications or additions 
to their collection as needed.

- View Water Usage of Outfits: The app provides information on the amount of water required to produce each outfit, 
empowering users to understand the environmental impact associated with their clothing choices.



- View Plastic Production of Outfits: Users can access data on the amount of plastic produced by each outfit, 
raising awareness about the plastic waste generated during the manufacturing process.

![Alt text](/Users/janner/IdeaProjects1/project_h7w5o/src/main/Untitled.png)
### **Task 3**

#### User stories :
-  As a user, I want to be able to _**add a clothing to my daily closet diary**_
-  As a user, I want to be able to _**view the list of logged clothes I wore for today**_
-  As a user, I want to be able to _**select a clothing from list by its index I wore for today**_
-  As a user, I want to be able to _**remove a clothing from my closet**_
-  As a user, I want to be able to _**add outfits from database**_

-  As a user, I want to be able to _**view the amount of water footprint of my outfit**_
-  As a user, I want to be able to _**view the highest climate impact outfit by export**_ 
-  As a user, I want to be able to _**view the highest climate impact outfit by water footprint**_ 

-  As a user, I want to be able to _**save my daily clothing log entries to file**_
-  As a user, I want to be able to _**load my daily clothing log entries from file**_

# Phase 3
## Instructions for grader
- You can generate the first required event of creating a new clothing related to adding Xs to a Y by pressing "New clothing"
- You can generate the second required event (remove) related to adding Xs to a Y by pressing "Remove clothing"
- You can locate my visual component by starting the program where splash screen appears and in NewClothingWindow 
- You can save the state of my application by exiting the program
- You can reload the state of my application by starting the program

# Phase 4: Task 2
At the end of the session, the console will print out the following

- when pressing "New clothing" and logging new clothing item it shows: 
  Wed Aug 09 16:50:30 PDT 2023
  New clothing added: skirt
- when pressing "Remove clothing" when item in the list is selected the console :
  Wed Aug 09 16:51:19 PDT 2023
  Is clothing removed: true
- when pressing "Add clothing" and selecting from the database clothing:
  Wed Aug 09 16:52:28 PDT 2023
  Existing clothing added: top

# Phase 4: Task 3
Reflection on the design and refactoring of the project:
- In my view, the most advantageous enhancement for this program
  would involve breaking down the entirety of its contents from one class into separate classes, each representing distinct GUI components.
  This approach would enable these components to interact with one another by means of a central class,
  facilitating bidirectional relationships among them.
- This would lead to improved cohesion and less coupling
- Log and OutfitDatabase could impplement an anstract class since they share similar functionalities 

# References: 
- https://github.students.cs.ubc.ca/CPSC210/TellerApp.git - Teller App
- https://www.baeldung.com/java-json - JSON in Java
- https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git - JsonSerialization Demo
- https://www.javatpoint.com/java-jtabbedpane - JTabbed Pane
- https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git - Alarm System
- 