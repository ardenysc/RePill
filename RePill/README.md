# rePill

## Description

Anyone that wishes to keep track of the pills they are taking can use this application. You can add or remove any kind of medication or supplement to the list as long as it is in a shape of a pill that can be counted individually. Enter information about each pill such as brief description, how many you have left, dosage, and medication time.

Many medications have fixed medication schedule you must adhere to, but it is very easy to miss especially when you have more than one medication to take on a daily basis. This applies to supplements as well since many of them are encouraged to be taken at certain time to maximize the effect. So, I wish to build an application that helps you keep track of your million pills and never miss a dosage or unexpectedly run out of it.

## User Stories

- As a user, I want to be able to add a pill to my collection of pills.
- As a user, I want to be able to remove a pill from my collection of pills.
- As a user, I want to be able to view count, dosage, description and schedule for each pill from my collection of pills.
- As a user, I want to be able to increment or decrement the count of a pill of my choice from my collection of pills.
- As a user, I want to be able to change the name, dosage, or description of a pill of my choice from my collection of pills.
- As a user, I want to be able to add, remove, or change schedule for each pill from my collection of pills.
- As a user, I want to be able to save my collection of pills.
- As a user, I want to be able to load the saved collection of pills.

## Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by clicking the button labelled "Add" and entering name, count, dosage and description of a new pill (count and dosage only accept decimal inputs), then clicking "OK"
- You can generate the second required event related to adding Xs to a Y by clicking "Select" of a row you want to remove, then clicking "Delete"
- You can locate my visual component by successfully loading or saving data. An image of Java Duke will pop up to congratulate you.
- You can save the state of my application by clicking "File" in the upper left corner and clicking "Save Data"
- You can reload the state of my application by clicking "File" in the upper left corner and clicking "Load Data"

[//]: # (### Adding a new Pill)

[//]: # (- Click the button labelled "Add")

[//]: # (- Input name, count, dosage and description of a pill &#40;count and dosage only accept decimal inputs&#41;)

[//]: # (- Click "OK")

[//]: # (### Removing a Pill)

[//]: # (- Click "Select" of a row you want to remove)

[//]: # (- Click "Delete")

[//]: # (### Saving data)

[//]: # (- Click "File" in the upper left corner)

[//]: # (- Click "Save Data")

[//]: # ()
[//]: # (### Loading data)

[//]: # (- Click "File" in the upper left corner)

[//]: # (- Click "Load Data")

[//]: # (### Visual Component)

[//]: # (- An image of Java Duke pops up when data has been successfully saved or loaded)

## Citation
- Classes in <code>persistence</code> package and methods <code>toJason()</code> from <code>Collection</code> class and <code>Pill</code> class were taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo with some modifications.
- Class <code>MenuBar</code> in <code>ui</code> package was taken from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuLookDemoProject/src/components/MenuLookDemo.java with some modifications.
- Classes <code>PillTableMenu</code> and <code>CollectionTable</code> were taken from https://stackoverflow.com/questions/16887534/java-gui-jtable-insert-link-to-remove-its-row-from-table and https://stackoverflow.com/questions/35369923/jlist-swing-adding-description-to-list-item with some modifications.

## Phase 4: Task 2
    Tue Nov 22 10:28:19 PST 2022
    New pill added to collection: tylenol
    Tue Nov 22 10:28:30 PST 2022
    New pill added to collection: advil
    Tue Nov 22 10:28:32 PST 2022
    Pill removed from collection: advil

## Phase 4: Task 3
- <code>Pill</code> class lacks cohesion; the <code>schedule</code> field and related methods should be in a separate class <code>Schedule</code>. <code>Pill</code> should have a single field or a collection of type <code>Schedule</code>, depending on the actual implementation. That way, <code>Pill</code> would be more cohesive and <code>Schedule</code> would be reusable in other occasions.
- <code>MenuBar</code> class should not extend <code>CollectionTable</code> class. I did it to let <code>MenuBar</code> have access to <code>tableModel</code> field in <code>CollectionTable</code> class; but <code>MenuBar</code> is not a subtype of <code>CollectionTable</code>, so it does not make sense to extend it. Instead, <code>CollectionTable</code> should have a single field of type <code>MenuBar</code>. Also, <code>MenuBar</code> is actually a part of <code>CollectionTable</code>, so the relationship could be aggregation.
- I did not include the console UI <code>RePill</code> in my UML diagram.

[//]: # (An example of text with **bold** and *italic* fonts.  )